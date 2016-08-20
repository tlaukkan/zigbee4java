/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies
   of the Italian National Research Council


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

package org.bubblecloud.zigbee.api.cluster.impl.core;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLFrameControl;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 */
public class ZCLFrameControlImpl implements ZCLFrameControl {
    public static final byte NULL_BYTE = 0x00;                //0b0000_0000

    public static final byte FRAME_TYPE_MASK = (byte) 0x03;    //0b0000_0011
    public static final byte MANUFACTURER_MASK = 0x04;        //0b0000_0100
    public static final byte DIRECTION_MASK = 0x08;            //0b0000_1000
    public static final byte RESPONSE_TYPE_MASK = 0x10;        //0b0001_0000

    public static final byte CLUSTER_COMMAND = 0x01;            //0b0000_0001
    public static final byte MANUFACTURER_EXT = 0x04;            //0b0000_0100
    public static final byte SERVER_CLIENT_DIRECTION = 0x08;    //0b0001_1000
    public static final byte DISABLE_DEFAULT_RESPONSE = 0x10;    //0b0001_0000

    private boolean isClusterSpecificCommand;
    private boolean isManufacturerExtension;
    private boolean isClientServerDirection;
    private boolean isDefaultResponseEnabled;
    private byte frameControl;

    public ZCLFrameControlImpl(Command cmd, boolean isDefaultResponseEnabled) {

        byte frameType = cmd.isClusterSpecific() ? CLUSTER_COMMAND : NULL_BYTE;
        byte manufacturerBit = cmd.isManufacturerExtension() ? MANUFACTURER_EXT : NULL_BYTE;
        byte directionBit = cmd.isClientServerDirection() ? NULL_BYTE : SERVER_CLIENT_DIRECTION;
        byte defaultResponse = isDefaultResponseEnabled ? NULL_BYTE : DISABLE_DEFAULT_RESPONSE;

        isClusterSpecificCommand = cmd.isClusterSpecific();
        isManufacturerExtension = cmd.isManufacturerExtension();
        isClientServerDirection = cmd.isClientServerDirection();
        this.isDefaultResponseEnabled = isDefaultResponseEnabled;

        frameControl = (byte) (frameType | manufacturerBit | directionBit | defaultResponse);
    }


    public ZCLFrameControlImpl(byte frameControl) {
        this.frameControl = frameControl;

        byte frameType = (byte) (frameControl & FRAME_TYPE_MASK);
        byte manufacturerBit = (byte) (frameControl & MANUFACTURER_MASK);
        byte directionBit = (byte) (frameControl & DIRECTION_MASK);
        byte defaultResponse = (byte) (frameControl & RESPONSE_TYPE_MASK);

        isClusterSpecificCommand = (frameType & CLUSTER_COMMAND) > 0;

        isManufacturerExtension = (manufacturerBit & MANUFACTURER_EXT) > 0;

        isClientServerDirection = !((directionBit & SERVER_CLIENT_DIRECTION) > 0);

        isDefaultResponseEnabled = !((defaultResponse & DISABLE_DEFAULT_RESPONSE) > 0);
    }

    public boolean isClusterSpecificCommand() {
        return isClusterSpecificCommand;
    }

    public boolean isManufacturerExtension() {
        return isManufacturerExtension;
    }

    public boolean isClientServerDirection() {
        return isClientServerDirection;
    }

    public boolean isDefaultResponseEnabled() {
        return isDefaultResponseEnabled;
    }

    public byte toByte() {
        return frameControl;
    }

}
