package org.bubblecloud.zigbee.network.zcl.protocol.command.level.control;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Move to Level (with On/Off) Command value object class.
 */
public class MoveToLevelWithOnOffCommand extends ZclCommand {
    /**
     * Level command message field.
     */
    private Byte level;
    /**
     * Transition time command message field.
     */
    private Short transitionTime;

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
        this.level = (Byte) message.getFields().get(ZclFieldType.MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND_LEVEL);
        this.transitionTime = (Short) message.getFields().get(ZclFieldType.MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND_TRANSITION_TIME);
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
    public Byte getLevel() {
        return level;
    }

    /**
     * Sets Level.
     * @param level the Level
     */
    public void setLevel(final Byte level) {
        this.level = level;
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
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND,MoveToLevelWithOnOffCommand.class);
    }
}
