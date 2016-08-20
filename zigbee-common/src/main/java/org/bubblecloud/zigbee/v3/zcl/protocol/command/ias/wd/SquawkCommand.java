package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.wd;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Squawk Command value object class.
 */
public class SquawkCommand extends ZclCommand {
    /**
     * Header command message field.
     */
    private Integer header;

    /**
     * Default constructor setting the command type field.
     */
    public SquawkCommand() {
        this.setType(ZclCommandType.SQUAWK_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public SquawkCommand(final ZclCommandMessage message) {
        super(message);
        this.header = (Integer) message.getFields().get(ZclFieldType.SQUAWK_COMMAND_HEADER);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.SQUAWK_COMMAND_HEADER,header);
        return message;
    }

    /**
     * Gets Header.
     * @return the Header
     */
    public Integer getHeader() {
        return header;
    }

    /**
     * Sets Header.
     * @param header the Header
     */
    public void setHeader(final Integer header) {
        this.header = header;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("header");
        builder.append('=');
        builder.append(header);
        return builder.toString();
    }

}
