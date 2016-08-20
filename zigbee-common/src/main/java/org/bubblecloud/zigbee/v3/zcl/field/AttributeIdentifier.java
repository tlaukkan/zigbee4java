package org.bubblecloud.zigbee.v3.zcl.field;

import org.bubblecloud.zigbee.v3.serialization.ZBDeserializer;
import org.bubblecloud.zigbee.v3.serialization.ZBSerializer;
import org.bubblecloud.zigbee.v3.zcl.ZclListItemField;

/**
 * Attribute Identifier field.
 */
public class AttributeIdentifier implements ZclListItemField {
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
     * Sets attribute.
     * @param attributeIdentifier the attribute identifier
     */
    public void setAttributeIdentifier(int attributeIdentifier) {
        this.attributeIdentifier = attributeIdentifier;
    }

    @Override
    public void serialize(final ZBSerializer serializer) {
        serializer.appendShort((short) attributeIdentifier);
    }

    @Override
    public void deserialize(final ZBDeserializer deserializer) {
        attributeIdentifier = deserializer.read_short() & (0xFFFF);
    }

    @Override
    public String toString() {
        return "Attribute Identifier " +
                ", attributeIdentifier=" + attributeIdentifier;
    }
}
