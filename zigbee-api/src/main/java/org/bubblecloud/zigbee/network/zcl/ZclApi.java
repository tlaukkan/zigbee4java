package org.bubblecloud.zigbee.network.zcl;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.ZclCommandListener;
import org.bubblecloud.zigbee.simple.ZigBeeDevice;

import java.util.List;

/**
 * ZigBee Cluster Library command interface.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public interface ZclApi {
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
    int sendCommand(final ZclCommand command) throws ZigBeeException;
    /**
     * Adds ZigBee Cluster Library command listener.
     * @param commandListener the command listener
     */
    void addCommandListener(final ZclCommandListener commandListener);
    /**
     * Removes ZigBee Cluster Library command listener.
     * @param commandListener the command listener
     */
    void removeCommandListener(final ZclCommandListener commandListener);
}
