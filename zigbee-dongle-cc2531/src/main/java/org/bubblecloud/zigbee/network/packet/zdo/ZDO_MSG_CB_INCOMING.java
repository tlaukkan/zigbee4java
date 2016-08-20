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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bubblecloud.zigbee.v3.model.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:ryan@presslab.us">Ryan Press</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2015-03-09 19:00:05 +0300 (Mon, 09 Mar 2015) $)
 */
public class ZDO_MSG_CB_INCOMING extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    private final static Logger logger = LoggerFactory.getLogger(ZDO_MSG_CB_INCOMING.class);

    static final Map<Integer, Class<? extends ZToolPacket>> clusterToRSP;
    static {
        final Map<Integer, Class<? extends ZToolPacket>> build = new HashMap<Integer, Class<? extends ZToolPacket>>();
        build.put(0x0013, ZDO_END_DEVICE_ANNCE_IND.class);
        build.put(0x8000, ZDO_NWK_ADDR_RSP.class);
        build.put(0x8001, ZDO_IEEE_ADDR_RSP.class);
        build.put(0x8002, ZDO_NODE_DESC_RSP.class);
        build.put(0x8003, ZDO_POWER_DESC_RSP.class);
        build.put(0x8004, ZDO_SIMPLE_DESC_RSP.class);
        build.put(0x8005, ZDO_ACTIVE_EP_RSP.class);
        build.put(0x8006, ZDO_MATCH_DESC_RSP.class);
        build.put(0x8011, ZDO_USER_DESC_RSP.class);
        build.put(0x8014, ZDO_USER_DESC_CONF.class);
        build.put(0x8020, ZDO_END_DEVICE_BIND_RSP.class);
        build.put(0x8021, ZDO_BIND_RSP.class);
        build.put(0x8022, ZDO_UNBIND_RSP.class);
        build.put(0x8031, ZDO_MGMT_LQI_RSP.class);
        build.put(0x8034, ZDO_MGMT_LEAVE_RSP.class);
        build.put(0x8036, ZDO_MGMT_PERMIT_JOIN_RSP.class);
        clusterToRSP = Collections.unmodifiableMap(build);
    }

    int FCS;

    /// <name>TI.ZPI2.ZDO_MSG_CB_INCOMING.SrcAddr</name>
    /// <summary>Short address (LSB-MSB) of the source of the ZDO message.</summary>
    public ZToolAddress16 SrcAddr;
    /// <name>TI.ZPI2.ZDO_MSG_CB_INCOMING.WasBroadcast</name>
    /// <summary>This field indicates whether or not this ZDO message was broadcast.</summary>
    public int WasBroadcast;
    /// <name>TI.ZPI2.ZDO_MSG_CB_INCOMING.ClusterId</name>
    /// <summary>The ZDO Cluster Id of this message.</summary>
    public DoubleByte ClusterId;
    /// <name>TI.ZPI2.ZDO_MSG_CB_INCOMING.SecurityUse</name>
    /// <summary>N/A - not used.</summary>
    public int SecurityUse;
    /// <name>TI.ZPI2.ZDO_MSG_CB_INCOMING.SeqNum</name>
    /// <summary>The sequence number of this ZDO message.</summary>
    public int SeqNum;
    /// <name>TI.ZPI2.ZDO_MSG_CB_INCOMING.MacDstAddr</name>
    /// <summary>TThe MAC destination short address (LSB-MSB) of the ZDO message.</summary>
    public ZToolAddress16 MacDstAddr;
    /// <name>TI.ZPI2.ZDO_MSG_CB_INCOMING.Data</name>
    /// <summary>The data that corresponds to the Cluster Id of the message.</summary>
    public int[] Data;

    /// <name>TI.ZPI2.ZDO_MSG_CB_INCOMING</name>
    /// <summary>Constructor</summary>
    public ZDO_MSG_CB_INCOMING() {
    }

    public ZDO_MSG_CB_INCOMING(int[] framedata) {
        this.SrcAddr = new ZToolAddress16(framedata[1], framedata[0]);
        this.WasBroadcast = framedata[2];
        this.ClusterId = new DoubleByte(framedata[4], framedata[3]);
        this.SecurityUse = framedata[5];
        this.SeqNum = framedata[6];
        this.MacDstAddr = new ZToolAddress16(framedata[8], framedata[7]);
        this.Data = Arrays.copyOfRange(framedata, 9, framedata.length);

        this.buildPacket(new DoubleByte(ZToolCMD.ZDO_MSG_CB_INCOMING), framedata);
        this.FCS = getFCS();
    }

    /**
     * Translates the ZigBee ZDO cluster packet into a ZTool RSP packet
     */
    public ZToolPacket translate() {
        ZToolPacket newPacket;
        int [] frame;

        logger.trace("Translating ZDO cluster callback {}", ClusterId);

        Class<? extends ZToolPacket> newPacketClass = clusterToRSP.get(ClusterId.get16BitValue());

        if(newPacketClass == null) {
            logger.error("Unhandled ZDO cluster callback {}", ClusterId);
            return this;
        } else if (newPacketClass == ZDO_NWK_ADDR_RSP.class || newPacketClass == ZDO_IEEE_ADDR_RSP.class) {
            // The address responses don't need SrcAddr.  NumAssocDev and StartIndex positions are reversed.

            // The new response frame is at least 13 bytes long.
            frame = new int[Math.max(Data.length, 13)];
            System.arraycopy(Data, 0, frame, 0, Data.length);
            // If RequestType == 1 there are two extra bytes in the frame
            if (Data.length > 12) {
                frame[11] = Data[12]; // NumAssocDev
                frame[12] = Data[11]; // StartIndex
            } else {
                frame[11] = 0;
                frame[12] = 0;
            }
        } else {
            // Default frame translation, this works for most callbacks.
            //  Get 2 extra bytes at the beginning to put source address into.
            frame = new int[Data.length + 2];
            System.arraycopy(Data,  0,  frame,  2,  Data.length);
            frame[0] = SrcAddr.getLsb();
            frame[1] = SrcAddr.getMsb();
        }

        try {
            newPacket = (ZToolPacket) newPacketClass.getConstructor(int[].class).newInstance(frame);
        } catch (Exception e) {
            logger.error("Error constructing response packet {}", e);
            return this;
        }

        // Set checksum with original packet checksum
        newPacket.setFCS(this.FCS);

        return newPacket;
    }

    @Override
    public String toString() {
        return "ZDO_MSG_CB_INCOMING{" + "ClusterId=" + ClusterId + '}';
    }
}
