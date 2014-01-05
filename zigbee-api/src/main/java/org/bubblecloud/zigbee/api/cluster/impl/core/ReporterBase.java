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

package org.bubblecloud.zigbee.api.cluster.impl.core;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.*;
import org.bubblecloud.zigbee.network.ClusterFilter;
import org.bubblecloud.zigbee.network.ClusterListener;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.AttributeReport;
import org.bubblecloud.zigbee.api.cluster.impl.global.reporting.ReportAttributesCommand;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 */
public abstract class ReporterBase implements Reporter {

    private final Logger log = LoggerFactory.getLogger(ReporterBase.class);

    protected final ArrayList<ReportListener> listeners = new ArrayList<ReportListener>();
    protected final ReportListenerNotifier bridge = new ReportListenerNotifier();
    protected final ZigBeeEndpoint device;
    protected final ZCLCluster cluster;

    @Override
    public Attribute getAttribute() {
        return attribute;
    }

    protected final Attribute attribute;

    protected int max = Reporter.DEFAULT_MAX_REPORTING_INTERVAL;
    protected int min = Reporter.DEFAULT_MIN_REPORTING_INTERVAL;

    protected class ReportListenerNotifier implements ClusterListener {

        public void handleCluster(ZigBeeEndpoint endpoint, ClusterMessage c) {
            try {
                if (c.getId() != cluster.getId()) return;
                ResponseImpl response = new ResponseImpl(c, cluster.getId());
                AttributeReport[] reports = new ReportAttributesCommand(response).getAttributeReports();
                Dictionary<Attribute, Object> event = new Hashtable<Attribute, Object>();
                for (int i = 0; i < reports.length; i++) {
                    event.put(
                            cluster.getAttribute(reports[i].getAttributeId()),
                            reports[i].getAttributeData()
                    );
                }
                ArrayList<ReportListener> localCopy;
                synchronized (listeners) {
                    localCopy = new ArrayList<ReportListener>(listeners);
                }
                log.debug("Notifying {} ReportListener", localCopy.size());
                for (ReportListener reportListner : localCopy) {
                    try {
                        log.debug("Notifying {}:{}", reportListner.getClass().getName(), reportListner);
                        reportListner.receivedReport(event);
                    } catch (Exception e) {
                        log.error("Error while notifying {}:{} caused by {}", new Object[]{
                                reportListner.getClass().getName(), reportListner, e.getStackTrace()
                        });
                    }
                }

            } catch (ZigBeeClusterException e) {
                e.printStackTrace();
            }
        }

        public ClusterFilter getClusterFilter() {
            return SubscriptionClusterFilter.FILTER;
        }

        public void setClusterFilter(ClusterFilter filter) {
        }

    }

    public ReporterBase(final ZigBeeEndpoint zb, final ZCLCluster c, final Attribute attrib) {
        device = zb;
        cluster = c;
        attribute = attrib;
    }

    private boolean doBindToDevice() {
        try {
            return device.bindToLocal(cluster.getId());
        } catch (ZigBeeNetworkManagerException e) {
            log.debug("Unable to bind to device {} on cluster {}", device, cluster.getId());
            log.error("Binding failed", e);
            return false;
        }
    }

    private boolean doUnbindToDevice() {
        try {
            return device.unbindFromLocal(cluster.getId());
        } catch (ZigBeeNetworkManagerException e) {
            log.debug("Unable to bind to device {} on cluster {}", device, cluster.getId());
            log.error("Binding failed", e);
            return false;
        }
    }

    protected abstract boolean doConfigureServer() throws ZigBeeClusterException;

    public boolean addReportListener(ReportListener listener) {
        synchronized (listeners) {
            if (listeners.size() == 0) {
                if (!doBindToDevice()) {
                    return false;
                }
                try {
                    doConfigureServer();
                } catch (ZigBeeClusterException e) {
                    log.error("Unable to configure server for Reporting", e);
                    return false;
                }
                device.addClusterListener(bridge);
            }
            listeners.add(listener);
        }
        return true;
    }

    public void clear() {
        if (doUnbindToDevice() == true) {
            synchronized (listeners) {
                listeners.clear();
            }
        }
    }

    public int getMaximumReportingInterval() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getMinimumReportingInterval() {
        // TODO Auto-generated method stub
        return 0;
    }

    public boolean removeReportListener(ReportListener listener) {
        synchronized (listeners) {
            if (listeners.size() == 1) {
                if (!doUnbindToDevice()) {
                    return false;
                }
                //TODO Change the configuration only if there were no subscriber
                try {
                    doConfigureServer();
                } catch (ZigBeeClusterException e) {
                    log.error("Unable to configure server for Reporting", e);
                    return false;
                }
                device.removeClusterListener(bridge);
            }
            listeners.remove(listener);
        }
        return true;
    }

    public int setMaximumReportingInterval(int value) {
        //TODO Check the real value
        max = value;
        return max;
    }

    public int setMinimumReportingInterval(int value) {
        min = value;
        return min;
    }

    public int getReportListenersCount() {
        synchronized (listeners) {
            return listeners.size();
        }
    }

    public boolean isActive() {
        synchronized (listeners) {
            return listeners.size() == 0;
        }
    }

    public boolean updateConfiguration() {
        try {
            if (isActive()) {
                return doConfigureServer();
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error("Unable to update Report configuration", e);
            return false;
        }
    }
}
