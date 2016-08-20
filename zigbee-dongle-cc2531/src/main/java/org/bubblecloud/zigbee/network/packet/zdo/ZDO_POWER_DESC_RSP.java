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
import org.bubblecloud.zigbee.v3.model.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * Processes the response of the Power Descriptor. Only a single Power Descriptor
 * is available per node.
 * <p>
 * The node power descriptor gives a dynamic indication of the power status of
 * the node and is mandatory for each node. There shall be only one node
 * power descriptor in a node.
 * 
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 */
public class ZDO_POWER_DESC_RSP extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.NWKAddrOfInterest</name>
    /// <summary>Device's short address of this Node descriptor</summary>
    public ZToolAddress16 nwkAddr;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SrcAddress</name>
    /// <summary>the message's source network address.</summary>
    public ZToolAddress16 SrcAddress;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.Status</name>
    /// <summary>this field indicates either SUCCESS or FAILURE.</summary>
    public int Status;

    // TODO: Add doc!
    public int CurrentMode;
    public int AvailableSources;
    public int CurrentSource;
    public int CurrentLevel;
    
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP</name>
    /// <summary>Constructor</summary>
    public ZDO_POWER_DESC_RSP() {
    }

    public ZDO_POWER_DESC_RSP(int[] framedata) {
        this.SrcAddress = new ZToolAddress16(framedata[1], framedata[0]);
        this.Status = framedata[2];
        this.nwkAddr = new ZToolAddress16(framedata[4], framedata[3]);
        this.CurrentMode = (framedata[5] & (0x0F));
        this.AvailableSources = (framedata[5] & (0xF0)) >> 4;
        this.CurrentSource = (framedata[6] & (0x0F));
        this.CurrentLevel = (framedata[6] & (0xF0)) >> 4;

        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_POWER_DESC_RSP), framedata);
    }

    @Override
    public String toString() {
        return "ZDO_NODE_DESC_RSP{" +
                "nwkAddr=" + nwkAddr +
                ", SrcAddress=" + SrcAddress +
                ", Status=" + ResponseStatus.getStatus(Status) +
                ", CurrentMode=" + CurrentMode +
                ", AvailableSources=" + AvailableSources +
                ", CurrentSource=" + CurrentSource +
                ", CurrentLevel=" + CurrentLevel +
                "}";
    }
}
