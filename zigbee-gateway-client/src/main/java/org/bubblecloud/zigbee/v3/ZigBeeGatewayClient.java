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
    public void setDeviceLabel(final int networkAddress, final int endPointId, final String label) {
        rpcClient.getZigBeeRpcApi().setDeviceLabel(networkAddress, endPointId, label);
    }

    @Override
    public void removeDevice(final int networkAddress) {
        rpcClient.getZigBeeRpcApi().removeDevice(networkAddress);
    }

    @Override
    public List<ZigBeeDevice> getDevices() {
        return rpcClient.getZigBeeRpcApi().getDevices();
    }

    @Override
    public void addMembership(final int groupId, final String label) {
        rpcClient.getZigBeeRpcApi().addGroup(groupId, label);
    }

    @Override
    public void removeMembership(final int groupId) {
        rpcClient.getZigBeeRpcApi().removeGroup(groupId);
    }

    @Override
    public ZigBeeGroupAddress getGroup(final int groupId) {
        return rpcClient.getZigBeeRpcApi().getGroup(groupId);
    }

    @Override
    public List<ZigBeeGroupAddress> getGroups() {
        return rpcClient.getZigBeeRpcApi().getGroups();
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
