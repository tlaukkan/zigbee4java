package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Request Own Location Command value object class.
 */
public class RequestOwnLocationCommand extends ZclCommand {
    /**
     * Requesting Address command message field.
     */
    private ZToolAddress64 requestingAddress;

    /**
     * Default constructor setting the command type field.
     */
    public RequestOwnLocationCommand() {
        this.setType(ZclCommandType.REQUEST_OWN_LOCATION_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public RequestOwnLocationCommand(final ZclCommandMessage message) {
        super(message);
        this.requestingAddress = (ZToolAddress64) message.getFields().get(ZclFieldType.REQUEST_OWN_LOCATION_COMMAND_REQUESTING_ADDRESS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.REQUEST_OWN_LOCATION_COMMAND_REQUESTING_ADDRESS,requestingAddress);
        return message;
    }

    /**
     * Gets Requesting Address.
     * @return the Requesting Address
     */
    public ZToolAddress64 getRequestingAddress() {
        return requestingAddress;
    }

    /**
     * Sets Requesting Address.
     * @param requestingAddress the Requesting Address
     */
    public void setRequestingAddress(final ZToolAddress64 requestingAddress) {
        this.requestingAddress = requestingAddress;
    }

}
