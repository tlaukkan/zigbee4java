package org.bubblecloud.zigbee.v3;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
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
        final List<Object> destinations = new ArrayList<Object>();
        destinations.addAll(networkState.getDevices());
        destinations.addAll(networkState.getGroups());
        try {
            return objectMapper.writeValueAsString(destinations);
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
        final List<Object> devices;
        try {
            devices = objectMapper.readValue(networkStateString, ArrayList.class);
        } catch (final IOException e) {
            throw new RuntimeException("Error serializing network state.", e);
        }
        for (final Object destination : devices) {
            if (destination instanceof ZigBeeGroupAddress) {
                networkState.addGroup((ZigBeeGroupAddress) destination);
            } else {
                networkState.addDevice((ZigBeeDevice) destination);
            }
        }
    }

}
