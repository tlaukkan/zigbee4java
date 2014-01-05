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
package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.Commissioning;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.commissioning.ResetStartupPayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.commissioning.RestartDevicePayload;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;

/**
 * PLACEHOLDER TO IMPLEMENT
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 */
public class CommissioningCluster extends ZCLClusterBase implements Commissioning {

    public CommissioningCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        // TODO Auto-generated constructor stub
    }

    public Attribute getAttributeShortAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeExtendedPanID() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributePanID() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeChannelMask() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeProtocolVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeStackProfile() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeStartupControl() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeTrustCenterAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeTrustCenterMasterKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeNetworkKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeUseInsecureJoin() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributePreconfiguredLinkKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeNetworkKeySeqNum() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeNetworkKeyType() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeNetworkManagerAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeScanAttempts() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeTimeBetweenScans() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeRejoinInterval() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeMaxRejoinInterval() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeIndirectPollRate() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeParentRetryThreshold() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeConcentratorFlag() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeConcentratorRadius() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttributeConcentratorDiscoveryTime() {
        // TODO Auto-generated method stub
        return null;
    }

    public Response restartDevice(RestartDevicePayload payload)
            throws ZigBeeClusterException {
        // TODO Auto-generated method stub
        return null;
    }

    public Response saveStartupParameters(int index)
            throws ZigBeeClusterException {
        // TODO Auto-generated method stub
        return null;
    }

    public Response restoreStartupParameters(int index)
            throws ZigBeeClusterException {
        // TODO Auto-generated method stub
        return null;
    }

    public Response resetStartupParameters(ResetStartupPayload payload)
            throws ZigBeeClusterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public short getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        // TODO Auto-generated method stub
        return null;
    }


}