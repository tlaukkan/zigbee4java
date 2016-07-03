package org.bubblecloud.zigbee.v3.zcl.protocol.command.scenes;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.v3.zcl.field.*;

import java.util.List;

/**
 * Code generated Get Scene Membership Response Command value object class.
 */
public class GetSceneMembershipResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Integer status;
    /**
     * Capacity command message field.
     */
    private Integer capacity;
    /**
     * Group ID command message field.
     */
    private Integer groupId;
    /**
     * Scene count command message field.
     */
    private Integer sceneCount;
    /**
     * Scene list command message field.
     */
    private List<Unsigned8BitInteger> sceneList;

    /**
     * Default constructor setting the command type field.
     */
    public GetSceneMembershipResponseCommand() {
        this.setType(ZclCommandType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetSceneMembershipResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.status = (Integer) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_STATUS);
        this.capacity = (Integer) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_CAPACITY);
        this.groupId = (Integer) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_GROUP_ID);
        this.sceneCount = (Integer) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_SCENE_COUNT);
        this.sceneList = (List<Unsigned8BitInteger>) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_SCENE_LIST);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_STATUS,status);
        message.getFields().put(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_CAPACITY,capacity);
        message.getFields().put(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_GROUP_ID,groupId);
        message.getFields().put(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_SCENE_COUNT,sceneCount);
        message.getFields().put(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_SCENE_LIST,sceneList);
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
     * Gets Scene count.
     * @return the Scene count
     */
    public Integer getSceneCount() {
        return sceneCount;
    }

    /**
     * Sets Scene count.
     * @param sceneCount the Scene count
     */
    public void setSceneCount(final Integer sceneCount) {
        this.sceneCount = sceneCount;
    }

    /**
     * Gets Scene list.
     * @return the Scene list
     */
    public List<Unsigned8BitInteger> getSceneList() {
        return sceneList;
    }

    /**
     * Sets Scene list.
     * @param sceneList the Scene list
     */
    public void setSceneList(final List<Unsigned8BitInteger> sceneList) {
        this.sceneList = sceneList;
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
        builder.append("capacity");
        builder.append('=');
        builder.append(capacity);
        builder.append(", ");
        builder.append("groupId");
        builder.append('=');
        builder.append(groupId);
        builder.append(", ");
        builder.append("sceneCount");
        builder.append('=');
        builder.append(sceneCount);
        builder.append(", ");
        builder.append("sceneList");
        builder.append('=');
        builder.append(sceneList);
        return builder.toString();
    }

}
