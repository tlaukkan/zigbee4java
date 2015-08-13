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

package org.bubblecloud.zigbee.network.packet.af;

import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.ByteUtils;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This callback message is in response to incoming data to any of the registered endpoints on this device.
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class AF_INCOMING_MSG extends ZToolPacket /*implements IINDICATION,IAF*/ {

    private final static Logger profiler = LoggerFactory.getLogger("profiling." + AF_INCOMING_MSG.class.getName());

    /// <name>TI.ZPI2.AF_INCOMING_MSG.ClusterID</name>
    /// <summary>specifies the cluster PROFILE_ID_HOME_AUTOMATION</summary>
    @Deprecated
    public DoubleByte ClusterID;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.Data</name>
    /// <summary>Dynamic array, variable length field of size 'Len' and is the transaction data frame</summary>
    @Deprecated
    public int[] Data;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.DstEndpoint</name>
    /// <summary>specifies the endpoint of the destination device</summary>
    @Deprecated
    public int DstEndpoint;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.GroupID</name>
    /// <summary>Group PROFILE_ID_HOME_AUTOMATION</summary>
    @Deprecated
    public DoubleByte GroupID;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.Len</name>
    /// <summary>specifies the length of the Data field</summary>
    @Deprecated
    public int Len;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.LinkQuality</name>
    /// <summary>indicates the link quality measured during reception. TBD</summary>
    @Deprecated
    public int LinkQuality;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.SecurityUse</name>
    /// <summary>indicates the type of security applied for the incoming NPDU. This field is no longer used.</summary>
    @Deprecated
    public int SecurityUse;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.SrcAddr</name>
    /// <summary>specifies the address of the source device</summary>
    @Deprecated
    public ZToolAddress16 SrcAddr;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.SrcEndpoint</name>
    /// <summary>specifies the endpoint of the source device</summary>
    @Deprecated
    public int SrcEndpoint;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.Timestamp</name>
    /// <summary>Timestamp</summary>
    @Deprecated
    public long Timestamp;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.TransSeqNumber</name>
    /// <summary>specifies the transaction Id of the device</summary>
    @Deprecated
    public int TransSeqNumber;
    /// <name>TI.ZPI2.AF_INCOMING_MSG.WasBroadcast</name>
    /// <summary>WasBroadcast</summary>
    @Deprecated
    public int WasBroadcast;
    private byte[] payload;

    /// <name>TI.ZPI2.AF_INCOMING_MSG</name>
    /// <summary>Constructor</summary>
    public AF_INCOMING_MSG() {
        this.Data = new int[0xff];
    }

    public AF_INCOMING_MSG(int[] framedata) {
        profiler.debug("AF_INCOMING_MSG: creating object");
        this.GroupID = new DoubleByte(framedata[1], framedata[0]);
        this.ClusterID = new DoubleByte(framedata[3], framedata[2]);
        this.SrcAddr = new ZToolAddress16(framedata[5], framedata[4]);
        this.SrcEndpoint = framedata[6];
        this.DstEndpoint = framedata[7];
        this.WasBroadcast = framedata[8];
        this.LinkQuality = framedata[9];
        this.SecurityUse = framedata[10];
        byte[] bytes = new byte[4];
        bytes[3] = (byte) framedata[11];
        bytes[2] = (byte) framedata[12];
        bytes[1] = (byte) framedata[13];
        bytes[0] = (byte) framedata[14];
        this.Timestamp = ByteUtils.convertMultiByteToLong(bytes);
        this.TransSeqNumber = framedata[15];
        this.Len = framedata[16];
        this.Data = new int[this.Len];
        for (int i = 0; i < this.Data.length; i++) {
            this.Data[i] = framedata[17 + i];
        }
        super.buildPacket(new DoubleByte(ZToolCMD.AF_INCOMING_MSG), framedata);
        profiler.debug("AF_INCOMING_MSG: object created");
    }

    /// <name>TI.ZPI2.AF_INCOMING_MSG.SECURITY_STATUS</name>
    /// <summary>Security status</summary>
    public class SECURITY_STATUS {
        /// <name>TI.ZPI2.AF_INCOMING_MSG.SECURITY_STATUS.ENABLED</name>
        /// <summary>Security status</summary>
        public static final int ENABLED = 1;
        /// <name>TI.ZPI2.AF_INCOMING_MSG.SECURITY_STATUS.NOT_ENABLED</name>
        /// <summary>Security status</summary>
        public static final int NOT_ENABLED = 0;
    }

    public byte getTransId() {
        return (byte) (super.packet[ZToolPacket.PAYLOAD_START_INDEX + 15]);
    }

    public byte getSrcEndpoint() {
        return (byte) super.packet[ZToolPacket.PAYLOAD_START_INDEX + 6];
    }

    public short getDstEndpoint() {
        return (byte) super.packet[ZToolPacket.PAYLOAD_START_INDEX + 7];
    }

    public int getSrcAddr() {
        return (int) ((super.packet[ZToolPacket.PAYLOAD_START_INDEX + 5] << 8)
                + (super.packet[ZToolPacket.PAYLOAD_START_INDEX + 4]));
    }

    public short getClusterId() {
        return (short) ((super.packet[ZToolPacket.PAYLOAD_START_INDEX + 3] << 8)
                + (super.packet[ZToolPacket.PAYLOAD_START_INDEX + 2]));
    }

    public short getGroupId() {
        return (short) ((super.packet[ZToolPacket.PAYLOAD_START_INDEX + 1] << 8)
                + (super.packet[ZToolPacket.PAYLOAD_START_INDEX + 0]));
    }

    public byte[] getData() {
        if (payload == null) {
            payload = new byte[Data.length];
            for (int i = 0; i < payload.length; i++) {
                payload[i] = (byte) Data[i];
            }

        }
        return payload;
    }

}
