package org.bubblecloud.zigbee.network.zcl.protocol.command.commissioning;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Reset Startup Parameters Command value object class.
 */
public class ResetStartupParametersCommand extends ZclCommand {
    /**
     * Option command message field.
     */
    private Integer option;
    /**
     * Index command message field.
     */
    private Integer index;

    /**
     * Default constructor setting the command type field.
     */
    public ResetStartupParametersCommand() {
        this.setType(ZclCommandType.RESET_STARTUP_PARAMETERS_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public ResetStartupParametersCommand(final ZclCommandMessage message) {
        super(message);
        this.option = (Integer) message.getFields().get(ZclFieldType.RESET_STARTUP_PARAMETERS_COMMAND_OPTION);
        this.index = (Integer) message.getFields().get(ZclFieldType.RESET_STARTUP_PARAMETERS_COMMAND_INDEX);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.RESET_STARTUP_PARAMETERS_COMMAND_OPTION,option);
        message.getFields().put(ZclFieldType.RESET_STARTUP_PARAMETERS_COMMAND_INDEX,index);
        return message;
    }

    /**
     * Gets Option.
     * @return the Option
     */
    public Integer getOption() {
        return option;
    }

    /**
     * Sets Option.
     * @param option the Option
     */
    public void setOption(final Integer option) {
        this.option = option;
    }

    /**
     * Gets Index.
     * @return the Index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets Index.
     * @param index the Index
     */
    public void setIndex(final Integer index) {
        this.index = index;
    }

}
