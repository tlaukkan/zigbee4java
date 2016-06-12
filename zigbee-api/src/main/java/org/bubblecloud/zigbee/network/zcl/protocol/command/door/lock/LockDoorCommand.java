package org.bubblecloud.zigbee.network.zcl.protocol.command.door.lock;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;


/**
 * Code generated Lock Door Command value object class.
 */
public class LockDoorCommand extends ZclCommand {
    /**
     * Pin code command message field.
     */
    private String pinCode;

    /**
     * Default constructor setting the command type field.
     */
    public LockDoorCommand() {
        this.setType(ZclCommandType.LOCK_DOOR_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public LockDoorCommand(final ZclCommandMessage message) {
        super(message);
        this.pinCode = (String) message.getFields().get(ZclFieldType.LOCK_DOOR_COMMAND_PIN_CODE);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.LOCK_DOOR_COMMAND_PIN_CODE,pinCode);
        return message;
    }

    /**
     * Gets Pin code.
     * @return the Pin code
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * Sets Pin code.
     * @param pinCode the Pin code
     */
    public void setPinCode(final String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("pinCode");
        builder.append('=');
        builder.append(pinCode);
        return builder.toString();
    }

}
