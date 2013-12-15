package org.bubblecloud.zigbee;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.driver.core.ClusterFactory;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactory;
import org.bubblecloud.zigbee.model.DeviceListener;
import org.bubblecloud.zigbee.model.HaDeviceListener;

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

    private List<HADeviceFactory> deviceFactories = new ArrayList<HADeviceFactory>();

    private List<HADeviceBase> devices = new ArrayList<HADeviceBase>();

    private final List<HaDeviceListener> deviceListeners = new ArrayList<HaDeviceListener>();

    public ClusterFactory getClusterFactory() {
        return clusterFactory;
    }

    public void setClusterFactory(ClusterFactory clusterFactory) {
        this.clusterFactory = clusterFactory;
    }

    public List<HADeviceFactory> getDeviceFactories() {
        return deviceFactories;
    }

    public void setDeviceFactories(List<HADeviceFactory> deviceFactories) {
        this.deviceFactories = deviceFactories;
    }

    /**
     * Gets the best matching device factory.
     * @param device the zigbee device
     * @return the best matching device factory.
     */
    public HADeviceFactory getBestDeviceFactory(final ZigBeeDevice device) {
        synchronized (deviceFactories) {
            HADeviceFactory bestMatchingFactory = null;
            int bestMatching = -1;

            for (final HADeviceFactory factory : deviceFactories) {
                final int matching = factory.hasMatch(device);
                if ( matching > bestMatching ) {
                    bestMatchingFactory = factory;
                    bestMatching = matching;
                }
            }

            return bestMatchingFactory;
        }
    }

    public void addHaDevice(final HADeviceBase device) {
        devices.add(device);
        notifyDeviceAdded(device);
    }

    public void updateHaDevice(final HADeviceBase device) {
        notifyDeviceUpdated(device);
    }

    public void removeHaDevice(final HADeviceBase device) {
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

    public void notifyDeviceAdded(final HADeviceBase device) {
        synchronized (deviceListeners) {
            for (final HaDeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceAdded(device);
            }
        }
    }

    public void notifyDeviceUpdated(final HADeviceBase device) {
        synchronized (deviceListeners) {
            for (final HaDeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceUpdated(device);
            }
        }
    }

    public void notifyDeviceRemoved(final HADeviceBase device) {
        synchronized (deviceListeners) {
            for (final HaDeviceListener deviceListener : deviceListeners) {
                deviceListener.deviceRemoved(device);
            }
        }
    }

}
