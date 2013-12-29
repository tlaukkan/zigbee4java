/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 


   See the NOTICE file distributed with this work for additional 
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.bubblecloud.zigbee.api.cluster.impl.global.read;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.ReadAttributesStatus;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.AttributeDescriptor;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ReadAttributeStatusImpl implements ReadAttributesStatus {

    private byte status;

    private int attributeId;

    private byte dataType;

    @SuppressWarnings("unchecked")
    private Class clazz;

    private Object data;

    public ReadAttributeStatusImpl(
            AttributeDescriptor descriptor, ZBDeserializer deserializer) {

        attributeId = deserializer.read_short();
        status = deserializer.read_byte();
        if (Status.getStatus(status).equals(Status.SUCCESS)) {
            dataType = deserializer.read_byte();
            final ZigBeeType type = ZigBeeType.getType(dataType);
            clazz = type.getJavaClass();
            data = deserializer.readZigBeeType(type);
        }

        // TODO Attribute Check
        // indeed the order could be different, so we should receive all the list
        // and in any case we could also avoid to throw an Exception

    }

    public Object getAttributeData() {
        return data;
    }

    public byte getAttributeDataType() {
        return dataType;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public byte getStatus() {
        return status;
    }

    @SuppressWarnings("unchecked")
    public Class getDataClass() {
        return clazz;
    }
}
