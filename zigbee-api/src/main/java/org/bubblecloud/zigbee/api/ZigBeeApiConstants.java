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

import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.HVAC.FanControl;
import org.bubblecloud.zigbee.api.cluster.impl.api.HVAC.PumpConfigurationAndControl;
import org.bubblecloud.zigbee.api.cluster.impl.api.HVAC.Thermostat;
import org.bubblecloud.zigbee.api.cluster.impl.api.HVAC.ThermostatUserInterfaceConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.closures.DoorLock;
import org.bubblecloud.zigbee.api.cluster.impl.api.closures.ShadeConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.closures.WindowCovering;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.*;
import org.bubblecloud.zigbee.api.cluster.impl.api.measurement_sensing.*;
import org.bubblecloud.zigbee.api.cluster.impl.api.smart_energy.Metering;
import org.bubblecloud.zigbee.api.device.generic.LevelControlSwitch;
import org.bubblecloud.zigbee.api.device.generic.MainsPowerOutlet;
import org.bubblecloud.zigbee.api.device.generic.OnOffOutput;
import org.bubblecloud.zigbee.api.device.generic.SimpleSensor;
import org.bubblecloud.zigbee.api.device.generic.OnOffSwitch;
import org.bubblecloud.zigbee.api.device.hvac.Pump;
import org.bubblecloud.zigbee.api.device.hvac.ThermostatControl;
import org.bubblecloud.zigbee.api.device.hvac.TemperatureSensor;
import org.bubblecloud.zigbee.api.device.lighting.ColorDimmableLight;
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
 * @author <a href="mailto:chris@cd-jackson.com>Chris Jackson</a>
 * @since 0.4.0
 */
public class ZigBeeApiConstants {

    /**
     * Cluster factory for reading cluster metadata.
     */
    private static final ClusterFactoryImpl clusterFactory = new ClusterFactoryImpl();

    /**
     * Broadcast address.
     */
    public static final byte BROADCAST_ADDRESS = 0x0F;
    /**
     * Unicast address.
     */
    public static final byte UNICAST_ADRESS = 0x02;
	/**
	 * Home Automation Profile definition
	 */
    public static final int PROFILE_ID_HOME_AUTOMATION = 0x0104;
    /**
     * ZigBee Light Link Profile definition
     */
    public static final int PROFILE_ID_LIGHT_LINK = 0xc05e;

    // CLUSTERS
    // Generic
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
    // Smart Energy	
    public static final int CLUSTER_ID_METERING = Metering.ID;
    // Closures
    public static final int CLUSTER_ID_SHADE_CONFIGURATION = ShadeConfiguration.ID;
    public static final int CLUSTER_ID_DOOR_LOCK = DoorLock.ID;
    public static final int CLUSTER_ID_WINDOW_COVERING = WindowCovering.ID;
    // HVAC
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
    public static final int CLUSTER_ID_ELECTRICAL_MEASUREMENT = ElectricalMeasurement.ID;
    // Security & Safety
    public static final int CLUSTER_ID_IAS_ZONE = IASZone.ID;
    public static final int CLUSTER_ID_IAS_ACE = IASACE.ID;
    public static final int CLUSTER_ID_IAS_WD = IASWD.ID;

    // Home Automation Profile devices
    // HA GENERIC 0x0000 - 0x00FF
    public static final int DEVICE_ID_HA_ON_OFF_SWITCH = OnOffSwitch.DEVICE_ID;
    public static final int DEVICE_ID_HA_LEVEL_CONTROL = LevelControlSwitch.DEVICE_ID;
    public static final int DEVICE_ID_HA_ON_OFF_OUTPUT = OnOffOutput.DEVICE_ID;
    public static final int DEVICE_ID_HA_SCENE_SELECTOR = 4;  // TODO
    public static final int DEVICE_ID_HA_CONFIGURATION_TOOL = 5;   // TODO
    public static final int DEVICE_ID_HA_REMOTE_CONTROL = 6;	// TODO
    public static final int DEVICE_ID_HA_RANGE_EXTENDER = 8;	// TODO
    public static final int DEVICE_ID_HA_MAINS_POWER_OUTLET = MainsPowerOutlet.DEVICE_ID;
    public static final int DEVICE_ID_HA_SIMPLE_SENSOR = SimpleSensor.DEVICE_ID;
    // HA LIGHTNING 0x0100 - 0x1FF
    public static final int DEVICE_ID_HA_ON_OFF_LIGHT = OnOffLight.DEVICE_ID;
    public static final int DEVICE_ID_HA_DIMMABLE_LIGHT = DimmableLight.DEVICE_ID;
    public static final int DEVICE_ID_HA_ON_OFF_LIGHT_SWITCH = OnOffLightSwitch.DEVICE_ID;
    public static final int DEVICE_ID_HA_LIGHT_SENSOR = LightSensor.DEVICE_ID;
    public static final int DEVICE_ID_HA_OCCUPANCY_SENSOR = OccupancySensor.DEVICE_ID;
    // HA HVAC - 0x0300 - 0x3FF
    public static final int DEVICE_ID_HA_THERMOSTAT_CONTROL = ThermostatControl.DEVICE_ID;
    public static final int DEVICE_ID_HA_TEMPERATURE_SENSOR = TemperatureSensor.DEVICE_ID;
    public static final int DEVICE_ID_HA_PUMP = Pump.DEVICE_ID;
    // HA Intruder Alarm Systems 0x0400 - 0x04FF
    public static final int DEVICE_ID_HA_IAS_CONTROL_INDICATING_EQUIPMENT = IASControlAndIndicatingEquipment.DEVICE_ID;
    public static final int DEVICE_ID_HA_IASZONE = IAS_Zone.DEVICE_ID;
    public static final int DEVICE_ID_HA_IAS_WARNING_DEVICE = IAS_Warning.DEVICE_ID;

    // ZLL Basic Lighting Device IDs
   	private static final int DEVICE_ID_ZLL_ON_OFF_LIGHT              = 0x0000;
   	private static final int DEVICE_ID_ZLL_ON_OFF_PLUG_IN_UNIT       = 0x0010;
    // ZLL Level Controlled Lighting Device IDs
   	private static final int DEVICE_ID_ZLL_DIMMABLE_LIGHT            = 0x0100;
   	private static final int DEVICE_ID_ZLL_DIMMABLE_PLUG_IN_UNIT     = 0x0110;
   	// ZLL Color Lighting Device IDs
   	private static final int DEVICE_ID_ZLL_COLOR_LIGHT                = 0x0200;
   	private static final int DEVICE_ID_ZLL_EXTENDED_COLOR_LIGHT       = 0x0210;
   	private static final int DEVICE_ID_ZLL_COLOR_TEMPERATURE_LIGHT    = 0x0220;
   	// ZLL Lighting Remotes Device IDs
   	private static final int DEVICE_ID_ZLL_COLOR_CONTROLLER           = 0x0800;
   	private static final int DEVICE_ID_ZLL_COLOR_SCENE_CONTROLLER     = 0x0810;
   	private static final int DEVICE_ID_ZLL_NON_COLOR_CONTROLLER       = 0x0820;
   	private static final int DEVICE_ID_ZLL_NON_COLOR_SCENE_CONTROLLER = 0x0830;
   	private static final int DEVICE_ID_ZLL_CONTROL_BRIDGE             = 0x0840;
   	private static final int DEVICE_ID_ZLL_ON_OFF_SENSOR              = 0x0850;

    /**
     * Private constructor to disable constructing utility class.
     */
    private ZigBeeApiConstants() {}


    public static String getDeviceName(int profileID, int deviceType, int deviceID) {
        if (deviceType == 0) {
            return "Coordinator";
        }
		switch (profileID) {
		case PROFILE_ID_HOME_AUTOMATION:
			if (deviceID == DEVICE_ID_HA_ON_OFF_SWITCH)
				return OnOffSwitch.NAME;
			else if (deviceID == DEVICE_ID_HA_LEVEL_CONTROL)
				return LevelControlSwitch.NAME;
			else if (deviceID == DEVICE_ID_HA_ON_OFF_OUTPUT)
				return OnOffOutput.NAME;
			else if (deviceID == DEVICE_ID_HA_MAINS_POWER_OUTLET)
				return MainsPowerOutlet.NAME;
			else if (deviceID == DEVICE_ID_HA_SIMPLE_SENSOR)
				return SimpleSensor.NAME;
			else if (deviceID == DEVICE_ID_HA_ON_OFF_LIGHT)
				return OnOffLight.NAME;
			else if (deviceID == DEVICE_ID_HA_DIMMABLE_LIGHT)
				return DimmableLight.NAME;
			else if (deviceID == DEVICE_ID_HA_ON_OFF_LIGHT_SWITCH)
				return OnOffLightSwitch.NAME;
			else if (deviceID == DEVICE_ID_HA_LIGHT_SENSOR)
				return LightSensor.NAME;
			else if (deviceID == DEVICE_ID_HA_OCCUPANCY_SENSOR)
				return OccupancySensor.NAME;
			else if (deviceID == DEVICE_ID_HA_THERMOSTAT_CONTROL)
				return ThermostatControl.NAME;
			else if (deviceID == DEVICE_ID_HA_TEMPERATURE_SENSOR)
				return TemperatureSensor.NAME;
			else if (deviceID == DEVICE_ID_HA_IAS_CONTROL_INDICATING_EQUIPMENT)
				return IASControlAndIndicatingEquipment.NAME;
            else if (deviceID == DEVICE_ID_HA_IASZONE)
                return IASZone.NAME;
			else if (deviceID == CLUSTER_ID_PRESSURE_MEASUREMENT)
				return PressureMeasurement.NAME;
			else if (deviceID == CLUSTER_ID_FLOW_MEASUREMENT)
				return FlowMeasurement.NAME;
			else if (deviceID == CLUSTER_ID_ILLUMINANCE_LEVEL_SENSING)
				return IlluminanceLevelSensing.NAME;
			break;
		case PROFILE_ID_LIGHT_LINK:
			if (deviceID == DEVICE_ID_ZLL_ON_OFF_LIGHT)
				return OnOffSwitch.NAME;
			else if (deviceID == DEVICE_ID_ZLL_DIMMABLE_LIGHT)
				return DimmableLight.NAME;
			else if (deviceID == DEVICE_ID_ZLL_COLOR_LIGHT)
				return ColorDimmableLight.NAME;
			else if (deviceID == DEVICE_ID_ZLL_EXTENDED_COLOR_LIGHT)
				return ColorDimmableLight.NAME;
			break;
		}
		
		return null;
    }

    public static String getClusterName(int clusterID) {
        try {
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
            else if(id.equals(Integer.toHexString(CLUSTER_ID_DOOR_LOCK)))
                return DoorLock.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_BINARY_INPUT)))
                return BinaryInput.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_COMMISSIONING)))
                return Commissioning.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_COLOR_CONTROL)))
                return ColorControl.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_ILLUMINANCE_MEASUREMENT)))
                return IlluminanceMeasurement.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_THERMOSTAT)))
                return Thermostat.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_THERMOSTAT_USER_INTERFACE_CONFIGURATION)))
                return ThermostatUserInterfaceConfiguration.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_FAN_CONTROL)))
                return FanControl.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_TEMPERATURE_MEASUREMENT)))
                return TemperatureMeasurement.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_RELATIVE_HUMIDITY_MEASUREMENT)))
                return RelativeHumidityMeasurement.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_OCCUPANCY_SENSING)))
                return OccupancySensing.NAME;
            else if(id.equals(Integer.toHexString(CLUSTER_ID_ELECTRICAL_MEASUREMENT)))
                return ElectricalMeasurement.NAME;
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

    public static String getCategoryDeviceName(int profileID, int deviceID) {
    	String id;
        try {
            id = Integer.toHexString(deviceID);
        }
        catch(Exception ex) {
            return null;
        }

    	switch(profileID) {
        	case PROFILE_ID_HOME_AUTOMATION:
	            if(id.startsWith("0"))
	                return "HA Generic";
	            else if(id.startsWith("1"))
	                return "HA Lighting";
	            else if(id.startsWith("2"))
	                return "HA Closures";
	            else if(id.startsWith("3"))
	                return "HA HVAC";
	            else if(id.startsWith("4"))
	                return "HA IAS";
	            break;
	            
        	case PROFILE_ID_LIGHT_LINK:
	            if(id.startsWith("0"))
	                return "ZLL OnOff Light";
	            else if(id.startsWith("1"))
	                return "ZLL Dimmable Light";
	            else if(id.startsWith("2"))
	                return "ZLL Color Light";
	            else if(id.startsWith("8"))
	                return "ZLL Remote Control";
	            break;
    	}
		return null;
    }

	public static String getProfileName(int profileId) {
    	switch(profileId) {
    	case PROFILE_ID_HOME_AUTOMATION:
    		return "ZigBee Home Automation";
    	case PROFILE_ID_LIGHT_LINK:
    		return "ZigBee Light Link";
    	}
    	return null;
	}

    /**
     * Gets cluster for reading metadata information.
     * @param profileId the profile ID
     * @param clusterId the cluster ID
     * @return
     */
    public static Cluster getCluster(final int profileId, final int clusterId) {
        Cluster cluster = clusterFactory.getInstance(profileId + ":" + clusterId, null);
        if (cluster == null) {
            // Attempt to get cluster from HA profile-
            cluster = clusterFactory.getInstance(ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":" + clusterId, null);
        }
        return cluster;
    }
}