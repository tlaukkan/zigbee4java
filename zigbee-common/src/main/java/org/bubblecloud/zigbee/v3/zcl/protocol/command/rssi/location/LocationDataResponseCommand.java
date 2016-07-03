package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Location Data Response Command value object class.
 */
public class LocationDataResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Integer status;
    /**
     * Location Type command message field.
     */
    private Integer locationType;
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
     * Location Method command message field.
     */
    private Integer locationMethod;
    /**
     * Quality Measure command message field.
     */
    private Integer qualityMeasure;
    /**
     * Location Age command message field.
     */
    private Integer locationAge;

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
        this.status = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_STATUS);
        this.locationType = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_LOCATION_TYPE);
        this.coordinate1 = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_COORDINATE_1);
        this.coordinate2 = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_COORDINATE_2);
        this.coordinate3 = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_COORDINATE_3);
        this.power = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_POWER);
        this.pathLossExponent = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_PATH_LOSS_EXPONENT);
        this.locationMethod = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_LOCATION_METHOD);
        this.qualityMeasure = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_QUALITY_MEASURE);
        this.locationAge = (Integer) message.getFields().get(ZclFieldType.LOCATION_DATA_RESPONSE_COMMAND_LOCATION_AGE);
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
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets Status.
     * @param status the Status
     */
    public void setStatus(final Integer status) {
        this.status = status;
    }

    /**
     * Gets Location Type.
     * @return the Location Type
     */
    public Integer getLocationType() {
        return locationType;
    }

    /**
     * Sets Location Type.
     * @param locationType the Location Type
     */
    public void setLocationType(final Integer locationType) {
        this.locationType = locationType;
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

    /**
     * Gets Location Method.
     * @return the Location Method
     */
    public Integer getLocationMethod() {
        return locationMethod;
    }

    /**
     * Sets Location Method.
     * @param locationMethod the Location Method
     */
    public void setLocationMethod(final Integer locationMethod) {
        this.locationMethod = locationMethod;
    }

    /**
     * Gets Quality Measure.
     * @return the Quality Measure
     */
    public Integer getQualityMeasure() {
        return qualityMeasure;
    }

    /**
     * Sets Quality Measure.
     * @param qualityMeasure the Quality Measure
     */
    public void setQualityMeasure(final Integer qualityMeasure) {
        this.qualityMeasure = qualityMeasure;
    }

    /**
     * Gets Location Age.
     * @return the Location Age
     */
    public Integer getLocationAge() {
        return locationAge;
    }

    /**
     * Sets Location Age.
     * @param locationAge the Location Age
     */
    public void setLocationAge(final Integer locationAge) {
        this.locationAge = locationAge;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("status");
        builder.append('=');
        builder.append(status);
        builder.append(", ");
        builder.append("locationType");
        builder.append('=');
        builder.append(locationType);
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
        builder.append(", ");
        builder.append("locationMethod");
        builder.append('=');
        builder.append(locationMethod);
        builder.append(", ");
        builder.append("qualityMeasure");
        builder.append('=');
        builder.append(qualityMeasure);
        builder.append(", ");
        builder.append("locationAge");
        builder.append('=');
        builder.append(locationAge);
        return builder.toString();
    }

}
