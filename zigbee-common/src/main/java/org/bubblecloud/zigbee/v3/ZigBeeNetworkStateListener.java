package org.bubblecloud.zigbee.v3;

/**
 * ZigBee network listener.
 * Provides notifications on devices - eg devices added to the network, removed from the network, or updated. 
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
