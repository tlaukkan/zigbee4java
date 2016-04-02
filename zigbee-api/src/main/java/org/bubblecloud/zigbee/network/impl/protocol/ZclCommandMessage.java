package org.bubblecloud.zigbee.network.impl.protocol;

import org.bubblecloud.zigbee.network.impl.ApplicationFrameworkLayer;

import java.util.TreeMap;

/**
 * Created by tlaukkan on 4/2/2016.
 */
public class ZclCommandMessage {

    private int sourceAddress;
    private short sourceEnpoint;
    private int destinationAddress;
    private short destinationEndpoint;

    private int profileId;
    private int clusterId;
    private byte commandId;
    private byte transactionId;

    ZclCommand command;

    TreeMap<ZclCommandField, Object> fields;

    public ZclCommandMessage() {
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public ZclCommand getCommand() {
        return command;
    }

    public void setCommand(ZclCommand command) {
        this.command = command;
    }

    public byte getCommandId() {
        return commandId;
    }

    public void setCommandId(byte commandId) {
        this.commandId = commandId;
    }

    public int getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(int destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public short getDestinationEndpoint() {
        return destinationEndpoint;
    }

    public void setDestinationEndpoint(short destinationEndpoint) {
        this.destinationEndpoint = destinationEndpoint;
    }

    public TreeMap<ZclCommandField, Object> getFields() {
        return fields;
    }

    public void setFields(TreeMap<ZclCommandField, Object> fields) {
        this.fields = fields;
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

    public short getSourceEnpoint() {
        return sourceEnpoint;
    }

    public void setSourceEnpoint(short sourceEnpoint) {
        this.sourceEnpoint = sourceEnpoint;
    }

    public byte getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(byte transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return command.name() + " " + sourceAddress + "." + sourceEnpoint + " -> " + destinationAddress + "." + destinationEndpoint + " " + fields;
    }
}
