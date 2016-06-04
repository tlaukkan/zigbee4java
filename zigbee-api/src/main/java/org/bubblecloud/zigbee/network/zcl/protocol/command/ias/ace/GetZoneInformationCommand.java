package org.bubblecloud.zigbee.network.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

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

}
