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
package it.cnr.isti.zigbee.zcl.cluster.impl.core;

import static org.junit.Assert.assertEquals;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeType;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultDeserializer;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultSerializer;

import org.junit.Test;

/**
 * 
 * Testing class for classes {@link DefaultSerializer} and {@link DefaultDeserializer} 
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 *
 */
public class DefaultDeserializerSerializerTest {

	@Test
	public void testFake() {
	}

	public void testReadAppendZigBeeType() {
		Object[] payloadObject = new Object[]{
				new Integer(-1), new Integer(-1), new Integer(-1), new Integer(-1), //Bitmap Testing
				new Integer(-1), new Integer(-1), new Integer(-1), new Integer(-1), //Data Testing
				new Integer(-1), new Integer(-1), new Integer(-1), new Integer(-1), //Signed Testing
				new Integer(0xFF),  new Integer(0x0F),
				new Integer(0xFFFF), new Integer(0x00FF), 
				new Integer(0xFFFFFF), new Integer(0x00FFFF),  
				new Long(0xFFFFFFFFL), new Long(0x00FFFFFFL), //Unsigned Testing
				new Integer(-1), new Integer(-1), //Enumeration Testing
				Boolean.TRUE, Boolean.FALSE, //Boolean Testing
				"Un po' di dati" //String Testing
		};
		byte[] payloadData = new byte[4096]; //Big enough to contain all the data
		ZigBeeType[] payloadContent = new ZigBeeType[]{
				ZigBeeType.Bitmap8bit,ZigBeeType.Bitmap16bit,ZigBeeType.Bitmap24bit,ZigBeeType.Bitmap32bit,
				ZigBeeType.Data8bit, ZigBeeType.Data16bit, ZigBeeType.Data24bit, ZigBeeType.Data32bit,
				ZigBeeType.SignedInteger8bit, ZigBeeType.SignedInteger16bit, ZigBeeType.SignedInteger24bit, ZigBeeType.SignedInteger32bit,
				ZigBeeType.UnsignedInteger8bit, ZigBeeType.UnsignedInteger8bit,
				ZigBeeType.UnsignedInteger16bit, ZigBeeType.UnsignedInteger16bit,
				ZigBeeType.UnsignedInteger24bit, ZigBeeType.UnsignedInteger24bit,
				ZigBeeType.UnsignedInteger32bit, ZigBeeType.UnsignedInteger32bit,
				ZigBeeType.Enumeration8bit, ZigBeeType.Enumeration16bit,
				ZigBeeType.Boolean, ZigBeeType.Boolean,
				ZigBeeType.CharacterString
		};
		DefaultSerializer serializer = new DefaultSerializer(payloadData,0);
		DefaultDeserializer deserializer = new DefaultDeserializer(payloadData,0);
		for (int i = 0; i < payloadContent.length; i++) {
			serializer.appendZigBeeType(payloadObject[i], payloadContent[i]);
		}
		for (int i = 0; i < payloadContent.length; i++) {
			assertEquals("Reading back serialized "+payloadObject[i]+" as "+payloadContent[i],payloadObject[i], deserializer.readZigBeeType(payloadContent[i]));
		}
	}

}
