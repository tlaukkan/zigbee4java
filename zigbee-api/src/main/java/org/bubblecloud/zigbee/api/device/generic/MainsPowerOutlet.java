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
package org.bubblecloud.zigbee.api.device.generic;

import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.cluster.general.Groups;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.api.cluster.general.OnOffSwitchConfiguration;
import org.bubblecloud.zigbee.api.cluster.general.Scenes;
import org.bubblecloud.zigbee.util.ArraysUtil;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi ISTI-CNR</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 */
public interface MainsPowerOutlet extends Device {

    public static final int DEVICE_ID = 0x0009;
    public static final String NAME = "Mains Power Outlet";
    public static final int[] MANDATORY = ArraysUtil.append(Device.MANDATORY, new int[]{
            ZigBeeApiConstants.CLUSTER_ID_ON_OFF, ZigBeeApiConstants.CLUSTER_ID_SCENES, ZigBeeApiConstants.CLUSTER_ID_GROUPS
    });
    public static final int[] OPTIONAL = Device.OPTIONAL;
    public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
    public static final int[] CUSTOM = {};


    /**
     * Access method for the <b>Mandatory</b> cluster: {@link OnOffSwitchConfiguration}
     *
     * @return the {@link OnOffSwitchConfiguration} cluster object
     */
    public OnOff getOnOff();

    public Scenes getScenes();

    public Groups getGroups();
}