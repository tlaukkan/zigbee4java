package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


/**
 * Code generated Panic Command value object class.
 */
public class PanicCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public PanicCommand() {
        this.setType(ZclCommandType.PANIC_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public PanicCommand(final ZclCommandMessage message) {
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
