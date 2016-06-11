package org.bubblecloud.zigbee.simple;

/**
 * ZigBee dongle interface implemented by different dongle hardware drivers.
 */
public interface ZigBeeDongle extends ZigBeeNetwork {
    /**
     * Starts up dongle.
     *
     * @return true if startup was success.
     */
    boolean startup();

    /**
     * Shuts down dongle.
     */
    void shutdown();
}
