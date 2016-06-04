package org.bubblecloud.zigbee.network.zcl.protocol.command.on.off;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Off Command value object class.
 */
public class OffCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public OffCommand() {
        this.setType(ZclCommandType.OFF_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public OffCommand(final ZclCommandMessage message) {
        super(message);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        return message;
    }

}
