package org.bubblecloud.zigbee.network.zcl.protocol.command.ias.wd;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Squawk Command value object class.
 */
public class SquawkCommand extends ZclCommand {
    /**
     * Header command message field.
     */
    private Byte header;

    /**
     * Default constructor setting the command type field.
     */
    public SquawkCommand() {
        this.setType(ZclCommandType.SQUAWK_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public SquawkCommand(final ZclCommandMessage message) {
        super(message);
        this.header = (Byte) message.getFields().get(ZclFieldType.SQUAWK_COMMAND_HEADER);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.SQUAWK_COMMAND_HEADER,header);
        return message;
    }

    /**
     * Gets Header.
     * @return the Header
     */
    public Byte getHeader() {
        return header;
    }

    /**
     * Sets Header.
     * @param header the Header
     */
    public void setHeader(final Byte header) {
        this.header = header;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.SQUAWK_COMMAND,SquawkCommand.class);
    }
}
