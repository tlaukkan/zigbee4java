package org.bubblecloud.zigbee.network.zcl.type;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

/**
 * AttributeInformation information data type;
 */
public class ReadAttributeStatusRecord implements ZclSerializable {
    /**
     * The attribute identifier.
     */
    private int attributeIdentifier;
    /**
     * The status.
     */
    private int status;
    /**
     * The attribute data type.
     */
    private int attributeDataType;
    /**
     * The attribute data type.
     */
    private Object attributeValue;

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

    public Object getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(Object attributeValue) {
        this.attributeValue = attributeValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void serialize(final ZBSerializer serializer) {
        serializer.appendShort((short) attributeIdentifier);
        serializer.appendByte((byte) status);
        serializer.appendByte((byte) attributeDataType);
        final ZigBeeType type = ZigBeeType.getType((byte) attributeDataType);
        serializer.appendZigBeeType(attributeValue, type);
    }

    public void deserialize(final ZBDeserializer deserializer) {
        attributeIdentifier = deserializer.read_short() & (0xFFFF);
        status = deserializer.read_byte() & (0xFF);
        if (Status.getStatus((byte) status).equals(Status.SUCCESS)) {
            attributeDataType = deserializer.read_byte() & (0xFF);
            final ZigBeeType type = ZigBeeType.getType((byte) attributeDataType);
            attributeValue = deserializer.readZigBeeType(type);
        }
    }

    @Override
    public String toString() {
        return "Read Attribute Status Record " +
                "attributeDataType=" + attributeDataType +
                ", attributeIdentifier=" + attributeIdentifier +
                ", status=" + status +
                ", attributeValue=" + attributeValue;
    }
}
