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
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class of {@link ZDO_ACTIVE_EP_REQ}
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ZDO_ACTIVE_EP_REQTest {

	@Test
	public void testZDO_ACTIVE_EP_REQInt() {
		ZDO_ACTIVE_EP_REQ original = new ZDO_ACTIVE_EP_REQ(
				new ZToolAddress16(0xF0,0xFA), new ZToolAddress16(0xF0, 0xFA)
		);
		ZDO_ACTIVE_EP_REQ modified = new ZDO_ACTIVE_EP_REQ((short) 0xF0FA);
		
		ZToolPacketUtil.isSamePacket(original, modified);
		
		int[] packet = new int[]{
				0xFE, 0x04, 0x25, 0x05, 0x01, 0x00, 0x01, 0x00, 0x24
		};
		
		original = new ZDO_ACTIVE_EP_REQ(new ZToolAddress16(0x00, 0x01), new ZToolAddress16(0x00, 0x01));
		assertArrayEquals(packet, original.getPacket());
		
		original = new ZDO_ACTIVE_EP_REQ(0x0001);
		assertArrayEquals(packet, original.getPacket());
		
		original = new ZDO_ACTIVE_EP_REQ((short)0x0001);
		assertArrayEquals(packet, original.getPacket());		
	}

}
