package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;

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
     * @throws ZigBeeException if exception occurs in sending
     */
    void send(final ZclCommandMessage command) throws ZigBeeException;

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
    List<ZclCommandMessage> receive(final String receiveQueueId) throws ZigBeeException;
}
