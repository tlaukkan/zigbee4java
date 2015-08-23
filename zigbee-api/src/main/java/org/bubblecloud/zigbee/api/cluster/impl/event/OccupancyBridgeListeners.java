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
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.event.OccupancyEvent;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.event.OccupancyListener;
import org.bubblecloud.zigbee.api.ReportingConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ReportListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Default implementation of the delegator class that handles the eventing of the {@link OccupancyListener}
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 */
public class OccupancyBridgeListeners implements ReportListener {

    private class OccupancyEventImpl implements OccupancyEvent {

        private final Cluster source;
        private final int event;

        public OccupancyEventImpl(Cluster cluster, Integer value) {
            source = cluster;
            event = value;
        }

        public int getEvent() {
            return event;
        }

        public Cluster getSource() {
            return source;
        }
    }

    private final Attribute bridged;
    private final ArrayList<OccupancyListener> listeners = new ArrayList<OccupancyListener>();
    private final Cluster cluster;
    private final ReportingConfiguration configuration;

    public OccupancyBridgeListeners(final ReportingConfiguration conf, final Attribute attribute, final Cluster c) {
        bridged = attribute;
        cluster = c;
        configuration = conf;
    }

    public void receivedReport(final String endPointId, final short clusterId, Dictionary<Attribute, Object> reports) {
        if (reports.get(bridged) == null) {
            return;
        }
        synchronized (listeners) {
            for (OccupancyListener listener : listeners) {
                listener.changedOccupancy(new OccupancyEventImpl(cluster, (Integer) reports.get(bridged)));
            }
        }
    }

    public List<OccupancyListener> getListeners() {
        return listeners;
    }

    public boolean subscribe(OccupancyListener listener) {
        synchronized (listeners) {
            if (listeners.size() == 0) {
                Reporter reporter = bridged.getReporter();
                if (configuration.getReportingOverwrite() || reporter.isActive() == false) {
                    reporter.setMaximumReportingInterval(configuration.getReportingMaximum());
                    reporter.setMinimumReportingInterval(configuration.getReportingMinimum());
                    reporter.updateConfiguration();
                }
                if (reporter.addReportListener(this, true) == false) {
                    return false;
                }
            }
            return listeners.add(listener);
        }
    }

    public boolean unsubscribe(OccupancyListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
            if (listeners.size() == 0) {
                Reporter reporter = bridged.getReporter();
                if (reporter.getReportListenersCount() == 1) {
                    reporter.clear();
                }
            }
        }
        return true;
    }
}
