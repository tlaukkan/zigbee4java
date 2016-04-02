/*
   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
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
package org.bubblecloud.zigbee.network.impl.protocol;


import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASZone;

/**
 * The IAS zone types.
 *
 * @author Tommi S.E. Laukkanen
 */
public enum ZclCommandField {


    ZONE_TYPE(ZigBeeType.Enumeration16bit, ZclCommand.ZONE_ENROLL_REQUEST),
    MANUFACTURE_CODE(ZigBeeType.UnsignedInteger16bit, ZclCommand.ZONE_ENROLL_REQUEST);

    final public ZclCommand command;
    final public int profileId;
    final public int clusterId;
    final public int commandId;
    final public ZigBeeType type;

    private ZclCommandField(ZigBeeType type, ZclCommand command){
        this.command = command;
        this.profileId = command.profileId;
        this.clusterId = command.clusterId;
        this.commandId = command.commandId;
        this.type = type;
    }
}
