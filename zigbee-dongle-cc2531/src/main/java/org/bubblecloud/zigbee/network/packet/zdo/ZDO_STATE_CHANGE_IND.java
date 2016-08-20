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

import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

import java.util.HashMap;
import java.util.Map;

/**
 * This callback message indicates the ZDO state change.
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZDO_STATE_CHANGE_IND extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    /// <name>TI.ZPI2.ZDO_STATE_CHANGE_IND.State</name>
    /// <summary>State</summary>
    public int State;

    /// <name>TI.ZPI2.ZDO_STATE_CHANGE_IND</name>
    /// <summary>Constructor</summary>
    public ZDO_STATE_CHANGE_IND() {
    }

    public ZDO_STATE_CHANGE_IND(int[] framedata) {
        this.State = framedata[0];
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_STATE_CHANGE_IND), framedata);
    }

    @Override
    public String toString() {
        return "ZDO_STATE_CHANGE_IND{" +
                "State=" + CMD_STATUS.getStatus(State) +
                '}';
    }

    public enum CMD_STATUS {
        DEV_HOLD(0x00), // Initialized - not started automatically
        DEV_INIT(0x01), // Initialized - not connected to anything
        DEV_NWK_DISC(0x02), // Discovering PAN's to join
        DEV_NWK_JOINING(0x03), // Joining a PAN
        DEV_NWK_REJOINING(0x04), // ReJoining a PAN, only for end-devices
        DEV_END_DEVICE_UNAUTH(0x05), // Joined but not yet authenticated by trust center
        DEV_END_DEVICE(0x06), // Started as device after authentication
        DEV_ROUTER(0x07), // Device joined, authenticated and is a router
        DEV_COORD_STARTING(0x08), // Started as ZigBee Coordinator
        DEV_ZB_COORD(0x09), // Started as ZigBee Coordinator
        DEV_NWK_ORPHAN(0x0A); // Device has lost information about its parent

        private static Map<Integer, CMD_STATUS> mapping = new HashMap<Integer, CMD_STATUS>();
        private int value;

        private CMD_STATUS(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static CMD_STATUS getStatus(int value) {
            return mapping.get(value);
        }

        static {
            for (CMD_STATUS status : CMD_STATUS.values()) {
                mapping.put(status.value, status);
            }
        }
    }
}
