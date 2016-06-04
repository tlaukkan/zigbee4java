package org.bubblecloud.zigbee.network.zcl.protocol.command.general;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Discover Attributes Command value object class.
 */
public class DiscoverAttributesCommand extends ZclCommand {
    /**
     * Start attribute identifier command message field.
     */
    private Integer startAttributeIdentifier;
    /**
     * Maximum attribute identifiers command message field.
     */
    private Integer maximumAttributeIdentifiers;

    /**
     * Default constructor setting the command type field.
     */
    public DiscoverAttributesCommand() {
        this.setType(ZclCommandType.DISCOVER_ATTRIBUTES_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public DiscoverAttributesCommand(final ZclCommandMessage message) {
        super(message);
        this.startAttributeIdentifier = (Integer) message.getFields().get(ZclFieldType.DISCOVER_ATTRIBUTES_COMMAND_START_ATTRIBUTE_IDENTIFIER);
        this.maximumAttributeIdentifiers = (Integer) message.getFields().get(ZclFieldType.DISCOVER_ATTRIBUTES_COMMAND_MAXIMUM_ATTRIBUTE_IDENTIFIERS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.DISCOVER_ATTRIBUTES_COMMAND_START_ATTRIBUTE_IDENTIFIER,startAttributeIdentifier);
        message.getFields().put(ZclFieldType.DISCOVER_ATTRIBUTES_COMMAND_MAXIMUM_ATTRIBUTE_IDENTIFIERS,maximumAttributeIdentifiers);
        return message;
    }

    /**
     * Gets Start attribute identifier.
     * @return the Start attribute identifier
     */
    public Integer getStartAttributeIdentifier() {
        return startAttributeIdentifier;
    }

    /**
     * Sets Start attribute identifier.
     * @param startAttributeIdentifier the Start attribute identifier
     */
    public void setStartAttributeIdentifier(final Integer startAttributeIdentifier) {
        this.startAttributeIdentifier = startAttributeIdentifier;
    }

    /**
     * Gets Maximum attribute identifiers.
     * @return the Maximum attribute identifiers
     */
    public Integer getMaximumAttributeIdentifiers() {
        return maximumAttributeIdentifiers;
    }

    /**
     * Sets Maximum attribute identifiers.
     * @param maximumAttributeIdentifiers the Maximum attribute identifiers
     */
    public void setMaximumAttributeIdentifiers(final Integer maximumAttributeIdentifiers) {
        this.maximumAttributeIdentifiers = maximumAttributeIdentifiers;
    }

}
