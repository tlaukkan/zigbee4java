package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Get Zone Information Command value object class.
 */
public class GetZoneInformationCommand extends ZclCommand {
    /**
     * Zone ID command message field.
     */
    private Integer zoneId;

    /**
     * Default constructor setting the command type field.
     */
    public GetZoneInformationCommand() {
        this.setType(ZclCommandType.GET_ZONE_INFORMATION_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetZoneInformationCommand(final ZclCommandMessage message) {
        super(message);
        this.zoneId = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_INFORMATION_COMMAND_ZONE_ID);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_ZONE_INFORMATION_COMMAND_ZONE_ID,zoneId);
        return message;
    }

    /**
     * Gets Zone ID.
     * @return the Zone ID
     */
    public Integer getZoneId() {
        return zoneId;
    }

    /**
     * Sets Zone ID.
     * @param zoneId the Zone ID
     */
    public void setZoneId(final Integer zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("zoneId");
        builder.append('=');
        builder.append(zoneId);
        return builder.toString();
    }

}
