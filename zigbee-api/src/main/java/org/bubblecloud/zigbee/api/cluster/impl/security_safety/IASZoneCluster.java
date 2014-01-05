/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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
package org.bubblecloud.zigbee.api.cluster.impl.security_safety;

import org.bubblecloud.zigbee.network.ClusterFilter;
import org.bubblecloud.zigbee.network.ClusterListener;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASZone;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneEnrollRequestPayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneEnrollResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneStatusChangeNotificationListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneStatusChangeNotificationPayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneStatusChangeNotificationResponse;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;
import org.bubblecloud.zigbee.api.cluster.impl.global.DefaultResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_zone.IAS_ZoneZoneStatusChangeNotificationFilter;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_zone.ZoneEnrollRequestCommand;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_zone.ZoneEnrollResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_zone.ZoneStatusChangeNotificationCommand;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_zone.ZoneStatusChangeNotificationResponseImpl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 */
public class IASZoneCluster extends ZCLClusterBase implements IASZone {

    private final AttributeImpl attributeZoneState;
    private final AttributeImpl attributeZoneType;
    private final AttributeImpl attributeZoneStatus;
    private final AttributeImpl attributeIASCIEAddress;

    private final ArrayList<ZoneStatusChangeNotificationListener> listeners = new ArrayList<ZoneStatusChangeNotificationListener>();
    private ZoneStatusChangeNotificationListenerNotifier bridge;

    private final Attribute[] attributes;

    private final Logger log = LoggerFactory.getLogger(IASZoneCluster.class);

    public IASZoneCluster(ZigBeeEndpoint zbDevice) {

        super(zbDevice);

        attributeZoneState = new AttributeImpl(zbDevice, this, Attributes.ZONE_STATE);
        attributeZoneType = new AttributeImpl(zbDevice, this, Attributes.ZONE_TYPE);
        attributeZoneStatus = new AttributeImpl(zbDevice, this, Attributes.ZONE_STATUS);
        attributeIASCIEAddress = new AttributeImpl(zbDevice, this, Attributes.IAS_CIE_ADDRESS);

        attributes = new AttributeImpl[]{attributeZoneState, attributeZoneType, attributeZoneStatus, attributeIASCIEAddress};

        bridge = new ZoneStatusChangeNotificationListenerNotifier();
    }

    public Attribute getAttributeZoneState() {

        return attributeZoneState;
    }

    public Attribute getAttributeZoneType() {

        return attributeZoneType;
    }

    public Attribute getAttributeZoneStatus() {

        return attributeZoneStatus;
    }

    public Attribute getAttributeIASCIEAddress() {

        return attributeIASCIEAddress;
    }

    @Override
    public short getId() {

        return IASZone.ID;
    }

    @Override
    public String getName() {

        return IASZone.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {

        return attributes;
    }

    public Response zoneStatusChangeNotification(ZoneStatusChangeNotificationPayload payload) throws ZigBeeClusterException {

        enableDefaultResponse();
        ZoneStatusChangeNotificationCommand zscnc = new ZoneStatusChangeNotificationCommand(payload);
        Response response = invoke(zscnc);
        return new DefaultResponseImpl(response);
    }

    public ZoneEnrollResponse zoneEnrollRequest(ZoneEnrollRequestPayload payload) throws ZigBeeClusterException {

        ZoneEnrollRequestCommand zoneEnrCmd = new ZoneEnrollRequestCommand(payload);
        Response response = invoke(zoneEnrCmd, false);
        return new ZoneEnrollResponseImpl(response);
    }

    public boolean addZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener) {
        synchronized (listeners) {
            if (listeners.size() == 0) {
                try {
                    getZigBeeEndpoint().bindToLocal(ID);
                } catch (ZigBeeNetworkManagerException e) {
                    log.error("Unable to bind to device for IASZone reporting", e);
                    return false;
                }
                if (getZigBeeEndpoint().addClusterListener(bridge) == false) {
                    log.error("Unable to register the cluster listener for IASZone reporting");
                    return false;
                }
            }
            listeners.add(listener);
            return true;
        }
    }

    public boolean removeZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener) {
        synchronized (listeners) {
            boolean removed = listeners.remove(listener);
            if (listeners.size() == 0 && removed) {
                try {
                    getZigBeeEndpoint().unbindFromLocal(ID);
                } catch (ZigBeeNetworkManagerException e) {
                    log.error("Unable to unbind to device for IASZone reporting", e);
                    return false;
                }
                if (getZigBeeEndpoint().removeClusterListener(bridge) == false) {
                    log.error("Unable to unregister the cluster listener for IASZone reporting");
                    return false;
                }
            }
            return removed;
        }
    }

    private class ZoneStatusChangeNotificationListenerNotifier implements ClusterListener {

        public void setClusterFilter(ClusterFilter filter) {
        }

        public ClusterFilter getClusterFilter() {
            return IAS_ZoneZoneStatusChangeNotificationFilter.FILTER;
        }

        public void handleCluster(ZigBeeEndpoint endpoint, ClusterMessage c) {
            try {
                ResponseImpl response = new ResponseImpl(c, ID);
                ZoneStatusChangeNotificationResponse zscnr = new ZoneStatusChangeNotificationResponseImpl(response);
                ArrayList<ZoneStatusChangeNotificationListener> localCopy;
                synchronized (listeners) {
                    localCopy = new ArrayList<ZoneStatusChangeNotificationListener>(listeners);
                }
                log.debug("Notifying {} ZoneStatusChangeNotificationListener", localCopy.size());
                for (ZoneStatusChangeNotificationListener alarmListner : localCopy) {
                    try {
                        log.debug("Notifying {}:{}", alarmListner.getClass().getName(), alarmListner);
                        alarmListner.zoneStatusChangeNotification(zscnr.getZoneStatus());
                    } catch (Exception e) {
                        log.error("Error while notifying {}:{} caused by {}", new Object[]{
                                alarmListner.getClass().getName(), alarmListner, e.getStackTrace()
                        });
                    }
                }

            } catch (ZigBeeClusterException e) {
                e.printStackTrace();
            }
        }
    }
}