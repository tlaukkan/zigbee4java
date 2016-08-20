package org.bubblecloud.zigbee.v3.zcl.protocol.command.identify;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Identify Query Response Command value object class.
 */
public class IdentifyQueryResponseCommand extends ZclCommand {
    /**
     * Identify Time command message field.
     */
    private Integer identifyTime;

    /**
     * Default constructor setting the command type field.
     */
    public IdentifyQueryResponseCommand() {
        this.setType(ZclCommandType.IDENTIFY_QUERY_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public IdentifyQueryResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.identifyTime = (Integer) message.getFields().get(ZclFieldType.IDENTIFY_QUERY_RESPONSE_COMMAND_IDENTIFY_TIME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.IDENTIFY_QUERY_RESPONSE_COMMAND_IDENTIFY_TIME,identifyTime);
        return message;
    }

    /**
     * Gets Identify Time.
     * @return the Identify Time
     */
    public Integer getIdentifyTime() {
        return identifyTime;
    }

    /**
     * Sets Identify Time.
     * @param identifyTime the Identify Time
     */
    public void setIdentifyTime(final Integer identifyTime) {
        this.identifyTime = identifyTime;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("identifyTime");
        builder.append('=');
        builder.append(identifyTime);
        return builder.toString();
    }

}
