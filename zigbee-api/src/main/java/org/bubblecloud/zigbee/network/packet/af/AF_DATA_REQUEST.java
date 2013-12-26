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
import org.bubblecloud.zigbee.util.DoubleByte;
import org.bubblecloud.zigbee.util.Integers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class AF_DATA_REQUEST extends ZToolPacket/* implements IREQUEST,IAF*/ {
    private static final Logger logger = LoggerFactory.getLogger(AF_DATA_REQUEST.class);
    /// <name>TI.ZPI2.AF_DATA_REQUEST.ClusterID</name>
    /// <summary>specifies the cluster PROFILE_ID_HOME_AUTOMATION</summary>
    public DoubleByte ClusterID;
    /// <name>TI.ZPI2.AF_DATA_REQUEST.Data</name>
    /// <summary>Dynamic array, requires memory allocation.  Variable length field of size 'Len' and is the transaction data frame</summary>
    public int[] Data;
    /// <name>TI.ZPI2.AF_DATA_REQUEST.DestEndpoint</name>
    /// <summary>specifies the endpoint of the device</summary>
    public int DestEndpoint;
    /// <name>TI.ZPI2.AF_DATA_REQUEST.DstAddr</name>
    /// <summary>the address of the destination device</summary>
    public ZToolAddress16 DstAddr;
    /// <name>TI.ZPI2.AF_DATA_REQUEST.Len</name>
    /// <summary>specifies the length of the TransactionData field</summary>
    public int Len;
    /// <name>TI.ZPI2.AF_DATA_REQUEST.Options</name>
    /// <summary>consists of the AF Tx Options bit fields; zero for none</summary>
    public int Options;
    /// <name>TI.ZPI2.AF_DATA_REQUEST.Radius</name>
    /// <summary>the number of hops allowed to deliver the message; usually use 7</summary>
    public int Radius;
    /// <name>TI.ZPI2.AF_DATA_REQUEST.SrcEndpoint</name>
    /// <summary>specifies the endpoint of the device</summary>
    public int SrcEndpoint;
    /// <name>TI.ZPI2.AF_DATA_REQUEST.TransID</name>
    /// <summary>specifies the transaction Id of the device</summary>
    public int TransID;

    /// <name>TI.ZPI2.AF_DATA_REQUEST</name>
    /// <summary>Constructor</summary>
    public AF_DATA_REQUEST() {
        this.Data = new int[0xff];
    }

    /// <name>TI.ZPI2.AF_DATA_REQUEST</name>
    /// <summary>Constructor</summary>
    public AF_DATA_REQUEST(ZToolAddress16 num1, int num2, int num3, DoubleByte num4, int num5, int num6, int num7, int num8, int[] buffer1) {
        this.DstAddr = num1;
        this.DestEndpoint = num2;
        this.SrcEndpoint = num3;
        this.ClusterID = num4;
        this.TransID = num5;
        this.Options = num6;
        this.Radius = num7;
        this.Len = num8;
        this.Data = new int[buffer1.length];
        this.Data = buffer1;
            /*if (buffer1.Length > 0xff)
            {
                throw new Exception("Error creating object.");
            }
            this.Data = new byte[0xff];
            Array.Copy(buffer1, this.Data, buffer1.Length);*/
        int[] framedata = new int[this.Data.length + 10];
        framedata[0] = this.DstAddr.getLsb();
        framedata[1] = this.DstAddr.getMsb();
        framedata[2] = this.DestEndpoint;
        framedata[3] = this.SrcEndpoint;
        framedata[4] = this.ClusterID.getLsb();
        framedata[5] = this.ClusterID.getMsb();
        framedata[6] = this.TransID;
        framedata[7] = this.Options;
        framedata[8] = this.Radius;
        framedata[9] = this.Len;
        for (int i = 0; i < this.Data.length; i++) {
            framedata[10 + i] = this.Data[i];
        }
        super.buildPacket(new DoubleByte(ZToolCMD.AF_DATA_REQUEST), framedata);
    }

    public AF_DATA_REQUEST(int nwkDstAddress, byte dstEndPoint,
                           byte srcEndPoint, short clusterId, byte transId, byte bitmapOpt, byte radius,
                           byte[] msg) {

        //TODO Check compatibility with other Constructor

        if (msg.length > 128) {
            throw new IllegalArgumentException("Payload is too big, maxium is 128");
        }

        int[] framedata = new int[msg.length + 10];
        framedata[0] = Integers.getByteAsInteger(nwkDstAddress, 0);
        framedata[1] = Integers.getByteAsInteger(nwkDstAddress, 1);
        framedata[2] = dstEndPoint & 0xFF;
        framedata[3] = srcEndPoint & 0xFF;
        framedata[4] = Integers.getByteAsInteger(clusterId, 0);
        framedata[5] = Integers.getByteAsInteger(clusterId, 1);
        framedata[6] = transId & 0xFF;
        framedata[7] = bitmapOpt & 0xFF;
        framedata[8] = radius & 0xFF;
        framedata[9] = msg.length;
        for (int i = 0; i < msg.length; i++) {
            framedata[10 + i] = msg[i];
        }
        super.buildPacket(new DoubleByte(ZToolCMD.AF_DATA_REQUEST), framedata);

    }

}
