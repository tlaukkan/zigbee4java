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
package it.cnr.isti.zigbee.zcl.library.impl.general.groups;

import static org.junit.Assert.*;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.Groups;
import it.cnr.isti.zigbee.zcl.library.impl.RawClusterImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ResponseImpl;

import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 *
 */
public class GetGroupMembershipResponseImplTest {

    @Test
    public void testGetGroupMembershipResponseImpl() {
        try {
            GetGroupMembershipResponseImpl response = new GetGroupMembershipResponseImpl(
                new ResponseImpl(
                        new RawClusterImpl(
                                Groups.ID,
                                new byte[]{0x09, 0x18, 0x02, 0x07, 0x01, 0x0f, 0x00}
                        ),
                        Groups.ID
                )
            );
            assertEquals(7, response.getCapacity());
            assertEquals(1, response.getGroupList().length);
            assertArrayEquals(new int[]{0x0f}, response.getGroupList());
        } catch (ZigBeeClusterException e) {
            e.printStackTrace();
            fail("Exception thrwon "+e.getMessage());
        }

        try {
            GetGroupMembershipResponseImpl response = new GetGroupMembershipResponseImpl(
                new ResponseImpl(
                        new RawClusterImpl(
                                Groups.ID,
                                new byte[]{0x09, 0x18, 0x02, (byte) 0xF0, 0x03, 0x0f, 0x00, 0x00, (byte) 0xf0, 0x34, 0x12}
                        ),
                        Groups.ID
                )
            );
            assertEquals(240, response.getCapacity());
            assertEquals(3, response.getGroupList().length);
            assertArrayEquals(new int[]{0x0f, 0xf000, 0x1234}, response.getGroupList());
        } catch (ZigBeeClusterException e) {
            e.printStackTrace();
            fail("Exception thrwon "+e.getMessage());
        }
    }
}
