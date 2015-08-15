/**
 * Copyright 2013 Tommi S.E. Laukkanen
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
package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.api.*;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;

import java.util.*;

/**
 * ZigBee proxy context.
 *
 * This class is thread safe.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigBeeApiContext {
    /**
     * The cluster factory.
     */
    private ClusterFactory clusterFactory;
    
    /**
     * The device factory.
     */
    private Map<Integer, DeviceFactory> deviceFactories = new TreeMap<Integer, DeviceFactory>();
    
    /**
     * The devices.
     */
    private List<Device> devices = new ArrayList<Device>();
    
    /**
     * The id and device mapping.
     * ID = [IEEE address]/[end point]
     */
    private Map<String, Device> idDeviceMap = new HashMap<String, Device>();
    
    /**
     * The device listeners.
     */
    private final List<DeviceListener> deviceListeners = new ArrayList<DeviceListener>();
    
    /**
     * @return the cluster factory.
     */
    public ClusterFactory getClusterFactory() {
        return clusterFactory;
    }

    /**
     * @param clusterFactory the cluster factory.
     */
    public void setClusterFactory(ClusterFactory clusterFactory) {
        this.clusterFactory = clusterFactory;
    }

    /**
     * @return list of device factories.
     */
    public Map<Integer, DeviceFactory> getDeviceFactories() {
        return deviceFactories;
    }

    /**
     * @param deviceFactories life of device factories
     */
    public void setDeviceFactories(Map<Integer, DeviceFactory> deviceFactories) {
        this.deviceFactories = deviceFactories;
    }

    /**
     * Gets the best matching device factory.
     *
     * @param device the zigbee device
     * @return the best matching device factory.
     */
    public DeviceFactory getBestDeviceProxyFactory(final ZigBeeEndpoint device) {
        synchronized (deviceFactories) {
            DeviceFactory bestMatchingFactory = null;
            int bestMatching = -1;

            for (DeviceFactory deviceFactory : deviceFactories.values()) {
                final int matching = deviceFactory.hasMatch(device);
                if (matching > bestMatching) {
                    bestMatchingFactory = deviceFactory;
                    bestMatching = matching;
                }
            }
            
            if(bestMatching == 0) {
            	return null;
            }

            return bestMatchingFactory;
        }
    }

    /**
     * Adds device.
     * @param device the device
     */
    public void addDevice(final Device device) {
        synchronized (devices) {
            devices.add(device);
        }
        synchronized (idDeviceMap) {
            idDeviceMap.put(device.getEndpoint().getEndpointId(), device);
        }
        notifyDeviceAdded(device);
    }

    /**
     * Updates device.
     * @param device the device
     */
    public void updateDevice(final Device device) {
        notifyDeviceUpdated(device);
    }

    /**
     * Removes device.
     * @param device the device
     */
    public void removeDevice(final Device device) {
        notifyDeviceRemoved(device);
        synchronized (devices) {
            devices.remove(device);
        }
        synchronized (idDeviceMap) {
            idDeviceMap.remove(device.getEndpoint().getDeviceTypeId());
        }
    }

    /**
     * Adds device listener.
     * @param deviceListener the device listener
     */
    public void addDeviceListener(final DeviceListener deviceListener) {
        synchronized (deviceListeners) {
            deviceListeners.add(deviceListener);
        }
    }

    /**
     * Removes device listener.
     * @param deviceListener the device listener
     */
    public void removeDeviceListener(final DeviceListener deviceListener) {
        synchronized (deviceListeners) {
            deviceListeners.remove(deviceListener);
        }
    }

    /**
     * Notifies listeners that device has been added.
     * @param device the device
     */
    public void notifyDeviceAdded(final Device device) {
        synchronized (deviceListeners) {
            for (final DeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceAdded(device);
            }
        }
    }

    /**
     * Notifies listeners that device has been updated.
     * @param device the device
     */
    public void notifyDeviceUpdated(final Device device) {
        synchronized (deviceListeners) {
            for (final DeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceUpdated(device);
            }
        }
    }

    /**
     * Notifies listeners that device has been removed.
     * @param device the device
     */
    public void notifyDeviceRemoved(final Device device) {
        synchronized (deviceListeners) {
            for (final DeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceRemoved(device);
            }
        }
    }

    /**
     * Gets device by device ID.
     * @param deviceId the device ID
     * @return the device
     */
    public Device getDevice(final String deviceId) {
        synchronized (idDeviceMap) {
            return idDeviceMap.get(deviceId);
        }
    }

    /**
     * Gets list of devices.
     * @return a {@link List} of ZigBee devices ({@link Device})
     */
    public List<Device> getDevices() {
        synchronized (devices) {
            return new ArrayList(devices);
        }
    }
}
