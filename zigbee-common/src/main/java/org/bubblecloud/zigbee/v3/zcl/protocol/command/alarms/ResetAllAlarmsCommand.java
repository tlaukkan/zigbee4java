package org.bubblecloud.zigbee.v3.zcl.protocol.command.alarms;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


/**
 * Code generated Reset All Alarms Command value object class.
 */
public class ResetAllAlarmsCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public ResetAllAlarmsCommand() {
        this.setType(ZclCommandType.RESET_ALL_ALARMS_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ResetAllAlarmsCommand(final ZclCommandMessage message) {
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
