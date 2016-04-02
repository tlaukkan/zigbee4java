package org.bubblecloud.zigbee.network.impl.protocol;

import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ZclCommandProtocol {
    private static ZclCommandProtocol protocol;

    public static synchronized ZclCommandProtocol getMessageProtocol() {
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

    public static ZclCommandMessage deserializePayloadToFields(int profileId, int cluster, byte commandId, byte[] commandPayload, ZclCommandMessage commandMessage) {
        TreeMap<ZclCommandField, Object> fields = null;
        final ZclCommandFormat messageFormat = getMessageProtocol().get(profileId, cluster, commandId);
        if (messageFormat != null) {
            final DefaultDeserializer defaultDeserializer = new DefaultDeserializer(commandPayload, 0);
            fields = new TreeMap<ZclCommandField, Object>();
            for (final ZclCommandField fieldType : messageFormat.getFields()) {
                fields.put(fieldType, defaultDeserializer.readZigBeeType(fieldType.type));
            }
        }
        commandMessage.setCommand(messageFormat.getFields().get(0).command);
        commandMessage.setFields(fields);
        return commandMessage;
    }

    private Map<Integer, Map<Integer, Map<Integer, ZclCommandFormat>>> messageFormats = new HashMap<Integer,Map<Integer, Map<Integer, ZclCommandFormat>>>();

    public void add(final ZclCommandFormat messageFormat) {
        if (!messageFormats.containsKey(messageFormat.getProfileId())) {
            messageFormats.put(messageFormat.getProfileId(), new HashMap<Integer, Map<Integer, ZclCommandFormat>>());
        }
        if (!messageFormats.get(messageFormat.getProfileId()).containsKey(messageFormat.getClusterId())) {
            messageFormats.get(messageFormat.getProfileId()).put(messageFormat.getClusterId(), new HashMap<Integer, ZclCommandFormat>());
        }
        messageFormats.get(messageFormat.getProfileId()).get(messageFormat.getClusterId()).put(messageFormat.getCommandId(), messageFormat);
    }

    public ZclCommandFormat get(final int profileId, final int clusterId, final int commandId) {
        if (!messageFormats.containsKey(profileId)) {
            messageFormats.put(profileId, new HashMap<Integer, Map<Integer, ZclCommandFormat>>());
        }
        if (!messageFormats.get(profileId).containsKey(clusterId)) {
            messageFormats.get(profileId).put(clusterId, new HashMap<Integer, ZclCommandFormat>());
        }
        return messageFormats.get(profileId).get(clusterId).get(commandId);
    }

}