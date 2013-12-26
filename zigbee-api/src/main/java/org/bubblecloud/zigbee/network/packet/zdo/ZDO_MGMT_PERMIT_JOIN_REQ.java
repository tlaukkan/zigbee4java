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

import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZDO_MGMT_PERMIT_JOIN_REQ extends ZToolPacket /*implements IREQUEST,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_MGMT_PERMIT_JOIN_REQ.DstAddr</name>
    /// <summary>Destination network address.</summary>
    public ZToolAddress16 DstAddr;
    /// <name>TI.ZPI1.ZDO_MGMT_PERMIT_JOIN_REQ.Duration</name>
    /// <summary>The duration to permit joining.  0 = join disabled.  0xff = join enabled. 0x01-0xfe = number of seconds to permit joining</summary>
    public int Duration;
    /// <name>TI.ZPI1.ZDO_MGMT_PERMIT_JOIN_REQ.TCSignificance</name>
    /// <summary>Trust Center Significance</summary>
    public int TCSignificance;

    /// <name>TI.ZPI1.ZDO_MGMT_PERMIT_JOIN_REQ</name>
    /// <summary>Constructor</summary>
    public ZDO_MGMT_PERMIT_JOIN_REQ() {
    }

    /// <name>TI.ZPI1.ZDO_MGMT_PERMIT_JOIN_REQ</name>
    /// <summary>Constructor</summary>
    public ZDO_MGMT_PERMIT_JOIN_REQ(ZToolAddress16 num1, int num2, int num3) {
        this.DstAddr = num1;
        this.Duration = num2;
        this.TCSignificance = num3;

        int[] framedata = new int[4];
        framedata[0] = this.DstAddr.getLsb();
        framedata[1] = this.DstAddr.getMsb();
        framedata[2] = this.Duration;
        framedata[3] = this.TCSignificance;
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_MGMT_PERMIT_JOIN_REQ), framedata);
    }
}
