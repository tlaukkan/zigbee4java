package org.bubblecloud.zigbee.v3.zcl.protocol.command.general;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Read Attributes Structured Command value object class.
 */
public class ReadAttributesStructuredCommand extends ZclCommand {
    /**
     * Attribute selectors command message field.
     */
    private Object attributeSelectors;

    /**
     * Default constructor setting the command type field.
     */
    public ReadAttributesStructuredCommand() {
        this.setType(ZclCommandType.READ_ATTRIBUTES_STRUCTURED_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ReadAttributesStructuredCommand(final ZclCommandMessage message) {
        super(message);
        this.attributeSelectors = (Object) message.getFields().get(ZclFieldType.READ_ATTRIBUTES_STRUCTURED_COMMAND_ATTRIBUTE_SELECTORS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.READ_ATTRIBUTES_STRUCTURED_COMMAND_ATTRIBUTE_SELECTORS,attributeSelectors);
        return message;
    }

    /**
     * Gets Attribute selectors.
     * @return the Attribute selectors
     */
    public Object getAttributeSelectors() {
        return attributeSelectors;
    }

    /**
     * Sets Attribute selectors.
     * @param attributeSelectors the Attribute selectors
     */
    public void setAttributeSelectors(final Object attributeSelectors) {
        this.attributeSelectors = attributeSelectors;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("attributeSelectors");
        builder.append('=');
        builder.append(attributeSelectors);
        return builder.toString();
    }

}
