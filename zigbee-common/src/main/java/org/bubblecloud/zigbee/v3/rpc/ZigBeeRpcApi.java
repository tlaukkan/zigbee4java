package org.bubblecloud.zigbee.v3.rpc;

import org.bubblecloud.zigbee.v3.Command;
import org.bubblecloud.zigbee.v3.ZigBeeDevice;
import org.bubblecloud.zigbee.v3.ZigBeeException;
import org.bubblecloud.zigbee.v3.ZigBeeGroupAddress;

import java.util.List;

/**
 * ZigBee RPC API to be used with JSON RPC.
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
     * Sets device label.
     * @param networkAddress the network address
     * @param endPointId the end point ID
     * @param label the label
     */
    void setDeviceLabel(int networkAddress, int endPointId, String label);

    /**
     * Removes device(s) by network address.
     * @param networkAddress the network address
     */
    void removeDevice(int networkAddress);

    /**
     * Gets ZigBee devices.
     * @return list of ZigBee devices
     */
    List<ZigBeeDevice> getDevices();

    /**
     * Sets group label.
     * @param groupId the group ID
     * @param label the label
     */
    void addGroup(int groupId, String label);

    /**
     * Removes group label.
     * @param groupId the group ID
     */
    void removeGroup(int groupId);

    /**
     * Gets group by network address.
     * @param groupId the group ID
     * @return the ZigBee group or null if no exists with given group ID.
     */
    ZigBeeGroupAddress getGroup(int groupId);

    /**
     * Gets all groups.
     * @return list of groups.
     */
    List<ZigBeeGroupAddress> getGroups();

}
