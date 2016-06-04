package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Send Pings Command value object class.
 */
public class SendPingsCommand extends ZclCommand {
    /**
     * Target Address command message field.
     */
    private ZToolAddress64 targetAddress;
    /**
     * Number RSSI Measurements command message field.
     */
    private Byte numberRssiMeasurements;
    /**
     * Calculation Period command message field.
     */
    private Short calculationPeriod;

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
        this.targetAddress = (ZToolAddress64) message.getFields().get(ZclFieldType.SEND_PINGS_COMMAND_TARGET_ADDRESS);
        this.numberRssiMeasurements = (Byte) message.getFields().get(ZclFieldType.SEND_PINGS_COMMAND_NUMBER_RSSI_MEASUREMENTS);
        this.calculationPeriod = (Short) message.getFields().get(ZclFieldType.SEND_PINGS_COMMAND_CALCULATION_PERIOD);
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
    public ZToolAddress64 getTargetAddress() {
        return targetAddress;
    }

    /**
     * Sets Target Address.
     * @param targetAddress the Target Address
     */
    public void setTargetAddress(final ZToolAddress64 targetAddress) {
        this.targetAddress = targetAddress;
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

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.SEND_PINGS_COMMAND,SendPingsCommand.class);
    }
}
