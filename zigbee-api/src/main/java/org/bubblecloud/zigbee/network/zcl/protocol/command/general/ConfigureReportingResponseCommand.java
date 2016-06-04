package org.bubblecloud.zigbee.network.zcl.protocol.command.general;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Configure Reporting Response Command value object class.
 */
public class ConfigureReportingResponseCommand extends ZclCommand {
    /**
     * Records command message field.
     */
    private Object records;

    /**
     * Default constructor setting the command type field.
     */
    public ConfigureReportingResponseCommand() {
        this.setType(ZclCommandType.CONFIGURE_REPORTING_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ConfigureReportingResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.records = (Object) message.getFields().get(ZclFieldType.CONFIGURE_REPORTING_RESPONSE_COMMAND_RECORDS);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.CONFIGURE_REPORTING_RESPONSE_COMMAND_RECORDS,records);
        return message;
    }

    /**
     * Gets Records.
     * @return the Records
     */
    public Object getRecords() {
        return records;
    }

    /**
     * Sets Records.
     * @param records the Records
     */
    public void setRecords(final Object records) {
        this.records = records;
    }

}
