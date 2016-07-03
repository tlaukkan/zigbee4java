package org.bubblecloud.zigbee.v3.zcl.field;

import org.bubblecloud.zigbee.v3.serialization.ZBDeserializer;
import org.bubblecloud.zigbee.v3.serialization.ZBSerializer;
import org.bubblecloud.zigbee.v3.zcl.ZclListItemField;

import java.util.Arrays;

/**
 * Attribute Identifier field.
 */
public class ExtensionFieldSet implements ZclListItemField {
    /**
     * The cluster id.
     */
    private int clusterId;

    /**
     * The data length.
     */
    private int length;

    /**
     * The data.
     */
    private byte[] data;

    /**
     * Gets cluster ID
     * @return the cluster ID
     */
    public int getClusterId() {
        return clusterId;
    }

    /**
     * Sets cluster ID
     * @param clusterId the cluster ID
     */
    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    /**
     * Gets data.
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets data.
     * @param data the data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Gets data length.
     * @return the data length
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets data length.
     * @param length the data length
     */
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void serialize(final ZBSerializer serializer) {
        serializer.appendShort((short) clusterId);
        serializer.appendByte((byte) length);
        for (int i = 0; i < length; i++) {
            serializer.appendByte(data[i]);
        }
    }

    @Override
    public void deserialize(final ZBDeserializer deserializer) {
        clusterId = deserializer.read_short() & (0xFFFF);
        length = deserializer.read_byte() & (0xFF);
        data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i]=deserializer.read_byte();
        }
    }

    @Override
    public String toString() {
        return "Extension Field Set " +
                "clusterId=" + clusterId +
                ", length=" + length +
                ", data=" + Arrays.toString(data);
    }
}
