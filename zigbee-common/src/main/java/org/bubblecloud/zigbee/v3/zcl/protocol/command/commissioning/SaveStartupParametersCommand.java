package org.bubblecloud.zigbee.v3.zcl.protocol.command.commissioning;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Save Startup Parameters Command value object class.
 */
public class SaveStartupParametersCommand extends ZclCommand {
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
    public SaveStartupParametersCommand() {
        this.setType(ZclCommandType.SAVE_STARTUP_PARAMETERS_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public SaveStartupParametersCommand(final ZclCommandMessage message) {
        super(message);
        this.option = (Integer) message.getFields().get(ZclFieldType.SAVE_STARTUP_PARAMETERS_COMMAND_OPTION);
        this.index = (Integer) message.getFields().get(ZclFieldType.SAVE_STARTUP_PARAMETERS_COMMAND_INDEX);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.SAVE_STARTUP_PARAMETERS_COMMAND_OPTION,option);
        message.getFields().put(ZclFieldType.SAVE_STARTUP_PARAMETERS_COMMAND_INDEX,index);
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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("option");
        builder.append('=');
        builder.append(option);
        builder.append(", ");
        builder.append("index");
        builder.append('=');
        builder.append(index);
        return builder.toString();
    }

}
