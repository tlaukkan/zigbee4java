/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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
package org.bubblecloud.zigbee.proxy.cluster.impl.general.groups;

import static org.junit.Assert.*;
import org.bubblecloud.zigbee.core.ClusterMessage;
import org.bubblecloud.zigbee.proxy.cluster.api.core.Response;
import org.bubblecloud.zigbee.proxy.cluster.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.proxy.cluster.api.general.Groups;
import org.bubblecloud.zigbee.proxy.cluster.impl.RawClusterMessageImpl;
import org.bubblecloud.zigbee.proxy.cluster.impl.core.ResponseImpl;

import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate$)
 * @since 0.8.0
 *
 */
public class ViewGroupResponseImplTest {

    @Test
    public void testViewGroupResponseImpl() {
        ClusterMessage c;
        Response r;
        c = new RawClusterMessageImpl((short) 0x04, new byte[]{
                0x19,
                0x15,
                0x01,
                0x00, // Status = SUCCESS
                0x10, 0x00, // GroupId = 16
                0x03, 0x61, 0x62, 0x63 // GroupName = "abc"
        });
        try {
            r = new ResponseImpl(c,Groups.ID);
            ViewGroupResponseImpl aux = new ViewGroupResponseImpl(r);
            assertEquals(16, aux.getGroupId() );
            assertEquals("abc", aux.getGroupName() );
        } catch (ZigBeeClusterException e) {
            fail("Exception thrown " + e.getMessage() );
            e.printStackTrace();
        }


        c = new RawClusterMessageImpl((short) 0x04, new byte[]{
                0x19,
                0x29,
                0x01,
                (byte) 0x8b, // Status != SUCCESS
                0x05, 0x00, // GroupId = 5
        });
        try {
            r = new ResponseImpl(c,Groups.ID);
            ViewGroupResponseImpl aux = new ViewGroupResponseImpl(r);
            assertEquals(5, aux.getGroupId() );
            assertEquals(null, aux.getGroupName() );
        } catch (ZigBeeClusterException e) {
            fail("Exception thrown " + e.getMessage() );
            e.printStackTrace();
        }
    }

}
