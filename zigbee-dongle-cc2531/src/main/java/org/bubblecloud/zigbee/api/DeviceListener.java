package org.bubblecloud.zigbee.api;

/**
 * Created with IntelliJ IDEA.
 * User: tlaukkan
 * Date: 12/15/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DeviceListener {

    public void deviceAdded(final Device device);

    public void deviceUpdated(final Device device);

    public void deviceRemoved(final Device device);

}
