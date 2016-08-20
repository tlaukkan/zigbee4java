package org.bubblecloud.zigbee.v3.zcl.protocol.command.scenes;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Store Scene Command value object class.
 */
public class StoreSceneCommand extends ZclCommand {
    /**
     * Group ID command message field.
     */
    private Integer groupId;
    /**
     * Scene ID command message field.
     */
    private Integer sceneId;

    /**
     * Default constructor setting the command type field.
     */
    public StoreSceneCommand() {
        this.setType(ZclCommandType.STORE_SCENE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public StoreSceneCommand(final ZclCommandMessage message) {
        super(message);
        this.groupId = (Integer) message.getFields().get(ZclFieldType.STORE_SCENE_COMMAND_GROUP_ID);
        this.sceneId = (Integer) message.getFields().get(ZclFieldType.STORE_SCENE_COMMAND_SCENE_ID);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.STORE_SCENE_COMMAND_GROUP_ID,groupId);
        message.getFields().put(ZclFieldType.STORE_SCENE_COMMAND_SCENE_ID,sceneId);
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
     * Gets Scene ID.
     * @return the Scene ID
     */
    public Integer getSceneId() {
        return sceneId;
    }

    /**
     * Sets Scene ID.
     * @param sceneId the Scene ID
     */
    public void setSceneId(final Integer sceneId) {
        this.sceneId = sceneId;
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
        builder.append("sceneId");
        builder.append('=');
        builder.append(sceneId);
        return builder.toString();
    }

}
