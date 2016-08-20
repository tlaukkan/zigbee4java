package org.bubblecloud.zigbee.v3.zdo.command;

import org.bubblecloud.zigbee.v3.zdo.ZdoCommand;
import org.bubblecloud.zigbee.v3.ZdoResponse;

/**
 * ManagementPermitJoinResponse.
 */
public class ManagementPermitJoinResponse extends ZdoCommand implements ZdoResponse {
    /**
     * The status.
     */
    private int status;
    /**
     * The source address.
     */
    private int sourceAddress;

    public ManagementPermitJoinResponse() {
    }

    public ManagementPermitJoinResponse(int status, int sourceAddress) {
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
        return "ManagementPermitJoinResponse " +
                "sourceAddress=" + sourceAddress +
                ", status=" + status;
    }
}
