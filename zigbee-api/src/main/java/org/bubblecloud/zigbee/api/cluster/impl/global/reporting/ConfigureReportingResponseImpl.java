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

package org.bubblecloud.zigbee.api.cluster.impl.global.reporting;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.AttributeStatusRecord;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.ConfigureReportingResponse;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.AttributeDescriptor;
import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.core.ResponseImpl;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ConfigureReportingResponseImpl extends ResponseImpl implements ConfigureReportingResponse {

    private AttributeStatusRecord[] attributes;

    public ConfigureReportingResponseImpl(Response response, AttributeDescriptor[] descriptors) throws ZigBeeClusterException {

        super(response);
        ResponseImpl.checkGeneralCommandFrame(response, ConfigureReportingResponse.ID);
        attributes = new AttributeStatusRecord[descriptors.length];
        ZBDeserializer deserializer = new DefaultDeserializer(getPayload(), 0);
        for (int i = 0; i < descriptors.length; i++) {
            attributes[i] = new AttributeStatusRecordImpl(descriptors[i], deserializer);
        }
    }

    public ConfigureReportingResponseImpl(Response response, Attribute[] attribs) throws ZigBeeClusterException {

        super(response);
        ResponseImpl.checkGeneralCommandFrame(response, ConfigureReportingResponse.ID);
        attributes = new AttributeStatusRecord[attribs.length];
        final byte[] msg = getPayload();
        if (msg.length == 1) { //ALL SUCCESS so attribute status field record are omitted
            if (msg[0] != 0) {
                //TODO Throw exception
            }
            for (int i = 0; i < attribs.length; i++) {
                //TODO Check for direction
                attributes[i] = new AttributeStatusRecordImpl((byte) 0, 0, attribs[i].getId());
            }
        } else { //SOME FAILED so we have parse the payload normally
            ZBDeserializer deserializer = new DefaultDeserializer(getPayload(), 0);
            for (int i = 0; i < attribs.length; i++) {
                attributes[i] = new AttributeStatusRecordImpl(attribs[i], deserializer);
            }
        }
    }

    public AttributeStatusRecord[] getAttributeStatusRecord() {
        return attributes;
    }
}
