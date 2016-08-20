package org.bubblecloud.zigbee.v3.zcl.protocol.command.alarms;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        return builder.toString();
    }

}
