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
package org.bubblecloud.zigbee.packet.util;

import org.bubblecloud.zigbee.packet.ZToolCMD;
import org.bubblecloud.zigbee.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @since 0.6.0
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class UTIL_CALLBACK_SUBSCRIBE_RESPONSE extends ZToolPacket /*implements IRESPONSE;ISYSTEM*/ {
    /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.Status</name>
    /// <summary>indicates SUCCESS or FAILURE</summary>
    public int Status;

    /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE</name>
    /// <summary>Constructor</summary>
    public UTIL_CALLBACK_SUBSCRIBE_RESPONSE() {
    }

    public UTIL_CALLBACK_SUBSCRIBE_RESPONSE(int cmd_status) {
        this.Status = cmd_status;

        int[] framedata = new int[1];
        framedata[0] = this.Status;

        super.buildPacket(new DoubleByte(ZToolCMD.UTIL_CALLBACK_SUBSCRIBE_RESPONSE), framedata);
    }
    
    public UTIL_CALLBACK_SUBSCRIBE_RESPONSE(int[] framedata) {
        this.Status= framedata[0];
        super.buildPacket(new DoubleByte(ZToolCMD.UTIL_CALLBACK_SUBSCRIBE_RESPONSE), framedata);
    }

    /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS</name>
    /// <summary>Status of command</summary>
    public class CMD_STATUS {
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.FAIL</name>
        /// <summary>Status of command</summary>
        public static final int FAIL = 1;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.SUCCESS</name>
        /// <summary>Status of command</summary>
        public static final int SUCCESS = 0;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAF_STATUS_FAILED</name>
        /// <summary>Status of command</summary>
        public static final int ZAF_STATUS_FAILED = 0x80;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAF_STATUS_INVALID_PARAMETER</name>
        /// <summary>Status of command</summary>
        public static final int ZAF_STATUS_INVALID_PARAMETER = 130;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAF_STATUS_MEM_FAIL</name>
        /// <summary>Status of command</summary>
        public static final int ZAF_STATUS_MEM_FAIL = 0x81;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAPS_FAIL</name>
        /// <summary>Status of command</summary>
        public static final int ZAPS_FAIL = 0xb1;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAPS_ILLEGAL_REQUEST</name>
        /// <summary>Status of command</summary>
        public static final int ZAPS_ILLEGAL_REQUEST = 0xb3;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAPS_INVALID_BINDING</name>
        /// <summary>Status of command</summary>
        public static final int ZAPS_INVALID_BINDING = 180;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAPS_NO_ACK</name>
        /// <summary>Status of command</summary>
        public static final int ZAPS_NO_ACK = 0xb7;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAPS_NOT_SUPPORTED</name>
        /// <summary>Status of command</summary>
        public static final int ZAPS_NOT_SUPPORTED = 0xb6;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAPS_TABLE_FULL</name>
        /// <summary>Status of command</summary>
        public static final int ZAPS_TABLE_FULL = 0xb2;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZAPS_UNSUPPORTED_ATTRIB</name>
        /// <summary>Status of command</summary>
        public static final int ZAPS_UNSUPPORTED_ATTRIB = 0xb5;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZBUFFER_FULL</name>
        /// <summary>Status of command</summary>
        public static final int ZBUFFER_FULL = 0x11;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_BEACON_LOSS</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_BEACON_LOSS = 0xe0;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_CHANNEL_ACCESS_FAILURE</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_CHANNEL_ACCESS_FAILURE = 0xe1;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_DENIED</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_DENIED = 0xe2;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_DISABLE_TRX_FAILURE</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_DISABLE_TRX_FAILURE = 0xe3;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_FAILED_SECURITY_CHECK</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_FAILED_SECURITY_CHECK = 0xe4;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_FRAME_TOO_LONG</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_FRAME_TOO_LONG = 0xe5;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_INVALID_GTS</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_INVALID_GTS = 230;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_INVALID_HANDLE</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_INVALID_HANDLE = 0xe7;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_INVALID_PARAMETER</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_INVALID_PARAMETER = 0xe8;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_MEM_ERROR</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_MEM_ERROR = 0x13;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_NO_ACK</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_NO_ACK = 0xe9;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_NO_BEACON</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_NO_BEACON = 0xea;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_NO_DATA</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_NO_DATA = 0xeb;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_NO_SHORT_ADDRESS</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_NO_SHORT_ADDRESS = 0xec;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_OUT_OF_CAP</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_OUT_OF_CAP = 0xed;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_PANID_CONFLICT</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_PANID_CONFLICT = 0xee;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_REALIGNMENT</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_REALIGNMENT = 0xef;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_TRANSACTION_EXPIRED</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_TRANSACTION_EXPIRED = 240;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_TRANSACTION_OVERFLOW</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_TRANSACTION_OVERFLOW = 0xf1;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_TX_ACTIVE</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_TX_ACTIVE = 0xf2;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_UNAVAILABLE_KEY</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_UNAVAILABLE_KEY = 0xf3;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMAC_UNSUPPORTED_ATTRIBUTE</name>
        /// <summary>Status of command</summary>
        public static final int ZMAC_UNSUPPORTED_ATTRIBUTE = 0xf4;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZMEM_ERROR</name>
        /// <summary>Status of command</summary>
        public static final int ZMEM_ERROR = 0x10;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_ALREADY_PRESENT</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_ALREADY_PRESENT = 0xc5;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_INVALID_PARAM</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_INVALID_PARAM = 0xc1;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_INVALID_REQUEST</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_INVALID_REQUEST = 0xc2;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_LEAVE_UNCONFIRMED</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_LEAVE_UNCONFIRMED = 0xcb;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_NO_ACK</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_NO_ACK = 0xcc;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_NO_NETWORKS</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_NO_NETWORKS = 0xca;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_NO_ROUTE</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_NO_ROUTE = 0xcd;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_NOT_PERMITTED</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_NOT_PERMITTED = 0xc3;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_STARTUP_FAILURE</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_STARTUP_FAILURE = 0xc4;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_SYNC_FAILURE</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_SYNC_FAILURE = 0xc6;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_TABLE_FULL</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_TABLE_FULL = 0xc7;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_UNKNOWN_DEVICE</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_UNKNOWN_DEVICE = 200;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZNWK_UNSUPPORTED_ATTRIBUTE</name>
        /// <summary>Status of command</summary>
        public static final int ZNWK_UNSUPPORTED_ATTRIBUTE = 0xc9;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZSEC_CCM_FAIL</name>
        /// <summary>Status of command</summary>
        public static final int ZSEC_CCM_FAIL = 0xa4;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZSEC_MAX_FRM_COUNT</name>
        /// <summary>Status of command</summary>
        public static final int ZSEC_MAX_FRM_COUNT = 0xa3;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZSEC_NO_KEY</name>
        /// <summary>Status of command</summary>
        public static final int ZSEC_NO_KEY = 0xa1;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZSEC_OLD_FRM_COUNT</name>
        /// <summary>Status of command</summary>
        public static final int ZSEC_OLD_FRM_COUNT = 0xa2;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE_RESPONSE.CMD_STATUS.ZUNSUPPORTED_MODE</name>
        /// <summary>Status of command</summary>
        public static final int ZUNSUPPORTED_MODE = 0x12;
        }
}
