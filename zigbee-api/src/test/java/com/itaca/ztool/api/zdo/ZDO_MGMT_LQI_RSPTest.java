/*
   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
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
package com.itaca.ztool.api.zdo;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolAddress64;
import com.itaca.ztool.api.zdo.ZDO_MGMT_LQI_RSP.NeighborLqiListItemClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
*
* @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
* @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
* @since 0.8.0
*/
public class ZDO_MGMT_LQI_RSPTest {

     /**
     * Test method for {@link com.itaca.ztool.api.zdo.ZDO_MGMT_LQI_RSP#ZDO_MGMT_LQI_RSP(int[])}.
     */
    @Test
    public void testZDO_MGMT_LQI_RSPIntArray() {
        final int[] bytes = new int[]{
            0x00, 0xFF,      //Source Address -256
            0x02,            //Status
            0x03,            //NEntries on device
            0x01,            //FirstIndex
            0x02,            //NEntries on message

            0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,  //PanId
            0x10, 0x20, 0x30, 0x40, 0x50, 0x60, 0x70, 0x80,  //IEEEAddress
            0xAA, 0x11,                                      //Network Address
            0x20,                                            //DeviceType, RxOnIdle, Relationship
            0x01,                                            //PermitJoining
            0x02,                                            //Depth
            0xAB,                                            //LQI

            0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,  //PanId
            0x00, 0xA0, 0xB0, 0xC0, 0xD0, 0xE0, 0xF0, 0x00,  //IEEEAddress
            0xF0, 0x0F,                                      //Network Address
            0x20,                                            //DeviceType, RxOnIdle, Relationship
            0x01,                                            //PermitJoining
            0x02,                                            //Depth
            0x8F,                                            //LQI
        };
        final ZDO_MGMT_LQI_RSP cmd = new ZDO_MGMT_LQI_RSP(bytes);
        assertEquals( 2, cmd.getNeighborLQICount() );
        assertEquals( cmd.NeighborLQICount, cmd.getNeighborLQICount() );
        assertEquals( 3, cmd.getNeighborLQIEntries() );
        assertEquals( cmd.NeighborLQIEntries, cmd.getNeighborLQIEntries() );
        assertEquals( 1, cmd.getStartIndex() );
        assertEquals( cmd.StartIndex, cmd.getStartIndex() );
        assertEquals( 2, cmd.Status );
        assertEquals( new ZToolAddress16(0xff, 0x00), cmd.SrcAddress );
        NeighborLqiListItemClass[] neighborLQI = cmd.getNeighborLqiList();
        for ( int i = 0; i < neighborLQI.length; i++ ) {
            assertEquals( 2, neighborLQI[i].Depth );
            assertEquals( 32, neighborLQI[i].Reserved_Relationship_RxOnWhenIdle_DeviceType );
            assertEquals( 1, neighborLQI[i].Reserved_PermitJoining );
            //assertEquals( 2, neighborLQI[i].ExtendedPanID);
        }
        assertEquals( 0xAB, neighborLQI[0].RxLQI );
        assertEquals( 0x8F, neighborLQI[1].RxLQI );

        assertEquals(
            new ZToolAddress64( new byte[]{(byte) 0x80, 0x70, 0x60, 0x50, 0x40, 0x30, 0x20, 0x10} )
            , neighborLQI[0].ExtendedAddress
        );

        assertEquals(
            new ZToolAddress64( new byte[]{
                0x00, (byte) 0xF0, (byte) 0xE0, (byte) 0xD0, (byte) 0xC0, (byte) 0xB0, (byte) 0xA0, 0x00
            } )
            , neighborLQI[1].ExtendedAddress
        );

        assertEquals( new ZToolAddress16( 0x11, 0xAA ), neighborLQI[0].NetworkAddress );
        assertEquals( new ZToolAddress16( 0x0F, 0xF0 ), neighborLQI[1].NetworkAddress );

        assertNotNull( cmd );
    }

}
