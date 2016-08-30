package org.bubblecloud.zigbee.v3.zcl;

import org.bubblecloud.zigbee.v3.Command;
import org.bubblecloud.zigbee.v3.CommandResponseMatcher;

/**
 * The ZCL response matcher.
 * 
 * Implements {@link CommandResponseMatcher} to check if a ZCL transaction matches a request.
 * The matcher will return true if the request and response transaction IDs match.
 */
public class ZclResponseMatcher implements CommandResponseMatcher {

    @Override
    public boolean isMatch(Command request, Command response) {
        if (response instanceof ZclCommand && ((ZclCommand) request).getTransactionId() != null) {
            final byte transactionId = ((ZclCommand) request).getTransactionId();
            return new Byte(transactionId).equals(((ZclCommand) response).getTransactionId());
        } else {
            return false;
        }
    }
}
