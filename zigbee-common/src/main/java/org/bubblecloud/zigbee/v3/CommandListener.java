package org.bubblecloud.zigbee.v3;

/**
 * Command listener.
 */
public interface CommandListener {

    /**
     * Invoked when command has been received.
     * @param command the command
     */
    void commandReceived(final Command command);

}
