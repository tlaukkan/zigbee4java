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
package it.cnr.isti.zigbee.zcl.clusters.api;

import it.cnr.isti.zigbee.zcl.library.api.general.Alarms;
import it.cnr.isti.zigbee.zcl.library.api.general.Basic;
import it.cnr.isti.zigbee.zcl.library.api.general.Commissioning;
import it.cnr.isti.zigbee.zcl.library.api.general.DeviceTemperatureConfiguration;
import it.cnr.isti.zigbee.zcl.library.api.general.Groups;
import it.cnr.isti.zigbee.zcl.library.api.general.Identify;
import it.cnr.isti.zigbee.zcl.library.api.general.LevelControl;
import it.cnr.isti.zigbee.zcl.library.api.general.OnOff;
import it.cnr.isti.zigbee.zcl.library.api.general.OnOffSwitchConfiguration;
import it.cnr.isti.zigbee.zcl.library.api.general.PowerConfiguration;
import it.cnr.isti.zigbee.zcl.library.api.general.RSSILocation;
import it.cnr.isti.zigbee.zcl.library.api.general.Scenes;
import it.cnr.isti.zigbee.zcl.library.api.general.Time;
import it.cnr.isti.zigbee.zcl.library.api.lighting.ColorControl;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.IlluminanceMeasurement;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.OccupacySensing;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.RelativeHumidityMeasurement;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.TemperatureMeasurement;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASACE;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASWD;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASZone;

/**
 *
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 *
 */
public interface Clusters {

    public static final String ALARMS = "9";
    public static final String BASIC = "0";
    public static final String COMMISSIONING = "21";
    public static final String DEVICE_TEMPERATURE_CONFIGURATION = "2";
    public static final String GROUPS = "4";
    public static final String IDENTIFY = "3";
    public static final String LEVEL_CONTROL = "8";
    public static final String ON_OFF = "6";
    public static final String ON_OFF_SWITCH_CONFIGURATION = "7";
    public static final String POWER_CONFIGURATION = "1";
    public static final String RSSI_LOCATION = "11";
    public static final String SCENES = "5";
    public static final String TIME = "10";

    public static final String COLOR_CONTROL = "768";

    public static final String ILLUMINANCE_MEASUREMENT = "1024";
    public static final String OCCUPANCY_SENSING = "1030";
    public static final String RELATIVE_HUMIDITY_MEASUREMENT = "1029";
    public static final String TEMPERATURE_MEASUREMENT = "1026";

    public static final String IAS_ACE = "1281";
    public static final String IAS_WD = "1282";
    public static final String IAS_ZONE = "1280";

    public static final String[][] cluster_impl = {new String[]{ALARMS, Alarms.class.getName()}, new String[]{BASIC, Basic.class.getName()},
        new String[]{COMMISSIONING, Commissioning.class.getName()}, new String[]{DEVICE_TEMPERATURE_CONFIGURATION, DeviceTemperatureConfiguration.class.getName()},
        new String[]{GROUPS, Groups.class.getName()}, new String[]{IDENTIFY, Identify.class.getName()}, new String[]{LEVEL_CONTROL, LevelControl.class.getName()},
        new String[]{ON_OFF, OnOff.class.getName()}, new String[]{ON_OFF_SWITCH_CONFIGURATION, OnOffSwitchConfiguration.class.getName()},
        new String[]{POWER_CONFIGURATION, PowerConfiguration.class.getName()}, new String[]{RSSI_LOCATION, RSSILocation.class.getName()},
        new String[]{SCENES, Scenes.class.getName()}, new String[]{TIME, Time.class.getName()},

        new String[]{COLOR_CONTROL, ColorControl.class.getName()},

        new String[]{ILLUMINANCE_MEASUREMENT, IlluminanceMeasurement.class.getName()}, new String[]{OCCUPANCY_SENSING, OccupacySensing.class.getName()},
        new String[]{RELATIVE_HUMIDITY_MEASUREMENT, RelativeHumidityMeasurement.class.getName()},
        new String[]{TEMPERATURE_MEASUREMENT, TemperatureMeasurement.class.getName()},

        new String[]{IAS_ACE, IASACE.class.getName()},new String[]{IAS_WD, IASWD.class.getName()},new String[]{IAS_ZONE, IASZone.class.getName()}};
}