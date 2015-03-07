package org.bubblecloud.zigbee.network.impl;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.ZigBeeNetworkManager;
import org.bubblecloud.zigbee.network.ZigBeeNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * NetworkStateSerializer serializes and deserializes the ZigBeeNetworkState.
 */
public class NetworkStateSerializer {

    /**
     * Serializes network state.
     * @return the serialized network state as String.
     */
    public String serialize(final ZigBeeNetwork zigBeeNetwork) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        try {
            final HashMap<ZigBeeNode, HashMap<Integer, ZigBeeEndpoint>> devices = new HashMap(zigBeeNetwork.getDevices());

            final List<ZigBeeEndpoint> endpoints = new ArrayList<ZigBeeEndpoint>();
            for (final ZigBeeNode node : devices.keySet()) {
                for (final ZigBeeEndpoint endpoint : devices.get(node).values()) {
                    endpoints.add(endpoint);
                }
            }

            return objectMapper.writeValueAsString(endpoints);
        } catch (final IOException e) {
            throw new RuntimeException("Error serializing network state.", e);
        }
    }

    /**
     * Deserializes network state.
     * @param zigBeeNetworkManager the ZigBee network manager
     * @param zigBeeNetwork the ZigBee network
     * @param networkStateString the network state as String
     */
    public void deserialize(final ZigBeeNetworkManager zigBeeNetworkManager, final ZigBeeNetwork zigBeeNetwork, final String networkStateString) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        final List<ZigBeeEndpoint> endpoints;
        try {
            endpoints = objectMapper.readValue(networkStateString, ArrayList.class);
        } catch (final IOException e) {
            throw new RuntimeException("Error serializing network state.", e);
        }
        for (final ZigBeeEndpoint endpoint : endpoints) {
            if (zigBeeNetwork.getNode(endpoint.getNode().getIeeeAddress()) == null) {
                zigBeeNetwork.addNode((ZigBeeNodeImpl) endpoint.getNode());
            }
            ((ZigBeeEndpointImpl) endpoint).setNetworkManager(zigBeeNetworkManager);
            zigBeeNetwork.addEndpoint(endpoint);
        }
    }

}
