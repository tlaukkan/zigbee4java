package org.bubblecloud.zigbee.network.zcl.protocol.command.general;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.zcl.type.*;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

import java.util.List;

/**
 * Code generated Report Attributes Command value object class.
 */
public class ReportAttributesCommand extends ZclCommand {
    /**
     * Reports command message field.
     */
    private Object reports;

    /**
     * Default constructor setting the command type field.
     */
    public ReportAttributesCommand() {
        this.setType(ZclCommandType.REPORT_ATTRIBUTES_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ReportAttributesCommand(final ZclCommandMessage message) {
        super(message);
        this.reports = (Object) message.getFields().get(ZclFieldType.REPORT_ATTRIBUTES_COMMAND_REPORTS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.REPORT_ATTRIBUTES_COMMAND_REPORTS,reports);
        return message;
    }

    /**
     * Gets Reports.
     * @return the Reports
     */
    public Object getReports() {
        return reports;
    }

    /**
     * Sets Reports.
     * @param reports the Reports
     */
    public void setReports(final Object reports) {
        this.reports = reports;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("reports");
        builder.append('=');
        builder.append(reports);
        return builder.toString();
    }

}
