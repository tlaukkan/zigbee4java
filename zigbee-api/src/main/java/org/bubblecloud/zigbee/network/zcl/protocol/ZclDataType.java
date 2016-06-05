package org.bubblecloud.zigbee.network.zcl.protocol;

import org.bubblecloud.zigbee.network.packet.ZToolAddress64;
import org.bubblecloud.zigbee.network.zcl.type.*;

public enum ZclDataType {
    BOOLEAN("Boolean",Boolean.class),
    CHARACTER_STRING("Character string",String.class),
    CLUSTER_ID("Cluster ID",Object.class),
    IEEE_ADDRESS("IEEE address",ZToolAddress64.class),
    N_X_ATTRIBUTE_IDENTIFIER("N X Attribute identifier",AttributeIdentifier.class),
    N_X_ATTRIBUTE_INFORMATION("N X Attribute information",AttributeInformation.class),
    N_X_ATTRIBUTE_RECORD("N X Attribute record",Object.class),
    N_X_ATTRIBUTE_REPORT("N X Attribute report",Object.class),
    N_X_ATTRIBUTE_REPORTING_CONFIGURATION_RECORD("N X Attribute reporting configuration record",Object.class),
    N_X_ATTRIBUTE_SELECTOR("N X Attribute selector",Object.class),
    N_X_ATTRIBUTE_STATUS_RECORD("N X Attribute status record",Object.class),
    N_X_EXTENSION_FIELD_SET("N X Extension field set",Object.class),
    N_X_NEIGHBORS_INFORMATION("N X Neighbors information",Object.class),
    N_X_READ_ATTRIBUTE_STATUS_RECORD("N X Read attribute status record",ReadAttributeStatusRecord.class),
    N_X_UNSIGNED_16_BIT_INTEGER("N X Unsigned 16-bit integer",Object.class),
    N_X_UNSIGNED_8_BIT_INTEGER("N X Unsigned 8-bit integer",Object.class),
    N_X_WRITE_ATTRIBUTE_RECORD("N X Write attribute record",Object.class),
    N_X_WRITE_ATTRIBUTE_STATUS_RECORD("N X Write attribute status record",Object.class),
    SIGNED_16_BIT_INTEGER("Signed 16-bit integer",Integer.class),
    SIGNED_8_BIT_INTEGER("Signed 8-bit integer",Integer.class),
    UNSIGNED_16_BIT_INTEGER("Unsigned 16-bit integer",Integer.class),
    UNSIGNED_32_BIT_INTEGER("Unsigned 32-bit integer",Integer.class),
    UNSIGNED_8_BIT_INTEGER("Unsigned 8-bit integer",Integer.class),
    _16_BIT_BITMAP("16-bit bitmap",Integer.class),
    _16_BIT_ENUMERATION("16-bit enumeration",Integer.class),
    _8_BIT_BITMAP("8-bit bitmap",Integer.class),
    _8_BIT_DATA("8-bit data",Integer.class),
    _8_BIT_ENUMERATION("8-bit enumeration",Integer.class);
    private final String label;
    private final Class dataClass;

    ZclDataType(final String label, final Class dataClass) {
        this.label = label;
        this.dataClass = dataClass;
    }

    public String getLabel() { return label; }
    public Class<?> getDataClass() { return dataClass; }

}
