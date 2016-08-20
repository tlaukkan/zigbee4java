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
package org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_zone;

import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneStatusChangeNotificationPayload;

public class ZoneStatusChangeNotificationPayloadImpl implements ZoneStatusChangeNotificationPayload {

    private short zoneStatus;
    private byte extendedStatus;

    public ZoneStatusChangeNotificationPayloadImpl(ZoneStatusChangeNotificationPayload payload) {

        this.zoneStatus = payload.getZoneStatus();
        this.extendedStatus = payload.getExtendedStatus();
    }

    public short getZoneStatus() {
        return zoneStatus;
    }

    public byte getExtendedStatus() {
        return extendedStatus;
    }
}