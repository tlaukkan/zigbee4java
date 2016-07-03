package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Get Location Data Command value object class.
 */
public class GetLocationDataCommand extends ZclCommand {
    /**
     * Header command message field.
     */
    private Integer header;
    /**
     * Number Responses command message field.
     */
    private Integer numberResponses;
    /**
     * Target Address command message field.
     */
    private Long targetAddress;

    /**
     * Default constructor setting the command type field.
     */
    public GetLocationDataCommand() {
        this.setType(ZclCommandType.GET_LOCATION_DATA_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetLocationDataCommand(final ZclCommandMessage message) {
        super(message);
        this.header = (Integer) message.getFields().get(ZclFieldType.GET_LOCATION_DATA_COMMAND_HEADER);
        this.numberResponses = (Integer) message.getFields().get(ZclFieldType.GET_LOCATION_DATA_COMMAND_NUMBER_RESPONSES);
        this.targetAddress = (Long) message.getFields().get(ZclFieldType.GET_LOCATION_DATA_COMMAND_TARGET_ADDRESS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_LOCATION_DATA_COMMAND_HEADER,header);
        message.getFields().put(ZclFieldType.GET_LOCATION_DATA_COMMAND_NUMBER_RESPONSES,numberResponses);
        message.getFields().put(ZclFieldType.GET_LOCATION_DATA_COMMAND_TARGET_ADDRESS,targetAddress);
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

    /**
     * Gets Number Responses.
     * @return the Number Responses
     */
    public Integer getNumberResponses() {
        return numberResponses;
    }

    /**
     * Sets Number Responses.
     * @param numberResponses the Number Responses
     */
    public void setNumberResponses(final Integer numberResponses) {
        this.numberResponses = numberResponses;
    }

    /**
     * Gets Target Address.
     * @return the Target Address
     */
    public Long getTargetAddress() {
        return targetAddress;
    }

    /**
     * Sets Target Address.
     * @param targetAddress the Target Address
     */
    public void setTargetAddress(final Long targetAddress) {
        this.targetAddress = targetAddress;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("header");
        builder.append('=');
        builder.append(header);
        builder.append(", ");
        builder.append("numberResponses");
        builder.append('=');
        builder.append(numberResponses);
        builder.append(", ");
        builder.append("targetAddress");
        builder.append('=');
        builder.append(targetAddress);
        return builder.toString();
    }

}
