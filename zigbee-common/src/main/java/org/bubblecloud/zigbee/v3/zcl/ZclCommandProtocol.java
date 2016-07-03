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

import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;

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
    private Map<ZclCommandType, ZclCommandFormat> commandFormats = new HashMap<ZclCommandType, ZclCommandFormat>();

    /**
     * Deserializes fields from payload.
     * @param payload the payload
     * @param commandMessage the command message
     * @return the command message
     */
    public static ZclCommandMessage deserializePayload(final byte[] payload, final ZclCommandMessage commandMessage) {
        final ZclCommandFormat messageFormat = getMessageProtocol().get(commandMessage.getType());
        final ZclFieldDeserializer deserializer = new ZclFieldDeserializer(payload, 0);
        final TreeMap<ZclFieldType, Object> fields = new TreeMap<ZclFieldType, Object>();
        if (messageFormat != null) {
            for (final ZclFieldType fieldType : messageFormat.getFields()) {
                fields.put(fieldType, deserializer.deserialize(fieldType.getDataType()));
            }
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
        final ZclCommandFormat messageFormat = getMessageProtocol().get(commandMessage.getType());
        final ZclFieldSerializer serializer = new ZclFieldSerializer();
        if (messageFormat != null) {
            for (final ZclFieldType fieldType : messageFormat.getFields()) {
                serializer.serialize(commandMessage.getFields().get(fieldType), fieldType.getDataType());
            }
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
    public ZclCommandFormat get(final ZclCommandType command) {
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
            for (final ZclFieldType fieldType : ZclFieldType.values()) {
                if (messageFormat == null) {
                    messageFormat = new ZclCommandFormat(fieldType.getCommandType());
                } else if (!(messageFormat.getCommand() == fieldType.getCommandType())) {
                    protocol.add(messageFormat);
                    messageFormat = new ZclCommandFormat(fieldType.getCommandType());
                }
                messageFormat.getFields().add(fieldType);
            }
            protocol.add(messageFormat);
        }
        return protocol;
    }
}