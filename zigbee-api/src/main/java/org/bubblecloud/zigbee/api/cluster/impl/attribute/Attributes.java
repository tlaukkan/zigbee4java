/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies
   of the Italian National Research Council


   See the NOTICE file distributed with this work for additional
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.bubblecloud.zigbee.api.cluster.impl.attribute;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class Attributes {

    final static public  AttributeDescriptor ON_OFF = new AbstractAttribute()
            .setId(0x0000)
            .setName("OnOff")
            .setReportable(true)
            .setZigBeeType(ZigBeeType.Boolean)
            .setWritable(false);

    final static public    AttributeDescriptor ZCL_VERSION = new AbstractAttribute()
            .setId(0x0000)
            .setName("ZCLVersion")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor APPLICATION_VERSION = new AbstractAttribute()
            .setId(0x0001)
            .setName("ApplicationVersion")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor STACK_VERSION = new AbstractAttribute()
            .setId(0x0002)
            .setName("StackVersion")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor HW_VERSION = new AbstractAttribute()
            .setId(0x0003)
            .setName("HWVersion")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor MANUFACTURER_NAME = new AbstractAttribute()
            .setId(0x0004)
            .setName("ManufacturerName")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.CharacterString)
            .setWritable(false);

    final static public   AttributeDescriptor MODEL_IDENTIFIER = new AbstractAttribute()
            .setId(0x0005)
            .setName("ModelIdentifier")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.CharacterString)
            .setWritable(false);

    final static public   AttributeDescriptor DATE_CODE = new AbstractAttribute()
            .setId(0x0006)
            .setName("DateCode")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.CharacterString)
            .setWritable(false);

    final static public   AttributeDescriptor POWER_SOURCE = new AbstractAttribute()
            .setId(0x0007)
            .setName("PowerSource")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(false);

    final static public   AttributeDescriptor LOCATION_DESCRIPTION = new AbstractAttribute()
            .setId(0x0010)
            .setName("LocationDescription")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.CharacterString)
            .setWritable(true);

    final static public   AttributeDescriptor PHYSICAL_ENVIRONMENT = new AbstractAttribute()
            .setId(0x0011)
            .setName("PhysicalEnvironment")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(true);

    final static public   AttributeDescriptor DEVICE_ENABLED = new AbstractAttribute()
            .setId(0x0012)
            .setName("DeviceEnabled")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Boolean)
            .setWritable(true);

    final static public   AttributeDescriptor ALARM_MASK = new AbstractAttribute()
            .setId(0x0013)
            .setName("AlarmMask")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(true);

    final static public   AttributeDescriptor DISABLE_LOCAL_CONFIG = new AbstractAttribute()
            .setId(0x0014)
            .setName("DisableLocalConfig")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(true);


    final static public   AttributeDescriptor IDENTIFY_TIME = new AbstractAttribute()
            .setId(0x0000)
            .setName("IdentifyTime")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor SCENE_COUNT = new AbstractAttribute()
            .setId(0x0000)
            .setName("SceneCount")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor CURRENT_SCENE = new AbstractAttribute()
            .setId(0x0001)
            .setName("CurrentScene")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor CURRENT_GROUP = new AbstractAttribute()
            .setId(0x0002)
            .setName("CurrentGroup")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor SCENE_VALID = new AbstractAttribute()
            .setId(0x0003)
            .setName("SceneValid")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Boolean)
            .setWritable(false);

    final static public   AttributeDescriptor LAST_CONFIGURED_BY = new AbstractAttribute()
            .setId(0x0005)
            .setName("LastConfiguredBy")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.IEEEAddress)
            .setWritable(false);

    final static public   AttributeDescriptor NAME_SUPPORT_GROUPS = new AbstractAttribute()
            .setId(0x0000)
            .setName("NameSupport")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(false);

    final static public   AttributeDescriptor NAME_SUPPORT_SCENES = new AbstractAttribute()
            .setId(0x0004)
            .setName("NameSupport")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(false);

    final static public   AttributeDescriptor OCCUPANCY = new AbstractAttribute()
            .setId(0x0000)
            .setName("Occupancy")
            .setReportable(true)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(false);

    final static public   AttributeDescriptor OCCUPANCY_SENSOR_TYPE = new AbstractAttribute()
            .setId(0x0001)
            .setName("OccupancySensorType")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(false);

    final static public   AttributeDescriptor PIR_OCCUPIED_TO_UNOCCUPIED_DELAY = new AbstractAttribute()
            .setId(0x0010)
            .setName("PIROccupiedToUnoccupiedDelay")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor PIR_UNOCCUPIED_TO_OCCUPIED_DELAY = new AbstractAttribute()
            .setId(0x0011)
            .setName("PIRUnoccupiedToOccupiedDelay")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor PIR_UNOCCUPIED_TO_OCCUPIED_THRESHOLD = new AbstractAttribute()
            .setId(0x0012)
            .setName("PIRUnoccupiedToOccupiedThreshold")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor ULTRA_SONIC_OCCUPIED_TO_UNOCCUPIED_DELAY = new AbstractAttribute()
            .setId(0x0020)
            .setName("UltraSonicOccupiedToUnoccupiedDelay")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor ULTRA_SONIC_UNOCCUPIED_TO_OCCUPIED_DELAY = new AbstractAttribute()
            .setId(0x0021)
            .setName("UltraSonicUnoccupiedToOccupiedDelay")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor ULTRASONIC_UNOCCUPIED_TO_OCCUPIED_THRESHOLD = new AbstractAttribute()
            .setId(0x0022)
            .setName("UltrasonicUnoccupiedToOccupiedThreshold")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor MAINS_VOLTAGE = new AbstractAttribute()
            .setId(0x0000)
            .setName("MainsVoltage")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor MAINS_FREQUENCY = new AbstractAttribute()
            .setId(0x0001)
            .setName("MainsFrequency")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor MAINS_ALARM_MASK = new AbstractAttribute()
            .setId(0x0010)
            .setName("MainsAlarmMask")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(true);

    final static public   AttributeDescriptor MAINS_VOLTAGE_MIN_THRESHOLD = new AbstractAttribute()
            .setId(0x0011)
            .setName("MainsVoltageMinThreshold")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor MAINS_VOLTAGE_MAX_THRESHOLD = new AbstractAttribute()
            .setId(0x0012)
            .setName("MainsVoltageMaxThreshold")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor MAINS_VOLTAGE_DWELL_TRIP_POINT = new AbstractAttribute()
            .setId(0x0013)
            .setName("MainsVoltageDwellTripPoint")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor BATTERY_VOLTAGE = new AbstractAttribute()
            .setId(0x0020)
            .setName("BatteryVoltage")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor BATTERY_MANUFACTURER = new AbstractAttribute()
            .setId(0x0030)
            .setName("BatteryManufacturer")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.CharacterString)
            .setWritable(true);

    final static public   AttributeDescriptor BATTERY_SIZE = new AbstractAttribute()
            .setId(0x0031)
            .setName("BatterySize")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(true);

    final static public   AttributeDescriptor BATTERY_AHr_RATING = new AbstractAttribute()
            .setId(0x0032)
            .setName("BatteryAHrRating")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor BATTERY_QUANTITY = new AbstractAttribute()
            .setId(0x0033)
            .setName("BatteryQuantity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor BATTERY_RATED_VOLTAGE = new AbstractAttribute()
            .setId(0x0034)
            .setName("BatteryRatedVoltage")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor BATTERY_ALARM_MASK = new AbstractAttribute()
            .setId(0x0035)
            .setName("BatteryAlarmMask")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(true);

    final static public   AttributeDescriptor BATTERY_VOLTAGE_MIN_THRESHOLD = new AbstractAttribute()
            .setId(0x0036)
            .setName("BatteryVoltageMinThreshold")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor CURRENT_TEMPERATURE = new AbstractAttribute()
            .setId(0x0000)
            .setName("CurrentTemperature")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor MIN_TEMP_EXPERIENCED = new AbstractAttribute()
            .setId(0x0001)
            .setName("MinTempExperienced")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor MAX_TEMP_EXPERIENCED = new AbstractAttribute()
            .setId(0x0002)
            .setName("MaxTempExperienced")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor OVER_TEMP_TOTAL_DWELL = new AbstractAttribute()
            .setId(0x0003)
            .setName("OverTempTotalDwell")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor DEVICE_TEMP_ALARM_MASK = new AbstractAttribute()
            .setId(0x0010)
            .setName("DeviceTempAlarmMask")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(true);

    final static public   AttributeDescriptor LOW_TEMP_THRESHOLD = new AbstractAttribute()
            .setId(0x0011)
            .setName("LowTempThreshold")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor HIGH_TEMP_THRESHOLD = new AbstractAttribute()
            .setId(0x0012)
            .setName("HighTempThreshold")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor LOW_TEMP_DWELL_TRIP_POINT = new AbstractAttribute()
            .setId(0x0013)
            .setName("LowTempDwellTripPoint")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger24bit)
            .setWritable(true);

    final static public   AttributeDescriptor HIGH_TEMP_DWELL_TRIP_POINT = new AbstractAttribute()
            .setId(0x0014)
            .setName("HighTempDwellTripPoint")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger24bit)
            .setWritable(true);

    final static public   AttributeDescriptor SWITCH_TYPE = new AbstractAttribute()
            .setId(0x0000)
            .setName("SwitchType")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(false);

    final static public   AttributeDescriptor SWITCH_ACTIONS = new AbstractAttribute()
            .setId(0x0000)
            .setName("SwitchActions")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(true);

    final static public   AttributeDescriptor CURRENT_LEVEL = new AbstractAttribute()
            .setId(0x0000)
            .setName("CurrentLevel")
            .setReportable(true)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor REMAINING_TIME = new AbstractAttribute()
            .setId(0x0001)
            .setName("RemainingTime")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor ON_OFF_TRANSITION_TIME = new AbstractAttribute()
            .setId(0x0010)
            .setName("OnOffTransitionTime")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor ON_LEVEL = new AbstractAttribute()
            .setId(0x0011)
            .setName("OnLevel")
            .setReportable(true)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor TIME = new AbstractAttribute()
            .setId(0x0000)
            .setName("Time")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger32bit)
            .setWritable(true);

    final static public   AttributeDescriptor TIME_STATUS = new AbstractAttribute()
            .setId(0x0001)
            .setName("TimeStatus")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(true);

    final static public   AttributeDescriptor MEASURED_VALUE_SIGNED_16_BIT = new AbstractAttribute()
            .setId(0x0000)
            .setName("MeasuredValue")
            .setReportable(true)
            .setZigBeeType(ZigBeeType.SignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor MIN_MEASURED_VALUE_SIGNED_16_BIT = new AbstractAttribute()
            .setId(0x0001)
            .setName("MinMeasuredValue")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor MAX_MEASURED_VALUE_SIGNED_16_BIT = new AbstractAttribute()
            .setId(0x0002)
            .setName("MaxMeasuredValue")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor MEASURED_VALUE_UNSIGNED_16_BIT = new AbstractAttribute()
            .setId(0x0000)
            .setName("MeasuredValue")
            .setReportable(true)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor MIN_MEASURED_VALUE_UNSIGNED_16_BIT = new AbstractAttribute()
            .setId(0x0001)
            .setName("MinMeasuredValue")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor MAX_MEASURED_VALUE_UNSIGNED_16_BIT = new AbstractAttribute()
            .setId(0x0002)
            .setName("MaxMeasuredValue")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor TOLERANCE = new AbstractAttribute()
            .setId(0x0003)
            .setName("Tolerance")
            .setReportable(true)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor ALLARM_COUNT = new AbstractAttribute()
            .setId(0x0000)
            .setName("AlarmCount")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor LIGHT_SENSOR_TYPE = new AbstractAttribute()
            .setId(0x0004)
            .setName("LightSensorType")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(false);

    final static public   AttributeDescriptor ZONE_STATE = new AbstractAttribute()
            .setId(0x0000)
            .setName("ZoneState")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(false);

    final static public   AttributeDescriptor ZONE_TYPE = new AbstractAttribute()
            .setId(0x0001)
            .setName("ZoneType")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration16bit)
            .setWritable(false);

    final static public   AttributeDescriptor ZONE_STATUS = new AbstractAttribute()
            .setId(0x0002)
            .setName("ZoneStatus")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Bitmap16bit)
            .setWritable(false);

    final static public   AttributeDescriptor IAS_CIE_ADDRESS = new AbstractAttribute()
            .setId(0x00010)
            .setName("IASCieAddress")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.IEEEAddress)
            .setWritable(true);

    final static public   AttributeDescriptor CURRENT_HUE = new AbstractAttribute()
            .setId(0x0000)
            .setName("CurrentHue")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor CURRENT_SATURATION = new AbstractAttribute()
            .setId(0x0001)
            .setName("CurrentSaturation")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor REMAINING_TIME_COLOR_CONTROL = new AbstractAttribute()
            .setId(0x0002)
            .setName("RemainingTime")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor CURRENT_X = new AbstractAttribute()
            .setId(0x0003)
            .setName("CurrentX")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor CURRENT_Y = new AbstractAttribute()
            .setId(0x0004)
            .setName("CurrentY")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor DRIFT_COMPENSATION = new AbstractAttribute()
            .setId(0x0005)
            .setName("DriftCompensation")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(false);

    final static public   AttributeDescriptor COMPENSATION_TEXT = new AbstractAttribute()
            .setId(0x0006)
            .setName("CompensationText")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.CharacterString)
            .setWritable(false);

    final static public   AttributeDescriptor COLOR_TEMPERATURE = new AbstractAttribute()
            .setId(0x0007)
            .setName("ColorTemperature")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor COLOR_MODE = new AbstractAttribute()
            .setId(0x0008)
            .setName("ColorMode")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(false);

    final static public   AttributeDescriptor NUMBER_OF_PRIMARIES = new AbstractAttribute()
            .setId(0x0010)
            .setName("NumberOfPrimaries")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_1_X = new AbstractAttribute()
            .setId(0x0011)
            .setName("Primary1X")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_1_Y = new AbstractAttribute()
            .setId(0x0012)
            .setName("Primary1Y")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_1_INTENSITY = new AbstractAttribute()
            .setId(0x0013)
            .setName("Primary1Intensity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_2_X = new AbstractAttribute()
            .setId(0x0015)
            .setName("Primary2X")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_2_Y = new AbstractAttribute()
            .setId(0x0016)
            .setName("Primary2Y")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_2_INTENSITY = new AbstractAttribute()
            .setId(0x0017)
            .setName("Primary2Intensity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_3_X = new AbstractAttribute()
            .setId(0x0019)
            .setName("Primary3X")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_3_Y = new AbstractAttribute()
            .setId(0x001a)
            .setName("Primary3Y")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_3_INTENSITY = new AbstractAttribute()
            .setId(0x001b)
            .setName("Primary3Intensity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_4_X = new AbstractAttribute()
            .setId(0x0020)
            .setName("Primary4X")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_4_Y = new AbstractAttribute()
            .setId(0x0021)
            .setName("Primary4Y")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_4_INTENSITY = new AbstractAttribute()
            .setId(0x0022)
            .setName("Primary1Intensity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_5_X = new AbstractAttribute()
            .setId(0x0024)
            .setName("Primary5X")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_5_Y = new AbstractAttribute()
            .setId(0x0025)
            .setName("Primary5Y")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_5_INTENSITY = new AbstractAttribute()
            .setId(0x0026)
            .setName("Primary5Intensity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_6_X = new AbstractAttribute()
            .setId(0x0028)
            .setName("Primary6X")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_6_Y = new AbstractAttribute()
            .setId(0x0029)
            .setName("Primary6Y")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(false);

    final static public   AttributeDescriptor PRIMARY_6_INTENSITY = new AbstractAttribute()
            .setId(0x002a)
            .setName("Primary6Intensity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(false);

    final static public   AttributeDescriptor WHITE_POINT_X = new AbstractAttribute()
            .setId(0x0030)
            .setName("WhitePointX")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor WHITE_POINT_Y = new AbstractAttribute()
            .setId(0x0031)
            .setName("WhitePointY")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor COLOR_POINT_RX = new AbstractAttribute()
            .setId(0x0032)
            .setName("ColorPointRX")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor COLOR_POINT_RY = new AbstractAttribute()
            .setId(0x0033)
            .setName("ColorPointRY")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor COLOR_POINT_R_INTENSITY = new AbstractAttribute()
            .setId(0x0034)
            .setName("ColorPointRIntensity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor COLOR_POINT_GX = new AbstractAttribute()
            .setId(0x0036)
            .setName("ColorPointGX")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor COLOR_POINT_GY = new AbstractAttribute()
            .setId(0x0037)
            .setName("ColorPointGY")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor COLOR_POINT_G_INTENSITY = new AbstractAttribute()
            .setId(0x0038)
            .setName("ColorPointGIntensity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor COLOR_POINT_BX = new AbstractAttribute()
            .setId(0x003a)
            .setName("ColorPointBX")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor COLOR_POINT_BY = new AbstractAttribute()
            .setId(0x003b)
            .setName("ColorPointBY")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public   AttributeDescriptor COLOR_POINT_B_INTENSITY = new AbstractAttribute()
            .setId(0x003d)
            .setName("ColorPointBIntensity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger8bit)
            .setWritable(true);

    final static public   AttributeDescriptor MAX_DURATION = new AbstractAttribute()
            .setId(0x0000)
            .setName("MaxDuration")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger16bit)
            .setWritable(true);

    final static public  AttributeDescriptor OUT_OF_SERVICE = new AbstractAttribute()
            .setId(0x0051)
            .setName("OutOfService")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Boolean)
            .setWritable(true);

    final static public  AttributeDescriptor STATUS_FLAGS = new AbstractAttribute()
            .setId(0x006F)
            .setName("StatusFlags")
            .setReportable(true)
            .setZigBeeType(ZigBeeType.Bitmap8bit)
            .setWritable(false);

    final static public  AttributeDescriptor PRESENT_VALUE = new AbstractAttribute()
            .setId(0x0055)
            .setName("PresentValue")
            .setReportable(true)
            .setZigBeeType(ZigBeeType.SinglePrecision)
            .setWritable(true);

    final static public  AttributeDescriptor APPLICATION_TYPE = new AbstractAttribute()
            .setId(0x0100)
            .setName("ApplicationType")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.UnsignedInteger32bit)
            .setWritable(true);

    final static public  AttributeDescriptor RELIABILITY = new AbstractAttribute()
            .setId(0x0067)
            .setName("Reliability")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(true);

    final static public  AttributeDescriptor POLARITY = new AbstractAttribute()
            .setId(0x0054)
            .setName("Polarity")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration8bit)
            .setWritable(false);

    final static public  AttributeDescriptor INACTIVE_TEXT = new AbstractAttribute()
            .setId(0x002E)
            .setName("InactiveText")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.CharacterString)
            .setWritable(true);

    final static public  AttributeDescriptor ACTIVE_TEXT = new AbstractAttribute()
            .setId(0x0004)
            .setName("ActiveText")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.CharacterString)
            .setWritable(true);

    final static public  AttributeDescriptor DESCRIPTION = new AbstractAttribute()
            .setId(0x001C)
            .setName("Description")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.CharacterString)
            .setWritable(true);

    public static final AttributeDescriptor MAX_PRESENT_VALUE = new AbstractAttribute()
            .setId(0x0041)
            .setName("MaxPresentValue")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SinglePrecision)
            .setWritable(true);

    public static final AttributeDescriptor MIN_PRESENT_VALUE= new AbstractAttribute()
            .setId(0x0045)
            .setName("MinPresentValue")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SinglePrecision)
            .setWritable(true);

    public static final AttributeDescriptor RESOLUTION = new AbstractAttribute()
            .setId(0x006A)
            .setName("Resolution")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.SinglePrecision)
            .setWritable(true);

    public static final AttributeDescriptor ENGINEERING_UNITS = new AbstractAttribute()
            .setId(0x0075)
            .setName("EngineeringUnits")
            .setReportable(false)
            .setZigBeeType(ZigBeeType.Enumeration16bit)
            .setWritable(true);
}
