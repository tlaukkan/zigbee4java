package org.bubblecloud.zigbee.v3.zdo;

import org.bubblecloud.zigbee.v3.Command;
import org.bubblecloud.zigbee.v3.CommandResponseMatcher;

/**
 * The ZDO response matcher.
 * 
 * The matcher will return true if the the response packet is a {@link ZdoResponse}
 * and the response source address matches the destination of the request.
 */
public class ZdoResponseMatcher implements CommandResponseMatcher {

    @Override
    public boolean isMatch(Command request, Command response) {
        if (response instanceof ZdoResponse) {
            return ((ZdoRequest) request).getDestinationAddress() == ((ZdoResponse) response).getSourceAddress();
        } else {
            return false;
        }
    }
}
