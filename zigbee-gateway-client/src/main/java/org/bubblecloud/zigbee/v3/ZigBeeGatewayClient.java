package org.bubblecloud.zigbee.v3;

import org.bubblecloud.zigbee.v3.rpc.ZigBeeRpcClient;

import java.util.List;

/**
 * ZigBeeConsole client which connect with ZigBee console with JSON RPC.
 */
public class ZigBeeGatewayClient extends ZigBeeApi implements ZigBeeNetwork {
    /**
     * The RPC client.
     */
    private final ZigBeeRpcClient rpcClient;
    /**
     * The ZCL API.
     */
    private final ZigBeeNetwork zigBeeNetwork;

    /**
     * Constructor which defines ZigBee RPC API URL and access token.
     * @param url the ZigBee RPC API URL
     * @param accessToken the ZigBee RPC API access token
     */
    public ZigBeeGatewayClient(final String url, final String accessToken) {
        super();
        rpcClient = new ZigBeeRpcClient(url, accessToken);
        zigBeeNetwork = rpcClient;
        setNetwork(rpcClient);
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
    public void addCommandListener(CommandListener commandListener) {
        zigBeeNetwork.addCommandListener(commandListener);
    }

    @Override
    public List<ZigBeeDevice> getZigBeeDevices() {
        return rpcClient.getZigBeeRpcApi().getZigBeeDevices();
    }

    @Override
    public void removeCommandListener(CommandListener commandListener) {
        zigBeeNetwork.removeCommandListener(commandListener);
    }

    @Override
    public int sendCommand(Command command) throws ZigBeeException {
        return zigBeeNetwork.sendCommand(command);
    }
}
