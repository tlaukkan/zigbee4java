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
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZDO_NODE_DESC_RSP extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.APSFlags</name>
    /// <summary>Node Flags assigned for APS. For V1.0 all bits are reserved</summary>
    public int APSFlags;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.BufferSize</name>
    /// <summary>Indicates size of maximum NPDU. This field is used as a high level indication for api</summary>
    public int BufferSize;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.Capabilities</name>
    /// <summary>Capability flags stored for the MAC</summary>
    public int Capabilities;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.ComplexDescriptorAvailable</name>
    /// <summary>Indicates if complex descriptor is available for the node</summary>
    public int ComplexDescriptorAvailable;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.ManufacturerCode</name>
    /// <summary>Specifies a manufacturer code that is allocated by ZigBee Alliance, relating to the manufacturer to the device</summary>
    public DoubleByte ManufacturerCode;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.NodeType</name>
    /// <summary>Node type</summary>
    public int NodeType;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.NWKAddrOfInterest</name>
    /// <summary>Device's short address of this Node descriptor</summary>
    public ZToolAddress16 nwkAddr;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.ServerMask</name>
    /// <summary>Specifies the system server capability</summary>
    public int ServerMask;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SrcAddress</name>
    /// <summary>the message's source network address.</summary>
    public ZToolAddress16 SrcAddress;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.Status</name>
    /// <summary>this field indicates either SUCCESS or FAILURE.</summary>
    public int Status;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.TransferSize</name>
    /// <summary>Indicates maximum size of Transfer up to 0x7fff (This field is reserved in version 1.0 and shall be set to zero ).</summary>
    public DoubleByte TransferSize;
    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.UserDescriptorAvailable</name>
    /// <summary>Indicates if user descriptor is available for the node</summary>
    public int UserDescriptorAvailable;
    public int FreqBand;

    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP</name>
    /// <summary>Constructor</summary>
    public ZDO_NODE_DESC_RSP() {
    }

    public ZDO_NODE_DESC_RSP(int[] framedata) {
        this.SrcAddress = new ZToolAddress16(framedata[1], framedata[0]);
        this.Status = framedata[2];
        this.nwkAddr = new ZToolAddress16(framedata[4], framedata[3]);
        this.NodeType = framedata[5] & (0x07);///Experimental
        this.ComplexDescriptorAvailable = (framedata[5] & (0x08)) >>> 3;///Experimental
        this.UserDescriptorAvailable = (framedata[5] & (16)) >>> 4;///Experimental
        this.APSFlags = framedata[6] & (0x0F);
        this.FreqBand = framedata[6] & (0xF0) >>> 4;
        this.Capabilities = framedata[10];
        this.ManufacturerCode = new DoubleByte(framedata[12], framedata[11]);
        this.BufferSize = framedata[13];
        this.TransferSize = new DoubleByte(framedata[15], framedata[14]);
        this.ServerMask = new DoubleByte(framedata[17], framedata[16]).get16BitValue();
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_NODE_DESC_RSP), framedata);
    }

    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.CAPABILITY_INFO</name>
    /// <summary>Capability Information bitfield</summary>
    public class CAPABILITY_INFO {
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.CAPABILITY_INFO.ALLOCATE_ADDRESS</name>
        /// <summary>Capability Information bitfield</summary>
        public static final int ALLOCATE_ADDRESS = 0x80;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.CAPABILITY_INFO.ALTER_PAN_COORD</name>
        /// <summary>Capability Information bitfield</summary>
        public static final int ALTER_PAN_COORD = 1;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.CAPABILITY_INFO.DEVICE_TYPE</name>
        /// <summary>Capability Information bitfield</summary>
        public static final int DEVICE_TYPE = 2;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.CAPABILITY_INFO.NONE</name>
        /// <summary>Capability Information bitfield</summary>
        public static final int NONE = 0;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.CAPABILITY_INFO.POWER_SOURCE</name>
        /// <summary>Capability Information bitfield</summary>
        public static final int POWER_SOURCE = 4;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.CAPABILITY_INFO.RECEIVER_ON_WHEN_IDLE</name>
        /// <summary>Capability Information bitfield</summary>
        public static final int RECEIVER_ON_WHEN_IDLE = 8;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.CAPABILITY_INFO.SECURITY_CAPABILITY</name>
        /// <summary>Capability Information bitfield</summary>
        public static final int SECURITY_CAPABILITY = 0x40;
    }


    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.NODE_TYPE</name>
    /// <summary>Type of Node</summary>
    public class NODE_TYPE {
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.NODE_TYPE.COORDINATOR</name>
        /// <summary>Type of Node</summary>
        public static final int COORDINATOR = 0;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.NODE_TYPE.END_DEVICE</name>
        /// <summary>Type of Node</summary>
        public static final int END_DEVICE = 2;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.NODE_TYPE.ROUTER</name>
        /// <summary>Type of Node</summary>
        public static final int ROUTER = 1;
    }

    /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SERVER_CAPABILITY</name>
    /// <summary>Capabilities bitfield</summary>
    public class SERVER_CAPABILITY {
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SERVER_CAPABILITY.BACKUP_TRUST_CENTER</name>
        /// <summary>Capabilities bitfield</summary>
        public static final int BACKUP_TRUST_CENTER = 2;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SERVER_CAPABILITY.BAK_BIND_TABLE_CACHE</name>
        /// <summary>Capabilities bitfield</summary>
        public static final int BAK_BIND_TABLE_CACHE = 8;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SERVER_CAPABILITY.BAK_DISC_CACHE</name>
        /// <summary>Capabilities bitfield</summary>
        public static final int BAK_DISC_CACHE = 50;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SERVER_CAPABILITY.NONE</name>
        /// <summary>Capabilities bitfield</summary>
        public static final int NONE = 0;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SERVER_CAPABILITY.PRIM_BIND_TABLE_CACHE</name>
        /// <summary>Capabilities bitfield</summary>
        public static final int PRIM_BIND_TABLE_CACHE = 4;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SERVER_CAPABILITY.PRIM_DISC_CACHE</name>
        /// <summary>Capabilities bitfield</summary>
        public static final int PRIM_DISC_CACHE = 0x16;
        /// <name>TI.ZPI1.ZDO_NODE_DESC_RSP.SERVER_CAPABILITY.PRIM_TRUST_CENTER</name>
        /// <summary>Capabilities bitfield</summary>
        public static final int PRIM_TRUST_CENTER = 1;
    }

    @Override
    public String toString() {
        return "ZDO_NODE_DESC_RSP{" +
                "APSFlags=" + APSFlags +
                ", BufferSize=" + BufferSize +
                ", Capabilities=" + Capabilities +
                ", ComplexDescriptorAvailable=" + ComplexDescriptorAvailable +
                ", ManufacturerCode=" + ManufacturerCode +
                ", NodeType=" + NodeType +
                ", nwkAddr=" + nwkAddr +
                ", ServerMask=" + ServerMask +
                ", SrcAddress=" + SrcAddress +
                ", Status=" + ResponseStatus.getStatus(Status) +
                ", TransferSize=" + TransferSize +
                ", UserDescriptorAvailable=" + UserDescriptorAvailable +
                ", FreqBand=" + FreqBand +
                '}';
    }
}
