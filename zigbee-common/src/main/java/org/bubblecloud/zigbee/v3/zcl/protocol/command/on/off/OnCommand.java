package org.bubblecloud.zigbee.v3.zcl.protocol.command.on.off;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


/**
 * Code generated On Command value object class.
 */
public class OnCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public OnCommand() {
        this.setType(ZclCommandType.ON_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public OnCommand(final ZclCommandMessage message) {
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
