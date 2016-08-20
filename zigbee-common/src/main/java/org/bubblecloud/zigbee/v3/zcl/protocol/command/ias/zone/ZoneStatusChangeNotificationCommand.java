package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.zone;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Zone Status Change Notification Command value object class.
 */
public class ZoneStatusChangeNotificationCommand extends ZclCommand {
    /**
     * Zone Status command message field.
     */
    private Integer zoneStatus;
    /**
     * Extended Status command message field.
     */
    private Integer extendedStatus;

    /**
     * Default constructor setting the command type field.
     */
    public ZoneStatusChangeNotificationCommand() {
        this.setType(ZclCommandType.ZONE_STATUS_CHANGE_NOTIFICATION_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ZoneStatusChangeNotificationCommand(final ZclCommandMessage message) {
        super(message);
        this.zoneStatus = (Integer) message.getFields().get(ZclFieldType.ZONE_STATUS_CHANGE_NOTIFICATION_COMMAND_ZONE_STATUS);
        this.extendedStatus = (Integer) message.getFields().get(ZclFieldType.ZONE_STATUS_CHANGE_NOTIFICATION_COMMAND_EXTENDED_STATUS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.ZONE_STATUS_CHANGE_NOTIFICATION_COMMAND_ZONE_STATUS,zoneStatus);
        message.getFields().put(ZclFieldType.ZONE_STATUS_CHANGE_NOTIFICATION_COMMAND_EXTENDED_STATUS,extendedStatus);
        return message;
    }

    /**
     * Gets Zone Status.
     * @return the Zone Status
     */
    public Integer getZoneStatus() {
        return zoneStatus;
    }

    /**
     * Sets Zone Status.
     * @param zoneStatus the Zone Status
     */
    public void setZoneStatus(final Integer zoneStatus) {
        this.zoneStatus = zoneStatus;
    }

    /**
     * Gets Extended Status.
     * @return the Extended Status
     */
    public Integer getExtendedStatus() {
        return extendedStatus;
    }

    /**
     * Sets Extended Status.
     * @param extendedStatus the Extended Status
     */
    public void setExtendedStatus(final Integer extendedStatus) {
        this.extendedStatus = extendedStatus;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("zoneStatus");
        builder.append('=');
        builder.append(zoneStatus);
        builder.append(", ");
        builder.append("extendedStatus");
        builder.append('=');
        builder.append(extendedStatus);
        return builder.toString();
    }

}
