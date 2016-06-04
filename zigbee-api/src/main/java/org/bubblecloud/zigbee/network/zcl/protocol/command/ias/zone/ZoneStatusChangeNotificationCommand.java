package org.bubblecloud.zigbee.network.zcl.protocol.command.ias.zone;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Zone Status Change Notification Command value object class.
 */
public class ZoneStatusChangeNotificationCommand extends ZclCommand {
    /**
     * Zone Status command message field.
     */
    private Short zoneStatus;
    /**
     * Extended Status command message field.
     */
    private Byte extendedStatus;

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
        this.zoneStatus = (Short) message.getFields().get(ZclFieldType.ZONE_STATUS_CHANGE_NOTIFICATION_COMMAND_ZONE_STATUS);
        this.extendedStatus = (Byte) message.getFields().get(ZclFieldType.ZONE_STATUS_CHANGE_NOTIFICATION_COMMAND_EXTENDED_STATUS);
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
    public Short getZoneStatus() {
        return zoneStatus;
    }

    /**
     * Sets Zone Status.
     * @param zoneStatus the Zone Status
     */
    public void setZoneStatus(final Short zoneStatus) {
        this.zoneStatus = zoneStatus;
    }

    /**
     * Gets Extended Status.
     * @return the Extended Status
     */
    public Byte getExtendedStatus() {
        return extendedStatus;
    }

    /**
     * Sets Extended Status.
     * @param extendedStatus the Extended Status
     */
    public void setExtendedStatus(final Byte extendedStatus) {
        this.extendedStatus = extendedStatus;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.ZONE_STATUS_CHANGE_NOTIFICATION_COMMAND,ZoneStatusChangeNotificationCommand.class);
    }
}
