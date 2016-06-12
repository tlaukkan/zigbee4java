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
    private int endpoint;
    /**
     * The profile ID.
     */
    private int profileId;
    /**
     * The logical type of the device.
     */
    private int deviceType;
    /**
     * The device ID.
     */
    private int deviceId;
    /**
     * The manufacturer code.
     */
    private int manufacturerCode;
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
     * Label.
     */
    private String label;
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
    public int getEndpoint() {
        return endpoint;
    }

    /**
     * Sets end point
     * @param endpoint the end point
     */
    public void setEndpoint(int endpoint) {
        this.endpoint = endpoint;
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

    /**
     * Gets device logical type.
     * @return the device logical type.
     */
    public int getDeviceType() {
        return deviceType;
    }

    /**
     * Sets device logical type.
     * @param deviceType the device logical type
     */
    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Gets manufacturer code.
     * @return the manufacturer code.
     */
    public int getManufacturerCode() {
        return manufacturerCode;
    }

    /**
     * Sets manufacturer code.
     * @param manufacturerCode the manufacturer code.
     */
    public void setManufacturerCode(int manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    /**
     * Gets label.
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label.
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ZigBeeDevice " +
                "label=" + label +
                ", networkAddress=" + networkAddress +
                ", endpoint=" + endpoint +
                ", ieeeAddress=" + ieeeAddress +
                ", profileId=" + profileId +
                ", deviceType=" + deviceType +
                ", deviceId=" + deviceId +
                ", manufacturerCode=" + manufacturerCode +
                ", deviceVersion=" + deviceVersion +
                ", inputClusterIds=" + Arrays.toString(inputClusterIds) +
                ", outputClusterIds=" + Arrays.toString(outputClusterIds);
    }
}
