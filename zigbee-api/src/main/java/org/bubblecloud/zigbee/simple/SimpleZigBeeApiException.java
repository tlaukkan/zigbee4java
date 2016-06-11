package org.bubblecloud.zigbee.simple;

/**
 * Runtime exception class for Simple ZigBee API.
 */
public class SimpleZigBeeApiException extends RuntimeException {
    /**
     * Constructor for setting message and cause.
     * @param message the message
     * @param cause the cause
     */
    public SimpleZigBeeApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
