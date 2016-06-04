package org.bubblecloud.zigbee.network.zcl;


import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandTypeRegistrar;

/**
 * Base class for value object classes holding a commands.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclCommand {
    /**
     * The source address.
     */
    private int sourceAddress;
    /**
     * The source endpoint.
     */
    private short sourceEnpoint;
    /**
     * The destination address.
     */
    private int destinationAddress;
    /**
     * The destination endpoint.
     */
    private short destinationEndpoint;
    /**
     * The type.
     */
    private ZclCommandType type;
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
    public short getDestinationEndpoint() {
        return destinationEndpoint;
    }

    /**
     * Sets destination endpoint
     * @param destinationEndpoint the destination endpoint
     */
    public void setDestinationEndpoint(final short destinationEndpoint) {
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
    public short getSourceEnpoint() {
        return sourceEnpoint;
    }

    /**
     * Sets source endpoint.
     * @param sourceEnpoint the source endpoint
     */
    public void setSourceEnpoint(final short sourceEnpoint) {
        this.sourceEnpoint = sourceEnpoint;
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

    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = new ZclCommandMessage();
        message.setSourceAddress(sourceAddress);
        message.setSourceEnpoint(sourceEnpoint);
        message.setDestinationAddress(destinationAddress);
        message.setDestinationEndpoint(destinationEndpoint);
        message.setType(type);
        message.setTransactionId(transactionId);
        return message;
    }

    @Override
    public String toString() {
        return type + " " + sourceAddress + "." + sourceEnpoint + " -> "
                + destinationAddress + "." + destinationEndpoint;
    }

}
