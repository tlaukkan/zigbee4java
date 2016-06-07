package org.bubblecloud.zigbee.network.zcl.protocol.command.door.lock;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;


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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        return builder.toString();
    }

}
