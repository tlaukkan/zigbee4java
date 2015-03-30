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

import org.bubblecloud.zigbee.util.DoubleByte;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class of {@link ZDO_SIMPLE_DESC_RSP}
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ZDO_SIMPLE_DESC_RSPTest {


	private ZDO_SIMPLE_DESC_RSP negative;
	private ZDO_SIMPLE_DESC_RSP order;
	int[] clusterList;

	@Before
	public void createdPacket(){
		if( order != null && negative != null)		
			return;
		
		clusterList = new int[]{
				0x1000, 0x0010, 0x8000, 0xFAF0
		};
		
		order = new ZDO_SIMPLE_DESC_RSP(new int[]{
				0x00, 0x10,										//16-bit Source Address
				0x00,											//Status
				0xF0, 0x10,										//16-bit Network Address
				0x18,											//Length
				0x10,											//End Point Address
				0x00, 0x10,										//Profile Id
				0x00, 0x10,										//Device Id
				0x10,											//Device Version
				0x04,											//Input Cluster Count
				0x00, 0x10, 0x10, 0x00, 0x00, 0x80, 0xF0, 0xFA, //Input Cluster List				
				0x04,											//Output Cluster Count
				0x00, 0x10, 0x10, 0x00, 0x00, 0x80, 0xF0, 0xFA, //Output Cluster List				
		});
		
		negative = new ZDO_SIMPLE_DESC_RSP(new int[]{
				0xF0, 0xFA,										//16-bit Source Address
				0x00,											//Status
				0xF0, 0xFA,										//16-bit Network Address
				0x18,											//Length
				0x80,											//End Point Address
				0xF0, 0xFA,										//Profile Id
				0xF0, 0xFA,										//Device Id
				0x80,											//Device Version
				0x04,											//Input Cluster Count
				0x00, 0x10, 0x10, 0x00, 0x00, 0x80, 0xF0, 0xFA, //Input Cluster List				
				0x04,											//Output Cluster Count
				0x00, 0x10, 0x10, 0x00, 0x00, 0x80, 0xF0, 0xFA, //Output Cluster List				
		});		
	}
	
	private void checkClusters(short[] list){		
		for (int i = 0; i < list.length; i++) {
			assertEquals(
					"Difference at "+i,
					(short) clusterList[i], list[i]
			);
			assertEquals(
					"Difference at "+i,
					clusterList[i], list[i] & 0xFFFF
			);
		}
		
	}
	
	private void checkClustersWithOld(short[] list, DoubleByte[] original){
		for (int i = 0; i < original.length; i++) {
			assertEquals(
					"Difference at "+i,
					(short) original[i].get16BitValue(), list[i]
			);
			assertEquals(
					"Difference at "+i,
					original[i].get16BitValue(), list[i] & 0xFFFF
			);
		}
	}
	
	@Test
	public void testGetInputClustersList() {
		checkClustersWithOld(order.getInputClustersList(), order.InClusterList);
		checkClustersWithOld(negative.getInputClustersList(), negative.InClusterList);
		checkClusters(order.getInputClustersList());
		checkClusters(negative.getInputClustersList());
	}

	@Test
	public void testGetOutputClustersList() {
		checkClustersWithOld(order.getOutputClustersList(), order.OutClusterList);
		checkClustersWithOld(negative.getOutputClustersList(), negative.OutClusterList);
		checkClusters(order.getOutputClustersList());
		checkClusters(negative.getOutputClustersList());
	}

	@Test
	public void testGetInputClustersCount() {
		assertEquals(4, order.getInputClustersCount());
		assertEquals(4, negative.getInputClustersCount());
	}


	@Test
	public void testGetOutputClustersCount() {
		assertEquals(4, order.getOutputClustersCount());
		assertEquals(4, negative.getOutputClustersCount());
	}

	@Test
	public void testGetEndPoint() {
		assertEquals(0x10, order.getEndPoint());
		assertEquals((byte) 0x80, negative.getEndPoint());
		assertEquals(0x80, negative.getEndPoint() & 0xFF);
	}
	
	@Test
	public void testGetDeviceVersion() {
		assertEquals(0x10, order.getDeviceVersion());
		assertEquals((byte) 0x80, negative.getDeviceVersion());
		assertEquals(0x80, negative.getDeviceVersion() & 0xFF);
	}

	@Test
	public void testGetProfileId() {
		assertEquals(0x1000, order.getProfileId());
		assertEquals((short) 0xFAF0, negative.getProfileId());
		assertEquals(0xFAF0, negative.getProfileId() & 0xFFFF);
	}

	@Test
	public void testGetDeviceId() {
		assertEquals(0x1000, order.getDeviceId());
		assertEquals((short) 0xFAF0, negative.getDeviceId());
		assertEquals(0xFAF0, negative.getDeviceId() & 0xFFFF);
	}


}
