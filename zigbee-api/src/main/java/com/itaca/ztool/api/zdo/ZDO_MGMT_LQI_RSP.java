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

package com.itaca.ztool.api.zdo;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolAddress64;
import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.ByteUtils;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
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
        this.NeighborLQIEntries = framedata[3];
        this.StartIndex = framedata[4];
        this.NeighborLQICount = framedata[5];
        this.NeighborLqiList = new NeighborLqiListItemClass[ framedata[5] ];
        
        int NOpt1;
        int NOpt2;
        
        int k = 0;
        byte[] bytes = new byte[8];
        for (int z = 0; z < this.NeighborLqiList.length; z++) {
            for (int j = 0; j < 8; j++) {
                bytes[7-j] = (byte) framedata[6 + k + j];///MSB><LSB?
            }
            final long panId = ByteUtils.convertMultiByteToLong(bytes);
            for (int j = 0; j < 8; j++) {
                bytes[7-j] = (byte) framedata[14 + k + j];///MSB><LSB?
            }
            final ZToolAddress64 ieeeAddr = new ZToolAddress64(bytes);
            final ZToolAddress16 nwkAddr = new ZToolAddress16(framedata[23 + k], framedata[22 + k]);///MSB><LSB?
            NOpt1=framedata[24 + k];
            NOpt2=framedata[25 + k];
            final int lqi = framedata[26 + k];
            final int depth = framedata[27 + k];
            this.NeighborLqiList[z] = new NeighborLqiListItemClass(panId,ieeeAddr,nwkAddr,NOpt1,NOpt2,lqi, depth);
            k += 22;
        }
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_MGMT_LQI_RSP), framedata);
    }

    /// <name>TI.ZPI1.ZDO_MGMT_LQI_RSP.CMD_STATUS</name>
    /// <summary>Status of command</summary>
    public class CMD_STATUS {

        public static final int FAIL = 1;
        public static final int SUCCESS = 0;
        public static final int ZAF_STATUS_FAILED = 0x80;
        public static final int ZAF_STATUS_INVALID_PARAMETER = 130;
        public static final int ZAF_STATUS_MEM_FAIL = 0x81;
        public static final int ZAPS_FAIL = 0xb1;
        public static final int ZAPS_ILLEGAL_REQUEST = 0xb3;
        public static final int ZAPS_INVALID_BINDING = 180;
        public static final int ZAPS_NO_ACK = 0xb7;
        public static final int ZAPS_NOT_SUPPORTED = 0xb6;
        public static final int ZAPS_TABLE_FULL = 0xb2;
        public static final int ZAPS_UNSUPPORTED_ATTRIB = 0xb5;
        public static final int ZBUFFER_FULL = 0x11;
        public static final int ZMAC_BEACON_LOSS = 0xe0;
        public static final int ZMAC_CHANNEL_ACCESS_FAILURE = 0xe1;
        public static final int ZMAC_DENIED = 0xe2;
        public static final int ZMAC_DISABLE_TRX_FAILURE = 0xe3;
        public static final int ZMAC_FAILED_SECURITY_CHECK = 0xe4;
        public static final int ZMAC_FRAME_TOO_LONG = 0xe5;
        public static final int ZMAC_INVALID_GTS = 230;
        public static final int ZMAC_INVALID_HANDLE = 0xe7;
        public static final int ZMAC_INVALID_PARAMETER = 0xe8;
        public static final int ZMAC_MEM_ERROR = 0x13;
        public static final int ZMAC_NO_ACK = 0xe9;
        public static final int ZMAC_NO_BEACON = 0xea;
        public static final int ZMAC_NO_DATA = 0xeb;
        public static final int ZMAC_NO_SHORT_ADDRESS = 0xec;
        public static final int ZMAC_OUT_OF_CAP = 0xed;
        public static final int ZMAC_PANID_CONFLICT = 0xee;
        public static final int ZMAC_REALIGNMENT = 0xef;
        public static final int ZMAC_TRANSACTION_EXPIRED = 240;
        public static final int ZMAC_TRANSACTION_OVERFLOW = 0xf1;
        public static final int ZMAC_TX_ACTIVE = 0xf2;
        public static final int ZMAC_UNAVAILABLE_KEY = 0xf3;
        public static final int ZMAC_UNSUPPORTED_ATTRIBUTE = 0xf4;
        public static final int ZMEM_ERROR = 0x10;
        public static final int ZNWK_ALREADY_PRESENT = 0xc5;
        public static final int ZNWK_INVALID_PARAM = 0xc1;
        public static final int ZNWK_INVALID_REQUEST = 0xc2;
        public static final int ZNWK_LEAVE_UNCONFIRMED = 0xcb;
        public static final int ZNWK_NO_ACK = 0xcc;
        public static final int ZNWK_NO_NETWORKS = 0xca;
        public static final int ZNWK_NO_ROUTE = 0xcd;
        public static final int ZNWK_NOT_PERMITTED = 0xc3;
        public static final int ZNWK_STARTUP_FAILURE = 0xc4;
        public static final int ZNWK_SYNC_FAILURE = 0xc6;
        public static final int ZNWK_TABLE_FULL = 0xc7;
        public static final int ZNWK_UNKNOWN_DEVICE = 200;
        public static final int ZNWK_UNSUPPORTED_ATTRIBUTE = 0xc9;
        public static final int ZSEC_CCM_FAIL = 0xa4;
        public static final int ZSEC_MAX_FRM_COUNT = 0xa3;
        public static final int ZSEC_NO_KEY = 0xa1;
        public static final int ZSEC_OLD_FRM_COUNT = 0xa2;
        public static final int ZUNSUPPORTED_MODE = 0x12;
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
     * 
     * @return the number of Neighbor LQI entries present on the message
     */
    public int getNeighborLQICount(){ 
        return this.NeighborLqiList.length; 
    }

    /**
     * 
     * @return the index of the first entries available on the message with respect 
     *      to the Neighbor LQI Tables on the device
     */
    public int getStartIndex() {
        return StartIndex;
    }
    
    /**
     * 
     * @return the number of Neighbor LQI entries available on the device
     */
    public int getNeighborLQIEntries(){ 
        return this.NeighborLQIEntries; 
    }

    public NeighborLqiListItemClass[] getNeighborLqiList(){ 
        return NeighborLqiList; 
    }
}
