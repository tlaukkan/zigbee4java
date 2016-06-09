/**
 * Copyright 2016 Tommi S.E. Laukkanen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bubblecloud.zigbee.network.zdo;

import org.bubblecloud.zigbee.network.AsynchronousCommandListener;
import org.bubblecloud.zigbee.network.impl.*;

import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.network.packet.zdo.*;
import org.bubblecloud.zigbee.network.zdo.command.*;
import org.bubblecloud.zigbee.simple.CommandListener;
import org.bubblecloud.zigbee.util.Integers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ZDO command transmitter.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZdoCommandTransmitter implements AsynchronousCommandListener {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZdoCommandTransmitter.class);
    /**
     * The network manager for transmitting data to and from network layer.
     */
    private ZigBeeNetworkManagerImpl networkManager;
    /**
     * The command listeners.
     */
    private List<CommandListener> commandListeners = new ArrayList<CommandListener>();

    /**
     * Constructor for setting network manager.
     * @param networkManager the network manager
     */
    public ZdoCommandTransmitter(final ZigBeeNetworkManagerImpl networkManager) {
        this.networkManager = networkManager;
    }

    /**
     * Adds command listener.
     * @param listener the command listener
     */
    public void addCommandListener(final CommandListener listener) {
        final List<CommandListener> modifiedCommandListeners = new ArrayList<CommandListener>(commandListeners);
        modifiedCommandListeners.add(listener);
        commandListeners = Collections.unmodifiableList(modifiedCommandListeners);
    }

    /**
     * Removes command listener.
     * @param listener the command listener
     */
    public void removeCommandListener(final CommandListener listener) {
        final List<CommandListener> modifiedCommandListeners = new ArrayList<CommandListener>(commandListeners);
        modifiedCommandListeners.remove(listener);
        commandListeners = Collections.unmodifiableList(modifiedCommandListeners);
    }

    /**
     * Sends command message.
     * @param command the command
     * @throws ZigBeeNetworkManagerException
     */
    public void sendCommand(final ZdoCommand command) throws ZigBeeException {
        synchronized (networkManager) {
            if (command instanceof ActiveEndpointsRequest) {
                final ActiveEndpointsRequest activeEndpointsRequest = (ActiveEndpointsRequest) command;
                networkManager.sendCommand(new ZDO_ACTIVE_EP_REQ(
                        getZToolAddress16(activeEndpointsRequest.getDestinationAddress()),
                        getZToolAddress16(activeEndpointsRequest.getNetworkAddressOfInterest())));
            }
            if (command instanceof SimpleDescriptorRequest) {
                final SimpleDescriptorRequest simpleDescriptorRequest = (SimpleDescriptorRequest) command;
                networkManager.sendCommand(new ZDO_SIMPLE_DESC_REQ(
                        (short) simpleDescriptorRequest.getDestinationAddress(),
                        (short) simpleDescriptorRequest.getEndpoint()));
            }
        }
    }

    private ZToolAddress16 getZToolAddress16(int networkAddress) {
        return new ZToolAddress16(
                            Integers.getByteAsInteger(networkAddress, 1),
                            Integers.getByteAsInteger(networkAddress, 0)
                    );
    }

    @Override
    public void receivedAsynchronousCommand(ZToolPacket packet) {
        if (packet.isError()) return;

        if (packet.getCMD().get16BitValue() == ZToolCMD.ZDO_SIMPLE_DESC_RSP) {
            final ZDO_SIMPLE_DESC_RSP message = (ZDO_SIMPLE_DESC_RSP) packet;

            final SimpleDescriptorResponse command = new SimpleDescriptorResponse();
            command.setSourceAddress(message.SrcAddress.get16BitValue());
            command.setStatus(message.Status);
            command.setProfileId(message.getProfileId());
            command.setDeviceId(message.getDeviceId());
            command.setDeviceVersion(message.getDeviceVersion());
            command.setNetworkAddress(message.nwkAddr.get16BitValue());
            command.setEndpoint(message.getEndPoint());
            final short[] inputClusterShorts = message.getInputClustersList();
            final int[] inputClusters = new int[message.getInputClustersCount()];
            for (int i = 0; i < inputClusters.length; i++) {
                inputClusters[i] = inputClusterShorts[i];
            }
            command.setInputClusters(inputClusters);
            final short[] outputClusterShorts = message.getOutputClustersList();
            final int[] outputClusters = new int[message.getOutputClustersCount()];
            for (int i = 0; i < outputClusters.length; i++) {
                outputClusters[i] = outputClusterShorts[i];
            }
            command.setOutputClusters(outputClusters);

            notifyCommandReceived(command);

            return;
        }

        if (packet.getCMD().get16BitValue() == ZToolCMD.ZDO_ACTIVE_EP_RSP) {
            final ZDO_ACTIVE_EP_RSP message = (ZDO_ACTIVE_EP_RSP) packet;

            final ActiveEndpointsResponse command = new ActiveEndpointsResponse();
            command.setSourceAddress(message.SrcAddress.get16BitValue());
            command.setStatus(message.Status);
            command.setNetworkAddress(message.nwkAddr.get16BitValue());

            final short[] activeEndPointList = message.getActiveEndPointList();
            final int[] activeEndPoints = new int[activeEndPointList.length];
            for (int i = 0; i < activeEndPoints.length; i++) {
                activeEndPoints[i] = activeEndPointList[i];
            }
            command.setActiveEndpoints(activeEndPoints);

            notifyCommandReceived(command);

            return;
        }

        if (packet.getCMD().get16BitValue() == ZToolCMD.ZDO_END_DEVICE_ANNCE_IND) {
            final ZDO_END_DEVICE_ANNCE_IND message = (ZDO_END_DEVICE_ANNCE_IND) packet;

            final DeviceAnnounce command = new DeviceAnnounce(
                    message.SrcAddr.get16BitValue(),
                    message.IEEEAddr.getLong(),
                    message.NwkAddr.get16BitValue(),
                    message.Capabilities);

            notifyCommandReceived(command);

            return;
        }

    }

    /**
     * Notifies ZDO command listeners that ZDO command was received.
     * @param command the ZDO command
     */
    private void notifyCommandReceived(final ZdoCommand command) {
        for (final CommandListener commandListener : commandListeners) {
            commandListener.commandReceived(command);
        }
    }
}
