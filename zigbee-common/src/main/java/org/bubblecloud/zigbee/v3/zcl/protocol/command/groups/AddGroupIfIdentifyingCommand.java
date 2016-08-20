package org.bubblecloud.zigbee.v3.zcl.protocol.command.groups;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Add Group If Identifying Command value object class.
 */
public class AddGroupIfIdentifyingCommand extends ZclCommand {
    /**
     * Group ID command message field.
     */
    private Integer groupId;
    /**
     * Group Name command message field.
     */
    private String groupName;

    /**
     * Default constructor setting the command type field.
     */
    public AddGroupIfIdentifyingCommand() {
        this.setType(ZclCommandType.ADD_GROUP_IF_IDENTIFYING_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public AddGroupIfIdentifyingCommand(final ZclCommandMessage message) {
        super(message);
        this.groupId = (Integer) message.getFields().get(ZclFieldType.ADD_GROUP_IF_IDENTIFYING_COMMAND_GROUP_ID);
        this.groupName = (String) message.getFields().get(ZclFieldType.ADD_GROUP_IF_IDENTIFYING_COMMAND_GROUP_NAME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.ADD_GROUP_IF_IDENTIFYING_COMMAND_GROUP_ID,groupId);
        message.getFields().put(ZclFieldType.ADD_GROUP_IF_IDENTIFYING_COMMAND_GROUP_NAME,groupName);
        return message;
    }

    /**
     * Gets Group ID.
     * @return the Group ID
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * Sets Group ID.
     * @param groupId the Group ID
     */
    public void setGroupId(final Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets Group Name.
     * @return the Group Name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets Group Name.
     * @param groupName the Group Name
     */
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("groupId");
        builder.append('=');
        builder.append(groupId);
        builder.append(", ");
        builder.append("groupName");
        builder.append('=');
        builder.append(groupName);
        return builder.toString();
    }

}
