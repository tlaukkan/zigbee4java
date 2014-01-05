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
package org.bubblecloud.zigbee.api.cluster.impl.security_safety;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASACE;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace.BypassPayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace.ZoneTable;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.ArmCommand;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.ArmResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.BypassCommand;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.EmergencyCommand;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.FireCommand;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.PanicCommand;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.ZoneIDMapCommand;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.ZoneIDMapResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.ZoneInformationCommand;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.ZoneInformationResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_ace.ZoneTableImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 */
public class IASACECluster extends ZCLClusterBase implements IASACE {

    private ZoneTable zoneTable;
    private final Attribute[] attributes;

    private final Logger log = LoggerFactory.getLogger(IASACECluster.class);

    public IASACECluster(ZigBeeEndpoint zbDevice) {

        super(zbDevice);
        zoneTable = new ZoneTableImpl();
        attributes = new AttributeImpl[]{};
    }

    public Response arm(byte armMode) throws ZigBeeClusterException {
        ArmCommand cmd = new ArmCommand(armMode);
        Response response = invoke(cmd, false);
        return new ArmResponseImpl(response);
    }

    public void bypass(BypassPayload payload) throws ZigBeeClusterException {
        BypassCommand cmd = new BypassCommand(payload);
        invoke(cmd);
    }

    public void emergency() throws ZigBeeClusterException {
        EmergencyCommand cmd = new EmergencyCommand();
        invoke(cmd);
    }

    public void fire() throws ZigBeeClusterException {
        FireCommand cmd = new FireCommand();
        invoke(cmd);
    }

    public void panic() throws ZigBeeClusterException {
        PanicCommand cmd = new PanicCommand();
        invoke(cmd);
    }

    public Response getZoneIdMap() throws ZigBeeClusterException {
        ZoneIDMapCommand cmd = new ZoneIDMapCommand();
        Response response = invoke(cmd);
        return new ZoneIDMapResponseImpl(response);
    }

    public Response getZoneInformation(int zoneID) throws ZigBeeClusterException {
        ZoneInformationCommand cmd = new ZoneInformationCommand(zoneID);
        Response response = invoke(cmd);
        return new ZoneInformationResponseImpl(response);
    }

    @Override
    public short getId() {
        return IASACE.ID;
    }

    @Override
    public String getName() {
        return IASACE.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }
}