/**
 * Copyright 2016 Tommi S.E. Laukkanen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bubblecloud.zigbee.network.zcl;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.core.ByteArrayOutputStreamSerializer;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclDataType;

/**
 * ZCL field serializer.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclFieldSerializer {

    /**
     * Delegate serializer.
     */
    private ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();

    /**
     * Serializes field value.
     * @param value the field value
     * @param dataType the data type
     */
    public void serialize(final Object value, final ZclDataType dataType) {
        final ZigBeeType zigBeeType = ZclUtil.mapDataType(dataType);
        serializer.appendZigBeeType(value, zigBeeType);
    }

    /**
     * Gets payload.
     * @return the payload
     */
    public byte[] getPayload() {
        return serializer.getPayload();
    }
}
