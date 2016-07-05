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
     * The group label.
     */
    private String label;

    /**
     * Default constructor.
     */
    public ZigBeeGroup() {
    }

    /**
     * Constructor which sets group ID.
     * @param groupId the group ID
     * @param label the group label
     */
    public ZigBeeGroup(final int groupId, final String label) {
        this.groupId = groupId;
        this.label = label;
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
     * Gets group label.
     * @return the group label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets group name.
     * @param label the group label
     */
    public void setLabel(final String label) {
        this.label = label;
    }
}
