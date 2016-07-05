package org.bubblecloud.zigbee.v3.rpc;

import org.bubblecloud.zigbee.v3.*;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * ZigBeeConsole API implementation.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeRpcApiImpl implements ZigBeeRpcApi, CommandListener {
    /**
     * The {@link org.slf4j.Logger}.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZigBeeRpcApiImpl.class);
    /**
     * The ZigBee console.
     */
    private final ZigBeeGateway zigBeeGateway;

    /**
     * Constructor for setting the ZigBeeConsole.
     * @param zigBeeGateway the ZigBeeConsole
     */
    public ZigBeeRpcApiImpl(final ZigBeeGateway zigBeeGateway) {
        this.zigBeeGateway = zigBeeGateway;
        this.zigBeeGateway.getZigBeeApi().getNetwork().addCommandListener(this);
    }

    @Override
    public String execute(final String command) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(outputStream);
        try {
            zigBeeGateway.processInputLine(command, out);
        } catch (final Exception e) {
            LOGGER.error("Error in ZigBeeConsole API execute command.", e);
            out.println("Error: " + e.getMessage());
        }
        return outputStream.toString();
    }

    @Override
    public int send(Command command) throws ZigBeeException {
        try {
            return zigBeeGateway.getZigBeeApi().getNetwork().sendCommand(command);
        } catch (ZigBeeNetworkManagerException e) {
            LOGGER.error("Error sending ZCL command message, e");
            throw new ZigBeeException("Error sending ZCL command message", e);
        }
    }

    private final Map<String, Queue<Command>> receiveQueues = new HashMap<String, Queue<Command>>();

    @Override
    public String addReceiveQueue() {
        final String receiveQueueId = UUID.randomUUID().toString();
        synchronized (receiveQueues) {
            receiveQueues.put(receiveQueueId, new LinkedList<Command>());
        }
        LOGGER.debug("ZCL command receive queue added: " + receiveQueueId);
        return receiveQueueId;
    }

    @Override
    public void removeReceiveQueue(String receiveQueueId) {
        synchronized (receiveQueues) {
            receiveQueues.remove(receiveQueueId);
        }
        LOGGER.debug("ZCL command receive queue removed: " + receiveQueueId);
    }

    @Override
    public List<Command> receive(String receiveQueueId) throws ZigBeeException {
        final Queue<Command> receiveQueue;
        synchronized (receiveQueues) {
            receiveQueue = receiveQueues.get(receiveQueueId);
        }
        if (receiveQueue == null) {
            throw new ZigBeeException("No such queue: " + receiveQueueId);
        }
        synchronized (receiveQueue) {
            int i = 0;
            final List<Command> receivedCommands = new ArrayList<Command>();
            while (i < 200 && receiveQueue.size() > 0) {
                receivedCommands.add(receiveQueue.poll());
                LOGGER.debug("ZCL command popped from receive queue: " + receiveQueueId);
                i++;
            }
            return receivedCommands;
        }
    }

    @Override
    public void setDeviceLabel(int networkAddress, int endPointId, String label) {
        zigBeeGateway.getZigBeeApi().setDeviceLabel(networkAddress, endPointId, label);
    }

    @Override
    public void setGroupLabel(int groupId, String label) {
        zigBeeGateway.getZigBeeApi().setGroupLabel(groupId, label);
    }

    @Override
    public ZigBeeGroup getGroup(int groupId) {
        return zigBeeGateway.getZigBeeApi().getGroup(groupId);
    }

    @Override
    public List<ZigBeeGroup> getGroups() {
        return zigBeeGateway.getZigBeeApi().getGroups();
    }

    @Override
    public List<ZigBeeDevice> getZigDevices() {
        return zigBeeGateway.getZigBeeApi().getDevices();
    }

    @Override
    public void commandReceived(final Command command) {
        synchronized (receiveQueues) {
            for (final String receiveQueueId : receiveQueues.keySet()) {
                final Queue<Command> receiveQueue = receiveQueues.get(receiveQueueId);
                if (receiveQueue.size() < 1000) {
                    receiveQueue.add(command);
                    LOGGER.debug("ZCL command pushed to receive queue: " + receiveQueueId);
                } else {
                    receiveQueues.remove(receiveQueueId);
                    LOGGER.debug("ZCL command receive queue removed due to overflow: " + receiveQueueId);
                }
            }
        }
    }
}
