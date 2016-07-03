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

package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.Groups;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.EmptyPayloadCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;
import org.bubblecloud.zigbee.api.cluster.impl.general.groups.AddGroupCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.groups.AddGroupIfIdentyfingCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.groups.AddGroupResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.general.groups.GetGroupMembershipCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.groups.GetGroupMembershipResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.general.groups.RemoveGroupCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.groups.RemoveGroupResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.general.groups.ViewGroupCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.groups.ViewGroupResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.global.DefaultResponseImpl;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class GroupsCluster extends ZCLClusterBase implements Groups {

    private final AttributeImpl nameSupport;

    private final Attribute[] attributes;

    private static EmptyPayloadCommand CMD_REMOVE_ALL_GROUP = new EmptyPayloadCommand()
            .setId(Groups.REMOVE_ALL_GROUP_ID)
            .setClientServerDirection(true)
            .setClusterSpecific(true)
            .setManufacturerExtension(false);


    public GroupsCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        nameSupport = new AttributeImpl(zbDevice, this, Attributes.NAME_SUPPORT_GROUPS);
        attributes = new AttributeImpl[]{nameSupport};
    }

    @Override
    public short getId() {
        return Groups.ID;
    }

    @Override
    public String getName() {
        return Groups.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }

    public Response addGroup(int groupId, String name) throws ZigBeeClusterException {
        enableDefaultResponse();
        AddGroupCommand addGroupCmd = new AddGroupCommand(groupId, name);
        Response response = invoke(addGroupCmd);
        return new AddGroupResponseImpl(response);
    }

    public Response addGroupIfIdentifying(int groupId, String name) throws ZigBeeClusterException {
        enableDefaultResponse();
        AddGroupIfIdentyfingCommand addGroupIfIdCMD = new AddGroupIfIdentyfingCommand(groupId, name);
        Response response = invoke(addGroupIfIdCMD);
        return new DefaultResponseImpl(response);
    }

    public Attribute getAttributeNameSupport() {
        return nameSupport;
    }

    public Response getGroupMembership(int[] groupList) throws ZigBeeClusterException {
        enableDefaultResponse();
        GetGroupMembershipCommand getGroupMemCmd = new GetGroupMembershipCommand(groupList);
        Response response = invoke(getGroupMemCmd);
        return new GetGroupMembershipResponseImpl(response);
    }

    public Response removeAllGroup() throws ZigBeeClusterException {
        enableDefaultResponse();
        Response response = invoke(CMD_REMOVE_ALL_GROUP);
        return new DefaultResponseImpl(response);
    }

    public Response removeGroup(int groupId) throws ZigBeeClusterException {
        RemoveGroupCommand removeGroupCmd = new RemoveGroupCommand(groupId);
        Response response = invoke(removeGroupCmd);
        return new RemoveGroupResponseImpl(response);
    }

    public Response viewGroup(int groupId) throws ZigBeeClusterException {
        ViewGroupCommand viewGroupCmd = new ViewGroupCommand(groupId);
        Response response = invoke(viewGroupCmd);
        return new ViewGroupResponseImpl(response);
    }

}
