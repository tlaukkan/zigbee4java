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
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.event.MeasuredValueEvent;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.event.MeasuredValueListener;
import org.bubblecloud.zigbee.api.ReportingConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.AnalogReporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ReportListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Default implementation of the delegator class that handles the eventing of the {@link MeasuredValueListener}
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 */
public class MeasuredValueBridgeListeners implements ReportListener {

    private class MeasuredValueEventImpl implements MeasuredValueEvent {

        private final Cluster source;
        private final int event;

        public MeasuredValueEventImpl(Cluster cluster, Integer value) {
            source = cluster;
            event = value.intValue();
        }

        public int getEvent() {
            return event;
        }

        public Cluster getSource() {
            return source;
        }
    }

    private final Attribute bridged;
    private final ArrayList<MeasuredValueListener> listeners = new ArrayList<MeasuredValueListener>();
    private final Cluster cluster;
    private final ReportingConfiguration configuration;

    /**
     * Constructor
     * @param conf {@link ReportingConfiguration}
     * @param attribute {@link Attribute}
     * @param c {@link Cluster}
     */
    public MeasuredValueBridgeListeners(final ReportingConfiguration conf, final Attribute attribute, final Cluster c) {
        bridged = attribute;
        cluster = c;
        configuration = conf;
    }

    /**
     * Called by the framework when a report has been received. This method calls all the listeners
     * <i>changedMeasuredValue</i> methods.
     */
    public void receivedReport(final String endPointId, final short clusterId,
                               final Dictionary<Attribute, Object> reports) {
        if (reports.get(bridged) == null) {
            return;
        }
        synchronized (listeners) {
            for (MeasuredValueListener listener : listeners) {
                listener.changedMeasuredValue(new MeasuredValueEventImpl(cluster, (Integer) reports.get(bridged)));
            }
        }
    }

    /**
     * Returns a {@link List} of registered {@link MeasuredValueListener} 
     * @return {@link List} of {@link MeasuredValueListener}
     */
    public List<MeasuredValueListener> getListeners() {
        return listeners;
    }

    /**
     * Subscribes to a report.
     * <p>
     * This method will update the device configuration.
     * 
     * @param listener {@link MeasuredValueListener}
     * @return true if the listener was subscribed
     */
    public boolean subscribe(MeasuredValueListener listener) {
        synchronized (listeners) {
            if (listeners.size() == 0) {
                AnalogReporter reporter = (AnalogReporter) bridged.getReporter();
                if (configuration.getReportingOverwrite() || reporter.isActive() == false) {
                    reporter.setMaximumReportingInterval(configuration.getReportingMaximum());
                    reporter.setMinimumReportingInterval(configuration.getReportingMinimum());
                    reporter.setReportableChange(configuration.getReportingChange());
                    reporter.updateConfiguration();
                }
                if (reporter.addReportListener(this, true) == false) {
                    return false;
                }
            }
            return listeners.add(listener);
        }
    }

    public boolean unsubscribe(MeasuredValueListener listener) {
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
