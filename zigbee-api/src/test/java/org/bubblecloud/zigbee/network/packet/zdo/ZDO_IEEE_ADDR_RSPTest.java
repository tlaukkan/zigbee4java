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

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test class of {@link ZDO_IEEE_ADDR_RSP}
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ZDO_IEEE_ADDR_RSPTest {
	
	@Test
	public void testGetIEEEAddress() {
		ZDO_IEEE_ADDR_RSP response = new ZDO_IEEE_ADDR_RSP(new int[]{
				0x00,											//Status
				0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, //64-bit Address
				0xF0, 0xFA, 									//16-bit Address
				0x00,											//Start Index
				0x03,											//Associated Device List Count
				0xF0, 0xFB, 0x80, 0x8F, 0x10, 0x01				//Associated Device List				
		});
		
		assertArrayEquals(response.getIEEEAddress().getAddress(), response.IEEEAddr.getAddress());
	}

	@Test
	public void testGetAssociatedDeviceList() {
		ZDO_IEEE_ADDR_RSP response = new ZDO_IEEE_ADDR_RSP(new int[]{
				0x00,											//Status
				0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, //64-bit Address
				0xF0, 0xFA, 									//16-bit Address
				0x00,											//Start Index
				0x03,											//Associated Device List Count
				0xF0, 0xFB, 0x80, 0x8F, 0x10, 0x01				//Associated Device List				
		});
		
		short[] devs = response.getAssociatedDeviceList();
		for (int i = 0; i < devs.length; i++) {
			assertEquals(devs[i] & 0xFFFF, response.AssocDevList[i].get16BitValue());
		}
		for (int i = 0; i < devs.length; i++) {
			assertEquals(devs[i], (short) response.AssocDevList[i].get16BitValue());
		}
	}

	@Test
	public void testGetStartIndex() {
		ZDO_IEEE_ADDR_RSP response = new ZDO_IEEE_ADDR_RSP(new int[]{
				0x00,											//Status
				0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, //64-bit Address
				0xF0, 0xFA, 									//16-bit Address
				0x00,											//Start Index
				0x03,											//Associated Device List Count
				0xF0, 0xFB, 0x80, 0x8F, 0x10, 0x01				//Associated Device List				
		});
		
		assertEquals(response.getStartIndex(), response.StartIndex);
		assertEquals(response.getStartIndex(), 0);
	}

	@Test
	public void testGetAssociatedDeviceCount() {
		ZDO_IEEE_ADDR_RSP response = new ZDO_IEEE_ADDR_RSP(new int[]{
				0x00,											//Status
				0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, //64-bit Address
				0xF0, 0xFA, 									//16-bit Address
				0x00,											//Start Index
				0x03,											//Associated Device List Count
				0xF0, 0xFB, 0x80, 0x8F, 0x10, 0x01				//Associated Device List				
		});
		
		
		short[] devs = response.getAssociatedDeviceList();
		assertEquals(devs.length, response.AssocDevList.length);
	}

}
