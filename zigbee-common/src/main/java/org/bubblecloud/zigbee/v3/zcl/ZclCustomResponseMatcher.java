package org.bubblecloud.zigbee.v3.zcl;

import org.bubblecloud.zigbee.v3.Command;
import org.bubblecloud.zigbee.v3.CommandResponseMatcher;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.DefaultResponse;

/**
 * ZCL custom response matcher.
 * 
 * Implements {@link CommandResponseMatcher} to check if a ZCL transaction matches a request.
 * The matcher will return true if the request and response transaction IDs match.
 * If the response matches the {@link DefaultResponse} class, then the status code is mustn't be 0.
 */
public class ZclCustomResponseMatcher implements CommandResponseMatcher {
        @Override
        public boolean isMatch(Command request, Command response) {
            if (((ZclCommand) request).getTransactionId() != null) {
                final byte transactionId = ((ZclCommand) request).getTransactionId();
                if (new Byte(transactionId).equals(((ZclCommand) response).getTransactionId())) {
                    if (response instanceof DefaultResponse) {
                        if (((DefaultResponse) response).getStatusCode() == 0) {
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