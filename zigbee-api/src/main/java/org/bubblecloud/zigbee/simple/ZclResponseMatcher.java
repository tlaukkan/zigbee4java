package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.network.zcl.ZclCommand;

/**
 * The ZCL response matcher.
 */
public class ZclResponseMatcher implements CommandResponseMatcher {

    @Override
    public boolean isMatch(Command request, Command response) {
        final byte transactionId = ((ZclCommand) request).getTransactionId();
        return new Byte(transactionId).equals(((ZclCommand) response).getTransactionId());
    }
}
