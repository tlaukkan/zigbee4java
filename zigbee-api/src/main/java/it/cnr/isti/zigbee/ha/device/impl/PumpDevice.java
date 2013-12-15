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

package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.LevelControl;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOff;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.TemperatureMeasurement;
import it.cnr.isti.zigbee.ha.device.api.hvac.Pump;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;
import org.bubblecloud.zigbee.BundleContext;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 *
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public class PumpDevice extends HADeviceBase implements Pump {

    private OnOff onOffCluster;
    private Scenes scenesCluster;
    private Groups groupsCluster;
    private LevelControl levelControlCluster;
    private TemperatureMeasurement temperatureMeasurementCluster;

    public PumpDevice(BundleContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException{
        super(ctx, zbDevice);

        levelControlCluster = (LevelControl) getCluster(HAProfile.LEVEL_CONTROL);
        onOffCluster = (OnOff) getCluster(HAProfile.ON_OFF);
        scenesCluster = (Scenes) getCluster(HAProfile.SCENES);
        groupsCluster = (Groups) getCluster(HAProfile.GROUPS);
        temperatureMeasurementCluster = (TemperatureMeasurement) getCluster(HAProfile.TEMPERATURE_MEASUREMENT);
    }

    final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

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
    public String getName() {
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
