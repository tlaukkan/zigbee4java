package org.bubblecloud.zigbee.v3;

/**
 * Local implementation of ZigBee API connects
 * directly to local ZigBee dongle.
 */
public class ZigBeeApiDongleImpl extends ZigBeeApi {
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
     * @param dongle the dongle
     * @param resetNetwork whether network is to be reset
     */
    public ZigBeeApiDongleImpl(final ZigBeeDongle dongle, final boolean resetNetwork) {
        this.dongle = dongle;
        this.networkState = new ZigBeeNetworkStateImpl(resetNetwork);
        this.networkDiscoverer = new ZigBeeNetworkDiscoverer(networkState, dongle);

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
