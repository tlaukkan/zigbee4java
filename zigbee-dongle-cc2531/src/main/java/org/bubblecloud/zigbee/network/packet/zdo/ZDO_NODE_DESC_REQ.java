/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 

   Copyright 2008-2013 ITACA-TSB, http://www.tsb.upv.es/
   Instituto Tecnologico de Aplicaciones de Comunicacion 
   Avanzadas - Grupo Tecnologias para la Salud y el 
   Bienestar (TSB)


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
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.bubblecloud.zigbee.util.Integers;

/**
 * Requests the Node Descriptor.
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ZDO_NODE_DESC_REQ extends ZToolPacket /*implements IREQUEST,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_NODE_DESC_REQ.DstAddr</name>
    /// <summary>destination address</summary>
    public ZToolAddress16 DstAddr;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_REQ.NWKAddrOfInterest</name>
    /// <summary>NWK address for the request</summary>
    public ZToolAddress16 NWKAddrOfInterest;

    /// <name>TI.ZPI1.ZDO_NODE_DESC_REQ</name>
    /// <summary>Constructor</summary>
    public ZDO_NODE_DESC_REQ() {
    }

    public ZDO_NODE_DESC_REQ(int destination) {
        //TODO Check compatibility with other Constructor
        int[] framedata = new int[4];
        framedata[0] = Integers.getByteAsInteger(destination, 0);
        framedata[1] = Integers.getByteAsInteger(destination, 1);
        framedata[2] = framedata[0];
        framedata[3] = framedata[1];
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_NODE_DESC_REQ), framedata);
    }

    public ZDO_NODE_DESC_REQ(ZToolAddress16 num1, ZToolAddress16 num2) {
        this.DstAddr = num1;
        this.NWKAddrOfInterest = num2;

        int[] framedata = new int[4];
        framedata[0] = this.DstAddr.getLsb();
        framedata[1] = this.DstAddr.getMsb();
        framedata[2] = this.NWKAddrOfInterest.getLsb();
        framedata[3] = this.NWKAddrOfInterest.getMsb();
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_NODE_DESC_REQ), framedata);
    }

}
