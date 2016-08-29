package org.bubblecloud.zigbee.v3;

/**
 * Defines an abstract ZigBee address
 */
public abstract class ZigBeeAddress {
    /**
     * Check whether this destination is ZigBee group.
     * @return TRUE if this is ZigBee group.
     */
    public abstract boolean isGroup();
}
