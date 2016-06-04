package org.bubblecloud.zigbee.network.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

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

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.FIRE_COMMAND,FireCommand.class);
    }
}
