package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.zcl.ZclApi;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.ZclCommandListener;
import org.bubblecloud.zigbee.rpc.ZigBeeRpcClient;
import org.bubblecloud.zigbee.simple.SimpleZigBeeApi;
import org.bubblecloud.zigbee.simple.ZigBeeDevice;

import java.util.List;

/**
 * ZigBeeConsole client which connect with ZigBee console with JSON RPC.
 */
public class ZigBeeConsoleClient extends SimpleZigBeeApi implements ZclApi {
    /**
     * The RPC client.
     */
    private final ZigBeeRpcClient rpcClient;
    /**
     * The ZCL API.
     */
    private final ZclApi zclApi;

    /**
     * Constructor which defines ZigBee RPC API URL and access token.
     * @param url the ZigBee RPC API URL
     * @param accessToken the ZigBee RPC API access token
     */
    public ZigBeeConsoleClient(final String url, final String accessToken) {
        super();
        rpcClient = new ZigBeeRpcClient(url, accessToken);
        zclApi = rpcClient;
        setZclApi(rpcClient);
    }

    /**
     * Starts client.
     */
    public void startup() {
        rpcClient.startup();
    }

    /**
     * Stops API client.
     */
    public void shutdown() {
        rpcClient.shutdown();
    }

    /**
     * Executes console command.
     * @param command the console command
     * @return the command output
     */
    public String execute(final String command) {
        return rpcClient.execute(command);
    }

    @Override
    public void addCommandListener(ZclCommandListener commandListener) {
        zclApi.addCommandListener(commandListener);
    }

    @Override
    public List<ZigBeeDevice> getZigBeeDevices() {
        return zclApi.getZigBeeDevices();
    }

    @Override
    public void removeCommandListener(ZclCommandListener commandListener) {
        zclApi.removeCommandListener(commandListener);
    }

    @Override
    public int sendCommand(ZclCommand command) throws ZigBeeException {
        return zclApi.sendCommand(command);
    }
}
