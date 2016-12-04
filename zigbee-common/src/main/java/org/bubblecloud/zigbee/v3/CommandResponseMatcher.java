package org.bubblecloud.zigbee.v3;

/**
 * Defines the interface for the response matcher
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
