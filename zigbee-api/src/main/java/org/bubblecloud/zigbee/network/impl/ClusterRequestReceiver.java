package org.bubblecloud.zigbee.network.impl;

import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLFrame;
import org.bubblecloud.zigbee.network.ApplicationFrameworkMessageListener;
import org.bubblecloud.zigbee.network.impl.protocol.ZclCommandMessage;
import org.bubblecloud.zigbee.network.impl.protocol.ZclCommandProtocol;
import org.bubblecloud.zigbee.network.model.IEEEAddress;
import org.bubblecloud.zigbee.network.packet.af.AF_INCOMING_MSG;
import org.bubblecloud.zigbee.network.port.ZigBeeNetworkManagerImpl;
import org.bubblecloud.zigbee.util.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Receiver of cluster requests.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ClusterRequestReceiver implements ApplicationFrameworkMessageListener {
    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ZigBeeEndpointImpl.class);

    static {
        ZclCommandProtocol.getMessageProtocol();
    }

    private ZigBeeNetworkManagerImpl networkManager;

    public ClusterRequestReceiver(ZigBeeNetworkManagerImpl networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public boolean notify(final AF_INCOMING_MSG clusterMessage) {

        ZCLFrame frame = new ZCLFrame(new ClusterMessageImpl(clusterMessage.getData(), clusterMessage.getClusterId()));
        final boolean isClientServerDirection = frame.getHeader().getFramecontrol().isClientServerDirection();
        final boolean isClusterSpecificCommand = frame.getHeader().getFramecontrol().isClusterSpecificCommand();
        final boolean isManufacturerExtension = frame.getHeader().getFramecontrol().isManufacturerExtension();
        final boolean isDefaultResponseEnabled = frame.getHeader().getFramecontrol().isDefaultResponseEnabled();

        int sourceAddress = clusterMessage.getSrcAddr();
        short sourceEnpoint = clusterMessage.getSrcEndpoint();
        int destinationAddress = ApplicationFrameworkLayer.getAFLayer(networkManager).getZigBeeNetwork().getNode(IEEEAddress.toColonNotation(networkManager.getIeeeAddress())).getNetworkAddress();
        short destinationEndpoint = clusterMessage.getDstEndpoint();

        int profileId = ApplicationFrameworkLayer.getAFLayer(networkManager).getSenderEndpointProfileId(destinationEndpoint, clusterMessage.getClusterId());
        int clusterId = clusterMessage.getClusterId();
        byte commandId = frame.getHeader().getCommandId();
        byte transactionId = frame.getHeader().getTransactionId();

        byte[] commandPayload = frame.getPayload();


        if (isClusterSpecificCommand && !isManufacturerExtension) {
            logger.trace("Received cluster specific command: [ clusterId: " + clusterId + " commmandId: " + commandId + " ZCL Header: " + ByteUtils.toBase16(frame.getHeader().toByte())
                    + ", ZCL Payload: " + ByteUtils.toBase16(frame.getPayload())
                    + "]");

            final ZclCommandMessage message = new ZclCommandMessage();

            message.setSourceAddress(sourceAddress);
            message.setSourceEnpoint(sourceEnpoint);
            message.setDestinationAddress(destinationAddress);
            message.setDestinationEndpoint(destinationEndpoint);

            message.setProfileId(profileId);
            message.setClusterId(clusterId);
            message.setCommandId(commandId);
            message.setTransactionId(transactionId);

            ZclCommandProtocol.deserializePayloadToFields(profileId, clusterId, commandId, commandPayload, message);

            if (message != null) {
                logger.debug("<<< " + message.toString());
            }
        }

        return true;
    }


}
