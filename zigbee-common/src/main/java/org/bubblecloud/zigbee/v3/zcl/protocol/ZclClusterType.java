package org.bubblecloud.zigbee.v3.zcl.protocol;

import java.util.HashMap;
import java.util.Map;

public enum ZclClusterType {
    BASIC(0, ZclProfileType.HOME_AUTOMATION, "Basic"),
    POWER_CONFIGURATION(1, ZclProfileType.HOME_AUTOMATION, "Power configuration"),
    DEVICE_TEMPERATURE_CONFIGURATION(2, ZclProfileType.HOME_AUTOMATION, "Device Temperature Configuration"),
    IDENTIFY(3, ZclProfileType.HOME_AUTOMATION, "Identify"),
    GROUPS(4, ZclProfileType.HOME_AUTOMATION, "Groups"),
    SCENES(5, ZclProfileType.HOME_AUTOMATION, "Scenes"),
    ON_OFF(6, ZclProfileType.HOME_AUTOMATION, "On/off"),
    ON_OFF_SWITCH_CONFIGURATION(7, ZclProfileType.HOME_AUTOMATION, "On/off Switch Configuration"),
    LEVEL_CONTROL(8, ZclProfileType.HOME_AUTOMATION, "Level Control"),
    ALARMS(9, ZclProfileType.HOME_AUTOMATION, "Alarms"),
    TIME(10, ZclProfileType.HOME_AUTOMATION, "Time"),
    RSSI_LOCATION(11, ZclProfileType.HOME_AUTOMATION, "RSSI Location"),
    ANALOG_INPUT__BASIC_(12, ZclProfileType.HOME_AUTOMATION, "Analog Input (Basic)"),
    ANALOG_OUTPUT__BASIC_(13, ZclProfileType.HOME_AUTOMATION, "Analog Output (Basic)"),
    ANALOG_VALUE__BASIC_(14, ZclProfileType.HOME_AUTOMATION, "Analog Value (Basic)"),
    BINARY_INPUT__BASIC_(15, ZclProfileType.HOME_AUTOMATION, "Binary Input (Basic)"),
    BINARY_OUTPUT__BASIC_(16, ZclProfileType.HOME_AUTOMATION, "Binary Output (Basic)"),
    BINARY_VALUE__BASIC_(17, ZclProfileType.HOME_AUTOMATION, "Binary Value (Basic)"),
    MULTISTATE_INPUT__BASIC_(18, ZclProfileType.HOME_AUTOMATION, "Multistate Input (Basic)"),
    MULTISTATE_OUTPUT__BASIC_(19, ZclProfileType.HOME_AUTOMATION, "Multistate Output (Basic)"),
    MULTISTATE_VALUE__BASIC_(20, ZclProfileType.HOME_AUTOMATION, "Multistate Value (Basic)"),
    COMMISSIONING(21, ZclProfileType.HOME_AUTOMATION, "Commissioning"),
    SHADE_CONFIGURATION(256, ZclProfileType.HOME_AUTOMATION, "Shade Configuration"),
    DOOR_LOCK(257, ZclProfileType.HOME_AUTOMATION, "Door Lock"),
    PUMP_CONFIGURATION_AND_CONTROL(512, ZclProfileType.HOME_AUTOMATION, "Pump Configuration and Control"),
    THERMOSTAT(513, ZclProfileType.HOME_AUTOMATION, "Thermostat"),
    FAN_CONTROL(514, ZclProfileType.HOME_AUTOMATION, "Fan Control"),
    DEHUMIDIFICATION_CONTROL(515, ZclProfileType.HOME_AUTOMATION, "Dehumidification Control"),
    THERMOSTAT_USER_INTERFACE_CONFIGURATION(516, ZclProfileType.HOME_AUTOMATION, "Thermostat User Interface Configuration"),
    COLOR_CONTROL(768, ZclProfileType.HOME_AUTOMATION, "Color control"),
    BALLAST_CONFIGURATION(769, ZclProfileType.HOME_AUTOMATION, "Ballast Configuration"),
    ILLUMINANCE_MEASUREMENT(1024, ZclProfileType.HOME_AUTOMATION, "Illuminance measurement"),
    ILLUMINANCE_LEVEL_SENSING(1025, ZclProfileType.HOME_AUTOMATION, "Illuminance level sensing"),
    TEMPERATURE_MEASUREMENT(1026, ZclProfileType.HOME_AUTOMATION, "Temperature measurement"),
    PRESSURE_MEASUREMENT(1027, ZclProfileType.HOME_AUTOMATION, "Pressure measurement"),
    FLOW_MEASUREMENT(1028, ZclProfileType.HOME_AUTOMATION, "Flow measurement"),
    RELATIVE_HUMIDITY_MEASUREMENT(1029, ZclProfileType.HOME_AUTOMATION, "Relative humidity measurement"),
    OCCUPANCY_SENSING(1030, ZclProfileType.HOME_AUTOMATION, "Occupancy sensing"),
    IAS_ZONE(1280, ZclProfileType.HOME_AUTOMATION, "IAS Zone"),
    IAS_ACE(1281, ZclProfileType.HOME_AUTOMATION, "IAS ACE"),
    IAS_WD(1282, ZclProfileType.HOME_AUTOMATION, "IAS WD"),
    GENERIC_TUNNEL(1536, ZclProfileType.HOME_AUTOMATION, "Generic Tunnel"),
    BACNET_PROTOCOL_TUNNEL(1537, ZclProfileType.HOME_AUTOMATION, "BACnet Protocol Tunnel"),
    ANALOG_INPUT__BACNET_REGULAR_(1538, ZclProfileType.HOME_AUTOMATION, "Analog Input (BACnet Regular)"),
    ANALOG_INPUT__BACNET_EXTENDED_(1539, ZclProfileType.HOME_AUTOMATION, "Analog Input (BACnet Extended)"),
    ANALOG_OUTPUT__BACNET_REGULAR_(1540, ZclProfileType.HOME_AUTOMATION, "Analog Output (BACnet Regular)"),
    ANALOG_OUTPUT__BACNET_EXTENDED_(1541, ZclProfileType.HOME_AUTOMATION, "Analog Output (BACnet Extended)"),
    ANALOG_VALUE__BACNET_REGULAR_(1542, ZclProfileType.HOME_AUTOMATION, "Analog Value (BACnet Regular)"),
    ANALOG_VALUE__BACNET_EXTENDED_(1543, ZclProfileType.HOME_AUTOMATION, "Analog Value (BACnet Extended)"),
    BINARY_INPUT__BACNET_REGULAR_(1544, ZclProfileType.HOME_AUTOMATION, "Binary Input (BACnet Regular)"),
    BINARY_INPUT__BACNET_EXTENDED_(1545, ZclProfileType.HOME_AUTOMATION, "Binary Input (BACnet Extended)"),
    BINARY_OUTPUT__BACNET_REGULAR_(1546, ZclProfileType.HOME_AUTOMATION, "Binary Output (BACnet Regular)"),
    BINARY_OUTPUT__BACNET_EXTENDED_(1547, ZclProfileType.HOME_AUTOMATION, "Binary Output (BACnet Extended)"),
    BINARY_VALUE__BACNET_REGULAR_(1548, ZclProfileType.HOME_AUTOMATION, "Binary Value (BACnet Regular)"),
    BINARY_VALUE__BACNET_EXTENDED_(1549, ZclProfileType.HOME_AUTOMATION, "Binary Value (BACnet Extended)"),
    MULTISTATE_INPUT__BACNET_REGULAR_(1550, ZclProfileType.HOME_AUTOMATION, "Multistate Input (BACnet Regular)"),
    MULTISTATE_INPUT__BACNET_EXTENDED_(1551, ZclProfileType.HOME_AUTOMATION, "Multistate Input (BACnet Extended)"),
    MULTISTATE_OUTPUT__BACNET_REGULAR_(1552, ZclProfileType.HOME_AUTOMATION, "Multistate Output (BACnet Regular)"),
    MULTISTATE_OUTPUT__BACNET_EXTENDED_(1553, ZclProfileType.HOME_AUTOMATION, "Multistate Output (BACnet Extended)"),
    MULTISTATE_VALUE__BACNET_REGULAR_(1554, ZclProfileType.HOME_AUTOMATION, "Multistate Value (BACnet Regular)"),
    MULTISTATE_VALUE__BACNET_EXTENDED_(1555, ZclProfileType.HOME_AUTOMATION, "Multistate Value (BACnet Extended)"),
    GENERAL(65535, ZclProfileType.HOME_AUTOMATION, "General");

    private static final Map<Integer, ZclClusterType> idValueMap = new HashMap<Integer, ZclClusterType>();

    private final int id;
    private final ZclProfileType profileType;
    private final String label;

    ZclClusterType(final int id, final ZclProfileType profileType, final String label) {
        this.id = id;
        this.profileType = profileType;
        this.label = label;
    }

    public int getId() { return id; }
    public ZclProfileType getProfileType() { return profileType; }
    public String getLabel() { return label; }
    public String toString() { return label; }

    public static ZclClusterType getValueById(final int id) {
        return idValueMap.get(id);
    }

    static {
        for (final ZclClusterType value : values()) {
            idValueMap.put(value.id, value);
        }
    }

}
