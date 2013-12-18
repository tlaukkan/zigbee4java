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

package org.bubblecloud.zigbee.proxy;

import org.bubblecloud.zigbee.proxy.cluster.impl.*;
import org.bubblecloud.zigbee.ZigbeeProxyContext;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 *
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 *
 */
public class ClusterFactoryImpl extends ClusterFactoryBase {

    //General
    final static String BASIC_KEY = ProxyConstants.ID + ":" + ProxyConstants.BASIC;
    final static String POWER_CONFIGURATION_KEY = ProxyConstants.ID + ":"+ ProxyConstants.POWER_CONFIGURATION;
    final static String DEVICE_TEMPERATURE_CONFIGURATION_KEY = ProxyConstants.ID + ":"+ ProxyConstants.DEVICE_TEMPERATURE_CONFIGURATION;
    final static String IDENTIFY_KEY = ProxyConstants.ID + ":"+ ProxyConstants.IDENTIFY;
    final static String GROUPS_KEY = ProxyConstants.ID + ":"+ ProxyConstants.GROUPS;
    final static String SCENES_KEY = ProxyConstants.ID + ":"+ ProxyConstants.SCENES;
    final static String ONOFF_KEY = ProxyConstants.ID + ":"+ ProxyConstants.ON_OFF;
    final static String ON_OFF_SWITCH_CONFIGURATION_KEY = ProxyConstants.ID + ":"+ ProxyConstants.ON_OFF_SWITCH_CONFIGURATION;
    final static String LEVEL_CONTROL_KEY = ProxyConstants.ID + ":"+ ProxyConstants.LEVEL_CONTROL;
    final static String ALARMS_KEY = ProxyConstants.ID + ":"+ ProxyConstants.ALARMS;
    final static String TIME_KEY = ProxyConstants.ID + ":"+ ProxyConstants.TIME;
    final static String BINARY_INPUT_KEY = ProxyConstants.ID + ":"+ ProxyConstants.BINARY_INPUT;

    // final static String RSSI_LOCATION
    final static String COMMISSIONING_KEY = ProxyConstants.ID + ":"+ ProxyConstants.COMMISSIONING;

    //Measurement and Sensing
    final static String ILLUMINANCE_MEASUREMENT_KEY = ProxyConstants.ID + ":"+ ProxyConstants.ILLUMINANCE_MEASUREMENT; // new
    final static String TEMPERATURE_MEASUREMENT_KEY = ProxyConstants.ID + ":"+ ProxyConstants.TEMPERATURE_MEASUREMENT;
    final static String RELATIVE_HUMIDITY_MEASUREMENT_KEY = ProxyConstants.ID + ":"+ ProxyConstants.RELATIVE_HUMIDITY_MEASUREMENT;
    final static String OCCUPANCY_SENSING_KEY = ProxyConstants.ID + ":"+ ProxyConstants.OCCUPANCY_SENSING;

    //Security and Safety
    final static String IAS_ZONE_KEY = ProxyConstants.ID + ":"+ ProxyConstants.IAS_ZONE;
    final static String IAS_WD_KEY = ProxyConstants.ID + ":"+ ProxyConstants.IAS_WD;
    final static String IAS_ACE_KEY = ProxyConstants.ID + ":"+ ProxyConstants.IAS_ACE;

    public ClusterFactoryImpl(ZigbeeProxyContext ctx){
        super(ctx);

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
        addCluster(TIME_KEY, TimeImpl.class);
        addCluster(BINARY_INPUT_KEY, BinaryInputImpl.class);

        addCluster(COMMISSIONING_KEY, TimeImpl.class); // new

        //Measurement and Sensing
        addCluster(ILLUMINANCE_MEASUREMENT_KEY, IlluminanceMeasurementImpl.class); // new
        addCluster(TEMPERATURE_MEASUREMENT_KEY, TemperatureMeasurementImpl.class);
        addCluster(RELATIVE_HUMIDITY_MEASUREMENT_KEY, RelativeHumidityMeasurementImpl.class);
        addCluster(OCCUPANCY_SENSING_KEY, OccupacySensingImpl.class);

        //Security and Safety
        addCluster(IAS_ZONE_KEY, IASZoneImpl.class);
        addCluster(IAS_WD_KEY, IASWDImpl.class);
        addCluster(IAS_ACE_KEY, IASACEImpl.class);
    }

}
