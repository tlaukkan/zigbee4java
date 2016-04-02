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

import org.bubblecloud.zigbee.api.cluster.impl.core.ByteArrayOutputStreamSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * ZCL command protocol implementation.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclCommandProtocol {
    /**
     * The singleton.
     */
    private static ZclCommandProtocol protocol;

    /**
     * The command formats.
     */
    private Map<ZclCommand, ZclCommandFormat> commandFormats = new HashMap<ZclCommand, ZclCommandFormat>();

    /**
     * Deserializes fields from payload.
     * @param payload the payload
     * @param commandMessage the command message
     * @return the command message
     */
    public static ZclCommandMessage deserializePayload(final byte[] payload, final ZclCommandMessage commandMessage) {
        final ZclCommandFormat messageFormat = getMessageProtocol().get(commandMessage.getCommand());
        final DefaultDeserializer defaultDeserializer = new DefaultDeserializer(payload, 0);
        final TreeMap<ZclCommandField, Object> fields = new TreeMap<ZclCommandField, Object>();
        for (final ZclCommandField fieldType : messageFormat.getFields()) {
            fields.put(fieldType, defaultDeserializer.readZigBeeType(fieldType.type));
        }
        commandMessage.setFields(fields);
        return commandMessage;
    }

    /**
     * Serializes fields to payload.
     * @param commandMessage the command message
     * @return the payload
     */
    public static byte[] serializePayload(final ZclCommandMessage commandMessage) {
        final ZclCommandFormat messageFormat = getMessageProtocol().get(commandMessage.getCommand());
        final ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
        for (final ZclCommandField fieldType : messageFormat.getFields()) {
            serializer.appendZigBeeType(commandMessage.getFields().get(fieldType), fieldType.type);
        }
        return serializer.getPayload();
    }

    /**
     * Adds message format for command.
     * @param messageFormat the message format
     */
    public void add(final ZclCommandFormat messageFormat) {
        commandFormats.put(messageFormat.getCommand(), messageFormat);
    }

    /**
     * Gets message format for command
     * @param command the command
     * @return the message format
     */
    public ZclCommandFormat get(final ZclCommand command) {
        return commandFormats.get(command);
    }

    /**
     * Private singleton access method.
     * @return
     */
    private static synchronized ZclCommandProtocol getMessageProtocol() {
        if (protocol == null) {
            protocol = new ZclCommandProtocol();

            ZclCommandFormat messageFormat = null;
            for (final ZclCommandField fieldType : ZclCommandField.values()) {
                if (messageFormat == null) {
                    messageFormat = new ZclCommandFormat(fieldType.command);
                } else if (!(messageFormat.getCommand() == fieldType.command)) {
                    protocol.add(messageFormat);
                    messageFormat = new ZclCommandFormat(fieldType.command);
                }
                messageFormat.getFields().add(fieldType);
            }
            protocol.add(messageFormat);
        }
        return protocol;
    }
}