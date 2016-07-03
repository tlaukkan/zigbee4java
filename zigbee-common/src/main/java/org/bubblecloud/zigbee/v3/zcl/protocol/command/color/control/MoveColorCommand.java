package org.bubblecloud.zigbee.v3.zcl.protocol.command.color.control;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Move Color Command value object class.
 */
public class MoveColorCommand extends ZclCommand {
    /**
     * RateX command message field.
     */
    private Integer rateX;
    /**
     * RateY command message field.
     */
    private Integer rateY;

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
        this.rateX = (Integer) message.getFields().get(ZclFieldType.MOVE_COLOR_COMMAND_RATEX);
        this.rateY = (Integer) message.getFields().get(ZclFieldType.MOVE_COLOR_COMMAND_RATEY);
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
    public Integer getRateX() {
        return rateX;
    }

    /**
     * Sets RateX.
     * @param rateX the RateX
     */
    public void setRateX(final Integer rateX) {
        this.rateX = rateX;
    }

    /**
     * Gets RateY.
     * @return the RateY
     */
    public Integer getRateY() {
        return rateY;
    }

    /**
     * Sets RateY.
     * @param rateY the RateY
     */
    public void setRateY(final Integer rateY) {
        this.rateY = rateY;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("rateX");
        builder.append('=');
        builder.append(rateX);
        builder.append(", ");
        builder.append("rateY");
        builder.append('=');
        builder.append(rateY);
        return builder.toString();
    }

}
