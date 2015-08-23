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
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.AnalogReporter;
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
 * @since 0.6.0
 */
public class AnalogReporterImpl extends ReporterBase implements AnalogReporter {

    private final Logger log = LoggerFactory.getLogger(AnalogReporterImpl.class);

    private Object minimumChange = null;

    public AnalogReporterImpl(final ZigBeeEndpoint zb, final ZCLCluster c, final Attribute attrib) {
        super(zb, c, attrib);
        final ZigBeeType type = attrib.getZigBeeType();
        if (type.isAnalog() == false) {
            throw new IllegalArgumentException(
                    "AnalogReporter applies only to Attribute with analog data type, " +
                            "the attribute " + attrib.getName() + " (" + attrib.getId() + ") of type " + type.toString() +
                            " is DISCRETE"
            );
        }
        setReportableChangeValue(new Double(AnalogReporter.DEFAULT_REPORTABLE_CHANGE_INTERVAL));
    }

    protected boolean doConfigureServer() throws ZigBeeClusterException {

        log.debug(
                "Subscribing to analog attribute {} ({}) with the following parameter min = {}, max = {}, change = {}",
                			attribute.getName(), attribute.getId(), min, max, minimumChange
        );

        AttributeReportingConfigurationRecordImpl config = new AttributeReportingConfigurationRecordImpl(
                attribute, 0x00, max, min, minimumChange, max
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
            final AttributeStatusRecord[] results = response.getAttributeStatusRecord();
            if (results[0].getStatus() != 0) {
            	Status status = Status.getStatus(results[0].getStatus());
                throw new ZigBeeClusterException("ConfigureReporting answered with a Failed status: " + (status==null?results[0].getStatus():status.toString()));
            }
        } catch (ZigBeeNetworkManagerException e) {
            throw new ZigBeeClusterException(e);
        }

        return true;
    }

    public Object getReportableChange() {
        return minimumChange;
    }

    private void setReportableChangeValue(Number n) {
        final ZigBeeType type = attribute.getZigBeeType();
        if (type.getJavaClass() == Long.class) {
            minimumChange = new Long(n.longValue());
        } else if (type.getJavaClass() == Integer.class) {
            minimumChange = new Integer(n.intValue());
        } else if (type.getJavaClass() == Float.class) {
            minimumChange = new Float(n.floatValue());
        } else if (type.getJavaClass() == Double.class) {
            minimumChange = new Double(n.doubleValue());
        } else {
            throw new IllegalArgumentException(
                    "Java class used for the interpretation of the " +
                            "the attribute " + attribute.getName() + " (" + attribute.getId() + ") of type " + type.toString() +
                            " is not recognized "
            );
        }
    }

    public void setReportableChange(Object value) {
        setReportableChangeValue((Number) value);
    }

}
