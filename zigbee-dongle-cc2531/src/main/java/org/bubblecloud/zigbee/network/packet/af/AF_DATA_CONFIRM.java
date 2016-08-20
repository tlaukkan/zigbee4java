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

package org.bubblecloud.zigbee.network.packet.af;

import org.bubblecloud.zigbee.network.packet.ResponseStatus;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class AF_DATA_CONFIRM extends ZToolPacket/* implements ICONFIRMATION, IAF*/ {

    private final static Logger profiler = LoggerFactory.getLogger("profiling." + AF_DATA_CONFIRM.class.getName());

    /// <name>TI.ZPI2.AF_DATA_CONFIRM.Endpoint</name>
    /// <summary>specifies the endpoint of the message</summary>
    public int Endpoint;
    /// <name>TI.ZPI2.AF_DATA_CONFIRM.Status</name>
    /// <summary>Status</summary>
    public int Status;
    /// <name>TI.ZPI2.AF_DATA_CONFIRM.TransID</name>
    /// <summary>Transaction PROFILE_ID_HOME_AUTOMATION</summary>
    public int TransID;

    /// <name>TI.ZPI2.AF_DATA_CONFIRM</name>
    /// <summary>Constructor</summary>
    public AF_DATA_CONFIRM() {
    }

    public AF_DATA_CONFIRM(int af_status1, int num1, int num2) {
        this.Status = af_status1;
        this.Endpoint = num1;
        this.TransID = num2;
        int[] framedata = new int[3];
        framedata[0] = this.Status;
        framedata[1] = this.Endpoint;
        framedata[2] = this.TransID;
        super.buildPacket(new DoubleByte(ZToolCMD.AF_DATA_CONFIRM), framedata);
    }

    public AF_DATA_CONFIRM(int[] framedata) {
        profiler.debug("AF_DATA_CONFIRM: creating object");
        this.Status = framedata[0];
        this.Endpoint = framedata[1];
        this.TransID = framedata[2];
        super.buildPacket(new DoubleByte(ZToolCMD.AF_DATA_CONFIRM), framedata);
        profiler.debug("AF_DATA_CONFIRM: object created");
    }

    public int getStatus() {
        return super.packet[ZToolPacket.PAYLOAD_START_INDEX];
    }

    @Override
    public String toString() {
        return "AF_DATA_CONFIRM{" +
                "Endpoint=" + Endpoint +
                ", Status=" + ResponseStatus.getStatus(Status) +
                ", TransID=" + TransID +
                '}';
    }
}
