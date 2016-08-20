package org.bubblecloud.zigbee.v3;

/**
 * The ZCL response matcher.
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
