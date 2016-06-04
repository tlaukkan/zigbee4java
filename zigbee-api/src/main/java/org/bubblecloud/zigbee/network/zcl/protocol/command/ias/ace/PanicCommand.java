package org.bubblecloud.zigbee.network.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Panic Command value object class.
 */
public class PanicCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public PanicCommand() {
        this.setType(ZclCommandType.PANIC_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public PanicCommand(final ZclCommandMessage message) {
        super(message);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        return message;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.PANIC_COMMAND,PanicCommand.class);
    }
}
