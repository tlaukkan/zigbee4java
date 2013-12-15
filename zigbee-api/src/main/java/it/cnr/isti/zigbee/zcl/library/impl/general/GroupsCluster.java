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

package it.cnr.isti.zigbee.zcl.library.impl.general;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.Groups;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.EmptyPayloadCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;
import it.cnr.isti.zigbee.zcl.library.impl.general.groups.AddGroupCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.groups.AddGroupIfIdentyfingCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.groups.AddGroupResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.groups.GetGroupMembershipCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.groups.GetGroupMembershipResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.groups.RemoveGroupCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.groups.RemoveGroupResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.groups.ViewGroupCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.groups.ViewGroupResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.global.DefaultResponseImpl;
/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 *
 */
public class GroupsCluster extends ZCLClusterBase implements Groups {

    private final AttributeImpl nameSupport;

    private final Attribute[] attributes;

    private static EmptyPayloadCommand CMD_REMOVE_ALL_GROUP = new EmptyPayloadCommand()
    .setId(Groups.REMOVE_ALL_GROUP_ID)
    .setClientServerDirection(true)
    .setClusterSpecific(true)
    .setManufacturerExtension(false);


    public GroupsCluster(ZigBeeDevice zbDevice){
        super(zbDevice);
        nameSupport = new AttributeImpl(zbDevice,this,Attributes.NAME_SUPPORT_GROUPS);
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
        return  new AddGroupResponseImpl(response);
    }

    public Response addGroupIfIdentifying(int groupId, String name) throws ZigBeeClusterException {
        enableDefaultResponse();
        AddGroupIfIdentyfingCommand addGroupIfIdCMD = new AddGroupIfIdentyfingCommand(groupId,name);
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
        return  new GetGroupMembershipResponseImpl(response);
    }

    public Response removeAllGroup() throws ZigBeeClusterException{
        enableDefaultResponse();
        Response response = invoke(CMD_REMOVE_ALL_GROUP);
        return  new DefaultResponseImpl(response);
    }

    public Response removeGroup(int groupId) throws ZigBeeClusterException{
        RemoveGroupCommand removeGroupCmd = new RemoveGroupCommand(groupId);
        Response response = invoke(removeGroupCmd);
        return new RemoveGroupResponseImpl(response);
    }

    public Response viewGroup(int groupId) throws ZigBeeClusterException{
        ViewGroupCommand viewGroupCmd = new ViewGroupCommand(groupId);
        Response response = invoke(viewGroupCmd);
        return new ViewGroupResponseImpl(response);
    }

}
