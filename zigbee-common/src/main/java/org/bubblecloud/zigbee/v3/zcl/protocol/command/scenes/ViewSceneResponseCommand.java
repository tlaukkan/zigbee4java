package org.bubblecloud.zigbee.v3.zcl.protocol.command.scenes;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.v3.zcl.field.*;

import java.util.List;

/**
 * Code generated View Scene Response Command value object class.
 */
public class ViewSceneResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Integer status;
    /**
     * Group ID command message field.
     */
    private Integer groupId;
    /**
     * Scene ID command message field.
     */
    private Integer sceneId;
    /**
     * Transition time command message field.
     */
    private Integer transitionTime;
    /**
     * Scene Name command message field.
     */
    private String sceneName;
    /**
     * Extension field sets command message field.
     */
    private List<ExtensionFieldSet> extensionFieldSets;

    /**
     * Default constructor setting the command type field.
     */
    public ViewSceneResponseCommand() {
        this.setType(ZclCommandType.VIEW_SCENE_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ViewSceneResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.status = (Integer) message.getFields().get(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_STATUS);
        this.groupId = (Integer) message.getFields().get(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_GROUP_ID);
        this.sceneId = (Integer) message.getFields().get(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_SCENE_ID);
        this.transitionTime = (Integer) message.getFields().get(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_TRANSITION_TIME);
        this.sceneName = (String) message.getFields().get(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_SCENE_NAME);
        this.extensionFieldSets = (List<ExtensionFieldSet>) message.getFields().get(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_EXTENSION_FIELD_SETS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_STATUS,status);
        message.getFields().put(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_GROUP_ID,groupId);
        message.getFields().put(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_SCENE_ID,sceneId);
        message.getFields().put(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_TRANSITION_TIME,transitionTime);
        message.getFields().put(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_SCENE_NAME,sceneName);
        message.getFields().put(ZclFieldType.VIEW_SCENE_RESPONSE_COMMAND_EXTENSION_FIELD_SETS,extensionFieldSets);
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

    /**
     * Gets Transition time.
     * @return the Transition time
     */
    public Integer getTransitionTime() {
        return transitionTime;
    }

    /**
     * Sets Transition time.
     * @param transitionTime the Transition time
     */
    public void setTransitionTime(final Integer transitionTime) {
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
    public List<ExtensionFieldSet> getExtensionFieldSets() {
        return extensionFieldSets;
    }

    /**
     * Sets Extension field sets.
     * @param extensionFieldSets the Extension field sets
     */
    public void setExtensionFieldSets(final List<ExtensionFieldSet> extensionFieldSets) {
        this.extensionFieldSets = extensionFieldSets;
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
        builder.append("sceneId");
        builder.append('=');
        builder.append(sceneId);
        builder.append(", ");
        builder.append("transitionTime");
        builder.append('=');
        builder.append(transitionTime);
        builder.append(", ");
        builder.append("sceneName");
        builder.append('=');
        builder.append(sceneName);
        builder.append(", ");
        builder.append("extensionFieldSets");
        builder.append('=');
        builder.append(extensionFieldSets);
        return builder.toString();
    }

}
