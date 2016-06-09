package org.bubblecloud.zigbee.rpc;

import org.bubblecloud.zigbee.ZigBeeConsole;
import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.simple.Command;
import org.bubblecloud.zigbee.simple.CommandListener;
import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.simple.ZigBeeDevice;
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
    private final ZigBeeConsole zigBeeConsole;

    /**
     * Constructor for setting the ZigBeeConsole.
     * @param zigBeeConsole the ZigBeeConsole
     */
    public ZigBeeRpcApiImpl(final ZigBeeConsole zigBeeConsole) {
        this.zigBeeConsole = zigBeeConsole;
        this.zigBeeConsole.getZigBeeApi().addCommandListener(this);
    }

    @Override
    public String execute(final String command) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(outputStream);
        try {
            zigBeeConsole.processInputLine(command, out);
        } catch (final Exception e) {
            LOGGER.error("Error in ZigBeeConsole API execute command.", e);
            out.println("Error: " + e.getMessage());
        }
        return outputStream.toString();
    }

    @Override
    public int send(Command command) throws ZigBeeException {
        try {
            return zigBeeConsole.getZigBeeApi().sendCommand(command);
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
    public List<ZigBeeDevice> getZigBeeDevices() {
        return zigBeeConsole.getZigBeeApi().getZigBeeDevices();
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
