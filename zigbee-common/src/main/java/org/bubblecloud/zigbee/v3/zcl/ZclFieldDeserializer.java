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
package org.bubblecloud.zigbee.v3.zcl;

import org.bubblecloud.zigbee.v3.serialization.DefaultDeserializer;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * ZCL field deserializer.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclFieldDeserializer {

    /**
     * Delegate deserializer.
     */
    private DefaultDeserializer defaultDeserializer;

    /**
     * Constructor for setting the payload and start index.
     * @param payload the payload
     * @param startIndex the start index
     */
    public ZclFieldDeserializer(final byte[] payload, final int startIndex) {
        this.defaultDeserializer = new DefaultDeserializer(payload, startIndex);
    }

    /**
     * Deserializes a field.
     * @param dataType the data type of the field.
     * @return the value
     */
    public Object deserialize(final ZclDataType dataType) {
        if (ZclListItemField.class.isAssignableFrom(dataType.getDataClass())) {
            final Class dataTypeClass = dataType.getDataClass();
            final List<ZclListItemField> list = new ArrayList<ZclListItemField>();
            while (defaultDeserializer.getSize() - defaultDeserializer.getPosition() > 0 ) {
                final ZclListItemField item;
                try {
                    item = (ZclListItemField) dataTypeClass.newInstance();
                } catch (final Exception e) {
                    throw new IllegalArgumentException("Error deserializing field: " + dataType.getLabel(), e);
                }
                item.deserialize(this.defaultDeserializer);
                list.add(item);
            }
            return list;
        }

        return defaultDeserializer.readZigBeeType(ZclUtil.mapDataType(dataType));
    }

}
