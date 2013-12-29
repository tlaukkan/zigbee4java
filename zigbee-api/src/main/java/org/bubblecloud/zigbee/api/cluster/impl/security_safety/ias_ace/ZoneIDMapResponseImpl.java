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
package org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace.ZoneIDMapResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace.ZoneTable;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace.ZoneTable.Zone;
import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.core.ResponseImpl;

public class ZoneIDMapResponseImpl extends ResponseImpl implements ZoneIDMapResponse {

    private ZoneTable zonesID;

    public ZoneIDMapResponseImpl(Response response) throws ZigBeeClusterException {
        super(response);

        ResponseImpl.checkSpecificCommandFrame(response, ID);

        ZBDeserializer deserializer = new DefaultDeserializer(getPayload(), 0);

        zonesID = new ZoneTableImpl();
        for (int i = 0; i < 16; i++)
            zonesID.addZone(new Short(deserializer.read_short()), null, null);
    }

    public Zone[] getZonesID() {
        Zone[] zones = new Zone[zonesID.getZoneTable().length];
        for (int i = 0; i < zonesID.getZoneTable().length; i++)
            zones[i] = zonesID.getZoneTable()[i];

        return zones;
    }
}