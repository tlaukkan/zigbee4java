package org.bubblecloud.zigbee.v3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements functions for managing the ZigBee interface
 *  
 * @author Chris Jackson
 */
public class ZigBeeNetworkManager implements ZigBeeNetwork {
    /**
     * The dongle implementation.
     */
    private final ZigBeeDongle dongle;
    /**
     * The ZigBee network networkDiscoverer.
     */
    private final ZigBeeNetworkDiscoverer networkDiscoverer;

    /**
     * The network state.
     */
    private ZigBeeNetworkStateImpl networkState;
    
    ZigBeeNetwork zigbeeNetwork;
    
    /**
     * The command listeners.
     */
    private List<CommandListener> commandListeners = new ArrayList<CommandListener>();

    /**
     * Constructor which configures serial port and ZigBee network.
     * @param dongle the dongle
     * @param resetNetwork whether network is to be reset
     */
    public ZigBeeNetworkManager(final ZigBeeDongle dongle, final boolean resetNetwork) {
        this.dongle = dongle;
        this.networkState = new ZigBeeNetworkStateImpl(resetNetwork);
        this.networkDiscoverer = new ZigBeeNetworkDiscoverer(networkState, this);

//        setNetwork(dongle);
        
        dongle.setZigBeeNetwork(this);
//        setNetworkState(networkState);
    }

    /**
     * Starts up ZigBee API components.
     * @return TRUE if startup was successful.
     */
    public boolean startup() {
        networkState.startup();
        if (dongle.startup()) {
            networkDiscoverer.startup();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Shuts down ZigBee API components.
     */
    public void shutdown() {
        networkDiscoverer.shutdown();
        dongle.shutdown();
        networkState.shutdown();
    }

    @Override
    public int sendCommand(Command command) throws ZigBeeException {
       return dongle.sendCommand(command);
    }

    @Override
    public void addCommandListener(CommandListener commandListener) {
        final List<CommandListener> modifiedCommandListeners = new ArrayList<CommandListener>(commandListeners);
        modifiedCommandListeners.add(commandListener);
        commandListeners = Collections.unmodifiableList(modifiedCommandListeners);
    }

    @Override
    public void removeCommandListener(CommandListener commandListener) {
        final List<CommandListener> modifiedCommandListeners = new ArrayList<CommandListener>(commandListeners);
        modifiedCommandListeners.remove(commandListener);
        commandListeners = Collections.unmodifiableList(modifiedCommandListeners);
    }    

    @Override
    public void receiveCommand(Command command) {
        for (final CommandListener commandListener : commandListeners) {
            commandListener.commandReceived(command);
        }
    }

    @Override
    public void setZigBeeNetwork(ZigBeeNetwork zigbeeNetwork) {
        this.zigbeeNetwork = zigbeeNetwork;
    }

    /**
     * Sets device label.
     * @param networkAddress the network address
     * @param endPointId the end point ID
     * @param label the label
     */
    public void setDeviceLabel(final int networkAddress, final int endPointId, final String label) {
        final ZigBeeDevice device = networkState.getDevice(networkAddress, endPointId);
        device.setLabel(label);
        networkState.updateDevice(device);
    }

    /**
     * Removes device(s) by network address.
     * @param networkAddress the network address
     */
    public void removeDevice(final int networkAddress) {
        final List<ZigBeeDevice> devices = networkState.getDevices();
        final List<ZigBeeDevice> devicesToRemove = new ArrayList<ZigBeeDevice>();
        for (final ZigBeeDevice device : devices) {
            if (device.getNetworkAddress() == networkAddress) {
                devicesToRemove.add(device);
            }
        }

        for (final ZigBeeDevice device : devicesToRemove) {
            networkState.removeDevice(device.getNetworkAddress(), device.getEndpoint());
        }
    }


    /**
     * Gets ZigBee devices.
     * @return list of ZigBee devices
     */
    public List<ZigBeeDevice> getDevices() {
        return networkState.getDevices();
    }

    /**
     * Sets group label.
     * @param groupId the group ID
     * @param label the label
     */
    public void addMembership(final int groupId, final String label) {
        if (networkState.getGroup(groupId) == null) {
            networkState.addGroup(new ZigBeeGroup(groupId, label));
        } else {
            final ZigBeeGroup group = networkState.getGroup(groupId);
            group.setLabel(label);
            networkState.updateGroup(group);
        }
    }

    /**
     * Removes group label.
     * @param groupId the group ID
     */
    public void removeMembership(final int groupId) {
        networkState.removeGroup(groupId);
    }

    /**
     * Gets group by network address.
     * @param groupId the group ID
     * @return the ZigBee group or null if no exists with given group ID.
     */
    public ZigBeeGroup getGroup(final int groupId) {
        return networkState.getGroup(groupId);
    }

    /**
     * Gets all groups.
     * @return list of groups.
     */
    public List<ZigBeeGroup> getGroups() {
        return networkState.getGroups();
    }

    public ZigBeeNetworkState getNetworkState() {
        return networkState;
    }
}
