package org.bubblecloud.zigbee.v3;

/**
 * ZigBee destination can be either group or device.
 */
public class ZigBeeDestination {
    /**
     * Check whether this destination is ZigBee group.
     * @return TRUE if this is ZigBee group.
     */
    public boolean isGroup() {
        return this instanceof ZigBeeGroup;
    }

    /**
     * Dummy setter for JSON RPC deserialization.
     * @param value the dummy value
     */
    public void setGroup(final boolean value) {
    }
}
