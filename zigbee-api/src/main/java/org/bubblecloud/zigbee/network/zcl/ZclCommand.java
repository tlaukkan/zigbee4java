package org.bubblecloud.zigbee.network.zcl;

import org.bubblecloud.zigbee.simple.Command;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclClusterType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;

/**
 * Base class for value object classes holding ZCL commands.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclCommand extends Command {
    /**
     * The source address.
     */
    private int sourceAddress;
    /**
     * The source endpoint.
     */
    private int sourceEnpoint;
    /**
     * The destination address.
     */
    private int destinationAddress;
    /**
     * The destination endpoint.
     */
    private int destinationEndpoint;
    /**
     * The type.
     */
    private ZclCommandType type;
    /**
     * The cluster ID for generic messages.
     */
    private Integer clusterId;
    /**
     * The transaction ID.
     */
    private Byte transactionId;

    /**
     * Default constructor.
     */
    public ZclCommand() {

    }

    /**
     * Constructor which copies field values from command message.
     * @param commandMessage the command message
     */
    public ZclCommand(final ZclCommandMessage commandMessage) {
        this.sourceAddress = commandMessage.getSourceAddress();
        this.sourceEnpoint = commandMessage.getSourceEnpoint();
        this.destinationAddress = commandMessage.getDestinationAddress();
        this.destinationEndpoint = commandMessage.getDestinationEndpoint();
        this.type = commandMessage.getType();
        this.clusterId = commandMessage.getClusterId();
        this.transactionId = commandMessage.getTransactionId();
    }

    /**
     * Gets the type
     * @return the type
     */
    public ZclCommandType getType() {
        return type;
    }

    /**
     * Sets the command
     * @param type the command
     */
    public void setType(final ZclCommandType type) {
        this.type = type;
    }

    /**
     * Gets destination address.
     * @return the destination address.
     */
    public int getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * Sets destination address.
     * @param destinationAddress the destination address.
     */
    public void setDestinationAddress(final int destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * Gets destination endpoint.
     * @return the destination endpoint
     */
    public int getDestinationEndpoint() {
        return destinationEndpoint;
    }

    /**
     * Sets destination endpoint
     * @param destinationEndpoint the destination endpoint
     */
    public void setDestinationEndpoint(final int destinationEndpoint) {
        this.destinationEndpoint = destinationEndpoint;
    }

    /**
     * Gets source address.
     * @return the source address
     */
    public int getSourceAddress() {
        return sourceAddress;
    }

    /**
     * Sets source address.
     * @param sourceAddress the source address
     */
    public void setSourceAddress(final int sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * Gets source endpoint.
     * @return the source endpoint
     */
    public int getSourceEnpoint() {
        return sourceEnpoint;
    }

    /**
     * Sets source endpoint.
     * @param sourceEnpoint the source endpoint
     */
    public void setSourceEnpoint(final int sourceEnpoint) {
        this.sourceEnpoint = sourceEnpoint;
    }

    /**
     * Gets the cluster ID for generic messages.
     * @return the cluster ID.
     */
    public Integer getClusterId() {
        return clusterId;
    }

    /**
     * Sets the cluster ID for generic messages.
     * @param clusterId the cluster ID
     */
    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    /**
     * Gets the transaction ID.
     * @return the transaction ID
     */
    public Byte getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the transaction ID.
     * @param transactionId the transaction ID
     */
    public void setTransactionId(final Byte transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Converts ZclCommand to ZclCommandMessage.
     * @return the ZclCommandMessage
     */
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage commandMessage = new ZclCommandMessage();
        commandMessage.setSourceAddress(sourceAddress);
        commandMessage.setSourceEnpoint(sourceEnpoint);
        commandMessage.setDestinationAddress(destinationAddress);
        commandMessage.setDestinationEndpoint(destinationEndpoint);
        commandMessage.setType(type);
        commandMessage.setClusterId(clusterId);
        commandMessage.setTransactionId(transactionId);
        return commandMessage;
    }

    @Override
    public String toString() {
        Integer resolvedClusterId = getClusterId();
        if (resolvedClusterId == null) {
            resolvedClusterId = type.getClusterType().getId();
        }
        return ZclClusterType.getValueById(resolvedClusterId).getLabel() + " - " + type + " "
                + sourceAddress + "." + sourceEnpoint + " -> "
                + destinationAddress + "." + destinationEndpoint + " tid=" + transactionId;
    }

}
