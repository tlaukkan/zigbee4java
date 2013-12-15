package org.bubblecloud.zigbee.model;

import it.cnr.isti.zigbee.api.ZigBeeDevice;

/**
 * Created with IntelliJ IDEA.
 * User: tlaukkan
 * Date: 12/15/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DeviceListener {

    public void deviceAdded(final ZigBeeDevice device);

    public void deviceUpdated(final ZigBeeDevice device);

    public void deviceRemoved(final ZigBeeDevice device);

}
