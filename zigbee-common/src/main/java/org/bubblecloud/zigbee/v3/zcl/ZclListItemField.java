package org.bubblecloud.zigbee.v3.zcl;

import org.bubblecloud.zigbee.v3.serialization.ZBDeserializer;
import org.bubblecloud.zigbee.v3.serialization.ZBSerializer;

/**
 * ZclField class for non primitive field types.
 *
 * @author Tommi S.E Laukkanen
 */
public interface ZclListItemField {
    /**
     * Serializes the field.
     * @param serializer the serializer
     */
    void serialize(ZBSerializer serializer);

    /**
     * Deserializes the field.
     * @param deserializer the deserializer.
     */
    void deserialize(ZBDeserializer deserializer);
}
