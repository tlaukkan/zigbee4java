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
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.*;
import org.bubblecloud.zigbee.api.cluster.general.Groups;
import org.bubblecloud.zigbee.api.cluster.general.LevelControl;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.api.cluster.general.Scenes;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.TemperatureMeasurement;
import org.bubblecloud.zigbee.api.device.hvac.Pump;
import org.bubblecloud.zigbee.ZigBeeApiContext;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 */
public class PumpDevice extends DeviceBase implements Pump {

    private OnOff onOffCluster;
    private Scenes scenesCluster;
    private Groups groupsCluster;
    private LevelControl levelControlCluster;
    private TemperatureMeasurement temperatureMeasurementCluster;

    public PumpDevice(ZigBeeApiContext ctx, ZigBeeEndpoint zbDevice) throws ZigBeeDeviceException {
        super(ctx, zbDevice);

        levelControlCluster = (LevelControl) getCluster(ZigBeeApiConstants.CLUSTER_ID_LEVEL_CONTROL);
        onOffCluster = (OnOff) getCluster(ZigBeeApiConstants.CLUSTER_ID_ON_OFF);
        scenesCluster = (Scenes) getCluster(ZigBeeApiConstants.CLUSTER_ID_SCENES);
        groupsCluster = (Groups) getCluster(ZigBeeApiConstants.CLUSTER_ID_GROUPS);
        temperatureMeasurementCluster = (TemperatureMeasurement) getCluster(ZigBeeApiConstants.CLUSTER_ID_TEMPERATURE_MEASUREMENT);
    }

    final static DeviceDescription DEVICE_DESCRIPTOR = new AbstractDeviceDescription() {

        public int[] getCustomClusters() {
            return Pump.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return Pump.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return Pump.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return Pump.STANDARD;
        }
    };

    @Override
    public DeviceDescription getDescription() {
        return DEVICE_DESCRIPTOR;
    }

    @Override
    public String getDeviceType() {
        return Pump.NAME;
    }

    public OnOff getOnOff() {
        return onOffCluster;
    }

    public Scenes getScenes() {
        return scenesCluster;
    }

    public Groups getGroups() {
        return groupsCluster;
    }

    public LevelControl getLevelControl() {
        return levelControlCluster;
    }

    public TemperatureMeasurement getTemperatureMeasurement() {
        return temperatureMeasurementCluster;
    }
}
