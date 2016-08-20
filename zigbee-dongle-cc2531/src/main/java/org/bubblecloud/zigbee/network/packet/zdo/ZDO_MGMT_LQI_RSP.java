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
import org.bubblecloud.zigbee.util.ByteUtils;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.bubblecloud.zigbee.v3.model.ZToolAddress16;
import org.bubblecloud.zigbee.v3.model.ZToolAddress64;

import java.util.Arrays;

/**
 * Processes the LQI Response.
 * <p>
 * The MGMT_Lqi_RSP is generated in response to an MGMT_LQI_REQ. If this
 * management command is not supported, a status of NOT_SUPPORTED will be
 * returned and all parameter fields after the Status field will be omitted.
 * 
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05
 *          +0300 (Tue, 06 Aug 2013) $)
 */
public class ZDO_MGMT_LQI_RSP extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDo*/ {
    /// <name>TI.ZPI1.ZDO_MGMT_LQI_RSP.NeighborLQICount</name>
    /// <summary>Number of entries in this response.</summary>
    public int NeighborLQICount; //TODO we should remove this
    /// <name>TI.ZPI1.ZDO_MGMT_LQI_RSP.NeighborLQIEntries</name>
    /// <summary>Total number of entries available in the device.</summary>
    public int NeighborLQIEntries;
    /// <name>TI.ZPI1.ZDO_MGMT_LQI_RSP.NeighborLqiList</name>
    /// <summary>Dynamic array, Number of entries in this response.</summary>
    public NeighborLqiListItemClass[] NeighborLqiList;
    //private NeighborLqiListItemClass NeighborLqiListItemClassDummyObj;
    /// <name>TI.ZPI1.ZDO_MGMT_LQI_RSP.SrcAddress</name>
    /// <summary>Source address of the message</summary>
    public ZToolAddress16 SrcAddress;
    /// <name>TI.ZPI1.ZDO_MGMT_LQI_RSP.StartIndex</name>
    /// <summary>Where in the total number of entries this response starts.</summary>
    public int StartIndex;
    /// <name>TI.ZPI1.ZDO_MGMT_LQI_RSP.Status</name>
    /// <summary>this field indicates either SUCCESS or FAILURE.</summary>
    public int Status;

    /// <name>TI.ZPI1.ZDO_MGMT_LQI_RSP</name>
    /// <summary>Constructor</summary>
    public ZDO_MGMT_LQI_RSP() {
        this.NeighborLqiList = new NeighborLqiListItemClass[]{};
    }

    public ZDO_MGMT_LQI_RSP(int[] framedata) {
        this.SrcAddress = new ZToolAddress16(framedata[1], framedata[0]);
        this.Status = framedata[2];
        if (framedata.length > 3)
        {
            this.NeighborLQIEntries = framedata[3];
            this.StartIndex = framedata[4];
            this.NeighborLQICount = framedata[5];
            this.NeighborLqiList = new NeighborLqiListItemClass[framedata[5]];

            int NOpt1;
            int NOpt2;

            int k = 0;
            byte[] bytes = new byte[8];
            for (int z = 0; z < this.NeighborLqiList.length; z++) {
                for (int j = 0; j < 8; j++) {
                    bytes[7 - j] = (byte) framedata[6 + k + j];///MSB><LSB?
                }
                final long panId = ByteUtils.convertMultiByteToLong(bytes);
                for (int j = 0; j < 8; j++) {
                    bytes[7 - j] = (byte) framedata[14 + k + j];///MSB><LSB?
                }
                final ZToolAddress64 ieeeAddr = new ZToolAddress64(bytes);
                final ZToolAddress16 nwkAddr = new ZToolAddress16(framedata[23 + k], framedata[22 + k]);///MSB><LSB?
                NOpt1 = framedata[24 + k];
                NOpt2 = framedata[25 + k];
                final int lqi = framedata[26 + k];
                final int depth = framedata[27 + k];
                this.NeighborLqiList[z] = new NeighborLqiListItemClass(panId, ieeeAddr, nwkAddr, NOpt1, NOpt2, lqi, depth);
                k += 22;
            }
        }
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_MGMT_LQI_RSP), framedata);
    }


    /// <name>TI.ZPI1.ZDO_MGMT_LQI_RSP.NeighborLqiListItemClass</name>
    /// <summary>Contains information in a single item of a network list</summary>
    public class NeighborLqiListItemClass {

        public int Depth;
        public ZToolAddress64 ExtendedAddress;
        public long ExtendedPanID;
        public ZToolAddress16 NetworkAddress;
        public int Reserved_Relationship_RxOnWhenIdle_DeviceType;
        public int Reserved_PermitJoining;
        public int RxLQI;

        public NeighborLqiListItemClass() {
        }

        public NeighborLqiListItemClass(long num1, ZToolAddress64 num2, ZToolAddress16 num3, int num4, int num5, int num6, int num7) {
            this.ExtendedPanID = num1;
            this.ExtendedAddress = num2;
            this.NetworkAddress = num3;
            this.Reserved_Relationship_RxOnWhenIdle_DeviceType = num4;
            this.Reserved_PermitJoining = num5;
            this.Depth = num6;
            this.RxLQI = num7;
        }
    }

    /**
     * @return the number of Neighbor LQI entries present on the message
     */
    public int getNeighborLQICount() {
        if (this.NeighborLqiList != null)
        {
            return this.NeighborLqiList.length;
        } else {
            return 0;
        }
    }

    /**
     * @return the index of the first entries available on the message with respect
     *         to the Neighbor LQI Tables on the device
     */
    public int getStartIndex() {
        return StartIndex;
    }

    /**
     * @return the number of Neighbor LQI entries available on the device
     */
    public int getNeighborLQIEntries() {
        return this.NeighborLQIEntries;
    }

    public NeighborLqiListItemClass[] getNeighborLqiList() {
        return NeighborLqiList;
    }

    @Override
    public String toString() {
        return "ZDO_MGMT_LQI_RSP{" +
                "NeighborLQICount=" + NeighborLQICount +
                ", NeighborLQIEntries=" + NeighborLQIEntries +
                ", NeighborLqiList=" + Arrays.toString(NeighborLqiList) +
                ", SrcAddress=" + SrcAddress +
                ", StartIndex=" + StartIndex +
                ", Status=" + ResponseStatus.getStatus(Status) +
                '}';
    }
}
