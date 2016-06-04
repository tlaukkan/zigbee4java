package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Set Absolute Location Command value object class.
 */
public class SetAbsoluteLocationCommand extends ZclCommand {
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
     * Power command message field.
     */
    private Short power;
    /**
     * Path Loss Exponent command message field.
     */
    private Short pathLossExponent;

    /**
     * Default constructor setting the command type field.
     */
    public SetAbsoluteLocationCommand() {
        this.setType(ZclCommandType.SET_ABSOLUTE_LOCATION_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public SetAbsoluteLocationCommand(final ZclCommandMessage message) {
        super(message);
        this.coordinate1 = (Short) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_COORDINATE_1);
        this.coordinate2 = (Short) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_COORDINATE_2);
        this.coordinate3 = (Short) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_COORDINATE_3);
        this.power = (Short) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_POWER);
        this.pathLossExponent = (Short) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_PATH_LOSS_EXPONENT);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_COORDINATE_1,coordinate1);
        message.getFields().put(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_COORDINATE_2,coordinate2);
        message.getFields().put(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_COORDINATE_3,coordinate3);
        message.getFields().put(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_POWER,power);
        message.getFields().put(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_PATH_LOSS_EXPONENT,pathLossExponent);
        return message;
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

    /**
     * Gets Power.
     * @return the Power
     */
    public Short getPower() {
        return power;
    }

    /**
     * Sets Power.
     * @param power the Power
     */
    public void setPower(final Short power) {
        this.power = power;
    }

    /**
     * Gets Path Loss Exponent.
     * @return the Path Loss Exponent
     */
    public Short getPathLossExponent() {
        return pathLossExponent;
    }

    /**
     * Sets Path Loss Exponent.
     * @param pathLossExponent the Path Loss Exponent
     */
    public void setPathLossExponent(final Short pathLossExponent) {
        this.pathLossExponent = pathLossExponent;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.SET_ABSOLUTE_LOCATION_COMMAND,SetAbsoluteLocationCommand.class);
    }
}
