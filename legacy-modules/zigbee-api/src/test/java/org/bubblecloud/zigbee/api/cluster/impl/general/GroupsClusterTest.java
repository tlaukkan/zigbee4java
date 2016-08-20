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
package org.bubblecloud.zigbee.api.cluster.impl.general;


import static org.easymock.EasyMock.*;

import static org.junit.Assert.*;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.v3.model.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.Groups;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.groups.AddGroupResponse;
import org.bubblecloud.zigbee.api.cluster.impl.RawClusterMessageImpl;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.groups.GetGroupMembershipResponse;

import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 *
 */
public class GroupsClusterTest {

    private ZigBeeEndpoint createMockDevice(final byte[] response) throws ZigBeeNetworkManagerException {
        ZigBeeEndpoint mock = createMock(ZigBeeEndpoint.class);

        expect(mock.invoke( (ClusterMessage) anyObject()))
            .andReturn( new RawClusterMessageImpl( Groups.ID, response ) );
        replay( mock );
        return mock;
    }

    @Test
    @Ignore
    public void testAddGroup() {
        GroupsCluster cluster = null;
        ZigBeeEndpoint device = null;
        try {
            device = createMockDevice( new byte[]{0x09, 0x18, Groups.ADD_GROUP_ID, 0x00, 0x00, (byte) 0xf0 } );
            cluster = new GroupsCluster(device);
        } catch (ZigBeeNetworkManagerException ignored) {
        }
        try {
            AddGroupResponse response = (AddGroupResponse) cluster.addGroup(0xFF00,"hello world!");
            assertEquals( Status.SUCCESS, response.getStatus() );
            assertEquals( 0xf000, response.getGroupId() );
        } catch (ZigBeeClusterException ex) {
            fail("Unexpected exception "+ex);
            ex.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void testGetGroupMembership() {
        GroupsCluster cluster = null;
        ZigBeeEndpoint device = null;
        try {
            device = device = createMockDevice(
                new byte[]{0x09, 0x18, Groups.GET_GROUP_MEMBERSHIP_ID,
                        0x10, 0x04, //Capacity, Group Count
                        0x10, 0x00, 0x00, 0x02, (byte) 0xF0, 0x00, 0x00, (byte) 0xFF //Group List
                }
            );;
            cluster = new GroupsCluster(device);
        } catch (ZigBeeNetworkManagerException ignored) {
        }
        try {
            GetGroupMembershipResponse response = (GetGroupMembershipResponse) cluster.getGroupMembership(new int[]{0x03, 0x0A});
            assertEquals( 0x10, response.getCapacity() );
            assertEquals( 0x04, response.getGroupList().length );
            assertArrayEquals( new int[]{ 0x0010, 0x0200, 0x00F0, 0xFF00 } , response.getGroupList() );
        } catch (ZigBeeClusterException ex) {
            fail("Unexpected exception "+ex);
            ex.printStackTrace();
        }
    }
}
