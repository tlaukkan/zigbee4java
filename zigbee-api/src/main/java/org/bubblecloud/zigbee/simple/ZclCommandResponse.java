package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.command.general.DefaultResponseCommand;

/**
 * Value class containing command response.
 */
public class ZclCommandResponse {

    /**
     * The response command.
     */
    private  final ZclCommand response;
    /**
     * The error.
     */
    private final String error;


    /**
     * Constructor which sets the received response command or null if timeout occurs..
     * @param response the response command.
     */
    public ZclCommandResponse(final ZclCommand response) {
        this.response = response;
        this.error = null;
    }

    /**
     * Constructor for error situations.
     * @param error the error
     */
    public ZclCommandResponse(final String error) {
        this.response = null;
        this.error = error;
    }

    /**
     * Constructor for timeout situations.
     */
    public ZclCommandResponse() {
        this.response = null;
        this.error = null;
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
        return response == null && error == null;
    }

    /**
     * Checks if error status code was received in default response.
     * @return the error status code
     */
    public boolean isError() {
        if (isDefaultResponse()) {
            return ((DefaultResponseCommand) response).getStatusCode() != 0;
        } else {
            if (error == null) {
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
    public boolean isDefaultResponse() {
        if (response != null) {
            return response.getType() == ZclCommandType.DEFAULT_RESPONSE_COMMAND;
        } else {
            return false;
        }
    }

    /**
     * Gets status code received in default response.
     * @return the status code
     */
    public Integer getStatusCode() {
        if (isDefaultResponse()) {
            return ((DefaultResponseCommand) response).getStatusCode();
        } else {
            return null;
        }
    }

    /**
     * Gets the received response.
     * @param <C> the expected received response type
     * @return the received response
     */
    public <C extends  ZclCommand> C getResponse() {
        return (C) response;
    }

    /**
     * Gets error.
     * @return the error
     */
    public String getError() {
        if (isTimeout()) {
            return "Timeout.";
        }
        if (isDefaultResponse()) {
            return Status.getStatus((byte) (int) getStatusCode()).toString();
        } else {
            return error;
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
            return "error: " + status.name() + "(0x" + Integer.toHexString(status.id) + ", " + status.description;
        }
    }
}
