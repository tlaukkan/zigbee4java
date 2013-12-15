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

package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASZone;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.alarms.GetAlarmResponse;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneEnrollRequestPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneEnrollResponse;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneStatusChangeNotificationListener;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneStatusChangeNotificationPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneStatusChangeNotificationResponse;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.IASZoneCluster;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 *
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public class IASZoneImpl implements IASZone {

    private final IASZoneCluster cluster;

    private final Attribute zoneState;
    private final Attribute zoneType;
    private final Attribute zoneStatus;
    private final Attribute iasCIEaddress;

    public IASZoneImpl(ZigBeeDevice zbDevice){

        cluster = new IASZoneCluster(zbDevice);
        zoneState = cluster.getAttributeZoneState();
        zoneType = cluster.getAttributeZoneType();
        zoneStatus = cluster.getAttributeZoneStatus();
        iasCIEaddress = cluster.getAttributeIASCIEAddress();
    }

    public int getId() {
        return cluster.getId();
    }

    public String getName() {
        return cluster.getName();
    }

    public Subscription[] getActiveSubscriptions() {
        return cluster.getActiveSubscriptions();
    }

    public Attribute[] getAttributes() {
        return cluster.getAvailableAttributes();
    }

    public Attribute getAttribute(int id) {

        Attribute[] attributes = cluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if( attributes[i].getId() == id )
                return attributes[i];
        }
        return null;
    }

    public Attribute getZoneState() {

        return zoneState;
    }

    public Attribute getZoneType() {

        return zoneType;
    }

    public Attribute getZoneStatus() {

        return zoneStatus;
    }

    public Attribute getIASCIEAddress() {

        return iasCIEaddress;
    }

    public ZoneEnrollResponse zoneEnrollRequest(ZoneEnrollRequestPayload payload) throws ZigBeeHAException {

        try {
            ZoneEnrollResponse response = (ZoneEnrollResponse) cluster.zoneEnrollRequest(payload);
            return response;
        }
        catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public Response zoneStatusChangeNotification(ZoneStatusChangeNotificationPayload payload) throws ZigBeeHAException {
        try {
            Response response = cluster.zoneStatusChangeNotification(payload);
            if (response.getZCLHeader().getCommandId() != ZoneStatusChangeNotificationResponse.ID)
                throw new ZigBeeHAException( ((DefaultResponse) response).getStatus().toString());

            return (GetAlarmResponse) response;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }

        /*try {
            cluster.zoneStatusChangeNotification();
        }
        catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }*/
    }

    public boolean addZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener) {
        return cluster.addZoneStatusChangeNotificationListener(listener);
    }

    public boolean removeZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener) {
        return cluster.removeZoneStatusChangeNotificationListener(listener);
    }
}