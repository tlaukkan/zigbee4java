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

package org.bubblecloud.zigbee.api.cluster.impl.core;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.*;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.AttributeReportingConfigurationRecord;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.AttributeStatusRecord;
import org.bubblecloud.zigbee.api.cluster.impl.ClusterMessageImpl;
import org.bubblecloud.zigbee.api.cluster.impl.global.reporting.AttributeReportingConfigurationRecordImpl;
import org.bubblecloud.zigbee.api.cluster.impl.global.reporting.ConfigureReportingCommand;
import org.bubblecloud.zigbee.api.cluster.impl.global.reporting.ConfigureReportingResponseImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ReporterImpl extends ReporterBase implements Reporter {

    private final Logger log = LoggerFactory.getLogger(ReporterImpl.class);

    public ReporterImpl(final ZigBeeEndpoint zb, final ZCLCluster c, final Attribute attrib) {
        super(zb, c, attrib);
    }

    protected boolean doConfigureServer() throws ZigBeeClusterException {
        log.info(
                "Subscribing to discrete attribute {} ( {} ) with the following parameter min = {}, max = {} ",
                new Object[]{attribute.getName(), attribute.getId(), min, max}
        );

        AttributeReportingConfigurationRecordImpl config = new AttributeReportingConfigurationRecordImpl(
                attribute, 0x00, max, min, null, max
        );
        ConfigureReportingCommand cmd = new ConfigureReportingCommand(
                new AttributeReportingConfigurationRecord[]{config}
        );

        final ZCLFrame frame = new ZCLFrame(cmd, true);
        final ClusterMessageImpl input = new ClusterMessageImpl(cluster.getId(), frame);
        ClusterMessage clusterMessage = null;
        try {
            clusterMessage = device.invoke(input);
            final ConfigureReportingResponseImpl response = new ConfigureReportingResponseImpl(
                    new ResponseImpl(clusterMessage, clusterMessage.getId()), new Attribute[]{attribute}
            );
            final AttributeStatusRecord results = response.getAttributeStatusRecord()[0];
            if (results.getStatus() != 0) {
                throw new ZigBeeClusterException("ConfigureReporting answered with a Failed status: " + Status.getStatus(results.getStatus()));
            }
        } catch (ZigBeeNetworkManagerException e) {
            throw new ZigBeeClusterException(e);
        }

        return true;
    }

}
