/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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

import org.bubblecloud.zigbee.network.ZigBeeNetworkManager;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetwork;
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_IEEE_ADDR_REQ;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_IEEE_ADDR_RSP;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_MGMT_LQI_REQ;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_MGMT_LQI_RSP;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_MGMT_LQI_RSP.NeighborLqiListItemClass;
import org.bubblecloud.zigbee.util.Integers;
import org.bubblecloud.zigbee.util.RunnableThread;
import org.bubblecloud.zigbee.util.ThreadUtils;
import org.bubblecloud.zigbee.network.ZigBeeNode;
import org.bubblecloud.zigbee.network.impl.ApplicationFrameworkLayer;
import org.bubblecloud.zigbee.network.impl.ZigBeeNodeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision: 67 $ ($LastChangedDate: 2010-10-01 04:08:24 +0200 (ven, 01 ott 2010) $)
 * @since 0.7.0
 */
public class LinkQualityIndicatorNetworkBrowser extends RunnableThread {

    private static final Logger logger = LoggerFactory.getLogger(LinkQualityIndicatorNetworkBrowser.class);

    private static final short COORDINATOR_NWK_ADDRESS = 0;
    private static final short LQI_START_INDEX = 0;

    private final ImportingQueue queue;
    final ZigBeeNetworkManager driver;

    final ArrayList<NetworkAddressNodeItem> toInspect = new ArrayList<NetworkAddressNodeItem>();
    final HashMap<Integer, NetworkAddressNodeItem> alreadyInspected = new HashMap<Integer, NetworkAddressNodeItem>();
    private List<NetworkAddressNodeItem> connectedNodesFound = new ArrayList<NetworkAddressNodeItem>();

    private class NetworkAddressNodeItem {
        final NetworkAddressNodeItem parent;
        final short address;
        ZigBeeNodeImpl node = null;

        NetworkAddressNodeItem(NetworkAddressNodeItem addressTreeParent, short networkAddress) {
            parent = addressTreeParent;
            address = networkAddress;
        }

        public String toString() {
            if (parent != null) {
                return "<" + parent.address + " / " + parent.node + "," + address + " / " + node + ">";
            } else {
                return "< NULL ," + address + " / " + node + ">";
            }
        }
    }

    public LinkQualityIndicatorNetworkBrowser(ImportingQueue queue, ZigBeeNetworkManager driver) {
        this.queue = queue;
        this.driver = driver;
    }

    private NetworkAddressNodeItem getIEEEAddress(short nwkAddress) {

        NetworkAddressNodeItem node = new NetworkAddressNodeItem(null, nwkAddress);

        ZDO_IEEE_ADDR_RSP ieee_addr_resp = driver.sendZDOIEEEAddressRequest(
                new ZDO_IEEE_ADDR_REQ(nwkAddress, ZDO_IEEE_ADDR_REQ.REQ_TYPE.SINGLE_DEVICE_RESPONSE, (byte) 0)
        );

        if (ieee_addr_resp == null) {
            logger.debug("No ZDO_IEEE_ADDR_RSP from #{}", nwkAddress);
            return null;
        } else {
            logger.debug(
                    "ZDO_IEEE_ADDR_RSP from {} with {} associated",
                    ieee_addr_resp.getIEEEAddress(), ieee_addr_resp.getAssociatedNodeCount()
            );

            node.node = new ZigBeeNodeImpl(node.address, ieee_addr_resp.getIEEEAddress(),
                    (short) driver.getCurrentPanId());

            ZToolAddress16 nwk = new ZToolAddress16(
                    Integers.getByteAsInteger(node.address, 1),
                    Integers.getByteAsInteger(node.address, 0)
            );

            if (!alreadyInspected.containsKey((int) node.address)) {
                queue.push(nwk, ieee_addr_resp.getIEEEAddress());
            }
            announceNode(node);

            return node;
        }
    }

    private void announceNodes(List<NetworkAddressNodeItem> nodes) {

        if (nodes != null)
            for (int i = 0; i < nodes.size(); i++)
                announceNode(nodes.get(i));
    }

    private void announceNode(NetworkAddressNodeItem node) {

        if (node != null) {
            notifyBrowsedNode(node);
        }
    }

    private List<NetworkAddressNodeItem> lqiRequestToNode(NetworkAddressNodeItem node, int index) {

        if (alreadyInspected.get((int) node.address) == null) {
            alreadyInspected.put((int) node.address, node);

            if (index == 0)
                connectedNodesFound.clear();

            ZToolAddress16 nwk16 = new ZToolAddress16(
                    Integers.getByteAsInteger(node.address, 1),
                    Integers.getByteAsInteger(node.address, 0)
            );

            logger.debug("ZDO_MGMT_LQI_REQ to {} from index {}", nwk16.get16BitValue(), index);
            ZDO_MGMT_LQI_RSP lqi_resp = driver.sendLQIRequest(new ZDO_MGMT_LQI_REQ(nwk16, index));

            if (lqi_resp == null) {
                logger.debug("No LQI answer from #{}", nwk16.get16BitValue());
                return null;
            } else {
                logger.debug(
                        "Found {} neighbors on node {}",
                        lqi_resp.getNeighborLQICount(), node.address);

                NeighborLqiListItemClass[] neighbors = (NeighborLqiListItemClass[]) lqi_resp.getNeighborLqiList();

                if (neighbors != null) {
                    for (int i = 0; i < neighbors.length; i++) {
                        NeighborLqiListItemClass neighbor = (NeighborLqiListItemClass) neighbors[i];

                        logger.debug("Node #{} visible from node #{} with LQI value {}", new Object[]{neighbor.NetworkAddress.get16BitValue(), nwk16.get16BitValue(), neighbor.RxLQI});

                        NetworkAddressNodeItem result = getIEEEAddress((short) neighbor.NetworkAddress.get16BitValue());
                        NetworkAddressNodeItem newNode;
                        if (result != null) {
                            logger.debug("Node #{} is {}", new Object[]{neighbor.NetworkAddress.get16BitValue(), result.node.getIEEEAddress()});
                            newNode = new NetworkAddressNodeItem(node, result.address);
                            connectedNodesFound.add(newNode);
                        } else {
                            newNode = new NetworkAddressNodeItem(node, (short) neighbor.NetworkAddress.get16BitValue());
                            connectedNodesFound.add(newNode);
                            logger.debug("No response to ZDO_IEEE_ADDR_REQ from node {}", neighbor.NetworkAddress.get16BitValue());
                        }
                    }
                }

                // NeighborLQICount: neighbors IN THIS RESPONSE
                // NeighborLQIEntries: all available neighbors
                if (lqi_resp.getNeighborLQIEntries() > (lqi_resp.getNeighborLQICount() + index + 1)) {
                    logger.debug("ZDO_MGMT_LQI_REQ new request to {} because of too many entries for a single request," +
                            " restarting from index {}", node.address, lqi_resp.getNeighborLQICount() + index + 1);
                    lqiRequestToNode(node, lqi_resp.getNeighborLQICount() + index + 1);
                }

                return connectedNodesFound;
            }
        } else {
            logger.debug("Node {} inspected few seconds ago, request delayed", node.address);
            return null;
        }
    }

    private void inspectQueue(ArrayList<NetworkAddressNodeItem> toInspectTemp) {

        for (int i = 0; i < toInspect.size(); i++) {
            List<NetworkAddressNodeItem> children = new ArrayList<NetworkAddressNodeItem>();
            NetworkAddressNodeItem node = toInspect.get(i);
            if (node != null) {
                children = lqiRequestToNode(node, LQI_START_INDEX);
                if (children != null) {
                    toInspectTemp.addAll(children);
                    announceNodes(children);
                }
            }
        }
    }

    public void task() {

        final String threadName = Thread.currentThread().getName();

        logger.trace("{} STARTED Succesfully", threadName);

        while (!isDone()) {
            cleanUpWalkingTree();

            logger.debug("Inspecting ZigBee network for new nodes.");

            try {
                NetworkAddressNodeItem coordinator = getIEEEAddress(COORDINATOR_NWK_ADDRESS);
                if (coordinator != null) {

                    //gt = new GraphThread();

                    List<NetworkAddressNodeItem> coordinatorChildren = lqiRequestToNode(coordinator, LQI_START_INDEX);
                    if (coordinatorChildren != null)
                        toInspect.addAll(coordinatorChildren);

                    ArrayList<NetworkAddressNodeItem> toInspectTemp = new ArrayList<NetworkAddressNodeItem>();

                    while (!toInspect.isEmpty()) {
                        inspectQueue(toInspectTemp);

                        toInspect.clear();
                        if (!toInspectTemp.isEmpty())
                            for (int i = 0; i < toInspectTemp.size(); i++)
                                toInspect.add(toInspectTemp.get(i));
                        toInspectTemp.clear();
                    }
                    toInspect.clear();
                }

                long wakeUpTime = System.currentTimeMillis() + 5 * 60 * 1000;
                if (!isDone()) ThreadUtils.waitingUntil(wakeUpTime);
                logger.info("Network browsing completed, waiting until {}", wakeUpTime);
                //gt.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //gt.end();
        logger.trace("{} TERMINATED Succesfully", threadName);
    }

    private void cleanUpWalkingTree() {
        alreadyInspected.clear();
        toInspect.clear();
    }

    private void notifyBrowsedNode(NetworkAddressNodeItem item) {
        final ZigBeeNode child = item.node;
        final ZigBeeNetwork network = ApplicationFrameworkLayer.getAFLayer(driver).getZigBeeNetwork();
        network.notifyNodeBrowsed(child);
    }
}