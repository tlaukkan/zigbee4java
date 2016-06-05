package org.bubblecloud.zigbee.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import fi.iki.elonen.NanoHTTPD;
import org.bubblecloud.zigbee.AccessLevel;
import org.bubblecloud.zigbee.AuthorizationProvider;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Class which implements NanoHttpd version of JsonRpcServer.
 *
 * @author Tommi S.E. Laukkanen
 */
public class NanoHttpdJsonRpcServer extends JsonRpcServer {
    /**
     * The {@link org.slf4j.Logger}.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(NanoHttpdJsonRpcServer.class);
    /**
     * The authorization provider.
     */
    private AuthorizationProvider authorizationProvider;

    /**
     * Constructor for setting handler and remote interface.
     * @param handler the handler
     * @param remoteInterface the remote interface
     * @param authorizationProvider the authorization provider
     */
    public NanoHttpdJsonRpcServer(final ObjectMapper objectMapper, final Object handler, final Class<?> remoteInterface,
                                  final AuthorizationProvider authorizationProvider) {
        super(objectMapper, handler, remoteInterface);
        this.authorizationProvider = authorizationProvider;
    }

    /**
     * Handle HTTP request.
     * @param session the session
     * @return the response
     */
    public NanoHttpdJsonRpcServerResponse handle(final NanoHTTPD.IHTTPSession session) {
        final NanoHttpdJsonRpcServerResponse response = new NanoHttpdJsonRpcServerResponse();

        final String authorizationHeader = session.getHeaders().get("authorization");
        if (authorizationHeader == null) {
            response.setStatus(NanoHTTPD.Response.Status.FORBIDDEN);
            return response;
        }

        if (!authorizationHeader.toLowerCase().startsWith("bearer ")) {
            response.setStatus(NanoHTTPD.Response.Status.FORBIDDEN);
            return response;
        }

        final String authorizationToken = authorizationHeader.substring(7);
        if (authorizationProvider.getAccessLevel(authorizationToken) != AccessLevel.ADMINISTRATION) {
            response.setStatus(NanoHTTPD.Response.Status.FORBIDDEN);
            return response;
        }


        LOGGER.trace("Handing HttpServletRequest " + session.getMethod());

        try {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            final Object input;
            if (session.getMethod() == NanoHTTPD.Method.POST) {
                input = session.getInputStream();
            } else {
                if (session.getMethod() != NanoHTTPD.Method.GET) {
                    throw new IOException("Invalid request method, only POST and GET is supported");
                }
                input = createInputStream(session.getParms().get("method"), session.getParms().get("id"),
                        session.getParms().get("params"));
            }

            final int result = this.handle((InputStream) input, output);
            response.setStatus(NanoHTTPD.Response.Status.OK);
            if (result != 0) {
                if (result == -32700 || result == -32602 || result == -32603 || result <= -32000 && result >= -32099) {
                    response.setStatus(NanoHTTPD.Response.Status.INTERNAL_ERROR);
                } else if (result == -32600) {
                    response.setStatus(NanoHTTPD.Response.Status.NOT_FOUND);
                } else if (result == -32601) {
                    response.setStatus(NanoHTTPD.Response.Status.FORBIDDEN);
                }
            }

            response.setMessage(new String(output.toByteArray(), Charset.forName("UTF-8")));
        } catch (final IOException e) {
            LOGGER.error("Error starting processing JSON RPC request.", e);
            response.setStatus(NanoHTTPD.Response.Status.INTERNAL_ERROR);
            response.setMessage("");
        }

        return response;
    }
}
