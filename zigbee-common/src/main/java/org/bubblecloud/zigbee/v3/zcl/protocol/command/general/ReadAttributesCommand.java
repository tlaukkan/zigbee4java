package org.bubblecloud.zigbee.v3.zcl.protocol.command.general;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.v3.zcl.field.*;

import java.util.List;

/**
 * Code generated Read Attributes Command value object class.
 */
public class ReadAttributesCommand extends ZclCommand {
    /**
     * Identifiers command message field.
     */
    private List<AttributeIdentifier> identifiers;

    /**
     * Default constructor setting the command type field.
     */
    public ReadAttributesCommand() {
        this.setType(ZclCommandType.READ_ATTRIBUTES_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ReadAttributesCommand(final ZclCommandMessage message) {
        super(message);
        this.identifiers = (List<AttributeIdentifier>) message.getFields().get(ZclFieldType.READ_ATTRIBUTES_COMMAND_IDENTIFIERS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.READ_ATTRIBUTES_COMMAND_IDENTIFIERS,identifiers);
        return message;
    }

    /**
     * Gets Identifiers.
     * @return the Identifiers
     */
    public List<AttributeIdentifier> getIdentifiers() {
        return identifiers;
    }

    /**
     * Sets Identifiers.
     * @param identifiers the Identifiers
     */
    public void setIdentifiers(final List<AttributeIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("identifiers");
        builder.append('=');
        builder.append(identifiers);
        return builder.toString();
    }

}
