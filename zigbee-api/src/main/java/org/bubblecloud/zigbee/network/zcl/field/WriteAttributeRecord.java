package org.bubblecloud.zigbee.network.zcl.field;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.network.zcl.ZclListItemField;

/**
 * Write Attribute Record field.
 */
public class WriteAttributeRecord implements ZclListItemField {
    /**
     * The attribute identifier.
     */
    private int attributeIdentifier;
    /**
     * The attribute data type.
     */
    private int attributeDataType;
    /**
     * The attribute data type.
     */
    private Object attributeValue;

    /**
     * Gets attribute data type.
     * @return the attribute data type
     */
    public int getAttributeDataType() {
        return attributeDataType;
    }

    /**
     * Sets attribute data type
     * @param attributeDataType the attribute data type
     */
    public void setAttributeDataType(int attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    /**
     * Gets attribute identifier
     * @return the attribute identifier
     */
    public int getAttributeIdentifier() {
        return attributeIdentifier;
    }

    /**
     * Sets attribute identifier
     * @param attributeIdentifier the attribute identifier
     */
    public void setAttributeIdentifier(int attributeIdentifier) {
        this.attributeIdentifier = attributeIdentifier;
    }

    /**
     * Gets attribute value.
     * @return the attribute value
     */
    public Object getAttributeValue() {
        return attributeValue;
    }

    /**
     * Sets attribute value.
     * @param attributeValue the attribute value
     */
    public void setAttributeValue(Object attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public void serialize(final ZBSerializer serializer) {
        serializer.appendShort((short) attributeIdentifier);
        serializer.appendByte((byte) attributeDataType);
        final ZigBeeType type = ZigBeeType.getType((byte) attributeDataType);
        serializer.appendZigBeeType(attributeValue, type);
    }

    @Override
    public void deserialize(final ZBDeserializer deserializer) {
        attributeIdentifier = deserializer.read_short() & (0xFFFF);
        attributeDataType = deserializer.read_byte() & (0xFF);
        final ZigBeeType type = ZigBeeType.getType((byte) attributeDataType);
        attributeValue = deserializer.readZigBeeType(type);
    }

    @Override
    public String toString() {
        return "Write Attribute Record " +
                "attributeDataType=" + attributeDataType +
                ", attributeIdentifier=" + attributeIdentifier +
                ", attributeValue=" + attributeValue;
    }
}
