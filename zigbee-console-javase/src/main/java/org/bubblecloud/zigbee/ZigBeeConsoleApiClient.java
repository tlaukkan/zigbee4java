package org.bubblecloud.zigbee;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * ZigBeeConsole API client.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeConsoleApiClient {
    /**
     * The {@link org.slf4j.Logger}.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZigBeeConsoleHttpServer.class);
    /**
     * The JSON RPC client.
     */
    private final ZigBeeConsoleRpcApi zigBeeConsoleApi;
    /**
     * The HTTP headers.
     */
    private final HashMap<String, String> headers;

    /**
     * Configures the speech NLP API JSON RPC client.
     * @param url the speech NLP JSON RPC HTTP server URL
     */
    public ZigBeeConsoleApiClient(final String url, final String accessToken) {
        JsonRpcHttpClient  jsonRpcClient;

        try {
            jsonRpcClient = new JsonRpcHttpClient(new URL(url));
            jsonRpcClient.setConnectionTimeoutMillis(3000);
            jsonRpcClient.setReadTimeoutMillis(5000);
            headers = new HashMap<String, String>();
            headers.put("Authorization", "Bearer " + accessToken);
            jsonRpcClient.setHeaders(headers);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL: " + url, e);
        }

        zigBeeConsoleApi = ProxyUtil.createClientProxy(
                getClass().getClassLoader(),
                ZigBeeConsoleRpcApi.class,
                jsonRpcClient);
    }

    /**
     * Gets the ZigBeeConsole API.
     * @return the ZigBeeConsole API.
     */
    public ZigBeeConsoleRpcApi getZigBeeConsoleApi() {
        return zigBeeConsoleApi;
    }
}
