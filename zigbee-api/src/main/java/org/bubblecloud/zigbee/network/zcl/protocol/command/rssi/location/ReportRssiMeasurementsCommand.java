package org.bubblecloud.zigbee.network.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Report RSSI Measurements Command value object class.
 */
public class ReportRssiMeasurementsCommand extends ZclCommand {
    /**
     * Reporting Address command message field.
     */
    private ZToolAddress64 reportingAddress;
    /**
     * Number of Neighbors command message field.
     */
    private Byte numberOfNeighbors;
    /**
     * Neighbors Information command message field.
     */
    private Object neighborsInformation;

    /**
     * Default constructor setting the command type field.
     */
    public ReportRssiMeasurementsCommand() {
        this.setType(ZclCommandType.REPORT_RSSI_MEASUREMENTS_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ReportRssiMeasurementsCommand(final ZclCommandMessage message) {
        super(message);
        this.reportingAddress = (ZToolAddress64) message.getFields().get(ZclFieldType.REPORT_RSSI_MEASUREMENTS_COMMAND_REPORTING_ADDRESS);
        this.numberOfNeighbors = (Byte) message.getFields().get(ZclFieldType.REPORT_RSSI_MEASUREMENTS_COMMAND_NUMBER_OF_NEIGHBORS);
        this.neighborsInformation = (Object) message.getFields().get(ZclFieldType.REPORT_RSSI_MEASUREMENTS_COMMAND_NEIGHBORS_INFORMATION);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.REPORT_RSSI_MEASUREMENTS_COMMAND_REPORTING_ADDRESS,reportingAddress);
        message.getFields().put(ZclFieldType.REPORT_RSSI_MEASUREMENTS_COMMAND_NUMBER_OF_NEIGHBORS,numberOfNeighbors);
        message.getFields().put(ZclFieldType.REPORT_RSSI_MEASUREMENTS_COMMAND_NEIGHBORS_INFORMATION,neighborsInformation);
        return message;
    }

    /**
     * Gets Reporting Address.
     * @return the Reporting Address
     */
    public ZToolAddress64 getReportingAddress() {
        return reportingAddress;
    }

    /**
     * Sets Reporting Address.
     * @param reportingAddress the Reporting Address
     */
    public void setReportingAddress(final ZToolAddress64 reportingAddress) {
        this.reportingAddress = reportingAddress;
    }

    /**
     * Gets Number of Neighbors.
     * @return the Number of Neighbors
     */
    public Byte getNumberOfNeighbors() {
        return numberOfNeighbors;
    }

    /**
     * Sets Number of Neighbors.
     * @param numberOfNeighbors the Number of Neighbors
     */
    public void setNumberOfNeighbors(final Byte numberOfNeighbors) {
        this.numberOfNeighbors = numberOfNeighbors;
    }

    /**
     * Gets Neighbors Information.
     * @return the Neighbors Information
     */
    public Object getNeighborsInformation() {
        return neighborsInformation;
    }

    /**
     * Sets Neighbors Information.
     * @param neighborsInformation the Neighbors Information
     */
    public void setNeighborsInformation(final Object neighborsInformation) {
        this.neighborsInformation = neighborsInformation;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.REPORT_RSSI_MEASUREMENTS_COMMAND,ReportRssiMeasurementsCommand.class);
    }
}
