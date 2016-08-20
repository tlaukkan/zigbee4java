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
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZB_GET_DEVICE_INFO_RSP extends ZToolPacket /*implements IRESPONSE,ISIMPLEAPI*/ {
    /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO_RSP.Param</name>
    /// <summary>Device parameter.</summary>
    public int Param;
    /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO_RSP.Value</name>
    /// <summary>Parameter value.</summary>
    public int[] Value;

    /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO_RSP</name>
    /// <summary>Constructor</summary>
    public ZB_GET_DEVICE_INFO_RSP() {
        this.Value = new int[8];
    }

    /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO_RSP</name>
    /// <summary>Constructor</summary>
    public ZB_GET_DEVICE_INFO_RSP(int[] framedata) {
        this.Param = framedata[0];
        this.Value = new int[8];
        for (int i = 0; i < 8; i++) {
            this.Value[i] = framedata[i + 1];
        }
            /*if (buffer1.Length > 8)
            {
                throw new Exception("Error creating object.");
            }
            this.Value = new byte[8];
            Array.Copy(buffer1, this.Value, buffer1.Length);*/
        super.buildPacket(new DoubleByte(ZToolCMD.ZB_GET_DEVICE_INFO_RSP), framedata);
    }

    public class DEV_INFO_TYPE {
        /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.CHANNEL</name>
        /// <summary>Reset type</summary>
        public static final int CHANNEL = 5;
        /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.EXT_PAN_ID</name>
        /// <summary>Reset type</summary>
        public static final int EXT_PAN_ID = 7;
        /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.IEEE_ADDR</name>
        /// <summary>Reset type</summary>
        public static final int IEEE_ADDR = 1;
        /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.PAN_ID</name>
        /// <summary>Reset type</summary>
        public static final int PAN_ID = 6;
        /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.PARENT_IEEE_ADDR</name>
        /// <summary>Reset type</summary>
        public static final int PARENT_IEEE_ADDR = 4;
        /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.PARENT_SHORT_ADDR</name>
        /// <summary>Reset type</summary>
        public static final int PARENT_SHORT_ADDR = 3;
        /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.SHORT_ADDR</name>
        /// <summary>Reset type</summary>
        public static final int SHORT_ADDR = 2;
        /// <name>TI.ZPI2.ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.STATE</name>
        /// <summary>Reset type</summary>
        public static final int STATE = 0;
    }

}
