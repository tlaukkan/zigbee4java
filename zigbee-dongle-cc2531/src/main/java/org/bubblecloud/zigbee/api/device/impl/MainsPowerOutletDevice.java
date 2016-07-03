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
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.api.cluster.general.Scenes;
import org.bubblecloud.zigbee.api.device.generic.MainsPowerOutlet;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 */
public class MainsPowerOutletDevice extends DeviceBase implements MainsPowerOutlet {

    private OnOff onOff;
    private Scenes scenes;
    private Groups groups;

    public MainsPowerOutletDevice(ZigBeeApiContext ctx, ZigBeeEndpoint zbDevice) throws ZigBeeDeviceException {

        super(ctx, zbDevice);
        onOff = (OnOff) getCluster(ZigBeeApiConstants.CLUSTER_ID_ON_OFF);
        groups = (Groups) getCluster(ZigBeeApiConstants.CLUSTER_ID_GROUPS);
        scenes = (Scenes) getCluster(ZigBeeApiConstants.CLUSTER_ID_SCENES);
    }

    public OnOff getOnOff() {

        return onOff;
    }

    public Scenes getScenes() {

        return scenes;
    }

    public Groups getGroups() {

        return groups;
    }

    @Override
    public String getDeviceType() {

        return MainsPowerOutlet.NAME;
    }

    @Override
    public DeviceDescription getDescription() {

        return DEVICE_DESCRIPTOR;
    }

    final static DeviceDescription DEVICE_DESCRIPTOR = new AbstractDeviceDescription() {

        public int[] getCustomClusters() {
            return MainsPowerOutlet.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return MainsPowerOutlet.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return MainsPowerOutlet.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return MainsPowerOutlet.STANDARD;
        }

    };
}