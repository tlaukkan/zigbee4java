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

package org.bubblecloud.zigbee.api.cluster.general;

import org.bubblecloud.zigbee.api.ZigbeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;

/**
 * 
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision:  $ ($LastChangedDate: $)
 *
 */

public interface ColorControl extends Cluster {
    
    public String getDescription() throws ZigbeeDeviceException;

    // commands
    public void moveToHue(short hue, byte direction, int transitionTime) throws ZigbeeDeviceException;

    public void moveHue(byte moveMode, short rate) throws ZigbeeDeviceException;

    public void stepHue(byte stepMode, short stepSize, short transtionTime) throws ZigbeeDeviceException;

    public void movetoSaturation(short saturation, int transitionTime) throws ZigbeeDeviceException;

    public void moveSaturation(byte moveMode, short rate) throws ZigbeeDeviceException;

    public void stepSaturation(byte stepMode, short stepSize, short transitionTime) throws ZigbeeDeviceException;

    public void movetoHue_Saturation(short hue, short saturation, int transitionTime) throws ZigbeeDeviceException;

    public void moveToColor(int colorX, int colorY, int transitionTime) throws ZigbeeDeviceException;

    public void moveColor(int rateX, int rateY) throws ZigbeeDeviceException;

    public void stepColor(int stepX, int stepY, int transitionTime) throws ZigbeeDeviceException;

    public void moveToColorTemperature(int colorTemperature, int transitionTime) throws ZigbeeDeviceException;
}
