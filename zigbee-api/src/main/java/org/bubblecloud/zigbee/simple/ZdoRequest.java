package org.bubblecloud.zigbee.simple;

/**
 * Common interface for response commands..
 */
public interface ZdoRequest {

    /**
     * Gets destination address.
     * @return the destination address
     */
    int getDestinationAddress();

}
