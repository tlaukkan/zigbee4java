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

package it.cnr.isti.zigbee.api;

import java.util.Dictionary;

/**
 * This class represent a ZigBee node, it means a physical device that can communicate<br> 
 * using the ZigBee protocol.<br>
 * Each physical may contain up 240 logical devices which are represented by the {@link ZigBeeDevice}<br>
 * class. Each logical device is identified by an <i>EndPoint</i> address, but shares iether the:<br>
 *  - <i>64-bit 802.15.4 IEEE Address</i><br>
 *  - <i>16-bit ZigBee Network Address</i><br>
 *  
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @since 0.1.0
 */
public interface ZigBeeNode {
	
	public final static String IEEE_ADDRESS = "zigbee.node.address.ieee";
	public final static String MAC_ADDRESS = IEEE_ADDRESS;
	
	public final static String NWK_ADDRESS = "zigbee.node.address.nwk";
	public final static String END_POINTS = "zigbee.node.endpoints";
	public final static String PAN_ID = "zigbee.node.pan.id";
	
	public final static String NODE_TYPE = "zigbee.node.description.node.type";
	public final static String NODE_FREQ_BAND = "zigbee.node.description.node.freq";
	public final static String NODE_MANUFACTURER = "zigbee.node.description.manufacturer";
	public final static String NODE_CAPABILITIES_ZIGBEE = "zigbee.node.description.capabilities.zigbee";
	public final static String NODE_CAPABILITIES_MAC = "zigbee.node.description.capabilities.mac";
	
	public final static String POWER_CURRENT_MODE = "zigbee.node.description.power.mode.current";
	public final static String POWER_AVAILABLE_MODE = "zigbee.node.description.power.mode.available";
	public final static String POWER_CURRENT_SOURCE = "zigbee.node.description.power.source.current";
	public final static String POWER_AVAILABLE_SOURCE = "zigbee.node.description.power.source.available";
		
	/**
	 * 
	 * @return int representing the current network address linked to the device
	 */
	public int getNetworkAddress();
	
	/**
	 * //TODO Define a ZigBeeAddress object
	 *  
	 * @return a {@link String} representing the IEEEAddress of the device
	 */
	public String getIEEEAddress();
	
	/**
	 * 
	 * @return
	 */
	public Dictionary getDescription();
	
//TODO Identify an OSGi compatible way to expose the method below. A solution could be to return ServiceReference instead of the Service itself	
//	/**
//	 * 
//	 * @return a list of all the End Point implemented by this ZigBee device
//	 */
//	public ZigBeeDevice[] getDevices();
//	
//	/**
//	 * @param id End Point identifier
//	 * @return the {@link ZigBeeDevice} identified by the given id or <code>null</code><br>
//	 * 		if no present
//	 */
//	public ZigBeeDevice getDevices(short id);

}
