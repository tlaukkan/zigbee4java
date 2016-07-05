package org.bubblecloud.zigbee.network.packet.af;

import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.bubblecloud.zigbee.util.Integers;

/**
 * AF_DATA_REQUEST_EXT message implementation according to Texas Instruments CC2530-ZNP specification.
 */
public class AfDataRequestExt extends ZToolPacket {

    public AfDataRequestExt(int groupdId,
                            short srcEndPoint, short clusterId, byte transId, byte bitmapOpt, byte radius,
                            byte[] msg) {

        if (msg.length > 230) {
            throw new IllegalArgumentException("Payload is too big, maxium is 230");
        }

        int[] framedata = new int[msg.length + 20];
        framedata[0] = 0x01; // Destination address mode 1 (group addressing)
        framedata[1] = Integers.getByteAsInteger(groupdId, 0); // Source address
        framedata[2] = Integers.getByteAsInteger(groupdId, 1); // Source address
        framedata[3] = 0x00; // Source address
        framedata[4] = 0x00; // Source address
        framedata[5] = 0x00; // Source address
        framedata[6] = 0x00; // Source address
        framedata[7] = 0x00; // Source address
        framedata[8] = 0x00; // Source address
        framedata[9] = 0x00; // Destination Endpoint
        framedata[10] = 0x00; // Destination PAN ID
        framedata[11] = 0x00; // Destination PAN ID
        framedata[12] = srcEndPoint & 0xFF;
        framedata[13] = Integers.getByteAsInteger(clusterId, 0);
        framedata[14] = Integers.getByteAsInteger(clusterId, 1);
        framedata[15] = transId & 0xFF;
        framedata[16] = bitmapOpt & 0xFF;
        framedata[17] = radius & 0xFF;
        framedata[18] = Integers.getByteAsInteger(msg.length, 0);
        framedata[19] = Integers.getByteAsInteger(msg.length, 1);
        for (int i = 0; i < msg.length; i++) {
            framedata[20 + i] = msg[i];
        }
        super.buildPacket(new DoubleByte(ZToolCMD.AF_DATA_REQUEST_EXT), framedata);
    }

}
