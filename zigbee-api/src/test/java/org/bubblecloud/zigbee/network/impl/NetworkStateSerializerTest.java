package org.bubblecloud.zigbee.network.impl;

import org.junit.Assert;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.junit.Test;

/**
 * Test for network state serialization.
 */
public class NetworkStateSerializerTest {

    @Test
    public void testSerializeNetworkState() {
        final ZigBeeNodeImpl node = new ZigBeeNodeImpl(1, "00:00:00:00:00:00:00:00", (short) 2);
        final ZigBeeEndpointImpl endpoint = new ZigBeeEndpointImpl(node, 1, 2, (byte) 3, (short) 4, new int[] {5}, new int[] {6});

        final ZigBeeNetwork zigBeeNetwork = new ZigBeeNetwork();
        zigBeeNetwork.addNode(node);
        zigBeeNetwork.addEndpoint(endpoint);

        final NetworkStateSerializer networkStateSerializer = new NetworkStateSerializer();
        final String networkState = networkStateSerializer.serialize(zigBeeNetwork);

        System.out.println(networkState);

        final ZigBeeNetwork zigBeeNetworkRestored = new ZigBeeNetwork();
        networkStateSerializer.deserialize(null, zigBeeNetworkRestored, networkState);

        Assert.assertEquals(1, zigBeeNetworkRestored.getDevices().size());
        Assert.assertEquals(1, zigBeeNetworkRestored.getEndPoints(
                zigBeeNetworkRestored.getDevices().keySet().iterator().next()).size());

        final String networkStateOfRestoredNetwork = networkStateSerializer.serialize(zigBeeNetworkRestored);
        Assert.assertEquals(networkState, networkStateOfRestoredNetwork);
    }
}
