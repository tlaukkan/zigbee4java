/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
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
import org.bubblecloud.zigbee.api.cluster.general.DoorLock;
import org.bubblecloud.zigbee.api.cluster.general.Scenes;
import org.bubblecloud.zigbee.api.device.security_safety.IDoorLock;
import org.bubblecloud.zigbee.api.DeviceBase;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class DoorLockDevice extends DeviceBase implements IDoorLock {

    private DoorLock doorLock;
    private Scenes scenes;
    private Groups groups;
    //private OccupancySensing occupancySensing;

    public DoorLockDevice(ZigBeeApiContext ctx, ZigBeeEndpoint zbDevice) throws ZigBeeDeviceException {
        super(ctx, zbDevice);
        //onOff = (OnOff) getCluster(ZigBeeApiConstants.CLUSTER_ID_ON_OFF);
        doorLock = (DoorLock) getCluster(ZigBeeApiConstants.CLUSTER_ID_DOOR_LOCK);
        groups = (Groups) getCluster(ZigBeeApiConstants.CLUSTER_ID_GROUPS);
        scenes = (Scenes) getCluster(ZigBeeApiConstants.CLUSTER_ID_SCENES);
        //occupancySensing = (OccupancySensing) getCluster(ZigBeeApiConstants.CLUSTER_ID_OCCUPANCY_SENSING);
    }

    final static DeviceDescription DEVICE_DESCRIPTOR = new AbstractDeviceDescription() {

        public int[] getCustomClusters() {
            return IDoorLock.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return IDoorLock.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return IDoorLock.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return IDoorLock.STANDARD;
        }

    };


    public DeviceDescription getDescription() {
        return DEVICE_DESCRIPTOR;
    }

    @Override
    public String getDeviceType() {
        return IDoorLock.NAME;
    }

    public Groups getGroups() {
        return groups;
    }


    public Scenes getScenes() {
        return scenes;
    }

}
