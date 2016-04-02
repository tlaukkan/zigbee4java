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

import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASZone;

/**
 * The ZCL commands.
 *
 * @author Tommi S.E. Laukkanen
 */
public enum ZclCommand {

    // HA IAS Zone commands
    ZONE_ENROLL_REQUEST(ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION, ZigBeeApiConstants.CLUSTER_ID_IAS_ZONE, IASZone.ZONE_ENROLL_REQUEST_ID, false, true),
    ZONE_ENROLL_RESPONSE(ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION, ZigBeeApiConstants.CLUSTER_ID_IAS_ZONE, IASZone.ZONE_ENROLL_RESPONSE_ID, true, true);

    /**
     * The profile ID.
     */
    final public int profileId;
    /**
     * The cluster ID.
     */
    final public int clusterId;
    /**
     * The command ID.
     */
    final public int commandId;
    /**
     * Is client server direction.
     */
    final public boolean isClientServerDirection;
    /**
     * Is this command cluster specific.
     */
    final public boolean isClusterSpecific;
    /**
     * The constructor for setting enumeration parameters.
     * @param profileId the profile ID
     * @param clusterId the cluster ID
     * @param commandId the command ID
     */
    private ZclCommand(int profileId, int clusterId, int commandId, boolean isClientServerDirection, boolean isClusterSpecific){
        this.profileId = profileId;
        this.clusterId = clusterId;
        this.commandId = commandId;
        this.isClientServerDirection = isClientServerDirection;
        this.isClusterSpecific = isClusterSpecific;
    }
}
