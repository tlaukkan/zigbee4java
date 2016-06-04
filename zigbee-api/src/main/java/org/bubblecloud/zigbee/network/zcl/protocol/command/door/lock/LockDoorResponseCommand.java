package org.bubblecloud.zigbee.network.zcl.protocol.command.door.lock;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Lock Door Response Command value object class.
 */
public class LockDoorResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Integer status;

    /**
     * Default constructor setting the command type field.
     */
    public LockDoorResponseCommand() {
        this.setType(ZclCommandType.LOCK_DOOR_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public LockDoorResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.status = (Integer) message.getFields().get(ZclFieldType.LOCK_DOOR_RESPONSE_COMMAND_STATUS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.LOCK_DOOR_RESPONSE_COMMAND_STATUS,status);
        return message;
    }

    /**
     * Gets Status.
     * @return the Status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets Status.
     * @param status the Status
     */
    public void setStatus(final Integer status) {
        this.status = status;
    }

}
