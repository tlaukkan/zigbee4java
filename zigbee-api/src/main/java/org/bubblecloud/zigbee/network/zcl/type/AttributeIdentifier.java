package org.bubblecloud.zigbee.network.zcl.type;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;

/**
 * AttributeInformation information data type;
 */
public class AttributeIdentifier implements ZclSerializable {
    /**
     * The attribute identifier.
     */
    private int attributeIdentifier;

    public int getAttributeIdentifier() {
        return attributeIdentifier;
    }

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
