package org.bubblecloud.zigbee.v3;

/**
 * ZigBee network.
 */
public interface ZigBeeNetwork {
    /**
     * Sends ZigBee Cluster Library command without waiting for response.
     * @param command the command
     * @return transaction ID
     * @throws ZigBeeException if exception occurs in sending
     */
    int sendCommand(final Command command) throws ZigBeeException;
    /**
     * Adds ZigBee Cluster Library command listener.
     * @param commandListener the command listener
     */
    void addCommandListener(final CommandListener commandListener);
    /**
     * Removes ZigBee Cluster Library command listener.
     * @param commandListener the command listener
     */
    void removeCommandListener(final CommandListener commandListener);
}
