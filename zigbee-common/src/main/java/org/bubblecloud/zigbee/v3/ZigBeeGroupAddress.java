package org.bubblecloud.zigbee.v3;

/**
 * ZigBee group.
 */
public class ZigBeeGroupAddress extends ZigBeeAddress {

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
    public ZigBeeGroupAddress() {
        int i = 0;
    }

    /**
     * Constructor which sets group ID.
     * 
     * @param groupId
     *            the group ID
     */
    public ZigBeeGroupAddress(final int groupId) {
        this.groupId = groupId;
    }

    /**
     * Constructor which sets group ID and label.
     * 
     * @param groupId
     *            the group ID
     * @param label
     *            the group label
     */
    public ZigBeeGroupAddress(final int groupId, final String label) {
        this.groupId = groupId;
        this.label = label;
    }

    /**
     * Gets group ID.
     * 
     * @return the group ID.
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Sets group ID.
     * 
     * @param groupId
     *            the group ID
     */
    public void setGroupId(final int groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets group label.
     * 
     * @return the group label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets group name.
     * 
     * @param label
     *            the group label
     */
    public void setLabel(final String label) {
        this.label = label;
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    @Override
    public String toString() {
        return groupId + "/" + label;
    }
}
