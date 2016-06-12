package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.network.zcl.ZclCommand;

/**
 * Matches command for response.
 */
public interface CommandResponseMatcher {
    /**
     * Matches request and response.
     * @param request the request
     * @param response the response
     * @return TRUE if request matches response
     */
    boolean isMatch(Command request, Command response);
}
