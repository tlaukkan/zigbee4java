package org.bubblecloud.zigbee.network.zcl.protocol.command.general;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Read Attributes Command value object class.
 */
public class ReadAttributesCommand extends ZclCommand {
    /**
     * Identifiers command message field.
     */
    private Object identifiers;

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
        this.identifiers = (Object) message.getFields().get(ZclFieldType.READ_ATTRIBUTES_COMMAND_IDENTIFIERS);
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
    public Object getIdentifiers() {
        return identifiers;
    }

    /**
     * Sets Identifiers.
     * @param identifiers the Identifiers
     */
    public void setIdentifiers(final Object identifiers) {
        this.identifiers = identifiers;
    }

}
