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

package org.bubblecloud.zigbee.api.cluster.impl;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.general.Alarms;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.alarms.AlarmListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.alarms.GetAlarmResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.DefaultResponse;
import org.bubblecloud.zigbee.api.cluster.impl.general.AlarmsCluster;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 */
public class AlarmsImpl implements Alarms {

    private final AlarmsCluster alarmsCluster;
    private final Attribute attribute;

    public AlarmsImpl(ZigBeeEndpoint zbDevice) {
        alarmsCluster = new AlarmsCluster(zbDevice);
        attribute = alarmsCluster.getAttributeAlarmCount();
    }

    public boolean getOnOff() throws ZigBeeDeviceException {
        try {
            Boolean value = (Boolean) attribute.getValue();
            return value.booleanValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Reporter[] getAttributeReporters() {
        return alarmsCluster.getAttributeReporters();
    }

    public int getId() {
        return alarmsCluster.getId();
    }

    public String getName() {
        return alarmsCluster.getName();
    }


    public Attribute getAttribute(int id) {
        Attribute[] attributes = alarmsCluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getId() == id)
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return alarmsCluster.getAvailableAttributes();
    }

    public Attribute getAttributeAlarmCount() {
        return attribute;
    }

    public boolean addAlarmListener(AlarmListener listener) {
        return alarmsCluster.addAlarmListerner(listener);
    }

    public boolean removeAlarmListerner(AlarmListener listener) {
        return alarmsCluster.removeAlarmListerner(listener);
    }

    public GetAlarmResponse getAlarm() throws ZigBeeDeviceException {
        try {
            Response response = alarmsCluster.getAlarm();
            if (response.getZCLHeader().getCommandId() != GetAlarmResponse.ID)
                throw new ZigBeeDeviceException(((DefaultResponse) response).getStatus().toString());

            return (GetAlarmResponse) response;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void resetAlarm(int clusterId, int attributeId) throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) alarmsCluster.resetAlarm(clusterId, attributeId);
            if (response.getStatus() != Status.SUCCESS)
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void resetAlarmLog() throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) alarmsCluster.resetAlarmLog();
            if (response.getStatus() != Status.SUCCESS)
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void resetAllAlarms() throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) alarmsCluster.resetAllAlarms();
            if (response.getStatus() != Status.SUCCESS)
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }
}
