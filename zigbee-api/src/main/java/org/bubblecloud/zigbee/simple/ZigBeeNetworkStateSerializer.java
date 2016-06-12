package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.ZigBeeNetworkManager;
import org.bubblecloud.zigbee.network.ZigBeeNode;
import org.bubblecloud.zigbee.network.impl.ZigBeeEndpointImpl;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetwork;
import org.bubblecloud.zigbee.network.impl.ZigBeeNodeImpl;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Serializes and deserializes the ZigBee network state.
 */
public class ZigBeeNetworkStateSerializer {

    /**
     * Serializes the network state.
     * @param networkState the network state
     * @return the serialized network state as json {@link String}.
     */
    public String serialize(final ZigBeeNetworkState networkState) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        try {
            return objectMapper.writeValueAsString(networkState.getDevices());
        } catch (final IOException e) {
            throw new RuntimeException("Error serializing network state.", e);
        }
    }

    /**
     * Deserializes the network state.
     * @param networkState the network state
     * @param networkStateString the network state as {@link String}
     */
    public void deserialize(final ZigBeeNetworkState networkState, final String networkStateString) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        final List<ZigBeeDevice> devices;
        try {
            devices = objectMapper.readValue(networkStateString, ArrayList.class);
        } catch (final IOException e) {
            throw new RuntimeException("Error serializing network state.", e);
        }
        for (final ZigBeeDevice device : devices) {
            networkState.addDevice(device);
        }
    }

}
