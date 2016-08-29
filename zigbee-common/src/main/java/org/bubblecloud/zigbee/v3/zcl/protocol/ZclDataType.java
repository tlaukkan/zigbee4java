package org.bubblecloud.zigbee.v3.zcl.protocol;

import java.util.Calendar;
import org.bubblecloud.zigbee.v3.zcl.field.*;

public enum ZclDataType {
    BITMAP_16_BIT("16-bit Bitmap",Integer.class),
    BITMAP_8_BIT("8-bit Bitmap",Integer.class),
    BOOLEAN("Boolean",Boolean.class),
    CHARACTER_STRING("Character string",String.class),
    DATA_8_BIT("8-bit data",Integer.class),
    ENUMERATION_16_BIT("16-bit Enumeration",Integer.class),
    ENUMERATION_8_BIT("8-bit Enumeration",Integer.class),
    IEEE_ADDRESS("IEEE Address",Long.class),
    N_X_ATTRIBUTE_IDENTIFIER("N X Attribute identifier",AttributeIdentifier.class),
    N_X_ATTRIBUTE_INFORMATION("N X Attribute information",AttributeInformation.class),
    N_X_ATTRIBUTE_RECORD("N X Attribute record",AttributeRecord.class),
    N_X_ATTRIBUTE_REPORT("N X Attribute report",AttributeReport.class),
    N_X_ATTRIBUTE_REPORTING_CONFIGURATION_RECORD("N X Attribute reporting configuration record",AttributeReportingConfigurationRecord.class),
    N_X_ATTRIBUTE_SELECTOR("N X Attribute selector",Object.class),
    N_X_ATTRIBUTE_STATUS_RECORD("N X Attribute status record",AttributeStatusRecord.class),
    N_X_EXTENSION_FIELD_SET("N X Extension field set",ExtensionFieldSet.class),
    N_X_NEIGHBORS_INFORMATION("N X Neighbors information",NeighborInformation.class),
    N_X_READ_ATTRIBUTE_STATUS_RECORD("N X Read attribute status record",ReadAttributeStatusRecord.class),
    N_X_UNSIGNED_16_BIT_INTEGER("N X Unsigned 16-bit integer",Unsigned16BitInteger.class),
    N_X_UNSIGNED_8_BIT_INTEGER("N X Unsigned 8-bit integer",Unsigned8BitInteger.class),
    N_X_WRITE_ATTRIBUTE_RECORD("N X Write attribute record",WriteAttributeRecord.class),
    N_X_WRITE_ATTRIBUTE_STATUS_RECORD("N X Write attribute status record",WriteAttributeStatusRecord.class),
    OCTET_STRING("Octet string",String.class),
    SIGNED_16_BIT_INTEGER("Signed 16-bit integer",Integer.class),
    SIGNED_32_BIT_INTEGER("Signed 32-bit integer",Integer.class),
    SIGNED_8_BIT_INTEGER("Signed 8-bit integer",Integer.class),
    UNSIGNED_16_BIT_INTEGER("Unsigned 16-bit integer",Integer.class),
    UNSIGNED_32_BIT_INTEGER("Unsigned 32-bit integer",Integer.class),
    UNSIGNED_8_BIT_INTEGER("Unsigned 8-bit integer",Integer.class),
    UTCTIME("UTCTime",Calendar.class);

    private final String label;
    private final Class<?> dataClass;

    ZclDataType(final String label, final Class<?> dataClass) {
        this.label = label;
        this.dataClass = dataClass;
    }

    public String getLabel() { return label; }
    public Class<?> getDataClass() { return dataClass; }
}
