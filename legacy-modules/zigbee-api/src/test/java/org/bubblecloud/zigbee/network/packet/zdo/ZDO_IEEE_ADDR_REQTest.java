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

package org.bubblecloud.zigbee.network.packet.zdo;

import org.bubblecloud.zigbee.v3.model.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.test.ZToolPacketUtil;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_IEEE_ADDR_REQ.REQ_TYPE;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class of {@link ZDO_IEEE_ADDR_REQ}
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ZDO_IEEE_ADDR_REQTest {

	@Test
	public void testZDO_IEEE_ADDR_REQshortREQ_TYPEint() {
		ZDO_IEEE_ADDR_REQ original = new ZDO_IEEE_ADDR_REQ(
				new ZToolAddress16(0xF0,0xFA), 1, 0
		);
		
		ZDO_IEEE_ADDR_REQ modified = new ZDO_IEEE_ADDR_REQ(
				(short) 0xF0FA, REQ_TYPE.EXTENDED, (byte) 0
		);
		
		ZToolPacketUtil.isSamePacket(original, modified);
	}
	
	
	@Test
	public void testGetShortAddress() {
		ZDO_IEEE_ADDR_REQ original;
		
		original = new ZDO_IEEE_ADDR_REQ(
				new ZToolAddress16(0xF0,0xFA), 1, 0
		);
		assertEquals(original.getShortAddress() & 0xFFFF, 0xF0FA);
		assertEquals(original.getShortAddress(), (short) -3846);

		original = new ZDO_IEEE_ADDR_REQ(
				new ZToolAddress16(0xFB,0xF0), 1, 0
		);
		assertEquals(original.getShortAddress() & 0xFFFF, 0xFBF0);
		assertEquals(original.getShortAddress(), (short) -1040);
		
		original = new ZDO_IEEE_ADDR_REQ(
				new ZToolAddress16(0x0F,0xFF), 1, 0
		);
		assertEquals(original.getShortAddress() & 0xFFFF, 0x0FFF);
		assertEquals(original.getShortAddress(), (short) 4095);
	}

	@Test
	public void testGetRequestType() {
		ZDO_IEEE_ADDR_REQ original;
		
		original = new ZDO_IEEE_ADDR_REQ(
				new ZToolAddress16(0xF0,0xFA), 1, 0
		);
		assertEquals(original.getRequestType(), REQ_TYPE.EXTENDED);

		original = new ZDO_IEEE_ADDR_REQ(
				new ZToolAddress16(0xFB,0xF0), 0, 0
		);
		assertEquals(original.getRequestType(), REQ_TYPE.SINGLE_DEVICE_RESPONSE);
	}

	@Test
	public void testGetStartIndex() {
		ZDO_IEEE_ADDR_REQ original;
		
		original = new ZDO_IEEE_ADDR_REQ(
				new ZToolAddress16(0xF0,0xFA), 1, 12
		);
		assertEquals(original.getStartIndex(), 12);

		original = new ZDO_IEEE_ADDR_REQ(
				new ZToolAddress16(0xFB,0xF0), 0, 15
		);
		assertEquals(original.getStartIndex(), 15);
	}

}
