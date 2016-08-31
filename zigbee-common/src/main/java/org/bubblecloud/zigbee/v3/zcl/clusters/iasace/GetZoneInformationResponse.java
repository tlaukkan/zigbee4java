package org.bubblecloud.zigbee.v3.zcl.clusters.iasace;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 *  Get Zone Information Response value object class.
 * 
 * 
 * Cluster: IAS ACE
 * The IAS ACE cluster defines an interface to the functionality of any Ancillary
 * Control Equipment of the IAS system. Using this cluster, a ZigBee enabled ACE
 * device can access a IAS CIE device and manipulate the IAS system, on behalf of a
 * level-2 user.
 * 
 * Code is autogenerated. Modifications may be overwritten!
 */
public class GetZoneInformationResponse extends ZclCommand {
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
    public GetZoneInformationResponse() {
        setType(ZclCommandType.GET_ZONE_INFORMATION_RESPONSE);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetZoneInformationResponse(final ZclCommandMessage message) {
        super(message);
        this.zoneId = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_ZONE_ID);
        this.zoneType = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_ZONE_TYPE);
        this.ieeeAddress = (Long) message.getFields().get(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_IEEE_ADDRESS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_ZONE_ID,zoneId);
        message.getFields().put(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_ZONE_TYPE,zoneType);
        message.getFields().put(ZclFieldType.GET_ZONE_INFORMATION_RESPONSE_IEEE_ADDRESS,ieeeAddress);
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