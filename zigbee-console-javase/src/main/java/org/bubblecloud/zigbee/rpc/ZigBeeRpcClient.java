package org.bubblecloud.zigbee.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.simple.Command;
import org.bubblecloud.zigbee.simple.CommandListener;
import org.bubblecloud.zigbee.simple.ZigBeeNetwork;
import org.bubblecloud.zigbee.simple.ZigBeeDevice;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ZigBeeConsole API client.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeRpcClient implements ZigBeeNetwork {
    /**
     * The {@link org.slf4j.Logger}.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZigBeeRpcServer.class);
    /**
     * The minimum polling period in milliseconds.
     */
    public static final long MINIMUM_POLLING_PERIOD_MILLIS = 100;
    /**
     * The wait time after receive error.
     */
    public static final int RECEIVE_ERROR_WAIT_MILLIS = 2000;
    /**
     * The JSON RPC client.
     */
    private final ZigBeeRpcApi zigBeeRpcApi;
    /**
     * The receive queue ID.
     */
    private String receiveQueueId;
    /**
     * The shutdown flag.
     */
    private boolean shutdown = false;
    /**
     * The receive thread.
     */
    private Thread receiveThread;
    /**
     * The ZCL command listeners.
     */
    private List<CommandListener> commandListeners = new ArrayList<CommandListener>();

    /**
     * Constructor which defines ZigBee RPC API URL and access token.
     * @param url the ZigBee RPC API URL
     * @param accessToken the ZigBee RPC API access token
     */
    public ZigBeeRpcClient(final String url, final String accessToken) {
        JsonRpcHttpClient  jsonRpcClient;

        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.enableDefaultTyping();
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

            final HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "Bearer " + accessToken);

            jsonRpcClient = new JsonRpcHttpClient(objectMapper, new URL(url), headers);
            jsonRpcClient.setConnectionTimeoutMillis(3000);
            jsonRpcClient.setReadTimeoutMillis(5000);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL: " + url, e);
        }

        zigBeeRpcApi = ProxyUtil.createClientProxy(
                getClass().getClassLoader(),
                ZigBeeRpcApi.class,
                jsonRpcClient);

    }

    /**
     * Starts API client.
     */
    public void startup() {
        if (receiveQueueId != null) {
            throw new UnsupportedOperationException("Already started");
        }
        receiveQueueId = zigBeeRpcApi.addReceiveQueue();
        receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                receiveLoop();
            }
        });
        receiveThread.start();
    }

    /**
     * Stops API client.
     */
    public void shutdown() {
        shutdown = true;
        receiveThread.interrupt();
        try {
            receiveThread.join();
        } catch (final InterruptedException e) {
            LOGGER.trace("Shutdown receive thread join interrupted.", e);
        }
        zigBeeRpcApi.removeReceiveQueue(receiveQueueId);
    }

    /**
     * Gets the ZigBeeConsole API.
     * @return the ZigBeeConsole API.
     */
    public ZigBeeRpcApi getZigBeeRpcApi() {
        return zigBeeRpcApi;
    }

    /**
     * Executes console command.
     * @param command the console command
     * @return the command output
     */
    public String execute(final String command) {
        return zigBeeRpcApi.execute(command);
    }

    @Override
    public int sendCommand(Command command) throws ZigBeeException {
        return zigBeeRpcApi.send(command);
    }

    @Override
    public void addCommandListener(final CommandListener commandListener) {
        final List<CommandListener> modifiedCommandListeners = new ArrayList<CommandListener>(commandListeners);
        modifiedCommandListeners.add(commandListener);
        commandListeners = modifiedCommandListeners;
    }

    @Override
    public void removeCommandListener(final CommandListener commandListener) {
        final List<CommandListener> modifiedCommandListeners = new ArrayList<CommandListener>(commandListeners);
        modifiedCommandListeners.add(commandListener);
        commandListeners = modifiedCommandListeners;
    }

    /**
     * The receive loop.
     */
    private void receiveLoop() {
        while (!shutdown) {
            try {
                final List<Command> receivedCommands = zigBeeRpcApi.receive(receiveQueueId);
                for (final Command receivedCommand : receivedCommands) {
                    for (final CommandListener commandListener : commandListeners) {
                        commandListener.commandReceived(receivedCommand);
                    }
                }
            } catch (ZigBeeException e) {
                LOGGER.error("Error while receiving ZCL commands.", e);
                try {
                    Thread.sleep(RECEIVE_ERROR_WAIT_MILLIS);
                } catch (final InterruptedException ex) {
                    break;
                }
            }
            try {
                Thread.sleep(MINIMUM_POLLING_PERIOD_MILLIS);
            } catch (final InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public List<ZigBeeDevice> getZigBeeDevices() {
        return zigBeeRpcApi.getZigBeeDevices();
    }
}
