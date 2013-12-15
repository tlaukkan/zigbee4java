package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.glue.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.ClusterFactory;
import org.bubblecloud.zigbee.proxy.DeviceProxyBase;
import org.bubblecloud.zigbee.proxy.DeviceProxyFactory;
import org.bubblecloud.zigbee.network.glue.HaDeviceListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tlaukkan
 * Date: 12/15/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class BundleContext {
    private ClusterFactory clusterFactory;

    private List<DeviceProxyFactory> deviceFactories = new ArrayList<DeviceProxyFactory>();

    private List<DeviceProxyBase> devices = new ArrayList<DeviceProxyBase>();

    private final List<HaDeviceListener> deviceListeners = new ArrayList<HaDeviceListener>();

    public ClusterFactory getClusterFactory() {
        return clusterFactory;
    }

    public void setClusterFactory(ClusterFactory clusterFactory) {
        this.clusterFactory = clusterFactory;
    }

    public List<DeviceProxyFactory> getDeviceFactories() {
        return deviceFactories;
    }

    public void setDeviceFactories(List<DeviceProxyFactory> deviceFactories) {
        this.deviceFactories = deviceFactories;
    }

    /**
     * Gets the best matching device factory.
     * @param device the zigbee device
     * @return the best matching device factory.
     */
    public DeviceProxyFactory getBestDeviceFactory(final ZigBeeDevice device) {
        synchronized (deviceFactories) {
            DeviceProxyFactory bestMatchingFactory = null;
            int bestMatching = -1;

            for (final DeviceProxyFactory factory : deviceFactories) {
                final int matching = factory.hasMatch(device);
                if ( matching > bestMatching ) {
                    bestMatchingFactory = factory;
                    bestMatching = matching;
                }
            }

            return bestMatchingFactory;
        }
    }

    public void addHaDevice(final DeviceProxyBase device) {
        devices.add(device);
        notifyDeviceAdded(device);
    }

    public void updateHaDevice(final DeviceProxyBase device) {
        notifyDeviceUpdated(device);
    }

    public void removeHaDevice(final DeviceProxyBase device) {
        notifyDeviceRemoved(device);
        devices.remove(device);
    }

    public void addDeviceListener(final HaDeviceListener deviceListener) {
        synchronized (deviceListeners) {
            deviceListeners.add(deviceListener);
        }
    }

    public void removeDeviceListener(final HaDeviceListener deviceListener) {
        synchronized (deviceListeners) {
            deviceListeners.remove(deviceListener);
        }
    }

    public void notifyDeviceAdded(final DeviceProxyBase device) {
        synchronized (deviceListeners) {
            for (final HaDeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceAdded(device);
            }
        }
    }

    public void notifyDeviceUpdated(final DeviceProxyBase device) {
        synchronized (deviceListeners) {
            for (final HaDeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceUpdated(device);
            }
        }
    }

    public void notifyDeviceRemoved(final DeviceProxyBase device) {
        synchronized (deviceListeners) {
            for (final HaDeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceRemoved(device);
            }
        }
    }

}
