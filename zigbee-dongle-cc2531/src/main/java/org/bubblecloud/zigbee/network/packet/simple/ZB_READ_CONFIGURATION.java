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

package org.bubblecloud.zigbee.network.packet.simple;

import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZB_READ_CONFIGURATION extends ZToolPacket /*implements IREQUEST,ISIMPLEAPI*/ {
    /// <name>TI.ZPI2.ZB_READ_CONFIGURATION.ConfigId</name>
    /// <summary>Identifier of property to read.</summary>
    public int ConfigId;

    /// <name>TI.ZPI2.ZB_READ_CONFIGURATION</name>
    /// <summary>Constructor</summary>
    public ZB_READ_CONFIGURATION() {
    }

    /// <name>TI.ZPI2.ZB_READ_CONFIGURATION</name>
    /// <summary>Constructor</summary>
    public ZB_READ_CONFIGURATION(int num1) {
        this.ConfigId = num1;
        int[] framedata = {num1};
        super.buildPacket(new DoubleByte(ZToolCMD.ZB_READ_CONFIGURATION), framedata);
    }

    public class CONFIG_ID {
        public static final int ZCD_NV_STARTUP_OPTION = 0x03;
        public static final int ZCD_NV_LOGICAL_TYPE = 0x87;
        public static final int ZCD_NV_POLL_RATE = 0x24;
        public static final int ZCD_NV_QEUED_POL_RATE = 0x25;
        public static final int ZCD_NV_RESPONSE_POLL_RATE = 0x26;
        public static final int ZCD_NV_POLL_FAILURE_RETRIES = 0x29;
        public static final int ZCD_NV_INDIRECT_MSG_TIMEOUT = 0x2B;
        public static final int ZCD_NV_APS_FRAME_RETRIES = 0x43;
        public static final int ZCD_NV_APS_ACK_WAIT_DURATION = 0x44;
        public static final int ZCD_NV_BINDING_TIME = 0x46;
        public static final int ZCD_NV_USERDESC = 0x81;
        public static final int ZCD_NV_PANID = 0x83;
        public static final int ZCD_NV_CHANLIST = 0x84;
        public static final int ZCD_NV_PRECFGKEY = 0x62;
        public static final int ZCD_NV_PRECFGKEYS_ENABLE = 0x63;
        public static final int ZCD_NV_SECURITY_MODE = 0x64;
        public static final int ZCD_NV_BCAST_RETRIES = 0x2E;
        public static final int ZCD_NV_PASSIVE_ACK_TIMEOUT = 0x2F;
        public static final int ZCD_NV_BCAST_DELIVERY_TIME = 0x30;
        public static final int ZCD_NV_ROUTE_EXPIRY_TIME = 0x2C;
    }

}
