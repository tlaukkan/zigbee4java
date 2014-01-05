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

package org.bubblecloud.zigbee.api;

import org.bubblecloud.zigbee.api.cluster.impl.api.HVAC.FanControl;
import org.bubblecloud.zigbee.api.cluster.impl.api.HVAC.PumpConfigurationAndControl;
import org.bubblecloud.zigbee.api.cluster.impl.api.HVAC.Thermostat;
import org.bubblecloud.zigbee.api.cluster.impl.api.HVAC.ThermostatUserInterfaceConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.closures.DoorLock;
import org.bubblecloud.zigbee.api.cluster.impl.api.closures.ShadeConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.closures.WindowCovering;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.*;
import org.bubblecloud.zigbee.api.cluster.impl.api.measureament_sensing.*;
import org.bubblecloud.zigbee.api.cluster.impl.api.smart_energy.Metering;
import org.bubblecloud.zigbee.api.device.generic.LevelControlSwitch;
import org.bubblecloud.zigbee.api.device.generic.MainsPowerOutlet;
import org.bubblecloud.zigbee.api.device.generic.OnOffOutput;
import org.bubblecloud.zigbee.api.device.generic.SimpleSensor;
import org.bubblecloud.zigbee.api.device.generic.OnOffSwitch;
import org.bubblecloud.zigbee.api.device.hvac.Pump;
import org.bubblecloud.zigbee.api.device.hvac.TemperatureSensor;
import org.bubblecloud.zigbee.api.device.lighting.DimmableLight;
import org.bubblecloud.zigbee.api.device.lighting.LightSensor;
import org.bubblecloud.zigbee.api.device.lighting.OccupancySensor;
import org.bubblecloud.zigbee.api.device.lighting.OnOffLight;
import org.bubblecloud.zigbee.api.device.lighting.OnOffLightSwitch;
import org.bubblecloud.zigbee.api.device.security_safety.IASControlAndIndicatingEquipment;
import org.bubblecloud.zigbee.api.device.security_safety.IAS_Warning;
import org.bubblecloud.zigbee.api.device.security_safety.IAS_Zone;
import org.bubblecloud.zigbee.api.cluster.impl.api.lighting.ColorControl;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASACE;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASWD;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASZone;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 */
public class ZigBeeApiConstants {

    public static final int PROFILE_ID_HOME_AUTOMATION = 0x0104;

    //CLUSTERS
    //Generic
    public static final int CLUSTER_ID_BASIC = Basic.ID;
    public static final int CLUSTER_ID_POWER_CONFIGURATION = PowerConfiguration.ID;
    public static final int CLUSTER_ID_DEVICE_TEMPERATURE_CONFIGURATION = DeviceTemperatureConfiguration.ID;
    public static final int CLUSTER_ID_IDENTIFY = Identify.ID;
    public static final int CLUSTER_ID_GROUPS = Groups.ID;
    public static final int CLUSTER_ID_SCENES = Scenes.ID;
    public static final int CLUSTER_ID_ON_OFF = OnOff.ID;
    public static final int CLUSTER_ID_ON_OFF_SWITCH_CONFIGURATION = OnOffSwitchConfiguration.ID;
    public static final int CLUSTER_ID_LEVEL_CONTROL = LevelControl.ID;
    public static final int CLUSTER_ID_ALARMS = Alarms.ID;
    public static final int CLUSTER_ID_BINARY_INPUT = BinaryInput.ID;
    //fuori specifica
    public static final int CLUSTER_ID_TIME = Time.ID;
    public static final int CLUSTER_ID_ANALOG_INPUT = AnalogInput.ID;
    public static final int CLUSTER_ID_COMMISSIONING = Commissioning.ID;
    //Smart Energy	
    public static final int CLUSTER_ID_METERING = Metering.ID;
    //Closures
    public static final int CLUSTER_ID_SHADE_CONFIGURATION = ShadeConfiguration.ID;
    public static final int CLUSTER_ID_DOOR_LOCK = DoorLock.ID;
    public static final int CLUSTER_ID_WINDOW_COVERING = WindowCovering.ID;
    //HVAC
    public static final int CLUSTER_ID_PUMP_CONFIGURATION_AND_CONTROL = PumpConfigurationAndControl.ID;
    public static final int CLUSTER_ID_THERMOSTAT = Thermostat.ID;
    public static final int CLUSTER_ID_FAN_CONTROL = FanControl.ID;
    public static final int CLUSTER_ID_THERMOSTAT_USER_INTERFACE_CONFIGURATION = ThermostatUserInterfaceConfiguration.ID;
    // Lighting
    public static final int CLUSTER_ID_COLOR_CONTROL = ColorControl.ID;
    //Measureament & Sensing
    public static final int CLUSTER_ID_PRESSURE_MEASUREMENT =   PressureMeasurement.ID;
    public static final int CLUSTER_ID_ILLUMINANCE_MEASUREMENT = IlluminanceMeasurement.ID;
    public static final int CLUSTER_ID_ILLUMINANCE_LEVEL_SENSING = IlluminanceLevelSensing.ID;
    public static final int CLUSTER_ID_TEMPERATURE_MEASUREMENT = TemperatureMeasurement.ID;
    public static final int CLUSTER_ID_FLOW_MEASUREMENT = FlowMeasurement.ID;
    public static final int CLUSTER_ID_RELATIVE_HUMIDITY_MEASUREMENT = RelativeHumidityMeasurement.ID;
    public static final int CLUSTER_ID_OCCUPANCY_SENSING = OccupancySensing.ID;
    // Security & Safety
    public static final int CLUSTER_ID_IAS_ZONE = IASZone.ID;
    public static final int CLUSTER_ID_IAS_ACE = IASACE.ID;
    public static final int CLUSTER_ID_IAS_WD = IASWD.ID;

    // devices
    // GENERIC 0x0000 - 0x00FF
    public static final int DEVICE_ID_ON_OFF_SWITCH = OnOffSwitch.DEVICE_ID;
    public static final int DEVICE_ID_LEVEL_CONTROL = LevelControlSwitch.DEVICE_ID;
    public static final int DEVICE_ID_ON_OFF_OUTPUT = OnOffOutput.DEVICE_ID;
    public static final int DEVICE_ID_MAINS_POWER_OUTLET = MainsPowerOutlet.DEVICE_ID;
    public static final int DEVICE_ID_SIMPLE_SENSOR = SimpleSensor.DEVICE_ID;
    // LIGHTNING 0x0100 - 0x1FF
    public static final int DEVICE_ID_ON_OFF_LIGHT = OnOffLight.DEVICE_ID;
    public static final int DEVICE_ID_DIMMABLE_LIGHT = DimmableLight.DEVICE_ID;
    public static final int DEVICE_ID_ON_OFF_LIGHT_SWITCH = OnOffLightSwitch.DEVICE_ID;
    public static final int DEVICE_ID_LIGHT_SENSOR = LightSensor.DEVICE_ID;
    public static final int DEVICE_ID_OCCUPANCY_SENSOR = OccupancySensor.DEVICE_ID;
    // HVAC - 0x0300 - 0x3FF
    public static final int DEVICE_ID_TEMPERATURE_SENSOR = TemperatureSensor.DEVICE_ID;
    public static final int DEVICE_ID_PUMP = Pump.DEVICE_ID;
    // Intruder Alarm Systems 0x0400 - 0xFFFF
    public static final int DEVICE_ID_IAS_CONTROL_INDICATING_EQUIPMENT = IASControlAndIndicatingEquipment.DEVICE_ID;
    public static final int DEVICE_ID_IASZONE = IAS_Zone.DEVICE_ID;
    public static final int DEVICE_ID_IAS_WARNING_DEVICE = IAS_Warning.DEVICE_ID;

    public static String getDeviceName(int deviceID){

        try{

            String id = Integer.toHexString(deviceID);

            if(id.equals(Integer.toHexString(DEVICE_ID_ON_OFF_SWITCH)))
                return OnOffSwitch.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_LEVEL_CONTROL)))
                return LevelControlSwitch.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_ON_OFF_OUTPUT)))
                return OnOffOutput.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_MAINS_POWER_OUTLET)))
                return MainsPowerOutlet.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_SIMPLE_SENSOR)))
                return SimpleSensor.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_ON_OFF_LIGHT)))
                return OnOffLight.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_DIMMABLE_LIGHT)))
                return DimmableLight.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_ON_OFF_LIGHT_SWITCH)))
                return OnOffLightSwitch.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_LIGHT_SENSOR)))
                return LightSensor.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_OCCUPANCY_SENSOR)))
                return OccupancySensor.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_TEMPERATURE_SENSOR)))
                return TemperatureSensor.NAME;
            else if(id.equals(Integer.toHexString(DEVICE_ID_IAS_CONTROL_INDICATING_EQUIPMENT)))
                return IASControlAndIndicatingEquipment.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_PRESSURE_MEASUREMENT)))
                return PressureMeasurement.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_FLOW_MEASUREMENT)))
                return FlowMeasurement.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_ILLUMINANCE_LEVEL_SENSING)))
                return IlluminanceLevelSensing.NAME;



            else return null;
        }
        catch(Exception ex){
            return null;
        }
    }

    public static String getClusterName(int clusterID){

        try{
            String id = Integer.toHexString(clusterID);

            if(id.equals(Integer.toHexString(CLUSTER_ID_BASIC)))
                return Basic.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_POWER_CONFIGURATION)))
                return PowerConfiguration.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_DEVICE_TEMPERATURE_CONFIGURATION)))
                return DeviceTemperatureConfiguration.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_IDENTIFY)))
                return Identify.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_GROUPS)))
                return Groups.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_SCENES)))
                return Scenes.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_ON_OFF)))
                return OnOff.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_ON_OFF_SWITCH_CONFIGURATION)))
                return OnOffSwitchConfiguration.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_LEVEL_CONTROL)))
                return LevelControl.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_ALARMS)))
                return Alarms.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_TIME)))
                return Time.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_BINARY_INPUT)))
                return BinaryInput.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_COMMISSIONING)))
                return Commissioning.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_COLOR_CONTROL)))
                return ColorControl.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_ILLUMINANCE_MEASUREMENT)))
                return IlluminanceMeasurement.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_TEMPERATURE_MEASUREMENT)))
                return TemperatureMeasurement.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_RELATIVE_HUMIDITY_MEASUREMENT)))
                return RelativeHumidityMeasurement.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_OCCUPANCY_SENSING)))
                return OccupancySensing.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_IAS_ZONE)))
                return IASZone.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_IAS_ACE)))
                return IASACE.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_IAS_WD)))
                return IASWD.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_ANALOG_INPUT)))
                return AnalogInput.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_METERING)))
                return Metering.NAME;

            else return null;
        }
        catch(Exception ex){
            return null;
        }
    }

    public static String getCategoryDeviceName(int deviceID){

        try{
            String id = Integer.toHexString(deviceID);
            if(id.startsWith("0"))
                return "Generic";
            else if(id.startsWith("1"))
                return "Lighting";
            else if(id.startsWith("2"))
                return "Closures";
            else if(id.startsWith("3"))
                return "HVAC";
            else if(id.startsWith("4"))
                return "IAS";
            else
                return null;
        }
        catch(Exception ex){
            return null;
        }
    }
}