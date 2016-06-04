package org.bubblecloud.zigbee.network.zcl.protocol.command.general;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Discover Attributes Response Command value object class.
 */
public class DiscoverAttributesResponseCommand extends ZclCommand {
    /**
     * Command identifier command message field.
     */
    private Boolean commandIdentifier;
    /**
     * Information command message field.
     */
    private Object information;

    /**
     * Default constructor setting the command type field.
     */
    public DiscoverAttributesResponseCommand() {
        this.setType(ZclCommandType.DISCOVER_ATTRIBUTES_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public DiscoverAttributesResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.commandIdentifier = (Boolean) message.getFields().get(ZclFieldType.DISCOVER_ATTRIBUTES_RESPONSE_COMMAND_COMMAND_IDENTIFIER);
        this.information = (Object) message.getFields().get(ZclFieldType.DISCOVER_ATTRIBUTES_RESPONSE_COMMAND_INFORMATION);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.DISCOVER_ATTRIBUTES_RESPONSE_COMMAND_COMMAND_IDENTIFIER,commandIdentifier);
        message.getFields().put(ZclFieldType.DISCOVER_ATTRIBUTES_RESPONSE_COMMAND_INFORMATION,information);
        return message;
    }

    /**
     * Gets Command identifier.
     * @return the Command identifier
     */
    public Boolean getCommandIdentifier() {
        return commandIdentifier;
    }

    /**
     * Sets Command identifier.
     * @param commandIdentifier the Command identifier
     */
    public void setCommandIdentifier(final Boolean commandIdentifier) {
        this.commandIdentifier = commandIdentifier;
    }

    /**
     * Gets Information.
     * @return the Information
     */
    public Object getInformation() {
        return information;
    }

    /**
     * Sets Information.
     * @param information the Information
     */
    public void setInformation(final Object information) {
        this.information = information;
    }

}
