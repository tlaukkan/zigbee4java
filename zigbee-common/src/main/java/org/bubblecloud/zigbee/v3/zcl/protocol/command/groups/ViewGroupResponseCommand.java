package org.bubblecloud.zigbee.v3.zcl.protocol.command.groups;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated View Group Response Command value object class.
 */
public class ViewGroupResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Integer status;
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
    public ViewGroupResponseCommand() {
        this.setType(ZclCommandType.VIEW_GROUP_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ViewGroupResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.status = (Integer) message.getFields().get(ZclFieldType.VIEW_GROUP_RESPONSE_COMMAND_STATUS);
        this.groupId = (Integer) message.getFields().get(ZclFieldType.VIEW_GROUP_RESPONSE_COMMAND_GROUP_ID);
        this.groupName = (String) message.getFields().get(ZclFieldType.VIEW_GROUP_RESPONSE_COMMAND_GROUP_NAME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.VIEW_GROUP_RESPONSE_COMMAND_STATUS,status);
        message.getFields().put(ZclFieldType.VIEW_GROUP_RESPONSE_COMMAND_GROUP_ID,groupId);
        message.getFields().put(ZclFieldType.VIEW_GROUP_RESPONSE_COMMAND_GROUP_NAME,groupName);
        return message;
    }

    /**
     * Gets Status.
     * @return the Status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets Status.
     * @param status the Status
     */
    public void setStatus(final Integer status) {
        this.status = status;
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
        builder.append("status");
        builder.append('=');
        builder.append(status);
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
