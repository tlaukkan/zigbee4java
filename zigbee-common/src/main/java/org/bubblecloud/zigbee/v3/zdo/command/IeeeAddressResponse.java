package org.bubblecloud.zigbee.v3.zdo.command;

import org.bubblecloud.zigbee.v3.zdo.ZdoCommand;
import org.bubblecloud.zigbee.v3.ZdoResponse;

import java.util.Arrays;

/**
 * IeeeAddressResponse.
 */
public class IeeeAddressResponse extends ZdoCommand implements ZdoResponse {
    /**
     * The status.
     */
    private int status;
    /**
     * The source address mode.
     */
    private int sourceAddressMode;
    /**
     * IEEE address.
     */
    private long ieeeAddress;
    /**
     * Network address.
     */
    private int networkAddress;
    /**
     * Start index.
     */
    public int startIndex;
    /**
     * Number of associated devices.
     */
    public int numberOfAssociatedDevices;
    /**
     * Associated device list.
     */
    public int[] associatedDeviceList;

    public IeeeAddressResponse() {
    }

    public IeeeAddressResponse(int status, int sourceAddressMode, long ieeeAddress, int networkAddress, int startIndex, int numberOfAssociatedDevices, int[] associatedDeviceList) {
        this.status = status;
        this.sourceAddressMode = sourceAddressMode;
        this.ieeeAddress = ieeeAddress;
        this.networkAddress = networkAddress;
        this.startIndex = startIndex;
        this.numberOfAssociatedDevices = numberOfAssociatedDevices;
        this.associatedDeviceList = associatedDeviceList;
    }

    @Override
    public int getSourceAddress() {
        return networkAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSourceAddressMode() {
        return sourceAddressMode;
    }

    public void setSourceAddressMode(int sourceAddressMode) {
        this.sourceAddressMode = sourceAddressMode;
    }

    public long getIeeeAddress() {
        return ieeeAddress;
    }

    public void setIeeeAddress(long ieeeAddress) {
        this.ieeeAddress = ieeeAddress;
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

    public int getNumberOfAssociatedDevices() {
        return numberOfAssociatedDevices;
    }

    public void setNumberOfAssociatedDevices(int numberOfAssociatedDevices) {
        this.numberOfAssociatedDevices = numberOfAssociatedDevices;
    }

    public int[] getAssociatedDeviceList() {
        return associatedDeviceList;
    }

    public void setAssociatedDeviceList(int[] associatedDeviceList) {
        this.associatedDeviceList = associatedDeviceList;
    }

    @Override
    public String toString() {
        return "IEEE Address Response " +
                "status=" + status +
                ", sourceAddressMode=" + sourceAddressMode +
                ", ieeeAddress=" + ieeeAddress +
                ", networkAddress=" + networkAddress +
                ", startIndex=" + startIndex +
                ", numberOfAssociatedDevices=" + numberOfAssociatedDevices +
                ", associatedDeviceList=" + Arrays.toString(associatedDeviceList);
    }
}
