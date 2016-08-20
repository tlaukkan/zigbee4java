package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Arm Response Command value object class.
 */
public class ArmResponseCommand extends ZclCommand {
    /**
     * Arm Notification command message field.
     */
    private Integer armNotification;

    /**
     * Default constructor setting the command type field.
     */
    public ArmResponseCommand() {
        this.setType(ZclCommandType.ARM_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ArmResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.armNotification = (Integer) message.getFields().get(ZclFieldType.ARM_RESPONSE_COMMAND_ARM_NOTIFICATION);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.ARM_RESPONSE_COMMAND_ARM_NOTIFICATION,armNotification);
        return message;
    }

    /**
     * Gets Arm Notification.
     * @return the Arm Notification
     */
    public Integer getArmNotification() {
        return armNotification;
    }

    /**
     * Sets Arm Notification.
     * @param armNotification the Arm Notification
     */
    public void setArmNotification(final Integer armNotification) {
        this.armNotification = armNotification;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("armNotification");
        builder.append('=');
        builder.append(armNotification);
        return builder.toString();
    }

}
