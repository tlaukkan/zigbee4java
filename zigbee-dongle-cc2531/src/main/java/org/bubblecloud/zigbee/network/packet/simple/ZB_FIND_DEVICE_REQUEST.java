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
import org.bubblecloud.zigbee.util.ByteUtils;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * This command is used to determine the short address for a device in the network.
 * The device initiating a call to ZB_FIND_DEVICE_REQUEST and the device being discovered
 * must both be a member of the same network. When the search is complete, the ZNP responds
 * with ZB_FIND_DEVICE_CONFIRM.
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZB_FIND_DEVICE_REQUEST extends ZToolPacket /*implements IREQUEST,ISIMPLEAPI*/ {
    /// <name>TI.ZPI2.ZB_FIND_DEVICE_REQUEST.SearchKey</name>
    /// <summary>IEEE address of the device to find.</summary>
    public long SearchKey;

    /// <name>TI.ZPI2.ZB_FIND_DEVICE_REQUEST</name>
    /// <summary>Constructor</summary>
    public ZB_FIND_DEVICE_REQUEST() {
    }

    /// <name>TI.ZPI2.ZB_FIND_DEVICE_REQUEST</name>
    /// <summary>Constructor</summary>
    public ZB_FIND_DEVICE_REQUEST(long num1) {
        this.SearchKey = num1;
        int[] framedata = ByteUtils.convertLongtoMultiByte(this.SearchKey);
        super.buildPacket(new DoubleByte(ZToolCMD.ZB_FIND_DEVICE_REQUEST), framedata);
    }

}
