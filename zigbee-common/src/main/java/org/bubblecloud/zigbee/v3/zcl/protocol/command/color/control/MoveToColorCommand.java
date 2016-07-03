package org.bubblecloud.zigbee.v3.zcl.protocol.command.color.control;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Move to Color Command value object class.
 */
public class MoveToColorCommand extends ZclCommand {
    /**
     * ColorX command message field.
     */
    private Integer colorX;
    /**
     * ColorY command message field.
     */
    private Integer colorY;
    /**
     * Transition time command message field.
     */
    private Integer transitionTime;

    /**
     * Default constructor setting the command type field.
     */
    public MoveToColorCommand() {
        this.setType(ZclCommandType.MOVE_TO_COLOR_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public MoveToColorCommand(final ZclCommandMessage message) {
        super(message);
        this.colorX = (Integer) message.getFields().get(ZclFieldType.MOVE_TO_COLOR_COMMAND_COLORX);
        this.colorY = (Integer) message.getFields().get(ZclFieldType.MOVE_TO_COLOR_COMMAND_COLORY);
        this.transitionTime = (Integer) message.getFields().get(ZclFieldType.MOVE_TO_COLOR_COMMAND_TRANSITION_TIME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.MOVE_TO_COLOR_COMMAND_COLORX,colorX);
        message.getFields().put(ZclFieldType.MOVE_TO_COLOR_COMMAND_COLORY,colorY);
        message.getFields().put(ZclFieldType.MOVE_TO_COLOR_COMMAND_TRANSITION_TIME,transitionTime);
        return message;
    }

    /**
     * Gets ColorX.
     * @return the ColorX
     */
    public Integer getColorX() {
        return colorX;
    }

    /**
     * Sets ColorX.
     * @param colorX the ColorX
     */
    public void setColorX(final Integer colorX) {
        this.colorX = colorX;
    }

    /**
     * Gets ColorY.
     * @return the ColorY
     */
    public Integer getColorY() {
        return colorY;
    }

    /**
     * Sets ColorY.
     * @param colorY the ColorY
     */
    public void setColorY(final Integer colorY) {
        this.colorY = colorY;
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
        builder.append("colorX");
        builder.append('=');
        builder.append(colorX);
        builder.append(", ");
        builder.append("colorY");
        builder.append('=');
        builder.append(colorY);
        builder.append(", ");
        builder.append("transitionTime");
        builder.append('=');
        builder.append(transitionTime);
        return builder.toString();
    }

}
