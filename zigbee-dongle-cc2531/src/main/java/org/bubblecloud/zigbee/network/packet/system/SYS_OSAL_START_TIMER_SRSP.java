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

package org.bubblecloud.zigbee.network.packet.system;

import org.bubblecloud.zigbee.network.packet.ResponseStatus;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class SYS_OSAL_START_TIMER_SRSP extends ZToolPacket /*implements IRESPONSE,ISYSTEM*/ {
    /// <name>TI.ZPI2.SYS_OSAL_START_TIMER_SRSP.Status</name>
    /// <summary>Status</summary>
    public int Status;

    /// <name>TI.ZPI2.SYS_OSAL_START_TIMER_SRSP</name>
    /// <summary>Constructor</summary>
    public SYS_OSAL_START_TIMER_SRSP() {
    }

    public SYS_OSAL_START_TIMER_SRSP(int[] framedata) {
        this.Status = framedata[0];
        super.buildPacket(new DoubleByte(ZToolCMD.SYS_OSAL_START_TIMER_SRSP), framedata);
    }

    @Override
    public String toString() {
        return "SYS_OSAL_START_TIMER_SRSP{" +
                "Status=" + ResponseStatus.getStatus(Status) +
                '}';
    }
}
