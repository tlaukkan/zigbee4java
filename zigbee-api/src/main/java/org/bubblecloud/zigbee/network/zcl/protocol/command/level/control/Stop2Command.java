package org.bubblecloud.zigbee.network.zcl.protocol.command.level.control;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

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

}
