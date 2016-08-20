package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Request Own Location Command value object class.
 */
public class RequestOwnLocationCommand extends ZclCommand {
    /**
     * Requesting Address command message field.
     */
    private Long requestingAddress;

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
        this.requestingAddress = (Long) message.getFields().get(ZclFieldType.REQUEST_OWN_LOCATION_COMMAND_REQUESTING_ADDRESS);
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
    public Long getRequestingAddress() {
        return requestingAddress;
    }

    /**
     * Sets Requesting Address.
     * @param requestingAddress the Requesting Address
     */
    public void setRequestingAddress(final Long requestingAddress) {
        this.requestingAddress = requestingAddress;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("requestingAddress");
        builder.append('=');
        builder.append(requestingAddress);
        return builder.toString();
    }

}
