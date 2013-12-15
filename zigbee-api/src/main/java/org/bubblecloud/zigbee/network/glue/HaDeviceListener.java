package org.bubblecloud.zigbee.network.glue;

import org.bubblecloud.zigbee.proxy.DeviceProxyBase;

/**
 * Created with IntelliJ IDEA.
 * User: tlaukkan
 * Date: 12/15/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface HaDeviceListener {

    public void deviceAdded(final DeviceProxyBase device);

    public void deviceUpdated(final DeviceProxyBase device);

    public void deviceRemoved(final DeviceProxyBase device);

}
