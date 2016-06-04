package org.bubblecloud.zigbee.network.zcl.protocol.command.alarms;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

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

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.RESET_ALL_ALARMS_COMMAND,ResetAllAlarmsCommand.class);
    }
}
