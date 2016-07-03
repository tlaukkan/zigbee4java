package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Get Device Configuration Command value object class.
 */
public class GetDeviceConfigurationCommand extends ZclCommand {
    /**
     * Target Address command message field.
     */
    private Long targetAddress;

    /**
     * Default constructor setting the command type field.
     */
    public GetDeviceConfigurationCommand() {
        this.setType(ZclCommandType.GET_DEVICE_CONFIGURATION_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetDeviceConfigurationCommand(final ZclCommandMessage message) {
        super(message);
        this.targetAddress = (Long) message.getFields().get(ZclFieldType.GET_DEVICE_CONFIGURATION_COMMAND_TARGET_ADDRESS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_DEVICE_CONFIGURATION_COMMAND_TARGET_ADDRESS,targetAddress);
        return message;
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
        builder.append("targetAddress");
        builder.append('=');
        builder.append(targetAddress);
        return builder.toString();
    }

}
