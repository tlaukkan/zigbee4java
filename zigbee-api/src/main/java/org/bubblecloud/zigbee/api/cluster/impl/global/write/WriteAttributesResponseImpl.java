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

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.WriteAttributesResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.WriteAttributesStatus;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.AttributeDescriptor;
import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.core.ResponseImpl;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class WriteAttributesResponseImpl extends ResponseImpl implements
        WriteAttributesResponse {

    private WriteAttributesStatus[] attributes;

    public WriteAttributesResponseImpl(Response response, AttributeDescriptor[] descriptors) throws ZigBeeClusterException {
        super(response);
        ResponseImpl.checkGeneralCommandFrame(response, WriteAttributesResponse.ID);
        attributes = new WriteAttributesStatus[descriptors.length];
        if (getPayload().length == 1 && getPayload()[0] == Status.SUCCESS.id) {
            for (int i = 0; i < descriptors.length; i++) {
                attributes[i] = new WriteAttributeStatusImpl(descriptors[i]);
            }
        } else {
            ZBDeserializer deserializer = new DefaultDeserializer(getPayload(), 0);
            for (int i = 0; i < descriptors.length; i++) {
                attributes[i] = new WriteAttributeStatusImpl(descriptors[i], deserializer);
            }
        }
    }

    public WriteAttributesStatus[] getWriteAttributesStatus() {
        return attributes;
    }

}
