/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 

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
import org.bubblecloud.zigbee.util.Integers;

import java.util.Arrays;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ZDO_SIMPLE_DESC_RSP extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.AppDevID</name>
    /// <summary>The Device Description PROFILE_ID_HOME_AUTOMATION for this endpoint.</summary>
    public DoubleByte DevID;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.AppDevVer</name>
    /// <summary>Flags indicating app version</summary>
    public int DevVer;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.AppInClusterCount</name>
    /// <summary>The number of input clusters in the AppInClusterList</summary>
    public int InClusterCount;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.AppInClusterList</name>
    /// <summary>List of input cluster IDs supported</summary>
    public DoubleByte[] InClusterList;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.AppOutClusterCount</name>
    /// <summary>The number of output clusters in the AppOutClusterList</summary>
    public int OutClusterCount;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.AppOutClusterList</name>
    /// <summary>List of output cluster IDs supported</summary>
    public DoubleByte[] OutClusterList;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.AppProfID</name>
    /// <summary>The profile PROFILE_ID_HOME_AUTOMATION for this endpoint.</summary>
    public DoubleByte ProfID;

    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.Endpoint</name>
    /// <summary>End point</summary>
    public int Endpoint;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.NWKAddrOfInterest</name>
    /// <summary>Device's short address that this response describes.</summary>
    public ZToolAddress16 nwkAddr;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.SrcAddress</name>
    /// <summary>the message's source network address.</summary>
    public ZToolAddress16 SrcAddress;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.Status</name>
    /// <summary>this field indicates either SUCCESS or FAILURE.</summary>
    public int Status;
    public int len;
    private short[] inputs;
    private short[] outputs;

    private final int MIN_DESC_LEN = 8;

    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP</name>
    /// <summary>Constructor</summary>
    public ZDO_SIMPLE_DESC_RSP() {
        this.InClusterList = new DoubleByte[0xff];
        this.OutClusterList = new DoubleByte[0xff];
    }

    public ZDO_SIMPLE_DESC_RSP(int[] framedata) {
        this.SrcAddress = new ZToolAddress16(framedata[1], framedata[0]);
        this.Status = framedata[2];
        this.nwkAddr = new ZToolAddress16(framedata[4], framedata[3]);
        this.len = framedata[5];

        /* Only parse simple descriptor if there is enough data */
        if (this.len >= MIN_DESC_LEN) {
            this.Endpoint = framedata[6];
            this.ProfID = new DoubleByte(framedata[8], framedata[7]);
            this.DevID = new DoubleByte(framedata[10], framedata[9]);
            this.DevVer = framedata[11];

            this.InClusterCount = framedata[12];
            this.InClusterList = new DoubleByte[this.InClusterCount];

            for (int i = 0; i < this.InClusterCount; i++) {
                this.InClusterList[i] = new DoubleByte(framedata[(i * 2) + 14], framedata[(i * 2) + 13]);
            }
            this.OutClusterCount = framedata[((this.InClusterCount) * 2) + 13];
            this.OutClusterList = new DoubleByte[this.OutClusterCount];
            for (int i = 0; i < this.OutClusterCount; i++) {
                this.OutClusterList[i] = new DoubleByte(framedata[(i * 2) + ((this.InClusterCount) * 2) + 15], framedata[(i * 2) + ((this.InClusterCount) * 2) + 14]);
           }
        }

        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_SIMPLE_DESC_RSP), framedata);
    }

    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.APP_DEV_VER</name>
    /// <summary>App dev ver</summary>
    public class DEV_VER {
        /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_RSP.APP_DEV_VER.VER_100</name>
        /// <summary>App dev ver</summary>
        public static final int VER_100 = 0;
    }

    public short[] getInputClustersList() {
        if (inputs == null) {
            if (len >= MIN_DESC_LEN) {
                inputs = new short[super.packet[ZToolPacket.PAYLOAD_START_INDEX + 12]];
                int j = 0;
                for (int i = 0; i < inputs.length; i++) {
                    inputs[i] = Integers.shortFromInts(
                            super.packet,
                            ZToolPacket.PAYLOAD_START_INDEX + 14 + j,
                            ZToolPacket.PAYLOAD_START_INDEX + 13 + j
                    );
                    j += 2;
                }
            } else {
                inputs = new short[0];
            }
        }
        return inputs;
    }

    public int getInputClustersCount() {
        return getInputClustersList().length;
    }


    public short[] getOutputClustersList() {
        if (outputs == null) {
            if (len >= MIN_DESC_LEN) {
                int j = getInputClustersCount() * 2;
                outputs = new short[super.packet[ZToolPacket.PAYLOAD_START_INDEX + 13 + j]];
                for (int i = 0; i < outputs.length; i++) {
                    outputs[i] = Integers.shortFromInts(
                            super.packet,
                            ZToolPacket.PAYLOAD_START_INDEX + 15 + j,
                            ZToolPacket.PAYLOAD_START_INDEX + 14 + j
                    );
                    j += 2;
                }
            } else {
                outputs = new short[0];
            }
        }
        return outputs;
    }

    public int getOutputClustersCount() {
        return getOutputClustersList().length;
    }

    public byte getEndPoint() {
        if (len >= MIN_DESC_LEN) {
            return (byte) (super.packet[ZToolPacket.PAYLOAD_START_INDEX + 6]);
        } else {
            return -1;
        }
    }

    public short getProfileId() {
        if (len >= MIN_DESC_LEN) {
            return Integers.shortFromInts(
                    super.packet,
                    ZToolPacket.PAYLOAD_START_INDEX + 8,
                    ZToolPacket.PAYLOAD_START_INDEX + 7
            );
        } else {
            return -1;
        }
    }

    public short getDeviceId() {
        if (len >= MIN_DESC_LEN) {
            return Integers.shortFromInts(
                    super.packet,
                    ZToolPacket.PAYLOAD_START_INDEX + 10,
                    ZToolPacket.PAYLOAD_START_INDEX + 9
            );
        } else {
            return -1;
        }
    }

    public byte getDeviceVersion() {
        if (len >= MIN_DESC_LEN) {
            return (byte) super.packet[ZToolPacket.PAYLOAD_START_INDEX + 11];
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "ZDO_SIMPLE_DESC_RSP{" +
                "DevID=" + DevID +
                ", DevVer=" + DevVer +
                ", InClusterCount=" + InClusterCount +
                ", InClusterList=" + Arrays.toString(InClusterList) +
                ", OutClusterCount=" + OutClusterCount +
                ", OutClusterList=" + Arrays.toString(OutClusterList) +
                ", ProfID=" + ProfID +
                ", Endpoint=" + Endpoint +
                ", nwkAddr=" + nwkAddr +
                ", SrcAddress=" + SrcAddress +
                ", Status=" + ResponseStatus.getStatus(Status) +
                ", len=" + len +
                ", inputs=" + Arrays.toString(inputs) +
                ", outputs=" + Arrays.toString(outputs) +
                '}';
    }
}
