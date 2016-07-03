package org.bubblecloud.zigbee.v3.zcl.protocol.command.groups;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.v3.zcl.field.*;

import java.util.List;

/**
 * Code generated Get Group Membership Response Command value object class.
 */
public class GetGroupMembershipResponseCommand extends ZclCommand {
    /**
     * Capacity command message field.
     */
    private Integer capacity;
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
    public GetGroupMembershipResponseCommand() {
        this.setType(ZclCommandType.GET_GROUP_MEMBERSHIP_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetGroupMembershipResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.capacity = (Integer) message.getFields().get(ZclFieldType.GET_GROUP_MEMBERSHIP_RESPONSE_COMMAND_CAPACITY);
        this.groupCount = (Integer) message.getFields().get(ZclFieldType.GET_GROUP_MEMBERSHIP_RESPONSE_COMMAND_GROUP_COUNT);
        this.groupList = (List<Unsigned16BitInteger>) message.getFields().get(ZclFieldType.GET_GROUP_MEMBERSHIP_RESPONSE_COMMAND_GROUP_LIST);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_GROUP_MEMBERSHIP_RESPONSE_COMMAND_CAPACITY,capacity);
        message.getFields().put(ZclFieldType.GET_GROUP_MEMBERSHIP_RESPONSE_COMMAND_GROUP_COUNT,groupCount);
        message.getFields().put(ZclFieldType.GET_GROUP_MEMBERSHIP_RESPONSE_COMMAND_GROUP_LIST,groupList);
        return message;
    }

    /**
     * Gets Capacity.
     * @return the Capacity
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Sets Capacity.
     * @param capacity the Capacity
     */
    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
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
        builder.append("capacity");
        builder.append('=');
        builder.append(capacity);
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
