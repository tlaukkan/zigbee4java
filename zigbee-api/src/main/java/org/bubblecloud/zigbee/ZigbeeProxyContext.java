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

import org.bubblecloud.zigbee.proxy.*;
import org.bubblecloud.zigbee.network.ZigbeeEndpoint;

import java.util.*;

/**
 * Zigbee proxy context.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigbeeProxyContext {

    private ClusterFactory clusterFactory;

    private List<DeviceProxyFactory> deviceProxyFactories = new ArrayList<DeviceProxyFactory>();

    private Map<String, DeviceProxy> deviceProxies = new HashMap<String, DeviceProxy>();

    private final List<DeviceProxyListener> deviceProxyListeners = new ArrayList<DeviceProxyListener>();

    public ClusterFactory getClusterFactory() {
        return clusterFactory;
    }

    public void setClusterFactory(ClusterFactory clusterFactory) {
        this.clusterFactory = clusterFactory;
    }

    public List<DeviceProxyFactory> getDeviceProxyFactories() {
        return deviceProxyFactories;
    }

    public void setDeviceProxyFactories(List<DeviceProxyFactory> deviceProxyFactories) {
        this.deviceProxyFactories = deviceProxyFactories;
    }

    /**
     * Gets the best matching device factory.
     *
     * @param device the zigbee device
     * @return the best matching device factory.
     */
    public DeviceProxyFactory getBestDeviceProxyFactory(final ZigbeeEndpoint device) {
        synchronized (deviceProxyFactories) {
            DeviceProxyFactory bestMatchingFactory = null;
            int bestMatching = -1;

            for (final DeviceProxyFactory factory : deviceProxyFactories) {
                final int matching = factory.hasMatch(device);
                if (matching > bestMatching) {
                    bestMatchingFactory = factory;
                    bestMatching = matching;
                }
            }

            return bestMatchingFactory;
        }
    }

    public void addDeviceProxy(final DeviceProxy device) {
        deviceProxies.put(device.getDevice().getEndpointId(), device);
        notifyDeviceProxyAdded(device);
    }

    public void updateDeviceProxy(final DeviceProxy device) {
        notifyDeviceProxyUpdated(device);
    }

    public void removeDeviceProxy(final DeviceProxy device) {
        notifyDeviceProxyRemoved(device);
        deviceProxies.remove(device.getDevice().getDeviceId());
    }

    public void addDeviceProxyListener(final DeviceProxyListener deviceListener) {
        synchronized (deviceProxyListeners) {
            deviceProxyListeners.add(deviceListener);
        }
    }

    public void removeDeviceProxyListener(final DeviceProxyListener deviceListener) {
        synchronized (deviceProxyListeners) {
            deviceProxyListeners.remove(deviceListener);
        }
    }

    public void notifyDeviceProxyAdded(final DeviceProxy device) {
        synchronized (deviceProxyListeners) {
            for (final DeviceProxyListener deviceListener : deviceProxyListeners) {
                deviceListener.deviceAdded(device);
            }
        }
    }

    public void notifyDeviceProxyUpdated(final DeviceProxy device) {
        synchronized (deviceProxyListeners) {
            for (final DeviceProxyListener deviceListener : deviceProxyListeners) {
                deviceListener.deviceUpdated(device);
            }
        }
    }

    public void notifyDeviceProxyRemoved(final DeviceProxy device) {
        synchronized (deviceProxyListeners) {
            for (final DeviceProxyListener deviceListener : deviceProxyListeners) {
                deviceListener.deviceRemoved(device);
            }
        }
    }

    public DeviceProxy getDeviceProxy(final String deviceUuid) {
        return deviceProxies.get(deviceUuid);
    }

    public List<DeviceProxy> getDeviceProxies() {
        return Collections.unmodifiableList(new ArrayList(deviceProxies.values()));
    }
}
