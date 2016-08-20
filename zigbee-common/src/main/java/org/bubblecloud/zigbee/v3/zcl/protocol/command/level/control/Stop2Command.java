package org.bubblecloud.zigbee.v3.zcl.protocol.command.level.control;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


/**
 * Code generated Stop 2 Command value object class.
 */
public class Stop2Command extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public Stop2Command() {
        this.setType(ZclCommandType.STOP_2_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public Stop2Command(final ZclCommandMessage message) {
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
