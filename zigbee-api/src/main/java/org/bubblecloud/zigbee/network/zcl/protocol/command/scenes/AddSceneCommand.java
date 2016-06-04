package org.bubblecloud.zigbee.network.zcl.protocol.command.scenes;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Add Scene Command value object class.
 */
public class AddSceneCommand extends ZclCommand {
    /**
     * Group ID command message field.
     */
    private Short groupId;
    /**
     * Scene ID command message field.
     */
    private Byte sceneId;
    /**
     * Transition time command message field.
     */
    private Short transitionTime;
    /**
     * Scene Name command message field.
     */
    private String sceneName;
    /**
     * Extension field sets command message field.
     */
    private Object extensionFieldSets;

    /**
     * Default constructor setting the command type field.
     */
    public AddSceneCommand() {
        this.setType(ZclCommandType.ADD_SCENE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public AddSceneCommand(final ZclCommandMessage message) {
        super(message);
        this.groupId = (Short) message.getFields().get(ZclFieldType.ADD_SCENE_COMMAND_GROUP_ID);
        this.sceneId = (Byte) message.getFields().get(ZclFieldType.ADD_SCENE_COMMAND_SCENE_ID);
        this.transitionTime = (Short) message.getFields().get(ZclFieldType.ADD_SCENE_COMMAND_TRANSITION_TIME);
        this.sceneName = (String) message.getFields().get(ZclFieldType.ADD_SCENE_COMMAND_SCENE_NAME);
        this.extensionFieldSets = (Object) message.getFields().get(ZclFieldType.ADD_SCENE_COMMAND_EXTENSION_FIELD_SETS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.ADD_SCENE_COMMAND_GROUP_ID,groupId);
        message.getFields().put(ZclFieldType.ADD_SCENE_COMMAND_SCENE_ID,sceneId);
        message.getFields().put(ZclFieldType.ADD_SCENE_COMMAND_TRANSITION_TIME,transitionTime);
        message.getFields().put(ZclFieldType.ADD_SCENE_COMMAND_SCENE_NAME,sceneName);
        message.getFields().put(ZclFieldType.ADD_SCENE_COMMAND_EXTENSION_FIELD_SETS,extensionFieldSets);
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

    /**
     * Gets Transition time.
     * @return the Transition time
     */
    public Short getTransitionTime() {
        return transitionTime;
    }

    /**
     * Sets Transition time.
     * @param transitionTime the Transition time
     */
    public void setTransitionTime(final Short transitionTime) {
        this.transitionTime = transitionTime;
    }

    /**
     * Gets Scene Name.
     * @return the Scene Name
     */
    public String getSceneName() {
        return sceneName;
    }

    /**
     * Sets Scene Name.
     * @param sceneName the Scene Name
     */
    public void setSceneName(final String sceneName) {
        this.sceneName = sceneName;
    }

    /**
     * Gets Extension field sets.
     * @return the Extension field sets
     */
    public Object getExtensionFieldSets() {
        return extensionFieldSets;
    }

    /**
     * Sets Extension field sets.
     * @param extensionFieldSets the Extension field sets
     */
    public void setExtensionFieldSets(final Object extensionFieldSets) {
        this.extensionFieldSets = extensionFieldSets;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.ADD_SCENE_COMMAND,AddSceneCommand.class);
    }
}
