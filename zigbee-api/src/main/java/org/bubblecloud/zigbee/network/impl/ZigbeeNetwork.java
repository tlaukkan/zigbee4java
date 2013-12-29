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

import org.bubblecloud.zigbee.network.EndpointListener;
import org.bubblecloud.zigbee.network.ZigbeeDiscoveryMonitor;
import org.bubblecloud.zigbee.network.ZigbeeEndpoint;
import org.bubblecloud.zigbee.network.ZigbeeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZigbeeNetwork {

    private static final Logger logger = LoggerFactory.getLogger(ZigbeeNetwork.class);

    private final Hashtable<String, ZigbeeNodeImpl> nodes = new Hashtable<String, ZigbeeNodeImpl>();
    private final Hashtable<ZigbeeNode, HashMap<Integer, ZigbeeEndpoint>> devices =
            new Hashtable<ZigbeeNode, HashMap<Integer, ZigbeeEndpoint>>();

    private final HashMap<Integer, ArrayList<ZigbeeEndpoint>> profiles =
            new HashMap<Integer, ArrayList<ZigbeeEndpoint>>();

    private final List<ZigbeeDiscoveryMonitor> discoveryMonitors = new ArrayList<ZigbeeDiscoveryMonitor>();

    private final List<EndpointListener> endpointListeners = new ArrayList<EndpointListener>();

    /**
     * <b>NOT IN USE, the Driver does not define a logic for unregister devices</b><br>
     * This method is require for implementing issues:
     * <ul>
     * <li><a href="http://zb4osgi.aaloa.org/redmine/issues/35">Blacklisting of device (#35)</a></li>
     * <li><a href="http://zb4osgi.aaloa.org/redmine/issues/64">Base Driver should monitor the health status of device (#64)</a></li>
     * </ul>
     *
     * @param node
     * @return
     */
    public synchronized boolean removeNode(ZigbeeNode node) {
        final String ieee = node.getIEEEAddress();

        if (!nodes.containsKey(ieee)) {
            return false;
        }
        HashMap<Integer, ZigbeeEndpoint> toRemove = devices.get(node);
        if (toRemove != null) {
            Iterator<ZigbeeEndpoint> it = toRemove.values().iterator();
            while (it.hasNext()) {
                final ZigbeeEndpoint device = it.next();
                if (device != null) {
                    notifyEndpointRemoved(device);
                    it.remove();
                    removeEndpointFromProfiles(device);
                }
            }
        }
        nodes.remove(ieee);
        return true;
    }

    public synchronized boolean addNode(ZigbeeNodeImpl node) {
        final String ieee = node.getIEEEAddress();

        if (nodes.containsKey(ieee)) {
            logger.debug("Node {} already present on the network", node);
            return false;
        }

        logger.debug("Adding node {} to the network", node);
        nodes.put(ieee, node);
        devices.put(node, new HashMap<Integer, ZigbeeEndpoint>());
        return true;
    }

    public synchronized boolean removeEndpoint(ZigbeeEndpoint endpoint) {

        notifyEndpointRemoved(endpoint);

        final String ieee = endpoint.getNode().getIEEEAddress();

        ZigbeeNode node = null;
        node = nodes.get(ieee);
        if (node == null) {
            logger.error("Trying to remove a device but containing node {} does not exists", node);
            return false;
        }

        final HashMap<Integer, ZigbeeEndpoint> endPoints = devices.get(node);
        endPoints.remove(endpoint.getEndPointAddress());
        removeEndpointFromProfiles(endpoint);

        return true;
    }

    public synchronized boolean addEndpoint(ZigbeeEndpoint endpoint) {
        final ZigbeeNode deviceNode = endpoint.getNode();
        final String ieee = deviceNode.getIEEEAddress();
        final short endPoint = endpoint.getEndPointAddress();
        logger.info("Adding device {} on node {} / end point {}.", endpoint.getDeviceId(),
                endpoint.getNode(), endPoint);
        final ZigbeeNode node = nodes.get(ieee);
        if (node == null) {
            logger.debug("No node {} found");
            return false;
        } else if (node.getNetworkAddress() != deviceNode.getNetworkAddress()) {
            logger.debug("Node ieee collision, stored is {} and new one is {}", node, deviceNode);
            return false;
        }

        HashMap<Integer, ZigbeeEndpoint> endPoints = devices.get(node);
        if (endPoints.containsKey(endPoint)) {
            logger.debug("Endpoint {} on node {} already registered", endPoint, node);
            return false;
        }
        endPoints.put((int) endPoint, endpoint);

        final int profileId = endpoint.getProfileId();
        ArrayList<ZigbeeEndpoint> list;
        list = profiles.get(profileId);
        if (list == null) {
            list = new ArrayList<ZigbeeEndpoint>();
            profiles.put(profileId, list);
        }
        list.add(endpoint);

        notifyEndpointAdded(endpoint);
        return true;
    }

    private synchronized boolean removeEndpointFromProfiles(final ZigbeeEndpoint device) {

        final int profileId = device.getProfileId();
        ArrayList<ZigbeeEndpoint> list = profiles.get(profileId);
        if (list == null) {
            logger.error("Trying to remove a device from a given profile but the profile doesn't exist");
            //XXX It should never happen, we should throw an IllegalStateException
            return true;
        }
        //XXX It following method must always return true, otherwise we should throw an IllegalStateException
        if (list.remove(device) == false) {
            logger.error("Device to remove not found in the given profile");
        }

        return true;

    }


    public synchronized Collection<ZigbeeEndpoint> getEndpoints(int profileId) {
        final ArrayList<ZigbeeEndpoint> result = new ArrayList<ZigbeeEndpoint>();
        final ArrayList<ZigbeeEndpoint> values = profiles.get(profileId);
        if (values == null) {
            logger.warn("No endpoints found implementing the profile={}", profileId);
        } else {
            logger.info("We found {} implementing the profile={}", values.size(), profileId);
            result.addAll(values);
        }
        return result;
    }

    public boolean containsEndpoint(String ieee, short endPoint) {
        final ZigbeeNode node = nodes.get(ieee);
        if (node == null) {
            return false;
        }
        /*
		 * If node is not null it means that we found the same network node because
		 * IEEE address must be unique for each network node
		 */
        final HashMap<Integer, ZigbeeEndpoint> endPoints = devices.get(node);
        if (endPoints == null) {
            return false;
        }
        return endPoints.containsKey(endPoint);
    }

    public ZigbeeNodeImpl containsNode(String ieeeAddress) {
        return nodes.get(ieeeAddress);
    }

    /**
     * Adds discovery monitor.
     *
     * @param discoveryMonitor the discovery monitor
     */
    public void addDiscoveryMonitor(final ZigbeeDiscoveryMonitor discoveryMonitor) {
        synchronized (discoveryMonitors) {
            this.discoveryMonitors.add(discoveryMonitor);
        }
    }

    /**
     * Removes discovery monitor.
     *
     * @param discoveryMonitor the discovery monitor
     */
    public void removeDiscoveryMonitor(final ZigbeeDiscoveryMonitor discoveryMonitor) {
        synchronized (discoveryMonitors) {
            this.discoveryMonitors.remove(discoveryMonitor);
        }
    }

    /**
     * Notifies discovery listeners that node has been browsed.
     *
     * @param node the node
     */
    public void notifyNodeBrowsed(ZigbeeNode node) {
        synchronized (discoveryMonitors) {
            for (final ZigbeeDiscoveryMonitor discoveryMonitor : discoveryMonitors) {
                discoveryMonitor.browsedNode(node);
            }
        }
    }

    /**
     * Notifies discovery listeners that node has been browsed.
     *
     * @param node the node
     */
    public void notifyNodeAnnounced(ZigbeeNode node) {
        synchronized (discoveryMonitors) {
            for (final ZigbeeDiscoveryMonitor discoveryMonitor : discoveryMonitors) {
                discoveryMonitor.announcedNode(node);
            }
        }
    }

    public void addEndpointListenerListener(final EndpointListener deviceListener) {
        synchronized (endpointListeners) {
            endpointListeners.add(deviceListener);
        }
    }

    public void removeEndpointListener(final EndpointListener deviceListener) {
        synchronized (endpointListeners) {
            endpointListeners.remove(deviceListener);
        }
    }

    public void notifyEndpointAdded(final ZigbeeEndpoint endpoint) {
        synchronized (endpointListeners) {
            for (final EndpointListener endpointListener : endpointListeners) {
                endpointListener.endpointAdded(endpoint);
            }
        }
    }

    public void notifyEndpointUpdated(final ZigbeeEndpoint endpoint) {
        synchronized (endpointListeners) {
            for (final EndpointListener endpointListener : endpointListeners) {
                endpointListener.endpointUpdated(endpoint);
            }
        }
    }

    public void notifyEndpointRemoved(final ZigbeeEndpoint endpoint) {
        synchronized (endpointListeners) {
            for (final EndpointListener endpointListener : endpointListeners) {
                endpointListener.endpointRemoved(endpoint);
            }
        }
    }

}
