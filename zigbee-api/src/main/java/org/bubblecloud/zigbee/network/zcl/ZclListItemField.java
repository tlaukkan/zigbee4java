package org.bubblecloud.zigbee.network.zcl;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;

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
