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

package org.bubblecloud.zigbee.network.impl;

import org.bubblecloud.zigbee.network.ClusterMessage;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ClusterMessageImpl implements ClusterMessage {

    private final byte[] msg;
    private final short id;


    public ClusterMessageImpl(byte[] msg, short id) {
        this.msg = msg;
        this.id = id;
    }

    public byte[] getClusterMsg() {
        return msg;
    }

    public short getId() {
        return id;
    }

    public String toString() {
		final StringBuilder builder = new StringBuilder("[ ");
		for (byte b : msg) {
			builder.append(String.format("%02X ", b));
		}
		builder.append("]");
		return builder.toString();
    }

}
