package org.bubblecloud.zigbee.v3;

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
