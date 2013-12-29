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

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.AttributeReport;
import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.core.ResponseImpl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.2.0
 */
public class ReportAttributesCommand extends ResponseImpl {

    private static final Logger logger = LoggerFactory.getLogger(ReportAttributesCommand.class);

    public static final byte ID = 0x0A;

    private AttributeReport[] attributesReport;

    public ReportAttributesCommand(ResponseImpl response) throws ZigBeeClusterException {
        super(response);
        ResponseImpl.checkGeneralCommandFrame(response, ReportAttributesCommand.ID);

        byte[] msg = getPayload();
        ZBDeserializer deserializer = new DefaultDeserializer(msg, 0);
        ArrayList<AttributeReportImpl> attributes = new ArrayList<AttributeReportImpl>();
        for (int i = 0; deserializer.getPosition() < msg.length; i++) {
            attributes.add(new AttributeReportImpl(deserializer));
        }

        attributesReport = attributes.toArray(new AttributeReport[]{});
    }

    public AttributeReport[] getAttributeReports() {
        return attributesReport;
    }
}
