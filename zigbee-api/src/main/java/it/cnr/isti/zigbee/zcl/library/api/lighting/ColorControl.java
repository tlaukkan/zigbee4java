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

package it.cnr.isti.zigbee.zcl.library.api.lighting;

import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 *
 */
public interface ColorControl extends ZCLCluster{
	
	static final short ID = 0x0300;
	static final String NAME = "Color Control";
	static final String DESCRIPTION = "Attributes and commands for changing the color of a light.";

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
	public Response moveToHue(short hue, byte direction, int transitionTime);
	public Response moveHue(byte moveMode, short rate);
	public Response stepHue(byte stepMode, short stepSize, short transtionTime);
	public Response movetoSaturation(short saturation, int transitionTime);
	public Response moveSaturation(byte moveMode, short rate);
	public Response stepSaturation(byte stepMode, short stepSize, short transitionTime);
	public Response movetoHue_Saturation(short hue, short saturation, int transitionTime);
	public Response moveToColor(int colorX, int colorY, int transitionTime);
	public Response moveColor(int rateX, int rateY);
	public Response stepColor(int stepX, int stepY, int transitionTime);
	public Response moveToColorTemperature(int colorTemperature, int transitionTime);
}
