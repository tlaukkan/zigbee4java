/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies
   of the Italian National Research Council


   See the NOTICE file distributed with this work for additional
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.bubblecloud.zigbee.network.impl;

import org.bubblecloud.zigbee.network.*;
import org.bubblecloud.zigbee.network.packet.ResponseStatus;
import org.bubblecloud.zigbee.network.packet.af.AF_DATA_CONFIRM;
import org.bubblecloud.zigbee.network.packet.af.AF_DATA_REQUEST;
import org.bubblecloud.zigbee.network.packet.af.AF_INCOMING_MSG;
import org.bubblecloud.zigbee.network.packet.zdo.*;
import org.bubblecloud.zigbee.util.Integers;
import org.bubblecloud.zigbee.util.ThreadUtils;
import org.bubblecloud.zigbee.network.model.IEEEAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ZigBeeEndpointImpl implements ZigBeeEndpoint, ApplicationFrameworkMessageListener, ApplicationFrameworkMessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(ZigBeeEndpointImpl.class);

    private static long TIMEOUT;
    private static final long DEFAULT_TIMEOUT = 5000;

    private final int[] inputs;
    private final int[] outputs;

    private final int deviceId;
    private final int profileId;
    private final byte deviceVersion;

    private final ZigBeeNode node;
    private final byte endPoint;

    //private final Properties properties = new Properties();
    private final ZigBeeNetworkManager networkManager;

    private final HashSet<Integer> boundCluster = new HashSet<Integer>();
    private final HashSet<ClusterListener> listeners = new HashSet<ClusterListener>();
    private final HashSet<ApplicationFrameworkMessageConsumer> consumers = new HashSet<ApplicationFrameworkMessageConsumer>();
    private String endpointId = null;

    public ZigBeeEndpointImpl(final ZigBeeNetworkManager zigBeeNetworkManager, final ZigBeeNode n, byte ep) throws ZigBeeNetworkManagerException {
        if (zigBeeNetworkManager == null || n == null) {
            logger.error("Creating {} with some nulls parameters {}", new Object[]{ZigBeeEndpoint.class, zigBeeNetworkManager, n, ep});
            throw new NullPointerException("Cannot create a device with a null ZigBeeNetworkManager or a null ZigBeeNode");
        }
        networkManager = zigBeeNetworkManager;
        endPoint = ep;

        final ZDO_SIMPLE_DESC_RSP result = doRetrieveSimpleDescription(n);
        short[] ins = result.getInputClustersList();
        inputs = new int[ins.length];
        for (int i = 0; i < ins.length; i++) {
            inputs[i] = ins[i];
        }
        Arrays.sort(inputs);
        short[] outs = result.getOutputClustersList();
        outputs = new int[outs.length];
        for (int i = 0; i < outs.length; i++) {
            outputs[i] = outs[i];
        }
        Arrays.sort(outputs);

        deviceId = (int) result.getDeviceId() & 0xFFFF;
        profileId = (int) result.getProfileId() & 0xFFFF;
        deviceVersion = result.getDeviceVersion();

        node = n;

        final StringBuffer sb_uuid = new StringBuffer()
                .append(node.getIEEEAddress())
                .append("/")
                .append(endPoint);
        endpointId = sb_uuid.toString();

        TIMEOUT = DEFAULT_TIMEOUT;
    }

    private ZDO_SIMPLE_DESC_RSP doRetrieveSimpleDescription(ZigBeeNode n) throws ZigBeeNetworkManagerException {
        //TODO Move into ZigBeeNetworkManager?!?!?
        final int nwk = n.getNetworkAddress();
        int i = 0;
        ZDO_SIMPLE_DESC_RSP result = null;

        while (i < 3) {
            logger.debug("Inspecting node {} / end point {}.", n, endPoint);

            result = networkManager.sendZDOSimpleDescriptionRequest(
                    new ZDO_SIMPLE_DESC_REQ((short) nwk, endPoint)
            );
            if (result == null) {
                //long waiting = (long) (Math.random() * (double) Activator.getCurrentConfiguration().getMessageRetryDelay())
                final long waiting = 1000;
                ThreadUtils.waitNonPreemptive(waiting);
                i++;
                logger.debug(
                        "Inspecting ZigBee EndPoint <{},{}> failed during it {}-th attempts. " +
                                "Waiting for {}ms before retrying",
                        new Object[]{nwk, endPoint, i, waiting}
                );

            } else {
                break;
            }
        }

        if (result == null) {
            logger.error(
                    "Unable to receive a ZDO_SIMPLE_DESC_RSP for endpoint {} on node {}",
                    nwk, endPoint
            );
            throw new ZigBeeNetworkManagerException("Unable to receive a ZDO_SIMPLE_DESC_RSP from endpoint");
        }

        return result;
    }

    public int getDeviceTypeId() {
        return deviceId;
    }

    public short getDeviceVersion() {
        return deviceVersion;
    }

    public String getEndpointId() {
        return endpointId;
    }

    @Override
    public int getNetworkAddress() {
        return node.getNetworkAddress();
    }

    @Override
    public String getIEEEAddress() {
        return node.getIEEEAddress();
    }

    public short getEndPointAddress() {
        return endPoint;
    }

    public int[] getInputClusters() {
        return inputs;
    }

    public int[] getOutputClusters() {
        return outputs;
    }

    public int getProfileId() {
        return profileId;
    }

    public ZigBeeNode getNode() {
        return node;
    }

    public void send(ClusterMessage input) throws ZigBeeNetworkManagerException {
        synchronized (networkManager) {
            final ApplicationFrameworkLayer af = ApplicationFrameworkLayer.getAFLayer(networkManager);
            final byte sender = af.getSendingEndpoint(this, input);
            final byte transaction = af.getNextTransactionId(sender);
            final byte[] msg = input.getClusterMsg();

            //TODO Create radius and options according to the current configuration
            AF_DATA_CONFIRM response = networkManager.sendAFDataRequest(new AF_DATA_REQUEST(
                    (short) node.getNetworkAddress(), (byte) endPoint, sender, input.getId(),
                    transaction, (byte) 0 /*options*/, (byte) 0 /*radius*/, msg
            ));

            if (response == null) {
                throw new ZigBeeNetworkManagerException("Unable to send cluster on the ZigBee network due to general error");
            } else if (response.getStatus() != 0) {
                throw new ZigBeeNetworkManagerException("Unable to send cluster on the ZigBee network:" + response.getErrorMsg());
            }
        }
    }

    public ClusterMessage invoke(ClusterMessage input) throws ZigBeeNetworkManagerException {
        synchronized (networkManager) {
            final ApplicationFrameworkLayer af = ApplicationFrameworkLayer.getAFLayer(networkManager);
            final byte sender = af.getSendingEndpoint(this, input);
            /*
            //FIX Removed because transaction is always 0 for the response due to a bug of CC2480
            final byte transaction = af.getNextTransactionId(sender);
            the next line is a workaround for the problem
            */
            final byte transaction = af.getNextTransactionId(sender);
            final byte[] msg = input.getClusterMsg();

            m_addAFMessageListener();

            //Registering the waiter before sending the message, so that they will be captured
            WaitForClusterResponse waiter = new WaitForClusterResponse(
                    this, transaction, input.getId(), TIMEOUT
            );

            logger.trace("---> SENDING transaction: " + transaction + " TO: " + node.getNetworkAddress() + " with"
                    + " byte 0 " + Integers.getByteAsInteger(node.getNetworkAddress(), 0)
                    + " byte 1 " + Integers.getByteAsInteger(node.getNetworkAddress(), 1)
                    + " byte 2 " + Integers.getByteAsInteger(node.getNetworkAddress(), 2)
                    + " byte 3 " + Integers.getByteAsInteger(node.getNetworkAddress(), 3)
                    + " from end point: " + sender
                    + " to end point: " + endPoint
            );
            //TODO Create radius and options according to the current configuration
            AF_DATA_CONFIRM response = networkManager.sendAFDataRequest(new AF_DATA_REQUEST(
                    node.getNetworkAddress(), endPoint, sender, input.getId(),
                    transaction, (byte) (0) /*options*/, (byte) 0 /*radius*/, msg
            ));

            if (response == null) {
                m_removeAFMessageListener();
                throw new ZigBeeNetworkManagerException("Unable to send cluster on the ZigBee network due to general error - is the device sleeping?");
            } else if (response.getStatus() != 0) {
                m_removeAFMessageListener();
                final ResponseStatus responseStatus = ResponseStatus.getStatus(Integers.getByteAsInteger(response.getStatus(), 0));

                /*if (responseStatus == ResponseStatus.Z_MAC_NO_ACK)  {
                    logger.info("Removing unresponsive device: " + getIEEEAddress());
                    ApplicationFrameworkLayer.getAFLayer(networkManager).getZigBeeNetwork().removeNode(this.getNode());
                }*/

                throw new ZigBeeNetworkManagerException("Unable to send cluster on the ZigBee network due to: "
                        + responseStatus + " (" + response.getErrorMsg() + ")");
            } else {
                //FIX Can't be singleton because the invoke method can be invoked by multiple-thread
                AF_INCOMING_MSG incoming = waiter.getResponse();
                m_removeAFMessageListener();
                if (incoming == null) {
                    throw new ZigBeeBasedriverTimeOutException();
                }
                ClusterMessage result = new ClusterMessageImpl(incoming.getData(), incoming.getClusterId());
                return result;
            }
        }
    }

    public boolean providesInputCluster(int id) {
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] == id) return true;
        }
        return false;
    }

    public boolean providesOutputCluster(int id) {
        for (int i = 0; i < outputs.length; i++) {
            if (outputs[i] == id) return true;
        }
        return false;
    }

    public boolean bindTo(ZigBeeEndpoint endpoint, int clusterId) throws ZigBeeNetworkManagerException {
        logger.info("Binding from endpoint {} to {} for cluster {}", new Object[]{
                getEndpointId(), endpoint.getEndpointId(), new Integer(clusterId)
        });

        final ZDO_BIND_RSP response = networkManager.sendZDOBind(new ZDO_BIND_REQ(
                (short) getNode().getNetworkAddress(), (short) clusterId,
                IEEEAddress.fromColonNotation(getNode().getIEEEAddress()), (byte) endPoint,
                IEEEAddress.fromColonNotation(endpoint.getNode().getIEEEAddress()), (byte) endpoint.getDeviceTypeId()
        ));
        if (response == null || response.Status != 0) {
            logger.warn("ZDO_BIND_REQ failed due to {}, unable to bind from endpoint {} to {} for cluster {}", new Object[]{
                    ResponseStatus.getStatus(response.Status) ,getEndpointId(), endpoint.getEndpointId(),
                    new Integer(clusterId)
            });
            return false;
        }
        return true;
    }

    public boolean unbindFrom(ZigBeeEndpoint endpoint, int clusterId) throws ZigBeeNetworkManagerException {
        logger.info("Un-binding from endpoint {} to {} for cluster {}", new Object[]{
                getEndpointId(), endpoint.getEndpointId(), new Integer(clusterId)
        });

        final ZDO_UNBIND_RSP response = networkManager.sendZDOUnbind(new ZDO_UNBIND_REQ(
                (short) getNode().getNetworkAddress(), (short) clusterId,
                IEEEAddress.fromColonNotation(getNode().getIEEEAddress()), (byte) endPoint,
                IEEEAddress.fromColonNotation(endpoint.getNode().getIEEEAddress()), (byte) endpoint.getDeviceTypeId()
        ));
        if (response == null || response.Status != 0) {
            logger.warn("ZDO_BIND_REQ failed, unable to un-bind from endpoint {} to {} for cluster {}", new Object[]{
                    getEndpointId(), endpoint.getEndpointId(), new Integer(clusterId)
            });
            return false;
        }
        return true;
    }


    public boolean bindToLocal(int clusterId) throws ZigBeeNetworkManagerException {
        if (boundCluster.contains(clusterId)) {
            logger.debug("Cluster already bound");
            return true;
        }

        byte dstEP = ApplicationFrameworkLayer.getAFLayer(networkManager).getSendingEndpoint(this, clusterId);

        logger.info("Binding from endpoint {} to {} for cluster {}", new Object[]{
                getEndpointId(), IEEEAddress.toString(networkManager.getIEEEAddress()) + "/" + dstEP, new Integer(clusterId)
        });

        final ZDO_BIND_RSP response = networkManager.sendZDOBind(new ZDO_BIND_REQ(
                (short) getNode().getNetworkAddress(), (short) clusterId,
                IEEEAddress.fromColonNotation(getNode().getIEEEAddress()), (byte) endPoint,
                networkManager.getIEEEAddress(), (byte) dstEP
        ));
        if (response == null || response.Status != 0) {
            logger.warn("ZDO_BIND_REQ failed due to {}, unable to bind from endpoint {} to {} for cluster {}", new Object[]{
                    response==null?"Timeout":ResponseStatus.getStatus(response.Status) ,getEndpointId(),
                    IEEEAddress.toString(networkManager.getIEEEAddress()) + "/" + dstEP,
                    new Integer(clusterId)
            });
            return false;
        }
        boundCluster.add(clusterId);
        return true;
    }

    public boolean unbindFromLocal(int clusterId) throws ZigBeeNetworkManagerException {
        logger.info("Unbinding from cluster {} of endpoint {}", clusterId, getEndpointId());
        if (!boundCluster.contains(clusterId)) {
            logger.warn("Cluster already unbound");
            return true;
        }

        byte dstEP = ApplicationFrameworkLayer.getAFLayer(networkManager).getSendingEndpoint(this, clusterId);

        final ZDO_UNBIND_RSP response = networkManager.sendZDOUnbind(new ZDO_UNBIND_REQ(
                (short) getNode().getNetworkAddress(), (short) clusterId,
                IEEEAddress.fromColonNotation(getNode().getIEEEAddress()), (byte) endPoint,
                networkManager.getIEEEAddress(), (byte) dstEP
        ));
        if (response == null || response.Status != 0) {
            logger.warn("ZDO_BIND_REQ failed, unable to unbind");
            return false;
        }
        boundCluster.remove(clusterId);
        return true;
    }

    private void m_addAFMessageListener() {
        if (listeners.isEmpty() && consumers.size() == 0) {
            logger.debug("Registered {} as {}", this, ApplicationFrameworkMessageListener.class.getName());
            networkManager.addAFMessageListner(this);
        } else {
            logger.debug("Skipped to registered {} as {}", this, ApplicationFrameworkMessageListener.class.getName());
            logger.trace(
                    "Skipped registration due to: listeners.isEmpty() = {}  or consumers.size() = {}",
                    listeners.isEmpty(), consumers.size()
            );
        }
    }

    private void m_removeAFMessageListener() {
        if (listeners.isEmpty() && consumers.size() == 0) {
            logger.debug("Unregistered {} as {}", this, ApplicationFrameworkMessageListener.class.getName());
            networkManager.removeAFMessageListener(this);
        } else {
            logger.debug("Skipped unregistration of {} as {}", this, ApplicationFrameworkMessageListener.class.getName());
            logger.trace(
                    "Skipped unregistration due to: listeners.isEmpty() = {}  or consumers.size() = {}",
                    listeners.isEmpty(), consumers.size()
            );
        }
    }

    public boolean addClusterListener(ClusterListener listener) {
        m_addAFMessageListener();

        return listeners.add(listener);
    }

    public boolean removeClusterListener(ClusterListener listener) {
        boolean result = listeners.remove(listener);
        m_removeAFMessageListener();
        return result;
    }

    private void notifyClusterListener(ClusterMessage c) {
        ArrayList<ClusterListener> localCopy;
        synchronized (listeners) {
            localCopy = new ArrayList<ClusterListener>(listeners);
        }
        if (localCopy.size() > 0) {
            logger.debug("Notifying {} ClusterListener of {}", localCopy.size(), c.getClusterMsg());

            for (ClusterListener listner : localCopy) {
                try {
                    final ClusterFilter filter = listner.getClusterFilter();
                    if (filter == null) {
                        listner.handleCluster(this, c);
                    } else if (filter.match(c) == true) {
                        listner.handleCluster(this, c);
                    }
                } catch (Throwable t) {
                    logger.error("Error during dispatching of Cluster <{},{}>", c.getId(), c.getClusterMsg());
                    logger.error("Error caused by:", t);
                }
            }
        }
    }

    public void notify(AF_INCOMING_MSG msg) {
        //THINK Do the notification in a separated Thread?
        //THINK Should consume messages only if they were sent from this device?!?!
        if (msg.isError()) return;
        logger.debug("AF_INCOMING_MSG arrived for {} message is {}", endpointId, msg);
        ArrayList<ApplicationFrameworkMessageConsumer> localConsumers = null;
        synchronized (consumers) {
            localConsumers = new ArrayList<ApplicationFrameworkMessageConsumer>(consumers);
        }
        logger.trace("Notifying {} ApplicationFrameworkMessageConsumer", localConsumers.size());
        for (ApplicationFrameworkMessageConsumer consumer : localConsumers) {
            if (consumer.consume(msg)) {
                logger.trace("AF_INCOMING_MSG Consumed by {}", consumer.getClass().getName());
                return;
            } else {
                logger.trace("AF_INCOMING_MSG Ignored by {}", consumer.getClass().getName());
            }
        }

        if (msg.getSrcAddr() != node.getNetworkAddress()) return;
        if (msg.getSrcEndpoint() != endPoint) return;
        logger.debug("Notifying cluster listener for received by {}", endpointId);
        notifyClusterListener(new ClusterMessageImpl(msg.getData(), msg.getClusterId()));
    }

    public boolean addAFMessageConsumer(ApplicationFrameworkMessageConsumer consumer) {
        synchronized (consumers) {
            return consumers.add(consumer);
        }
    }

    public boolean removeAFMessageConsumer(ApplicationFrameworkMessageConsumer consumer) {
        synchronized (consumers) {
            return consumers.remove(consumer);
        }
    }

    /**
     * @since 0.4.0
     */
    public String toString() {
        return getEndpointId();
    }

}
