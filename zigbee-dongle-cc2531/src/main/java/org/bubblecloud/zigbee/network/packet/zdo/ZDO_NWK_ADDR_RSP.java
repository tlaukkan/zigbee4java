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

import org.bubblecloud.zigbee.network.packet.*;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.bubblecloud.zigbee.v3.model.ZToolAddress16;
import org.bubblecloud.zigbee.v3.model.ZToolAddress64;

import java.util.Arrays;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZDO_NWK_ADDR_RSP extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.AssocDevList</name>
    /// <summary>Dynamic array, array of 16 bit short addresses - list of network address for associated devices.  This list can be a partial list if the entire list doesn't fit into a packet.  If it is a partial list, the starting index is StartIndex.</summary>
    public ZToolAddress16[] AssocDevList;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.IEEEAddr</name>
    /// <summary>64 bit IEEE address of source device</summary>
    public ZToolAddress64 IEEEAddr;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.NumAssocDev</name>
    /// <summary>number of associated devices</summary>
    public int NumAssocDev;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.nwkAddr</name>
    /// <summary>short network address of responding device</summary>
    public ZToolAddress16 nwkAddr;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.SrcAddress</name>
    /// <summary>Source address, size is dependent on SrcAddrMode</summary>
    public ZToolAddress64 SrcAddress;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.StartIndex</name>
    /// <summary>Starting index into the list of associated devices for this report.</summary>
    public int StartIndex;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.Status</name>
    /// <summary>this field indicates either SUCCESS or FAILURE</summary>
    public int Status;

    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP</name>
    /// <summary>Constructor</summary>
    public ZDO_NWK_ADDR_RSP() {
        this.AssocDevList = new ZToolAddress16[0xff];
    }

    public ZDO_NWK_ADDR_RSP(int[] framedata) {
        ///WARNING: variable length.
        ///resulting SrcAddress is 8 bytes but serialized is either 2 or 8 depending on Mode
        this.Status = framedata[0];
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) framedata[8 - i];
        }
        this.IEEEAddr = new ZToolAddress64(bytes);
        this.nwkAddr = new ZToolAddress16(framedata[10], framedata[9]);
        this.StartIndex = framedata[11];
        this.NumAssocDev = framedata[12];
        this.AssocDevList = new ZToolAddress16[this.NumAssocDev];
        for (int i = 0; i < this.AssocDevList.length; i++) {
            this.AssocDevList[i] = new ZToolAddress16(framedata[14 + (i * 2)], framedata[13 + (i * 2)]);
        }
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_NWK_ADDR_RSP), framedata);
    }

    @Override
    public String toString() {
        return "ZDO_NWK_ADDR_RSP{" +
                "AssocDevList=" + Arrays.toString(AssocDevList) +
                ", IEEEAddr=" + IEEEAddr +
                ", NumAssocDev=" + NumAssocDev +
                ", nwkAddr=" + nwkAddr +
                ", SrcAddress=" + SrcAddress +
                ", StartIndex=" + StartIndex +
                ", Status=" + ResponseStatus.getStatus(Status) +
                '}';
    }
}
