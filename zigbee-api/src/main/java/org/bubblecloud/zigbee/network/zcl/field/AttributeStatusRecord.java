package org.bubblecloud.zigbee.network.zcl.field;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.network.zcl.ZclField;

/**
 * Read Attribute Status Record field.
 */
public class AttributeStatusRecord implements ZclField {
    /**
     * The status.
     */
    private int status;
    /**
     * The direction.
     */
    private boolean direction;
    /**
     * The attribute identifier.
     */
    private int attributeIdentifier;

    /**
     * Gets attribute identifier.
     * @return the attribute identifier
     */
    public int getAttributeIdentifier() {
        return attributeIdentifier;
    }

    /**
     * Sets attribute identifier.
     * @param attributeIdentifier the attribute identifier.
     */
    public void setAttributeIdentifier(int attributeIdentifier) {
        this.attributeIdentifier = attributeIdentifier;
    }

    /**
     * Is direction?
     * @return the direction
     */
    public boolean isDirection() {
        return direction;
    }

    /**
     * Sets direction.
     * @param direction the direction
     */
    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    /**
     * Gets status.
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets status.
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void serialize(final ZBSerializer serializer) {
        serializer.appendByte((byte) status);
        serializer.appendBoolean(direction);
        serializer.appendShort((short) attributeIdentifier);
    }

    @Override
    public void deserialize(final ZBDeserializer deserializer) {
        status = deserializer.read_byte() & (0xFF);
        direction = deserializer.read_boolean();
        attributeIdentifier = deserializer.read_short() & (0xFFFF);
    }

    @Override
    public String toString() {
        return "Attribute Status Record " +
                ", status=" + status +
                ", direction=" + direction +
                ", attributeIdentifier=" + attributeIdentifier;
    }
}
