package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Get Zone Information Response Command value object class.
 */
public class GetZoneInformationResponseCommand extends ZclCommand {
    /**
     * Zone ID command message field.
     */
    private Integer zoneId;
    /**
     * Zone Type command message field.
     */
    private Integer zoneType;
    /**
     * IEEE address command message field.
     */
    private Long ieeeAddress;

    /**
     * Default constructor setting the command type field.
     */
    public GetZoneInformationResponseCommand() {
        this.setType(ZclCommandType.GET_ZONE_INFORMATION_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetZoneInformationResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.zoneId = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_COMMAND_ZONE_ID);
        this.zoneType = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_COMMAND_ZONE_TYPE);
        this.ieeeAddress = (Long) message.getFields().get(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_COMMAND_IEEE_ADDRESS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_COMMAND_ZONE_ID,zoneId);
        message.getFields().put(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_COMMAND_ZONE_TYPE,zoneType);
        message.getFields().put(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_COMMAND_IEEE_ADDRESS,ieeeAddress);
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

    /**
     * Gets Zone Type.
     * @return the Zone Type
     */
    public Integer getZoneType() {
        return zoneType;
    }

    /**
     * Sets Zone Type.
     * @param zoneType the Zone Type
     */
    public void setZoneType(final Integer zoneType) {
        this.zoneType = zoneType;
    }

    /**
     * Gets IEEE address.
     * @return the IEEE address
     */
    public Long getIeeeAddress() {
        return ieeeAddress;
    }

    /**
     * Sets IEEE address.
     * @param ieeeAddress the IEEE address
     */
    public void setIeeeAddress(final Long ieeeAddress) {
        this.ieeeAddress = ieeeAddress;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("zoneId");
        builder.append('=');
        builder.append(zoneId);
        builder.append(", ");
        builder.append("zoneType");
        builder.append('=');
        builder.append(zoneType);
        builder.append(", ");
        builder.append("ieeeAddress");
        builder.append('=');
        builder.append(ieeeAddress);
        return builder.toString();
    }

}
