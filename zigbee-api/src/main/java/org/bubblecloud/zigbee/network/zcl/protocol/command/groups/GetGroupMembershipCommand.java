package org.bubblecloud.zigbee.network.zcl.protocol.command.groups;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Get Group Membership Command value object class.
 */
public class GetGroupMembershipCommand extends ZclCommand {
    /**
     * Group count command message field.
     */
    private Integer groupCount;
    /**
     * Group list command message field.
     */
    private Object groupList;

    /**
     * Default constructor setting the command type field.
     */
    public GetGroupMembershipCommand() {
        this.setType(ZclCommandType.GET_GROUP_MEMBERSHIP_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetGroupMembershipCommand(final ZclCommandMessage message) {
        super(message);
        this.groupCount = (Integer) message.getFields().get(ZclFieldType.GET_GROUP_MEMBERSHIP_COMMAND_GROUP_COUNT);
        this.groupList = (Object) message.getFields().get(ZclFieldType.GET_GROUP_MEMBERSHIP_COMMAND_GROUP_LIST);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_GROUP_MEMBERSHIP_COMMAND_GROUP_COUNT,groupCount);
        message.getFields().put(ZclFieldType.GET_GROUP_MEMBERSHIP_COMMAND_GROUP_LIST,groupList);
        return message;
    }

    /**
     * Gets Group count.
     * @return the Group count
     */
    public Integer getGroupCount() {
        return groupCount;
    }

    /**
     * Sets Group count.
     * @param groupCount the Group count
     */
    public void setGroupCount(final Integer groupCount) {
        this.groupCount = groupCount;
    }

    /**
     * Gets Group list.
     * @return the Group list
     */
    public Object getGroupList() {
        return groupList;
    }

    /**
     * Sets Group list.
     * @param groupList the Group list
     */
    public void setGroupList(final Object groupList) {
        this.groupList = groupList;
    }

}
