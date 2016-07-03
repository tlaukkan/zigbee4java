package org.bubblecloud.zigbee.v3.zcl.protocol.command.color.control;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Move to Hue Command value object class.
 */
public class MoveToHueCommand extends ZclCommand {
    /**
     * Hue command message field.
     */
    private Integer hue;
    /**
     * Direction command message field.
     */
    private Integer direction;
    /**
     * Transition time command message field.
     */
    private Integer transitionTime;

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
        this.hue = (Integer) message.getFields().get(ZclFieldType.MOVE_TO_HUE_COMMAND_HUE);
        this.direction = (Integer) message.getFields().get(ZclFieldType.MOVE_TO_HUE_COMMAND_DIRECTION);
        this.transitionTime = (Integer) message.getFields().get(ZclFieldType.MOVE_TO_HUE_COMMAND_TRANSITION_TIME);
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
    public Integer getHue() {
        return hue;
    }

    /**
     * Sets Hue.
     * @param hue the Hue
     */
    public void setHue(final Integer hue) {
        this.hue = hue;
    }

    /**
     * Gets Direction.
     * @return the Direction
     */
    public Integer getDirection() {
        return direction;
    }

    /**
     * Sets Direction.
     * @param direction the Direction
     */
    public void setDirection(final Integer direction) {
        this.direction = direction;
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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("hue");
        builder.append('=');
        builder.append(hue);
        builder.append(", ");
        builder.append("direction");
        builder.append('=');
        builder.append(direction);
        builder.append(", ");
        builder.append("transitionTime");
        builder.append('=');
        builder.append(transitionTime);
        return builder.toString();
    }

}
