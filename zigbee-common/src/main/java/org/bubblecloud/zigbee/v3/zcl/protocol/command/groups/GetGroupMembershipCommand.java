package org.bubblecloud.zigbee.v3.zcl.protocol.command.groups;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.v3.zcl.field.*;

import java.util.List;

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
    private List<Unsigned16BitInteger> groupList;

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
        this.groupList = (List<Unsigned16BitInteger>) message.getFields().get(ZclFieldType.GET_GROUP_MEMBERSHIP_COMMAND_GROUP_LIST);
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
    public List<Unsigned16BitInteger> getGroupList() {
        return groupList;
    }

    /**
     * Sets Group list.
     * @param groupList the Group list
     */
    public void setGroupList(final List<Unsigned16BitInteger> groupList) {
        this.groupList = groupList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("groupCount");
        builder.append('=');
        builder.append(groupCount);
        builder.append(", ");
        builder.append("groupList");
        builder.append('=');
        builder.append(groupList);
        return builder.toString();
    }

}
