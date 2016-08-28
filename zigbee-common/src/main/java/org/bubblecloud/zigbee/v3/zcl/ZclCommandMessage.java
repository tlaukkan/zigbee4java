/**
 * Copyright 2016 Tommi S.E. Laukkanen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bubblecloud.zigbee.v3.zcl;

import org.bubblecloud.zigbee.v3.ZigBeeAddress;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclClusterType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandTypeRegistrar;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;

import java.util.Map;
import java.util.TreeMap;

/**
 * Value object holding a command message.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclCommandMessage {
    /**
     * The source address.
     */
    private ZigBeeAddress sourceAddress;
    /**
     * The source endpoint.
     */
//    private int sourceEnpoint;
    /**
     * The destination address.
     */
    private ZigBeeAddress destinationAddress;
    /**
     * The destination endpoint.
     */
//    private int destinationEndpoint;
    /**
     * The destination group ID which can be used instead of destination address and endpoint.
     */
    private Integer destinationGroupId;
    /**
     * The type.
     */
    private ZclCommandType type;
    /**
     * The cluster ID for generic messages.
     */
    private Integer clusterId;
    /**
     * The transaction ID.
     */
    private Byte transactionId;
    /**
     * The fields and their values.
     */
    Map<ZclFieldType, Object> fields = new TreeMap<ZclFieldType, Object>();
    /**
     * Default constructor for inbound messages.
     */
    public ZclCommandMessage() {
    }

    /**
     * Gets the type
     * @return the type
     */
    public ZclCommandType getType() {
        return type;
    }

    /**
     * Sets the command
     * @param type the command
     */
    public void setType(final ZclCommandType type) {
        this.type = type;
    }

    /**
     * Gets destination address.
     * @return the destination address.
     */
    public ZigBeeAddress getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * Sets destination address.
     * @param destinationAddress the destination address.
     */
    public void setDestinationAddress(final ZigBeeAddress destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * Gets destination endpoint.
     * @return the destination endpoint
     */
//    public int getDestinationEndpoint() {
  //      return destinationEndpoint;
    //}

    /**
     * Sets destination endpoint
     * @param destinationEndpoint the destination endpoint
     */
//    public void setDestinationEndpoint(final int destinationEndpoint) {
  //      this.destinationEndpoint = destinationEndpoint;
    //}

    /**
     * Gets destination group ID
     * @return the destination group ID
     */
    public Integer getDestinationGroupId() {
        return destinationGroupId;
    }

    /**
     * Sets destination group ID
     * @param destinationGroupId the destination group ID
     */
    public void setDestinationGroupId(final Integer destinationGroupId) {
        this.destinationGroupId = destinationGroupId;
    }

    /**
     * Gets the fields
     * @return the fields
     */
    public Map<ZclFieldType, Object> getFields() {
        return fields;
    }

    /**
     * Sets the fields.
     * @param fields the fields
     */
    public void setFields(final Map<ZclFieldType, Object> fields) {
        this.fields = fields;
    }

    /**
     * Gets source address.
     * @return the source address
     */
    public ZigBeeAddress getSourceAddress() {
        return sourceAddress;
    }

    /**
     * Sets source address.
     * @param sourceAddress the source address
     */
    public void setSourceAddress(final ZigBeeAddress sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * Gets source endpoint.
     * @return the source endpoint
     */
//    public int getSourceEnpoint() {
  //      return sourceEnpoint;
    //}

    /**
     * Sets source endpoint.
     * @param sourceEnpoint the source endpoint
     */
    public void setSourceEndpoint(final int sourceEnpoint) {
//        this.sourceEnpoint = sourceEnpoint;
    }

    /**
     * Gets the cluster ID for generic messages.
     * @return the cluster ID.
     */
    public Integer getClusterId() {
        return clusterId;
    }

    /**
     * Sets the cluster ID for generic messages.
     * @param clusterId the cluster ID
     */
    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    /**
     * Gets the transaction ID.
     * @return the transaction ID
     */
    public Byte getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the transaction ID.
     * @param transactionId the transaction ID
     */
    public void setTransactionId(final Byte transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        Integer resolvedClusterId = getClusterId();
        StringBuilder sb = new StringBuilder();
        if (resolvedClusterId == null && type != null) {
            resolvedClusterId = type.getClusterType().getId();
            
            sb.append(ZclClusterType.getValueById(resolvedClusterId).getLabel() + " - " + type + " ");
        }
        else {
        	sb.append("ZCL unknown ");
        }
        
        sb.append(sourceAddress + " -> "
                + destinationAddress  + " tid=" + transactionId + " " + fields);
        
        return sb.toString();
    }

    static {
        ZclCommandTypeRegistrar.register();
    }
}
