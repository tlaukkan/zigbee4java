package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


/**
 * Code generated Get Zone ID Map Command value object class.
 */
public class GetZoneIdMapCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public GetZoneIdMapCommand() {
        this.setType(ZclCommandType.GET_ZONE_ID_MAP_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetZoneIdMapCommand(final ZclCommandMessage message) {
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
