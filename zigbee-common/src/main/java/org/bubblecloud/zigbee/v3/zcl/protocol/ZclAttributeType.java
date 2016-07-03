package org.bubblecloud.zigbee.v3.zcl.protocol;

import org.bubblecloud.zigbee.v3.model.ZigBeeType;

import java.util.HashMap;
import java.util.Map;

public enum ZclAttributeType {
    BASIC_ZCL_VERSION(ZclClusterType.BASIC, 0, "ZCLVersion", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    BASIC_APPLICATION_VERSION(ZclClusterType.BASIC, 1, "ApplicationVersion", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    BASIC_STACK_VERSION(ZclClusterType.BASIC, 2, "StackVersion", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    BASIC_HW_VERSION(ZclClusterType.BASIC, 3, "HWVersion", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    BASIC_MANUFACTURER_NAME(ZclClusterType.BASIC, 4, "ManufacturerName", false, false, ZigBeeType.CharacterString, java.lang.String.class),
    BASIC_MODEL_IDENTIFIER(ZclClusterType.BASIC, 5, "ModelIdentifier", false, false, ZigBeeType.CharacterString, java.lang.String.class),
    BASIC_DATE_CODE(ZclClusterType.BASIC, 6, "DateCode", false, false, ZigBeeType.CharacterString, java.lang.String.class),
    BASIC_POWER_SOURCE(ZclClusterType.BASIC, 7, "PowerSource", false, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    BASIC_LOCATION_DESCRIPTION(ZclClusterType.BASIC, 16, "LocationDescription", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    BASIC_PHYSICAL_ENVIRONMENT(ZclClusterType.BASIC, 17, "PhysicalEnvironment", true, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    BASIC_DEVICE_ENABLED(ZclClusterType.BASIC, 18, "DeviceEnabled", true, false, ZigBeeType.Boolean, java.lang.Boolean.class),
    BASIC_ALARM_MASK(ZclClusterType.BASIC, 19, "AlarmMask", true, false, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    BASIC_DISABLE_LOCAL_CONFIG(ZclClusterType.BASIC, 20, "DisableLocalConfig", true, false, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    POWER_CONFIGURATION_MAINS_VOLTAGE(ZclClusterType.POWER_CONFIGURATION, 0, "MainsVoltage", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    POWER_CONFIGURATION_MAINS_FREQUENCY(ZclClusterType.POWER_CONFIGURATION, 1, "MainsFrequency", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    POWER_CONFIGURATION_MAINS_ALARM_MASK(ZclClusterType.POWER_CONFIGURATION, 16, "MainsAlarmMask", true, false, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    POWER_CONFIGURATION_MAINS_VOLTAGE_MIN_THRESHOLD(ZclClusterType.POWER_CONFIGURATION, 17, "MainsVoltageMinThreshold", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    POWER_CONFIGURATION_MAINS_VOLTAGE_MAX_THRESHOLD(ZclClusterType.POWER_CONFIGURATION, 18, "MainsVoltageMaxThreshold", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    POWER_CONFIGURATION_MAINS_VOLTAGE_DWELL_TRIP_POINT(ZclClusterType.POWER_CONFIGURATION, 19, "MainsVoltageDwellTripPoint", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    POWER_CONFIGURATION_BATTERY_VOLTAGE(ZclClusterType.POWER_CONFIGURATION, 32, "BatteryVoltage", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    POWER_CONFIGURATION_BATTERY_MANUFACTURER(ZclClusterType.POWER_CONFIGURATION, 48, "BatteryManufacturer", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    POWER_CONFIGURATION_BATTERY_SIZE(ZclClusterType.POWER_CONFIGURATION, 49, "BatterySize", true, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    POWER_CONFIGURATION_BATTERY_A_HR_RATING(ZclClusterType.POWER_CONFIGURATION, 50, "BatteryAHrRating", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    POWER_CONFIGURATION_BATTERY_QUANTITY(ZclClusterType.POWER_CONFIGURATION, 51, "BatteryQuantity", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    POWER_CONFIGURATION_BATTERY_RATED_VOLTAGE(ZclClusterType.POWER_CONFIGURATION, 52, "BatteryRatedVoltage", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    POWER_CONFIGURATION_BATTERY_ALARM_MASK(ZclClusterType.POWER_CONFIGURATION, 53, "BatteryAlarmMask", true, false, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    POWER_CONFIGURATION_BATTERY_VOLTAGE_MIN_THRESHOLD(ZclClusterType.POWER_CONFIGURATION, 54, "BatteryVoltageMinThreshold", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    DEVICE_TEMPERATURE_CONFIGURATION_CURRENT_TEMPERATURE(ZclClusterType.DEVICE_TEMPERATURE_CONFIGURATION, 0, "CurrentTemperature", false, false, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    DEVICE_TEMPERATURE_CONFIGURATION_MIN_TEMP_EXPERIENCED(ZclClusterType.DEVICE_TEMPERATURE_CONFIGURATION, 1, "MinTempExperienced", false, false, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    DEVICE_TEMPERATURE_CONFIGURATION_MAX_TEMP_EXPERIENCED(ZclClusterType.DEVICE_TEMPERATURE_CONFIGURATION, 2, "MaxTempExperienced", false, false, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    DEVICE_TEMPERATURE_CONFIGURATION_OVER_TEMP_TOTAL_DWELL(ZclClusterType.DEVICE_TEMPERATURE_CONFIGURATION, 3, "OverTempTotalDwell", false, false, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    DEVICE_TEMPERATURE_CONFIGURATION_DEVICE_TEMP_ALARM_MASK(ZclClusterType.DEVICE_TEMPERATURE_CONFIGURATION, 16, "DeviceTempAlarmMask", true, false, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    DEVICE_TEMPERATURE_CONFIGURATION_LOW_TEMP_THRESHOLD(ZclClusterType.DEVICE_TEMPERATURE_CONFIGURATION, 17, "LowTempThreshold", true, false, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    DEVICE_TEMPERATURE_CONFIGURATION_HIGH_TEMP_THRESHOLD(ZclClusterType.DEVICE_TEMPERATURE_CONFIGURATION, 18, "HighTempThreshold", true, false, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    DEVICE_TEMPERATURE_CONFIGURATION_LOW_TEMP_DWELL_TRIP_POINT(ZclClusterType.DEVICE_TEMPERATURE_CONFIGURATION, 19, "LowTempDwellTripPoint", true, false, ZigBeeType.UnsignedInteger24bit, java.lang.Integer.class),
    DEVICE_TEMPERATURE_CONFIGURATION_HIGH_TEMP_DWELL_TRIP_POINT(ZclClusterType.DEVICE_TEMPERATURE_CONFIGURATION, 20, "HighTempDwellTripPoint", true, false, ZigBeeType.UnsignedInteger24bit, java.lang.Integer.class),
    IDENTIFY_IDENTIFY_TIME(ZclClusterType.IDENTIFY, 0, "IdentifyTime", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    GROUPS_NAME_SUPPORT(ZclClusterType.GROUPS, 0, "NameSupport", false, false, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    SCENES_SCENE_COUNT(ZclClusterType.SCENES, 0, "SceneCount", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    SCENES_CURRENT_SCENE(ZclClusterType.SCENES, 1, "CurrentScene", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    SCENES_SCENE_VALID(ZclClusterType.SCENES, 3, "SceneValid", false, false, ZigBeeType.Boolean, java.lang.Boolean.class),
    SCENES_NAME_SUPPORT(ZclClusterType.SCENES, 4, "NameSupport", false, false, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    SCENES_LAST_CONFIGURED_BY(ZclClusterType.SCENES, 5, "LastConfiguredBy", false, false, ZigBeeType.IEEEAddress, java.lang.String.class),
    ON_OFF_ON_OFF(ZclClusterType.ON_OFF, 0, "OnOff", false, true, ZigBeeType.Boolean, java.lang.Boolean.class),
    ON_OFF_SWITCH_CONFIGURATION_SWITCH_TYPE(ZclClusterType.ON_OFF_SWITCH_CONFIGURATION, 0, "SwitchType", false, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    ON_OFF_SWITCH_CONFIGURATION_SWITCH_ACTIONS(ZclClusterType.ON_OFF_SWITCH_CONFIGURATION, 0, "SwitchActions", true, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    LEVEL_CONTROL_CURRENT_LEVEL(ZclClusterType.LEVEL_CONTROL, 0, "CurrentLevel", false, true, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    LEVEL_CONTROL_REMAINING_TIME(ZclClusterType.LEVEL_CONTROL, 1, "RemainingTime", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    LEVEL_CONTROL_ON_OFF_TRANSITION_TIME(ZclClusterType.LEVEL_CONTROL, 16, "OnOffTransitionTime", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    LEVEL_CONTROL_ON_LEVEL(ZclClusterType.LEVEL_CONTROL, 17, "OnLevel", true, true, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    ALARMS_ALARM_COUNT(ZclClusterType.ALARMS, 0, "AlarmCount", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    TIME_TIME(ZclClusterType.TIME, 0, "Time", true, false, ZigBeeType.UnsignedInteger32bit, java.lang.Long.class),
    TIME_TIME_STATUS(ZclClusterType.TIME, 1, "TimeStatus", true, false, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    ANALOG_INPUT__BASIC__DESCRIPTION(ZclClusterType.ANALOG_INPUT__BASIC_, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    ANALOG_INPUT__BASIC__MAX_PRESENT_VALUE(ZclClusterType.ANALOG_INPUT__BASIC_, 65, "MaxPresentValue", true, false, ZigBeeType.SinglePrecision, java.lang.Float.class),
    ANALOG_INPUT__BASIC__MIN_PRESENT_VALUE(ZclClusterType.ANALOG_INPUT__BASIC_, 69, "MinPresentValue", true, false, ZigBeeType.SinglePrecision, java.lang.Float.class),
    ANALOG_INPUT__BASIC__OUT_OF_SERVICE(ZclClusterType.ANALOG_INPUT__BASIC_, 81, "OutOfService", true, false, ZigBeeType.Boolean, java.lang.Boolean.class),
    ANALOG_INPUT__BASIC__PRESENT_VALUE(ZclClusterType.ANALOG_INPUT__BASIC_, 85, "PresentValue", true, true, ZigBeeType.SinglePrecision, java.lang.Float.class),
    ANALOG_INPUT__BASIC__RELIABILITY(ZclClusterType.ANALOG_INPUT__BASIC_, 103, "Reliability", true, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    ANALOG_INPUT__BASIC__RESOLUTION(ZclClusterType.ANALOG_INPUT__BASIC_, 106, "Resolution", true, false, ZigBeeType.SinglePrecision, java.lang.Float.class),
    ANALOG_INPUT__BASIC__STATUS_FLAGS(ZclClusterType.ANALOG_INPUT__BASIC_, 111, "StatusFlags", false, true, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    ANALOG_INPUT__BASIC__ENGINEERING_UNITS(ZclClusterType.ANALOG_INPUT__BASIC_, 117, "EngineeringUnits", true, false, ZigBeeType.Enumeration16bit, java.lang.Byte.class),
    ANALOG_INPUT__BASIC__APPLICATION_TYPE(ZclClusterType.ANALOG_INPUT__BASIC_, 256, "ApplicationType", true, false, ZigBeeType.UnsignedInteger32bit, java.lang.Long.class),
    BINARY_INPUT__BASIC__PRESENT_VALUE(ZclClusterType.BINARY_INPUT__BASIC_, 85, "PresentValue", true, true, ZigBeeType.SinglePrecision, java.lang.Float.class),
    BINARY_INPUT__BASIC__OUT_OF_SERVICE(ZclClusterType.BINARY_INPUT__BASIC_, 81, "OutOfService", true, false, ZigBeeType.Boolean, java.lang.Boolean.class),
    BINARY_INPUT__BASIC__STATUS_FLAGS(ZclClusterType.BINARY_INPUT__BASIC_, 111, "StatusFlags", false, true, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    BINARY_INPUT__BASIC__APPLICATION_TYPE(ZclClusterType.BINARY_INPUT__BASIC_, 256, "ApplicationType", true, false, ZigBeeType.UnsignedInteger32bit, java.lang.Long.class),
    BINARY_INPUT__BASIC__RELIABILITY(ZclClusterType.BINARY_INPUT__BASIC_, 103, "Reliability", true, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    BINARY_INPUT__BASIC__POLARITY(ZclClusterType.BINARY_INPUT__BASIC_, 84, "Polarity", false, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    BINARY_INPUT__BASIC__INACTIVE_TEXT(ZclClusterType.BINARY_INPUT__BASIC_, 46, "InactiveText", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    BINARY_INPUT__BASIC__DESCRIPTION(ZclClusterType.BINARY_INPUT__BASIC_, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    BINARY_INPUT__BASIC__ACTIVE_TEXT(ZclClusterType.BINARY_INPUT__BASIC_, 4, "ActiveText", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    SHADE_CONFIGURATION_DESCRIPTION(ZclClusterType.SHADE_CONFIGURATION, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    DOOR_LOCK_DESCRIPTION(ZclClusterType.DOOR_LOCK, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    DOOR_LOCK_LOCK_STATE(ZclClusterType.DOOR_LOCK, 0, "LockState", false, true, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    PUMP_CONFIGURATION_AND_CONTROL_DESCRIPTION(ZclClusterType.PUMP_CONFIGURATION_AND_CONTROL, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    THERMOSTAT_LOCAL_TEMPERATURE(ZclClusterType.THERMOSTAT, 0, "LocalTemperature", false, true, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    THERMOSTAT_OCCUPIED_COOLING_SETPOINT(ZclClusterType.THERMOSTAT, 17, "OccupiedCoolingSetpoint", true, true, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    THERMOSTAT_OCCUPIED_HEATING_SETPOINT(ZclClusterType.THERMOSTAT, 18, "OccupiedHeatingSetpoint", true, true, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    THERMOSTAT_CONTROL_SEQUENCE_OF_OPERATION(ZclClusterType.THERMOSTAT, 27, "ControlSequenceOfOperation", true, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    THERMOSTAT_SYSTEM_MODE(ZclClusterType.THERMOSTAT, 28, "SystemMode", true, true, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    FAN_CONTROL_DESCRIPTION(ZclClusterType.FAN_CONTROL, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    THERMOSTAT_USER_INTERFACE_CONFIGURATION_DESCRIPTION(ZclClusterType.THERMOSTAT_USER_INTERFACE_CONFIGURATION, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    COLOR_CONTROL_CURRENT_HUE(ZclClusterType.COLOR_CONTROL, 0, "CurrentHue", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_CURRENT_SATURATION(ZclClusterType.COLOR_CONTROL, 1, "CurrentSaturation", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_REMAINING_TIME(ZclClusterType.COLOR_CONTROL, 2, "RemainingTime", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_CURRENT_X(ZclClusterType.COLOR_CONTROL, 3, "CurrentX", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_CURRENT_Y(ZclClusterType.COLOR_CONTROL, 4, "CurrentY", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_DRIFT_COMPENSATION(ZclClusterType.COLOR_CONTROL, 5, "DriftCompensation", false, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    COLOR_CONTROL_COMPENSATION_TEXT(ZclClusterType.COLOR_CONTROL, 6, "CompensationText", false, false, ZigBeeType.CharacterString, java.lang.String.class),
    COLOR_CONTROL_COLOR_TEMPERATURE(ZclClusterType.COLOR_CONTROL, 7, "ColorTemperature", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_MODE(ZclClusterType.COLOR_CONTROL, 8, "ColorMode", false, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    COLOR_CONTROL_NUMBER_OF_PRIMARIES(ZclClusterType.COLOR_CONTROL, 16, "NumberOfPrimaries", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_1_X(ZclClusterType.COLOR_CONTROL, 17, "Primary1X", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_1_Y(ZclClusterType.COLOR_CONTROL, 18, "Primary1Y", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_1_INTENSITY(ZclClusterType.COLOR_CONTROL, 19, "Primary1Intensity", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_2_X(ZclClusterType.COLOR_CONTROL, 21, "Primary2X", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_2_Y(ZclClusterType.COLOR_CONTROL, 22, "Primary2Y", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_2_INTENSITY(ZclClusterType.COLOR_CONTROL, 23, "Primary2Intensity", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_3_X(ZclClusterType.COLOR_CONTROL, 25, "Primary3X", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_3_Y(ZclClusterType.COLOR_CONTROL, 26, "Primary3Y", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_3_INTENSITY(ZclClusterType.COLOR_CONTROL, 27, "Primary3Intensity", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_4_X(ZclClusterType.COLOR_CONTROL, 32, "Primary4X", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_4_Y(ZclClusterType.COLOR_CONTROL, 33, "Primary4Y", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_4_INTENSITY(ZclClusterType.COLOR_CONTROL, 34, "Primary4Intensity", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_5_X(ZclClusterType.COLOR_CONTROL, 36, "Primary5X", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_5_Y(ZclClusterType.COLOR_CONTROL, 37, "Primary5Y", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_5_INTENSITY(ZclClusterType.COLOR_CONTROL, 38, "Primary5Intensity", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_6_X(ZclClusterType.COLOR_CONTROL, 40, "Primary6X", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_6_Y(ZclClusterType.COLOR_CONTROL, 41, "Primary6Y", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_PRIMARY_6_INTENSITY(ZclClusterType.COLOR_CONTROL, 42, "Primary6Intensity", false, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_WHITE_POINT_X(ZclClusterType.COLOR_CONTROL, 48, "WhitePointX", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_WHITE_POINT_Y(ZclClusterType.COLOR_CONTROL, 49, "WhitePointY", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_POINT_RX(ZclClusterType.COLOR_CONTROL, 50, "ColorPointRX", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_POINT_RY(ZclClusterType.COLOR_CONTROL, 51, "ColorPointRY", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_POINT_R_INTENSITY(ZclClusterType.COLOR_CONTROL, 52, "ColorPointRIntensity", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_POINT_GX(ZclClusterType.COLOR_CONTROL, 54, "ColorPointGX", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_POINT_GY(ZclClusterType.COLOR_CONTROL, 55, "ColorPointGY", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_POINT_G_INTENSITY(ZclClusterType.COLOR_CONTROL, 56, "ColorPointGIntensity", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_POINT_BX(ZclClusterType.COLOR_CONTROL, 58, "ColorPointBX", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_POINT_BY(ZclClusterType.COLOR_CONTROL, 59, "ColorPointBY", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    COLOR_CONTROL_COLOR_POINT_B_INTENSITY(ZclClusterType.COLOR_CONTROL, 61, "ColorPointBIntensity", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    ILLUMINANCE_MEASUREMENT_MEASURED_VALUE(ZclClusterType.ILLUMINANCE_MEASUREMENT, 0, "MeasuredValue", false, true, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    ILLUMINANCE_MEASUREMENT_MIN_MEASURED_VALUE(ZclClusterType.ILLUMINANCE_MEASUREMENT, 1, "MinMeasuredValue", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    ILLUMINANCE_MEASUREMENT_MAX_MEASURED_VALUE(ZclClusterType.ILLUMINANCE_MEASUREMENT, 2, "MaxMeasuredValue", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    ILLUMINANCE_MEASUREMENT_TOLERANCE(ZclClusterType.ILLUMINANCE_MEASUREMENT, 3, "Tolerance", false, true, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    ILLUMINANCE_MEASUREMENT_LIGHT_SENSOR_TYPE(ZclClusterType.ILLUMINANCE_MEASUREMENT, 4, "LightSensorType", false, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    ILLUMINANCE_LEVEL_SENSING_DESCRIPTION(ZclClusterType.ILLUMINANCE_LEVEL_SENSING, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    TEMPERATURE_MEASUREMENT_MEASURED_VALUE(ZclClusterType.TEMPERATURE_MEASUREMENT, 0, "MeasuredValue", false, true, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    TEMPERATURE_MEASUREMENT_MIN_MEASURED_VALUE(ZclClusterType.TEMPERATURE_MEASUREMENT, 1, "MinMeasuredValue", false, false, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    TEMPERATURE_MEASUREMENT_MAX_MEASURED_VALUE(ZclClusterType.TEMPERATURE_MEASUREMENT, 2, "MaxMeasuredValue", false, false, ZigBeeType.SignedInteger16bit, java.lang.Integer.class),
    TEMPERATURE_MEASUREMENT_TOLERANCE(ZclClusterType.TEMPERATURE_MEASUREMENT, 3, "Tolerance", false, true, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    PRESSURE_MEASUREMENT_DESCRIPTION(ZclClusterType.PRESSURE_MEASUREMENT, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    FLOW_MEASUREMENT_DESCRIPTION(ZclClusterType.FLOW_MEASUREMENT, 28, "Description", true, false, ZigBeeType.CharacterString, java.lang.String.class),
    RELATIVE_HUMIDITY_MEASUREMENT_MEASURED_VALUE(ZclClusterType.RELATIVE_HUMIDITY_MEASUREMENT, 0, "MeasuredValue", false, true, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    RELATIVE_HUMIDITY_MEASUREMENT_MIN_MEASURED_VALUE(ZclClusterType.RELATIVE_HUMIDITY_MEASUREMENT, 1, "MinMeasuredValue", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    RELATIVE_HUMIDITY_MEASUREMENT_MAX_MEASURED_VALUE(ZclClusterType.RELATIVE_HUMIDITY_MEASUREMENT, 2, "MaxMeasuredValue", false, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    RELATIVE_HUMIDITY_MEASUREMENT_TOLERANCE(ZclClusterType.RELATIVE_HUMIDITY_MEASUREMENT, 3, "Tolerance", false, true, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    OCCUPANCY_SENSING_OCCUPANCY(ZclClusterType.OCCUPANCY_SENSING, 0, "Occupancy", false, true, ZigBeeType.Bitmap8bit, java.lang.Integer.class),
    OCCUPANCY_SENSING_OCCUPANCY_SENSOR_TYPE(ZclClusterType.OCCUPANCY_SENSING, 1, "OccupancySensorType", false, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    OCCUPANCY_SENSING_PIR_OCCUPIED_TO_UNOCCUPIED_DELAY(ZclClusterType.OCCUPANCY_SENSING, 16, "PIROccupiedToUnoccupiedDelay", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    OCCUPANCY_SENSING_PIR_UNOCCUPIED_TO_OCCUPIED_DELAY(ZclClusterType.OCCUPANCY_SENSING, 17, "PIRUnoccupiedToOccupiedDelay", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    OCCUPANCY_SENSING_ULTRA_SONIC_OCCUPIED_TO_UNOCCUPIED_DELAY(ZclClusterType.OCCUPANCY_SENSING, 32, "UltraSonicOccupiedToUnoccupiedDelay", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    OCCUPANCY_SENSING_ULTRA_SONIC_UNOCCUPIED_TO_OCCUPIED_DELAY(ZclClusterType.OCCUPANCY_SENSING, 33, "UltraSonicUnoccupiedToOccupiedDelay", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class),
    OCCUPANCY_SENSING_PIR_UNOCCUPIED_TO_OCCUPIED_THRESHOLD(ZclClusterType.OCCUPANCY_SENSING, 18, "PIRUnoccupiedToOccupiedThreshold", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    OCCUPANCY_SENSING_ULTRASONIC_UNOCCUPIED_TO_OCCUPIED_THRESHOLD(ZclClusterType.OCCUPANCY_SENSING, 34, "UltrasonicUnoccupiedToOccupiedThreshold", true, false, ZigBeeType.UnsignedInteger8bit, java.lang.Integer.class),
    IAS_ZONE_ZONE_STATE(ZclClusterType.IAS_ZONE, 0, "ZoneState", false, false, ZigBeeType.Enumeration8bit, java.lang.Byte.class),
    IAS_ZONE_ZONE_TYPE(ZclClusterType.IAS_ZONE, 1, "ZoneType", false, false, ZigBeeType.Enumeration16bit, java.lang.Byte.class),
    IAS_ZONE_ZONE_STATUS(ZclClusterType.IAS_ZONE, 2, "ZoneStatus", false, false, ZigBeeType.Bitmap16bit, java.lang.Integer.class),
    IAS_ZONE_IAS_CIE_ADDRESS(ZclClusterType.IAS_ZONE, 16, "IASCieAddress", true, false, ZigBeeType.IEEEAddress, java.lang.String.class),
    IAS_WD_MAX_DURATION(ZclClusterType.IAS_WD, 0, "MaxDuration", true, false, ZigBeeType.UnsignedInteger16bit, java.lang.Integer.class);

    private final ZclClusterType clusterType;
    private final int id;
    private final String label;
    private final boolean writable;
    private final boolean reportable;
    private final ZigBeeType zigBeeType;
    private final Class dataClass;

    ZclAttributeType(ZclClusterType clusterType, int id, String label, boolean writable, boolean reportable, ZigBeeType zigBeeType, Class dataClass) {
        this.clusterType = clusterType;
        this.id = id;
        this.label = label;
        this.writable = writable;
        this.reportable = reportable;
        this.zigBeeType = zigBeeType;
        this.dataClass = dataClass;
    }

    private static Map<Integer,Map<Integer, ZclAttributeType>> clusterAttributeMap = new HashMap<Integer, Map<Integer, ZclAttributeType>>();

    static {
        for (final ZclAttributeType zclAttributeType : values()) {
            final int clusterId = zclAttributeType.getClusterType().getId();
            if (!clusterAttributeMap.containsKey(clusterId)) {
                clusterAttributeMap.put(clusterId, new HashMap<Integer, ZclAttributeType>());
            }
            clusterAttributeMap.get(clusterId).put(zclAttributeType.getId(), zclAttributeType);
        }
    }

    public static ZclAttributeType get(int clusterId, int attributeId) {
        if (!clusterAttributeMap.containsKey(clusterId)) {
            return null;
        }
        return clusterAttributeMap.get(clusterId).get(attributeId);
    }

    public ZclClusterType getClusterType() {
        return clusterType;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public boolean isWritable() {
        return writable;
    }

    public boolean isReportable() {
        return reportable;
    }

    public ZigBeeType getZigBeeType() {
        return zigBeeType;
    }

    public Class getDataClass() {
        return dataClass;
    }
}
