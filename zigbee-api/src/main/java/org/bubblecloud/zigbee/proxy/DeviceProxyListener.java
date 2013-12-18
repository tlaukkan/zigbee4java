package org.bubblecloud.zigbee.proxy;

/**
 * Created with IntelliJ IDEA.
 * User: tlaukkan
 * Date: 12/15/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DeviceProxyListener {

    public void deviceAdded(final DeviceProxy device);

    public void deviceUpdated(final DeviceProxy device);

    public void deviceRemoved(final DeviceProxy device);

}
