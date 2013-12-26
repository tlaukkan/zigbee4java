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
package org.bubblecloud.zigbee.proxy.device.api.security_safety;

import org.bubblecloud.zigbee.proxy.DeviceProxy;
import org.bubblecloud.zigbee.proxy.cluster.api.HomeAutomationProfile;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Groups;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Identify;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Scenes;
import org.bubblecloud.zigbee.proxy.cluster.glue.security_safety.IASACE;
import org.bubblecloud.zigbee.proxy.cluster.glue.security_safety.IASWD;
import org.bubblecloud.zigbee.proxy.cluster.glue.security_safety.IASZone;
import org.bubblecloud.zigbee.util.ArraysUtil;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 */
public interface IASControlAndIndicatingEquipment extends DeviceProxy {

    public static final int DEVICE_ID = 0x0400;
    public static final String NAME = "IAS Control and Indicating Equipment";
    public static final int[] MANDATORY = ArraysUtil.append(DeviceProxy.MANDATORY, new int[]{
            HomeAutomationProfile.IAS_ZONE, HomeAutomationProfile.IDENTIFY, HomeAutomationProfile.IAS_ACE, HomeAutomationProfile.IAS_WD
    });
    public static final int[] OPTIONAL = ArraysUtil.append(DeviceProxy.OPTIONAL, new int[]{
            HomeAutomationProfile.SCENES, HomeAutomationProfile.GROUPS
    });
    public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
    public static final int[] CUSTOM = {};

    public IASACE getIASACE();

    public Identify getIdentify();

    public IASZone getIASZone();

    public IASWD getIASwd();

    public Scenes getScenes();

    public Groups getGroups();
}