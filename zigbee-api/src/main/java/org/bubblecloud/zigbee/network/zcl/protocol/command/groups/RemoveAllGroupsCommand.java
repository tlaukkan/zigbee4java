package org.bubblecloud.zigbee.network.zcl.protocol.command.groups;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Remove All Groups Command value object class.
 */
public class RemoveAllGroupsCommand extends ZclCommand {

    /**
     * Default constructor setting the command type field.
     */
    public RemoveAllGroupsCommand() {
        this.setType(ZclCommandType.REMOVE_ALL_GROUPS_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public RemoveAllGroupsCommand(final ZclCommandMessage message) {
        super(message);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        return message;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.REMOVE_ALL_GROUPS_COMMAND,RemoveAllGroupsCommand.class);
    }
}
