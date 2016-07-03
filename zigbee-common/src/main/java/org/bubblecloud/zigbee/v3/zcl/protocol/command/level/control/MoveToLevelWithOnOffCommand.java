package org.bubblecloud.zigbee.v3.zcl.protocol.command.level.control;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Move to Level (with On/Off) Command value object class.
 */
public class MoveToLevelWithOnOffCommand extends ZclCommand {
    /**
     * Level command message field.
     */
    private Integer level;
    /**
     * Transition time command message field.
     */
    private Integer transitionTime;

    /**
     * Default constructor setting the command type field.
     */
    public MoveToLevelWithOnOffCommand() {
        this.setType(ZclCommandType.MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public MoveToLevelWithOnOffCommand(final ZclCommandMessage message) {
        super(message);
        this.level = (Integer) message.getFields().get(ZclFieldType.MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND_LEVEL);
        this.transitionTime = (Integer) message.getFields().get(ZclFieldType.MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND_TRANSITION_TIME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND_LEVEL,level);
        message.getFields().put(ZclFieldType.MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND_TRANSITION_TIME,transitionTime);
        return message;
    }

    /**
     * Gets Level.
     * @return the Level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * Sets Level.
     * @param level the Level
     */
    public void setLevel(final Integer level) {
        this.level = level;
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
        builder.append("level");
        builder.append('=');
        builder.append(level);
        builder.append(", ");
        builder.append("transitionTime");
        builder.append('=');
        builder.append(transitionTime);
        return builder.toString();
    }

}
