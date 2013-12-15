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

package org.bubblecloud.zigbee.packet.af;

import org.bubblecloud.zigbee.packet.test.ZToolPacketUtil;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test class of {@link AF_REGISTER}
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>

 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class AF_REGISTERTest {

    @Test
    public void testAF_REGISTERByteShortShortByteShortArrayShortArray() {
        AF_REGISTER original = new AF_REGISTER(
                0xFA, new DoubleByte(0xF0, 0xFA), new DoubleByte(0xFB, 0xFC), 0xFA,
                0, new DoubleByte[]{}, 0, new DoubleByte[]{}
        );
        AF_REGISTER modified = new AF_REGISTER(
                (byte) 0xFA, (short) 0xF0FA, (short) 0xFBFC, (byte) 0xFA,
                new int[]{},new int[]{}
        );

        ZToolPacketUtil.isSamePacket(original, modified);


        int[] sniffed = new int[]{
                0xFE, 0x1B, 0x24, 0x00,
                0x01, 0x04, 0x01,
                0x00, 0x00, 0x00, 0x00,
                0x09,
                0x05, 0x00, 0x02, 0x00, 0x0A, 0x00, 0x04, 0x00,
                0x01, 0x00, 0x09, 0x00, 0x06, 0x00, 0x03, 0x00,
                0x00, 0x00,
                0x00, //
                0x36  //FCS
        };

        modified = new AF_REGISTER((byte)0x01,(int)0x0104, (short)0x0000, (byte)0x00,
                new int[] {
                    0x0005, 0x0002, 0x000A, 0x0004, 0x0001, 0x0009, 0x0006, 0x0003, 0x0000
                },new int[]{
                }
        );

        assertArrayEquals(sniffed, modified.getPacket());
        System.out.println(Arrays.toString(sniffed));
        System.out.println(Arrays.toString(modified.getPacket()));
    }

}
