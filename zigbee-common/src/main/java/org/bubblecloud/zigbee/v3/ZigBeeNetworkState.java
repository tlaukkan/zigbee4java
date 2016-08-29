package org.bubblecloud.zigbee.v3;

import java.util.List;

/**
 * The ZigBee network interface. The implementations of this class have to be thread safe.
 */
public interface ZigBeeNetworkState {
    /**
     * Adds group.
     * @param group the group to add
     */
    void addGroup(ZigBeeGroupAddress group);

    /**
     * Updates group.
     * @param group the group to update
     */
    void updateGroup(ZigBeeGroupAddress group);

    /**
     * Removes group by network address.
     * @param groupId the group ID
     */
    void removeGroup(int groupId);

    /**
     * Gets group by network address.
     * @param groupId the group ID
     * @return the ZigBee group or null if no exists with given group ID.
     */
    ZigBeeGroupAddress getGroup(int groupId);

    /**
     * Gets all groups.
     * @return list of groups.
     */
    List<ZigBeeGroupAddress> getGroups();

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
     * @param zigBeeDestination the network address
     * @return the ZigBee device or null if no exists with given network address.
     */
    ZigBeeDevice getDevice(ZigBeeAddress zigBeeDestination);

    /**
     * Gets all devices.
     * @return list of devices.
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
