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

package org.bubblecloud.zigbee.network.discovery;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.ZigBeeNode;
import org.bubblecloud.zigbee.network.ZigBeeNetworkManager;
import org.bubblecloud.zigbee.network.ZigBeeNodeDescriptor;
import org.bubblecloud.zigbee.network.ZigBeeNodePowerDescriptor;
import org.bubblecloud.zigbee.network.impl.*;
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_ACTIVE_EP_REQ;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_ACTIVE_EP_RSP;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_NODE_DESC_REQ;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_NODE_DESC_RSP;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_POWER_DESC_REQ;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_POWER_DESC_RSP;
import org.bubblecloud.zigbee.util.Stoppable;
import org.bubblecloud.zigbee.util.ThreadUtils;
import org.bubblecloud.zigbee.network.model.IEEEAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

/**
 * This class implements the {@link Thread} that completes the discovery of the node
 * found either by {@link AssociationNetworkBrowser} or {@link AnnounceListenerImpl} by
 * inspecting the <i>Node<i> and then the <i>End Point</i> on the node.
 * <p>
 * Before discovery, the {@link ZigBeeNode} is created.
 * <p>
 * The initial discovery requests the node descriptors from the device. These are only available
 * at device level (ie not for each endpoint). These are simply stored in the node class for later
 * use.
 * <p>
 * After this the endpoints are queried and generated. The inspection of each <i>End Point</i>
 * leads to the creation of {@link ZigBeeEndpoint}.
 * <p>
 * If no endpoints are found on the device, the {@link ZigBeeNode} is removed from the nodes list.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 * @since 0.1.0
 */
public class EndpointBuilder implements Stoppable {

    private static final Logger logger = LoggerFactory.getLogger(EndpointBuilder.class);

    private final ImportingQueue queue;
    private final List<ZigBeeEndpointReference> failedEndpoints = new ArrayList<ZigBeeEndpointReference>();

    private Map<ZigBeeEndpointReference, Integer> failedAttempts = new HashMap<ZigBeeEndpointReference, Integer>();
    private final int maxRetriesFailedEndpoints = 3;

    private Map<ZigBeeEndpointReference, Long> delayedReattempts = new HashMap<ZigBeeEndpointReference, Long>();
    private final long delay = 60000;

    private final ZigBeeNetworkManager driver;
    private boolean end;

    private long nextInspectionSlot = 0;
    private long nextInspectionDelay = 100;		// TODO: Moved here from inline code. Why was this 10ms? 
//    private ImportingQueue.ZigBeeNodeAddress dev;

    private class ZigBeeEndpointReference {
        ZigBeeNode node;
        short endPoint;

        ZigBeeEndpointReference(ZigBeeNode node, short ep) {
            super();
            this.node = node;
            this.endPoint = ep;
        }
    }

    public EndpointBuilder(ImportingQueue queue, ZigBeeNetworkManager driver) {
        this.queue = queue;
        this.driver = driver;
    }

    private boolean inspectEndpointOfNode(final int nwkAddress, final ZigBeeNode node) {
        int i = 0;
        ZDO_ACTIVE_EP_RSP result = null;

        while (i <= 1) {
            result = driver.sendZDOActiveEndPointRequest(new ZDO_ACTIVE_EP_REQ(nwkAddress));

            if (result == null) {
                final long waiting = 1000;
                logger.trace(
                        "Inspecting device on node {} failed during the {}-nth attempt. " +
                                "Waiting for {}ms before retrying",
                        new Object[]{node, i, waiting}
                );
                ThreadUtils.waitNonPreemptive(waiting);
                i++;
            } else {
                break;
            }
        }

        if (result == null) {
            logger.warn("ZDO_ACTIVE_EP_REQ FAILED on {}", node);
            return false;
        }

        short[] endPoints = result.getActiveEndPointList();
        logger.trace("Found {} end points on #{}.", endPoints.length, nwkAddress);
        for (i = 0; i < endPoints.length; i++) {
            doCreateZigBeeEndpoint(node, endPoints[i]);
        }

        return true;
    }

    private void doCreateZigBeeEndpoint(ZigBeeNode node, short ep) {
        final ZigBeeNetwork network = ApplicationFrameworkLayer.getAFLayer(driver).getZigBeeNetwork();
        synchronized (network) {
            if (network.containsEndpoint(node.getIeeeAddress(), ep)) {
                logger.info(
                        "Skipping device creation for endpoint {} on node {} as it already exists.", ep, node
                );
                return;
            } else {
                logger.trace(
                        "Inspecting node {} / endpoint {}.",
                        new Object[]{node, ep}
                );
            }
        }
        try {
            ZigBeeEndpointImpl endpoint = new ZigBeeEndpointImpl(driver, node, ep);
            if (endpoint.getNode().getNetworkAddress() == 0) {
                logger.trace("Sender end point {} found with profile PROFILE_ID_HOME_AUTOMATION: {}", endpoint.getEndpointId(), endpoint.getProfileId());
                ApplicationFrameworkLayer.getAFLayer(driver).registerSenderEndPoint(
                        ep, endpoint.getProfileId(), endpoint.getOutputClusters());
            }
            if ( !(endpoint.getNode().getNetworkAddress() == 0 && endpoint.getInputClusters().length == 0)
                    && !network.addEndpoint(endpoint)) {
                logger.error("Failed to add endpoint {} to the network map for node {}", ep, node);
            }
        } catch (ZigBeeNetworkManagerException e) {
            logger.error("Error building the device: {}", node, e);

            ZigBeeEndpointReference last = new ZigBeeEndpointReference(node, ep);
            if (!failedAttempts.containsKey(last)) {
                failedAttempts.put(last, 0);
            } else if (failedAttempts.get(last) + 1 < maxRetriesFailedEndpoints) {
                failedAttempts.put(last, failedAttempts.get(last) + 1);
            } else {
                logger.debug("Too many attempts failed, device {}:{} adding delay of {} ms", new Object[]{node, ep, delay});
                failedEndpoints.remove(last);
                delayedReattempts.put(last, delay);
            }
        }
    }

    private void inspectNode(ZToolAddress16 nwkAddress, ZToolAddress64 ieeeAddress) {
        int nwk = nwkAddress.get16BitValue();
        final String ieee = IEEEAddress.toString(ieeeAddress.getLong());
        ZigBeeNodeImpl node = null;
        boolean isNew = false, correctlyInspected = false;
        final ZigBeeNetwork network = ApplicationFrameworkLayer.getAFLayer(driver).getZigBeeNetwork();
        synchronized (network) {
        	// See if we already know about this node
            node = network.getNode(ieee);
            if (node == null) {
            	// It's new - create a new node and add it to the network
                node = new ZigBeeNodeImpl(nwk, ieeeAddress, (short) driver.getCurrentPanId());
                isNew = true;
                network.addNode(node);
                logger.debug("Created node object for {} that was not available on the network", node);
            }
        }
        if (isNew) {
        	// This is a new node
            logger.debug("Inspecting new node {}", node);

        	// Get the node descriptor
            ZDO_NODE_DESC_REQ nodeDescriptorReq = new ZDO_NODE_DESC_REQ(nwkAddress.get16BitValue());
            final ZDO_NODE_DESC_RSP nodeResult = driver.sendZDONodeDescriptionRequest(nodeDescriptorReq);
            if (nodeResult == null) {
                logger.warn("ZDO_NODE_DESC_REQ FAILED on {}", node);
            }
            else {
            	// Save the descriptor in the node
            	node.setNodeDescriptor(new ZigBeeNodeDescriptor(nodeResult));

                // If there's an advanced descriptor, get it
                if(nodeResult.ComplexDescriptorAvailable) {
                	// TODO: Save node complex descriptor
                }

                // If there's a user defined descriptor, get it
                if(nodeResult.UserDescriptorAvailable) {
                	// TODO: Save node user descriptor
                }
            }

            // Get the power descriptor
            ZDO_POWER_DESC_REQ powerDescriptorReq = new ZDO_POWER_DESC_REQ(nwkAddress.get16BitValue());
            final ZDO_POWER_DESC_RSP powerResult = driver.sendZDOPowerDescriptionRequest(powerDescriptorReq);
            if (powerResult == null) {
                logger.warn("ZDO_POWER_DESC_REQ FAILED on {}", node);
            }
            else {
            	// Save the power descriptor
            	node.setPowerDescriptor(new ZigBeeNodePowerDescriptor(powerResult));
            }

            // Get a list of supported endpoints
            correctlyInspected = inspectEndpointOfNode(nwk, node);
            if (correctlyInspected) {
            	network.notifyNodeDiscovered(node);
                return;
            } else {
                // If you don't remove node with devices not yet inspected from network,
            	// you won't be able to re-inspect them later...
                // Maybe the device is sleeping and you have to wait for a non-sleeping period...
                logger.warn("Node {} removed from network because no endpoints have been discovered", node);
                network.removeNode(node);
            }
        } else {
            logger.debug("Inspecting existing node {}", node);

            if (node.getNetworkAddress() != nwk) { //TODO We have to verify this step by means of JUnit
                logger.warn(
                        "The device {} has been found again with a new network address #{} ",
                        node, nwkAddress.get16BitValue()
                );

                // Update the network address
                node.setNetworkAddress(nwk);

                // Notify listeners that the device has been updated
                for (final ZigBeeEndpoint endpoint : network.getEndpoints(node)) {
                    network.notifyEndpointUpdated(endpoint);
                }
                
                // Notify listeners that the node has been updated
            	network.notifyNodeUpdated(node);
            }
        }
    }

    boolean inspectingNewEndpoint = false;
    private void inspectNewEndpoint() {
        nextInspectionSlot = nextInspectionDelay + System.currentTimeMillis();
        // Get an address from the end of the queue to inspect
        final ImportingQueue.ZigBeeNodeAddress dev = queue.pop();
        if (dev == null)  {
            return;
        }
        inspectingNewEndpoint = true;
        final ZToolAddress16 nwk = dev.getNetworkAddress();
        final ZToolAddress64 ieee = dev.getIeeeAddress();
        logger.debug("Inspecting device {}.", IEEEAddress.toString(ieee.getLong()));
        inspectNode(nwk, ieee);
        logger.debug("Endpoint inspection completed, next inspection slot in {}",
                Math.max(nextInspectionSlot - System.currentTimeMillis(), 0)
        );
        inspectingNewEndpoint = false;
    }

    private void inspectFailedEndpoint() {
        //TODO We should add a statistical history for removing a device when we tried it too many times
        logger.info("Trying to register a node extracted from FailedQueue");
        final ZigBeeEndpointReference failed = failedEndpoints.get(0);
        nextInspectionSlot = nextInspectionDelay + System.currentTimeMillis();
        doCreateZigBeeEndpoint(failed.node, failed.endPoint);
    }

    /**
     * @return the number of Node waiting for inspection
     * @since 0.6.0 - Revision 71
     */
    public int getPendingNodes() {
        return queue.size();
    }

    /**
     * @return the number of Node waiting for inspection
     * @since 0.6.0 - Revision 71
     */
    public int getPendingEndpoints() {
        return failedEndpoints.size();
    }

    public void run() {
        logger.trace("{} STARTED Successfully", Thread.currentThread().getName());

        while (!isEnd()) {
            try {
                if (!delayedReattempts.isEmpty()) {
                    Iterator<Entry<ZigBeeEndpointReference, Long>> iterator = delayedReattempts.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<ZigBeeEndpointReference, Long> endpointReferenceEntry = iterator.next();
                        if ((endpointReferenceEntry.getValue() + delay) >= System.currentTimeMillis()) {
                            failedEndpoints.add(endpointReferenceEntry.getKey());
                            logger.debug("EP {} of node {} has been readded to queue for inspection after {} ms",
                                    new Object[]{endpointReferenceEntry.getKey().endPoint,
                                            endpointReferenceEntry.getKey().node,
                                            System.currentTimeMillis() - endpointReferenceEntry.getValue()});
                        }
                    }
                }
                ThreadUtils.waitingUntil(nextInspectionSlot);

                logger.debug("Inspection queue: New queue size: {}. Failed queue size: {}", queue.size(), failedEndpoints.size());

                // Prioritise new endpoints over re-inspecting failed endpoints
                if (queue.size() > 0 && failedEndpoints.size() > 0) {
                    // 2/3rds of the time, inspect new endpoints
                    if (Math.random() > 0.6) {
                        inspectFailedEndpoint();
                    } else {
                        inspectNewEndpoint();
                    }
                } else if (queue.size() == 0 && failedEndpoints.size() > 0) {
                	// There are no new endpoints, but failed endoints are queued
                    inspectFailedEndpoint();
                } else if (queue.size() > 0 && failedEndpoints.size() == 0) {
                	// There are no failed endpoints, but new endpoints are queued
                    inspectNewEndpoint();
                } else if (queue.size() == 0 && failedEndpoints.size() == 0) {
                	// There are no endpoints queued.........
                	// Why is this here - queue size is 0?!?
                    inspectNewEndpoint();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.trace("{} TERMINATED Successfully", Thread.currentThread().getName());
    }

    public synchronized boolean isEnd() {
        return end;
    }

    public synchronized void end() {
        end = true;
    }

    public boolean isReady() {
        return queue.isEmpty() && !inspectingNewEndpoint;
    }
}
