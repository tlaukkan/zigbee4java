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

package org.bubblecloud.zigbee.network.packet.system;

import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class SYS_VERSION_RESPONSE extends ZToolPacket /*implements /*IRESPONSE,ISYTEM*/ {
    /// <name>TI.ZPI2.SYS_VERSION_RESPONSE.HwRev</name>
    /// <summary>Hardware revision</summary>
    public int HwRev;
    /// <name>TI.ZPI2.SYS_VERSION_RESPONSE.MajorRel</name>
    /// <summary>Major release number</summary>
    public int MajorRel;
    /// <name>TI.ZPI2.SYS_VERSION_RESPONSE.MinorRel</name>
    /// <summary>Minor release number</summary>
    public int MinorRel;
    /// <name>TI.ZPI2.SYS_VERSION_RESPONSE.Product</name>
    /// <summary>Product PROFILE_ID_HOME_AUTOMATION</summary>
    public int Product;
    /// <name>TI.ZPI2.SYS_VERSION_RESPONSE.TransportRev</name>
    /// <summary>Transport revision</summary>
    public int TransportRev;

    /// <name>TI.ZPI2.SYS_VERSION_RESPONSE</name>
    /// <summary>Constructor</summary>
    public SYS_VERSION_RESPONSE() {
    }

    /// <name>TI.ZPI2.SYS_VERSION_RESPONSE</name>
    /// <summary>Constructor</summary>
    public SYS_VERSION_RESPONSE(int[] framedata) {
        this.TransportRev = framedata[0];
        this.Product = framedata[1];
        this.MajorRel = framedata[2];
        this.MinorRel = framedata[3];
        this.HwRev = framedata[4];
        super.buildPacket(new DoubleByte(ZToolCMD.SYS_VERSION_RESPONSE), framedata);
    }
}
