package org.bubblecloud.zigbee.network.zcl.protocol.command.level.control;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Step (with On/Off) Command value object class.
 */
public class StepWithOnOffCommand extends ZclCommand {
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
    private Short transitionTime;

    /**
     * Default constructor setting the command type field.
     */
    public StepWithOnOffCommand() {
        this.setType(ZclCommandType.STEP__WITH_ON_OFF__COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public StepWithOnOffCommand(final ZclCommandMessage message) {
        super(message);
        this.stepMode = (Byte) message.getFields().get(ZclFieldType.STEP__WITH_ON_OFF__COMMAND_STEP_MODE);
        this.stepSize = (Byte) message.getFields().get(ZclFieldType.STEP__WITH_ON_OFF__COMMAND_STEP_SIZE);
        this.transitionTime = (Short) message.getFields().get(ZclFieldType.STEP__WITH_ON_OFF__COMMAND_TRANSITION_TIME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.STEP__WITH_ON_OFF__COMMAND_STEP_MODE,stepMode);
        message.getFields().put(ZclFieldType.STEP__WITH_ON_OFF__COMMAND_STEP_SIZE,stepSize);
        message.getFields().put(ZclFieldType.STEP__WITH_ON_OFF__COMMAND_TRANSITION_TIME,transitionTime);
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
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.STEP__WITH_ON_OFF__COMMAND,StepWithOnOffCommand.class);
    }
}
