package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.api.cluster.impl.api.global.DefaultResponse;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.command.general.DefaultResponseCommand;

/**
 * ZCL custom response matcher.
 */
public class ZclCustomResponseMatcher implements CommandResponseMatcher {
        @Override
        public boolean isMatch(Command request, Command response) {
            if (((ZclCommand) request).getTransactionId() != null) {
                final byte transactionId = ((ZclCommand) request).getTransactionId();
                if (new Byte(transactionId).equals(((ZclCommand) response).getTransactionId())) {
                    if (response instanceof DefaultResponse) {
                        if (((DefaultResponseCommand) response).getStatusCode() == 0) {
                            return false; // Default response success another response incoming, skip this one.
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