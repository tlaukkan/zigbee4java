package org.bubblecloud.zigbee.network.zcl.field;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.network.zcl.ZclListItemField;

/**
 * Read Attribute Status Record field.
 */
public class AttributeRecord implements ZclListItemField {
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
     * @param attributeIdentifier the attribute identifier
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

    @Override
    public void serialize(final ZBSerializer serializer) {
        serializer.appendBoolean(direction);
        serializer.appendShort((short) attributeIdentifier);
    }

    @Override
    public void deserialize(final ZBDeserializer deserializer) {
        direction = deserializer.read_boolean();
        attributeIdentifier = deserializer.read_short() & (0xFFFF);
    }

    @Override
    public String toString() {
        return "Attribute Record " +
                ", direction=" + direction +
                ", attributeIdentifier=" + attributeIdentifier;
    }
}
