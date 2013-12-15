package org.bubblecloud.zigbee;

import it.cnr.isti.zigbee.ha.driver.core.ClusterFactory;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactory;

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
}
