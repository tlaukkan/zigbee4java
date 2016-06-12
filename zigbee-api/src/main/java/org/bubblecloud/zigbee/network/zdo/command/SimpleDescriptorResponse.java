package org.bubblecloud.zigbee.network.zdo.command;

import org.bubblecloud.zigbee.network.zdo.ZdoCommand;
import org.bubblecloud.zigbee.simple.ZdoResponse;

import java.util.Arrays;

/**
 * SimpleDescriptorResponse.
 */
public class SimpleDescriptorResponse extends ZdoCommand implements ZdoResponse {
    /**
     * The source address.
     */
    public int sourceAddress;
    /**
     * The status.
     */
    public int status;

    /**
     * The profile ID.
     */
    public int profileId;
    /**
     * The device ID.
     */
    public int deviceId;
    /**
     * The device version.
     */
    public int deviceVersion;
    /**
     * The network address.
     */
    public int networkAddress;
    /**
     * The endpoint.
     */
    public int endpoint;
    /**
     * The input clusters.
     */
    private int[] inputClusters;
    /**
     * The output clusters.
     */
    private int[] outputClusters;

    public SimpleDescriptorResponse() {
    }

    public SimpleDescriptorResponse(int sourceAddress, int status, int profileId, int deviceId, int deviceVersion, int networkAddress, int endpoint, int[] inputClusters, int[] outputClusters) {
        this.sourceAddress = sourceAddress;
        this.status = status;
        this.profileId = profileId;
        this.deviceId = deviceId;
        this.deviceVersion = deviceVersion;
        this.networkAddress = networkAddress;
        this.endpoint = endpoint;
        this.inputClusters = inputClusters;
        this.outputClusters = outputClusters;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(int deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public int getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(int endpoint) {
        this.endpoint = endpoint;
    }

    public int[] getInputClusters() {
        return inputClusters;
    }

    public void setInputClusters(int[] inputClusters) {
        this.inputClusters = inputClusters;
    }

    public int getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(int networkAddress) {
        this.networkAddress = networkAddress;
    }

    public int[] getOutputClusters() {
        return outputClusters;
    }

    public void setOutputClusters(int[] outputClusters) {
        this.outputClusters = outputClusters;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
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
        return "Simple Descriptor Response " +
                "deviceId=" + deviceId +
                ", sourceAddress=" + sourceAddress +
                ", status=" + status +
                ", profileId=" + profileId +
                ", deviceVersion=" + deviceVersion +
                ", networkAddress=" + networkAddress +
                ", endpoint=" + endpoint +
                ", inputClusters=" + Arrays.toString(inputClusters) +
                ", outputClusters=" + Arrays.toString(outputClusters);
    }
}
