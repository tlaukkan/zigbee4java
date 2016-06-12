package org.bubblecloud.zigbee.simple;

import java.util.List;

/**
 * The ZigBee network interface. The implementations of this class have to be thread safe.
 */
public interface ZigBeeNetworkState {
    /**
     * Adds device.
     * @param device the device to add
     */
    void addDevice(ZigBeeDevice device);

    /**
     * Updates device.
     * @param device the device to update
     */
    void updateDevice(ZigBeeDevice device);

    /**
     * Removes device by network address.
     * @param networkAddress the network address
     */
    void removeDevice(int networkAddress, int endpoint);

    /**
     * Gets device by network address.
     * @param networkAddress the network address
     * @return the ZigBee device or null if no exists with given network address.
     */
    ZigBeeDevice getDevice(int networkAddress, int endpoint);

    /**
     * Gets all devices.
     * @return list od devices.
     */
    List<ZigBeeDevice> getDevices();

    /**
     * Adds network listener.
     * @param networkListener the network listener
     */
    void addNetworkListener(ZigBeeNetworkStateListener networkListener);

    /**
     * Removes network listener
     * @param networkListener the network listener
     */
    void removeNetworkListener(ZigBeeNetworkStateListener networkListener);
}
