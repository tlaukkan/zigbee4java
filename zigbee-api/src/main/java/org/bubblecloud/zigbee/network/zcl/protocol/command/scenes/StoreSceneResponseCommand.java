package org.bubblecloud.zigbee.network.zcl.protocol.command.scenes;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Store Scene Response Command value object class.
 */
public class StoreSceneResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Byte status;
    /**
     * Group ID command message field.
     */
    private Short groupId;
    /**
     * Scene ID command message field.
     */
    private Byte sceneId;

    /**
     * Default constructor setting the command type field.
     */
    public StoreSceneResponseCommand() {
        this.setType(ZclCommandType.STORE_SCENE_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public StoreSceneResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.status = (Byte) message.getFields().get(ZclFieldType.STORE_SCENE_RESPONSE_COMMAND_STATUS);
        this.groupId = (Short) message.getFields().get(ZclFieldType.STORE_SCENE_RESPONSE_COMMAND_GROUP_ID);
        this.sceneId = (Byte) message.getFields().get(ZclFieldType.STORE_SCENE_RESPONSE_COMMAND_SCENE_ID);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.STORE_SCENE_RESPONSE_COMMAND_STATUS,status);
        message.getFields().put(ZclFieldType.STORE_SCENE_RESPONSE_COMMAND_GROUP_ID,groupId);
        message.getFields().put(ZclFieldType.STORE_SCENE_RESPONSE_COMMAND_SCENE_ID,sceneId);
        return message;
    }

    /**
     * Gets Status.
     * @return the Status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * Sets Status.
     * @param status the Status
     */
    public void setStatus(final Byte status) {
        this.status = status;
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

    /**
     * Gets Scene ID.
     * @return the Scene ID
     */
    public Byte getSceneId() {
        return sceneId;
    }

    /**
     * Sets Scene ID.
     * @param sceneId the Scene ID
     */
    public void setSceneId(final Byte sceneId) {
        this.sceneId = sceneId;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.STORE_SCENE_RESPONSE_COMMAND,StoreSceneResponseCommand.class);
    }
}
