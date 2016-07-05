package org.bubblecloud.zigbee.v3;

/**
 * ZigBee group.
 */
public class ZigBeeGroup extends ZigBeeDestination {

    /**
     * The group ID.
     */
    private int groupId;

    /**
     * Constructor which sets group ID.
     * @param groupId the group ID
     */
    public ZigBeeGroup(final int groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets group ID.
     * @return the group ID.
     */
    public int getGroupId() {
        return groupId;
    }
}
