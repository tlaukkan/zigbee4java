package org.bubblecloud.zigbee.network.zcl.type;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;

/**
 * Created by tlaukkan on 6/5/2016.
 */
public interface ZclSerializable {
    void serialize(ZBSerializer serializer);

    void deserialize(ZBDeserializer deserializer);
}
