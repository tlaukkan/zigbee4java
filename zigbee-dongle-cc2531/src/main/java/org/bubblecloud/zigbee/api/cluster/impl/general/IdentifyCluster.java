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

package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.Identify;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.EmptyPayloadCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;
import org.bubblecloud.zigbee.api.cluster.impl.general.identify.IdentifyCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.identify.IdentifyQueryResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.global.DefaultResponseImpl;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class IdentifyCluster extends ZCLClusterBase implements Identify {

    private final AttributeImpl identifyTime;

    private final Attribute[] attributes;

    private static EmptyPayloadCommand CMD_IDENTIFY_QUERY = new EmptyPayloadCommand()
            .setId(Identify.IDENTIFY_QUERY_ID)
            .setClientServerDirection(true)
            .setClusterSpecific(true)
            .setManufacturerExtension(false);

    public IdentifyCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        identifyTime = new AttributeImpl(zbDevice, this, Attributes.IDENTIFY_TIME);
        attributes = new AttributeImpl[]{identifyTime};
    }

    @Override
    public short getId() {
        return Identify.ID;
    }

    @Override
    public String getName() {
        return Identify.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }

    public Attribute getAttributeIdentifyTime() {
        return identifyTime;
    }

    public Response identify(int time) throws ZigBeeClusterException {
        enableDefaultResponse();
        IdentifyCommand identifyCmd = new IdentifyCommand(time);
        Response response = invoke(identifyCmd);
        return new DefaultResponseImpl(response);
    }

    public Response identifyQuery() throws ZigBeeClusterException {
        Response response = invoke(CMD_IDENTIFY_QUERY);
        return new IdentifyQueryResponseImpl(response);
    }

}
