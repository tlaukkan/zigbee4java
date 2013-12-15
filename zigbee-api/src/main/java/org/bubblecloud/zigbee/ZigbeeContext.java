package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.proxy.DeviceProxyListener;
import org.bubblecloud.zigbee.network.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.ClusterFactory;
import org.bubblecloud.zigbee.proxy.DeviceProxyBase;
import org.bubblecloud.zigbee.proxy.DeviceProxyFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tlaukkan
 * Date: 12/15/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class ZigbeeContext {

    private ClusterFactory clusterFactory;

    private List<DeviceProxyFactory> deviceProxyFactories = new ArrayList<DeviceProxyFactory>();

    private List<DeviceProxyBase> deviceProxies = new ArrayList<DeviceProxyBase>();

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
     * @param device the zigbee device
     * @return the best matching device factory.
     */
    public DeviceProxyFactory getBestDeviceProxyFactory(final ZigBeeDevice device) {
        synchronized (deviceProxyFactories) {
            DeviceProxyFactory bestMatchingFactory = null;
            int bestMatching = -1;

            for (final DeviceProxyFactory factory : deviceProxyFactories) {
                final int matching = factory.hasMatch(device);
                if ( matching > bestMatching ) {
                    bestMatchingFactory = factory;
                    bestMatching = matching;
                }
            }

            return bestMatchingFactory;
        }
    }

    public void addDeviceProxy(final DeviceProxyBase device) {
        deviceProxies.add(device);
        notifyDeviceProxyAdded(device);
    }

    public void updateDeviceProxy(final DeviceProxyBase device) {
        notifyDeviceProxyUpdated(device);
    }

    public void removeDeviceProxy(final DeviceProxyBase device) {
        notifyDeviceProxyRemoved(device);
        deviceProxies.remove(device);
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

    public void notifyDeviceProxyAdded(final DeviceProxyBase device) {
        synchronized (deviceProxyListeners) {
            for (final DeviceProxyListener deviceListener : deviceProxyListeners) {
                deviceListener.deviceAdded(device);
            }
        }
    }

    public void notifyDeviceProxyUpdated(final DeviceProxyBase device) {
        synchronized (deviceProxyListeners) {
            for (final DeviceProxyListener deviceListener : deviceProxyListeners) {
                deviceListener.deviceUpdated(device);
            }
        }
    }

    public void notifyDeviceProxyRemoved(final DeviceProxyBase device) {
        synchronized (deviceProxyListeners) {
            for (final DeviceProxyListener deviceListener : deviceProxyListeners) {
                deviceListener.deviceRemoved(device);
            }
        }
    }

}
