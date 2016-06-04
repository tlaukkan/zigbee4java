package org.bubblecloud.zigbee.network.zcl.protocol.command.alarms;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Get Alarm Response Command value object class.
 */
public class GetAlarmResponseCommand extends ZclCommand {
    /**
     * Status command message field.
     */
    private Integer status;
    /**
     * Alarm code command message field.
     */
    private Integer alarmCode;
    /**
     * Cluster identifier command message field.
     */
    private Object clusterIdentifier;
    /**
     * Timestamp command message field.
     */
    private Integer timestamp;

    /**
     * Default constructor setting the command type field.
     */
    public GetAlarmResponseCommand() {
        this.setType(ZclCommandType.GET_ALARM_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetAlarmResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.status = (Integer) message.getFields().get(ZclFieldType.GET_ALARM_RESPONSE_COMMAND_STATUS);
        this.alarmCode = (Integer) message.getFields().get(ZclFieldType.GET_ALARM_RESPONSE_COMMAND_ALARM_CODE);
        this.clusterIdentifier = (Object) message.getFields().get(ZclFieldType.GET_ALARM_RESPONSE_COMMAND_CLUSTER_IDENTIFIER);
        this.timestamp = (Integer) message.getFields().get(ZclFieldType.GET_ALARM_RESPONSE_COMMAND_TIMESTAMP);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_ALARM_RESPONSE_COMMAND_STATUS,status);
        message.getFields().put(ZclFieldType.GET_ALARM_RESPONSE_COMMAND_ALARM_CODE,alarmCode);
        message.getFields().put(ZclFieldType.GET_ALARM_RESPONSE_COMMAND_CLUSTER_IDENTIFIER,clusterIdentifier);
        message.getFields().put(ZclFieldType.GET_ALARM_RESPONSE_COMMAND_TIMESTAMP,timestamp);
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
     * Gets Alarm code.
     * @return the Alarm code
     */
    public Integer getAlarmCode() {
        return alarmCode;
    }

    /**
     * Sets Alarm code.
     * @param alarmCode the Alarm code
     */
    public void setAlarmCode(final Integer alarmCode) {
        this.alarmCode = alarmCode;
    }

    /**
     * Gets Cluster identifier.
     * @return the Cluster identifier
     */
    public Object getClusterIdentifier() {
        return clusterIdentifier;
    }

    /**
     * Sets Cluster identifier.
     * @param clusterIdentifier the Cluster identifier
     */
    public void setClusterIdentifier(final Object clusterIdentifier) {
        this.clusterIdentifier = clusterIdentifier;
    }

    /**
     * Gets Timestamp.
     * @return the Timestamp
     */
    public Integer getTimestamp() {
        return timestamp;
    }

    /**
     * Sets Timestamp.
     * @param timestamp the Timestamp
     */
    public void setTimestamp(final Integer timestamp) {
        this.timestamp = timestamp;
    }

}
