package org.bubblecloud.zigbee.network.zdo.command;

import org.bubblecloud.zigbee.network.zdo.ZdoCommand;
import org.bubblecloud.zigbee.simple.ZdoResponse;

/**
 * NodeDescriptorResponse.
 */
public class NodeDescriptorResponse extends ZdoCommand implements ZdoResponse {
    /**
     * this field indicates either SUCCESS or FAILURE.
     */
    public int status;
    /**
     * the message's source network address.
     */
    public int sourceAddress;
    /**
     * Device's short address of this Node descriptor.
     */
    public int networkAddress;
    /**
     * Node Flags assigned for APS. For V1.0 all bits are reserved
     */
    public int apsFlags;
    /**
     * Indicates size of maximum NPDU. This field is used as a high level indication for api
     */
    public int bufferSize;
    /**
     * Capability flags stored for the MAC
     */
    public int capabilities;
    /**
     * Indicates if complex descriptor is available for the node
     */
    public boolean complexDescriptorAvailable;
    /**
     * Specifies a manufacturer code that is allocated by ZigBee Alliance, relating to the manufacturer to the device
     */
    public int manufacturerCode;
    /**
     * Logical type
     */
    public int logicalType;
    /**
     * Specifies the system server capability
     */
    public int serverMask;
    /**
     * Indicates maximum size of Transfer up to 0x7fff (This field is reserved in version 1.0 and shall be set to zero ).
     */
    public int maximumInTransferSize;
    /**
     * Indicates if user descriptor is available for the node
     */
    public boolean userDescriptorAvailable;
    /**
     * Frequency band.
     */
    public int frequencyBand;

    public NodeDescriptorResponse() {
    }

    public NodeDescriptorResponse(int status, int sourceAddress, int networkAddress, int apsFlags, int bufferSize, int capabilities, boolean complexDescriptorAvailable, int manufacturerCode, int logicalType, int serverMask, int transferSize, boolean userDescriptorAvailable, int frequencyBand) {
        this.status = status;
        this.sourceAddress = sourceAddress;
        this.networkAddress = networkAddress;
        this.apsFlags = apsFlags;
        this.bufferSize = bufferSize;
        this.capabilities = capabilities;
        this.complexDescriptorAvailable = complexDescriptorAvailable;
        this.manufacturerCode = manufacturerCode;
        this.logicalType = logicalType;
        this.serverMask = serverMask;
        this.maximumInTransferSize = transferSize;
        this.userDescriptorAvailable = userDescriptorAvailable;
        this.frequencyBand = frequencyBand;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(int sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public int getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(int networkAddress) {
        this.networkAddress = networkAddress;
    }

    public int getApsFlags() {
        return apsFlags;
    }

    public void setApsFlags(int apsFlags) {
        this.apsFlags = apsFlags;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(int capabilities) {
        this.capabilities = capabilities;
    }

    public boolean isComplexDescriptorAvailable() {
        return complexDescriptorAvailable;
    }

    public void setComplexDescriptorAvailable(boolean complexDescriptorAvailable) {
        this.complexDescriptorAvailable = complexDescriptorAvailable;
    }

    public int getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(int manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public int getLogicalType() {
        return logicalType;
    }

    public void setLogicalType(int logicalType) {
        this.logicalType = logicalType;
    }

    public int getServerMask() {
        return serverMask;
    }

    public void setServerMask(int serverMask) {
        this.serverMask = serverMask;
    }

    public int getMaximumInTransferSize() {
        return maximumInTransferSize;
    }

    public void setMaximumInTransferSize(int maximumInTransferSize) {
        this.maximumInTransferSize = maximumInTransferSize;
    }

    public boolean isUserDescriptorAvailable() {
        return userDescriptorAvailable;
    }

    public void setUserDescriptorAvailable(boolean userDescriptorAvailable) {
        this.userDescriptorAvailable = userDescriptorAvailable;
    }

    public int getFrequencyBand() {
        return frequencyBand;
    }

    public void setFrequencyBand(int frequencyBand) {
        this.frequencyBand = frequencyBand;
    }

    @Override
    public String toString() {
        return "Node Descriptor Response " +
                "status=" + status +
                ", sourceAddress=" + sourceAddress +
                ", networkAddress=" + networkAddress +
                ", apsFlags=" + apsFlags +
                ", bufferSize=" + bufferSize +
                ", capabilities=" + capabilities +
                ", complexDescriptorAvailable=" + complexDescriptorAvailable +
                ", manufacturerCode=" + manufacturerCode +
                ", logicalType=" + logicalType +
                ", serverMask=" + serverMask +
                ", maximumInTransferSize=" + maximumInTransferSize +
                ", userDescriptorAvailable=" + userDescriptorAvailable +
                ", frequencyBand=" + frequencyBand;
    }
}
