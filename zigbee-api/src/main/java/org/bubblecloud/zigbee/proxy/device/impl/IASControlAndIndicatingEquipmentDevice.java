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

package org.bubblecloud.zigbee.proxy.device.impl;

import org.bubblecloud.zigbee.core.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Groups;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Identify;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Scenes;
import org.bubblecloud.zigbee.proxy.cluster.glue.security_safety.IASACE;
import org.bubblecloud.zigbee.proxy.cluster.glue.security_safety.IASWD;
import org.bubblecloud.zigbee.proxy.cluster.glue.security_safety.IASZone;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IASControlAndIndicatingEquipment;
import org.bubblecloud.zigbee.proxy.core.HADeviceBase;
import org.bubblecloud.zigbee.proxy.core.HAProfile;
import org.bubblecloud.zigbee.proxy.core.ZigBeeHAException;
import org.bubblecloud.zigbee.proxy.core.reflection.AbstractDeviceDescription;
import org.bubblecloud.zigbee.proxy.core.reflection.DeviceDescription;
import org.bubblecloud.zigbee.BundleContext;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 *
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public class IASControlAndIndicatingEquipmentDevice extends HADeviceBase implements IASControlAndIndicatingEquipment {

    private Identify identify;
    private IASZone iasZone;
    private IASACE iasAce;
    private IASWD iasWD;
    private Scenes scenes;
    private Groups groups;

    public IASControlAndIndicatingEquipmentDevice(BundleContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {

        super(ctx, zbDevice);

        iasAce = (IASACE) getCluster(HAProfile.IAS_ACE);
        iasZone = (IASZone) getCluster(HAProfile.IAS_ZONE);
        identify = (Identify) getCluster(HAProfile.IDENTIFY);
        iasWD = (IASWD) getCluster(HAProfile.IAS_WD);
        scenes = (Scenes) getCluster(HAProfile.SCENES);
        groups = (Groups) getCluster(HAProfile.GROUPS);
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
    public String getName() {
        return IASControlAndIndicatingEquipment.NAME;
    }

    @Override
    public DeviceDescription getDescription() {

        return DEVICE_DESCRIPTOR;
    }

    final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

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