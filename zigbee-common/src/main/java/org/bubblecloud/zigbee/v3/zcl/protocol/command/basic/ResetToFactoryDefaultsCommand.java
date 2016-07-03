package org.bubblecloud.zigbee.v3.zcl.protocol.command.basic;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


/**
 * Code generated Reset to Factory Defaults Command value object class.
 */
public class ResetToFactoryDefaultsCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public ResetToFactoryDefaultsCommand() {
        this.setType(ZclCommandType.RESET_TO_FACTORY_DEFAULTS_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ResetToFactoryDefaultsCommand(final ZclCommandMessage message) {
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
