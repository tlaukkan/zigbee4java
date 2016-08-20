package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Set Absolute Location Command value object class.
 */
public class SetAbsoluteLocationCommand extends ZclCommand {
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
     * Power command message field.
     */
    private Integer power;
    /**
     * Path Loss Exponent command message field.
     */
    private Integer pathLossExponent;

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
        this.coordinate1 = (Integer) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_COORDINATE_1);
        this.coordinate2 = (Integer) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_COORDINATE_2);
        this.coordinate3 = (Integer) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_COORDINATE_3);
        this.power = (Integer) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_POWER);
        this.pathLossExponent = (Integer) message.getFields().get(ZclFieldType.SET_ABSOLUTE_LOCATION_COMMAND_PATH_LOSS_EXPONENT);
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

    /**
     * Gets Power.
     * @return the Power
     */
    public Integer getPower() {
        return power;
    }

    /**
     * Sets Power.
     * @param power the Power
     */
    public void setPower(final Integer power) {
        this.power = power;
    }

    /**
     * Gets Path Loss Exponent.
     * @return the Path Loss Exponent
     */
    public Integer getPathLossExponent() {
        return pathLossExponent;
    }

    /**
     * Sets Path Loss Exponent.
     * @param pathLossExponent the Path Loss Exponent
     */
    public void setPathLossExponent(final Integer pathLossExponent) {
        this.pathLossExponent = pathLossExponent;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
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
        builder.append(", ");
        builder.append("power");
        builder.append('=');
        builder.append(power);
        builder.append(", ");
        builder.append("pathLossExponent");
        builder.append('=');
        builder.append(pathLossExponent);
        return builder.toString();
    }

}
