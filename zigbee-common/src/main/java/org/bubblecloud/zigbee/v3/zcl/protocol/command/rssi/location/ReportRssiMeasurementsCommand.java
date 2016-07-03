package org.bubblecloud.zigbee.v3.zcl.protocol.command.rssi.location;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.v3.zcl.field.*;

import java.util.List;

/**
 * Code generated Report RSSI Measurements Command value object class.
 */
public class ReportRssiMeasurementsCommand extends ZclCommand {
    /**
     * Reporting Address command message field.
     */
    private Long reportingAddress;
    /**
     * Number of Neighbors command message field.
     */
    private Integer numberOfNeighbors;
    /**
     * Neighbors Information command message field.
     */
    private List<NeighborInformation> neighborsInformation;

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
        this.reportingAddress = (Long) message.getFields().get(ZclFieldType.REPORT_RSSI_MEASUREMENTS_COMMAND_REPORTING_ADDRESS);
        this.numberOfNeighbors = (Integer) message.getFields().get(ZclFieldType.REPORT_RSSI_MEASUREMENTS_COMMAND_NUMBER_OF_NEIGHBORS);
        this.neighborsInformation = (List<NeighborInformation>) message.getFields().get(ZclFieldType.REPORT_RSSI_MEASUREMENTS_COMMAND_NEIGHBORS_INFORMATION);
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
    public Long getReportingAddress() {
        return reportingAddress;
    }

    /**
     * Sets Reporting Address.
     * @param reportingAddress the Reporting Address
     */
    public void setReportingAddress(final Long reportingAddress) {
        this.reportingAddress = reportingAddress;
    }

    /**
     * Gets Number of Neighbors.
     * @return the Number of Neighbors
     */
    public Integer getNumberOfNeighbors() {
        return numberOfNeighbors;
    }

    /**
     * Sets Number of Neighbors.
     * @param numberOfNeighbors the Number of Neighbors
     */
    public void setNumberOfNeighbors(final Integer numberOfNeighbors) {
        this.numberOfNeighbors = numberOfNeighbors;
    }

    /**
     * Gets Neighbors Information.
     * @return the Neighbors Information
     */
    public List<NeighborInformation> getNeighborsInformation() {
        return neighborsInformation;
    }

    /**
     * Sets Neighbors Information.
     * @param neighborsInformation the Neighbors Information
     */
    public void setNeighborsInformation(final List<NeighborInformation> neighborsInformation) {
        this.neighborsInformation = neighborsInformation;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("reportingAddress");
        builder.append('=');
        builder.append(reportingAddress);
        builder.append(", ");
        builder.append("numberOfNeighbors");
        builder.append('=');
        builder.append(numberOfNeighbors);
        builder.append(", ");
        builder.append("neighborsInformation");
        builder.append('=');
        builder.append(neighborsInformation);
        return builder.toString();
    }

}
