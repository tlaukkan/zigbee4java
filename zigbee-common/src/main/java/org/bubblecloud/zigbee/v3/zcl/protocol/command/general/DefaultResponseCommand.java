package org.bubblecloud.zigbee.v3.zcl.protocol.command.general;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Default Response Command value object class.
 */
public class DefaultResponseCommand extends ZclCommand {
    /**
     * Command identifier command message field.
     */
    private Integer commandIdentifier;
    /**
     * Status code command message field.
     */
    private Integer statusCode;

    /**
     * Default constructor setting the command type field.
     */
    public DefaultResponseCommand() {
        this.setType(ZclCommandType.DEFAULT_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public DefaultResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.commandIdentifier = (Integer) message.getFields().get(ZclFieldType.DEFAULT_RESPONSE_COMMAND_COMMAND_IDENTIFIER);
        this.statusCode = (Integer) message.getFields().get(ZclFieldType.DEFAULT_RESPONSE_COMMAND_STATUS_CODE);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.DEFAULT_RESPONSE_COMMAND_COMMAND_IDENTIFIER,commandIdentifier);
        message.getFields().put(ZclFieldType.DEFAULT_RESPONSE_COMMAND_STATUS_CODE,statusCode);
        return message;
    }

    /**
     * Gets Command identifier.
     * @return the Command identifier
     */
    public Integer getCommandIdentifier() {
        return commandIdentifier;
    }

    /**
     * Sets Command identifier.
     * @param commandIdentifier the Command identifier
     */
    public void setCommandIdentifier(final Integer commandIdentifier) {
        this.commandIdentifier = commandIdentifier;
    }

    /**
     * Gets Status code.
     * @return the Status code
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * Sets Status code.
     * @param statusCode the Status code
     */
    public void setStatusCode(final Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("commandIdentifier");
        builder.append('=');
        builder.append(commandIdentifier);
        builder.append(", ");
        builder.append("statusCode");
        builder.append('=');
        builder.append(statusCode);
        return builder.toString();
    }

}
