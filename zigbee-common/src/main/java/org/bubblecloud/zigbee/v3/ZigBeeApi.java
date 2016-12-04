package org.bubblecloud.zigbee.v3;

import org.bubblecloud.zigbee.util.ZigBeeConstants;
import org.bubblecloud.zigbee.v3.model.ZToolAddress16;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.field.AttributeIdentifier;
import org.bubblecloud.zigbee.v3.zcl.field.AttributeReportingConfigurationRecord;
import org.bubblecloud.zigbee.v3.zcl.field.Unsigned16BitInteger;
import org.bubblecloud.zigbee.v3.zcl.field.WriteAttributeRecord;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclAttributeType;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.color.control.MoveToColorCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.door.lock.LockDoorCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.door.lock.UnlockDoorCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.general.ConfigureReportingCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.general.ReadAttributesCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.general.WriteAttributesCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.groups.AddGroupCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.groups.GetGroupMembershipCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.groups.RemoveGroupCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.groups.ViewGroupCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.wd.SquawkCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.wd.StartWarningCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.level.control.MoveToLevelCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.on.off.OffCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.on.off.OnCommand;
import org.bubblecloud.zigbee.v3.zdo.command.*;
import org.bubblecloud.zigbee.util.Cie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

/**
 * ZigBee API. This API is experimental and under development.
 */
public class ZigBeeApi {
    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ZigBeeApi.class);
    /**
     * The network.
     */
    private ZigBeeNetwork network;
    /**
     * The network state.
     */
    private ZigBeeNetworkState networkState;
    /**
     * The command listener creation times.
     */
    private Set<CommandExecution> commandExecutions =
            new HashSet<CommandExecution>();

    /**
     * Default constructor inheritance.
     */
    public ZigBeeApi() {
    }

    /**
     * Constructor for setting the ZCL API.
     * @param network the ZCL API
     */
    public ZigBeeApi(final ZigBeeNetwork network) {
        this.network = network;
    }

    /**
     * Sets network.
     * @param network the network
     */
    public void setNetwork(final ZigBeeNetwork network) {
        this.network = network;
    }

    /**
     * Gets the ZigBee network.
     * @return the ZigBee network
     */
    public ZigBeeNetwork getNetwork() {
        return network;
    }

    /**
     * Sets network state.
     * @param networkState the network state
     */
    public void setNetworkState(final ZigBeeNetworkState networkState) {
        this.networkState = networkState;
    }

    /**
     * Gets the ZigBee network state.
     * @return the ZigBee network state
     */
    public ZigBeeNetworkState getNetworkState() {
        return networkState;
    }

    /**
     * Sets device label.
     * @param networkAddress the network address
     * @param endPointId the end point ID
     * @param label the label
     */
    public void setDeviceLabel(final int networkAddress, final int endPointId, final String label) {
        final ZigBeeDevice device = networkState.getDevice(new ZigBeeDeviceAddress(networkAddress, endPointId));
        device.setLabel(label);
        networkState.updateDevice(device);
    }

    /**
     * Removes device(s) by network address.
     * @param networkAddress the network address
     */
    public void removeDevice(final int networkAddress) {
        final List<ZigBeeDevice> devices = networkState.getDevices();
        final List<ZigBeeDevice> devicesToRemove = new ArrayList<ZigBeeDevice>();
        for (final ZigBeeDevice device : devices) {
            if (device.getNetworkAddress() == networkAddress) {
                devicesToRemove.add(device);
            }
        }

        for (final ZigBeeDevice device : devicesToRemove) {
            networkState.removeDevice(device.getNetworkAddress(), device.getEndpoint());
        }
    }


    /**
     * Gets ZigBee devices.
     * @return list of ZigBee devices
     */
    public List<ZigBeeDevice> getDevices() {
        return getNetworkState().getDevices();
    }

    /**
     * Sets group label.
     * @param groupId the group ID
     * @param label the label
     */
    public void addMembership(final int groupId, final String label) {
        if (networkState.getGroup(groupId) == null) {
            networkState.addGroup(new ZigBeeGroupAddress(groupId, label));
        } else {
            final ZigBeeGroupAddress group = networkState.getGroup(groupId);
            group.setLabel(label);
            networkState.updateGroup(group);
        }
    }

    /**
     * Removes group label.
     * @param groupId the group ID
     */
    public void removeMembership(final int groupId) {
        networkState.removeGroup(groupId);
    }

    /**
     * Gets group by network address.
     * @param groupId the group ID
     * @return the ZigBee group or null if no exists with given group ID.
     */
    public ZigBeeGroupAddress getGroup(final int groupId) {
        return networkState.getGroup(groupId);
    }

    /**
     * Gets all groups.
     * @return list of groups.
     */
    public List<ZigBeeGroupAddress> getGroups() {
        return networkState.getGroups();
    }

    /**
     * Labels destination.
     * @param destination the {@link ZigBeeAddress}
     */
    public void label(final ZigBeeAddress destination, final String label) {
        if (destination.isGroup()) {
            final ZigBeeGroupAddress group = (ZigBeeGroupAddress) destination;
            this.addMembership(group.getGroupId(), label);
        } else {
            final ZigBeeDeviceAddress device = (ZigBeeDeviceAddress) destination;
            this.setDeviceLabel(device.getAddress(), device.getEndpoint(), label);
        }
    }

    /**
     * Sets user descriptor.
     * @param device the device
     * @param descriptor the descriptor
     * @return TRUE if no errors occurred in sending.
     */
    public Future<CommandResult> describe(final ZigBeeDevice device, final String descriptor) {
        final UserDescriptorSet command = new UserDescriptorSet(device.getNetworkAddress(), device.getNetworkAddress(),
                descriptor);

        return unicast(command);
    }

    /**
     * Binds two devices.
     * @param source the source device
     * @param destination the destination device
     * @param clusterId the cluster ID
     * @return TRUE if no errors occurred in sending.
     */
    public Future<CommandResult> bind(final ZigBeeDevice source, final ZigBeeDevice destination, final int clusterId) {
        final int destinationAddress = source.getNetworkAddress();
        final long bindSourceAddress = source.getIeeeAddress();
        final int bindSourceEndpoint = source.getEndpoint();
        final int bindCluster = clusterId;
        final int bindDestinationAddressingMode = 3; // 64 bit addressing
        final long bindDestinationAddress = destination.getIeeeAddress();
        final int bindDestinationEndpoint = destination.getEndpoint();
        final BindRequest command = new BindRequest(
                destinationAddress,
                bindSourceAddress,
                bindSourceEndpoint,
                bindCluster,
                bindDestinationAddressingMode,
                bindDestinationAddress,
                bindDestinationEndpoint
        );
        return unicast(command);
    }

    /**
     * Unbinds two devices.
     * @param source the source device
     * @param destination the destination device
     * @param clusterId the cluster ID
     * @return TRUE if no errors occurred in sending.
     */
    public Future<CommandResult> unbind(final ZigBeeDevice source, final ZigBeeDevice destination,
                                        final int clusterId) {
        final int destinationAddress = source.getNetworkAddress();
        final long bindSourceAddress = source.getIeeeAddress();
        final int bindSourceEndpoint = source.getEndpoint();
        final int bindCluster = clusterId;
        final int bindDestinationAddressingMode = 3; // 64 bit addressing
        final long bindDestinationAddress = destination.getIeeeAddress();
        final int bindDestinationEndpoint = destination.getEndpoint();
        final UnbindRequest command = new UnbindRequest(
                destinationAddress,
                bindSourceAddress,
                bindSourceEndpoint,
                bindCluster,
                bindDestinationAddressingMode,
                bindDestinationAddress,
                bindDestinationEndpoint
        );
        return unicast(command);
    }

    /**
     * Switches destination on.
     * @param destination the {@link ZigBeeAddress}
     * @return the command result future.
     */
    public Future<CommandResult> on(final ZigBeeAddress destination) {
        final OnCommand command = new OnCommand();
        return send(destination, command);

    }

    /**
     * Switches destination off.
     * @param destination the {@link ZigBeeAddress}
     * @return the command result future.
     */
    public Future<CommandResult> off(final ZigBeeAddress destination) {
        final OffCommand command = new OffCommand();
        return send(destination, command);
    }

    /**
     * Colors device light.
     * @param destination the {@link ZigBeeAddress}
     * @param red the red component [0..1]
     * @param green the green component [0..1]
     * @param blue the blue component [0..1]
     * @param time the in seconds
     * @return the command result future.
     */
    public Future<CommandResult> color(final ZigBeeAddress destination, final double red, final double green,
                                       final double blue, double time) {
        final MoveToColorCommand command = new MoveToColorCommand();

        final Cie cie = Cie.rgb2cie(red, green ,blue);

        int x = (int) (cie.x * 65536);
        int y = (int) (cie.y * 65536);
        if (x > 65279) {
            x = 65279;
        }
        if (y > 65279) {
            y = 65279;
        }

        command.setColorX(x);
        command.setColorY(y);
        command.setTransitionTime((int) (time * 10));

        return send(destination, command);
    }

    /**
     * Moves device level.
     * @param destination the {@link ZigBeeAddress}
     * @param level the level in range [0, 1] which will be mapped to [0, 254] in ZigBee layer
     * @param time the transition time in seconds which will be mapped to 1 tenth of seconds in ZigBee layer
     * @return the command result future.
     */
    public Future<CommandResult> level(final ZigBeeAddress destination, final double level, final double time) {

        final MoveToLevelCommand command = new MoveToLevelCommand();

        int l = (int) (level * 254);
        if (l > 254) {
            l = 254;
        }
        if (l < 0) {
            l = 0;
        }

        command.setLevel(l);
        command.setTransitionTime((int) (time * 10));

        return send(destination, command);
    }

    /**
     * Locks door.
     * @param destination the {@link ZigBeeAddress}
     * @param pinCode the pin code
     * @return the command result future.
     */
    public Future<CommandResult> lock(final ZigBeeAddress destination, final String pinCode) {
        final LockDoorCommand command = new LockDoorCommand();

        command.setPinCode(pinCode);

        return send(destination, command);
    }

    /**
     * Unlocks door.
     * @param destination the {@link ZigBeeAddress}
     * @param pinCode the pin code
     * @return the command result future.
     */
    public Future<CommandResult> unlock(final ZigBeeAddress destination, final String pinCode) {
        final UnlockDoorCommand command = new UnlockDoorCommand();

        command.setPinCode(pinCode);

        return send(destination, command);
    }

    /**
     * This command uses the WD capabilities to emit a quick audible/visible pulse called a "squawk".
     * @param destination the {@link ZigBeeAddress}
     * @param mode the mode
     * @param strobe the strobe
     * @param level the level
     * @return the command result future
     */
    public Future<CommandResult> squawk(final ZigBeeAddress destination, final int mode, final int strobe, final int level) {
        final SquawkCommand command = new SquawkCommand();

        final int header = (level << 6) | (strobe << 4) | mode;

        command.setHeader(header);

        return send(destination, command);
    }

    /**
     * Starts warning.
     * @param destination the {@link ZigBeeAddress}
     * @param mode the mode
     * @param strobe the strobe
     * @param duration the duration
     * @return the command result future
     */
    public Future<CommandResult> warn(final ZigBeeAddress destination, final int mode, final int strobe, final int duration) {
        final StartWarningCommand command = new StartWarningCommand();

        final int header = (strobe << 4) | mode;
        command.setHeader(header);
        command.setWarningDuration(duration);

        return send(destination, command);
    }

    /**
     * Writes attribute to device.
     * @param device the device
     * @param clusterId the cluster ID
     * @param attributeId the attribute ID
     * @param value the value
     * @return the command result future
     */
    public Future<CommandResult> write(final ZigBeeDevice device, final int clusterId, final int attributeId,
                                       final Object value) {

        final WriteAttributesCommand command = new WriteAttributesCommand();
        command.setClusterId(clusterId);

        final WriteAttributeRecord record = new WriteAttributeRecord();
        record.setAttributeIdentifier(attributeId);
        record.setAttributeDataType(ZclAttributeType.get(clusterId, attributeId).getZigBeeType().getId());
        record.setAttributeValue(value);
        command.setRecords(Collections.singletonList(record));

        command.setDestinationAddress(device.getDeviceAddress());
//        command.setDestinationAddress(device.getNetworkAddress());
  //      command.setDestinationEndpoint(device.getEndpoint());

        return unicast(command, new ZclCustomResponseMatcher());

    }

    /**
     * Reads attribute from device.
     * @param device the device
     * @param clusterId the cluster ID
     * @param attributeId the attribute ID
     * @return the command result future
     */
    public Future<CommandResult> read(final ZigBeeDevice device, final int clusterId, final int attributeId) {
        final ReadAttributesCommand command = new ReadAttributesCommand();

        command.setClusterId(clusterId);
        final AttributeIdentifier attributeIdentifier = new AttributeIdentifier();
        attributeIdentifier.setAttributeIdentifier(attributeId);
        command.setIdentifiers(Collections.singletonList(attributeIdentifier));

        command.setDestinationAddress(device.getDeviceAddress());

        return unicast(command, new ZclCustomResponseMatcher());
    }

    /**
     * Configures attribute reporting.
     * @param device the device
     * @param clusterId the cluster ID
     * @param attributeId the attribute ID
     * @param minInterval the minimum interval
     * @param maxInterval the maximum interval
     * @param reportableChange the reportable change
     * @return the command result future
     */
    public Future<CommandResult> report(final ZigBeeDevice device, final int clusterId, final int attributeId,
                                        final int minInterval, final int maxInterval, final Object reportableChange) {
        final ConfigureReportingCommand command = new ConfigureReportingCommand();

        command.setClusterId(clusterId);

        final AttributeReportingConfigurationRecord record = new AttributeReportingConfigurationRecord();
        record.setDirection(0);
        record.setAttributeIdentifier(attributeId);
        record.setAttributeDataType(ZclAttributeType.get(clusterId, attributeId).getZigBeeType().getId());
        record.setMinimumReportingInterval(minInterval);
        record.setMaximumReportingInterval(maxInterval);
        record.setReportableChange(reportableChange);
        record.setTimeoutPeriod(0);
        command.setRecords(Collections.singletonList(record));

        command.setDestinationAddress(device.getDeviceAddress());

        return unicast(command, new ZclCustomResponseMatcher());
    }

    /**
     * Permit joining.
     * @param enable enable
     */
    public void permitJoin(final boolean enable) {

        final ManagementPermitJoinRequest command = new ManagementPermitJoinRequest();

        if (enable) {
            command.setDuration(0xFF);
        } else {
            command.setDuration(0);
        }

        command.setAddressingMode(ZigBeeConstants.BROADCAST_ADDRESS);
        command.setDestinationAddress(ZToolAddress16.ZCZR_BROADCAST.get16BitValue());
        command.setTrustCenterSignificance(1);

        try {
            network.sendCommand(command);
        } catch (final ZigBeeException e) {
            throw new ZigBeeApiException("Error sending permit join command.", e);
        }
    }

    /**
     * Adds group membership to device.
     * @param device the device
     * @param groupId the group ID
     * @param groupName the group name
     * @return the command result future
     */
    public Future<CommandResult> addMembership(final ZigBeeDevice device, final int groupId, final String groupName) {
        final AddGroupCommand command = new AddGroupCommand();
        command.setGroupId(groupId);
        command.setGroupName(groupName);

        command.setDestinationAddress(device.getDeviceAddress());

        return unicast(command, new ZclCustomResponseMatcher());
    }

    /**
     * Gets group memberships from device.
     * @param device the device
     * @return the command result future
     */
    public Future<CommandResult> getGroupMemberships(final ZigBeeDevice device) {
        final GetGroupMembershipCommand command = new GetGroupMembershipCommand();

        command.setGroupCount(0);
        command.setGroupList(Collections.<Unsigned16BitInteger>emptyList());
        command.setDestinationAddress(device.getDeviceAddress());

        return unicast(command, new ZclCustomResponseMatcher());
    }

    /**
     * Views group membership from device.
     * @param device the device
     * @param groupId the group ID
     * @return the command result future
     */
    public Future<CommandResult> viewMembership(final ZigBeeDevice device, final int groupId) {
        final ViewGroupCommand command = new ViewGroupCommand();
        command.setGroupId(groupId);

        command.setDestinationAddress(device.getDeviceAddress());

        return unicast(command, new ZclCustomResponseMatcher());
    }

    /**
     * Removes group membership from device.
     * @param device the device
     * @param groupId the group ID
     * @return the command result future
     */
    public Future<CommandResult> removeMembership(final ZigBeeDevice device, final int groupId) {
        final RemoveGroupCommand command = new RemoveGroupCommand();
        command.setGroupId(groupId);

        command.setDestinationAddress(device.getDeviceAddress());

        return unicast(command, new ZclCustomResponseMatcher());
    }

    /**
     * Sends command to {@link ZigBeeAddress}.
     * @param destination the destination
     * @param command the command
     * @return the command result future
     */
    private Future<CommandResult> send(ZigBeeAddress destination, ZclCommand command) {
        command.setDestinationAddress(destination);
        if (destination.isGroup()) {
            return broadcast(command);
        } else {
            return unicast(command);
        }
    }

    /**
     * Sends command.
     * @param command the command
     * @return the command result future.
     */
    public Future<CommandResult> unicast(final Command command) {

        final CommandResponseMatcher responseMatcher;
        if (command instanceof ZclCommand) {
            responseMatcher = new ZclResponseMatcher();
        } else {
            responseMatcher = new ZdoResponseMatcher();
        }

        return unicast(command, responseMatcher);
    }

    /**
     * Sends ZCL command.
     * @param command the command
     * @param responseMatcher the response matcher.
     * @return the command result future.
     */
    private Future<CommandResult> unicast(final Command command, final CommandResponseMatcher responseMatcher) {
        synchronized (command) {
            final CommandResultFuture future = new CommandResultFuture(this);
            final CommandExecution commandExecution = new CommandExecution(
                    System.currentTimeMillis(), command, future);
            future.setCommandExecution(commandExecution);
            final CommandListener commandListener = new CommandListener() {
                @Override
                public void commandReceived(Command receivedCommand) {
                    // Ensure that received command is not processed before command is sent and
                    // hence transaction ID for the command set.
                    synchronized (command) {
                        if (responseMatcher.isMatch(command, receivedCommand)) {
                            synchronized (future) {
                                future.set(new CommandResult(receivedCommand));
                                synchronized (future) {
                                    future.notify();
                                }
                                removeCommandExecution(commandExecution);
                            }
                        }
                    }
                }
            };
            commandExecution.setCommandListener(commandListener);
            addCommandExecution(commandExecution);
            try {
                int transactionId = network.sendCommand(command);
                if (command instanceof ZclCommand) {
                    ((ZclCommand) command).setTransactionId((byte) transactionId);
                }
            } catch (final ZigBeeException e) {
                future.set(new CommandResult(e.toString()));
                removeCommandExecution(commandExecution);
            }
            return future;
        }
    }

    /**
     * Broadcasts command i.e. does not wait for response.
     * @param command the command
     * @return the command result future.
     */
    private Future<CommandResult> broadcast(final Command command) {
        synchronized (command) {
            final CommandResultFuture future = new CommandResultFuture(this);

            try {
                network.sendCommand(command);
                future.set(new CommandResult(new BroadcastResponse()));
            } catch (final ZigBeeException e) {
                future.set(new CommandResult(e.toString()));
            }

            return future;
        }
    }

    /**
     * Adds command listener and removes expired command listeners.
     *
     * @param commandExecution the command execution
     */
    private void addCommandExecution(final CommandExecution commandExecution) {
        synchronized (commandExecutions) {
            final List<CommandExecution> expiredCommandExecutions =
                    new ArrayList<CommandExecution>();
            for (final CommandExecution existingCommandExecution : commandExecutions) {
                if (System.currentTimeMillis() - existingCommandExecution.getStartTime() > 8000) {
                    expiredCommandExecutions.add(existingCommandExecution);
                }
            }
            for (final CommandExecution expiredCommandExecution : expiredCommandExecutions) {
                ((CommandResultFuture) expiredCommandExecution.getFuture()).set(new CommandResult());
                removeCommandExecution(expiredCommandExecution);
            }
            commandExecutions.add(commandExecution);
            network.addCommandListener(commandExecution.getCommandListener());
        }
    }

    /**
     * Removes command execution.
     * @param expiredCommandExecution the command execution
     */
    protected void removeCommandExecution(CommandExecution expiredCommandExecution) {
        commandExecutions.remove(expiredCommandExecution);
        network.removeCommandListener(expiredCommandExecution.getCommandListener());
        synchronized (expiredCommandExecution.getFuture()) {
            expiredCommandExecution.getFuture().notify();
        }
    }

}
