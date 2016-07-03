package org.bubblecloud.zigbee.v3.zcl.protocol.command.alarms;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Reset Alarm Command value object class.
 */
public class ResetAlarmCommand extends ZclCommand {
    /**
     * Alarm code command message field.
     */
    private Integer alarmCode;
    /**
     * Cluster identifier command message field.
     */
    private Integer clusterIdentifier;

    /**
     * Default constructor setting the command type field.
     */
    public ResetAlarmCommand() {
        this.setType(ZclCommandType.RESET_ALARM_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ResetAlarmCommand(final ZclCommandMessage message) {
        super(message);
        this.alarmCode = (Integer) message.getFields().get(ZclFieldType.RESET_ALARM_COMMAND_ALARM_CODE);
        this.clusterIdentifier = (Integer) message.getFields().get(ZclFieldType.RESET_ALARM_COMMAND_CLUSTER_IDENTIFIER);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.RESET_ALARM_COMMAND_ALARM_CODE,alarmCode);
        message.getFields().put(ZclFieldType.RESET_ALARM_COMMAND_CLUSTER_IDENTIFIER,clusterIdentifier);
        return message;
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
    public Integer getClusterIdentifier() {
        return clusterIdentifier;
    }

    /**
     * Sets Cluster identifier.
     * @param clusterIdentifier the Cluster identifier
     */
    public void setClusterIdentifier(final Integer clusterIdentifier) {
        this.clusterIdentifier = clusterIdentifier;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("alarmCode");
        builder.append('=');
        builder.append(alarmCode);
        builder.append(", ");
        builder.append("clusterIdentifier");
        builder.append('=');
        builder.append(clusterIdentifier);
        return builder.toString();
    }

}
