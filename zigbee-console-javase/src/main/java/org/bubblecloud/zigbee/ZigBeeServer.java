package org.bubblecloud.zigbee;

import fi.iki.elonen.NanoHTTPD;
import org.bubblecloud.zigbee.rpc.NanoHttpdJsonRpcServer;
import org.bubblecloud.zigbee.rpc.NanoHttpdJsonRpcServerResponse;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * ZigBeeConsole HTTP Server provides JSON RPC API to ZigBeeConsole functions.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeServer extends NanoHTTPD {
    /**
     * The {@link org.slf4j.Logger}.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZigBeeServer.class);
    /**
     * The JSON ROC server.
     */
    private final NanoHttpdJsonRpcServer jsonRpcServer;

    /**
     * Constructor which allows setting the ZigBeeConsole instance and HTTPD port.
     * @param zigBeeConsole the ZigBee Console
     * @param port the HTTP port
     * @param keystorePath the keystore path for SSL or NULL if not used
     * @param keystorePassword the keystore password for SSL or NULL if not used
     * @param sslProtocols the SSL protocols or NULL if not used.
     * @param authorizationProvider the authorization provider
     * @throws IOException if IO Exception occurs
     */
    public ZigBeeServer(final ZigBeeConsole zigBeeConsole, final int port,
                        final String keystorePath, final char[] keystorePassword,
                        final String[] sslProtocols,
                        final AuthorizationProvider authorizationProvider) throws IOException {
        super(port);
        final ZigBeeRpcApiImpl zigBeeRpcApi = new ZigBeeRpcApiImpl(zigBeeConsole);
        jsonRpcServer = new NanoHttpdJsonRpcServer(zigBeeRpcApi, ZigBeeRpcApi.class, authorizationProvider);
        if (keystorePath != null) {
            makeSecure(NanoHTTPD.makeSSLSocketFactory(keystorePath, keystorePassword), sslProtocols);
        }
    }

    @Override
    public void start() throws IOException {
        super.start();
        LOGGER.info("ZigBeeConsole HTTP server started.");
    }

    @Override
    public Response serve(IHTTPSession session) {
        final NanoHttpdJsonRpcServerResponse response = jsonRpcServer.handle(session);
        return newFixedLengthResponse(response.getStatus(), "application/json-rpc", response.getMessage());
    }

}