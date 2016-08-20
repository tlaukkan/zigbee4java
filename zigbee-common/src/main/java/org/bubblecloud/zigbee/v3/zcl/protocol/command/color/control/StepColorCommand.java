package org.bubblecloud.zigbee.v3.zcl.protocol.command.color.control;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Step Color Command value object class.
 */
public class StepColorCommand extends ZclCommand {
    /**
     * StepX command message field.
     */
    private Integer stepX;
    /**
     * StepY command message field.
     */
    private Integer stepY;
    /**
     * Transition time command message field.
     */
    private Integer transitionTime;

    /**
     * Default constructor setting the command type field.
     */
    public StepColorCommand() {
        this.setType(ZclCommandType.STEP_COLOR_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public StepColorCommand(final ZclCommandMessage message) {
        super(message);
        this.stepX = (Integer) message.getFields().get(ZclFieldType.STEP_COLOR_COMMAND_STEPX);
        this.stepY = (Integer) message.getFields().get(ZclFieldType.STEP_COLOR_COMMAND_STEPY);
        this.transitionTime = (Integer) message.getFields().get(ZclFieldType.STEP_COLOR_COMMAND_TRANSITION_TIME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.STEP_COLOR_COMMAND_STEPX,stepX);
        message.getFields().put(ZclFieldType.STEP_COLOR_COMMAND_STEPY,stepY);
        message.getFields().put(ZclFieldType.STEP_COLOR_COMMAND_TRANSITION_TIME,transitionTime);
        return message;
    }

    /**
     * Gets StepX.
     * @return the StepX
     */
    public Integer getStepX() {
        return stepX;
    }

    /**
     * Sets StepX.
     * @param stepX the StepX
     */
    public void setStepX(final Integer stepX) {
        this.stepX = stepX;
    }

    /**
     * Gets StepY.
     * @return the StepY
     */
    public Integer getStepY() {
        return stepY;
    }

    /**
     * Sets StepY.
     * @param stepY the StepY
     */
    public void setStepY(final Integer stepY) {
        this.stepY = stepY;
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
        builder.append("stepX");
        builder.append('=');
        builder.append(stepX);
        builder.append(", ");
        builder.append("stepY");
        builder.append('=');
        builder.append(stepY);
        builder.append(", ");
        builder.append("transitionTime");
        builder.append('=');
        builder.append(transitionTime);
        return builder.toString();
    }

}
