package org.bubblecloud.zigbee.network.zcl.protocol.command.color.control;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Move to Hue Command value object class.
 */
public class MoveToHueCommand extends ZclCommand {
    /**
     * Hue command message field.
     */
    private Byte hue;
    /**
     * Direction command message field.
     */
    private Byte direction;
    /**
     * Transition time command message field.
     */
    private Short transitionTime;

    /**
     * Default constructor setting the command type field.
     */
    public MoveToHueCommand() {
        this.setType(ZclCommandType.MOVE_TO_HUE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public MoveToHueCommand(final ZclCommandMessage message) {
        super(message);
        this.hue = (Byte) message.getFields().get(ZclFieldType.MOVE_TO_HUE_COMMAND_HUE);
        this.direction = (Byte) message.getFields().get(ZclFieldType.MOVE_TO_HUE_COMMAND_DIRECTION);
        this.transitionTime = (Short) message.getFields().get(ZclFieldType.MOVE_TO_HUE_COMMAND_TRANSITION_TIME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.MOVE_TO_HUE_COMMAND_HUE,hue);
        message.getFields().put(ZclFieldType.MOVE_TO_HUE_COMMAND_DIRECTION,direction);
        message.getFields().put(ZclFieldType.MOVE_TO_HUE_COMMAND_TRANSITION_TIME,transitionTime);
        return message;
    }

    /**
     * Gets Hue.
     * @return the Hue
     */
    public Byte getHue() {
        return hue;
    }

    /**
     * Sets Hue.
     * @param hue the Hue
     */
    public void setHue(final Byte hue) {
        this.hue = hue;
    }

    /**
     * Gets Direction.
     * @return the Direction
     */
    public Byte getDirection() {
        return direction;
    }

    /**
     * Sets Direction.
     * @param direction the Direction
     */
    public void setDirection(final Byte direction) {
        this.direction = direction;
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

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.MOVE_TO_HUE_COMMAND,MoveToHueCommand.class);
    }
}
