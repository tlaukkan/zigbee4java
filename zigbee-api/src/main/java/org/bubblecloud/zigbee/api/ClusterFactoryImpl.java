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

import org.bubblecloud.zigbee.api.cluster.impl.*;
import org.bubblecloud.zigbee.ZigBeeApiContext;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 */
public class ClusterFactoryImpl extends ClusterFactoryBase {

    //General
    final static String BASIC_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":" + ZigBeeApiConstants.CLUSTER_ID_BASIC;
    final static String POWER_CONFIGURATION_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_POWER_CONFIGURATION;
    final static String DEVICE_TEMPERATURE_CONFIGURATION_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_DEVICE_TEMPERATURE_CONFIGURATION;
    final static String IDENTIFY_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_IDENTIFY;
    final static String GROUPS_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_GROUPS;
    final static String SCENES_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_SCENES;
    final static String ONOFF_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_ON_OFF;
    final static String ON_OFF_SWITCH_CONFIGURATION_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_ON_OFF_SWITCH_CONFIGURATION;
    final static String LEVEL_CONTROL_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_LEVEL_CONTROL;
    final static String ALARMS_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_ALARMS;
    final static String BINARY_INPUT_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_BINARY_INPUT;

    //Smart Energy
    final static String METERING_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_METERING;

    //Measurement and Sensing
    final static String ILLUMINANCE_LEVEL_SENSING_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_ILLUMINANCE_LEVEL_SENSING; // new
    final static String ILLUMINANCE_MEASUREMENT_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_ILLUMINANCE_MEASUREMENT; // new
    final static String TEMPERATURE_MEASUREMENT_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_TEMPERATURE_MEASUREMENT;
    final static String PRESSURE_MEASUREMENT_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_PRESSURE_MEASUREMENT;
    final static String FLOW_MEASUREMENT_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_FLOW_MEASUREMENT;
    final static String RELATIVE_HUMIDITY_MEASUREMENT_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_RELATIVE_HUMIDITY_MEASUREMENT;
    final static String OCCUPANCY_SENSING_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_OCCUPANCY_SENSING;
    //Security and Safety
    final static String IAS_ZONE_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_IAS_ZONE;
    final static String IAS_WD_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_IAS_WD;
    final static String IAS_ACE_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_IAS_ACE;
    //fuori profilo HA
    final static String TIME_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_TIME;
    final static String COMMISSIONING_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_COMMISSIONING;
    final static String ANALOG_INPUT_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_ANALOG_INPUT;

    //HVAC
    private static final String THERMOSTAT_USER_INTERFACE_CONFIGURATION_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_THERMOSTAT_USER_INTERFACE_CONFIGURATION;
    private static final String FAN_CONTROL_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_FAN_CONTROL;
    private static final String THERMOSTAT_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_THERMOSTAT;
    private static final String PUMP_CONFIGURATION_AND_CONTROL_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_PUMP_CONFIGURATION_AND_CONTROL;
    //Closures
    private static final String SHADE_CONFIGURATION_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_SHADE_CONFIGURATION;
    private static final String DOOR_LOCK_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_DOOR_LOCK;
    private static final String WINDOW_COVERING_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_WINDOW_COVERING;
    //Lightening
    private static final String COLOR_CONTROL_KEY = ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION + ":"+ ZigBeeApiConstants.CLUSTER_ID_COLOR_CONTROL;

    public ClusterFactoryImpl(ZigBeeApiContext context){
        super(context);

        //General
        addCluster(BASIC_KEY, BasicImpl.class);
        addCluster(POWER_CONFIGURATION_KEY, PowerConfigurationImpl.class);
        addCluster(DEVICE_TEMPERATURE_CONFIGURATION_KEY, DeviceTemperatureConfigurationImpl.class);
        addCluster(IDENTIFY_KEY, IdentifyImpl.class);
        addCluster(GROUPS_KEY, GroupsImpl.class);
        addCluster(SCENES_KEY, ScenesImpl.class);
        addCluster(ONOFF_KEY, OnOffImpl.class);
        addCluster(ON_OFF_SWITCH_CONFIGURATION_KEY, OnOffSwitchConfigurationImpl.class);
        addCluster(LEVEL_CONTROL_KEY, LevelControlImpl.class);
        addCluster(ALARMS_KEY, AlarmsImpl.class);
        addCluster(BINARY_INPUT_KEY, BinaryInputImpl.class);

        //Smart Energy
        addCluster(METERING_KEY, MeteringImpl.class);

        //fuori profilo HA
        addCluster(ANALOG_INPUT_KEY, AnalogInputImpl.class);
        addCluster(COMMISSIONING_KEY, CommissioningImpl.class); // new
        addCluster(TIME_KEY, TimeImpl.class);


        //Measurement and Sensing
        addCluster(ILLUMINANCE_MEASUREMENT_KEY, IlluminanceMeasurementImpl.class); // new
        addCluster(TEMPERATURE_MEASUREMENT_KEY, TemperatureMeasurementImpl.class);
        addCluster(RELATIVE_HUMIDITY_MEASUREMENT_KEY, RelativeHumidityMeasurementImpl.class);
        addCluster(OCCUPANCY_SENSING_KEY, OccupancySensingImpl.class);
        addCluster(PRESSURE_MEASUREMENT_KEY, PressureMeasurementImpl.class);
        addCluster(FLOW_MEASUREMENT_KEY, FlowMeasurementImpl.class);
        addCluster(ILLUMINANCE_LEVEL_SENSING_KEY, IlluminanceLevelSensingImpl.class);

        //Security and Safety
        addCluster(IAS_ZONE_KEY, IASZoneImpl.class);
        addCluster(IAS_WD_KEY, IASWDImpl.class);
        addCluster(IAS_ACE_KEY, IASACEImpl.class);

        //HVAC
        addCluster(PUMP_CONFIGURATION_AND_CONTROL_KEY, PumpConfigurationAndControlImpl.class);
        addCluster(THERMOSTAT_KEY, ThermostatImpl.class);
        addCluster(FAN_CONTROL_KEY, FanControlImpl.class);
        addCluster(THERMOSTAT_USER_INTERFACE_CONFIGURATION_KEY, ThermostatUserInterfaceConfigurationImpl.class);

        //Closures
        addCluster(SHADE_CONFIGURATION_KEY, ShadeConfigurationImpl.class);
        addCluster(DOOR_LOCK_KEY, DoorLockImpl.class);
        addCluster(WINDOW_COVERING_KEY, WindowCoveringImpl.class);


        //Lighting
        addCluster(COLOR_CONTROL_KEY, ColorControlImpl.class);


    }


}
