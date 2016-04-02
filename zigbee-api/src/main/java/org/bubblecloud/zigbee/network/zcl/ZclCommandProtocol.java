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
    private Map<Integer, Map<Integer, Map<Integer, ZclCommandFormat>>> commandFormats = new HashMap<Integer,Map<Integer, Map<Integer, ZclCommandFormat>>>();

    public static ZclCommandMessage deserializePayload(byte[] commandPayload, ZclCommandMessage commandMessage) {
        final ZclCommandFormat messageFormat = getMessageProtocol().get(commandMessage.getCommand().profileId, commandMessage.getCommand().clusterId, commandMessage.getCommand().commandId);
        final DefaultDeserializer defaultDeserializer = new DefaultDeserializer(commandPayload, 0);
        final TreeMap<ZclCommandField, Object> fields = new TreeMap<ZclCommandField, Object>();
        for (final ZclCommandField fieldType : messageFormat.getFields()) {
            fields.put(fieldType, defaultDeserializer.readZigBeeType(fieldType.type));
        }
        commandMessage.setFields(fields);
        return commandMessage;
    }

    public static byte[] serializePayload(final ZclCommand command, TreeMap<ZclCommandField, Object> parameters) {
        final ZclCommandFormat messageFormat = getMessageProtocol().get(command.profileId, command.clusterId, command.commandId);
        if (messageFormat == null) {
            throw new IllegalArgumentException("No serialisation defined for: " + command);
        }
        final ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
        for (final ZclCommandField fieldType : messageFormat.getFields()) {
            serializer.appendZigBeeType(parameters.get(fieldType), fieldType.type);
        }
        return serializer.getPayload();
    }

    public void add(final ZclCommandFormat messageFormat) {
        if (!commandFormats.containsKey(messageFormat.getProfileId())) {
            commandFormats.put(messageFormat.getProfileId(), new HashMap<Integer, Map<Integer, ZclCommandFormat>>());
        }
        if (!commandFormats.get(messageFormat.getProfileId()).containsKey(messageFormat.getClusterId())) {
            commandFormats.get(messageFormat.getProfileId()).put(messageFormat.getClusterId(), new HashMap<Integer, ZclCommandFormat>());
        }
        commandFormats.get(messageFormat.getProfileId()).get(messageFormat.getClusterId()).put(messageFormat.getCommandId(), messageFormat);
    }

    public ZclCommandFormat get(final int profileId, final int clusterId, final int commandId) {
        if (!commandFormats.containsKey(profileId)) {
            commandFormats.put(profileId, new HashMap<Integer, Map<Integer, ZclCommandFormat>>());
        }
        if (!commandFormats.get(profileId).containsKey(clusterId)) {
            commandFormats.get(profileId).put(clusterId, new HashMap<Integer, ZclCommandFormat>());
        }
        return commandFormats.get(profileId).get(clusterId).get(commandId);
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
                    messageFormat = new ZclCommandFormat(fieldType.profileId, fieldType.clusterId, fieldType.commandId);
                } else if (!(messageFormat.getProfileId() == fieldType.profileId && messageFormat.getClusterId() == fieldType.clusterId && messageFormat.getCommandId() == fieldType.commandId)) {
                    protocol.add(messageFormat);
                    messageFormat = new ZclCommandFormat(fieldType.profileId, fieldType.clusterId, fieldType.commandId);
                }
                messageFormat.getFields().add(fieldType);
            }
            protocol.add(messageFormat);
        }
        return protocol;
    }
}