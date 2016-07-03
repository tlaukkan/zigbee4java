package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Send Pings Command value object class.
 */
public class SendPingsCommand extends ZclCommand {
    /**
     * Target Address command message field.
     */
    private Long targetAddress;
    /**
     * Number RSSI Measurements command message field.
     */
    private Integer numberRssiMeasurements;
    /**
     * Calculation Period command message field.
     */
    private Integer calculationPeriod;

    /**
     * Default constructor setting the command type field.
     */
    public SendPingsCommand() {
        this.setType(ZclCommandType.SEND_PINGS_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public SendPingsCommand(final ZclCommandMessage message) {
        super(message);
        this.targetAddress = (Long) message.getFields().get(ZclFieldType.SEND_PINGS_COMMAND_TARGET_ADDRESS);
        this.numberRssiMeasurements = (Integer) message.getFields().get(ZclFieldType.SEND_PINGS_COMMAND_NUMBER_RSSI_MEASUREMENTS);
        this.calculationPeriod = (Integer) message.getFields().get(ZclFieldType.SEND_PINGS_COMMAND_CALCULATION_PERIOD);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.SEND_PINGS_COMMAND_TARGET_ADDRESS,targetAddress);
        message.getFields().put(ZclFieldType.SEND_PINGS_COMMAND_NUMBER_RSSI_MEASUREMENTS,numberRssiMeasurements);
        message.getFields().put(ZclFieldType.SEND_PINGS_COMMAND_CALCULATION_PERIOD,calculationPeriod);
        return message;
    }

    /**
     * Gets Target Address.
     * @return the Target Address
     */
    public Long getTargetAddress() {
        return targetAddress;
    }

    /**
     * Sets Target Address.
     * @param targetAddress the Target Address
     */
    public void setTargetAddress(final Long targetAddress) {
        this.targetAddress = targetAddress;
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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("targetAddress");
        builder.append('=');
        builder.append(targetAddress);
        builder.append(", ");
        builder.append("numberRssiMeasurements");
        builder.append('=');
        builder.append(numberRssiMeasurements);
        builder.append(", ");
        builder.append("calculationPeriod");
        builder.append('=');
        builder.append(calculationPeriod);
        return builder.toString();
    }

}
