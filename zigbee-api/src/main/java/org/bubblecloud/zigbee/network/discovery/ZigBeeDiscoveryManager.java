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

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.network.ApplicationFrameworkMessageListener;
import org.bubblecloud.zigbee.network.ZigBeeNetworkManager;
import org.bubblecloud.zigbee.network.discovery.LinkQualityIndicatorNetworkBrowser.NetworkNeighbourLinks;
import org.bubblecloud.zigbee.network.impl.ApplicationFrameworkLayer;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetwork;
import org.bubblecloud.zigbee.network.impl.ZigBeeNodeImpl;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.af.AF_INCOMING_MSG;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_IEEE_ADDR_REQ;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_IEEE_ADDR_RSP;
import org.bubblecloud.zigbee.util.Integers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * This class tracks the {@link org.bubblecloud.zigbee.network.ZigBeeNetworkManager} service available
 * and it creates all the resources required by this implementation of the <i>ZigBee Base Driver</i>
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 */
public class ZigBeeDiscoveryManager implements ApplicationFrameworkMessageListener {

    private final static Logger logger = LoggerFactory.getLogger(ZigBeeDiscoveryManager.class);

    private ZigBeeNetworkManager networkManager;

    private AnnounceListenerImpl announceListener;
    private AssociationNetworkBrowser associationNetworkBrowser = null;
    private LinkQualityIndicatorNetworkBrowser linkQualityIndicatorNetworkBrowser = null;

    private EndpointBuilder endpointBuilder;
    private final ImportingQueue importingQueue;

    private EnumSet<DiscoveryMode> enabledDiscoveries;

    /**
     * Contains a list of all the network addresses we've inspected
     */
    private Set<Integer> inspectedNetworkAddresses = new HashSet<Integer>();

    public ZigBeeDiscoveryManager(ZigBeeNetworkManager networkManager, final EnumSet<DiscoveryMode> enabledDiscoveries) {
        importingQueue = new ImportingQueue();
        this.networkManager = networkManager;
        this.enabledDiscoveries = enabledDiscoveries;
    }

    public void startup() {
        logger.trace("Setting up all the importer data and threads");
        importingQueue.clear();
        ApplicationFrameworkLayer.getAFLayer(networkManager);

        if (enabledDiscoveries.contains(DiscoveryMode.Announce)) {
            announceListener = new AnnounceListenerImpl(importingQueue, networkManager);
            networkManager.addAnnounceListener(announceListener);
        } else {
            logger.trace("ANNOUNCE discovery disabled.");
        }

        if (enabledDiscoveries.contains(DiscoveryMode.Addressing)) {
            associationNetworkBrowser = new AssociationNetworkBrowser(importingQueue, networkManager);
            new Thread(associationNetworkBrowser, "AssociationNetworkBrowser[" + networkManager + "]").start();
        } else {
            logger.trace("{} discovery disabled.",
                    AssociationNetworkBrowser.class);
        }

        if (enabledDiscoveries.contains(DiscoveryMode.LinkQuality)) {
            linkQualityIndicatorNetworkBrowser = new LinkQualityIndicatorNetworkBrowser(importingQueue, networkManager);
            new Thread(linkQualityIndicatorNetworkBrowser, "LinkQualityIndicatorNetworkBrowser[" + networkManager + "]").start();
        } else {
            logger.trace("{} discovery disabled.",
                    LinkQualityIndicatorNetworkBrowser.class);
        }

        endpointBuilder = new EndpointBuilder(importingQueue, networkManager);
        new Thread(endpointBuilder, "EndpointBuilder[" + networkManager + "]").start();

        networkManager.addAFMessageListner(this);
    }

    public void shutdown() {
        //logger.info("Driver used left:clean up all the data and closing all the threads");

        networkManager.removeAnnounceListener(announceListener);

        if (associationNetworkBrowser != null) {
            associationNetworkBrowser.end();
            associationNetworkBrowser.interrupt();
        }
        if (linkQualityIndicatorNetworkBrowser != null) {
            linkQualityIndicatorNetworkBrowser.end();
            linkQualityIndicatorNetworkBrowser.interrupt();
        }
        if (endpointBuilder != null) {
            endpointBuilder.end();
        }
        importingQueue.close();
    }

    public boolean isInitialNetworkBrowsingComplete() {
        return (associationNetworkBrowser == null || associationNetworkBrowser.isInitialNetworkBrowsingComplete())
                && endpointBuilder.isReady();
    }

    @Override
    public void notify(AF_INCOMING_MSG msg) {
        final int sourceNetworkAddress = msg.getSrcAddr();

        synchronized (inspectedNetworkAddresses) {
        	// If this is an unknown device, then inspect it
            if (!inspectedNetworkAddresses.contains(sourceNetworkAddress)) {
            	// Add the device to the list so we don't inspect it again
                inspectedNetworkAddresses.add(sourceNetworkAddress);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        inspectNetworkAddress(sourceNetworkAddress);
                    }
                }).start();
            }
        }
    }

    /**
     * Inspect given network address.
     *
     * @param sourceNetworkAddress the network address to inspect
     */
    private synchronized void inspectNetworkAddress(final int sourceNetworkAddress) {
        logger.debug("Inspecting node based on incoming AF message from network address #{}.",
                sourceNetworkAddress);

        final ZDO_IEEE_ADDR_RSP result = networkManager.sendZDOIEEEAddressRequest(
                new ZDO_IEEE_ADDR_REQ(sourceNetworkAddress, ZDO_IEEE_ADDR_REQ.REQ_TYPE.SINGLE_DEVICE_RESPONSE, (byte) 0)
        );

        if (result == null) {
            logger.debug("Node did not respond to ZDO_IEEE_ADDR_REQ #{}", sourceNetworkAddress);
        } else if (result.Status == 0) {
            logger.debug("Node network address #{} resolved to IEEE address {}.", sourceNetworkAddress, result.getIeeeAddress());
            final ZigBeeNodeImpl node = new ZigBeeNodeImpl(sourceNetworkAddress, result.getIeeeAddress(),
                    (short) networkManager.getCurrentPanId());

            ZToolAddress16 nwk = new ZToolAddress16(
                    Integers.getByteAsInteger(sourceNetworkAddress, 1),
                    Integers.getByteAsInteger(sourceNetworkAddress, 0)
            );
            importingQueue.push(nwk, result.getIeeeAddress());

            final ZigBeeNetwork network = ApplicationFrameworkLayer.getAFLayer(networkManager).getZigBeeNetwork();
            network.notifyDiscoveryBrowsed(node);
        } else {
            logger.warn("Node #{} ZDO_IEEE_ADDR_REQ failed with status {} ", sourceNetworkAddress,
                    Status.getStatus((byte) result.Status));
        }
    }

    /**
     * Returns the link quality information
     * @return
     */
    public NetworkNeighbourLinks getLinkQualityInfo() {
    	return linkQualityIndicatorNetworkBrowser.getConnectedNodes();
    }
}
