package org.bubblecloud.zigbee.simple;

/**
 * ZigBee network listener.
 */
public interface ZigBeeNetworkStateListener {
    /**
     * Device was added to network.
     * @param device the device
     */
    void deviceAdded(final ZigBeeDevice device);
    /**
     * Device was updated.
     * @param device the device
     */
    void deviceUpdated(final ZigBeeDevice device);
    /**
     * Device was removed from network.
     * @param device the device
     */
    void deviceRemoved(final ZigBeeDevice device);
}
