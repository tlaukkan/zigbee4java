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
import org.bubblecloud.zigbee.api.cluster.general.LevelControl;
import org.bubblecloud.zigbee.api.cluster.general.Scenes;
import org.bubblecloud.zigbee.api.cluster.general.Thermostat;
import org.bubblecloud.zigbee.api.cluster.general.FanControl;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.TemperatureMeasurement;
import org.bubblecloud.zigbee.api.device.hvac.ThermostatControl;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:ryan@presslab.us">Ryan Press</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2015-02-26 19:00:05 +0300 (Thu, 26 Feb 2015) $)
 * @since 0.6.0
 */
public class ThermostatControlDevice extends DeviceBase implements ThermostatControl {

    private Scenes scenesCluster;
    private Groups groupsCluster;
    private Thermostat thermostatCluster;
    private FanControl fanControlCluster;

    private TemperatureMeasurement temperatureMeasurementCluster;
    
    public ThermostatControlDevice(ZigBeeApiContext ctx, ZigBeeEndpoint zbDevice) throws ZigBeeDeviceException {
        super(ctx, zbDevice);
      
        scenesCluster = (Scenes) getCluster(ZigBeeApiConstants.CLUSTER_ID_SCENES);
        groupsCluster = (Groups) getCluster(ZigBeeApiConstants.CLUSTER_ID_GROUPS);
        thermostatCluster = (Thermostat) getCluster(ZigBeeApiConstants.CLUSTER_ID_THERMOSTAT);
        temperatureMeasurementCluster = (TemperatureMeasurement) getCluster(ZigBeeApiConstants.CLUSTER_ID_TEMPERATURE_MEASUREMENT);
    }

    public String getDeviceType() {
        return ThermostatControl.NAME;
    }

    final static DeviceDescription DEVICE_DESCRIPTOR = new AbstractDeviceDescription() {

        public int[] getCustomClusters() {
            return ThermostatControl.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return ThermostatControl.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return ThermostatControl.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return ThermostatControl.STANDARD;
        }

    };

    public DeviceDescription getDescription() {
        return DEVICE_DESCRIPTOR;
    }

    public Thermostat getThermostat() {
    	return thermostatCluster;
    }
}
