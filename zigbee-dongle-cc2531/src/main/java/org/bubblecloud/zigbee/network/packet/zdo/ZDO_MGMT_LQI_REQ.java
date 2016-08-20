/*
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

/**
 * This command is generated to request the destination device to return its neighbor table.
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZDO_MGMT_LQI_REQ extends ZToolPacket /*implements IREQUEST,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_MGMT_LQI_REQ.DstAddr</name>
    /// <summary>Destination network address.</summary>
    public ZToolAddress16 DstAddr;
    /// <name>TI.ZPI1.ZDO_MGMT_LQI_REQ.StartIndex</name>
    /// <summary>Where to start.  The result can be more networks than can be reported, so this field allows a user to ask for more.</summary>
    public int StartIndex;

    /// <name>TI.ZPI1.ZDO_MGMT_LQI_REQ</name>
    /// <summary>Constructor</summary>
    public ZDO_MGMT_LQI_REQ() {
    }

    /// <name>TI.ZPI1.ZDO_MGMT_LQI_REQ</name>
    /// <summary>Constructor</summary>
    public ZDO_MGMT_LQI_REQ(ZToolAddress16 num1, int num2) {
        this.DstAddr = num1;
        this.StartIndex = num2;

        int[] framedata = new int[3];
        framedata[0] = this.DstAddr.getLsb();
        framedata[1] = this.DstAddr.getMsb();
        framedata[2] = this.StartIndex;
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_MGMT_LQI_REQ), framedata);
    }
}
