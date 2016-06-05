package org.bubblecloud.zigbee.network.zcl.protocol.command.scenes;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.zcl.field.*;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

import java.util.List;

/**
 * Code generated Get Scene Membership Command value object class.
 */
public class GetSceneMembershipCommand extends ZclCommand {
    /**
     * Group ID command message field.
     */
    private Integer groupId;

    /**
     * Default constructor setting the command type field.
     */
    public GetSceneMembershipCommand() {
        this.setType(ZclCommandType.GET_SCENE_MEMBERSHIP_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetSceneMembershipCommand(final ZclCommandMessage message) {
        super(message);
        this.groupId = (Integer) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_COMMAND_GROUP_ID);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_SCENE_MEMBERSHIP_COMMAND_GROUP_ID,groupId);
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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("groupId");
        builder.append('=');
        builder.append(groupId);
        return builder.toString();
    }

}
