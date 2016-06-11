package org.bubblecloud.zigbee.simple;

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
