package org.bubblecloud.zigbee.v3;

import java.util.List;

/**
 * ZigBee network.
 */
public interface ZigBeeNetwork {
    /**
     * Gets ZigBee devices.
     * @return list of ZigBee devices
     */
    //List<ZigBeeDevice> getZigBeeDevices();
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
