package org.bubblecloud.zigbee.model;

import org.bubblecloud.zigbee.proxy.HADeviceBase;

/**
 * Created with IntelliJ IDEA.
 * User: tlaukkan
 * Date: 12/15/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface HaDeviceListener {

    public void deviceAdded(final HADeviceBase device);

    public void deviceUpdated(final HADeviceBase device);

    public void deviceRemoved(final HADeviceBase device);

}
