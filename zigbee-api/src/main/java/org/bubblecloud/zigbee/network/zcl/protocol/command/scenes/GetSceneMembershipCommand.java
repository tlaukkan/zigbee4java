package org.bubblecloud.zigbee.network.zcl.protocol.command.scenes;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Get Scene Membership Command value object class.
 */
public class GetSceneMembershipCommand extends ZclCommand {
    /**
     * Group ID command message field.
     */
    private Short groupId;

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
        this.groupId = (Short) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_COMMAND_GROUP_ID);
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
    public Short getGroupId() {
        return groupId;
    }

    /**
     * Sets Group ID.
     * @param groupId the Group ID
     */
    public void setGroupId(final Short groupId) {
        this.groupId = groupId;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.GET_SCENE_MEMBERSHIP_COMMAND,GetSceneMembershipCommand.class);
    }
}
