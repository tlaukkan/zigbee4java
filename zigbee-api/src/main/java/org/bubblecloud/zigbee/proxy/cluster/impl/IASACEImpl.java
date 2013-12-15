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

package org.bubblecloud.zigbee.proxy.cluster.impl;

import org.bubblecloud.zigbee.core.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.cluster.glue.security_safety.IASACE;
import org.bubblecloud.zigbee.proxy.ZigBeeHAException;
import org.bubblecloud.zigbee.proxy.cluster.api.core.Attribute;
import org.bubblecloud.zigbee.proxy.cluster.api.core.Response;
import org.bubblecloud.zigbee.proxy.cluster.api.core.Subscription;
import org.bubblecloud.zigbee.proxy.cluster.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.proxy.cluster.api.security_safety.ias_ace.BypassPayload;
import org.bubblecloud.zigbee.proxy.cluster.api.security_safety.ias_ace.ZoneIDMapResponse;
import org.bubblecloud.zigbee.proxy.cluster.api.security_safety.ias_ace.ZoneInformationResponse;
import org.bubblecloud.zigbee.proxy.cluster.impl.security_safety.IASACECluster;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 *
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public class IASACEImpl implements IASACE {

    private final IASACECluster cluster;

    public IASACEImpl(ZigBeeDevice zbDevice){
        cluster = new IASACECluster(zbDevice);
    }

    public int getId() {
        return cluster.getId();
    }

    public String getName() {
        return cluster.getName();
    }

    public Subscription[] getActiveSubscriptions() {
        return cluster.getActiveSubscriptions();
    }

    public Attribute[] getAttributes() {
        return cluster.getAvailableAttributes();
    }

    public Attribute getAttribute(int id) {
        Attribute[] attributes = cluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if( attributes[i].getId() == id )
                return attributes[i];
        }
        return null;
    }

    public Response arm(byte armMode) throws ZigBeeHAException {
        try {
            Response response = (Response) cluster.arm(armMode);
            return response;
        }
        catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public void bypass(BypassPayload payload)	throws ZigBeeHAException {
        try {
            cluster.bypass(payload);
        }
        catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public void emergency() throws ZigBeeHAException {
        try {
            cluster.emergency();
        }
        catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public void fire() throws ZigBeeHAException {
        try {
            cluster.fire();
        }
        catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public void panic() throws ZigBeeHAException {
        try {
            cluster.panic();
        }
        catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public ZoneIDMapResponse getZoneIdMap() throws ZigBeeHAException {
        try {
            ZoneIDMapResponse response = (ZoneIDMapResponse) cluster.getZoneIdMap();
            return response;
        }
        catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public ZoneInformationResponse getZoneInformation(int zoneID) throws ZigBeeHAException {
        try {
            ZoneInformationResponse response = (ZoneInformationResponse) cluster.getZoneInformation(zoneID);
            return response;
        }
        catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }
}