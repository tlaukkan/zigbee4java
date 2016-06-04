package org.bubblecloud.zigbee.network.zcl.protocol.command.scenes;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Get Scene Membership Response Command value object class.
 */
public class GetSceneMembershipResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Byte status;
    /**
     * Capacity command message field.
     */
    private Byte capacity;
    /**
     * Group ID command message field.
     */
    private Short groupId;
    /**
     * Scene count command message field.
     */
    private Byte sceneCount;
    /**
     * Scene list command message field.
     */
    private Object sceneList;

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
        this.status = (Byte) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_STATUS);
        this.capacity = (Byte) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_CAPACITY);
        this.groupId = (Short) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_GROUP_ID);
        this.sceneCount = (Byte) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_SCENE_COUNT);
        this.sceneList = (Object) message.getFields().get(ZclFieldType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND_SCENE_LIST);
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
     * Gets Capacity.
     * @return the Capacity
     */
    public Byte getCapacity() {
        return capacity;
    }

    /**
     * Sets Capacity.
     * @param capacity the Capacity
     */
    public void setCapacity(final Byte capacity) {
        this.capacity = capacity;
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
     * Gets Scene count.
     * @return the Scene count
     */
    public Byte getSceneCount() {
        return sceneCount;
    }

    /**
     * Sets Scene count.
     * @param sceneCount the Scene count
     */
    public void setSceneCount(final Byte sceneCount) {
        this.sceneCount = sceneCount;
    }

    /**
     * Gets Scene list.
     * @return the Scene list
     */
    public Object getSceneList() {
        return sceneList;
    }

    /**
     * Sets Scene list.
     * @param sceneList the Scene list
     */
    public void setSceneList(final Object sceneList) {
        this.sceneList = sceneList;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND,GetSceneMembershipResponseCommand.class);
    }
}
