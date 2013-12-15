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
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.IlluminanceMeasurement;
import it.cnr.isti.zigbee.ha.device.api.lighting.LightSensor;
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
public class LightSensorDevice extends HADeviceBase implements LightSensor {

    private IlluminanceMeasurement illuminanceMeasurement;
    private Groups groups;

    public LightSensorDevice(BundleContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {

        super(ctx, zbDevice);

        illuminanceMeasurement = (IlluminanceMeasurement) addCluster(HAProfile.ILLUMINANCE_MEASUREMENT);
        groups = (Groups) addCluster(HAProfile.GROUPS);
    }

    public IlluminanceMeasurement getIlluminanceMeasurement() {

        return illuminanceMeasurement;
    }

    @Override
    public String getName() {

        return LightSensor.NAME;
    }

    @Override
    public DeviceDescription getDescription() {

        return DEVICE_DESCRIPTOR;
    }

    final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

        public int[] getCustomClusters() {
            return LightSensor.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return LightSensor.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return LightSensor.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return LightSensor.STANDARD;
        }

    };

    public Groups getGroups() {
        return groups;
    }
}