package org.bubblecloud.zigbee.network.zcl.protocol.command.general;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.zcl.field.*;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

import java.util.List;

/**
 * Code generated Read Reporting Configuration Command value object class.
 */
public class ReadReportingConfigurationCommand extends ZclCommand {
    /**
     * Records command message field.
     */
    private List<AttributeRecord> records;

    /**
     * Default constructor setting the command type field.
     */
    public ReadReportingConfigurationCommand() {
        this.setType(ZclCommandType.READ_REPORTING_CONFIGURATION_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ReadReportingConfigurationCommand(final ZclCommandMessage message) {
        super(message);
        this.records = (List<AttributeRecord>) message.getFields().get(ZclFieldType.READ_REPORTING_CONFIGURATION_COMMAND_RECORDS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.READ_REPORTING_CONFIGURATION_COMMAND_RECORDS,records);
        return message;
    }

    /**
     * Gets Records.
     * @return the Records
     */
    public List<AttributeRecord> getRecords() {
        return records;
    }

    /**
     * Sets Records.
     * @param records the Records
     */
    public void setRecords(final List<AttributeRecord> records) {
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
