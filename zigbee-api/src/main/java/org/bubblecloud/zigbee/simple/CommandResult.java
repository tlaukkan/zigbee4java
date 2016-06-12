package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.DefaultResponse;
import org.bubblecloud.zigbee.network.zcl.protocol.command.general.DefaultResponseCommand;

/**
 * Value class containing command response.
 */
public class CommandResult {

    /**
     * The response command.
     */
    private final Command response;
    /**
     * The message.
     */
    private final String message;

    /**
     * Constructor which sets the received response command or null if timeout occurs..
     * @param response the response command.
     */
    public CommandResult(final Command response) {
        this.response = response;
        this.message = null;
    }

    /**
     * Constructor for message situations.
     * @param message the message
     */
    public CommandResult(final String message) {
        this.response = null;
        this.message = message;
    }

    /**
     * Constructor for timeout situations.
     */
    public CommandResult() {
        this.response = null;
        this.message = null;
    }

    /**
     * Checks whether command execution was successful.
     * @return TRUE if command execution was successful.
     */
    public boolean isSuccess() {
        return !isTimeout() && !isError();
    }

    /**
     * Checks whether command timed out.
     * @return TRUE if timeout occurred
     */
    public boolean isTimeout() {
        return response == null && message == null;
    }

    /**
     * Checks if message status code was received in default response.
     * @return the message status code
     */
    public boolean isError() {
        if (hasStatusCode()) {
            return getStatusCode() != 0;
        } else {
            if (message == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Check if default response was received.
     * @return TRUE if default response was received
     */
    public boolean hasStatusCode() {
        if (response != null) {
            return response instanceof DefaultResponse || response instanceof ZdoResponse;
        } else {
            return false;
        }
    }

    /**
     * Gets status code received in default response.
     * @return the status code
     */
    public Integer getStatusCode() {
        if (hasStatusCode()) {
            if (response instanceof DefaultResponse) {
                return ((DefaultResponseCommand) response).getStatusCode();
            } else {
                return ((ZdoResponse) response).getStatus();
            }
        } else {
            return null;
        }
    }

    /**
     * Gets the received response.
     * @return the received response
     */
    public Command getResponse() {
        return response;
    }

    /**
     * Gets error or timeout message.
     * @return the message
     */
    public String getMessage() {
        if (isTimeout()) {
            return "Timeout.";
        }
        if (hasStatusCode()) {
            return Status.getStatus((byte) (int) getStatusCode()).description;
        } else {
            return message;
        }
    }

    @Override
    public String toString() {
        if (isSuccess()) {
            return "success";
        } else if (isTimeout()) {
            return "timeout";
        } else {
            final Status status = Status.getStatus((byte) getStatusCode().intValue());
            return "message: " + status.name() + "(0x" + Integer.toHexString(status.id) + ", " + status.description;
        }
    }
}
