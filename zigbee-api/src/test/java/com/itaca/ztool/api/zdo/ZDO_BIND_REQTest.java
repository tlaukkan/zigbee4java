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
import com.itaca.ztool.api.test.ZToolPacketUtil;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class of {@link ZDO_BIND_REQ}
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.3.0
 */
public class ZDO_BIND_REQTest {
	
	@Test
	public void testZDO_BIND_REQShortShortLongByteShortByte() {
		//TODO Create a test
	}

	@Test
	public void testZDO_BIND_REQShortShortLongByteLongByte() {
		
		/*
		 FE 17 25 21 
		 6F 79 
		 59 18 01 00 00 00 4B 12 
		 DC 0B FC 03 
		 8D CC 0B 00 00 4B 12 00 
		 20 07                   
		*/
		
		ZDO_BIND_REQ original = new ZDO_BIND_REQ(
				new ZToolAddress16(0x79, 0x6F),
				/* "59 18 01 00 00 00 4B 12" */
				new ZToolAddress64( 0x124B000000011859L ),
				0xDC,
				new DoubleByte( 0xFC, 0x0B ),
				0x03,
				/* "8D CC 0B 00 00 4B 12 00" */
				new ZToolAddress64( 0x00124B00000BCC8DL ),
				//new ZToolAddress64( 0x8DCC0B00004B1200L ),
				0x20
		);
		
		ZDO_BIND_REQ modified = new ZDO_BIND_REQ(
				 (short) 0x796F, (short) 0xFC0B, 
				 0x124B000000011859L, (byte) 0xDC,
				 0x00124B00000BCC8DL, (byte) 0x20				                    
		);
		
		
		int[] packet = new int[]{
				0xFE, 0x17, 0x25, 0x21, //Header : SP, Length, Cmd1, Cmd2 
				0x6F, 0x79, //Destination Address 
				0x59, 0x18, 0x01, 0x00, 0x00, 0x00, 0x4B, 0x12, //Source Address 
				0xDC, //Source EndPoint 
				0x0B, 0xFC, // Cluster Id 
				0x03, // Addressing Mode 
				0x8D, 0xCC, 0x0B, 0x00, 0x00, 0x4B, 0x12, 0x00, //Destination Address 
				0x20, // Destination EndPoint
				0x07 //FCS
		};
		
		assertArrayEquals(packet, original.getPacket());
		
		ZToolPacketUtil.isSamePacket(original, modified);
		
		assertArrayEquals(packet, modified.getPacket());
	}

}
