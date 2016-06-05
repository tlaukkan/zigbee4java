package org.bubblecloud.zigbee.simple;

import java.util.Arrays;

/**
 * Value object for ZigBee device.
 */
public class ZigBeeDevice {
    /**
     * The IEEE address.
     */
    private long ieeeAddress;
    /**
     * The network address.
     */
    private int networkAddress;
    /**
     * The end point.
     */
    private int endPoint;
    /**
     * The profile ID.
     */
    private int profileId;
    /**
     * The device ID.
     */
    private int deviceId;
    /**
     * The device version.
     */
    private int deviceVersion;
    /**
     * Input clusters.
     */
    private int[] inputClusterIds;
    /**
     * Output clusters.
     */
    private int[] outputClusterIds;

    /**
     * Gets the device ID.
     * @return the device ID
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the device ID.
     * @param deviceId the device ID.
     */
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Gets device version.
     * @return the device version
     */
    public int getDeviceVersion() {
        return deviceVersion;
    }

    /**
     * Sets device version.
     * @param deviceVersion the device version
     */
    public void setDeviceVersion(int deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    /**
     * Gets end point.
     * @return the end point
     */
    public int getEndPoint() {
        return endPoint;
    }

    /**
     * Sets end point
     * @param endPoint the end point
     */
    public void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Gets IEEE Address.
     * @return the IEEE address
     */
    public long getIeeeAddress() {
        return ieeeAddress;
    }

    /**
     * Sets IEEE Address.
     * @param ieeeAddress the IEEE address
     */
    public void setIeeeAddress(long ieeeAddress) {
        this.ieeeAddress = ieeeAddress;
    }

    /**
     * Gets input cluster IDs.
     * @return the input cluster IDs
     */
    public int[] getInputClusterIds() {
        return inputClusterIds;
    }

    /**
     * Sets input cluster IDs.
     * @param inputClusterIds the input cluster IDs
     */
    public void setInputClusterIds(int[] inputClusterIds) {
        this.inputClusterIds = inputClusterIds;
    }

    /**
     * Gets network address.
     * @return the network address
     */
    public int getNetworkAddress() {
        return networkAddress;
    }

    /**
     * Sets network address.
     * @param networkAddress the network address
     */
    public void setNetworkAddress(int networkAddress) {
        this.networkAddress = networkAddress;
    }

    /**
     * Gets output cluster IDs.
     * @return the output cluster IDs
     */
    public int[] getOutputClusterIds() {
        return outputClusterIds;
    }

    /**
     * Sets output cluster IDs.
     * @param outputClusterIds the output cluster IDs
     */
   public void setOutputClusterIds(int[] outputClusterIds) {
        this.outputClusterIds = outputClusterIds;
    }

    /**
     * Gets profile ID.
     * @return the profile ID.
     */
    public int getProfileId() {
        return profileId;
    }

    /**
     * Sets profile ID
     * @param profileId the profile ID
     */
    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "ZigBeeDevice " +
                "deviceId=" + deviceId +
                ", deviceVersion=" + deviceVersion +
                ", ieeeAddress=" + ieeeAddress +
                ", networkAddress=" + networkAddress +
                ", endPoint=" + endPoint +
                ", profileId=" + profileId +
                ", inputClusterIds=" + Arrays.toString(inputClusterIds) +
                ", outputClusterIds=" + Arrays.toString(outputClusterIds);
    }
}
