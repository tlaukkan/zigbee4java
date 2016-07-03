package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated RSSI Ping Command value object class.
 */
public class RssiPingCommand extends ZclCommand {
    /**
     * Location Type command message field.
     */
    private Integer locationType;

    /**
     * Default constructor setting the command type field.
     */
    public RssiPingCommand() {
        this.setType(ZclCommandType.RSSI_PING_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public RssiPingCommand(final ZclCommandMessage message) {
        super(message);
        this.locationType = (Integer) message.getFields().get(ZclFieldType.RSSI_PING_COMMAND_LOCATION_TYPE);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.RSSI_PING_COMMAND_LOCATION_TYPE,locationType);
        return message;
    }

    /**
     * Gets Location Type.
     * @return the Location Type
     */
    public Integer getLocationType() {
        return locationType;
    }

    /**
     * Sets Location Type.
     * @param locationType the Location Type
     */
    public void setLocationType(final Integer locationType) {
        this.locationType = locationType;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("locationType");
        builder.append('=');
        builder.append(locationType);
        return builder.toString();
    }

}
