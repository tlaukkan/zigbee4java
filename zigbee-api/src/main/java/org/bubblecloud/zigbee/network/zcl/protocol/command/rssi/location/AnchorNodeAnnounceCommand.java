package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Anchor Node Announce Command value object class.
 */
public class AnchorNodeAnnounceCommand extends ZclCommand {
    /**
     * Anchor Node Address command message field.
     */
    private ZToolAddress64 anchorNodeAddress;
    /**
     * Coordinate 1 command message field.
     */
    private Short coordinate1;
    /**
     * Coordinate 2 command message field.
     */
    private Short coordinate2;
    /**
     * Coordinate 3 command message field.
     */
    private Short coordinate3;

    /**
     * Default constructor setting the command type field.
     */
    public AnchorNodeAnnounceCommand() {
        this.setType(ZclCommandType.ANCHOR_NODE_ANNOUNCE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public AnchorNodeAnnounceCommand(final ZclCommandMessage message) {
        super(message);
        this.anchorNodeAddress = (ZToolAddress64) message.getFields().get(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_ANCHOR_NODE_ADDRESS);
        this.coordinate1 = (Short) message.getFields().get(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_COORDINATE_1);
        this.coordinate2 = (Short) message.getFields().get(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_COORDINATE_2);
        this.coordinate3 = (Short) message.getFields().get(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_COORDINATE_3);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_ANCHOR_NODE_ADDRESS,anchorNodeAddress);
        message.getFields().put(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_COORDINATE_1,coordinate1);
        message.getFields().put(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_COORDINATE_2,coordinate2);
        message.getFields().put(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_COORDINATE_3,coordinate3);
        return message;
    }

    /**
     * Gets Anchor Node Address.
     * @return the Anchor Node Address
     */
    public ZToolAddress64 getAnchorNodeAddress() {
        return anchorNodeAddress;
    }

    /**
     * Sets Anchor Node Address.
     * @param anchorNodeAddress the Anchor Node Address
     */
    public void setAnchorNodeAddress(final ZToolAddress64 anchorNodeAddress) {
        this.anchorNodeAddress = anchorNodeAddress;
    }

    /**
     * Gets Coordinate 1.
     * @return the Coordinate 1
     */
    public Short getCoordinate1() {
        return coordinate1;
    }

    /**
     * Sets Coordinate 1.
     * @param coordinate1 the Coordinate 1
     */
    public void setCoordinate1(final Short coordinate1) {
        this.coordinate1 = coordinate1;
    }

    /**
     * Gets Coordinate 2.
     * @return the Coordinate 2
     */
    public Short getCoordinate2() {
        return coordinate2;
    }

    /**
     * Sets Coordinate 2.
     * @param coordinate2 the Coordinate 2
     */
    public void setCoordinate2(final Short coordinate2) {
        this.coordinate2 = coordinate2;
    }

    /**
     * Gets Coordinate 3.
     * @return the Coordinate 3
     */
    public Short getCoordinate3() {
        return coordinate3;
    }

    /**
     * Sets Coordinate 3.
     * @param coordinate3 the Coordinate 3
     */
    public void setCoordinate3(final Short coordinate3) {
        this.coordinate3 = coordinate3;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.ANCHOR_NODE_ANNOUNCE_COMMAND,AnchorNodeAnnounceCommand.class);
    }
}
