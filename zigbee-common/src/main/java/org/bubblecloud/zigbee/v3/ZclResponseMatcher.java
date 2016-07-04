package org.bubblecloud.zigbee.v3;

import org.bubblecloud.zigbee.v3.zcl.ZclCommand;

/**
 * The ZCL response matcher.
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
