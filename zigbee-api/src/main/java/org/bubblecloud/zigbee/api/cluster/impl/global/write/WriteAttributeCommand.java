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

package org.bubblecloud.zigbee.api.cluster.impl.global.write;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.WriteAttributeRecord;
import org.bubblecloud.zigbee.api.cluster.impl.core.AbstractCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.ByteArrayOutputStreamSerializer;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class WriteAttributeCommand extends AbstractCommand {
    private static byte ID = 0x02;

    private WriteAttributeRecord[] attributeRecord;

    public WriteAttributeCommand(WriteAttributeRecord[] attributerecord) {
        super(ID, false);
        this.attributeRecord = attributerecord;
    }

    public byte[] getPayload() {
        if (payload == null) {
            ZBSerializer serializer = new ByteArrayOutputStreamSerializer();

            for (int i = 0; i < attributeRecord.length; i++) {
                serializer.append_short((short) attributeRecord[i].getAttributeId());
                final ZigBeeType type = attributeRecord[i].getAttributeDataType();
                serializer.append_byte((byte) type.getId());
                serializer.appendZigBeeType(attributeRecord[i].getAttributeData(), type);
            }
            payload = serializer.getPayload();
        }
        return payload;
    }
}
