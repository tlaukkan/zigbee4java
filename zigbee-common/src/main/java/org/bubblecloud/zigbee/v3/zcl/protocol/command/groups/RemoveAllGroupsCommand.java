package org.bubblecloud.zigbee.v3.zcl.protocol.command.groups;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;


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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        return builder.toString();
    }

}
