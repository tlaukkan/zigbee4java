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

package com.itaca.ztool.api.zdo;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolAddress64;
import com.itaca.ztool.api.ZToolCMD;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 *
 */
public class ZDO_END_DEVICE_ANNCE_INDTest {

	@Test
	public void testZDO_END_DEVICE_ANNCE_INDIntArray() {
		//FE 0D 45 C1 01 00 FE FF D1 17 01 00 00 4B 12 00 0E 19
		int[] packet = new int[]{
				0xFE, 0x0D, 0x45, 0xC1, //Header 				
				0x01, 0x00, 0xFE, 0xFF, 0xD1, 0x17, 0x01, 0x00, 0x00, 0x4B, 0x12, 0x00, 0x0E, 
				0x19 //FCS
		};
		ZDO_END_DEVICE_ANNCE_IND msg = new ZDO_END_DEVICE_ANNCE_IND(
				//Copy the payload
				Arrays.copyOfRange(packet, 4, packet.length-1)
		);
		assertArrayEquals(packet, msg.getPacket());
		assertEquals((short) msg.getCMD().get16BitValue(), msg.getCommandId());
		assertEquals((short) ZToolCMD.ZDO_END_DEVICE_ANNCE_IND, msg.getCommandId());
		assertEquals(msg.SrcAddr, new ZToolAddress16(0x00, 0x01));
		assertEquals(msg.NwkAddr, new ZToolAddress16(0xFF, 0xFE));
		assertEquals(
				msg.IEEEAddr, 
				new ZToolAddress64(new byte[]{
						0x00, 0x12, 0x4B, 0x00, 0x00, 0x01, 0x17, (byte) 0xD1 
				})
		);
		assertEquals(
				msg.IEEEAddr.toString(), 
				"0x00 0x12 0x4b 0x00 0x00 0x01 0x17 0xd1"
		);
	}

}
