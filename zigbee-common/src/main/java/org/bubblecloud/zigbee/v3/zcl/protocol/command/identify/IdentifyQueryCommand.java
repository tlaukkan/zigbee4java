package org.bubblecloud.zigbee.v3.zcl.protocol.command.identify;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


/**
 * Code generated Identify Query Command value object class.
 */
public class IdentifyQueryCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public IdentifyQueryCommand() {
        this.setType(ZclCommandType.IDENTIFY_QUERY_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public IdentifyQueryCommand(final ZclCommandMessage message) {
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
