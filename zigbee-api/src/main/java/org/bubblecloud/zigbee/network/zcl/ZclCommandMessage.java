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

import java.util.TreeMap;

/**
 * Value object holding a command message.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclCommandMessage {

    private int sourceAddress;
    private short sourceEnpoint;
    private int destinationAddress;
    private short destinationEndpoint;

    ZclCommand command;

    private Byte transactionId;


    TreeMap<ZclCommandField, Object> fields = new TreeMap<ZclCommandField, Object>();

    public ZclCommandMessage() {
    }

    public ZclCommandMessage(final int destinationAddress, short destinationEndpoint, final ZclCommand command, final Byte transactionId) {
        this.destinationAddress = destinationAddress;
        this.destinationEndpoint = destinationEndpoint;
        this.transactionId = transactionId;
        this.command = command;
    }

    public void addField(final ZclCommandField field, final Object value) {
        fields.put(field, value);
    }

    public ZclCommand getCommand() {
        return command;
    }

    public void setCommand(ZclCommand command) {
        this.command = command;
    }

    public int getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(int destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public short getDestinationEndpoint() {
        return destinationEndpoint;
    }

    public void setDestinationEndpoint(short destinationEndpoint) {
        this.destinationEndpoint = destinationEndpoint;
    }

    public TreeMap<ZclCommandField, Object> getFields() {
        return fields;
    }

    public void setFields(TreeMap<ZclCommandField, Object> fields) {
        this.fields = fields;
    }

    public int getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(int sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public short getSourceEnpoint() {
        return sourceEnpoint;
    }

    public void setSourceEnpoint(short sourceEnpoint) {
        this.sourceEnpoint = sourceEnpoint;
    }

    public Byte getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Byte transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return command.name() + " " + sourceAddress + "." + sourceEnpoint + " -> " + destinationAddress + "." + destinationEndpoint + " " + fields;
    }
}
