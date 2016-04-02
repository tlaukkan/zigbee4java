/**
 * Copyright 2016 Tommi S.E. Laukkanen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bubblecloud.zigbee.network.zcl;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

/**
 * The field metadata for ZCL commands.
 *
 * The order of the fields in enumeration is the order of serialisation.
 *
 * @author Tommi S.E. Laukkanen
 */
public enum ZclCommandField {

    // ZONE_ENROLL_REQUEST
    ZONE_TYPE(ZigBeeType.Enumeration16bit, ZclCommand.ZONE_ENROLL_REQUEST),
    MANUFACTURE_CODE(ZigBeeType.UnsignedInteger16bit, ZclCommand.ZONE_ENROLL_REQUEST),

    // ZONE_ENROLL_RESPONSE
    ENROLL_RESPONSE_CODE(ZigBeeType.Enumeration8bit, ZclCommand.ZONE_ENROLL_RESPONSE),
    ZONE_ID(ZigBeeType.UnsignedInteger8bit, ZclCommand.ZONE_ENROLL_RESPONSE);

    /**
     * The command.
     */
    final public ZclCommand command;
    /**
     * The value type.
     */
    final public ZigBeeType type;
    /**
     * The profile ID replicated from command.
     */
    final public int profileId;
    /**
     * The cluster ID replicated from command.
     */
    final public int clusterId;
    /**
     * The command ID replicated from command.
     */
    final public int commandId;

    /**
     * Enumeration constructor.
     * @param type the value type
     * @param command the command
     */
    ZclCommandField(ZigBeeType type, ZclCommand command){
        this.command = command;
        this.profileId = command.profileId;
        this.clusterId = command.clusterId;
        this.commandId = command.commandId;
        this.type = type;
    }
}
