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

package org.bubblecloud.zigbee.packet.zdo;

import org.bubblecloud.zigbee.packet.ZToolAddress16;
import org.bubblecloud.zigbee.packet.ZToolCMD;
import org.bubblecloud.zigbee.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZDO_END_DEVICE_BIND_RSP extends ZToolPacket /*implements IRESPONE_CALLBACK,IZDO*/{
    /// <name>TI.ZPI1.ZDO_END_DEVICE_BIND_RSP.SrcAddress</name>
        /// <summary>the message's source network address</summary>
        public ZToolAddress16 SrcAddress;
        /// <name>TI.ZPI1.ZDO_END_DEVICE_BIND_RSP.Status</name>
        /// <summary>this field indicates status of the bind request</summary>
        public int Status;

        /// <name>TI.ZPI1.ZDO_END_DEVICE_BIND_RSP</name>
        /// <summary>Constructor</summary>
        public ZDO_END_DEVICE_BIND_RSP()
        {
        }

        public ZDO_END_DEVICE_BIND_RSP(int[] framedata)
        {
            this.SrcAddress=new ZToolAddress16(framedata[1],framedata[0]);
            this.Status = framedata[2];
            super.buildPacket(new DoubleByte(ZToolCMD.ZDO_END_DEVICE_BIND_RSP), framedata);
        }

        /// <name>TI.ZPI1.ZDO_END_DEVICE_BIND_RSP.ED_BIND_RESPONSE_STATUS</name>
        /// <summary>status code for End Device bind Response</summary>
        public class ED_BIND_RESPONSE_STATUS 
        {
            /// <name>TI.ZPI1.ZDO_END_DEVICE_BIND_RSP.ED_BIND_RESPONSE_STATUS.NO_MATCH</name>
            /// <summary>status code for End Device bind Response</summary>
            public static final int NO_MATCH = 3;
            /// <name>TI.ZPI1.ZDO_END_DEVICE_BIND_RSP.ED_BIND_RESPONSE_STATUS.NOT_SUPPORTED</name>
            /// <summary>status code for End Device bind Response</summary>
            public static final int NOT_SUPPORTED = 1;
            /// <name>TI.ZPI1.ZDO_END_DEVICE_BIND_RSP.ED_BIND_RESPONSE_STATUS.SUCCESS</name>
            /// <summary>status code for End Device bind Response</summary>
            public static final int SUCCESS = 0;
            /// <name>TI.ZPI1.ZDO_END_DEVICE_BIND_RSP.ED_BIND_RESPONSE_STATUS.TIMEOUT</name>
            /// <summary>status code for End Device bind Response</summary>
            public static final int TIMEOUT = 2;
        }

}
