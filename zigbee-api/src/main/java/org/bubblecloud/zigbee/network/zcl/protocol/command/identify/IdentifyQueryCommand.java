package org.bubblecloud.zigbee.network.zcl.protocol.command.identify;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.zcl.field.*;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

import java.util.List;

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
