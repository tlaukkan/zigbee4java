package org.bubblecloud.zigbee.network.zcl.protocol.command.basic;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

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

}
