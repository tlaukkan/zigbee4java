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

package org.bubblecloud.zigbee.api.device.lighting;

import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.cluster.general.Groups;
import org.bubblecloud.zigbee.api.cluster.general.LevelControl;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.api.cluster.general.Scenes;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.OccupancySensing;
import org.bubblecloud.zigbee.util.ArraysUtil;
import org.bubblecloud.zigbee.api.Device;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public interface DimmableLight extends Device {

    public static final int DEVICE_ID = 0x0101;
    public static final String NAME = "Dimmable Light";
    public static final int[] MANDATORY = ArraysUtil.append(Device.MANDATORY, new int[]{
            ZigBeeApiConstants.CLUSTER_ID_ON_OFF, ZigBeeApiConstants.CLUSTER_ID_LEVEL_CONTROL, ZigBeeApiConstants.CLUSTER_ID_SCENES, ZigBeeApiConstants.CLUSTER_ID_GROUPS
    });
    public static final int[] OPTIONAL = ArraysUtil.append(Device.OPTIONAL, new int[]{
            ZigBeeApiConstants.CLUSTER_ID_OCCUPANCY_SENSING
    });
    public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
    public static final int[] CUSTOM = {};


    /**
     * Access method for the <b>Mandatory</b> cluster: {@link OnOff}
     *
     * @return the {@link OnOff} cluster object
     */
    public OnOff getOnOff();

    /**
     * Access method for the <b>Mandatory</b> cluster: {@link Scenes}
     *
     * @return the {@link Scenes} cluster object
     */
    public Scenes getScenes();

    /**
     * Access method for the <b>Mandatory</b> cluster: {@link Groups}
     *
     * @return the {@link Groups} cluster object
     */
    public Groups getGroups();

    /**
     * Access method for the <b>Mandatory</b> cluster: {@link LevelControl}
     *
     * @return the {@link LevelControl} cluster object
     */
    public LevelControl getLevelControl();

    /**
     * Access method for the <b>Optional</b> cluster: {@link org.bubblecloud.zigbee.api.cluster.measurement_sensing.OccupancySensing}
     *
     * @return the {@link org.bubblecloud.zigbee.api.cluster.measurement_sensing.OccupancySensing} cluster object if implemented by the device, otherwise <code>null</code>
     */
    public abstract OccupancySensing getOccupacySensing();

}
