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

import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZB_SEND_DATA_REQUEST extends ZToolPacket /*implements IREQUEST,ISIMPLEAPI*/ {
    /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.CommandId</name>
    /// <summary>Command identifier of the data packet.</summary>
    public DoubleByte CommandId;
    /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.Destination</name>
    /// <summary>Short address of destination device ( 0xFFFE indicates NULL address and a binding must have been established previously for this command identifier. Broadcast addresses include 0xFFFF for all devices, 0xFFFC for all routers/coordinator devices and 0xFFFD for all devices with receiver turned ON ).</summary>
    public ZToolAddress16 Destination;
    /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.Handle</name>
    /// <summary>Identifier for this data send operation.</summary>
    public int Handle;
    /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.PayloadLen</name>
    /// <summary>Size in bytes of the data packet payload.</summary>
    public int PayloadLen;
    /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.PayloadValue</name>
    /// <summary>Packet payload.</summary>
    public int[] PayloadValue;
    /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.Radius</name>
    /// <summary>Max number of hops the data packet can travel ( set to 0 to use default value ).</summary>
    public int Radius;
    /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.TxOptions</name>
    /// <summary>Bit mask of transmission options ( 0x10 for end-to-end acknowledgements and retransmissions ).</summary>
    public int TxOptions;

    /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST</name>
    /// <summary>Constructor</summary>
    public ZB_SEND_DATA_REQUEST() {
        this.PayloadValue = new int[0xff];
    }

    public ZB_SEND_DATA_REQUEST(ZToolAddress16 num1, DoubleByte num2, int num3, int txoptions_type1, int num4, int num5, int[] buffer1) {
        this.Destination = num1;
        this.CommandId = num2;
        this.Handle = num3;
        this.TxOptions = txoptions_type1;
        this.Radius = num4;
        this.PayloadLen = num5;
        this.PayloadValue = new int[buffer1.length];
        this.PayloadValue = buffer1;
            /*if (buffer1.Length > 0xff)
            {
                throw new Exception("Error creating object.");
            }
            this.PayloadValue = new byte[0xff];
            Array.Copy(buffer1, this.PayloadValue, buffer1.Length);*/
        int[] framedata = new int[this.PayloadValue.length + 8];
        framedata[0] = this.Destination.getLsb();
        framedata[1] = this.Destination.getMsb();
        framedata[2] = this.CommandId.getLsb();
        framedata[3] = this.CommandId.getMsb();
        framedata[4] = this.Handle;
        framedata[5] = this.TxOptions;
        framedata[6] = this.Radius;
        framedata[7] = this.PayloadLen;
        for (int i = 0; i < this.PayloadValue.length; i++) {
            framedata[i + 8] = this.PayloadValue[i];
        }
        super.buildPacket(new DoubleByte(ZToolCMD.ZB_SEND_DATA_REQUEST), framedata);

    }

    /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.TXOPTIONS_TYPE</name>
    /// <summary>Reset type</summary>
    public class TXOPTIONS_TYPE {
        /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.TXOPTIONS_TYPE.END-TO-END-ACK</name>
        /// <summary>Reset type</summary>
        public static final int END_TO_END_ACK = 0x10;
        /// <name>TI.ZPI2.ZB_SEND_DATA_REQUEST.TXOPTIONS_TYPE.NONE</name>
        /// <summary>Reset type</summary>
        public static final int NONE = 0;
    }

}
