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

import org.bubblecloud.zigbee.network.packet.ResponseStatus;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author alfiva
 */
public class ZDO_STARTUP_FROM_APP_SRSP extends ZToolPacket /*implements IRESPONSE,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_STARTUP_FROM_APP_SRSP.Status</name>
    /// <summary>Status</summary>
    public int Status;

    /// <name>TI.ZPI1.ZDO_STARTUP_FROM_APP_SRSP</name>
    /// <summary>Constructor</summary>
    public ZDO_STARTUP_FROM_APP_SRSP() {
    }

    public ZDO_STARTUP_FROM_APP_SRSP(int[] framedata) {
        this.Status = framedata[0];
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_STARTUP_FROM_APP_SRSP), framedata);
    }

    @Override
    public String toString() {
        return "ZDO_STARTUP_FROM_APP_SRSP{" +
                "Status=" + ResponseStatus.getStatus(Status) +
                '}';
    }
    
    public class STATUS_TYPE {
        public static final int RESTORED_NETWORK = 0;
        public static final int NEW_NETWORK = 1;
        public static final int NOT_STARTED = 2;        
    }
}
