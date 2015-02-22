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
import org.bubblecloud.zigbee.network.impl.*;
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_ACTIVE_EP_REQ;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_ACTIVE_EP_RSP;
import org.bubblecloud.zigbee.util.Stoppable;
import org.bubblecloud.zigbee.util.ThreadUtils;
import org.bubblecloud.zigbee.network.model.IEEEAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.Map.Entry;

/**
 * This class implements the {@link Thread} that completes the discovery of the node<br>
 * found either by {@link AssociationNetworkBrowser} or {@link AnnounceListenerImpl} by<br>
 * inspecting the <i>End Point</i> on the node.<br>
 * The inspection of each <i>End Point</i> lead to the creation {@link org.bubblecloud.zigbee.network.ZigBeeEndpoint}..
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
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
    private ImportingQueue.ZigBeeNodeAddress dev;

    private class ZigBeeEndpointReference {
        ZigBeeNode node;
        byte endPoint;

        ZigBeeEndpointReference(ZigBeeNode node, byte endPoint) {
            super();
            this.node = node;
            this.endPoint = endPoint;
        }
    }

    public EndpointBuilder(ImportingQueue queue, ZigBeeNetworkManager driver) {
        this.queue = queue;
        this.driver = driver;
    }


    private ZDO_ACTIVE_EP_RSP doInspectEndpointOfNode(final int nwkAddress, final ZigBeeNode node) {
        logger.trace("Listing end points on node #{} to find devices.", nwkAddress);

        int i = 0;
        ZDO_ACTIVE_EP_RSP result = null;

        while (i < 1) {
            result = driver.sendZDOActiveEndPointRequest(new ZDO_ACTIVE_EP_REQ(nwkAddress));

            if (result == null) {
                final long waiting = 1000;
                logger.trace(
                        "Inspecting device on node {} failed during it {}-nth attempt. " +
                                "Waiting for {}ms before retrying",
                        new Object[]{node, i, waiting}
                );
                ThreadUtils.waitNonPreemptive(waiting);
                i++;
            } else {
                break;
            }
        }

        return result;
    }

    private boolean inspectEndpointOfNode(final int nwkAddress, final ZigBeeNode node) {

        final ZDO_ACTIVE_EP_RSP result = doInspectEndpointOfNode(nwkAddress, node);
        if (result == null) {
            logger.warn("ZDO_ACTIVE_EP_REQ FAILED on {}", node);
            return false;
        }

        byte[] endPoints = result.getActiveEndPointList();
        logger.trace("Found {} end points on #{}.", endPoints.length, nwkAddress);
        for (int i = 0; i < endPoints.length; i++) {
            doCreateZigBeeEndpoint(node, endPoints[i]);
        }

        return true;
    }

    private void doCreateZigBeeEndpoint(ZigBeeNode node, byte ep) {
        final ZigBeeNetwork network = ApplicationFrameworkLayer.getAFLayer(driver).getZigBeeNetwork();
        synchronized (network) {
            if (network.containsEndpoint(node.getIEEEAddress(), ep)) {
                logger.info(
                        "Skipping device creation for endpoint {} on node {} as it is created.", ep, node
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
                logger.trace("Sender end point {} found with profile PROFILE_ID_HOME_AUTOMATION: " + endpoint.getProfileId(), endpoint.getEndpointId());
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
            if (!failedAttempts.containsKey(last))
                failedAttempts.put(last, 0);
            else if (failedAttempts.get(last) + 1 < maxRetriesFailedEndpoints)
                failedAttempts.put(last, failedAttempts.get(last) + 1);
            else {
                logger.debug("Too many attempts failed, device {}:{} adding delayed of {} ms", new Object[]{node, ep, delay});
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
            node = network.containsNode(ieee);
            if (node == null) {
                node = new ZigBeeNodeImpl(nwk, ieeeAddress, (short) driver.getCurrentPanId());
                isNew = true;
                network.addNode(node);
                logger.debug("Created node object for {} that was not available on the network", node);
            } else {

            }
        }
        if (isNew) {
            //logger.info("Inspecting node #{} devices.", nwk);
            correctlyInspected = inspectEndpointOfNode(nwk, node);
            if (correctlyInspected) {
                return;
            } else {
                // if you don't remove node with devices not yet inspected from network, you won't be able to re-inspect them later
                // maybe device is sleeping and you have to wait for a non-sleeping period
                logger.debug("Node {} removed from network because attempts to instantiate devices on it are failed", node);
                network.removeNode(node);
            }
        } else {
            if (node.getNetworkAddress() != nwk) { //TODO We have to verify this step by means of JUnit
                logger.warn(
                        "The device {} has been found again with a new network address {} ",
                        node, nwkAddress.get16BitValue()
                );
                if (!changedNetworkAddress(node, nwk)) {
                    /*
                     * No previous device inspection completed successfully, so we should try to inspect
                     * the device again
                     */
                    inspectEndpointOfNode(nwk, new ZigBeeNodeImpl(nwk, node.getIEEEAddress(), (short) driver.getCurrentPanId()));
                }
            node.setNetworkAddress(nwk);
           }
           for (final ZigBeeEndpoint endpoint : network.getEndPoints(node)) {
               network.notifyEndpointUpdated(endpoint);
           }
        }
    }

    /**
     * This method updates the network address on all the device belonging the node<br>
     * with the change network address<br>
     *
     * @param node {@link org.bubblecloud.zigbee.network.impl.ZigBeeNodeImpl} the old node with the obsoleted network address
     * @param nwk  the new network address of the node
     * @return if at least a device has been updated
     * @since 0.6.0 - Revision 74
     */
    private boolean changedNetworkAddress(ZigBeeNodeImpl node, int nwk) {
        throw new NotImplementedException();
    }

    boolean inspectingNewEndpoint = false;
    private void inspectNewEndpoint() {
        nextInspectionSlot = 10 + System.currentTimeMillis();
        final ImportingQueue.ZigBeeNodeAddress dev = queue.pop();
        inspectingNewEndpoint = true;
        if (dev == null)  {
            inspectingNewEndpoint = false;
            return;
        }
        final ZToolAddress16 nwk = dev.getNetworkAddress();
        final ZToolAddress64 ieee = dev.getIEEEAddress();
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
        nextInspectionSlot = 10 + System.currentTimeMillis();
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

                if (queue.size() > 0 && failedEndpoints.size() > 0) {
                    double sel = Math.random();
                    if (sel > 0.6) {
                        inspectFailedEndpoint();
                    } else {
                        inspectNewEndpoint();
                    }
                } else if (queue.size() == 0 && failedEndpoints.size() > 0) {
                    inspectFailedEndpoint();
                } else if (queue.size() > 0 && failedEndpoints.size() == 0) {
                    inspectNewEndpoint();
                } else if (queue.size() == 0 && failedEndpoints.size() == 0) {
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