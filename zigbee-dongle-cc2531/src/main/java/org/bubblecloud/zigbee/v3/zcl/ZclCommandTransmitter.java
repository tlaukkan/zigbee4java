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
package org.bubblecloud.zigbee.v3.zcl;

import org.bubblecloud.zigbee.api.cluster.impl.core.AbstractCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLFrame;
import org.bubblecloud.zigbee.network.ApplicationFrameworkMessageListener;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.packet.ResponseStatus;
import org.bubblecloud.zigbee.network.packet.af.*;
import org.bubblecloud.zigbee.v3.CommandListener;
import org.bubblecloud.zigbee.v3.ZigBeeAddress;
import org.bubblecloud.zigbee.v3.ZigBeeDeviceAddress;
import org.bubblecloud.zigbee.network.impl.*;
import org.bubblecloud.zigbee.v3.ZigBeeException;
import org.bubblecloud.zigbee.v3.model.Status;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.util.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ZCL command transmitter.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclCommandTransmitter implements
        ApplicationFrameworkMessageListener {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ZclCommandTransmitter.class);
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
     * 
     * @param networkManager
     *            the network manager
     */
    public ZclCommandTransmitter(final ZigBeeNetworkManagerImpl networkManager) {
        this.networkManager = networkManager;
    }

    /**
     * Adds command listener.
     * 
     * @param listener
     *            the command listener
     */
    public void addCommandListener(final CommandListener listener) {
        final List<CommandListener> modifiedCommandListeners = new ArrayList<CommandListener>(
                commandListeners);
        modifiedCommandListeners.add(listener);
        commandListeners = Collections
                .unmodifiableList(modifiedCommandListeners);
    }

    /**
     * Removes command listener.
     * 
     * @param listener
     *            the command listener
     */
    public void removeCommandListener(final CommandListener listener) {
        final List<CommandListener> modifiedCommandListeners = new ArrayList<CommandListener>(
                commandListeners);
        modifiedCommandListeners.remove(listener);
        commandListeners = Collections
                .unmodifiableList(modifiedCommandListeners);
    }

    @Override
    public boolean notify(final AF_INCOMING_MSG clusterMessage) {

        final ZCLFrame frame = new ZCLFrame(new ClusterMessageImpl(
                clusterMessage.getData(), clusterMessage.getClusterId()));

        final boolean isClientServerDirection = frame.getHeader()
                .getFramecontrol().isClientServerDirection();
        final boolean isClusterSpecificCommand = frame.getHeader()
                .getFramecontrol().isClusterSpecificCommand();
        final boolean isManufacturerExtension = frame.getHeader()
                .getFramecontrol().isManufacturerExtension();
        final boolean isDefaultResponseEnabled = frame.getHeader()
                .getFramecontrol().isDefaultResponseEnabled();

        int clusterId = clusterMessage.getClusterId();
        final byte commandId = frame.getHeader().getCommandId();
        final byte transactionId = frame.getHeader().getTransactionId();

        final byte[] commandPayload = frame.getPayload();

        LOGGER.debug("Received command: [ clusterId: " + clusterId
                + " commandId: " + commandId + " specific: "
                + isClusterSpecificCommand + " extension: "
                + isManufacturerExtension + " ZCL Header: "
                + ByteUtils.toBase16(frame.getHeader().toByte())
                + ", ZCL Payload: " + ByteUtils.toBase16(frame.getPayload())
                + "]");

        if (isManufacturerExtension) {
            return false;
        }
        
        final int sourceAddress = clusterMessage.getSrcAddr();
        final short sourceEndpoint = clusterMessage.getSrcEndpoint();
        final int destinationAddress = 0;
        final short destinationEndpoint = clusterMessage.getDstEndpoint();

        final int profileId = ApplicationFrameworkLayer.getAFLayer(
                networkManager).getSenderEndpointProfileId(destinationEndpoint,
                clusterMessage.getClusterId());
        final ZclCommandMessage commandMessage = new ZclCommandMessage();
        commandMessage.setClusterId(clusterId);
        commandMessage.setSourceAddress(new ZigBeeDeviceAddress(sourceAddress,
                sourceEndpoint & 0xffff));
        // commandMessage.setSourceEndpoint(sourceEnpoint & (0xFFFF));
        commandMessage.setDestinationAddress(new ZigBeeDeviceAddress(
                destinationAddress, destinationEndpoint & 0xffff));
        // commandMessage.setDestinationEndpoint(destinationEndpoint &
        // (0xFFFF));
        commandMessage.setTransactionId(transactionId);

        ZclCommandType command = null;
        if (isClusterSpecificCommand) {
            LOGGER.debug("Received cluster specific command: [ clusterId: "
                    + clusterId + " commandId: " + commandId + " ZCL Header: "
                    + ByteUtils.toBase16(frame.getHeader().toByte())
                    + ", ZCL Payload: "
                    + ByteUtils.toBase16(frame.getPayload()) + "]");

            for (final ZclCommandType candidate : ZclCommandType.values()) {
                if (candidate.getClusterType().getProfileType().getId() == profileId
                        && candidate.getClusterType().getId() == clusterId
                        && candidate.getId() == (commandId & (0xFF))
                        && candidate.isReceived() == isClientServerDirection) {
                    command = candidate;
                    break;
                }
            }
        } else {
            LOGGER.debug("Received general command: [ clusterId: " + clusterId
                    + " commandId: " + commandId + " ZCL Header: "
                    + ByteUtils.toBase16(frame.getHeader().toByte())
                    + ", ZCL Payload: "
                    + ByteUtils.toBase16(frame.getPayload()) + "]");

            for (final ZclCommandType candidate : ZclCommandType.values()) {
                if (candidate.getClusterType().getProfileType().getId() == profileId
                        && candidate.isGeneric()
                        && candidate.getId() == (commandId & (0xFF))) {
                    command = candidate;
                    break;
                }
            }
        }

        if (command == null) {
            return false;
        }

        commandMessage.setType(command);
        ZclCommandProtocol.deserializePayload(commandPayload, commandMessage);

        LOGGER.debug("<<< " + commandMessage.toString());

        for (final CommandListener commandListener : commandListeners) {
            commandListener.commandReceived(ZclUtil.toCommand(commandMessage));
        }

        return true;
    }

    /**
     * Sends command message.
     * 
     * @param commandMessage
     *            the command message
     * @return transaction ID
     * @throws ZigBeeNetworkManagerException
     */
    public int sendCommand(final ZclCommandMessage commandMessage)
            throws ZigBeeException {
        synchronized (networkManager) {
            final ApplicationFrameworkLayer af = ApplicationFrameworkLayer
                    .getAFLayer(networkManager);

            final int clusterId;
            if (commandMessage.getType().isGeneric()) {
                clusterId = commandMessage.getClusterId();
            } else {
                clusterId = commandMessage.getType().getClusterType().getId();
            }
            commandMessage.setClusterId(clusterId);

            // TODO load properly dongle source address
            ZigBeeAddress sourceAddress = new ZigBeeDeviceAddress(0,
                    af.getSendingEndpoint(commandMessage.getType()
                            .getClusterType().getProfileType().getId(),
                            clusterId));
            commandMessage.setSourceAddress(sourceAddress);

            final byte[] payload = ZclCommandProtocol
                    .serializePayload(commandMessage);

            final AbstractCommand cmd = new AbstractCommand(
                    (byte) commandMessage.getType().getId(), null,
                    commandMessage.getType().isGeneric() ? true
                            : commandMessage.getType().isReceived(),
                    !commandMessage.getType().isGeneric());
            cmd.setPayload(payload);
            final ZCLFrame zclFrame = new ZCLFrame(cmd, true);
            if (commandMessage.getTransactionId() != null) {
                zclFrame.getHeader().setTransactionId(
                        commandMessage.getTransactionId());
            }
            final ClusterMessage input = new org.bubblecloud.zigbee.api.cluster.impl.ClusterMessageImpl(
                    (short) clusterId, zclFrame);

            final short sender = af.getSendingEndpoint(commandMessage.getType()
                    .getClusterType().getProfileType().getId(), clusterId);
            final byte afTransactionId = af.getNextTransactionId(sender);
            final byte[] msg = input.getClusterMsg();

            if (commandMessage.getDestinationGroupId() == null) {
                ZigBeeDeviceAddress destination = (ZigBeeDeviceAddress) commandMessage
                        .getDestinationAddress();
                final AF_DATA_CONFIRM response = networkManager
                        .sendAFDataRequest(new AF_DATA_REQUEST(destination
                                .getAddress(), (short) destination
                                .getEndpoint(), sender, input.getId(),
                                afTransactionId, (byte) (0) /* options */,
                                (byte) 0 /* radius */, msg));

                commandMessage.setTransactionId(zclFrame.getHeader()
                        .getTransactionId());
                LOGGER.debug(">>> " + commandMessage.toString());

                if (response == null) {
                    throw new ZigBeeException(
                            "Unable to send cluster on the ZigBee network due to general error.");
                }

                if (response.getStatus() != 0) {
                    throw new ZigBeeException(
                            "Unable to send cluster on the ZigBee network due to: "
                                    + ResponseStatus.getStatus(response
                                            .getStatus())
                                    + " "
                                    + (response.getErrorMsg() != null ? " - "
                                            + response.getErrorMsg() : "")
                                    + ")");
                }

                return commandMessage.getTransactionId();

            } else {
                final AfDataSrspExt response = networkManager
                        .sendAFDataRequestExt(new AfDataRequestExt(
                                commandMessage.getDestinationGroupId(), sender,
                                input.getId(), afTransactionId,
                                (byte) (0) /* options */,
                                (byte) 0 /* radius */, msg));
                commandMessage.setTransactionId(zclFrame.getHeader()
                        .getTransactionId());
                LOGGER.debug(">>> " + commandMessage.toString());

                if (response.getStatus() != 0) {
                    throw new ZigBeeException(
                            "Unable to send cluster on the ZigBee network due to: "
                                    + ResponseStatus.getStatus(response
                                            .getStatus()));
                }

                return commandMessage.getTransactionId();
            }

        }
    }

}
