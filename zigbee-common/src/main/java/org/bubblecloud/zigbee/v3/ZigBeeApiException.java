package org.bubblecloud.zigbee.v3;

/**
 * Runtime exception class for ZigBee API.
 */
public class ZigBeeApiException extends RuntimeException {
    /**
     * Constructor for setting message and cause.
     * @param message the message
     * @param cause the cause
     */
    public ZigBeeApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
