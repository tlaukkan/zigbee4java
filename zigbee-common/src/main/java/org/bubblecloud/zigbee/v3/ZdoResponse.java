package org.bubblecloud.zigbee.v3;

/**
 * Common interface for response commands..
 */
public interface ZdoResponse {

    /**
     * Gets the source address
     * @return the source address
     */
    int getSourceAddress();

    /**
     * Gets the response status
     * @return the response status
     */
    int getStatus();

}
