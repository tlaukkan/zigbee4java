package org.bubblecloud.zigbee.simple;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The ZigBee network implementation. This class is thread safe.
 */
public class ZigBeeNetworkStateImpl implements ZigBeeNetworkState {
    /**
     * The logger.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ZigBeeNetworkStateImpl.class);
    /**
     * The devices in the ZigBee network.
     */
    private Map<String, ZigBeeDevice> devices = new TreeMap<String, ZigBeeDevice>();
    /**
     * The listeners of the ZigBee network.
     */
    private List<ZigBeeNetworkStateListener> listeners = Collections.unmodifiableList(
            new ArrayList<ZigBeeNetworkStateListener>());
    /**
     * The network reset flag.
     */
    private final boolean resetNetwork;
    /**
     * The network state file path.
     */
    private final String networkStateFilePath = "simple-network.json";

    /**
     * Constructor which configures whether network shoule be reset at startup
     * or loaded from serialized file.
     * @param resetNetwork the network reset flag
     */
    public ZigBeeNetworkStateImpl(boolean resetNetwork) {
        this.resetNetwork = resetNetwork;
    }

    /**
     * Starts up network state.
     */
    public void startup() {
        final File networkStateFile = new File(networkStateFilePath);
        final boolean networkStateExists = networkStateFile.exists();
        if (!resetNetwork && networkStateExists) {
            LOGGER.info("Loading network state...");
            final String networkStateString;
            try {
                networkStateString = FileUtils.readFileToString(networkStateFile);
            } catch (final IOException e) {
                LOGGER.error("Error loading network state from file: " + networkStateFile.getAbsolutePath());
                return;
            }
            final ZigBeeNetworkStateSerializer serializer = new ZigBeeNetworkStateSerializer();
            serializer.deserialize(this, networkStateString);
            LOGGER.info("Loading network state done.");
        }
    }

    /**
     * Shuts down the network state.
     */
    public void shutdown() {
        LOGGER.info("ZigBeeApi saving network state...");
        final ZigBeeNetworkStateSerializer serializer = new ZigBeeNetworkStateSerializer();
        final File networkStateFile = new File(networkStateFilePath);
        try {
            FileUtils.writeStringToFile(networkStateFile, serializer.serialize(this), false);
        } catch (final IOException e) {
            LOGGER.error("Error loading network state from file: " + networkStateFile.getAbsolutePath());
            return;
        }
        LOGGER.info("ZigBeeApi saving network state done.");
    }

    @Override
    public void addDevice(final ZigBeeDevice device) {
        synchronized (devices) {
            devices.put(device.getNetworkAddress() + "/" + device.getEndpoint(), device);
        }
        synchronized (this) {
            for (final ZigBeeNetworkStateListener listener : listeners) {
                listener.deviceAdded(device);
            }
        }
    }

    @Override
    public void updateDevice(ZigBeeDevice device) {
        synchronized (devices) {
            devices.put(device.getNetworkAddress() + "/" + device.getEndpoint(), device);
        }
        synchronized (this) {
            for (final ZigBeeNetworkStateListener listener : listeners) {
                listener.deviceUpdated(device);
            }
        }
    }

    @Override
    public ZigBeeDevice getDevice(final int networkAddress, int endpoint) {
        synchronized (devices) {
            return devices.get(networkAddress + "/" + endpoint);
        }
    }

    @Override
    public void removeDevice(final int networkAddress, int endpoint) {
        final ZigBeeDevice device;
        synchronized (devices) {
            device = devices.remove(networkAddress + "/" + endpoint);
        }
        synchronized (this) {
            if (device != null) {
                for (final ZigBeeNetworkStateListener listener : listeners) {
                    listener.deviceRemoved(device);
                }
            }
        }
    }

    @Override
    public List<ZigBeeDevice> getDevices() {
        synchronized (devices) {
            return new ArrayList<ZigBeeDevice>(devices.values());
        }
    }

    @Override
    public void addNetworkListener(final ZigBeeNetworkStateListener networkListener) {
        synchronized (this) {
            final List<ZigBeeNetworkStateListener> modifiedListeners = new ArrayList<ZigBeeNetworkStateListener>(
                    listeners);
            modifiedListeners.add(networkListener);
            listeners = Collections.unmodifiableList(modifiedListeners);
        }
    }

    @Override
    public void removeNetworkListener(final ZigBeeNetworkStateListener networkListener) {
        synchronized (this) {
            final List<ZigBeeNetworkStateListener> modifiedListeners = new ArrayList<ZigBeeNetworkStateListener>(
                    listeners);
            modifiedListeners.remove(networkListener);
            listeners = Collections.unmodifiableList(modifiedListeners);
        }
    }

}
