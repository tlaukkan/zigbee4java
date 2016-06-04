package org.bubblecloud.zigbee.network.zcl.protocol.command.level.control;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Move Command value object class.
 */
public class MoveCommand extends ZclCommand {
    /**
     * Move mode command message field.
     */
    private Byte moveMode;
    /**
     * Rate command message field.
     */
    private Byte rate;

    /**
     * Default constructor setting the command type field.
     */
    public MoveCommand() {
        this.setType(ZclCommandType.MOVE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public MoveCommand(final ZclCommandMessage message) {
        super(message);
        this.moveMode = (Byte) message.getFields().get(ZclFieldType.MOVE_COMMAND_MOVE_MODE);
        this.rate = (Byte) message.getFields().get(ZclFieldType.MOVE_COMMAND_RATE);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.MOVE_COMMAND_MOVE_MODE,moveMode);
        message.getFields().put(ZclFieldType.MOVE_COMMAND_RATE,rate);
        return message;
    }

    /**
     * Gets Move mode.
     * @return the Move mode
     */
    public Byte getMoveMode() {
        return moveMode;
    }

    /**
     * Sets Move mode.
     * @param moveMode the Move mode
     */
    public void setMoveMode(final Byte moveMode) {
        this.moveMode = moveMode;
    }

    /**
     * Gets Rate.
     * @return the Rate
     */
    public Byte getRate() {
        return rate;
    }

    /**
     * Sets Rate.
     * @param rate the Rate
     */
    public void setRate(final Byte rate) {
        this.rate = rate;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.MOVE_COMMAND,MoveCommand.class);
    }
}
