package org.bubblecloud.zigbee.network.zcl.protocol.command.ias.zone;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.zcl.type.*;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

import java.util.List;

/**
 * Code generated Zone Enroll Response Command value object class.
 */
public class ZoneEnrollResponseCommand extends ZclCommand {
    /**
     * Enroll response code command message field.
     */
    private Integer enrollResponseCode;
    /**
     * Zone ID command message field.
     */
    private Integer zoneId;

    /**
     * Default constructor setting the command type field.
     */
    public ZoneEnrollResponseCommand() {
        this.setType(ZclCommandType.ZONE_ENROLL_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ZoneEnrollResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.enrollResponseCode = (Integer) message.getFields().get(ZclFieldType.ZONE_ENROLL_RESPONSE_COMMAND_ENROLL_RESPONSE_CODE);
        this.zoneId = (Integer) message.getFields().get(ZclFieldType.ZONE_ENROLL_RESPONSE_COMMAND_ZONE_ID);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.ZONE_ENROLL_RESPONSE_COMMAND_ENROLL_RESPONSE_CODE,enrollResponseCode);
        message.getFields().put(ZclFieldType.ZONE_ENROLL_RESPONSE_COMMAND_ZONE_ID,zoneId);
        return message;
    }

    /**
     * Gets Enroll response code.
     * @return the Enroll response code
     */
    public Integer getEnrollResponseCode() {
        return enrollResponseCode;
    }

    /**
     * Sets Enroll response code.
     * @param enrollResponseCode the Enroll response code
     */
    public void setEnrollResponseCode(final Integer enrollResponseCode) {
        this.enrollResponseCode = enrollResponseCode;
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
        builder.append("enrollResponseCode");
        builder.append('=');
        builder.append(enrollResponseCode);
        builder.append(", ");
        builder.append("zoneId");
        builder.append('=');
        builder.append(zoneId);
        return builder.toString();
    }

}
