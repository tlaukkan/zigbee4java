package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Device Configuration Response Command value object class.
 */
public class DeviceConfigurationResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Integer status;
    /**
     * Power command message field.
     */
    private Integer power;
    /**
     * Path Loss Exponent command message field.
     */
    private Integer pathLossExponent;
    /**
     * Calculation Period command message field.
     */
    private Integer calculationPeriod;
    /**
     * Number RSSI Measurements command message field.
     */
    private Integer numberRssiMeasurements;
    /**
     * Reporting Period command message field.
     */
    private Integer reportingPeriod;

    /**
     * Default constructor setting the command type field.
     */
    public DeviceConfigurationResponseCommand() {
        this.setType(ZclCommandType.DEVICE_CONFIGURATION_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public DeviceConfigurationResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.status = (Integer) message.getFields().get(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_STATUS);
        this.power = (Integer) message.getFields().get(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_POWER);
        this.pathLossExponent = (Integer) message.getFields().get(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_PATH_LOSS_EXPONENT);
        this.calculationPeriod = (Integer) message.getFields().get(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_CALCULATION_PERIOD);
        this.numberRssiMeasurements = (Integer) message.getFields().get(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_NUMBER_RSSI_MEASUREMENTS);
        this.reportingPeriod = (Integer) message.getFields().get(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_REPORTING_PERIOD);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_STATUS,status);
        message.getFields().put(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_POWER,power);
        message.getFields().put(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_PATH_LOSS_EXPONENT,pathLossExponent);
        message.getFields().put(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_CALCULATION_PERIOD,calculationPeriod);
        message.getFields().put(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_NUMBER_RSSI_MEASUREMENTS,numberRssiMeasurements);
        message.getFields().put(ZclFieldType.DEVICE_CONFIGURATION_RESPONSE_COMMAND_REPORTING_PERIOD,reportingPeriod);
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
     * Gets Calculation Period.
     * @return the Calculation Period
     */
    public Integer getCalculationPeriod() {
        return calculationPeriod;
    }

    /**
     * Sets Calculation Period.
     * @param calculationPeriod the Calculation Period
     */
    public void setCalculationPeriod(final Integer calculationPeriod) {
        this.calculationPeriod = calculationPeriod;
    }

    /**
     * Gets Number RSSI Measurements.
     * @return the Number RSSI Measurements
     */
    public Integer getNumberRssiMeasurements() {
        return numberRssiMeasurements;
    }

    /**
     * Sets Number RSSI Measurements.
     * @param numberRssiMeasurements the Number RSSI Measurements
     */
    public void setNumberRssiMeasurements(final Integer numberRssiMeasurements) {
        this.numberRssiMeasurements = numberRssiMeasurements;
    }

    /**
     * Gets Reporting Period.
     * @return the Reporting Period
     */
    public Integer getReportingPeriod() {
        return reportingPeriod;
    }

    /**
     * Sets Reporting Period.
     * @param reportingPeriod the Reporting Period
     */
    public void setReportingPeriod(final Integer reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
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
        builder.append("power");
        builder.append('=');
        builder.append(power);
        builder.append(", ");
        builder.append("pathLossExponent");
        builder.append('=');
        builder.append(pathLossExponent);
        builder.append(", ");
        builder.append("calculationPeriod");
        builder.append('=');
        builder.append(calculationPeriod);
        builder.append(", ");
        builder.append("numberRssiMeasurements");
        builder.append('=');
        builder.append(numberRssiMeasurements);
        builder.append(", ");
        builder.append("reportingPeriod");
        builder.append('=');
        builder.append(reportingPeriod);
        return builder.toString();
    }

}
