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
     * The group name.
     */
    private String name;

    /**
     * Default constructor.
     */
    public ZigBeeGroup() {
    }

    /**
     * Constructor which sets group ID.
     * @param groupId the group ID
     * @param name the group name
     */
    public ZigBeeGroup(final int groupId, final String name) {
        this.groupId = groupId;
        this.name = name;
    }

    /**
     * Gets group ID.
     * @return the group ID.
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Sets group ID.
     * @param groupId the group ID
     */
    public void setGroupId(final int groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets group name.
     * @return the group name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets group name.
     * @param name the group name
     */
    public void setName(final String name) {
        this.name = name;
    }
}
