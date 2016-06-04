package org.bubblecloud.zigbee.network.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Arm Command value object class.
 */
public class ArmCommand extends ZclCommand {
    /**
     * Arm Mode command message field.
     */
    private Byte armMode;

    /**
     * Default constructor setting the command type field.
     */
    public ArmCommand() {
        this.setType(ZclCommandType.ARM_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ArmCommand(final ZclCommandMessage message) {
        super(message);
        this.armMode = (Byte) message.getFields().get(ZclFieldType.ARM_COMMAND_ARM_MODE);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.ARM_COMMAND_ARM_MODE,armMode);
        return message;
    }

    /**
     * Gets Arm Mode.
     * @return the Arm Mode
     */
    public Byte getArmMode() {
        return armMode;
    }

    /**
     * Sets Arm Mode.
     * @param armMode the Arm Mode
     */
    public void setArmMode(final Byte armMode) {
        this.armMode = armMode;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.ARM_COMMAND,ArmCommand.class);
    }
}
