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

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 * @since 0.6.0
 */
public abstract class ReporterBase implements Reporter {

    private final Logger log = LoggerFactory.getLogger(ReporterBase.class);

    protected final Set<ReportListener> listeners = new HashSet<ReportListener>();
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
                if (c.getId() != cluster.getId()) {
                	return;
                }
                ResponseImpl response = new ResponseImpl(c, cluster.getId());
                AttributeReport[] reports = new ReportAttributesCommand(response).getAttributeReports();
                Dictionary<Attribute, Object> event = new Hashtable<Attribute, Object>();
                for (int i = 0; i < reports.length; i++) {
                    event.put(
                            cluster.getAttribute(reports[i].getAttributeId()),
                            reports[i].getAttributeData()
                    );
                }
                HashSet<ReportListener> localCopy;
                synchronized (listeners) {
                    localCopy = new HashSet<ReportListener>(listeners);
                }
                log.debug("Notifying {} ReportListener", localCopy.size());
                for (ReportListener reportListner : localCopy) {
                    try {
                        log.debug("Notifying {}:{}", reportListner.getClass().getName(), reportListner);
                        reportListner.receivedReport(endpoint.getEndpointId(), cluster.getId(), event);
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
            log.debug("Unable to unbind to device {} on cluster {}", device, cluster.getId());
            log.error("Unbinding failed", e);
            return false;
        }
    }

    protected abstract boolean doConfigureServer() throws ZigBeeClusterException;

    /**
     * Adds a report listener. If the listener already exists (ie the listener is already subscribed)
     * then it is not added.
     * <p>
     * If <i>subscribe</i> is set to true then a call will be made to the device to configure the reporting
     * subscription.
     * <p>
     * The method returns true if the listener was added. If the listener already existed, then <i>false</i>
     * will be returned.
     * 
     * @param listener the callback to be used for the listener
     * @param subscribe true if a subscribe call should be made to the device
     * @return true if the listener was added. false if there was an error or the
     *            listener was already listening
     *
     */
    public boolean addReportListener(ReportListener listener, boolean subscribe) {
        synchronized (listeners) {
            if (subscribe) {
                if (!doBindToDevice()) {
                    return false;
                }
                try {
                    doConfigureServer();
                } catch (ZigBeeClusterException e) {
                    log.error("Unable to configure server for Reporting (Add)", e);
                    return false;
                }
            }

            // Add a cluster listener
            device.addClusterListener(bridge);
            
            // And add the attribute listener
            listeners.add(listener);
        }
        return true;
    }

    /**
     * Clears all listeners.
     */
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

    public boolean removeReportListener(ReportListener listener, boolean unsubscribe) {
        synchronized (listeners) {
            if (unsubscribe) {
                if (!doUnbindToDevice()) {
                    return false;
                }
                //TODO Change the configuration only if there were no subscriber
                try {
                    doConfigureServer();
                } catch (ZigBeeClusterException e) {
                    log.error("Unable to configure server for Reporting (Remove)", e);
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

    /**
     * Gets the number of listeners current subscribed
     * @return integer number of subscribed listeners
     */
    public int getReportListenersCount() {
        synchronized (listeners) {
            return listeners.size();
        }
    }

    /**
     * Returns true if the attribute currently has any active listeners
     * @return true if there are active listeners
     */
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
