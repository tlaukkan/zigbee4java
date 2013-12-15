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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class of {@link ZDO_ACTIVE_EP_RSP}
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ZDO_ACTIVE_EP_RSPTest {

	@Test
	public void testGetActiveEndPointList() {
		ZDO_ACTIVE_EP_RSP response = new ZDO_ACTIVE_EP_RSP(new int[]{
			0xF0, 0xFA,						// Source Address 
			0x00,							// Status
			0xFB, 0xFC,						// Network Address
			0x05,							// Active End Point Count
			0x80, 0x8F, 0x10, 0x20, 0xF0	// Active End Point List
		});
		byte[] list = response.getActiveEndPointList();
		assertEquals(list.length, response.ActiveEndpointCount);
		for (int i = 0; i < list.length; i++) {
			assertEquals(list[i] & 0xFF, response.ActiveEndpointList[i]);
		}
		for (int i = 0; i < list.length; i++) {
			assertEquals(list[i], (byte) response.ActiveEndpointList[i]);
		}
	}

}
