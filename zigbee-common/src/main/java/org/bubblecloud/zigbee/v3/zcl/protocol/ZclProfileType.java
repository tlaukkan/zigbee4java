package org.bubblecloud.zigbee.v3.zcl.protocol;

public enum ZclProfileType {
    HOME_AUTOMATION(260, "Home Automation");

    private final int id;
    private final String label;

    ZclProfileType(final int id, final String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }
    public String toString() { return label; }

}
