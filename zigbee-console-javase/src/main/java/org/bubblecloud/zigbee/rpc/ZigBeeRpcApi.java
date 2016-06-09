package org.bubblecloud.zigbee.rpc;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.simple.Command;
import org.bubblecloud.zigbee.simple.ZigBeeDevice;

import java.util.List;

/**
 * ZigBee Console API.
 *
 * @author Tommi S.E. Laukkanen
 */
public interface ZigBeeRpcApi {

    /**
     * Executes console command.
     * @param command the console command
     * @return the command output
     */
    String execute(final String command);

    /**
     * Sends ZigBee Cluster Library command without waiting for response.
     *
     * @param command the command
     * @return transaction ID
     * @throws ZigBeeException if exception occurs in sending
     */
    int send(final Command command) throws ZigBeeException;

    /**
     * Start receiving commands by creating receive queue.
     *
     * @return the receive queue ID
     */
    String addReceiveQueue();

    /**
     * Stops receiving by removing receive queue.
     *
     * @param receiveQueueId the receive queue ID
     */
    void removeReceiveQueue(final String receiveQueueId);

    /**
     * Receives ZigBee Cluster Library commands.
     *
     * @return list of commands received.
     * @throws ZigBeeException if exception occurs in receiving
     */
    List<Command> receive(final String receiveQueueId) throws ZigBeeException;

    /**
     * Gets ZigBee devices.
     * @return list of ZigBee devices
     */
    List<ZigBeeDevice> getZigBeeDevices();
}
