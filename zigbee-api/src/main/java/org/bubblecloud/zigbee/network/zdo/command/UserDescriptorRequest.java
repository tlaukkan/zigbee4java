package org.bubblecloud.zigbee.network.zdo.command;

import org.bubblecloud.zigbee.network.zdo.ZdoCommand;
import org.bubblecloud.zigbee.simple.ZdoRequest;

/**
 * UserDescriptorRequest.
 */
public class UserDescriptorRequest extends ZdoCommand implements ZdoRequest {
    /**
     * Destination address.
     */
    private int destinationAddress;
    /**
     * Network address of interest.
     */
    private int networkAddressOfInterest;

    public UserDescriptorRequest() {
    }

    public UserDescriptorRequest(int destinationAddress, int networkAddressOfInterest) {
        this.destinationAddress = destinationAddress;
        this.networkAddressOfInterest = networkAddressOfInterest;
    }

    public int getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(int destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public int getNetworkAddressOfInterest() {
        return networkAddressOfInterest;
    }

    public void setNetworkAddressOfInterest(int networkAddressOfInterest) {
        this.networkAddressOfInterest = networkAddressOfInterest;
    }

    @Override
    public String toString() {
        return "User Descriptor Request " +
                "destinationAddress=" + destinationAddress +
                ", networkAddressOfInterest=" + networkAddressOfInterest;
    }
}
