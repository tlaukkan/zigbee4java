/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 


   See the NOTICE file distributed with this work for additional 
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.bubblecloud.zigbee.api.cluster.impl.event;

import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.general.event.OnOffEvent;
import org.bubblecloud.zigbee.api.cluster.general.event.OnOffListener;
import org.bubblecloud.zigbee.api.ReportingConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ReportListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Default implementation of the delegator class that handles the eventing of the {@link OnOffListener}
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 */
public class OnOffBridgeListeners implements ReportListener {

    private class OnOffEventImpl implements OnOffEvent {

        private final Cluster source;
        private final boolean event;

        public OnOffEventImpl(Cluster cluster, Boolean value) {
            source = cluster;
            event = value;
        }

        public boolean getEvent() {
            return event;
        }

        public Cluster getSource() {
            return source;
        }
    }

    private final Attribute bridged;
    private final ArrayList<OnOffListener> listeners = new ArrayList<OnOffListener>();
    private final Cluster cluster;
    private final ReportingConfiguration configuration;

    public OnOffBridgeListeners(final ReportingConfiguration conf, final Attribute attribute, final Cluster c) {
        bridged = attribute;
        cluster = c;
        configuration = conf;
    }

    public void receivedReport(final String endPointId, final short clusterId,
                               Dictionary<Attribute, Object> reports) {
        if (reports.get(bridged) == null) {
            return;
        }
        synchronized (listeners) {
            for (OnOffListener listener : listeners) {
                listener.changedOnOff(new OnOffEventImpl(cluster, (Boolean) reports.get(bridged)));
            }
        }
    }

    public List<OnOffListener> getListeners() {
        return listeners;
    }

    public boolean subscribe(OnOffListener listener) {
        synchronized (listeners) {
            if (listeners.isEmpty()) {
                Reporter reporter = bridged.getReporter();
                if (configuration.getReportingOverwrite() || !reporter.isActive()) {
                    reporter.setMaximumReportingInterval(configuration.getReportingMaximum());
                    reporter.setMinimumReportingInterval(configuration.getReportingMinimum());
                    reporter.updateConfiguration();
                }
                if (!reporter.addReportListener(this, true)) {
                    return false;
                }
            }
            return listeners.add(listener);
        }
    }

    public boolean unsubscribe(OnOffListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                Reporter reporter = bridged.getReporter();
                if (reporter.getReportListenersCount() == 1) {
                    reporter.clear();
                }
            }
        }
        return true;
    }
}
