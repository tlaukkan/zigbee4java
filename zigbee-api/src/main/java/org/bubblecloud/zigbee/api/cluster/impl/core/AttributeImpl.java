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
import org.bubblecloud.zigbee.api.cluster.impl.api.global.DefaultResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.ReadAttributesResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.ReadAttributesStatus;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.WriteAttributeRecord;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.WriteAttributesResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.WriteAttributesStatus;
import org.bubblecloud.zigbee.api.cluster.impl.ClusterMessageImpl;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.AttributeDescriptor;
import org.bubblecloud.zigbee.api.cluster.impl.global.DefaultResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.global.read.ReadAttributeCommand;
import org.bubblecloud.zigbee.api.cluster.impl.global.read.ReadAttributesResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.global.write.WriteAttributeCommand;
import org.bubblecloud.zigbee.api.cluster.impl.global.write.WriteAttributeRecordImpl;
import org.bubblecloud.zigbee.api.cluster.impl.global.write.WriteAttributesResponseImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class AttributeImpl implements Attribute {

    private static final Logger logger = LoggerFactory.getLogger(AttributeImpl.class);

    final private Object LazyInstantiation = new Object();
    private ZigBeeEndpoint zbDevice;
    private ZCLCluster zclCluster;
    private Reporter reporter;
    private AttributeDescriptor descriptor;

    public AttributeImpl(ZigBeeEndpoint zbDevice, ZCLCluster zclCluster, AttributeDescriptor descriptor) {
        this.zbDevice = zbDevice;
        this.zclCluster = zclCluster;
        this.descriptor = descriptor;
    }

    public int getId() {
        return descriptor.getId();
    }

    public String getName() {
        return descriptor.getName();
    }

    @SuppressWarnings("unchecked")
    public Class getType() {
        return descriptor.getType();
    }

    public ZigBeeType getZigBeeType() {
        return descriptor.getZigBeeType();
    }

    public boolean isReportable() {
        return descriptor.isReportable();
    }

    public boolean isWritable() {
        return descriptor.isWritable();
    }

    public void setValue(Object o) throws ZigBeeClusterException {
        if (isWritable() == false) {
            throw new ZigBeeClusterException(
                    "Trying to set the attribute " + getName() + "(" + getId() + ") that is Read Only"
            );
        }
        doClusterWideWrite(o);
    }

    public Object getValue() throws ZigBeeClusterException {
        return doClusterWideRead();
    }

    /**
     * Gets the {@link Reporter reporter} for this attribute.
     * <p>
     * If there is currently no reporter, then a new reporter is created.
     * @return the {@link Reporter}
     */
    public Reporter getReporter() {
        if (isReportable() == false) {
            return null;
        }

        synchronized (LazyInstantiation) {
            if (reporter == null) {
                if (getZigBeeType().isAnalog()) {
                    reporter = new AnalogReporterImpl(zbDevice, zclCluster, this);
                } else {
                    reporter = new ReporterImpl(zbDevice, zclCluster, this);
                }
            }
        }
        return reporter;
    }

    private Object doClusterWideRead() throws ZigBeeClusterException {

        logger.info("Reading " + getName() + " from " + zbDevice.getIeeeAddress());
        ReadAttributeCommand readAttrCom = new ReadAttributeCommand(new int[]{getId()});
        ZCLFrame frame = new ZCLFrame(readAttrCom, zclCluster.isDefaultResponseEnabled());
        ClusterMessageImpl input;
        input = new ClusterMessageImpl(zclCluster.getId(), frame);
        ClusterMessage clusterMessage = null;
        try {
            clusterMessage = zbDevice.invoke(input);
            Response response = new ResponseImpl(clusterMessage, zclCluster.getId());
            if (response.getZCLHeader().getTransactionId() != frame.getHeader().getTransactionId()) {
                logger.error(
                        "Received mismatching transaction response, " +
                                "we have to change heuristic for dispatching. Received {} while sent {}",
                        response.getZCLHeader().getTransactionId(), frame.getHeader().getTransactionId()
                );
                return null;
            }
            AttributeDescriptor[] requestedAttributes = new AttributeDescriptor[]{descriptor};

            switch (response.getZCLHeader().getCommandId()) {
                case ReadAttributesResponse.ID:
                    ReadAttributesResponse readResponse = new ReadAttributesResponseImpl(response, requestedAttributes);
                    ReadAttributesStatus attributeStatus = readResponse.getReadAttributeStatus()[0];
                    if (attributeStatus.getStatus() == Status.SUCCESS.id) {
                        return attributeStatus.getAttributeData();
                    } else {
                        Status state = Status.getStatus(attributeStatus.getStatus());
                        throw new ZigBeeClusterException(
                                "Read Attribute '" + getName() + "' (" + getId() + ") failed." +
                                        "Due to " + state + " that means " + state.description
                        );
                    }
                case DefaultResponse.ID:
                    //Means that the read command is not supported
                    final DefaultResponse result = new DefaultResponseImpl(response);
                    Status state = result.getStatus();
                    throw new ZigBeeClusterException(
                            "Read Attribute " + getId() + " failed because command is not supported."
                                    + "Due to " + state + " that means " + state.description
                                    + " Follows the ZCLFrame recieved " + ResponseImpl.toString(response)
                    );

                default:
                    throw new ZigBeeClusterException(
                            "Read Attribute " + getId() + " failed due to: Unsupported answer: " + response
                                    + " Follows the ZCLFrame recieved " + ResponseImpl.toString(response)
                    );
            }
        } catch (ZigBeeNetworkManagerException e) {
            throw new ZigBeeClusterException("Read Attribute " + getId() + " failed due to:  " + e);
        }
    }

    private void doClusterWideWrite(Object o) throws ZigBeeClusterException {
        WriteAttributeRecord writeAttrComRec = new WriteAttributeRecordImpl(this, o);
        WriteAttributeCommand writeAttrCom = new WriteAttributeCommand(new WriteAttributeRecord[]{writeAttrComRec});
        ZCLFrame frame = new ZCLFrame(writeAttrCom, zclCluster.isDefaultResponseEnabled());
        ClusterMessageImpl input = new ClusterMessageImpl(zclCluster.getId(), frame);
        try {
            ClusterMessage clusterMessage = zbDevice.invoke(input);
            Response response = new ResponseImpl(clusterMessage, zclCluster.getId());
            AttributeDescriptor[] requestedAttributes = new AttributeDescriptor[]{descriptor};
            WriteAttributesResponse writeResposne = new WriteAttributesResponseImpl(response, requestedAttributes);
            WriteAttributesStatus attributeStatus = writeResposne.getWriteAttributesStatus()[0];
            if (attributeStatus.getStatus() != Status.SUCCESS.id) {
                Status state = Status.getStatus(attributeStatus.getStatus());
                throw new ZigBeeClusterException(
                        "Unable to write value " + o.toString()
                                + ". It failed with error " + state + "(" + state.id + "):" + state.description
                                + ". Follows the ZCLFrame recieved " + ResponseImpl.toString(writeResposne)
                );
            }
        } catch (ZigBeeNetworkManagerException e) {
            throw new ZigBeeClusterException(e);
        }
        return;
    }

    public Object getDefaultValue() throws ZigBeeClusterException {
        // TODO Auto-generated method stub
        return null;
    }
}
