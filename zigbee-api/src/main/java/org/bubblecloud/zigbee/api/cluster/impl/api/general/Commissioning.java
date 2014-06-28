/*
   Copyright 2012-2012 CNR-ISTI, http://isti.cnr.it
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
package org.bubblecloud.zigbee.api.cluster.impl.api.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLCluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.commissioning.ResetStartupPayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.commissioning.RestartDevicePayload;

/**
 * This class represent the <b>Commissioning</b> Cluster as defined by the document:<br>
 * <i>ZigBee Cluster Library</i> public release version 075366r01
 *
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 42 $ ($LastChangedDate: 2010-09-23 14:21:48 +0200 (gio, 23 set 2010) $)
 * @since 0.8.0
 */
public interface Commissioning extends ZCLCluster {

    static final short ID = 0x0015;
    static final String NAME = "Commissioning";
    static final String DESCRIPTION = "Attributes and commands pertaining to the commissioning and api of ZigBee devices operating in a network.";

    static final byte RESTART_DEVICE_ID = 0x00;
    static final byte SAVE_STARTUP_PARAMETERS_ID = 0x01;
    static final byte RESTORE_STARTUP_PARAMETERS_ID = 0x02;
    static final byte RESET_STARTUP_PARAMETERS_ID = 0x03;

    // Startup Parameters
    public Attribute getAttributeShortAddress();

    public Attribute getAttributeExtendedPanID();

    public Attribute getAttributePanID();

    public Attribute getAttributeChannelMask();

    public Attribute getAttributeProtocolVersion();

    public Attribute getAttributeStackProfile();

    public Attribute getAttributeStartupControl();

    public Attribute getAttributeTrustCenterAddress();

    public Attribute getAttributeTrustCenterMasterKey();

    public Attribute getAttributeNetworkKey();

    public Attribute getAttributeUseInsecureJoin();

    public Attribute getAttributePreconfiguredLinkKey();

    public Attribute getAttributeNetworkKeySeqNum();

    public Attribute getAttributeNetworkKeyType();

    public Attribute getAttributeNetworkManagerAddress();

    // Join Parameters
    public Attribute getAttributeScanAttempts();

    public Attribute getAttributeTimeBetweenScans();

    public Attribute getAttributeRejoinInterval();

    public Attribute getAttributeMaxRejoinInterval();

    // End Device Parameters
    public Attribute getAttributeIndirectPollRate();

    public Attribute getAttributeParentRetryThreshold();

    // Concentrator Parameters
    public Attribute getAttributeConcentratorFlag();

    public Attribute getAttributeConcentratorRadius();

    public Attribute getAttributeConcentratorDiscoveryTime();

    // Commands
    public Response restartDevice(RestartDevicePayload payload) throws ZigBeeClusterException;

    public Response saveStartupParameters(int index) throws ZigBeeClusterException;

    public Response restoreStartupParameters(int index) throws ZigBeeClusterException;

    public Response resetStartupParameters(ResetStartupPayload payload) throws ZigBeeClusterException;
}