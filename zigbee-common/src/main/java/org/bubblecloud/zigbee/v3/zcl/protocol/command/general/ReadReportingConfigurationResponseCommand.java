package org.bubblecloud.zigbee.v3.zcl.protocol.command.general;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.v3.zcl.field.*;

import java.util.List;

/**
 * Code generated Read Reporting Configuration Response Command value object class.
 */
public class ReadReportingConfigurationResponseCommand extends ZclCommand {
    /**
     * Records command message field.
     */
    private List<AttributeReportingConfigurationRecord> records;

    /**
     * Default constructor setting the command type field.
     */
    public ReadReportingConfigurationResponseCommand() {
        this.setType(ZclCommandType.READ_REPORTING_CONFIGURATION_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ReadReportingConfigurationResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.records = (List<AttributeReportingConfigurationRecord>) message.getFields().get(ZclFieldType.READ_REPORTING_CONFIGURATION_RESPONSE_COMMAND_RECORDS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.READ_REPORTING_CONFIGURATION_RESPONSE_COMMAND_RECORDS,records);
        return message;
    }

    /**
     * Gets Records.
     * @return the Records
     */
    public List<AttributeReportingConfigurationRecord> getRecords() {
        return records;
    }

    /**
     * Sets Records.
     * @param records the Records
     */
    public void setRecords(final List<AttributeReportingConfigurationRecord> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("records");
        builder.append('=');
        builder.append(records);
        return builder.toString();
    }

}
