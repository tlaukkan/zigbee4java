package org.bubblecloud.zigbee.tools.zcl;

import java.util.HashMap;
import java.util.Map;

public class ZclDataType {

    public static class DataTypeMap {
        public String dataClass;
        public Integer id;
        public Integer length;
        public Integer invalid;

        DataTypeMap(String dataClass, int id, int length) {
            this(dataClass, id, length, 0);
        }

        DataTypeMap(String dataClass, int id, int length, int invalid) {
            this.dataClass = dataClass;
            this.id = id;
            this.length = length;
            this.invalid = invalid;
        }
    };

    final static Map<String, DataTypeMap> dataTypeMapping;

    static {
        dataTypeMapping = new HashMap<String, DataTypeMap>();

        dataTypeMapping.put("CHARACTER_STRING", new DataTypeMap("String", 0x42, -1));
        dataTypeMapping.put("IEEE_ADDRESS", new DataTypeMap("Long", 0xf0, 8, 0xffffffff));
        dataTypeMapping.put("N_X_EXTENSION_FIELD_SET", new DataTypeMap("List<ExtensionFieldSet>", 0, 0));
        dataTypeMapping.put("N_X_NEIGHBORS_INFORMATION", new DataTypeMap("List<NeighborInformation>", 0, 0));
        dataTypeMapping.put("N_X_UNSIGNED_16_BIT_INTEGER", new DataTypeMap("List<Unsigned16BitInteger>", 0, 0));
        dataTypeMapping.put("N_X_UNSIGNED_8_BIT_INTEGER", new DataTypeMap("List<Unsigned8BitInteger>", 0, 0));
        dataTypeMapping.put("N_X_ATTRIBUTE_IDENTIFIER", new DataTypeMap("List<AttributeIdentifier>", 0, 0));
        dataTypeMapping.put("N_X_READ_ATTRIBUTE_STATUS_RECORD",
                new DataTypeMap("List<ReadAttributeStatusRecord>", 0, 0));
        dataTypeMapping.put("N_X_WRITE_ATTRIBUTE_RECORD", new DataTypeMap("List<WriteAttributeRecord>", 0, 0));
        dataTypeMapping.put("N_X_WRITE_ATTRIBUTE_STATUS_RECORD", new DataTypeMap("List<WriteAttributeStatusRecord>", 0,
                0));
        dataTypeMapping.put("N_X_ATTRIBUTE_REPORTING_CONFIGURATION_RECORD", new DataTypeMap(
                "List<AttributeReportingConfigurationRecord>", 0, 0));
        dataTypeMapping.put("N_X_ATTRIBUTE_STATUS_RECORD", new DataTypeMap("List<AttributeStatusRecord>", 0, 0));
        dataTypeMapping.put("N_X_ATTRIBUTE_RECORD", new DataTypeMap("List<AttributeRecord>", 0, 0));
        dataTypeMapping.put("N_X_ATTRIBUTE_REPORT", new DataTypeMap("List<AttributeReport>", 0, 0));
        dataTypeMapping.put("N_X_ATTRIBUTE_INFORMATION", new DataTypeMap("List<AttributeInformation>", 0, 0));
        dataTypeMapping.put("N_X_ATTRIBUTE_SELECTOR", new DataTypeMap("Object", 0, 0));
        dataTypeMapping.put("BOOLEAN", new DataTypeMap("Boolean", 0x10, 1, 0xff));
        dataTypeMapping.put("SIGNED_32_BIT_INTEGER", new DataTypeMap("Integer", 0x2b, 4, 0x80000000));
        dataTypeMapping.put("SIGNED_16_BIT_INTEGER", new DataTypeMap("Integer", 0x29, 2, 0x8000));
        dataTypeMapping.put("SIGNED_8_BIT_INTEGER", new DataTypeMap("Integer", 0x28, 1, 0x80));
        dataTypeMapping.put("UNSIGNED_16_BIT_INTEGER", new DataTypeMap("Integer", 0x21, 2, 0xffff));
        dataTypeMapping.put("UNSIGNED_32_BIT_INTEGER", new DataTypeMap("Integer", 0x23, 4, 0xffffffff));
        dataTypeMapping.put("UNSIGNED_8_BIT_INTEGER", new DataTypeMap("Integer", 0x20, 1, 0xff));
        dataTypeMapping.put("BITMAP_16_BIT", new DataTypeMap("Integer", 0x19, 2));
        dataTypeMapping.put("BITMAP_8_BIT", new DataTypeMap("Integer", 0x18, 1));
        dataTypeMapping.put("ENUMERATION_16_BIT", new DataTypeMap("Integer", 0x31, 2, 0xffff));
        dataTypeMapping.put("ENUMERATION_8_BIT", new DataTypeMap("Integer", 0x30, 1, 0xff));
        dataTypeMapping.put("DATA_8_BIT", new DataTypeMap("Integer", 0x08, 1));
        dataTypeMapping.put("OCTET_STRING", new DataTypeMap("String", 0x41, -1));
        dataTypeMapping.put("UTCTIME", new DataTypeMap("Calendar", 0xe2, 4, 0xffffffff));
    };

    public static Map<String, DataTypeMap> getDataTypeMapping() {
        return dataTypeMapping;
    }
}
