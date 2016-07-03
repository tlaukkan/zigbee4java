package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Anchor Node Announce Command value object class.
 */
public class AnchorNodeAnnounceCommand extends ZclCommand {
    /**
     * Anchor Node Address command message field.
     */
    private Long anchorNodeAddress;
    /**
     * Coordinate 1 command message field.
     */
    private Integer coordinate1;
    /**
     * Coordinate 2 command message field.
     */
    private Integer coordinate2;
    /**
     * Coordinate 3 command message field.
     */
    private Integer coordinate3;

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
        this.anchorNodeAddress = (Long) message.getFields().get(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_ANCHOR_NODE_ADDRESS);
        this.coordinate1 = (Integer) message.getFields().get(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_COORDINATE_1);
        this.coordinate2 = (Integer) message.getFields().get(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_COORDINATE_2);
        this.coordinate3 = (Integer) message.getFields().get(ZclFieldType.ANCHOR_NODE_ANNOUNCE_COMMAND_COORDINATE_3);
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
    public Long getAnchorNodeAddress() {
        return anchorNodeAddress;
    }

    /**
     * Sets Anchor Node Address.
     * @param anchorNodeAddress the Anchor Node Address
     */
    public void setAnchorNodeAddress(final Long anchorNodeAddress) {
        this.anchorNodeAddress = anchorNodeAddress;
    }

    /**
     * Gets Coordinate 1.
     * @return the Coordinate 1
     */
    public Integer getCoordinate1() {
        return coordinate1;
    }

    /**
     * Sets Coordinate 1.
     * @param coordinate1 the Coordinate 1
     */
    public void setCoordinate1(final Integer coordinate1) {
        this.coordinate1 = coordinate1;
    }

    /**
     * Gets Coordinate 2.
     * @return the Coordinate 2
     */
    public Integer getCoordinate2() {
        return coordinate2;
    }

    /**
     * Sets Coordinate 2.
     * @param coordinate2 the Coordinate 2
     */
    public void setCoordinate2(final Integer coordinate2) {
        this.coordinate2 = coordinate2;
    }

    /**
     * Gets Coordinate 3.
     * @return the Coordinate 3
     */
    public Integer getCoordinate3() {
        return coordinate3;
    }

    /**
     * Sets Coordinate 3.
     * @param coordinate3 the Coordinate 3
     */
    public void setCoordinate3(final Integer coordinate3) {
        this.coordinate3 = coordinate3;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("anchorNodeAddress");
        builder.append('=');
        builder.append(anchorNodeAddress);
        builder.append(", ");
        builder.append("coordinate1");
        builder.append('=');
        builder.append(coordinate1);
        builder.append(", ");
        builder.append("coordinate2");
        builder.append('=');
        builder.append(coordinate2);
        builder.append(", ");
        builder.append("coordinate3");
        builder.append('=');
        builder.append(coordinate3);
        return builder.toString();
    }

}
