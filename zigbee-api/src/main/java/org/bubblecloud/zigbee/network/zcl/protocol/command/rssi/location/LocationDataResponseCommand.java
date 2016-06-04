package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Location Data Response Command value object class.
 */
public class LocationDataResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Byte status;
    /**
     * Location Type command message field.
     */
    private Byte locationType;
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
     * Location Method command message field.
     */
    private Byte locationMethod;
    /**
     * Quality Measure command message field.
     */
    private Byte qualityMeasure;
    /**
     * Location Age command message field.
     */
    private Short locationAge;

    /**
     * Default constructor setting the command type field.
     */
    public LocationDataResponseCommand() {
        this.setType(ZclCommandType.LOCATION_DATA_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public LocationDataResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.status = (Byte) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_STATUS);
        this.locationType = (Byte) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_LOCATION_TYPE);
        this.coordinate1 = (Short) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_COORDINATE_1);
        this.coordinate2 = (Short) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_COORDINATE_2);
        this.coordinate3 = (Short) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_COORDINATE_3);
        this.power = (Short) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_POWER);
        this.pathLossExponent = (Short) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_PATH_LOSS_EXPONENT);
        this.locationMethod = (Byte) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_LOCATION_METHOD);
        this.qualityMeasure = (Byte) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_QUALITY_MEASURE);
        this.locationAge = (Short) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_LOCATION_AGE);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_STATUS,status);
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_LOCATION_TYPE,locationType);
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_COORDINATE_1,coordinate1);
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_COORDINATE_2,coordinate2);
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_COORDINATE_3,coordinate3);
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_POWER,power);
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_PATH_LOSS_EXPONENT,pathLossExponent);
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_LOCATION_METHOD,locationMethod);
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_QUALITY_MEASURE,qualityMeasure);
        message.getFields().put(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_LOCATION_AGE,locationAge);
        return message;
    }

    /**
     * Gets Status.
     * @return the Status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * Sets Status.
     * @param status the Status
     */
    public void setStatus(final Byte status) {
        this.status = status;
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

    /**
     * Gets Location Method.
     * @return the Location Method
     */
    public Byte getLocationMethod() {
        return locationMethod;
    }

    /**
     * Sets Location Method.
     * @param locationMethod the Location Method
     */
    public void setLocationMethod(final Byte locationMethod) {
        this.locationMethod = locationMethod;
    }

    /**
     * Gets Quality Measure.
     * @return the Quality Measure
     */
    public Byte getQualityMeasure() {
        return qualityMeasure;
    }

    /**
     * Sets Quality Measure.
     * @param qualityMeasure the Quality Measure
     */
    public void setQualityMeasure(final Byte qualityMeasure) {
        this.qualityMeasure = qualityMeasure;
    }

    /**
     * Gets Location Age.
     * @return the Location Age
     */
    public Short getLocationAge() {
        return locationAge;
    }

    /**
     * Sets Location Age.
     * @param locationAge the Location Age
     */
    public void setLocationAge(final Short locationAge) {
        this.locationAge = locationAge;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.LOCATION_DATA_RESPONSE_COMMAND,LocationDataResponseCommand.class);
    }
}
