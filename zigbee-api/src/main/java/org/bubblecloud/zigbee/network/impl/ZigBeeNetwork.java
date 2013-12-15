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

import org.bubblecloud.zigbee.network.DeviceListener;
import org.bubblecloud.zigbee.network.ZigBeeDevice;
import org.bubblecloud.zigbee.network.ZigBeeDiscoveryMonitor;
import org.bubblecloud.zigbee.network.ZigBeeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 *
 */
public class ZigBeeNetwork {
	
	private static final Logger logger = LoggerFactory.getLogger(ZigBeeNetwork.class);
	
	private final Hashtable<String, ZigBeeNodeImpl> nodes = new Hashtable<String, ZigBeeNodeImpl>();
	private final Hashtable<ZigBeeNode, HashMap<Integer, ZigBeeDevice>> devices =
		new Hashtable<ZigBeeNode, HashMap<Integer, ZigBeeDevice>>();
	
	private final HashMap<Integer, ArrayList<ZigBeeDevice>> profiles =
		new HashMap<Integer, ArrayList<ZigBeeDevice>>();

    private final List<ZigBeeDiscoveryMonitor> discoveryMonitors = new ArrayList<ZigBeeDiscoveryMonitor>();

    private final List<DeviceListener> deviceListeners = new ArrayList<DeviceListener>();

	/**
	 * 
	 * <b>NOT IN USE, the Driver does not define a logic for unregister devices</b><br>
	 * This method is require for implementing issues:
	 * <ul>
	 * <li><a href="http://zb4osgi.aaloa.org/redmine/issues/35">Blacklisting of device (#35)</a></li>
     * <li><a href="http://zb4osgi.aaloa.org/redmine/issues/64">Base Driver should monitor the health status of device (#64)</a></li>
     * </ul>
	 * 
	 * 
	 * @param node
	 * @return
	 */
	public synchronized boolean removeNode(ZigBeeNode node){
		final String ieee = node.getIEEEAddress();
		
		if( !nodes.containsKey(ieee) ){
			return false;
		}
		HashMap<Integer, ZigBeeDevice> toRemove = devices.get(node);
		if(toRemove != null){
			Iterator<ZigBeeDevice> it = toRemove.values().iterator();
            while(it.hasNext()){
                final ZigBeeDevice device = it.next();
                if (device != null){
                    notifyDeviceRemoved(device);
                    it.remove();
                    removeDeviceFromProfiles(device);
                }
            }
		}
		nodes.remove(ieee);
		return true;
	}
	
	public  synchronized boolean  addNode(ZigBeeNodeImpl node){
		final String ieee = node.getIEEEAddress();
		
		if( nodes.containsKey(ieee) ){
		    logger.debug( "Node {} already present on the network", node );
			return false;
		}
		
		logger.debug( "Adding node {} to the network", node );
		nodes.put(ieee, node);
		devices.put(node, new HashMap<Integer, ZigBeeDevice>());
		return true;
	}
	
	public synchronized boolean removeDevice(ZigBeeDevice device){

        notifyDeviceRemoved(device);

        final String ieee = device.getPhysicalNode().getIEEEAddress();
		
		ZigBeeNode node = null;
		node = nodes.get(ieee);
		if( node == null ){
			logger.error("Trying to remove a device but containing node {} does not exists", node);
			return false;
		}

		final HashMap<Integer, ZigBeeDevice> endPoints = devices.get(node);
		endPoints.remove(device.getId());
		removeDeviceFromProfiles(device);
		
		return true;
	}
	
	public synchronized boolean addDevice(ZigBeeDevice device){
	    final ZigBeeNode deviceNode = device.getPhysicalNode();
		final String ieee = deviceNode.getIEEEAddress();
		final short endPoint = device.getId();
		logger.info( "Adding device {} on node {} / end point {}", device.getDeviceId(),
                device.getPhysicalNode(), endPoint );
        final ZigBeeNode node = nodes.get(ieee);
		if( node == null ){
		    logger.debug( "No node {} found" );
			return false;
		} else if ( node.getNetworkAddress() != deviceNode.getNetworkAddress() ){
		    logger.debug( "Node ieee collision, stored is {} and new one is {}", node, deviceNode );
		    return false;
		}

        HashMap<Integer, ZigBeeDevice> endPoints = devices.get(node);
		if(endPoints.containsKey(endPoint)){
            logger.debug( "Device {} on node {} already registered", endPoint, node );
			return false;
		}
		endPoints.put((int) endPoint, device);
		
		final int profileId = device.getProfileId();
		ArrayList<ZigBeeDevice> list;
		list  = profiles.get(profileId);
		if ( list == null ){
			list = new ArrayList<ZigBeeDevice>();
			profiles.put(profileId, list);
		}
		list.add(device);

        notifyDeviceAdded(device);
		return true;
	}
	
	private synchronized boolean removeDeviceFromProfiles(final ZigBeeDevice device){		
		
		final int profileId = device.getProfileId();
		ArrayList<ZigBeeDevice> list = profiles.get(profileId);
		if( list == null ){
			logger.error("Trying to remove a device from a given profile but the profile doesn't exist");
			//XXX It should never happen, we should throw an IllegalStateException
			return true;
		}
		//XXX It following method must always return true, otherwise we should throw an IllegalStateException
		if( list.remove(device) == false){
			logger.error("Device to remove not found in the given profile");
		}
		
		return true;
		
	}
	
	
	public synchronized Collection<ZigBeeDevice> getDevices(int profileId){
		final ArrayList<ZigBeeDevice> result = new ArrayList<ZigBeeDevice>();		
		final ArrayList<ZigBeeDevice> values = profiles.get(profileId);
		if ( values == null ) {
			logger.warn("No devices found implemting the profile={}", profileId);
		} else {
			logger.error("We found {} implementing the profile={}", values.size(), profileId);
			result.addAll(values);
		}				
		return result;
	}

	public boolean containsDevice(String ieee, short endPoint) {
		final ZigBeeNode node = nodes.get(ieee);
		if ( node == null ){
			return false;
		}
		/*
		 * If node is not null it means that we found the same network node because
		 * IEEE address must be unique for each network node
		 */
		final HashMap<Integer, ZigBeeDevice> endPoints = devices.get(node);
		if ( endPoints == null ) {
			return false;
		}
		return endPoints.containsKey(endPoint);
	}
    
	public ZigBeeNodeImpl containsNode(String ieeeAddress) {
		return nodes.get(ieeeAddress);
	}

    /**
     * Adds discovery monitor.
     *
     * @param discoveryMonitor the discovery monitor
     */
    public void addDiscoveryMonitor(final ZigBeeDiscoveryMonitor discoveryMonitor) {
        synchronized (discoveryMonitors) {
            this.discoveryMonitors.add(discoveryMonitor);
        }
    }

    /**
     * Removes discovery monitor.
     *
     * @param discoveryMonitor the discovery monitor
     */
    public void removeDiscoveryMonitor(final ZigBeeDiscoveryMonitor discoveryMonitor) {
        synchronized (discoveryMonitors) {
            this.discoveryMonitors.remove(discoveryMonitor);
        }
    }

    /**
     * Notifies discovery listeners that node has been browsed.
     *
     * @param node the node
     */
    public void notifyNodeBrowsed(ZigBeeNode node) {
        synchronized (discoveryMonitors) {
            for (final ZigBeeDiscoveryMonitor discoveryMonitor : discoveryMonitors) {
                discoveryMonitor.browsedNode(node);
            }
        }
    }

    /**
     * Notifies discovery listeners that node has been browsed.
     *
     * @param node the node
     */
    public void notifyNodeAnnounced(ZigBeeNode node) {
        synchronized (discoveryMonitors) {
            for (final ZigBeeDiscoveryMonitor discoveryMonitor : discoveryMonitors) {
                discoveryMonitor.announcedNode(node);
            }
        }
    }

    public void addDeviceListener(final DeviceListener deviceListener) {
        synchronized (deviceListeners) {
            deviceListeners.add(deviceListener);
        }
    }

    public void removeDeviceListener(final DeviceListener deviceListener) {
        synchronized (deviceListeners) {
            deviceListeners.remove(deviceListener);
        }
    }

    public void notifyDeviceAdded(final ZigBeeDevice device) {
        synchronized (deviceListeners) {
            for (final DeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceAdded(device);
            }
        }
    }

    public void notifyDeviceUpdated(final ZigBeeDevice device) {
        synchronized (deviceListeners) {
            for (final DeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceUpdated(device);
            }
        }
    }

    public void notifyDeviceRemoved(final ZigBeeDevice device) {
        synchronized (deviceListeners) {
            for (final DeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceRemoved(device);
            }
        }
    }

}
