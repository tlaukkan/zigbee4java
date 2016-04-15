package org.bubblecloud.zigbee.network.zcl.protocol;

public enum ZclDataType {
    CHARACTER_STRING("Character string"),
    CLUSTER_ID("Cluster ID"),
    IEEE_ADDRESS("IEEE address"),
    N_X_EXTENSION_FIELD_SET("N X Extension field set"),
    N_X_NEIGHBORS_INFORMATION("N X Neighbors information"),
    N_X_UNSIGNED_16_BIT_INTEGER("N X Unsigned 16-bit integer"),
    N_X_UNSIGNED_8_BIT_INTEGER("N X Unsigned 8-bit integer"),
    SIGNED_16_BIT_INTEGER("Signed 16-bit integer"),
    SIGNED_8_BIT_INTEGER("Signed 8-bit integer"),
    UNSIGNED_16_BIT_INTEGER("Unsigned 16-bit integer"),
    UNSIGNED_32_BIT_INTEGER("Unsigned 32-bit integer"),
    UNSIGNED_8_BIT_INTEGER("Unsigned 8-bit integer"),
    _16_BIT_BITMAP("16-bit bitmap"),
    _16_BIT_ENUMERATION("16-bit enumeration"),
    _8_BIT_BITMAP("8-bit bitmap"),
    _8_BIT_DATA("8-bit data"),
    _8_BIT_ENUMERATION("8-bit enumeration");
    private final String label;

    ZclDataType(final String label) {
        this.label = label;
    }

    public String getLabel() { return label; }

}
