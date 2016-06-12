package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.network.SerialPort;

/**
 * Local implementation of ZigBee API connects
 * directly to local ZigBee dongle.
 */
public class LocalZigBeeApi extends SimpleZigBeeApi {
    /**
     * The dongle implementation.
     */
    private final ZigBeeDongle dongle;
    /**
     * The ZigBee network state.
     */
    private final ZigBeeNetworkStateImpl networkState;
    /**
     * The ZigBee network networkDiscoverer.
     */
    private final ZigBeeNetworkDiscoverer networkDiscoverer;

    /**
     * Constructor which configures serial port and ZigBee network.
     * @param serialPort the serial port
     * @param pan the pan
     * @param channel the channel
     * @param resetNetwork
     */
    public LocalZigBeeApi(final SerialPort serialPort, final int pan, final int channel, final boolean resetNetwork) {
        dongle = new ZigBeeDongleTiCc2531Impl(serialPort, pan, channel, resetNetwork);
        networkState = new ZigBeeNetworkStateImpl(resetNetwork);
        networkDiscoverer = new ZigBeeNetworkDiscoverer(networkState, dongle);

        setNetwork(dongle);
        setNetworkState(networkState);
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

}
