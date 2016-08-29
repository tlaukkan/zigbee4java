package org.bubblecloud.zigbee.v3;

/**
 * ZigBee destination can be either group or device.
 */
public abstract class ZigBeeAddress {
    /**
     * Check whether this destination is ZigBee group.
     * @return TRUE if this is ZigBee group.
     */
    public abstract boolean isGroup();
  //      return this instanceof ZigBeeGroupDestination;
//    }

    /**
     * Dummy setter for JSON RPC deserialization.
     * @param value the dummy value
     */
//    public void setGroup(final boolean value) {
  //  }
}
