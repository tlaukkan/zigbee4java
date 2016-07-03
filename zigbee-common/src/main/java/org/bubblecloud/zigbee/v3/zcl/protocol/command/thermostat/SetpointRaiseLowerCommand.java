package org.bubblecloud.zigbee.v3.zcl.protocol.command.thermostat;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Setpoint Raise/Lower Command value object class.
 */
public class SetpointRaiseLowerCommand extends ZclCommand {
    /**
     * Mode command message field.
     */
    private Integer mode;
    /**
     * Amount command message field.
     */
    private Integer amount;

    /**
     * Default constructor setting the command type field.
     */
    public SetpointRaiseLowerCommand() {
        this.setType(ZclCommandType.SETPOINT_RAISE_LOWER_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public SetpointRaiseLowerCommand(final ZclCommandMessage message) {
        super(message);
        this.mode = (Integer) message.getFields().get(ZclFieldType.SETPOINT_RAISE_LOWER_COMMAND_MODE);
        this.amount = (Integer) message.getFields().get(ZclFieldType.SETPOINT_RAISE_LOWER_COMMAND_AMOUNT);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.SETPOINT_RAISE_LOWER_COMMAND_MODE,mode);
        message.getFields().put(ZclFieldType.SETPOINT_RAISE_LOWER_COMMAND_AMOUNT,amount);
        return message;
    }

    /**
     * Gets Mode.
     * @return the Mode
     */
    public Integer getMode() {
        return mode;
    }

    /**
     * Sets Mode.
     * @param mode the Mode
     */
    public void setMode(final Integer mode) {
        this.mode = mode;
    }

    /**
     * Gets Amount.
     * @return the Amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets Amount.
     * @param amount the Amount
     */
    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("mode");
        builder.append('=');
        builder.append(mode);
        builder.append(", ");
        builder.append("amount");
        builder.append('=');
        builder.append(amount);
        return builder.toString();
    }

}
