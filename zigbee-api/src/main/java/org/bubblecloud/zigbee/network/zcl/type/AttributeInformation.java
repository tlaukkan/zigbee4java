package org.bubblecloud.zigbee.network.zcl.type;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;

/**
 * AttributeInformation information data type;
 */
public class AttributeInformation implements ZclSerializable {
    /**
     * The attribute identifier.
     */
    private int attributeIdentifier;
    /**
     * The attribute data type.
     */
    private int attributeDataType;

    public int getAttributeDataType() {
        return attributeDataType;
    }

    public void setAttributeDataType(int attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    public int getAttributeIdentifier() {
        return attributeIdentifier;
    }

    public void setAttributeIdentifier(int attributeIdentifier) {
        this.attributeIdentifier = attributeIdentifier;
    }

    @Override
    public void serialize(final ZBSerializer serializer) {
        serializer.appendShort((short) attributeIdentifier);
        serializer.appendByte((byte) attributeDataType);
    }

    @Override
    public void deserialize(final ZBDeserializer deserializer) {
        attributeIdentifier = deserializer.read_short() & (0xFFFF);
        attributeDataType = deserializer.read_byte() & (0xFF);
    }

    @Override
    public String toString() {
        return "Attribute Information " +
                "attributeDataType=" + attributeDataType +
                ", attributeIdentifier=" + attributeIdentifier;
    }
}
