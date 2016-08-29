package org.bubblecloud.zigbee.v3;

import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.general.DefaultResponseCommand;

/**
 * ZCL custom response matcher.
 */
public class ZclCustomResponseMatcher implements CommandResponseMatcher {
    @Override
    public boolean isMatch(Command request, Command response) {
        if (((ZclCommand) request).getTransactionId() != null) {
            final byte transactionId = ((ZclCommand) request).getTransactionId();
            if (new Byte(transactionId).equals(((ZclCommand) response).getTransactionId())) {
                if (response instanceof DefaultResponseCommand) {
                    if (((DefaultResponseCommand) response).getStatusCode() == 0) {
                        return false; // Default response success another
                                      // response incoming, skip this one.
                    } else {
                        return true; // Default response failure, return this one.
                    }
                } else {
                    return true; // This is the actual response, return this one.
                }
            } else {
                return false; // Transaction ID mismatch.
            }
        } else {
            return false; // Transaction ID not set in original command.
        }
    }
}
