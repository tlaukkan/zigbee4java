package org.bubblecloud.zigbee.v3.zdo.command;

import org.bubblecloud.zigbee.v3.zdo.ZdoCommand;
import org.bubblecloud.zigbee.v3.ZdoResponse;

/**
 * BindResponse.
 */
public class BindResponse extends ZdoCommand implements ZdoResponse {
    /**
     * The status.
     */
    private int status;
    /**
     * The source address.
     */
    private int sourceAddress;

    public BindResponse() {
    }

    public BindResponse(int status, int sourceAddress) {
        this.sourceAddress = sourceAddress;
        this.status = status;
    }

    public int getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(int sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bind Response " +
                "sourceAddress=" + sourceAddress +
                ", status=" + status;
    }
}
