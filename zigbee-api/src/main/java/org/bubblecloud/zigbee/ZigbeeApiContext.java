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
import org.bubblecloud.zigbee.network.ZigbeeEndpoint;

import java.util.*;

/**
 * Zigbee proxy context.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigbeeApiContext {

    private ClusterFactory clusterFactory;

    private List<DeviceFactory> deviceProxyFactories = new ArrayList<DeviceFactory>();

    private List<Device> devices = new ArrayList<Device>();

    private Map<String, Device> idDeviceMap = new HashMap<String, Device>();

    private final List<DeviceListener> deviceListeners = new ArrayList<DeviceListener>();

    public ClusterFactory getClusterFactory() {
        return clusterFactory;
    }

    public void setClusterFactory(ClusterFactory clusterFactory) {
        this.clusterFactory = clusterFactory;
    }

    public List<DeviceFactory> getDeviceProxyFactories() {
        return deviceProxyFactories;
    }

    public void setDeviceProxyFactories(List<DeviceFactory> deviceProxyFactories) {
        this.deviceProxyFactories = deviceProxyFactories;
    }

    /**
     * Gets the best matching device factory.
     *
     * @param device the zigbee device
     * @return the best matching device factory.
     */
    public DeviceFactory getBestDeviceProxyFactory(final ZigbeeEndpoint device) {
        synchronized (deviceProxyFactories) {
            DeviceFactory bestMatchingFactory = null;
            int bestMatching = -1;

            for (final DeviceFactory factory : deviceProxyFactories) {
                final int matching = factory.hasMatch(device);
                if (matching > bestMatching) {
                    bestMatchingFactory = factory;
                    bestMatching = matching;
                }
            }

            return bestMatchingFactory;
        }
    }

    public void addDevice(final Device device) {
        devices.add(device);
        idDeviceMap.put(device.getEndpoint().getEndpointId(), device);
        notifyDeviceAdded(device);
    }

    public void updateDevice(final Device device) {
        notifyDeviceUpdated(device);
    }

    public void removeDevice(final Device device) {
        devices.remove(device);
        notifyDeviceRemoved(device);
        idDeviceMap.remove(device.getEndpoint().getDeviceTypeId());
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

    public void notifyDeviceAdded(final Device device) {
        synchronized (deviceListeners) {
            for (final DeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceAdded(device);
            }
        }
    }

    public void notifyDeviceUpdated(final Device device) {
        synchronized (deviceListeners) {
            for (final DeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceUpdated(device);
            }
        }
    }

    public void notifyDeviceRemoved(final Device device) {
        synchronized (deviceListeners) {
            for (final DeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceRemoved(device);
            }
        }
    }

    public Device getDevice(final String endPointId) {
        return idDeviceMap.get(endPointId);
    }

    public List<Device> getIdDeviceMap() {
        return new ArrayList(devices);
    }
}
