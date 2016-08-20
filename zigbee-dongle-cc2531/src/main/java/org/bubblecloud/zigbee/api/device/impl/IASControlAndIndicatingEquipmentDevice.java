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

package org.bubblecloud.zigbee.api.device.impl;

import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.ZigBeeApiContext;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.*;
import org.bubblecloud.zigbee.api.cluster.general.Groups;
import org.bubblecloud.zigbee.api.cluster.general.Identify;
import org.bubblecloud.zigbee.api.cluster.general.Scenes;
import org.bubblecloud.zigbee.api.cluster.security_safety.IASACE;
import org.bubblecloud.zigbee.api.cluster.security_safety.IASWD;
import org.bubblecloud.zigbee.api.cluster.security_safety.IASZone;
import org.bubblecloud.zigbee.api.device.security_safety.IASControlAndIndicatingEquipment;
import org.bubblecloud.zigbee.api.DeviceBase;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 */
public class IASControlAndIndicatingEquipmentDevice extends DeviceBase implements IASControlAndIndicatingEquipment {

    private Identify identify;
    private IASZone iasZone;
    private IASACE iasAce;
    private IASWD iasWD;
    private Scenes scenes;
    private Groups groups;

    public IASControlAndIndicatingEquipmentDevice(ZigBeeApiContext ctx, ZigBeeEndpoint zbDevice) throws ZigBeeDeviceException {

        super(ctx, zbDevice);

        iasAce = (IASACE) getCluster(ZigBeeApiConstants.CLUSTER_ID_IAS_ACE);
        iasZone = (IASZone) getCluster(ZigBeeApiConstants.CLUSTER_ID_IAS_ZONE);
        identify = (Identify) getCluster(ZigBeeApiConstants.CLUSTER_ID_IDENTIFY);
        iasWD = (IASWD) getCluster(ZigBeeApiConstants.CLUSTER_ID_IAS_WD);
        scenes = (Scenes) getCluster(ZigBeeApiConstants.CLUSTER_ID_SCENES);
        groups = (Groups) getCluster(ZigBeeApiConstants.CLUSTER_ID_GROUPS);
    }

    public IASACE getIASACE() {
        return iasAce;
    }

    public IASZone getIASZone() {
        return iasZone;
    }

    public IASWD getIASwd() {
        return iasWD;
    }

    public Scenes getScenes() {
        return scenes;
    }

    public Groups getGroups() {
        return groups;
    }

    public Identify getIdentify() {
        return identify;
    }

    @Override
    public String getDeviceType() {
        return IASControlAndIndicatingEquipment.NAME;
    }

    @Override
    public DeviceDescription getDescription() {

        return DEVICE_DESCRIPTOR;
    }

    final static DeviceDescription DEVICE_DESCRIPTOR = new AbstractDeviceDescription() {

        public int[] getCustomClusters() {
            return IASControlAndIndicatingEquipment.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return IASControlAndIndicatingEquipment.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return IASControlAndIndicatingEquipment.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return IASControlAndIndicatingEquipment.STANDARD;
        }
    };
}