package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zdo.command.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ZigBee network discoverer is used to discover devices in the network.
 * This class is thread safe.
 */
public class ZigBeeNetworkDiscoverer implements CommandListener {
    /**
     * The logger.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ZigBeeNetworkDiscoverer.class);
    /**
     * Minimum time before information can be queried again for same network address or endpoint.
     */
    private static final int MINIMUM_REQUERY_TIME_MILLIS = 10000;
    /**
     * The ZigBee network state.
     */
    private ZigBeeNetworkState networkState;
    /**
     * The ZigBee command interface.
     */
    private ZigBeeNetwork commandInterface;
    /**
     * The received IEEE addresses.
     */
    private Map<Integer, IeeeAddressResponse> ieeeAddresses =
            Collections.synchronizedMap(new HashMap<Integer, IeeeAddressResponse>());
    /**
     * The received node descriptors.
     */
    private Map<Integer, NodeDescriptorResponse> nodeDescriptors =
            Collections.synchronizedMap(new HashMap<Integer, NodeDescriptorResponse>());
    /**
     * Map of IEEE address request times.
     */
    final Map<Integer, Long> ieeeAddressRequestTimes =
            Collections.synchronizedMap(new HashMap<Integer, Long>());
    /**
     * Map of node descriptor request times.
     */
    final Map<Integer, Long> nodeDescriptorRequestTimes =
            Collections.synchronizedMap(new HashMap<Integer, Long>());
    /**
     * Map of active endpoints request times.
     */
    final Map<Integer, Long> activeEndpointsRequestTimes =
            Collections.synchronizedMap(new HashMap<Integer, Long>());
    /**
     * Map of endpoint descriptor request times.
     */
    final Map<String, Long> endpointDescriptorRequestTimes =
            Collections.synchronizedMap(new HashMap<String, Long>());

    /**
     * Discovers ZigBee network state.
     * @param networkState the network state
     * @param commandInterface the command interface
     */
    public ZigBeeNetworkDiscoverer(final ZigBeeNetworkState networkState,
                                   final ZigBeeNetwork commandInterface) {
        this.networkState = networkState;
        this.commandInterface = commandInterface;
    }

    /**
     * Starts up ZigBee network discoverer.
     */
    public void startup() {
        commandInterface.addCommandListener(this);
        // Start discovery from root node.
        requestNodeIeeeAddressAndAssociatedNodes(0);
    }

    /**
     * Shuts down ZigBee network discoverer.
     */
    public void shutdown() {
        commandInterface.removeCommandListener(this);
    }

    @Override
    public void commandReceived(final Command command) {
        LOGGER.info("Received: " + command);

        // 0. ZCL command received from remote node. Request IEEE address if it is not yet known.
        if (command instanceof ZclCommand) {
            final ZclCommand zclCommand = (ZclCommand) command;
            if (networkState.getDevice(zclCommand.getSourceAddress(), zclCommand.getSourceEnpoint()) == null) {
                requestNodeIeeeAddressAndAssociatedNodes(zclCommand.getSourceAddress());
            }
        }

        // 0. Node has been announced.
        if (command instanceof DeviceAnnounce) {
            final DeviceAnnounce deviceAnnounce = (DeviceAnnounce) command;
            requestNodeIeeeAddressAndAssociatedNodes(deviceAnnounce.getNetworkAddress());
        }

        // 1. Node IEEE address and associated nodes have been received.
        if (command instanceof IeeeAddressResponse) {
            final IeeeAddressResponse ieeeAddressResponse = (IeeeAddressResponse) command;

            if (ieeeAddressResponse.getStatus() == 0) {
                ieeeAddresses.put(ieeeAddressResponse.getNetworkAddress(), ieeeAddressResponse);
                describeNode(ieeeAddressResponse.getNetworkAddress());

                for (final int networkAddress : ieeeAddressResponse.associatedDeviceList) {
                    requestNodeIeeeAddressAndAssociatedNodes(networkAddress);
                }
            } else {
                LOGGER.warn(ieeeAddressResponse.toString());
            }
        }

        // 2. Node has been described.
        if (command instanceof NodeDescriptorResponse) {
            final NodeDescriptorResponse nodeDescriptorResponse = (NodeDescriptorResponse) command;

            if (nodeDescriptorResponse.getStatus() == 0) {
                nodeDescriptors.put(nodeDescriptorResponse.getNetworkAddress(), nodeDescriptorResponse);
                requestNodeEndpoints(nodeDescriptorResponse.getNetworkAddress());
            } else {
                LOGGER.warn(nodeDescriptorResponse.toString());
            }
        }

        // 3. Endpoints have been received.
        if (command instanceof ActiveEndpointsResponse) {
            final ActiveEndpointsResponse activeEndpointsResponse = (ActiveEndpointsResponse) command;
            if (activeEndpointsResponse.getStatus() == 0) {
                for (final int endpoint : activeEndpointsResponse.getActiveEndpoints()) {
                    int networkAddress = activeEndpointsResponse.getNetworkAddress();
                    describeEndpoint(networkAddress, endpoint);
                }
            } else {
                LOGGER.warn(activeEndpointsResponse.toString());
            }
        }

        // 4. Endpoint is described.
        if (command instanceof SimpleDescriptorResponse) {
            final SimpleDescriptorResponse simpleDescriptorResponse = (SimpleDescriptorResponse) command;
            if (simpleDescriptorResponse.getStatus() == 0) {
                final int networkAddress = simpleDescriptorResponse.getNetworkAddress();
                final IeeeAddressResponse ieeeAddressResponse = ieeeAddresses.get(networkAddress);
                final NodeDescriptorResponse nodeDescriptorResponse = nodeDescriptors.get(networkAddress);

                if (ieeeAddressResponse == null || nodeDescriptorResponse == null) {
                    return;
                }

                addOrUpdateDevice(ieeeAddressResponse, nodeDescriptorResponse, simpleDescriptorResponse);
            } else {
                LOGGER.warn(simpleDescriptorResponse.toString());
            }
        }

        // 5. Endpoint user descriptor is received.
        if (command instanceof UserDescriptorResponse) {
            final UserDescriptorResponse userDescriptorResponse = (UserDescriptorResponse) command;
            LOGGER.info("Received user descriptor response: " + userDescriptorResponse);
        }
    }


    /**
     * Requests node IEEE address and associated nodes.
     * @param networkAddress the network address
     */
    private void requestNodeIeeeAddressAndAssociatedNodes(final int networkAddress) {
        if (ieeeAddressRequestTimes.get(networkAddress) != null &&
                System.currentTimeMillis() - ieeeAddressRequestTimes.get(networkAddress) < MINIMUM_REQUERY_TIME_MILLIS) {
            return;
        }
        ieeeAddressRequestTimes.put(networkAddress, System.currentTimeMillis());

        try {
            final IeeeAddressRequest ieeeAddressRequest = new IeeeAddressRequest(networkAddress, 1, 0);
            commandInterface.sendCommand(ieeeAddressRequest);
        } catch (ZigBeeException e) {
            LOGGER.error("Error sending discovery command.", e);
        }
    }

    /**
     * Describe node at given network address.
     * @param networkAddress the network address
     */
    private void describeNode(final int networkAddress) {
        if (nodeDescriptorRequestTimes.get(networkAddress) != null &&
                System.currentTimeMillis() - nodeDescriptorRequestTimes.get(networkAddress) < MINIMUM_REQUERY_TIME_MILLIS) {
            return;
        }
        nodeDescriptorRequestTimes.put(networkAddress, System.currentTimeMillis());

        try {
            final NodeDescriptorRequest nodeDescriptorRequest = new NodeDescriptorRequest(networkAddress, networkAddress);
            commandInterface.sendCommand(nodeDescriptorRequest);
        } catch (ZigBeeException e) {
            LOGGER.error("Error sending discovery command.", e);
        }
    }

    /**
     * Discover node endpoints.
     * @param networkAddress the network address
     */
    private void requestNodeEndpoints(final int networkAddress) {
        if (activeEndpointsRequestTimes.get(networkAddress) != null &&
                System.currentTimeMillis() - activeEndpointsRequestTimes.get(networkAddress) < MINIMUM_REQUERY_TIME_MILLIS) {
            return;
        }
        activeEndpointsRequestTimes.put(networkAddress, System.currentTimeMillis());

        try {
            final ActiveEndpointsRequest activeEndpointsRequest = new ActiveEndpointsRequest();
            activeEndpointsRequest.setDestinationAddress(networkAddress);
            activeEndpointsRequest.setNetworkAddressOfInterest(networkAddress);
            commandInterface.sendCommand(activeEndpointsRequest);
        } catch (ZigBeeException e) {
            LOGGER.error("Error sending discovery command.", e);
        }
    }

    /**
     * Describe node endpoint
     * @param networkAddress the network address
     * @param endpoint the endpoint
     */
    private void describeEndpoint(final int networkAddress, final int endpoint) {
        final String deviceIdentifier = networkAddress + "/" + endpoint;
        if (endpointDescriptorRequestTimes.get(deviceIdentifier) != null &&
                System.currentTimeMillis() - endpointDescriptorRequestTimes.get(deviceIdentifier) < MINIMUM_REQUERY_TIME_MILLIS) {
            return;
        }
        endpointDescriptorRequestTimes.put(deviceIdentifier, System.currentTimeMillis());
        try {
            final SimpleDescriptorRequest request = new SimpleDescriptorRequest();
            request.setDestinationAddress(networkAddress);
            request.setEndpoint(endpoint);
            commandInterface.sendCommand(request);
        } catch (ZigBeeException e) {
            LOGGER.error("Error sending discovery command.", e);
        }
    }

    /**
     * Adds device to network state.
     * @param ieeeAddressResponse the IEEE address response
     * @param nodeDescriptorResponse the node descriptor response
     * @param simpleDescriptorResponse the simple descriptor response
     */
    private void addOrUpdateDevice(final IeeeAddressResponse ieeeAddressResponse,
                                   final NodeDescriptorResponse nodeDescriptorResponse,
                                   final SimpleDescriptorResponse simpleDescriptorResponse) {
        final ZigBeeDevice device;
        final boolean newDevice = networkState.getDevice(ieeeAddressResponse.getNetworkAddress(),
                simpleDescriptorResponse.getEndpoint()) == null;

        if (newDevice) {
            device = new ZigBeeDevice();
        } else {
            device = networkState.getDevice(ieeeAddressResponse.getNetworkAddress(),
                    simpleDescriptorResponse.getEndpoint());
        }

        device.setNetworkAddress(ieeeAddressResponse.getNetworkAddress());
        device.setIeeeAddress(ieeeAddressResponse.getIeeeAddress());
        device.setEndpoint(simpleDescriptorResponse.getEndpoint());
        device.setProfileId(simpleDescriptorResponse.getProfileId());
        device.setDeviceId(simpleDescriptorResponse.getDeviceId());
        device.setManufacturerCode(nodeDescriptorResponse.getManufacturerCode());
        device.setDeviceVersion(simpleDescriptorResponse.getDeviceVersion());
        device.setDeviceType(nodeDescriptorResponse.getLogicalType());
        device.setInputClusterIds(simpleDescriptorResponse.getInputClusters());
        device.setOutputClusterIds(simpleDescriptorResponse.getOutputClusters());

        if (newDevice) {
            networkState.addDevice(device);
        } else {
            networkState.updateDevice(device);
        }

        /*
        final UserDescriptorRequest userDescriptorRequest = new UserDescriptorRequest(
                device.getNetworkAddress(), device.getNetworkAddress());
        try {
            commandInterface.sendCommand(userDescriptorRequest);
        } catch (final ZigBeeException e) {
            LOGGER.error("Error sending discovery command.", e);
        }*/
    }
}
