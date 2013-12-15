/*

   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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
package org.bubblecloud.zigbee.proxy.cluster.impl.global.write;

import static org.junit.Assert.assertArrayEquals;
import org.bubblecloud.zigbee.proxy.cluster.api.global.WriteAttributeRecord;
import org.bubblecloud.zigbee.proxy.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.proxy.cluster.impl.core.AttributeImpl;

import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.1
 *
 */
public class WriteAttributeCommandTest {

    @Test
    public void testGetPayloadWithString() {
        AttributeImpl attribute = new AttributeImpl(null, null, Attributes.LOCATION_DESCRIPTION);
        WriteAttributeRecord[] values = new WriteAttributeRecord[]{
                new WriteAttributeRecordImpl( attribute, "garage" )
        };
        WriteAttributeCommand command = new WriteAttributeCommand(values);
        assertArrayEquals(new byte[]{
                0x10, 0x00, 0x42, 0x06, 0x67, 0x61, 0x72, 0x61, 0x67, 0x65
        }, command.getPayload()
        );
    }

    @Test
    public void testGetPayloadWithInteger() {
        AttributeImpl attribute = new AttributeImpl(null, null, Attributes.IDENTIFY_TIME);
        WriteAttributeRecord[] values = new WriteAttributeRecord[]{
                new WriteAttributeRecordImpl( attribute, new Integer(10) )
        };
        WriteAttributeCommand command = new WriteAttributeCommand(values);
        assertArrayEquals(new byte[]{
                0x00, 0x00, 0x21, 0x0a, 0x00
        }, command.getPayload()
        );
    }

}
