package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


/**
 * Code generated Fire Command value object class.
 */
public class FireCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public FireCommand() {
        this.setType(ZclCommandType.FIRE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public FireCommand(final ZclCommandMessage message) {
        super(message);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        return message;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        return builder.toString();
    }

}
