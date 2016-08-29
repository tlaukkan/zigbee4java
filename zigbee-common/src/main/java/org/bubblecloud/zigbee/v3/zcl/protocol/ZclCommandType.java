package org.bubblecloud.zigbee.v3.zcl.protocol;

import org.bubblecloud.zigbee.v3.zcl.ZclCommand;

import org.bubblecloud.zigbee.v3.zcl.clusters.basic.ResetToFactoryDefaultsCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.identify.IdentifyCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.identify.IdentifyQueryResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.identify.IdentifyQueryCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.AddGroupCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.AddGroupResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.ViewGroupCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.ViewGroupResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.GetGroupMembershipCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.GetGroupMembershipResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.RemoveGroupCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.RemoveGroupResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.RemoveAllGroupsCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.groups.AddGroupIfIdentifyingCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.AddSceneCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.AddSceneResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.ViewSceneCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.ViewSceneResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.RemoveSceneCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.RemoveSceneResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.RemoveAllScenesCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.RemoveAllScenesResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.StoreSceneCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.StoreSceneResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.RecallSceneCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.GetSceneMembershipResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.scenes.GetSceneMembershipCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.onoff.OffCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.onoff.OnCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.onoff.ToggleCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.levelcontrol.MoveToLevelCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.levelcontrol.MoveCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.levelcontrol.StepCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.levelcontrol.StopCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.levelcontrol.MoveToLevelWithOnOffCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.levelcontrol.MoveWithOnOffCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.levelcontrol.StepWithOnOffCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.levelcontrol.Stop2Command;
import org.bubblecloud.zigbee.v3.zcl.clusters.alarms.ResetAlarmCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.alarms.AlarmCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.alarms.ResetAllAlarmsCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.alarms.GetAlarmResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.alarms.GetAlarmCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.alarms.ResetAlarmLogCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.SetAbsoluteLocationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.DeviceConfigurationResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.SetDeviceConfigurationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.LocationDataResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.GetDeviceConfigurationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.LocationDataNotificationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.GetLocationDataCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.CompactLocationDataNotificationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.RssiResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.RssiPingCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.SendPingsCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.RssiRequestCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.AnchorNodeAnnounceCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.ReportRssiMeasurementsCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.rssilocation.RequestOwnLocationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.commissioning.RestartDeviceCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.commissioning.RestartDeviceResponseResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.commissioning.SaveStartupParametersCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.commissioning.SaveStartupParametersResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.commissioning.RestoreStartupParametersCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.commissioning.RestoreStartupParametersResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.commissioning.ResetStartupParametersCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.commissioning.ResetStartupParametersResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.doorlock.LockDoorCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.doorlock.LockDoorResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.doorlock.UnlockDoorCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.doorlock.UnlockDoorResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.thermostat.SetpointRaiseLowerCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.MoveToHueCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.MoveHueCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.StepHueCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.MoveToSaturationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.MoveSaturationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.StepSaturationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.MoveToHueAndSaturationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.MoveToColorCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.MoveColorCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.StepColorCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.colorcontrol.MoveToColorTemperatureCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iaszone.ZoneEnrollResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.iaszone.ZoneStatusChangeNotificationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iaszone.ZoneEnrollRequestCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.ArmCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.ArmResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.BypassCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.GetZoneIdMapResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.EmergencyCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.GetZoneInformationResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.FireCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.PanicCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.GetZoneIdMapCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iasace.GetZoneInformationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iaswd.StartWarningCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.iaswd.SquawkCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.ReadAttributesCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.ReadAttributesResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.WriteAttributesCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.WriteAttributesUndividedCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.WriteAttributesResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.WriteAttributesNoResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.ConfigureReportingCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.ConfigureReportingResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.ReadReportingConfigurationCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.ReadReportingConfigurationResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.ReportAttributesCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.DefaultResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.DiscoverAttributesCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.DiscoverAttributesResponse;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.ReadAttributesStructuredCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.WriteAttributesStructuredCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.WriteAttributesStructuredResponse;


/**
 * Code generated command type.
 */
public enum ZclCommandType {
    /**
     * Register command types.
     */
    RESET_TO_FACTORY_DEFAULTS_COMMAND(ZclClusterType.BASIC, 0, ResetToFactoryDefaultsCommand.class, "Reset to Factory Defaults Command", true),
    IDENTIFY_COMMAND(ZclClusterType.IDENTIFY, 0, IdentifyCommand.class, "Identify Command", true),
    IDENTIFY_QUERY_RESPONSE(ZclClusterType.IDENTIFY, 0, IdentifyQueryResponse.class, " Identify Query Response", false),
    IDENTIFY_QUERY_COMMAND(ZclClusterType.IDENTIFY, 1, IdentifyQueryCommand.class, "Identify Query Command", true),
    ADD_GROUP_COMMAND(ZclClusterType.GROUPS, 0, AddGroupCommand.class, "Add Group Command", true),
    ADD_GROUP_RESPONSE(ZclClusterType.GROUPS, 0, AddGroupResponse.class, " Add Group Response", false),
    VIEW_GROUP_COMMAND(ZclClusterType.GROUPS, 1, ViewGroupCommand.class, "View Group Command", true),
    VIEW_GROUP_RESPONSE(ZclClusterType.GROUPS, 1, ViewGroupResponse.class, " View Group Response", false),
    GET_GROUP_MEMBERSHIP_COMMAND(ZclClusterType.GROUPS, 2, GetGroupMembershipCommand.class, "Get Group Membership Command", true),
    GET_GROUP_MEMBERSHIP_RESPONSE(ZclClusterType.GROUPS, 2, GetGroupMembershipResponse.class, " Get Group Membership Response", false),
    REMOVE_GROUP_COMMAND(ZclClusterType.GROUPS, 3, RemoveGroupCommand.class, "Remove Group Command", true),
    REMOVE_GROUP_RESPONSE(ZclClusterType.GROUPS, 3, RemoveGroupResponse.class, " Remove Group Response", false),
    REMOVE_ALL_GROUPS_COMMAND(ZclClusterType.GROUPS, 4, RemoveAllGroupsCommand.class, "Remove All Groups Command", true),
    ADD_GROUP_IF_IDENTIFYING_COMMAND(ZclClusterType.GROUPS, 5, AddGroupIfIdentifyingCommand.class, "Add Group If Identifying Command", true),
    ADD_SCENE_COMMAND(ZclClusterType.SCENES, 0, AddSceneCommand.class, "Add Scene Command", true),
    ADD_SCENE_RESPONSE(ZclClusterType.SCENES, 0, AddSceneResponse.class, " Add Scene Response", false),
    VIEW_SCENE_COMMAND(ZclClusterType.SCENES, 1, ViewSceneCommand.class, "View Scene Command", true),
    VIEW_SCENE_RESPONSE(ZclClusterType.SCENES, 1, ViewSceneResponse.class, " View Scene Response", false),
    REMOVE_SCENE_COMMAND(ZclClusterType.SCENES, 2, RemoveSceneCommand.class, "Remove Scene Command", true),
    REMOVE_SCENE_RESPONSE(ZclClusterType.SCENES, 2, RemoveSceneResponse.class, " Remove Scene Response", false),
    REMOVE_ALL_SCENES_COMMAND(ZclClusterType.SCENES, 3, RemoveAllScenesCommand.class, "Remove All Scenes Command", true),
    REMOVE_ALL_SCENES_RESPONSE(ZclClusterType.SCENES, 3, RemoveAllScenesResponse.class, " Remove All Scenes Response", false),
    STORE_SCENE_COMMAND(ZclClusterType.SCENES, 4, StoreSceneCommand.class, "Store Scene Command", true),
    STORE_SCENE_RESPONSE(ZclClusterType.SCENES, 4, StoreSceneResponse.class, " Store Scene Response", false),
    RECALL_SCENE_COMMAND(ZclClusterType.SCENES, 5, RecallSceneCommand.class, "Recall Scene Command", true),
    GET_SCENE_MEMBERSHIP_RESPONSE(ZclClusterType.SCENES, 5, GetSceneMembershipResponse.class, " Get Scene Membership Response", false),
    GET_SCENE_MEMBERSHIP_COMMAND(ZclClusterType.SCENES, 6, GetSceneMembershipCommand.class, "Get Scene Membership Command", true),
    OFF_COMMAND(ZclClusterType.ON_OFF, 0, OffCommand.class, "Off Command", true),
    ON_COMMAND(ZclClusterType.ON_OFF, 1, OnCommand.class, "On Command", true),
    TOGGLE_COMMAND(ZclClusterType.ON_OFF, 2, ToggleCommand.class, "Toggle Command", true),
    MOVE_TO_LEVEL_COMMAND(ZclClusterType.LEVEL_CONTROL, 0, MoveToLevelCommand.class, "Move to Level Command", true),
    MOVE_COMMAND(ZclClusterType.LEVEL_CONTROL, 1, MoveCommand.class, "Move Command", true),
    STEP_COMMAND(ZclClusterType.LEVEL_CONTROL, 2, StepCommand.class, "Step Command", true),
    STOP_COMMAND(ZclClusterType.LEVEL_CONTROL, 3, StopCommand.class, "Stop Command", true),
    MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND(ZclClusterType.LEVEL_CONTROL, 4, MoveToLevelWithOnOffCommand.class, "Move to Level (with On/Off) Command", true),
    MOVE__WITH_ON_OFF__COMMAND(ZclClusterType.LEVEL_CONTROL, 5, MoveWithOnOffCommand.class, "Move (with On/Off) Command", true),
    STEP__WITH_ON_OFF__COMMAND(ZclClusterType.LEVEL_CONTROL, 6, StepWithOnOffCommand.class, "Step (with On/Off) Command", true),
    STOP_2_COMMAND(ZclClusterType.LEVEL_CONTROL, 7, Stop2Command.class, "Stop 2 Command", true),
    RESET_ALARM_COMMAND(ZclClusterType.ALARMS, 0, ResetAlarmCommand.class, "Reset Alarm Command", true),
    ALARM_COMMAND(ZclClusterType.ALARMS, 0, AlarmCommand.class, "Alarm Command", false),
    RESET_ALL_ALARMS_COMMAND(ZclClusterType.ALARMS, 1, ResetAllAlarmsCommand.class, "Reset All Alarms Command", true),
    GET_ALARM_RESPONSE(ZclClusterType.ALARMS, 1, GetAlarmResponse.class, " Get Alarm Response", false),
    GET_ALARM_COMMAND(ZclClusterType.ALARMS, 2, GetAlarmCommand.class, "Get Alarm Command", true),
    RESET_ALARM_LOG_COMMAND(ZclClusterType.ALARMS, 3, ResetAlarmLogCommand.class, "Reset Alarm Log Command", true),
    SET_ABSOLUTE_LOCATION_COMMAND(ZclClusterType.RSSI_LOCATION, 0, SetAbsoluteLocationCommand.class, "Set Absolute Location Command", true),
    DEVICE_CONFIGURATION_RESPONSE(ZclClusterType.RSSI_LOCATION, 0, DeviceConfigurationResponse.class, " Device Configuration Response", false),
    SET_DEVICE_CONFIGURATION_COMMAND(ZclClusterType.RSSI_LOCATION, 1, SetDeviceConfigurationCommand.class, "Set Device Configuration Command", true),
    LOCATION_DATA_RESPONSE(ZclClusterType.RSSI_LOCATION, 1, LocationDataResponse.class, " Location Data Response", false),
    GET_DEVICE_CONFIGURATION_COMMAND(ZclClusterType.RSSI_LOCATION, 2, GetDeviceConfigurationCommand.class, "Get Device Configuration Command", true),
    LOCATION_DATA_NOTIFICATION_COMMAND(ZclClusterType.RSSI_LOCATION, 2, LocationDataNotificationCommand.class, "Location Data Notification Command", false),
    GET_LOCATION_DATA_COMMAND(ZclClusterType.RSSI_LOCATION, 3, GetLocationDataCommand.class, "Get Location Data Command", true),
    COMPACT_LOCATION_DATA_NOTIFICATION_COMMAND(ZclClusterType.RSSI_LOCATION, 3, CompactLocationDataNotificationCommand.class, "Compact Location Data Notification Command", false),
    RSSI_RESPONSE(ZclClusterType.RSSI_LOCATION, 4, RssiResponse.class, " RSSI Response", true),
    RSSI_PING_COMMAND(ZclClusterType.RSSI_LOCATION, 4, RssiPingCommand.class, "RSSI Ping Command", false),
    SEND_PINGS_COMMAND(ZclClusterType.RSSI_LOCATION, 5, SendPingsCommand.class, "Send Pings Command", true),
    RSSI_REQUEST_COMMAND(ZclClusterType.RSSI_LOCATION, 5, RssiRequestCommand.class, "RSSI Request Command", false),
    ANCHOR_NODE_ANNOUNCE_COMMAND(ZclClusterType.RSSI_LOCATION, 6, AnchorNodeAnnounceCommand.class, "Anchor Node Announce Command", true),
    REPORT_RSSI_MEASUREMENTS_COMMAND(ZclClusterType.RSSI_LOCATION, 6, ReportRssiMeasurementsCommand.class, "Report RSSI Measurements Command", false),
    REQUEST_OWN_LOCATION_COMMAND(ZclClusterType.RSSI_LOCATION, 7, RequestOwnLocationCommand.class, "Request Own Location Command", false),
    RESTART_DEVICE_COMMAND(ZclClusterType.COMMISSIONING, 0, RestartDeviceCommand.class, "Restart Device Command", true),
    RESTART_DEVICE_RESPONSE_RESPONSE(ZclClusterType.COMMISSIONING, 0, RestartDeviceResponseResponse.class, " Restart Device Response Response", false),
    SAVE_STARTUP_PARAMETERS_COMMAND(ZclClusterType.COMMISSIONING, 1, SaveStartupParametersCommand.class, "Save Startup Parameters Command", true),
    SAVE_STARTUP_PARAMETERS_RESPONSE(ZclClusterType.COMMISSIONING, 1, SaveStartupParametersResponse.class, " Save Startup Parameters Response", false),
    RESTORE_STARTUP_PARAMETERS_COMMAND(ZclClusterType.COMMISSIONING, 2, RestoreStartupParametersCommand.class, "Restore Startup Parameters Command", true),
    RESTORE_STARTUP_PARAMETERS_RESPONSE(ZclClusterType.COMMISSIONING, 2, RestoreStartupParametersResponse.class, " Restore Startup Parameters Response", false),
    RESET_STARTUP_PARAMETERS_COMMAND(ZclClusterType.COMMISSIONING, 3, ResetStartupParametersCommand.class, "Reset Startup Parameters Command", true),
    RESET_STARTUP_PARAMETERS_RESPONSE(ZclClusterType.COMMISSIONING, 3, ResetStartupParametersResponse.class, " Reset Startup Parameters Response", false),
    LOCK_DOOR_COMMAND(ZclClusterType.DOOR_LOCK, 0, LockDoorCommand.class, "Lock Door Command", true),
    LOCK_DOOR_RESPONSE(ZclClusterType.DOOR_LOCK, 0, LockDoorResponse.class, " Lock Door Response", false),
    UNLOCK_DOOR_COMMAND(ZclClusterType.DOOR_LOCK, 1, UnlockDoorCommand.class, "Unlock Door Command", true),
    UNLOCK_DOOR_RESPONSE(ZclClusterType.DOOR_LOCK, 1, UnlockDoorResponse.class, " Unlock Door Response", false),
    SETPOINT_RAISE_LOWER_COMMAND(ZclClusterType.THERMOSTAT, 0, SetpointRaiseLowerCommand.class, "Setpoint Raise/Lower Command", true),
    MOVE_TO_HUE_COMMAND(ZclClusterType.COLOR_CONTROL, 0, MoveToHueCommand.class, "Move to Hue Command", true),
    MOVE_HUE_COMMAND(ZclClusterType.COLOR_CONTROL, 1, MoveHueCommand.class, "Move Hue Command", true),
    STEP_HUE_COMMAND(ZclClusterType.COLOR_CONTROL, 2, StepHueCommand.class, "Step Hue Command", true),
    MOVE_TO_SATURATION_COMMAND(ZclClusterType.COLOR_CONTROL, 3, MoveToSaturationCommand.class, "Move to Saturation Command", true),
    MOVE_SATURATION_COMMAND(ZclClusterType.COLOR_CONTROL, 4, MoveSaturationCommand.class, "Move Saturation Command", true),
    STEP_SATURATION_COMMAND(ZclClusterType.COLOR_CONTROL, 5, StepSaturationCommand.class, "Step Saturation Command", true),
    MOVE_TO_HUE_AND_SATURATION_COMMAND(ZclClusterType.COLOR_CONTROL, 6, MoveToHueAndSaturationCommand.class, "Move to Hue and Saturation Command", true),
    MOVE_TO_COLOR_COMMAND(ZclClusterType.COLOR_CONTROL, 7, MoveToColorCommand.class, "Move to Color Command", true),
    MOVE_COLOR_COMMAND(ZclClusterType.COLOR_CONTROL, 8, MoveColorCommand.class, "Move Color Command", true),
    STEP_COLOR_COMMAND(ZclClusterType.COLOR_CONTROL, 9, StepColorCommand.class, "Step Color Command", true),
    MOVE_TO_COLOR_TEMPERATURE_COMMAND(ZclClusterType.COLOR_CONTROL, 10, MoveToColorTemperatureCommand.class, "Move to Color Temperature Command", true),
    ZONE_ENROLL_RESPONSE(ZclClusterType.IAS_ZONE, 0, ZoneEnrollResponse.class, " Zone Enroll Response", true),
    ZONE_STATUS_CHANGE_NOTIFICATION_COMMAND(ZclClusterType.IAS_ZONE, 0, ZoneStatusChangeNotificationCommand.class, "Zone Status Change Notification Command", false),
    ZONE_ENROLL_REQUEST_COMMAND(ZclClusterType.IAS_ZONE, 1, ZoneEnrollRequestCommand.class, "Zone Enroll Request Command", false),
    ARM_COMMAND(ZclClusterType.IAS_ACE, 0, ArmCommand.class, "Arm Command", true),
    ARM_RESPONSE(ZclClusterType.IAS_ACE, 0, ArmResponse.class, " Arm Response", false),
    BYPASS_COMMAND(ZclClusterType.IAS_ACE, 1, BypassCommand.class, "Bypass Command", true),
    GET_ZONE_ID_MAP_RESPONSE(ZclClusterType.IAS_ACE, 1, GetZoneIdMapResponse.class, " Get Zone ID Map Response", false),
    EMERGENCY_COMMAND(ZclClusterType.IAS_ACE, 2, EmergencyCommand.class, "Emergency Command", true),
    GET_ZONE_INFORMATION_RESPONSE(ZclClusterType.IAS_ACE, 2, GetZoneInformationResponse.class, " Get Zone Information Response", false),
    FIRE_COMMAND(ZclClusterType.IAS_ACE, 3, FireCommand.class, "Fire Command", true),
    PANIC_COMMAND(ZclClusterType.IAS_ACE, 4, PanicCommand.class, "Panic Command", true),
    GET_ZONE_ID_MAP_COMMAND(ZclClusterType.IAS_ACE, 5, GetZoneIdMapCommand.class, "Get Zone ID Map Command", true),
    GET_ZONE_INFORMATION_COMMAND(ZclClusterType.IAS_ACE, 6, GetZoneInformationCommand.class, "Get Zone Information Command", true),
    START_WARNING_COMMAND(ZclClusterType.IAS_WD, 0, StartWarningCommand.class, "Start Warning Command", true),
    SQUAWK_COMMAND(ZclClusterType.IAS_WD, 2, SquawkCommand.class, "Squawk Command", true),
    READ_ATTRIBUTES_COMMAND(ZclClusterType.GENERAL, 0, ReadAttributesCommand.class, "Read Attributes Command", true),
    READ_ATTRIBUTES_RESPONSE(ZclClusterType.GENERAL, 1, ReadAttributesResponse.class, " Read Attributes Response", true),
    WRITE_ATTRIBUTES_COMMAND(ZclClusterType.GENERAL, 2, WriteAttributesCommand.class, "Write Attributes Command", true),
    WRITE_ATTRIBUTES_UNDIVIDED_COMMAND(ZclClusterType.GENERAL, 3, WriteAttributesUndividedCommand.class, "Write Attributes Undivided Command", true),
    WRITE_ATTRIBUTES_RESPONSE(ZclClusterType.GENERAL, 4, WriteAttributesResponse.class, " Write Attributes Response", true),
    WRITE_ATTRIBUTES_NO_RESPONSE(ZclClusterType.GENERAL, 5, WriteAttributesNoResponse.class, " Write Attributes No Response", true),
    CONFIGURE_REPORTING_COMMAND(ZclClusterType.GENERAL, 6, ConfigureReportingCommand.class, "Configure Reporting Command", true),
    CONFIGURE_REPORTING_RESPONSE(ZclClusterType.GENERAL, 7, ConfigureReportingResponse.class, " Configure Reporting Response", true),
    READ_REPORTING_CONFIGURATION_COMMAND(ZclClusterType.GENERAL, 8, ReadReportingConfigurationCommand.class, "Read Reporting Configuration Command", true),
    READ_REPORTING_CONFIGURATION_RESPONSE(ZclClusterType.GENERAL, 9, ReadReportingConfigurationResponse.class, " Read Reporting Configuration Response", true),
    REPORT_ATTRIBUTES_COMMAND(ZclClusterType.GENERAL, 10, ReportAttributesCommand.class, "Report Attributes Command", true),
    DEFAULT_RESPONSE(ZclClusterType.GENERAL, 11, DefaultResponse.class, " Default Response", true),
    DISCOVER_ATTRIBUTES_COMMAND(ZclClusterType.GENERAL, 12, DiscoverAttributesCommand.class, "Discover Attributes Command", true),
    DISCOVER_ATTRIBUTES_RESPONSE(ZclClusterType.GENERAL, 13, DiscoverAttributesResponse.class, " Discover Attributes Response", true),
    READ_ATTRIBUTES_STRUCTURED_COMMAND(ZclClusterType.GENERAL, 14, ReadAttributesStructuredCommand.class, "Read Attributes Structured Command", true),
    WRITE_ATTRIBUTES_STRUCTURED_COMMAND(ZclClusterType.GENERAL, 15, WriteAttributesStructuredCommand.class, "Write Attributes Structured Command", true),
    WRITE_ATTRIBUTES_STRUCTURED_RESPONSE(ZclClusterType.GENERAL, 16, WriteAttributesStructuredResponse.class, " Write Attributes Structured Response", true);

    private final int commandId;
    private final ZclClusterType clusterType;
    private final Class<? extends ZclCommand> commandClass;
    private final String label;
    private final boolean received;

    ZclCommandType(final ZclClusterType clusterType, final int commandId, final Class<? extends ZclCommand> commandClass, final String label, final boolean received) {
        this.clusterType = clusterType;
        this.commandId = commandId;
        this.commandClass = commandClass;
        this.label = label;
        this.received = received;
    }

    public ZclClusterType getClusterType() { return clusterType; }
    public int getId() { return commandId; }
    public String getLabel() { return label; }
    public boolean isGeneric() { return clusterType==ZclClusterType.GENERAL; }
    public boolean isReceived() { return received; }
    public Class<? extends ZclCommand> getCommandClass() { return commandClass; }

    public static ZclCommandType getValueById(final ZclClusterType clusterType, final int commandId) {
        for (final ZclCommandType value : values()) {
            if(value.clusterType == clusterType && value.commandId == commandId) {
                return value;
            }
        }
        return null;
    }
}
