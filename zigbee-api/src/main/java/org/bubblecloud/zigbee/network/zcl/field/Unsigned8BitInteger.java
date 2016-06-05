package org.bubblecloud.zigbee.network.zcl.field;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.network.zcl.ZclListItemField;

/**
 * Unsigned 8 Bit Integer field.
 */
public class Unsigned8BitInteger implements ZclListItemField {
    /**
     * The attribute identifier.
     */
    private int value;

    /**
     * Gets value.
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets value.
     * @param value the value
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void serialize(final ZBSerializer serializer) {
        serializer.append_byte((byte) value);
    }

    @Override
    public void deserialize(final ZBDeserializer deserializer) {
        value = deserializer.read_uint8bit();
    }

    @Override
    public String toString() {
        return "Unsigned 8 Bit Integer " +
                ", value=" + value;
    }
}
