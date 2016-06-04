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
package org.bubblecloud.zigbee.network.zcl;

import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandTypeRegistrar;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;

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
    private int sourceAddress;
    /**
     * The source endpoint.
     */
    private int sourceEnpoint;
    /**
     * The destination address.
     */
    private int destinationAddress;
    /**
     * The destination endpoint.
     */
    private int destinationEndpoint;
    /**
     * The type.
     */
    private ZclCommandType type;
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
     * Constructor for outbound messages.
     * @param destinationAddress the destination address
     * @param destinationEndpoint the destination endpoint
     * @param type the type
     * @param transactionId the transaction ID
     */
    public ZclCommandMessage(final int destinationAddress, short destinationEndpoint, final ZclCommandType type,
                             final Byte transactionId) {
        this.destinationAddress = destinationAddress;
        this.destinationEndpoint = destinationEndpoint;
        this.transactionId = transactionId;
        this.type = type;
    }

    /**
     * Add field to message.
     * @param field the field
     * @param value the value
     */
    public void addField(final ZclFieldType field, final Object value) {
        fields.put(field, value);
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
    public int getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * Sets destination address.
     * @param destinationAddress the destination address.
     */
    public void setDestinationAddress(final int destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * Gets destination endpoint.
     * @return the destination endpoint
     */
    public int getDestinationEndpoint() {
        return destinationEndpoint;
    }

    /**
     * Sets destination endpoint
     * @param destinationEndpoint the destination endpoint
     */
    public void setDestinationEndpoint(final int destinationEndpoint) {
        this.destinationEndpoint = destinationEndpoint;
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
    public int getSourceAddress() {
        return sourceAddress;
    }

    /**
     * Sets source address.
     * @param sourceAddress the source address
     */
    public void setSourceAddress(final int sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * Gets source endpoint.
     * @return the source endpoint
     */
    public int getSourceEnpoint() {
        return sourceEnpoint;
    }

    /**
     * Sets source endpoint.
     * @param sourceEnpoint the source endpoint
     */
    public void setSourceEnpoint(final int sourceEnpoint) {
        this.sourceEnpoint = sourceEnpoint;
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
        return type + " " + sourceAddress + "." + sourceEnpoint + " -> "
                + destinationAddress + "." + destinationEndpoint + " " + fields;
    }

    static {
        ZclCommandTypeRegistrar.register();
    }
}
