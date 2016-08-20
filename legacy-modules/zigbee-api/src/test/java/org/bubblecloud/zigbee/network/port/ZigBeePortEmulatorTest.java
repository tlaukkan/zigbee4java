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
package org.bubblecloud.zigbee.network.port;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZigBeePortEmulatorTest
{

    @Test
    public void testExtractTimeMillis() {
        final Object[][] lines = new Object[][] {
            new Object[] {
                10l, "Request: 06/08/2010 17:03:13.42064 (+0.0100 seconds)"
            }, new Object[] {
                10l, "Request: 06/08/2010 17:03:13.42064 (+0.0100 seconds)"
            }, new Object[] {
                911l, "Request: 06/08/2010 17:03:01.20864 (+0.9112 seconds"
            }, new Object[] {
                5l, "Answer: 06/08/2010 17:03:14.96564 (+0.0050 seconds)"
            }, new Object[] {
                0l, "Answer: 06/08/2010 17:03:12.46464 (+0.0000 seconds)"
            }
        };
        final ZigBeePortEmulator e = new ZigBeePortEmulator( );
        for ( int i = 0; i < lines.length; i++ ) {
            assertEquals( ( (Long) lines[i][0] ).longValue(), e.extractTimeMillis( (String) lines[i][1] ) );
        }
    }

    @Test
    public void testExtractBytes() {
        final Object[][] lines = new Object[][] {
            new Object[] {
                new byte[] {
                    (byte) 0xFE, 0x00, 0x21, 0x02, 0x23
                }, " FE 00 21 02 23                                    þ.!.#"
            }

            , new Object[] {
                new byte[] {
                    (byte) 0xFE, 0x05, 0x61, 0x02, 002, 0x00, 0x02, 0x01, 0x00, 0x67
                }, " FE 05 61 02 02 00 02 01 00 67                     þ.a......g"
            }

        };
        final ZigBeePortEmulator e = new ZigBeePortEmulator( );
        for ( int i = 0; i < lines.length; i++ ) {
            final byte[] parsed = e.extractBytes( (String) lines[i][1] );
            //System.out.println( Arrays.toString( (byte[]) lines[i][0] ) );
            //System.out.println( Arrays.toString( parsed ) );
            assertArrayEquals( (byte[]) lines[i][0], parsed );
        }
    }

    @Test
    public void testSimulate(){
        try {
            final ZigBeePortEmulator e = new ZigBeePortEmulator( this.getClass().getResourceAsStream( "session.fsm" ) );
        }
        catch ( Exception ex ) {
            ex.printStackTrace( );
            fail("Exception thrwon");
        }
    }

    @Test
    public void testParsing(){
        ZigBeePortEmulator e;
        try {
            e = new ZigBeePortEmulator( this.getClass().getResourceAsStream( "session.size.0.fsm" ) );
            assertEquals(
                    "The number of input byte parsed is wrong",
                    0,
                    e.getEmulatorInputStream().getFullInputStream().available()
            );
            e = new ZigBeePortEmulator( this.getClass().getResourceAsStream( "session.size.27.fsm" ) );
            assertEquals(
                    "The number of input byte parsed is wrong",
                    27,
                    e.getEmulatorInputStream().getFullInputStream().available()
            );
        }
        catch ( Exception ex ) {
            ex.printStackTrace( );
            fail("Exception thrwon");
        }
    }
}
