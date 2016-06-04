package org.bubblecloud.zigbee.network.zcl.protocol.command.color.control;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Step Color Command value object class.
 */
public class StepColorCommand extends ZclCommand {
    /**
     * StepX command message field.
     */
    private Short stepX;
    /**
     * StepY command message field.
     */
    private Short stepY;
    /**
     * Transition time command message field.
     */
    private Short transitionTime;

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
        this.stepX = (Short) message.getFields().get(ZclFieldType.STEP_COLOR_COMMAND_STEPX);
        this.stepY = (Short) message.getFields().get(ZclFieldType.STEP_COLOR_COMMAND_STEPY);
        this.transitionTime = (Short) message.getFields().get(ZclFieldType.STEP_COLOR_COMMAND_TRANSITION_TIME);
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
    public Short getStepX() {
        return stepX;
    }

    /**
     * Sets StepX.
     * @param stepX the StepX
     */
    public void setStepX(final Short stepX) {
        this.stepX = stepX;
    }

    /**
     * Gets StepY.
     * @return the StepY
     */
    public Short getStepY() {
        return stepY;
    }

    /**
     * Sets StepY.
     * @param stepY the StepY
     */
    public void setStepY(final Short stepY) {
        this.stepY = stepY;
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
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.STEP_COLOR_COMMAND,StepColorCommand.class);
    }
}
