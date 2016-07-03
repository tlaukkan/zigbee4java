package org.bubblecloud.zigbee.v3.rpc;


import fi.iki.elonen.NanoHTTPD;

/**
 * NanoHttpd JSON RPC server response.
 *
 * @author Tommi S.E. Laukkanen
 */
public class NanoHttpdJsonRpcServerResponse {
    /**
     * The response status.
     */
    private NanoHTTPD.Response.Status status;
    /**
     * The response message.
     */
    private String message;

    /**
     * Gets message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Gets status.
     * @return the status.
     */
    public NanoHTTPD.Response.Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     * @param status the status
     */
    public void setStatus(final NanoHTTPD.Response.Status status) {
        this.status = status;
    }
}
