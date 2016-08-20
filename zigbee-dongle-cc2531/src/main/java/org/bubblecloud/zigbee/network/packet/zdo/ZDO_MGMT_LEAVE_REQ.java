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
import org.bubblecloud.zigbee.v3.model.ZToolAddress64;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * This command is generated to request a Management Leave Request for the target device and is used to remove
 * devices from the network.
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZDO_MGMT_LEAVE_REQ extends ZToolPacket /*implements IREQUEST,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_MGMT_LEAVE_REQ.DeviceAddress</name>
    /// <summary>The 64 bit IEEE Address of the device you want to leave.</summary>
    public ZToolAddress64 DeviceAddress;
    /// <name>TI.ZPI1.ZDO_MGMT_LEAVE_REQ.DstAddr</name>
    /// <summary>Destination network address.</summary>
    public ZToolAddress16 DstAddr;
    /// <name>TI.ZPI1.ZDO_MGMT_LEAVE_REQ.RemoveChildren</name>
    /// <summary>This field has a value of 1 if the device being asked to leave the network is also being asked to remove its child devices, if any. Otherwise it has a value of 0. Currently, the stack profile of Home Control specifies that this field should always be set to 0</summary>
    public int RemoveChildren_Rejoin;

    /// <name>TI.ZPI1.ZDO_MGMT_LEAVE_REQ</name>
    /// <summary>Constructor</summary>
    public ZDO_MGMT_LEAVE_REQ() {
    }

    public ZDO_MGMT_LEAVE_REQ(ZToolAddress16 num1, ZToolAddress64 num2, int flag1) {
        this.DstAddr = num1;
        this.DeviceAddress = num2;
        this.RemoveChildren_Rejoin = flag1;

        int[] framedata = new int[11];
        framedata[0] = this.DstAddr.getLsb();
        framedata[1] = this.DstAddr.getMsb();
        for (int i = 0; i < 8; i++) {
            framedata[2 + i] = this.DeviceAddress.getAddress()[7 - i];
        }
        framedata[10] = this.RemoveChildren_Rejoin;
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_MGMT_LEAVE_REQ), framedata);
    }
}
