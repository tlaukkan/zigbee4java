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

import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;

/**
 * 
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision:  $ ($LastChangedDate: $)
 *
 */

public interface ColorControl extends Cluster {

	/**
     * Sets hue according to HSV (aka HSB) color model.
     * @param hue hue (degrees) / 360 * 254 in the range 0 - 254.
     * @param direction 0 = shortest, 1 = longest, 2 = up, 3 = down
     * @param transitionTime transition time in 1/10ths of a second.
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void moveToHue(int hue, int direction, int transitionTime) throws ZigBeeDeviceException;
    /**
     * Moves hue according to HSV (aka HSB) color model.
     * @param moveMode 0 = stop, 1 = up, 2 = reserved, 3 = down
     * @param rate steps per second
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void moveHue(int moveMode, int rate) throws ZigBeeDeviceException;
    /**
     * Steps hue according to HSV (aka HSB) color model.
     * @param stepMode 0 = reserved, 1 = up, 2 = reserved, 3 = down
     * @param stepSize step to be added or subtracted from current hue
     * @param transtionTime transition time in 1/10ths of a second.
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void stepHue(int stepMode, int stepSize, int transtionTime) throws ZigBeeDeviceException;
    /**
     * Moves to saturation according to HSV (aka HSB) color model.
     * @param saturation saturation * 254 in the range 0 - 254.
     * @param transitionTime transition time in 1/10ths of a second.
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void movetoSaturation(int saturation, int transitionTime) throws ZigBeeDeviceException;
    /**
     * Moves saturation according to HSV (aka HSB) color model.
     * @param moveMode 0 = stop, 1 = up, 2 = reserved, 3 = down
     * @param rate steps per second
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void moveSaturation(int moveMode, int rate) throws ZigBeeDeviceException;
    /**
     * Steps saturation according to HSV (aka HSB) color model.
     * @param stepMode 0 = reserved, 1 = up, 2 = reserved, 3 = down
     * @param stepSize step to be added or subtracted from current saturation
     * @param transitionTime transition time in 1/10ths of a second.
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void stepSaturation(int stepMode, int stepSize, int transitionTime) throws ZigBeeDeviceException;
    /**
     * Moves to hue and saturation according to HSV (aka HSB) color model.
     * @param hue hue (degrees) / 360 * 254 in the range 0 - 254.
     * @param saturation saturation * 254 in the range 0 - 254.
     * @param transitionTime transition time in 1/10ths of a second.
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void moveToHueAndSaturation(int hue, int saturation, int transitionTime) throws ZigBeeDeviceException;
    /**
     * Moves to color according to CIE 1931 Color Space.
     * @param colorX x * 65536 where colorX can be in rance 0 to 65279
     * @param colorY y * 65536 where colorY can be in rance 0 to 65279
     * @param transitionTime transition time in 1/10ths of a second.
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void moveToColor(int colorX, int colorY, int transitionTime) throws ZigBeeDeviceException;
    /**
     * Moves color according to CIE 1931 Color Space.
     * @param rateX steps per second in X value
     * @param rateY steps per second in X value
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void moveColor(int rateX, int rateY) throws ZigBeeDeviceException;
    /**
     * Steps color according to CIE 1931 Color Space.
     * @param stepX step to be added or subtracted from current X value
     * @param stepY step to be added or subtracted from current Y value
     * @param transitionTime transition time in 1/10ths of a second.
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void stepColor(int stepX, int stepY, int transitionTime) throws ZigBeeDeviceException;
    /**
     * Moves to color temperature.
     * @param colorTemperature 1 000 000 / color temperature (Kelvins) in the range 1 to 65279
     * @param transitionTime  transition time in 1/10ths of a second.
     * @throws ZigBeeDeviceException if connectivity or other exception occurs with the device.
     */
    public void moveToColorTemperature(int colorTemperature, int transitionTime) throws ZigBeeDeviceException;
}
