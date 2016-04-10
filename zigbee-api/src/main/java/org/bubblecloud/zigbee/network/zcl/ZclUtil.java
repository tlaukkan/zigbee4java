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
import org.bubblecloud.zigbee.network.impl.ZigBeeEndpointImpl;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common utility methods for ZigBee Cluster Library implementation.
 */
public class ZclUtil {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZigBeeEndpointImpl.class);

    /**
     * Maps ZclDataType to delegate serializer data type.
     * @param dataType the data type
     * @return the mapped data type
     */
    public static ZigBeeType mapDataType(final ZclDataType dataType) {
        ZigBeeType zigBeeType = null;
        switch(dataType) {
            case CHARACTER_STRING:
                zigBeeType = ZigBeeType.CharacterString;
                break;
            case CLUSTER_ID:
                zigBeeType = null;
                break;
            case IEEE_ADDRESS:
                zigBeeType = ZigBeeType.IEEEAddress;
                break;
            case N_X_EXTENSION_FIELD_SET:
                zigBeeType = null;
                break;
            case N_X_NEIGHBORS_INFORMATION:
                zigBeeType = null;
                break;
            case N_X_UNSIGNED_16_BIT_INTEGE:
                zigBeeType = null;
                break;
            case N_X_UNSIGNED_8_BIT_INTEGER:
                zigBeeType = null;
                break;
            case SIGNED_16_BIT_INTEGER:
                zigBeeType = ZigBeeType.SignedInteger16bit;
                break;
            case SIGNED_8_BIT_INTEGER:
                zigBeeType = ZigBeeType.SignedInteger8bit;
                break;
            case UNSIGNED_16_BIT_INTEGER:
                zigBeeType = ZigBeeType.UnsignedInteger8bit;
                break;
            case UNSIGNED_32_BIT_INTEGER:
                zigBeeType = ZigBeeType.UnsignedInteger32bit;
                break;
            case UNSIGNED_8_BIT_INTEGER:
                zigBeeType = ZigBeeType.UnsignedInteger8bit;
                break;
            case _16_BIT_ENUMERATION:
                zigBeeType = ZigBeeType.Enumeration16bit;
                break;
            case _8_BIT_BITMAP:
                zigBeeType = ZigBeeType.Bitmap8bit;
                break;
            case _8_BIT_DATA:
                zigBeeType = ZigBeeType.Data8bit;
                break;
            case _8_BIT_ENUMERATION:
                zigBeeType = ZigBeeType.Enumeration8bit;
                break;
            default:
                break;
        }

        if (zigBeeType == null) {
            LOGGER.warn("Data type not implemented or mapped: " + dataType);
        }

        return zigBeeType;
    }
}
