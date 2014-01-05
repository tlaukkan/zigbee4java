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

package org.bubblecloud.zigbee.network;

import java.util.Dictionary;

/**
 * This class represent a ZigBee node, it means a physical device that can communicate<br>
 * using the ZigBee protocol.<br>
 * Each physical may contain up 240 endpoints which are represented by the {@link ZigBeeEndpoint}<br>
 * class. Each endpoint is identified by an <i>EndPoint</i> address, but shares iether the:<br>
 * - <i>64-bit 802.15.4 IEEE Address</i><br>
 * - <i>16-bit ZigBee Network Address</i><br>
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @since 0.1.0
 */
public interface ZigBeeNode {

    /**
     * @return int representing the current network address linked to the node
     */
    public int getNetworkAddress();

    /**
     * @return a {@link String} representing the IEEEAddress of the node
     */
    public String getIEEEAddress();

}
