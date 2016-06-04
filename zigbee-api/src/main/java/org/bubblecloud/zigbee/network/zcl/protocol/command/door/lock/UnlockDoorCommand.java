package org.bubblecloud.zigbee.network.zcl.protocol.command.door.lock;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Unlock Door Command value object class.
 */
public class UnlockDoorCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public UnlockDoorCommand() {
        this.setType(ZclCommandType.UNLOCK_DOOR_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public UnlockDoorCommand(final ZclCommandMessage message) {
        super(message);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        return message;
    }

}
