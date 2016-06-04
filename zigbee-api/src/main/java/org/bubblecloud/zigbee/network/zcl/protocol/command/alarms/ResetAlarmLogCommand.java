package org.bubblecloud.zigbee.network.zcl.protocol.command.alarms;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Reset Alarm Log Command value object class.
 */
public class ResetAlarmLogCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public ResetAlarmLogCommand() {
        this.setType(ZclCommandType.RESET_ALARM_LOG_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ResetAlarmLogCommand(final ZclCommandMessage message) {
        super(message);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        return message;
    }

}
