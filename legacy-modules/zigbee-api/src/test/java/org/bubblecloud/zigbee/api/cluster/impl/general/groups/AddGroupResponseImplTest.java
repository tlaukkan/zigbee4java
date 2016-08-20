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
package org.bubblecloud.zigbee.api.cluster.impl.general.groups;

import static org.junit.Assert.*;
import org.bubblecloud.zigbee.v3.model.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.Groups;
import org.bubblecloud.zigbee.api.cluster.impl.RawClusterMessageImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ResponseImpl;

import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 *
 */
public class AddGroupResponseImplTest {

    @Test
    public void testAddGroupResponseImpl() {
        try {
            AddGroupResponseImpl response = new AddGroupResponseImpl(
                new ResponseImpl(
                        new RawClusterMessageImpl(
                                Groups.ID,
                                new byte[]{0x09, 0x18, 0x00, 0x00, 0x00, (byte) 0xf0 }
                        ),
                        Groups.ID
                )
            );
            assertEquals( Status.SUCCESS, response.getStatus() );
            assertEquals( 0xf000, response.getGroupId() );
        } catch (ZigBeeClusterException e) {
            e.printStackTrace();
            fail("Exception thrwon "+e.getMessage());
        }
    }

}
