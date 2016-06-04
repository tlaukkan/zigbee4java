package org.bubblecloud.zigbee.network.zcl.protocol.command.color.control;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Move Color Command value object class.
 */
public class MoveColorCommand extends ZclCommand {
    /**
     * RateX command message field.
     */
    private Short rateX;
    /**
     * RateY command message field.
     */
    private Short rateY;

    /**
     * Default constructor setting the command type field.
     */
    public MoveColorCommand() {
        this.setType(ZclCommandType.MOVE_COLOR_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public MoveColorCommand(final ZclCommandMessage message) {
        super(message);
        this.rateX = (Short) message.getFields().get(ZclFieldType.MOVE_COLOR_COMMAND_RATEX);
        this.rateY = (Short) message.getFields().get(ZclFieldType.MOVE_COLOR_COMMAND_RATEY);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.MOVE_COLOR_COMMAND_RATEX,rateX);
        message.getFields().put(ZclFieldType.MOVE_COLOR_COMMAND_RATEY,rateY);
        return message;
    }

    /**
     * Gets RateX.
     * @return the RateX
     */
    public Short getRateX() {
        return rateX;
    }

    /**
     * Sets RateX.
     * @param rateX the RateX
     */
    public void setRateX(final Short rateX) {
        this.rateX = rateX;
    }

    /**
     * Gets RateY.
     * @return the RateY
     */
    public Short getRateY() {
        return rateY;
    }

    /**
     * Sets RateY.
     * @param rateY the RateY
     */
    public void setRateY(final Short rateY) {
        this.rateY = rateY;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.MOVE_COLOR_COMMAND,MoveColorCommand.class);
    }
}
