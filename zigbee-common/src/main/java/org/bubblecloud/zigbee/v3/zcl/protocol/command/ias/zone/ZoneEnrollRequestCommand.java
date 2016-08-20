package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.zone;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Zone Enroll Request Command value object class.
 */
public class ZoneEnrollRequestCommand extends ZclCommand {
    /**
     * Zone Type command message field.
     */
    private Integer zoneType;
    /**
     * Manufacturer Code command message field.
     */
    private Integer manufacturerCode;

    /**
     * Default constructor setting the command type field.
     */
    public ZoneEnrollRequestCommand() {
        this.setType(ZclCommandType.ZONE_ENROLL_REQUEST_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ZoneEnrollRequestCommand(final ZclCommandMessage message) {
        super(message);
        this.zoneType = (Integer) message.getFields().get(ZclFieldType.ZONE_ENROLL_REQUEST_COMMAND_ZONE_TYPE);
        this.manufacturerCode = (Integer) message.getFields().get(ZclFieldType.ZONE_ENROLL_REQUEST_COMMAND_MANUFACTURER_CODE);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.ZONE_ENROLL_REQUEST_COMMAND_ZONE_TYPE,zoneType);
        message.getFields().put(ZclFieldType.ZONE_ENROLL_REQUEST_COMMAND_MANUFACTURER_CODE,manufacturerCode);
        return message;
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
     * Gets Manufacturer Code.
     * @return the Manufacturer Code
     */
    public Integer getManufacturerCode() {
        return manufacturerCode;
    }

    /**
     * Sets Manufacturer Code.
     * @param manufacturerCode the Manufacturer Code
     */
    public void setManufacturerCode(final Integer manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("zoneType");
        builder.append('=');
        builder.append(zoneType);
        builder.append(", ");
        builder.append("manufacturerCode");
        builder.append('=');
        builder.append(manufacturerCode);
        return builder.toString();
    }

}
