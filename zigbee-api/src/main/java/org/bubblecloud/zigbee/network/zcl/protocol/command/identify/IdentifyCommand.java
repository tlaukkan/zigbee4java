package org.bubblecloud.zigbee.network.zcl.protocol.command.identify;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.zcl.field.*;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

import java.util.List;

/**
 * Code generated Identify Command value object class.
 */
public class IdentifyCommand extends ZclCommand {
    /**
     * Identify Time command message field.
     */
    private Integer identifyTime;

    /**
     * Default constructor setting the command type field.
     */
    public IdentifyCommand() {
        this.setType(ZclCommandType.IDENTIFY_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public IdentifyCommand(final ZclCommandMessage message) {
        super(message);
        this.identifyTime = (Integer) message.getFields().get(ZclFieldType.IDENTIFY_COMMAND_IDENTIFY_TIME);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.IDENTIFY_COMMAND_IDENTIFY_TIME,identifyTime);
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
