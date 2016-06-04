package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

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

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.COMPACT_LOCATION_DATA_NOTIFICATION_COMMAND,CompactLocationDataNotificationCommand.class);
    }
}
