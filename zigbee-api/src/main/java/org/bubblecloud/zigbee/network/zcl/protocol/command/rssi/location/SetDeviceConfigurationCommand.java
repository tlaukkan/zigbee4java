package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Set Device Configuration Command value object class.
 */
public class SetDeviceConfigurationCommand extends ZclCommand {
    /**
     * Power command message field.
     */
    private Short power;
    /**
     * Path Loss Exponent command message field.
     */
    private Short pathLossExponent;
    /**
     * Calculation Period command message field.
     */
    private Short calculationPeriod;
    /**
     * Number RSSI Measurements command message field.
     */
    private Byte numberRssiMeasurements;
    /**
     * Reporting Period command message field.
     */
    private Short reportingPeriod;

    /**
     * Default constructor setting the command type field.
     */
    public SetDeviceConfigurationCommand() {
        this.setType(ZclCommandType.SET_DEVICE_CONFIGURATION_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public SetDeviceConfigurationCommand(final ZclCommandMessage message) {
        super(message);
        this.power = (Short) message.getFields().get(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_POWER);
        this.pathLossExponent = (Short) message.getFields().get(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_PATH_LOSS_EXPONENT);
        this.calculationPeriod = (Short) message.getFields().get(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_CALCULATION_PERIOD);
        this.numberRssiMeasurements = (Byte) message.getFields().get(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_NUMBER_RSSI_MEASUREMENTS);
        this.reportingPeriod = (Short) message.getFields().get(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_REPORTING_PERIOD);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_POWER,power);
        message.getFields().put(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_PATH_LOSS_EXPONENT,pathLossExponent);
        message.getFields().put(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_CALCULATION_PERIOD,calculationPeriod);
        message.getFields().put(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_NUMBER_RSSI_MEASUREMENTS,numberRssiMeasurements);
        message.getFields().put(ZclFieldType.SET_DEVICE_CONFIGURATION_COMMAND_REPORTING_PERIOD,reportingPeriod);
        return message;
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
     * Gets Calculation Period.
     * @return the Calculation Period
     */
    public Short getCalculationPeriod() {
        return calculationPeriod;
    }

    /**
     * Sets Calculation Period.
     * @param calculationPeriod the Calculation Period
     */
    public void setCalculationPeriod(final Short calculationPeriod) {
        this.calculationPeriod = calculationPeriod;
    }

    /**
     * Gets Number RSSI Measurements.
     * @return the Number RSSI Measurements
     */
    public Byte getNumberRssiMeasurements() {
        return numberRssiMeasurements;
    }

    /**
     * Sets Number RSSI Measurements.
     * @param numberRssiMeasurements the Number RSSI Measurements
     */
    public void setNumberRssiMeasurements(final Byte numberRssiMeasurements) {
        this.numberRssiMeasurements = numberRssiMeasurements;
    }

    /**
     * Gets Reporting Period.
     * @return the Reporting Period
     */
    public Short getReportingPeriod() {
        return reportingPeriod;
    }

    /**
     * Sets Reporting Period.
     * @param reportingPeriod the Reporting Period
     */
    public void setReportingPeriod(final Short reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.SET_DEVICE_CONFIGURATION_COMMAND,SetDeviceConfigurationCommand.class);
    }
}
