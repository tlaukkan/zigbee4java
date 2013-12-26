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

import org.bubblecloud.zigbee.network.packet.ZToolAddress64;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZB_BIND_DEVICE extends ZToolPacket /*implements IREQUEST, ISIMPLEAPI*/ {
    /// <name>TI.ZPI2.ZB_BIND_DEVICE.Action</name>
    /// <summary>CREATE or DELETE a binding entry.</summary>
    public int Action;
    /// <name>TI.ZPI2.ZB_BIND_DEVICE.CommandId</name>
    /// <summary>The Command identifier of packets for this binding.</summary>
    public DoubleByte CommandId;
    /// <name>TI.ZPI2.ZB_BIND_DEVICE.Destination</name>
    /// <summary>IEEE address of device to establish the binding with ( all zeros indicate NULL ).</summary>
    public ZToolAddress64 Destination;

    /// <name>TI.ZPI2.ZB_BIND_DEVICE</name>
    /// <summary>Constructor</summary>
    public ZB_BIND_DEVICE() {
    }

    public ZB_BIND_DEVICE(int bind_action_type1, DoubleByte num1, ZToolAddress64 num2) {
        this.Action = bind_action_type1;
        this.CommandId = num1;
        this.Destination = num2;
        int[] framedata = new int[11];
        framedata[0] = this.Action;
        framedata[1] = this.CommandId.getLsb();
        framedata[2] = this.CommandId.getMsb();
        byte[] bytes = Destination.getAddress();
        for (int i = 0; i < 8; i++) {
            framedata[i + 2] = bytes[7 - i];
        }
        super.buildPacket(new DoubleByte(ZToolCMD.ZB_BIND_DEVICE), framedata);
    }

    /// <name>TI.ZPI2.ZB_BIND_DEVICE.BIND_ACTION_TYPE</name>
    /// <summary>Reset type</summary>
    public class BIND_ACTION_TYPE {
        /// <name>TI.ZPI2.ZB_BIND_DEVICE.BIND_ACTION_TYPE.CREATE_BIND</name>
        /// <summary>Reset type</summary>
        public static final int CREATE_BIND = 1;
        /// <name>TI.ZPI2.ZB_BIND_DEVICE.BIND_ACTION_TYPE.DELETE_BIND</name>
        /// <summary>Reset type</summary>
        public static final int DELETE_BIND = 0;
    }

}
