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

package org.bubblecloud.zigbee.api.cluster.impl.api.lighting;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLCluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 */
public interface ColorControl extends ZCLCluster {

    static final short ID = 0x0300;
    static final String NAME = "Color Control";
    static final String DESCRIPTION = "Attributes and commands for changing the color of a light.";

    public static final byte MOVE_TO_HUE_ID = 0x00;
    public static final byte MOVE_HUE_ID = 0x01;
    public static final byte STEP_HUE_ID = 0x02;
    public static final byte MOVE_TO_SATURATION_ID = 0x03;
    public static final byte MOVE_SATURATION_ID = 0x04;
    public static final byte STEP_SATURATION_ID = 0x05;
    public static final byte MOVE_TO_HUE_AND_SATURATION_ID = 0x06;
    public static final byte MOVE_TO_COLOR_ID = 0x07;
    public static final byte MOVE_COLOR_ID = 0x08;
    public static final byte STEP_COLOR_ID = 0x09;
    public static final byte MOVE_TO_COLOR_TEMPERATURE = 0x0a;

    // color information
    public Attribute getAttributeCurrentHue();

    public Attribute getAttributeCurrentSaturation();

    public Attribute getAttributeRemainingTime();

    public Attribute getAttributeCurrentX();

    public Attribute getAttributeCurrentY();

    public Attribute getAttributeDriftCompensation();

    public Attribute getAttributeCompensationText();

    public Attribute getAttributeColorTemperature();

    public Attribute getAttributeColorMode();

    // defined primaries information
    public Attribute getAttributeNumberOfPrimaries();

    public Attribute getAttributePrimary1X();

    public Attribute getAttributePrimary1Y();

    public Attribute getAttributePrimary1Intensity();

    public Attribute getAttributePrimary2X();

    public Attribute getAttributePrimary2Y();

    public Attribute getAttributePrimary2Intensity();

    public Attribute getAttributePrimary3X();

    public Attribute getAttributePrimary3Y();

    public Attribute getAttributePrimary3Intensity();

    // additional defined primaries information
    public Attribute getAttributePrimary4X();

    public Attribute getAttributePrimary4Y();

    public Attribute getAttributePrimary4Intensity();

    public Attribute getAttributePrimary5X();

    public Attribute getAttributePrimary5Y();

    public Attribute getAttributePrimary5Intensity();

    public Attribute getAttributePrimary6X();

    public Attribute getAttributePrimary6Y();

    public Attribute getAttributePrimary6Intensity();

    // defined color point settings
    public Attribute getAttributeWhitePointX();

    public Attribute getAttributeWhitePointY();

    public Attribute getAttributeColorPointRX();

    public Attribute getAttributeColorPointRY();

    public Attribute getAttributeColorPointRIntensity();

    public Attribute getAttributeColorPointGX();

    public Attribute getAttributeColorPointGY();

    public Attribute getAttributeColorPointGIntensity();

    public Attribute getAttributeColorPointBX();

    public Attribute getAttributeColorPointBY();

    public Attribute getAttributeColorPointBIntensity();

    // commands
    public Response moveToHue(short hue, byte direction, int transitionTime) throws ZigBeeClusterException;

    public Response moveHue(byte moveMode, short rate) throws ZigBeeClusterException;

    public Response stepHue(byte stepMode, short stepSize, short transtionTime) throws ZigBeeClusterException;

    public Response movetoSaturation(short saturation, int transitionTime) throws ZigBeeClusterException;

    public Response moveSaturation(byte moveMode, short rate) throws ZigBeeClusterException;

    public Response stepSaturation(byte stepMode, short stepSize, short transitionTime) throws ZigBeeClusterException;

    public Response movetoHue_Saturation(short hue, short saturation, int transitionTime) throws ZigBeeClusterException;

    public Response moveToColor(int colorX, int colorY, int transitionTime) throws ZigBeeClusterException;

    public Response moveColor(int rateX, int rateY) throws ZigBeeClusterException;

    public Response stepColor(int stepX, int stepY, int transitionTime) throws ZigBeeClusterException;

    public Response moveToColorTemperature(int colorTemperature, int transitionTime) throws ZigBeeClusterException;
}
