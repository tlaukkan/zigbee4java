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

import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneEnrollResponse;
import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.core.ResponseImpl;

public class ZoneEnrollResponseImpl extends ResponseImpl implements ZoneEnrollResponse {

    private int enrollResponseCode;
    private int zoneID;

    public ZoneEnrollResponseImpl(Response response) throws ZigBeeClusterException {

        super(response);

        //ResponseImpl.checkGeneralCommandFrame(response, ZoneEnrollResponse.PROFILE_ID_HOME_AUTOMATION);
        ZBDeserializer deserializer = new DefaultDeserializer(getPayload(), 0);
        enrollResponseCode = (Integer) deserializer.readZigBeeType(ZigBeeType.Enumeration8bit);
        zoneID = (Integer) deserializer.readZigBeeType(ZigBeeType.UnsignedInteger8bit);
    }

    public ZoneEnrollResponseImpl(ClusterMessage clusterMessage, short expectedClusterId) throws ZigBeeClusterException {

        super(clusterMessage, expectedClusterId);

        //ResponseImpl.checkGeneralCommandFrame(response, ZoneEnrollResponse.PROFILE_ID_HOME_AUTOMATION);
        ZBDeserializer deserializer = new DefaultDeserializer(getPayload(), 0);
        enrollResponseCode = (Integer) deserializer.readZigBeeType(ZigBeeType.Enumeration8bit);
        zoneID = (Integer) deserializer.readZigBeeType(ZigBeeType.UnsignedInteger8bit);
    }

    public int getEnrollResponseCode() {

        return enrollResponseCode;
    }

    public int getZoneID() {

        return zoneID;
    }
}