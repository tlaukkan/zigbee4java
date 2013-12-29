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
package org.bubblecloud.zigbee.api.cluster.impl.api.security_safety;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLCluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace.BypassPayload;

/**
 * @author <a href="mailto:manlio.baco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision: 42 $ ($LastChangedDate: 2010-09-23 14:21:48 +0200 (Thu, 23 Sep 2010) $)
 * @since 0.8.0
 */
public interface IASACE extends ZCLCluster {

    public static final short ID = 0x0501;
    static final String NAME = "IAS ACE";
    static final String DESCRIPTION = "Attributes and commands for any Ancillary Control Equipment device.";

    static final byte ARM = 0x00;
    static final byte BYPASS = 0x01;
    static final byte EMERGENCY = 0x02;
    static final byte FIRE = 0x03;
    static final byte PANIC = 0x04;
    static final byte GET_ZONE_ID_MAP = 0x05;
    static final byte GET_ZONE_INFORMATION = 0x06;

    public Response arm(byte armMode) throws ZigBeeClusterException;

    public void bypass(BypassPayload payload) throws ZigBeeClusterException;

    public void emergency() throws ZigBeeClusterException;

    public void fire() throws ZigBeeClusterException;

    public void panic() throws ZigBeeClusterException;

    public Response getZoneIdMap() throws ZigBeeClusterException;

    public Response getZoneInformation(int zoneID) throws ZigBeeClusterException;
}