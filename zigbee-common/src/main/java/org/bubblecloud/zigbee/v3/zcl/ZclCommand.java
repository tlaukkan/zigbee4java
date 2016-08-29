package org.bubblecloud.zigbee.v3.zcl;

import org.bubblecloud.zigbee.v3.Command;
import org.bubblecloud.zigbee.v3.ZigBeeAddress;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclClusterType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;

/**
 * Base class for value object classes holding ZCL commands.
 *
 * @author Tommi S.E. Laukkanen
 * @author Chris Jackson
 */
public class ZclCommand extends Command {
    /**
     * The source address.
     */
    private ZigBeeAddress sourceAddress;
    /**
     * The destination address.
     */
    private ZigBeeAddress destinationAddress;
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
        this.destinationAddress = commandMessage.getDestinationAddress();
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
    public ZigBeeAddress getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * Sets destination address.
     * @param destinationAddress the destination address.
     */
    public void setDestinationAddress(final ZigBeeAddress destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * Gets source address.
     * @return the source address
     */
    public ZigBeeAddress getSourceAddress() {
        return sourceAddress;
    }

    /**
     * Sets source address.
     * @param sourceAddress the source address
     */
    public void setSourceAddress(final ZigBeeAddress sourceAddress) {
        this.sourceAddress = sourceAddress;
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
        commandMessage.setDestinationAddress(destinationAddress);
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
                + sourceAddress + " -> "
                + destinationAddress + " tid=" + transactionId;
    }

}
