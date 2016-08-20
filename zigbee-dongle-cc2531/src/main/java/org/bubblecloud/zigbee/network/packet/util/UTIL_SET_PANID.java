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
package org.bubblecloud.zigbee.network.packet.util;

import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 */
public class UTIL_SET_PANID extends ZToolPacket/* implements IREQUEST,ISYSTEM*/ {
    /// <name>TI.ZPI1.SYS_SET_PANID.PanID</name>
    /// <summary>PanID.</summary>
    public DoubleByte PanID;

    /// <name>TI.ZPI1.SYS_SET_PANID</name>
    /// <summary>Constructor</summary>
    public UTIL_SET_PANID() {
    }

    /// <name>TI.ZPI1.SYS_SET_PANID</name>
    /// <summary>Constructor</summary>
    public UTIL_SET_PANID(DoubleByte num1) {
        this.PanID = num1;

        int[] framedata = new int[1];
        framedata[0] = this.PanID.getMsb();
        framedata[1] = this.PanID.getLsb();

        super.buildPacket(new DoubleByte(ZToolCMD.UTIL_SET_PANID), framedata);
    }

    public UTIL_SET_PANID(int[] framedata) {
        this.PanID = new DoubleByte(framedata[1], framedata[0]);

        super.buildPacket(new DoubleByte(ZToolCMD.UTIL_SET_PANID), framedata);
    }
}
