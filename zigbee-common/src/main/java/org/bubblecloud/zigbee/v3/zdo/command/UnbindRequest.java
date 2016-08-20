package org.bubblecloud.zigbee.v3.zdo.command;

import org.bubblecloud.zigbee.v3.zdo.ZdoCommand;
import org.bubblecloud.zigbee.v3.ZdoRequest;

/**
 * UnbindRequest.
 */
public class UnbindRequest extends ZdoCommand implements ZdoRequest {
    /**
     * Destination address;
     */
    int destinationAddress;
    /**
     * Bind source address.
     */
    long bindSourceAddress;
    /**
     * Bind source end point.
     */
    int bindSourceEndpoint;
    /**
     * Bind cluster.
     */
    int bindCluster;
    /**
     * Bind destination addressing mode.
     */
    int bindDestinationAddressingMode;
    /**
     * Bind destination address.
     */
    long bindDestinationAddress;
    /**
     * Bind destination endpoint.
     */
    int bindDestinationEndpoint;

    public UnbindRequest() {
    }

    public UnbindRequest(int destinationAddress, long bindSourceAddress, int bindSourceEndpoint, int bindCluster, int bindDestinationAddressingMode, long bindDestinationAddress, int bindDestinationEndpoint) {
        this.destinationAddress = destinationAddress;
        this.bindSourceAddress = bindSourceAddress;
        this.bindSourceEndpoint = bindSourceEndpoint;
        this.bindCluster = bindCluster;
        this.bindDestinationAddressingMode = bindDestinationAddressingMode;
        this.bindDestinationAddress = bindDestinationAddress;
        this.bindDestinationEndpoint = bindDestinationEndpoint;
    }

    public int getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(int destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public long getBindSourceAddress() {
        return bindSourceAddress;
    }

    public void setBindSourceAddress(long bindSourceAddress) {
        this.bindSourceAddress = bindSourceAddress;
    }

    public int getBindSourceEndpoint() {
        return bindSourceEndpoint;
    }

    public void setBindSourceEndpoint(int bindSourceEndpoint) {
        this.bindSourceEndpoint = bindSourceEndpoint;
    }

    public int getBindCluster() {
        return bindCluster;
    }

    public void setBindCluster(int bindCluster) {
        this.bindCluster = bindCluster;
    }

    public int getBindDestinationAddressingMode() {
        return bindDestinationAddressingMode;
    }

    public void setBindDestinationAddressingMode(int bindDestinationAddressingMode) {
        this.bindDestinationAddressingMode = bindDestinationAddressingMode;
    }

    public long getBindDestinationAddress() {
        return bindDestinationAddress;
    }

    public void setBindDestinationAddress(long bindDestinationAddress) {
        this.bindDestinationAddress = bindDestinationAddress;
    }

    public int getBindDestinationEndpoint() {
        return bindDestinationEndpoint;
    }

    public void setBindDestinationEndpoint(int bindDestinationEndpoint) {
        this.bindDestinationEndpoint = bindDestinationEndpoint;
    }

    @Override
    public String toString() {
        return "Unbind Request " +
                "destinationAddress=" + destinationAddress +
                ", bindSourceAddress=" + bindSourceAddress +
                ", bindSourceEndpoint=" + bindSourceEndpoint +
                ", bindCluster=" + bindCluster +
                ", bindDestinationAddressingMode=" + bindDestinationAddressingMode +
                ", bindDestinationAddress=" + bindDestinationAddress +
                ", bindDestinationEndpoint=" + bindDestinationEndpoint;
    }
}
