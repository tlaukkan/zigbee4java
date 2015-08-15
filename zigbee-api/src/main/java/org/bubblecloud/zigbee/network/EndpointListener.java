package org.bubblecloud.zigbee.network;

/**
 * Provides an interface to notify upper layers of changes to {@link ZigBeeEndpoint endpoints}.
 */
public interface EndpointListener {

    public void endpointAdded(final ZigBeeEndpoint endpoint);

    public void endpointUpdated(final ZigBeeEndpoint endpoint);

    public void endpointRemoved(final ZigBeeEndpoint endpoint);

}
