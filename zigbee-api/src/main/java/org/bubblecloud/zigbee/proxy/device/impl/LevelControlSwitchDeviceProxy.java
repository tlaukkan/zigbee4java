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

package org.bubblecloud.zigbee.proxy.device.impl;

import org.bubblecloud.zigbee.network.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.*;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.OnOffSwitchConfiguration;
import org.bubblecloud.zigbee.proxy.device.api.generic.LevelControlSwitch;
import org.bubblecloud.zigbee.proxy.cluster.api.HomeAutomationProfile;
import org.bubblecloud.zigbee.ZigbeeProxyContext;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class LevelControlSwitchDeviceProxy extends DeviceProxyBase implements LevelControlSwitch {

    private OnOffSwitchConfiguration onOffSwitchConfiguration;

    public LevelControlSwitchDeviceProxy(ZigbeeProxyContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {
        super(ctx, zbDevice);
        onOffSwitchConfiguration = (OnOffSwitchConfiguration) getCluster(HomeAutomationProfile.ON_OFF_SWITCH_CONFIGURATION);
    }

    final static DeviceDescription DEVICE_DESCRIPTOR = new AbstractDeviceDescription() {

        public int[] getCustomClusters() {
            return LevelControlSwitch.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return LevelControlSwitch.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return LevelControlSwitch.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return LevelControlSwitch.STANDARD;
        }

    };

    @Override
    public DeviceDescription getDescription() {
        return DEVICE_DESCRIPTOR;
    }

    @Override
    public String getName() {
        return LevelControlSwitch.NAME;
    }

    public OnOffSwitchConfiguration getOnOffSwitchConfiguration() {
        return onOffSwitchConfiguration;
    }
}
