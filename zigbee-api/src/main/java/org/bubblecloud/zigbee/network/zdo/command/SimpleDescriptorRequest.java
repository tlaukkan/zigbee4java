package org.bubblecloud.zigbee.network.zdo.command;

import org.bubblecloud.zigbee.network.zdo.ZdoCommand;
import org.bubblecloud.zigbee.simple.ZdoRequest;

/**
 * SimpleDescriptorRequest
 */
public class SimpleDescriptorRequest extends ZdoCommand implements ZdoRequest {
    /**
     * Destination address.
     */
    private int destinationAddress;
    /**
     * Endpoint.
     */
    private int endpoint;

    public SimpleDescriptorRequest() {
    }

    public SimpleDescriptorRequest(int destinationAddress, int endpoint) {
        this.destinationAddress = destinationAddress;
        this.endpoint = endpoint;
    }

    public int getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(int destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public int getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(int endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "Simple Descriptor Request " +
                "destinationAddress=" + destinationAddress +
                ", endpoint=" + endpoint;
    }
}
