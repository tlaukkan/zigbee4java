package org.bubblecloud.zigbee.v3;

/**
 * Defines a unicast ZigBee address
 * 
 * @author Chris Jackson
 */
public class ZigBeeDeviceAddress extends ZigBeeAddress {
    private int networkAddress;
    private int endpoint;

    /**
     * Constructor
     * 
     * @param networkAddress
     *            the network address
     * @param endpoint
     *            the endpoint number
     */
    public ZigBeeDeviceAddress(int networkAddress, int endpoint) {
        this.networkAddress = networkAddress;
        this.endpoint = endpoint;
    }

    /**
     * Default constructor
     */
    public ZigBeeDeviceAddress() {
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    /**
     * Sets the endpoint number
     * 
     * @param endpoint
     *            number
     */
    public void setEndpoint(int endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Returns the endpoint number
     * 
     * @return the endpoint number
     */
    public int getEndpoint() {
        return endpoint;
    }

    /**
     * Returns the network address
     * 
     * @return the network address
     */
    public int getAddress() {
        return networkAddress;
    }

    /**
     * Sets the network address
     * 
     * @param networkAddress
     *            the network address
     */
    public void setAddress(int networkAddress) {
        this.networkAddress = networkAddress;
    }

    @Override
    public String toString() {
        return networkAddress + "/" + endpoint;
    }
}
