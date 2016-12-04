package org.bubblecloud.zigbee.v3;

/**
 * ZigBee dongle interface implemented by different dongle hardware drivers.
 */
public interface ZigBeeDongle {
    /**
     * Starts up dongle.
     *
     * @return true if startup was success.
     */
    boolean startup();

    /**
     * Shuts down dongle.
     */
    void shutdown();
    
    /**
     * Sends ZigBee library command without waiting for response.
     * @param command the command
     * @return transaction ID
     * @throws ZigBeeException if exception occurs in sending
     */
    int sendCommand(final Command command) throws ZigBeeException;

    
    void setZigBeeNetwork(ZigBeeNetwork zigbeeNetwork);

}
