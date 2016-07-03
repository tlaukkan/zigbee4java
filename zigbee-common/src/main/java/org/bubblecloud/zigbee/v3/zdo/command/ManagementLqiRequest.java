package org.bubblecloud.zigbee.v3.zdo.command;

import org.bubblecloud.zigbee.v3.zdo.ZdoCommand;
import org.bubblecloud.zigbee.v3.ZdoRequest;

/**
 * ManagementLqiRequest.
 */
public class ManagementLqiRequest extends ZdoCommand implements ZdoRequest {
    /**
     * The network address.
     */
    private int networkAddress;
    /**
     * The started index.
     */
    private int startIndex;

    public ManagementLqiRequest() {
    }

    public ManagementLqiRequest(int networkAddress, int type, int startIndex) {
        this.networkAddress = networkAddress;
        this.startIndex = startIndex;
    }

    public int getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(int networkAddress) {
        this.networkAddress = networkAddress;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public String toString() {
        return "Management LQI Request " +
                "networkAddress=" + networkAddress +
                ", startIndex=" + startIndex;
    }

    @Override
    public int getDestinationAddress() {
        return networkAddress;
    }
}
