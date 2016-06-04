package org.bubblecloud.zigbee.network.zcl.protocol.command.color.control;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Step Hue Command value object class.
 */
public class StepHueCommand extends ZclCommand {
    /**
     * Step mode command message field.
     */
    private Byte stepMode;
    /**
     * Step size command message field.
     */
    private Byte stepSize;
    /**
     * Transition time command message field.
     */
    private Byte transitionTime;

    /**
     * Default constructor setting the command type field.
     */
    public StepHueCommand() {
        this.setType(ZclCommandType.STEP_HUE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public StepHueCommand(final ZclCommandMessage message) {
        super(message);
        this.stepMode = (Byte) message.getFields().get(ZclFieldType.STEP_HUE_COMMAND_STEP_MODE);
        this.stepSize = (Byte) message.getFields().get(ZclFieldType.STEP_HUE_COMMAND_STEP_SIZE);
        this.transitionTime = (Byte) message.getFields().get(ZclFieldType.STEP_HUE_COMMAND_TRANSITION_TIME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.STEP_HUE_COMMAND_STEP_MODE,stepMode);
        message.getFields().put(ZclFieldType.STEP_HUE_COMMAND_STEP_SIZE,stepSize);
        message.getFields().put(ZclFieldType.STEP_HUE_COMMAND_TRANSITION_TIME,transitionTime);
        return message;
    }

    /**
     * Gets Step mode.
     * @return the Step mode
     */
    public Byte getStepMode() {
        return stepMode;
    }

    /**
     * Sets Step mode.
     * @param stepMode the Step mode
     */
    public void setStepMode(final Byte stepMode) {
        this.stepMode = stepMode;
    }

    /**
     * Gets Step size.
     * @return the Step size
     */
    public Byte getStepSize() {
        return stepSize;
    }

    /**
     * Sets Step size.
     * @param stepSize the Step size
     */
    public void setStepSize(final Byte stepSize) {
        this.stepSize = stepSize;
    }

    /**
     * Gets Transition time.
     * @return the Transition time
     */
    public Byte getTransitionTime() {
        return transitionTime;
    }

    /**
     * Sets Transition time.
     * @param transitionTime the Transition time
     */
    public void setTransitionTime(final Byte transitionTime) {
        this.transitionTime = transitionTime;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.STEP_HUE_COMMAND,StepHueCommand.class);
    }
}
