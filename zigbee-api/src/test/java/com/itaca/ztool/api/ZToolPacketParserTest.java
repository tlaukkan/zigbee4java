/*
   Copyright 2010-2013 CNR-ISTI, http://isti.cnr.it
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
package com.itaca.ztool.api;

import com.itaca.ztool.api.simple.ZB_GET_DEVICE_INFO_RSP;
import org.bubblecloud.zigbee.util.ByteUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 *
 * Test unit for class {@link ZToolPacketParser}
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 *
 */
public class ZToolPacketParserTest {

    int[] nPackets = new int[1];

    private final static Logger logger = LoggerFactory.getLogger(ZToolPacketParserTest.class);

    @Test
    public void testBufferParser() {
        String BUFFER_ZB_GET_DEVICE_INFO_RSP =
            "0xfe 0x09 0x66 0x06 0x06 0x4d 0x05 0x09 0x8d 0x0f 0x00 0x2b 0xe2 0x65";

        ZToolPacket parsedFromInt = ZToolPacketStream.parsePacket(
                ByteUtils.fromBase16toIntArray(BUFFER_ZB_GET_DEVICE_INFO_RSP));
        try{
            ZToolPacket parsedFromByte = ZToolPacketStream.parsePacket(
                    ByteUtils.fromBase16toByteArray(BUFFER_ZB_GET_DEVICE_INFO_RSP));
            fail("Expected exception due to negative length");
        }catch(Exception ex){
        }

        assertTrue("Paserd wrong packet type from int stream", parsedFromInt.getClass()==ZB_GET_DEVICE_INFO_RSP.class);

    }

    @Test
    public void testOverwrittenPacketHandling() {
        final ZToolPacket[] packets = new ZToolPacket[1];
        final int TOTAL_GOD_PACKET = 26;
        SerialEmulator serial;
        try {
            serial = new SerialEmulator( ZToolPacketParserTest.class.getResourceAsStream( "overwritten.packet.fsm" ), false );
            serial.open("VIRTUAL", 19200, new ZToolPacketHandler(){

                public void error(Throwable th) {
                }

                public void handlePacket(ZToolPacket packet) {
                    synchronized (nPackets) {
                        if ( nPackets[0] < 0 ) {
                            return;
                        }
                        packets[0] = packet;
                        if( packet.isError() ){
                            nPackets[0]=-1;
                        } else {
                            nPackets[0]++;
                        }
                        logger.debug("Received packet, total packet received is {}",nPackets[0]);
                    }
                }

            });
        } catch (IOException e) {
            serial = null;
        }

        final long delta = 250;
        long time = 0;
        final ZToolPacketParser parser = serial.getParser();
        synchronized (nPackets) {
            while( nPackets[0] >= 0 && nPackets[0] < TOTAL_GOD_PACKET
                && parser.isAlive()
                && time < delta*10
            ){
                logger.debug(
                        "Waiting for one of the following: " +
                        "BAD PACKET, enough packet received, parser dead, timout {}", delta*10
                );
                try {
                    nPackets.wait(delta);
                } catch (InterruptedException e) {
                }
                time+=delta;
            }
        }
        serial.close();
        assertTrue("Notified of a bad packet", packets[0].isError() == false);
        assertEquals("Parsed less input packet then available", TOTAL_GOD_PACKET, nPackets[0]);
    }

}
