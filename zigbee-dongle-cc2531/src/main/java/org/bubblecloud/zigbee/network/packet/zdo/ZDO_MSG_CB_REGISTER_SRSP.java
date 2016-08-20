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

import org.bubblecloud.zigbee.network.packet.ResponseStatus;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:ryan@presslab.us">Ryan Press</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2015-03-09 19:00:05 +0300 (Mon, 09 Mar 2015) $)
 */
public class ZDO_MSG_CB_REGISTER_SRSP extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    /// <name>TI.ZPI2.ZDO_MSG_CB_REGISTER_SRSP.Status</name>
    /// <summary>this field indicates either SUCCESS or FAILURE.</summary>
    public int Status;
    /// <name>TI.ZPI2.ZDO_MSG_CB_REGISTER_SRSP</name>
    /// <summary>Constructor</summary>
    public ZDO_MSG_CB_REGISTER_SRSP() {
    }

    public ZDO_MSG_CB_REGISTER_SRSP(int[] framedata) {
        this.Status = framedata[0];
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_MSG_CB_REGISTER_SRSP), framedata);
    }

    @Override
    public String toString() {
        return "ZDO_MSG_CB_REGISTER_SRSP{" +
                "Status=" + ResponseStatus.getStatus(Status) +
                '}';
    }
}
