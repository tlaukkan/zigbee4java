package org.bubblecloud.zigbee.network.zcl.protocol.command.ias.zone;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Zone Enroll Request Command value object class.
 */
public class ZoneEnrollRequestCommand extends ZclCommand {
    /**
     * Zone Type command message field.
     */
    private Short zoneType;
    /**
     * Manufacturer Code command message field.
     */
    private Short manufacturerCode;

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
        this.zoneType = (Short) message.getFields().get(ZclFieldType.ZONE_ENROLL_REQUEST_COMMAND_ZONE_TYPE);
        this.manufacturerCode = (Short) message.getFields().get(ZclFieldType.ZONE_ENROLL_REQUEST_COMMAND_MANUFACTURER_CODE);
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
    public Short getZoneType() {
        return zoneType;
    }

    /**
     * Sets Zone Type.
     * @param zoneType the Zone Type
     */
    public void setZoneType(final Short zoneType) {
        this.zoneType = zoneType;
    }

    /**
     * Gets Manufacturer Code.
     * @return the Manufacturer Code
     */
    public Short getManufacturerCode() {
        return manufacturerCode;
    }

    /**
     * Sets Manufacturer Code.
     * @param manufacturerCode the Manufacturer Code
     */
    public void setManufacturerCode(final Short manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.ZONE_ENROLL_REQUEST_COMMAND,ZoneEnrollRequestCommand.class);
    }
}
