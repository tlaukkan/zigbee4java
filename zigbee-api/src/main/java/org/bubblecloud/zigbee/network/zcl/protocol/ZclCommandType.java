package org.bubblecloud.zigbee.network.zcl.protocol;

public enum ZclCommandType {
    RESET_TO_FACTORY_DEFAULTS(0, ZclClusterType.BASIC, "Reset to Factory Defaults", true, false),
    IDENTIFY(0, ZclClusterType.IDENTIFY, "Identify", true, false),
    IDENTIFY_QUERY(1, ZclClusterType.IDENTIFY, "Identify Query", true, false),
    IDENTIFY_QUERY_RESPONSE(0, ZclClusterType.IDENTIFY, "Identify Query Response", false, false),
    ADD_GROUP_COMMAND(0, ZclClusterType.GROUPS, "Add Group Command", true, false),
    VIEW_GROUP_COMMAND(1, ZclClusterType.GROUPS, "View Group Command", true, false),
    GET_GROUP_MEMBERSHIP_COMMAND(2, ZclClusterType.GROUPS, "Get Group Membership Command", true, false),
    REMOVE_GROUP_COMMAND(3, ZclClusterType.GROUPS, "Remove Group Command", true, false),
    REMOVE_ALL_GROUPS_COMMAND(4, ZclClusterType.GROUPS, "Remove All Groups Command", true, false),
    ADD_GROUP_IF_IDENTIFYING_COMMAND(5, ZclClusterType.GROUPS, "Add Group If Identifying Command", true, false),
    ADD_GROUP_RESPONSE_COMMAND(0, ZclClusterType.GROUPS, "Add group response Command", false, false),
    VIEW_GROUP_RESPONSE_COMMAND(1, ZclClusterType.GROUPS, "View Group Response Command", false, false),
    GET_GROUP_MEMBERSHIP_RESPONSE_COMMAND(2, ZclClusterType.GROUPS, "Get Group Membership Response Command", false, false),
    REMOVE_GROUP_RESPONSE_COMMAND(3, ZclClusterType.GROUPS, "Remove Group Response Command", false, false),
    ADD_SCENE_COMMAND(0, ZclClusterType.SCENES, "Add Scene Command", true, false),
    VIEW_SCENE_COMMAND(1, ZclClusterType.SCENES, "View Scene Command", true, false),
    REMOVE_SCENE_COMMAND(2, ZclClusterType.SCENES, "Remove Scene Command", true, false),
    REMOVE_ALL_SCENES_COMMAND(3, ZclClusterType.SCENES, "Remove All Scenes Command", true, false),
    STORE_SCENE_COMMAND(4, ZclClusterType.SCENES, "Store Scene Command", true, false),
    RECALL_SCENE_COMMAND(5, ZclClusterType.SCENES, "Recall Scene Command", true, false),
    GET_SCENE_MEMBERSHIP_COMMAND(6, ZclClusterType.SCENES, "Get Scene Membership Command", true, false),
    ADD_SCENE_RESPONSE_COMMAND(0, ZclClusterType.SCENES, "Add Scene Response Command", false, false),
    VIEW_SCENE_RESPONSE_COMMAND(1, ZclClusterType.SCENES, "View Scene Response Command", false, false),
    REMOVE_SCENE_RESPONSE_COMMAND(2, ZclClusterType.SCENES, "Remove Scene Response Command", false, false),
    REMOVE_ALL_SCENES_RESPONSE_COMMAND(3, ZclClusterType.SCENES, "Remove All Scenes Response Command", false, false),
    STORE_SCENE_RESPONSE_COMMAND(4, ZclClusterType.SCENES, "Store Scene Response Command", false, false),
    GET_SCENE_MEMBERSHIP_RESPONSE_COMMAND(5, ZclClusterType.SCENES, "Get Scene Membership Response Command", false, false),
    OFF(0, ZclClusterType.ON_OFF, "Off", true, false),
    ON(1, ZclClusterType.ON_OFF, "On", true, false),
    TOGGLE(2, ZclClusterType.ON_OFF, "Toggle", true, false),
    MOVE_TO_LEVEL_COMMAND(0, ZclClusterType.LEVEL_CONTROL, "Move to Level Command", true, false),
    MOVE_COMMAND(1, ZclClusterType.LEVEL_CONTROL, "Move Command", true, false),
    STEP_COMMAND(2, ZclClusterType.LEVEL_CONTROL, "Step Command", true, false),
    STOP_COMMAND(3, ZclClusterType.LEVEL_CONTROL, "Stop Command", true, false),
    MOVE_TO_LEVEL__WITH_ON_OFF__COMMAND(4, ZclClusterType.LEVEL_CONTROL, "Move to Level (with On/Off) Command", true, false),
    MOVE__WITH_ON_OFF__COMMAND(5, ZclClusterType.LEVEL_CONTROL, "Move (with On/Off) Command", true, false),
    STEP__WITH_ON_OFF__COMMAND(6, ZclClusterType.LEVEL_CONTROL, "Step (with On/Off) Command", true, false),
    STOP_2_COMMAND(7, ZclClusterType.LEVEL_CONTROL, "Stop 2 Command", true, false),
    RESET_ALARM_COMMAND(0, ZclClusterType.ALARMS, "Reset Alarm Command", true, false),
    RESET_ALL_ALARMS_COMMAND(1, ZclClusterType.ALARMS, "Reset All Alarms Command", true, false),
    GET_ALARM_COMMAND(2, ZclClusterType.ALARMS, "Get Alarm Command", true, false),
    RESET_ALARM_LOG_COMMAND(3, ZclClusterType.ALARMS, "Reset Alarm Log Command", true, false),
    ALARM_COMMAND(0, ZclClusterType.ALARMS, "Alarm Command", false, false),
    GET_ALARM_RESPONSE_COMMAND(1, ZclClusterType.ALARMS, "Get Alarm Response Command", false, false),
    SET_ABSOLUTE_LOCATION_COMMAND(0, ZclClusterType.RSSI_LOCATION, "Set Absolute Location Command", true, false),
    SET_DEVICE_CONFIGURATION_COMMAND(1, ZclClusterType.RSSI_LOCATION, "Set Device Configuration Command", true, false),
    GET_DEVICE_CONFIGURATION_COMMAND(2, ZclClusterType.RSSI_LOCATION, "Get Device Configuration Command", true, false),
    GET_LOCATION_DATA_COMMAND(3, ZclClusterType.RSSI_LOCATION, "Get Location Data Command", true, false),
    RSSI_RESPONSE_COMMAND(4, ZclClusterType.RSSI_LOCATION, "RSSI Response Command", true, false),
    SEND_PINGS_COMMAND(5, ZclClusterType.RSSI_LOCATION, "Send Pings Command", true, false),
    ANCHOR_NODE_ANNOUNCE_COMMAND(6, ZclClusterType.RSSI_LOCATION, "Anchor Node Announce Command", true, false),
    DEVICE_CONFIGURATION_RESPONSE_COMMAND(0, ZclClusterType.RSSI_LOCATION, "Device Configuration Response Command", false, false),
    LOCATION_DATA_RESPONSE_COMMAND(1, ZclClusterType.RSSI_LOCATION, "Location Data Response Command", false, false),
    LOCATION_DATA_NOTIFICATION_COMMAND(2, ZclClusterType.RSSI_LOCATION, "Location Data Notification Command", false, false),
    COMPACT_LOCATION_DATA_NOTIFICATION_COMMAND(3, ZclClusterType.RSSI_LOCATION, "Compact Location Data Notification Command", false, false),
    RSSI_PING_COMMAND(4, ZclClusterType.RSSI_LOCATION, "RSSI Ping Command", false, false),
    RSSI_REQUEST_COMMAND(5, ZclClusterType.RSSI_LOCATION, "RSSI Request Command", false, false),
    REPORT_RSSI_MEASUREMENTS_COMMAND(6, ZclClusterType.RSSI_LOCATION, "Report RSSI Measurements Command", false, false),
    REQUEST_OWN_LOCATION_COMMAND(7, ZclClusterType.RSSI_LOCATION, "Request Own Location Command", false, false),
    RESTART_DEVICE_COMMAND(0, ZclClusterType.COMMISSIONING, "Restart Device Command", true, false),
    SAVE_STARTUP_PARAMETERS_COMMAND(1, ZclClusterType.COMMISSIONING, "Save Startup Parameters Command", true, false),
    RESTORE_STARTUP_PARAMETERS_COMMAND(2, ZclClusterType.COMMISSIONING, "Restore Startup Parameters Command", true, false),
    RESET_STARTUP_PARAMETERS_COMMAND(3, ZclClusterType.COMMISSIONING, "Reset Startup Parameters Command", true, false),
    RESTART_DEVICE_RESPONSE_RESPONSE(0, ZclClusterType.COMMISSIONING, "Restart Device Response Response", false, false),
    SAVE_STARTUP_PARAMETERS_RESPONSE(1, ZclClusterType.COMMISSIONING, "Save Startup Parameters Response", false, false),
    RESTORE_STARTUP_PARAMETERS_RESPONSE(2, ZclClusterType.COMMISSIONING, "Restore Startup Parameters Response", false, false),
    RESET_STARTUP_PARAMETERS_RESPONSE(3, ZclClusterType.COMMISSIONING, "Reset Startup Parameters Response", false, false),
    LOCK_DOOR(0, ZclClusterType.DOOR_LOCK, "Lock Door", true, false),
    UNLOCK_DOOR(1, ZclClusterType.DOOR_LOCK, "Unlock Door", true, false),
    LOCK_DOOR_RESPONSE_COMMAND(0, ZclClusterType.DOOR_LOCK, "Lock Door Response Command", false, false),
    UNLOCK_DOOR_RESPONSE_COMMAND(1, ZclClusterType.DOOR_LOCK, "Unlock Door Response Command", false, false),
    ZONE_ENROLL_RESPONSE(0, ZclClusterType.IAS_ZONE, "Zone Enroll Response", true, false),
    ZONE_STATUS_CHANGE_NOTIFICATION(0, ZclClusterType.IAS_ZONE, "Zone Status Change Notification", false, false),
    ZONE_ENROLL_REQUEST_COMMAND(1, ZclClusterType.IAS_ZONE, "Zone Enroll Request Command", false, false);

    private final int id;
    private final ZclClusterType clusterType;
    private final String label;
    private final boolean received;
    private final boolean generic;

    ZclCommandType(final int id, final ZclClusterType clusterType, final String label, final boolean received, final boolean generic) {
        this.id = id;
        this.clusterType = clusterType;
        this.label = label;
        this.received = received;
        this.generic = generic;
    }

    public int getId() { return id; }
    public ZclClusterType getClusterType() { return clusterType; }
    public String getLabel() { return label; }
    public boolean isReceived() { return received; }
    public boolean isGeneric() { return generic; }
    public String toString() { return label; }

}
