package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;

import java.util.List;

/**
 * ZigBee Cluster Library command interface.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public interface ZigBeeDongle {
    /**
     * Gets ZigBee devices.
     * @return list of ZigBee devices
     */
    List<ZigBeeDevice> getZigBeeDevices();
    /**
     * Sends ZigBee Cluster Library command without waiting for response.
     * @param command the command
     * @return transaction ID
     * @throws ZigBeeNetworkManagerException if exception occurs in sending
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
