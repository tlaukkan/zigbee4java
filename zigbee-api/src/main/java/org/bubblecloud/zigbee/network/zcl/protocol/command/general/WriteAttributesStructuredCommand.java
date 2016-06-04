package org.bubblecloud.zigbee.network.zcl.protocol.command.general;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Write Attributes Structured Command value object class.
 */
public class WriteAttributesStructuredCommand extends ZclCommand {
    /**
     * Attribute selectors command message field.
     */
    private Object attributeSelectors;

    /**
     * Default constructor setting the command type field.
     */
    public WriteAttributesStructuredCommand() {
        this.setType(ZclCommandType.WRITE_ATTRIBUTES_STRUCTURED_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public WriteAttributesStructuredCommand(final ZclCommandMessage message) {
        super(message);
        this.attributeSelectors = (Object) message.getFields().get(ZclFieldType.WRITE_ATTRIBUTES_STRUCTURED_COMMAND_ATTRIBUTE_SELECTORS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.WRITE_ATTRIBUTES_STRUCTURED_COMMAND_ATTRIBUTE_SELECTORS,attributeSelectors);
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

}
