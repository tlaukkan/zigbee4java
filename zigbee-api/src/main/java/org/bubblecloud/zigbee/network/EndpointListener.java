package org.bubblecloud.zigbee.network;

/**
 * Created with IntelliJ IDEA.
 * User: tlaukkan
 * Date: 12/15/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EndpointListener {

    public void endpointAdded(final ZigBeeEndpoint endpoint);

    public void endpointUpdated(final ZigBeeEndpoint endpoint);

    public void endpointRemoved(final ZigBeeEndpoint endpoint);

}
