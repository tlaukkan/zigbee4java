package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


/**
 * Code generated Compact Location Data Notification Command value object class.
 */
public class CompactLocationDataNotificationCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public CompactLocationDataNotificationCommand() {
        this.setType(ZclCommandType.COMPACT_LOCATION_DATA_NOTIFICATION_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public CompactLocationDataNotificationCommand(final ZclCommandMessage message) {
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
