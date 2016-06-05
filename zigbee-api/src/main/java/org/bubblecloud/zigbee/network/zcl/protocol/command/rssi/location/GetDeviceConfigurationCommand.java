package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.zcl.field.*;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

import java.util.List;

/**
 * Code generated Get Device Configuration Command value object class.
 */
public class GetDeviceConfigurationCommand extends ZclCommand {
    /**
     * Target Address command message field.
     */
    private ZToolAddress64 targetAddress;

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
        this.targetAddress = (ZToolAddress64) message.getFields().get(ZclFieldType.GET_DEVICE_CONFIGURATION_COMMAND_TARGET_ADDRESS);
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
    public ZToolAddress64 getTargetAddress() {
        return targetAddress;
    }

    /**
     * Sets Target Address.
     * @param targetAddress the Target Address
     */
    public void setTargetAddress(final ZToolAddress64 targetAddress) {
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
