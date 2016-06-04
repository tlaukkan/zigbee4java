package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated RSSI Ping Command value object class.
 */
public class RssiPingCommand extends ZclCommand {
    /**
     * Location Type command message field.
     */
    private Byte locationType;

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
        this.locationType = (Byte) message.getFields().get(ZclFieldType.RSSI_PING_COMMAND_LOCATION_TYPE);
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
    public Byte getLocationType() {
        return locationType;
    }

    /**
     * Sets Location Type.
     * @param locationType the Location Type
     */
    public void setLocationType(final Byte locationType) {
        this.locationType = locationType;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.RSSI_PING_COMMAND,RssiPingCommand.class);
    }
}
