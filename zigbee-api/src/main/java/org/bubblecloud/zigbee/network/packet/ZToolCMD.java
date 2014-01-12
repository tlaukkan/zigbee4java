/*
   Copyright 2008-2013 ITACA-TSB, http://www.tsb.upv.es/
   Instituto Tecnologico de Aplicaciones de Comunicacion 
   Avanzadas - Grupo Tecnologias para la Salud y el 
   Bienestar (TSB)


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

package org.bubblecloud.zigbee.network.packet;

/**
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ZToolCMD {
    /// <name>TI.ZPI2.MESSAGE_ID.AF_DATA_CONFIRM</name>
    /// <summary>AF Data confirm.</summary>
    public static final int AF_DATA_CONFIRM = 0x4480;
    /// <name>TI.ZPI2.MESSAGE_ID.AF_DATA_REQUEST</name>
    /// <summary>This command is used by tester to build and send a data request message</summary>
    public static final int AF_DATA_REQUEST = 0x2401;
    /// <name>TI.ZPI2.MESSAGE_ID.AF_DATA_SRSP</name>
    /// <summary>Response for AF_DATA_REQUEST</summary>
    public static final int AF_DATA_SRSP = 0x6401;
    /// <name>TI.ZPI2.MESSAGE_ID.AF_INCOMING_MSG</name>
    /// <summary>Incoming AF data.</summary>
    public static final int AF_INCOMING_MSG = 0x4481;
    /// <name>TI.ZPI2.MESSAGE_ID.AF_REGISTER</name>
    /// <summary>This command enables the tester to register an application's endpoint description</summary>
    public static final int AF_REGISTER = 0x2400;
    /// <name>TI.ZPI2.MESSAGE_ID.AF_REGISTER_SRSP</name>
    /// <summary>Response for AF_REGISTER</summary>
    public static final int AF_REGISTER_SRSP = 0x6400;
    /// <name>TI.ZPI2.MESSAGE_ID.APP_MSG</name>
    /// <summary>Use this message to send raw data to an application.</summary>
    public static final int APP_MSG = 0x2900;
    /// <name>TI.ZPI2.MESSAGE_ID.APP_MSG_RESPONSE</name>
    /// <summary>Status for APP_MSG</summary>
    public static final int APP_MSG_RESPONSE = 0x6900;
    /// <name>TI.ZPI2.MESSAGE_ID.APP_MSG_RSP</name>
    /// <summary>Response for APP_MSG</summary>
    public static final int APP_MSG_RSP = 0x6980;
    /// <name>TI.ZPI2.MESSAGE_ID.APP_USER_TEST</name>
    /// <summary>This command is used by the tester to set the debug threshold level for a particular software component in the target.</summary>
    public static final int APP_USER_TEST = 0x2901;
    /// <name>TI.ZPI2.MESSAGE_ID.APP_USER_TEST_RESPONSE</name>
    /// <summary>Response for APP_USER_TEST</summary>
    public static final int APP_USER_TEST_RESPONSE = 0x6901;
    /// <name>TI.ZPI2.MESSAGE_ID.APSDE_DATA_CONFIRMATION</name>
    /// <summary>This message is issued by the target APS to the tester to report the results of a request to transfer a data PDU from a local NHLE (Next Higher Layer Entity) to a single peer NHLE.</summary>
    public static final int APSDE_DATA_CONFIRMATION = 0x880;
    /// <name>TI.ZPI2.MESSAGE_ID.APSME_BIND_RESPONSE</name>
    /// <summary>Response for APSME_BIND</summary>
    public static final int APSME_BIND_RESPONSE = 0x1804;
    /// <name>TI.ZPI2.MESSAGE_ID.DEBUG_SET_DEBUG_THRESHOLD</name>
    /// <summary>This command is used by the tester to set the debug threshold level for a particular software component in the target.</summary>
    public static final int DEBUG_SET_DEBUG_THRESHOLD = 0x2800;
    /// <name>TI.ZPI2.MESSAGE_ID.DEBUG_SET_DEBUG_THRESHOLD_RESPONSE</name>
    /// <summary>Response for SYS_SET_DEBUG_THRESHOLD</summary>
    public static final int DEBUG_SET_DEBUG_THRESHOLD_RESPONSE = 0x6800;
    /// <name>TI.ZPI2.MESSAGE_ID.DEBUG_STRING</name>
    /// <summary>Debug message sent by device</summary>
    public static final int DEBUG_STRING = 0x4880;
    /// <name>TI.ZPI2.MESSAGE_ID.NLDE_DATA_CONFIRMATION</name>
    /// <summary>This message is issued by the target NWK to the tester to report the results of a request to transfer a data PDU from a local APS sub-layer entity to a single peer APS sub-layer entity.</summary>
    public static final int NLDE_DATA_CONFIRMATION = 0x4380;
    /// <name>TI.ZPI2.MESSAGE_ID.NLDE_DATA_INDICATION</name>
    /// <summary>This message is issued by the target NWK to the tester to indicate the transfer of a data PDU from the NWK layer to the local APS sub-layer entity.</summary>
    public static final int NLDE_DATA_INDICATION = 0x4381;
    /// <name>TI.ZPI2.MESSAGE_ID.NLDE_DATA_REQUEST</name>
    /// <summary>This command enables the tester to request the transfer of data from the local APS sub-layer to a peer APS sublayer entity.</summary>
    public static final int NLDE_DATA_REQUEST = 0x2301;
    /// <name>TI.ZPI2.MESSAGE_ID.NLDE_DATA_RESPONSE</name>
    /// <summary>Response for NLDE_DATA_REQUEST</summary>
    public static final int NLDE_DATA_RESPONSE = 0x6301;
    /// <name>TI.ZPI2.MESSAGE_ID.NLDE_NWK_INIT</name>
    /// <summary>This function will initialize the nwk with the NWK_TASKID.</summary>
    public static final int NLDE_NWK_INIT = 0x4300;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_DIRECTJOIN_REQUEST</name>
    /// <summary>NLME direct join request used by tester</summary>
    public static final int NLME_DIRECTJOIN_REQUEST = 0x230b;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_DIRECTJOIN_RESPONSE</name>
    /// <summary>Response for NLME_DIRECTJOIN_REQUEST</summary>
    public static final int NLME_DIRECTJOIN_RESPONSE = 0x630b;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_GET_REQUEST</name>
    /// <summary>This command is used by tester to make a request (on behalf of the next higher layer) to read the value of an attribute from the NWK information base (NIB).</summary>
    public static final int NLME_GET_REQUEST = 0x2307;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_GET_RESPONSE</name>
    /// <summary>Response for NLME_GET_REQUEST</summary>
    public static final int NLME_GET_RESPONSE = 0x6307;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_JOIN_CONFIRMATION</name>
    /// <summary>This command is issued by the target NWK (to tester) to announce the next higher layer of the results of its request to join itself or another device to a network.</summary>
    public static final int NLME_JOIN_CONFIRMATION = 0x4383;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_JOIN_INDICATION</name>
    /// <summary>This message is sent by the target to announce the next higher layer of a remote join request.</summary>
    public static final int NLME_JOIN_INDICATION = 0x4384;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_JOIN_REQUEST</name>
    /// <summary>This command is used by tester to make a request (on behalf of the next higher layer) to join the device itself or another device to a network.</summary>
    public static final int NLME_JOIN_REQUEST = 0x2304;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_JOIN_RESPONSE</name>
    /// <summary>Response for NLME_JOIN_REQUEST</summary>
    public static final int NLME_JOIN_RESPONSE = 0x6304;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_LEAVE_CONFIRMATION</name>
    /// <summary>This message is sent by the target to indicate to the next higher layer that the device itself or another device is leaving the network.</summary>
    public static final int NLME_LEAVE_CONFIRMATION = 0x4385;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_LEAVE_INDICATION</name>
    /// <summary>This message is sent by the target to indicate a remote leave request to the next higher layer of a coordinator</summary>
    public static final int NLME_LEAVE_INDICATION = 0x4386;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_LEAVE_REQUEST</name>
    /// <summary>This command is used by tester to make a request (on behalf of the next higher layer) that the device itself or another device leave the network.</summary>
    public static final int NLME_LEAVE_REQUEST = 0x2305;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_LEAVE_RESPONSE</name>
    /// <summary>Response for NLME_LEAVE_REQUEST</summary>
    public static final int NLME_LEAVE_RESPONSE = 0x6305;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_NETWORK_FORMATION_CONFIRMATION</name>
    /// <summary>This message is used by the target NWK to inform the tester of the result of a previous association request command</summary>
    public static final int NLME_NETWORK_FORMATION_CONFIRMATION = 0x4382;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_NETWORK_FORMATION_REQUEST</name>
    /// <summary>This command is used by tester to request (on behalf of the next higher layer) that the device be initiated as a coordinator.</summary>
    public static final int NLME_NETWORK_FORMATION_REQUEST = 0x2302;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_NETWORK_FORMATION_RESPONSE</name>
    /// <summary>Response for NLME_NETWORK_FORMATION_REQUEST</summary>
    public static final int NLME_NETWORK_FORMATION_RESPONSE = 0x6302;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_NETWORKDISCOVERY_CONFIRMATION</name>
    /// <summary>This message is sent by the target to indicate network discovery confirmation</summary>
    public static final int NLME_NETWORKDISCOVERY_CONFIRMATION = 0x4389;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_NETWORKDISCOVERY_REQUEST</name>
    /// <summary>NLME Network discovery request used by tester</summary>
    public static final int NLME_NETWORKDISCOVERY_REQUEST = 0x2309;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_NETWORKDISCOVERY_RESPONSE</name>
    /// <summary>Response for NLME_NETWORKDISCOVERY_REQUEST</summary>
    public static final int NLME_NETWORKDISCOVERY_RESPONSE = 0x6309;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_ORPHANJOIN_REQUEST</name>
    /// <summary>NLME orphan join request used by tester</summary>
    public static final int NLME_ORPHANJOIN_REQUEST = 0x230c;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_ORPHANJOIN_RESPONSE</name>
    /// <summary>Response for NLME_ORPHANJOIN_REQUEST</summary>
    public static final int NLME_ORPHANJOIN_RESPONSE = 0x630c;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_PERMITJOINING_REQUEST</name>
    /// <summary>This command is used by the tester to define how the next higher layer of a coordinator device would permit devices to join its network for a fixed period.</summary>
    public static final int NLME_PERMITJOINING_REQUEST = 0x2303;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_PERMITJOINING_RESPONSE</name>
    /// <summary>Response for NLME_PERMITJOINING_REQUEST</summary>
    public static final int NLME_PERMITJOINING_RESPONSE = 0x6303;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_POLL_CONFIRMATION</name>
    /// <summary>This function reports the results of a polling attempt.</summary>
    public static final int NLME_POLL_CONFIRMATION = 0x4387;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_RESET_REQUEST</name>
    /// <summary>This command is used by tester to make a request (on behalf of the next higher layer) that the NWK layer perform a reset operation</summary>
    public static final int NLME_RESET_REQUEST = 0x2306;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_RESET_RESPONSE</name>
    /// <summary>Response for NLME_RESET_REQUEST</summary>
    public static final int NLME_RESET_RESPONSE = 0x6306;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_ROUTEDISCOVERY_REQUEST</name>
    /// <summary>NLME route discovery request used by tester</summary>
    public static final int NLME_ROUTEDISCOVERY_REQUEST = 0x230a;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_ROUTEDISCOVERY_RESPONSE</name>
    /// <summary>Response for NLME_ROUTEDISCOVERY_REQUEST</summary>
    public static final int NLME_ROUTEDISCOVERY_RESPONSE = 0x630a;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_SET_REQUEST</name>
    /// <summary>This command is used by tester to make a request (on behalf of the next higher layer) to set the value of an attribute in the NWK information base (NIB).</summary>
    public static final int NLME_SET_REQUEST = 0x2308;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_SET_RESPONSE</name>
    /// <summary>Response for NLME_SET_REQUEST</summary>
    public static final int NLME_SET_RESPONSE = 0x6308;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_STARTROUTER_CONFIRMATION</name>
    /// <summary>This message is sent by the target to the next higher layer of the results of its request to start a router</summary>
    public static final int NLME_STARTROUTER_CONFIRMATION = 0x438a;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_STARTROUTER_REQUEST</name>
    /// <summary>NLME Start router request used by tester</summary>
    public static final int NLME_STARTROUTER_REQUEST = 0x230d;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_STARTROUTER_RESPONSE</name>
    /// <summary>Response for NLME_STARTROUTER_REQUEST</summary>
    public static final int NLME_STARTROUTER_RESPONSE = 0x630d;
    /// <name>TI.ZPI2.MESSAGE_ID.NLME_SYNC_INDICATION</name>
    /// <summary>This message is sent by the target to indicate a sync request to the next higher layer of a coordinator</summary>
    public static final int NLME_SYNC_INDICATION = 0x4388;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_ASSOCIATE_CONFIRMATION</name>
    /// <summary>This function is used to inform the upper layers of the initiating device whether its request to associate was successful or unsuccessful.</summary>
    public static final int NWK_ASSOCIATE_CONFIRMATION = 0x2382;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_ASSOCIATE_INDICATION</name>
    /// <summary>This function is used to indicate the reception of an association request command.</summary>
    public static final int NWK_ASSOCIATE_INDICATION = 0x2381;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_BEACON_NOTIFY_INDICATION</name>
    /// <summary>This function is used to send parameters contained within a beacon frame received by the MAC sublayer to the next higher layer. The function also sends a measure of the link quality and the time the beacon was received.</summary>
    public static final int NWK_BEACON_NOTIFY_INDICATION = 0x2383;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_COMM_STATUS_INDICATION</name>
    /// <summary>This function reports a comm status error</summary>
    public static final int NWK_COMM_STATUS_INDICATION = 0x238d;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_DATA_CONFIRMATION</name>
    /// <summary>This function is used to send the results of a request to transfer a data SPDU (MSDU) from a local SSCS entity to a single peer SSCS entity; or multiple peer SSCS entities.</summary>
    public static final int NWK_DATA_CONFIRMATION = 0x2384;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_DATA_INDICATION</name>
    /// <summary>This function indicates the transfer of a data SPDU (MSDU) from the MAC sublayer to the local SSCS entity.</summary>
    public static final int NWK_DATA_INDICATION = 0x2385;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_DISASSOCIATE_CONFIRMATION</name>
    /// <summary>This function is sent as the result of a disassociation request.</summary>
    public static final int NWK_DISASSOCIATE_CONFIRMATION = 0x2387;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_DISASSOCIATE_INDICATION</name>
    /// <summary>This function is used to indicate the reception of a disassociation notification command.</summary>
    public static final int NWK_DISASSOCIATE_INDICATION = 0x2386;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_ORPHAN_INDICATION</name>
    /// <summary>This function allows the MLME to announce the next higher layer of an orphaned device</summary>
    public static final int NWK_ORPHAN_INDICATION = 0x238a;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_POLL_CONFIRMATION</name>
    /// <summary>This function reports the results of a polling attempt.</summary>
    public static final int NWK_POLL_CONFIRMATION = 0x238b;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_PURGE_CONFIRMATION</name>
    /// <summary>This function reports the results of a purge attempt.</summary>
    public static final int NWK_PURGE_CONFIRMATION = 0x2390;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_RX_ENABLE_CONFIRMATION</name>
    /// <summary>This function reports the results of an RX enable attempt.</summary>
    public static final int NWK_RX_ENABLE_CONFIRMATION = 0x238f;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_SCAN_CONFIRMATION</name>
    /// <summary>This function reports the results of a channel scan request.</summary>
    public static final int NWK_SCAN_CONFIRMATION = 0x238c;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_START_CONFIRMATION</name>
    /// <summary>This function reports the success of the start request.</summary>
    public static final int NWK_START_CONFIRMATION = 0x238e;
    /// <name>TI.ZPI2.MESSAGE_ID.NWK_SYNCHRONIZATION_LOSS_INDICATION</name>
    /// <summary>This function indicates the loss of synchronization of a network beacon</summary>
    public static final int NWK_SYNCHRONIZATION_LOSS_INDICATION = 0x2380;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_ADC_READ</name>
    /// <summary>Stop timer.</summary>
    public static final int SYS_ADC_READ = 0x210d;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_ADC_READ_SRSP</name>
    /// <summary>Response for SYS_ADC_READ</summary>
    public static final int SYS_ADC_READ_SRSP = 0x610d;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_GPIO</name>
    /// <summary>Configure the accessible GPIO pins</summary>
    public static final int SYS_GPIO = 0x210e;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_TEST_RF</name>
    /// <summary>Configure the device RF test modes</summary>
    public static final int SYS_TEST_RF = 0x4140;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_TEST_LOOPBACK</name>
    /// <summary>Test the physical interface</summary>
    public static final int SYS_TEST_LOOPBACK = 0x2141;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_GPIO</name>
    /// <summary>Response to SYS_GPIO</summary>
    public static final int SYS_GPIO_SRSP = 0x610e;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_TEST_LOOPBACK</name>
    /// <summary>Response to SYS_TEST_LOOPBACK</summary>
    public static final int SYS_TEST_LOOPBACK_SRSP = 0x6141;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_OSAL_NV_READ</name>
    /// <summary>This command is used by the tester to read a single memory location in the target non-volatile memory. The command accepts an address value and returns the memory value present in the target at that address.</summary>
    public static final int SYS_OSAL_NV_READ = 0x2108;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_OSAL_NV_READ_SRSP</name>
    /// <summary>Response for SYS_OSAL_NV_READ</summary>
    public static final int SYS_OSAL_NV_READ_SRSP = 0x6108;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_OSAL_NV_WRITE</name>
    /// <summary>This command is used by the tester to write to a particular location in non-volatile memory.  The command accepts an address location and a memory value. The memory value is written to the address location in the target.</summary>
    public static final int SYS_OSAL_NV_WRITE = 0x2109;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_OSAL_NV_WRITE_SRSP</name>
    /// <summary>Response for SYS_OSAL_NV_WRITE</summary>
    public static final int SYS_OSAL_NV_WRITE_SRSP = 0x6109;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_OSAL_START_TIMER</name>
    /// <summary>Start timer.</summary>
    public static final int SYS_OSAL_START_TIMER = 0x210a;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_OSAL_START_TIMER_SRSP</name>
    /// <summary>Response for SYS_OSAL_START_TIMER</summary>
    public static final int SYS_OSAL_START_TIMER_SRSP = 0x610a;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_OSAL_STOP_TIMER</name>
    /// <summary>Stop timer.</summary>
    public static final int SYS_OSAL_STOP_TIMER = 0x210b;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_OSAL_STOP_TIMER_SRSP</name>
    /// <summary>Response for SYS_OSAL_STOP_TIMER</summary>
    public static final int SYS_OSAL_STOP_TIMER_SRSP = 0x610b;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_OSAL_TIMER_EXPIRED_IND</name>
    /// <summary>OSAL timer expired</summary>
    public static final int SYS_OSAL_TIMER_EXPIRED_IND = 0x4181;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_PING</name>
    /// <summary>This command is used to check for a device</summary>
    public static final int SYS_PING = 0x2101;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_PING_RESPONSE</name>
    /// <summary>Response for SYS_PING</summary>
    public static final int SYS_PING_RESPONSE = 0x6101;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_RANDOM</name>
    /// <summary>Generate random number.</summary>
    public static final int SYS_RANDOM = 0x210c;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_RANDOM_SRSP</name>
    /// <summary>Response for SYS_RANDOM</summary>
    public static final int SYS_RANDOM_SRSP = 0x610c;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_RESET</name>
    /// <summary>This command is sent by the tester to the target to reset it</summary>
    public static final int SYS_RESET = 0x4100;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_RESET_RESPONSE</name>
    /// <summary>Indicates a device has reset.</summary>
    public static final int SYS_RESET_RESPONSE = 0x4180;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_RPC_ERROR</name>
    /// <summary>RPC transport layer error.</summary>
    public static final int SYS_RPC_ERROR = 0x6000;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_VERSION</name>
    /// <summary>Ask for the device's version string.</summary>
    public static final int SYS_VERSION = 0x2102;
    /// <name>TI.ZPI2.MESSAGE_ID.SYS_VERSION_RESPONSE</name>
    /// <summary>Response for SYS_VERSION</summary>
    public static final int SYS_VERSION_RESPONSE = 0x6102;
    /// <name>TI.ZPI2.MESSAGE_ID.USERTEST_REQUEST</name>
    /// <summary>This message is sent to the target in order to test the functions defined for individual applications (which internally use attributes and cluster IDs from various device descriptions).</summary>
    public static final int USERTEST_REQUEST = 0xb51;
    /// <name>TI.ZPI2.MESSAGE_ID.USERTEST_RESPONSE</name>
    /// <summary>Response for USERTEST_REQUEST</summary>
    public static final int USERTEST_RESPONSE = 0x1b51;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_CALLBACK_SUBSCRIBE</name>
    /// <summary>This command subscribes/unsubscribes to layer callbacks.</summary>
    public static final int UTIL_CALLBACK_SUBSCRIBE = 0x2706;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_CALLBACK_SUBSCRIBE_RESPONSE</name>
    /// <summary>Response for UTIL_CALLBACK_SUBSCRIBE</summary>
    public static final int UTIL_CALLBACK_SUBSCRIBE_RESPONSE = 0x6706;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_GET_DEVICE_INFO</name>
    /// <summary>This command is used by the tester to read a single memory location in the target non-volatile memory. The command accepts an address value and returns the memory value present in the target at that address.</summary>
    public static final int UTIL_GET_DEVICE_INFO = 0x2700;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_GET_DEVICE_INFO_RESPONSE</name>
    /// <summary>Response for UTIL_GET_DEVICE_INFO</summary>
    public static final int UTIL_GET_DEVICE_INFO_RESPONSE = 0x6700;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_GET_NV_INFO</name>
    /// <summary>Use this message to get the NV information.</summary>
    public static final int UTIL_GET_NV_INFO = 0x2701;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_GET_NV_INFO_RESPONSE</name>
    /// <summary>Response for UTIL_GET_NV_INFO</summary>
    public static final int UTIL_GET_NV_INFO_RESPONSE = 0x6701;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_GET_TIME_ALIVE</name>
    /// <summary>Use this message to get board's time alive.</summary>
    public static final int UTIL_GET_TIME_ALIVE = 0x2709;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_GET_TIME_ALIVE_RESPONSE</name>
    /// <summary>Response for UTIL_GET_TIME_ALIVE</summary>
    public static final int UTIL_GET_TIME_ALIVE_RESPONSE = 0x6709;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_KEY_EVENT</name>
    /// <summary>Sends a key event to the device registered application.   The device register application means that the application registered for key events with OnBoard.  Not all application support all keys; so you must know what keys the application supports.</summary>
    public static final int UTIL_KEY_EVENT = 0x2707;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_KEY_EVENT_RESPONSE</name>
    /// <summary>Response for UTIL_KEY_EVENT</summary>
    public static final int UTIL_KEY_EVENT_RESPONSE = 0x6707;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_LED_CONTROL</name>
    /// <summary>Use this message to control LEDs on the board.</summary>
    public static final int UTIL_LED_CONTROL = 0x270a;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_LED_CONTROL_RESPONSE</name>
    /// <summary>Response for UTIL_LED_CONTROL</summary>
    public static final int UTIL_LED_CONTROL_RESPONSE = 0x670a;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_SET_CHANNELS</name>
    /// <summary>Use this message to set the channels.</summary>
    public static final int UTIL_SET_CHANNELS = 0x2703;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_SET_CHANNELS_RESPONSE</name>
    /// <summary>Response for UTIL_SET_CHANNELS</summary>
    public static final int UTIL_SET_CHANNELS_RESPONSE = 0x6703;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_SET_PANID</name>
    /// <summary>Use this message to set PANID.</summary>
    public static final int UTIL_SET_PANID = 0x2702;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_SET_PANID_RESPONSE</name>
    /// <summary>Response for UTIL_SET_PANID</summary>
    public static final int UTIL_SET_PANID_RESPONSE = 0x6702;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_SET_PRECONFIG_KEY</name>
    /// <summary>Use this message to set the preconfig key.</summary>
    public static final int UTIL_SET_PRECONFIG_KEY = 0x2705;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_SET_PRECONFIG_KEY_RESPONSE</name>
    /// <summary>Response for UTIL_SET_PRECONFIG_KEY</summary>
    public static final int UTIL_SET_PRECONFIG_KEY_RESPONSE = 0x6705;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_SET_SECURITY_LEVEL</name>
    /// <summary>Use this message to set the security level.</summary>
    public static final int UTIL_SET_SECURITY_LEVEL = 0x2704;
    /// <name>TI.ZPI2.MESSAGE_ID.UTIL_SET_SECURITY_LEVEL_RESPONSE</name>
    /// <summary>Response for UTIL_SET_SECURITY_LEVEL</summary>
    public static final int UTIL_SET_SECURITY_LEVEL_RESPONSE = 0x6704;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_ALLOW_BIND</name>
    /// <summary>Puts the device into the Allow Bind mode (zb_AllowBind).</summary>
    public static final int ZB_ALLOW_BIND = 0x2602;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_ALLOW_BIND_CONFIRM</name>
    /// <summary>Response for ZB_ALLOW_BIND</summary>
    public static final int ZB_ALLOW_BIND_CONFIRM = 0x4682;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_ALLOW_BIND_RSP</name>
    /// <summary>Response for ZB_ALLOW_BIND</summary>
    public static final int ZB_ALLOW_BIND_RSP = 0x6602;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_APP_REGISTER_REQUEST</name>
    /// <summary>This command register the device descriptor</summary>
    public static final int ZB_APP_REGISTER_REQUEST = 0x260a;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_APP_REGISTER_RSP</name>
    /// <summary>Response for ZB_APP_REGISTER_REQUEST</summary>
    public static final int ZB_APP_REGISTER_RSP = 0x660a;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_BIND_CONFIRM</name>
    /// <summary>Response for ZB_BIND_DEVICE</summary>
    public static final int ZB_BIND_CONFIRM = 0x4681;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_BIND_DEVICE</name>
    /// <summary>Create or remove a binding entry (zb_BindDevice).</summary>
    public static final int ZB_BIND_DEVICE = 0x2601;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_BIND_DEVICE_RSP</name>
    /// <summary>Response for ZB_BIND_DEVICE</summary>
    public static final int ZB_BIND_DEVICE_RSP = 0x6601;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_FIND_DEVICE_CONFIRM</name>
    /// <summary>(zb_FindDeviceConfirm)</summary>
    public static final int ZB_FIND_DEVICE_CONFIRM = 0x4685;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_FIND_DEVICE_REQUEST</name>
    /// <summary>Search for a device's short address given its IEEE address.</summary>
    public static final int ZB_FIND_DEVICE_REQUEST = 0x2607;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_FIND_DEVICE_REQUEST_RSP</name>
    /// <summary>Response for ZB_FIND_DEVICE_REQUEST</summary>
    public static final int ZB_FIND_DEVICE_REQUEST_RSP = 0x6607;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_GET_DEVICE_INFO</name>
    /// <summary>Reads current device infromation.</summary>
    public static final int ZB_GET_DEVICE_INFO = 0x2606;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_GET_DEVICE_INFO_RSP</name>
    /// <summary>Response for ZB_GET_DEVICE_INFO</summary>
    public static final int ZB_GET_DEVICE_INFO_RSP = 0x6606;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_PERMIT_JOINING_REQUEST</name>
    /// <summary>Enables or disables the joining permissions on the destination device thus controlling the ability of new devices to join the network.</summary>
    public static final int ZB_PERMIT_JOINING_REQUEST = 0x2608;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_PERMIT_JOINING_REQUEST_RSP</name>
    /// <summary>Response for ZB_PERMIT_JOINING_REQUEST</summary>
    public static final int ZB_PERMIT_JOINING_REQUEST_RSP = 0x6608;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_READ_CONFIGURATION</name>
    /// <summary>Reads a configuration property from nonvolatile memory (zb_ReadConfiguration).</summary>
    public static final int ZB_READ_CONFIGURATION = 0x2604;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_READ_CONFIGURATION_RSP</name>
    /// <summary>
    /// </summary>
    public static final int ZB_READ_CONFIGURATION_RSP = 0x6604;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_RECEIVE_DATA_INDICATION</name>
    /// <summary>(zb_ReceiveDataIndication)</summary>
    public static final int ZB_RECEIVE_DATA_INDICATION = 0x4687;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_SEND_DATA_CONFIRM</name>
    /// <summary>Response for ZB_SEND_DATA_REQUEST</summary>
    public static final int ZB_SEND_DATA_CONFIRM = 0x4683;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_SEND_DATA_REQUEST</name>
    /// <summary>Send a data packet to another device (zb_SendDataRequest).</summary>
    public static final int ZB_SEND_DATA_REQUEST = 0x2603;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_SEND_DATA_REQUEST_RSP</name>
    /// <summary>Response for ZB_SEND_DATA_REQUEST</summary>
    public static final int ZB_SEND_DATA_REQUEST_RSP = 0x6603;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_START_CONFIRM</name>
    /// <summary>Response for ZB_START_REQUEST</summary>
    public static final int ZB_START_CONFIRM = 0x4680;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_START_REQUEST</name>
    /// <summary>Starts the ZigBee stack (zb_StartRequest).</summary>
    public static final int ZB_START_REQUEST = 0x2600;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_START_REQUEST_RSP</name>
    /// <summary>Response for ZB_START_REQUEST</summary>
    public static final int ZB_START_REQUEST_RSP = 0x6600;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_SYSTEM_RESET</name>
    /// <summary>Reboot the device (zb_SystemReset)</summary>
    public static final int ZB_SYSTEM_RESET = 0x4609;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_WRITE_CONFIGURATION</name>
    /// <summary>Writes a configuration property to nonvolatile memory (zb_WriteConfiguration).</summary>
    public static final int ZB_WRITE_CONFIGURATION = 0x2605;
    /// <name>TI.ZPI2.MESSAGE_ID.ZB_WRITE_CONFIGURATION_RSP</name>
    /// <summary>Response for ZB_WRITE_CONFIGURATION</summary>
    public static final int ZB_WRITE_CONFIGURATION_RSP = 0x6605;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_ACTIVE_EP_REQ</name>
    /// <summary>This command is generated to request a list of active endpoint from the destination device.</summary>
    public static final int ZDO_ACTIVE_EP_REQ = 0x2505;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_ACTIVE_EP_REQ_SRSP</name>
    /// <summary>Response for ZDO_ACTIVE_EP_REQ</summary>
    public static final int ZDO_ACTIVE_EP_REQ_SRSP = 0x6505;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_ACTIVE_EP_RSP</name>
    /// <summary>This callback message is in response to the ZDO Active Endpoint Request.</summary>
    public static final int ZDO_ACTIVE_EP_RSP = 0x4585;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_AUTO_FIND_DESTINATION</name>
    /// <summary>This function will issue a Match Description Request for the requested endpoint outputs.  This message will generate a broadcast message.</summary>
    public static final int ZDO_AUTO_FIND_DESTINATION = 0x4541;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_BIND_REQ</name>
    /// <summary>This command is generated to request a Bind</summary>
    public static final int ZDO_BIND_REQ = 0x2521;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_BIND_REQ_SRSP</name>
    /// <summary>Response for ZDO_BIND_REQ</summary>
    public static final int ZDO_BIND_REQ_SRSP = 0x6521;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_BIND_RSP</name>
    /// <summary>This callback message is in response to the ZDO Bind Request</summary>
    public static final int ZDO_BIND_RSP = 0x45a1;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_COMPLEX_DESC_REQ</name>
    /// <summary>This command is generated to request for  the destination device's complex descriptor</summary>
    public static final int ZDO_COMPLEX_DESC_REQ = 0x2507;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_COMPLEX_DESC_REQ_SRSP</name>
    /// <summary>Response for ZDO_COMPLEX_DESC_REQ</summary>
    public static final int ZDO_COMPLEX_DESC_REQ_SRSP = 0x6507;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_COMPLEX_DESC_RSP</name>
    /// <summary>Response for ZDO_COMPLEX_DESC_REQ</summary>
    public static final int ZDO_COMPLEX_DESC_RSP = 0x4587;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_END_DEVICE_ANNCE</name>
    /// <summary>This command is generated to request an End Device Announce.</summary>
    public static final int ZDO_END_DEVICE_ANNCE = 0x250a;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_END_DEVICE_ANNCE_IND</name>
    /// <summary>ZDO end device announce indication.</summary>
    public static final int ZDO_END_DEVICE_ANNCE_IND = 0x45c1;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_END_DEVICE_ANNCE_SRSP</name>
    /// <summary>Response for ZDO_END_DEVICE_ANNCE</summary>
    public static final int ZDO_END_DEVICE_ANNCE_SRSP = 0x650a;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_END_DEVICE_BIND_REQ</name>
    /// <summary>This command is generated to request an End Device Bind with the destination device</summary>
    public static final int ZDO_END_DEVICE_BIND_REQ = 0x2520;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_END_DEVICE_BIND_REQ_SRSP</name>
    /// <summary>Response for ZDO_END_DEVICE_BIND_REQ</summary>
    public static final int ZDO_END_DEVICE_BIND_REQ_SRSP = 0x6520;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_END_DEVICE_BIND_RSP</name>
    /// <summary>This callback message is in response to the ZDO End Device Bind Request</summary>
    public static final int ZDO_END_DEVICE_BIND_RSP = 0x45a0;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_IEEE_ADDR_REQ</name>
    /// <summary>This command will request a device's IEEE 64-bit address.  You must subscribe to 'ZDO IEEE Address Response' to receive the data response to this message.  The response message listed below only indicates whether or not the message was received properly.</summary>
    public static final int ZDO_IEEE_ADDR_REQ = 0x2501;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_IEEE_ADDR_REQ_SRSP</name>
    /// <summary>Response for ZDO_IEEE_ADDR_REQ</summary>
    public static final int ZDO_IEEE_ADDR_REQ_SRSP = 0x6501;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_IEEE_ADDR_RSP</name>
    /// <summary>This callback message is in response to the ZDO IEEE Address Request.</summary>
    public static final int ZDO_IEEE_ADDR_RSP = 0x4581;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MATCH_DESC_REQ</name>
    /// <summary>This command is generated to request a list of active endpoint from the destination device</summary>
    public static final int ZDO_MATCH_DESC_REQ = 0x2506;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MATCH_DESC_REQ_SRSP</name>
    /// <summary>Response for ZDO_MATCH_DESC_REQ</summary>
    public static final int ZDO_MATCH_DESC_REQ_SRSP = 0x6506;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MATCH_DESC_RSP</name>
    /// <summary>This callback message is in response to the ZDO Match Description Request</summary>
    public static final int ZDO_MATCH_DESC_RSP = 0x4586;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_BIND_REQ</name>
    /// <summary>This command is generated to request a Management Binding Table Request.</summary>
    public static final int ZDO_MGMT_BIND_REQ = 0x2533;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_BIND_REQ_SRSP</name>
    /// <summary>Response for ZDO_MGMT_BIND_REQ</summary>
    public static final int ZDO_MGMT_BIND_REQ_SRSP = 0x6533;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_BIND_RSP</name>
    /// <summary>This callback message is in response to the ZDO Management Binding Table Request</summary>
    public static final int ZDO_MGMT_BIND_RSP = 0x45b3;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_DIRECT_JOIN_REQ</name>
    /// <summary>This command is generated to request a Management Direct Join Request</summary>
    public static final int ZDO_MGMT_DIRECT_JOIN_REQ = 0x2535;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_DIRECT_JOIN_REQ_SRSP</name>
    /// <summary>Response for ZDO_MGMT_DIRECT_JOIN_REQ</summary>
    public static final int ZDO_MGMT_DIRECT_JOIN_REQ_SRSP = 0x6535;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_DIRECT_JOIN_RSP</name>
    /// <summary>This callback message is in response to the ZDO Management Direct Join Request.</summary>
    public static final int ZDO_MGMT_DIRECT_JOIN_RSP = 0x45b5;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_LEAVE_REQ</name>
    /// <summary>This command is generated to request a Management Leave Request</summary>
    public static final int ZDO_MGMT_LEAVE_REQ = 0x2534;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_LEAVE_REQ_SRSP</name>
    /// <summary>Response for ZDO_MGMT_LEAVE_REQ</summary>
    public static final int ZDO_MGMT_LEAVE_REQ_SRSP = 0x6534;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_LEAVE_RSP</name>
    /// <summary>This callback message is in response to the ZDO Management Leave Request.</summary>
    public static final int ZDO_MGMT_LEAVE_RSP = 0x45b4;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_LQI_REQ</name>
    /// <summary>This command is generated to request a Management LQI Request.</summary>
    public static final int ZDO_MGMT_LQI_REQ = 0x2531;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_LQI_REQ_SRSP</name>
    /// <summary>Response for ZDO_MGMT_LQI_REQ</summary>
    public static final int ZDO_MGMT_LQI_REQ_SRSP = 0x6531;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_LQI_RSP</name>
    /// <summary>This callback message is in response to the ZDO Management LQI Request.</summary>
    public static final int ZDO_MGMT_LQI_RSP = 0x45b1;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_NWK_DISC_REQ</name>
    /// <summary>This command is generated to request a Management Network Discovery Request</summary>
    public static final int ZDO_MGMT_NWK_DISC_REQ = 0x2530;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_NWK_DISC_REQ_SRSP</name>
    /// <summary>Response for ZDO_MGMT_NWK_DISC_REQ</summary>
    public static final int ZDO_MGMT_NWK_DISC_REQ_SRSP = 0x6530;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_NWK_DISC_RSP</name>
    /// <summary>This callback message is in response to the ZDO Management Network Discovery Request</summary>
    public static final int ZDO_MGMT_NWK_DISC_RSP = 0x45b0;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_NWK_UPDATE_REQ</name>
    /// <summary>This command is provided to allow updating of network configuration parameters or to request information from devices on network conditions in the local operating environment.</summary>
    public static final int ZDO_MGMT_NWK_UPDATE_REQ = 0x2537;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_NWK_UPDATE_REQ_SRSP</name>
    /// <summary>Response for ZDO_MGMT_NWK_UPDATE_REQ</summary>
    public static final int ZDO_MGMT_NWK_UPDATE_REQ_SRSP = 0x6537;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_PERMIT_JOIN_REQ_SRSP</name>
    /// <summary>Response for ZDO_MGMT_PERMIT_JOIN_REQ</summary>
    public static final int ZDO_MGMT_PERMIT_JOIN_REQ_SRSP = 0x6536;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_PERMIT_JOIN_REQUEST</name>
    /// <summary>This command is generated to request a Management Join Request</summary>
    public static final int ZDO_MGMT_PERMIT_JOIN_REQ = 0x2536;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_PERMIT_JOIN_RSP</name>
    /// <summary>This callback message is in response to the ZDO Management Permit Join Request</summary>
    public static final int ZDO_MGMT_PERMIT_JOIN_RSP = 0x45b6;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_RTG_REQ</name>
    /// <summary>This command is generated to request a Management Routing Table Request.</summary>
    public static final int ZDO_MGMT_RTG_REQ = 0x2532;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_RTG_REQ_SRSP</name>
    /// <summary>Response for ZDO_MGMT_RTG_REQ</summary>
    public static final int ZDO_MGMT_RTG_REQ_SRSP = 0x6532;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_MGMT_RTG_RSP</name>
    /// <summary>This callback message is in response to the ZDO Management Routing Table Request. </summary>
    public static final int ZDO_MGMT_RTG_RSP = 0x45b2;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_NODE_DESC_REQ</name>
    /// <summary>This command is generated to inquire as to the Node Descriptor of the destination device</summary>
    public static final int ZDO_NODE_DESC_REQ = 0x2502;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_NODE_DESC_REQ_SRSP</name>
    /// <summary>Response for ZDO_NODE_DESC_REQ</summary>
    public static final int ZDO_NODE_DESC_REQ_SRSP = 0x6502;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_NODE_DESC_RSP</name>
    /// <summary>This callback message is in response to the ZDO Node Descriptor Request.</summary>
    public static final int ZDO_NODE_DESC_RSP = 0x4582;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_NWK_ADDR_REQ</name>
    /// <summary>This message will request the device to send a "Network Address Request".  This message sends a broadcast message looking for a 16 bit address with a 64 bit address as bait.   You must subscribe to "ZDO Network Address Response" to receive the response to this message.  The response message listed below only indicates whether or not the message was received properly.</summary>
    public static final int ZDO_NWK_ADDR_REQ = 0x2500;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_NWK_ADDR_REQ_SRSP</name>
    /// <summary>Response for ZDO_NWK_ADDR_REQ</summary>
    public static final int ZDO_NWK_ADDR_REQ_SRSP = 0x6500;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_NWK_ADDR_RSP</name>
    /// <summary>This callback message is in response to the ZDO Network Address Request.</summary>
    public static final int ZDO_NWK_ADDR_RSP = 0x4580;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_POWER_DESC_REQ</name>
    /// <summary>This command is generated to inquire as to the Power Descriptor of the destination</summary>
    public static final int ZDO_POWER_DESC_REQ = 0x2503;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_POWER_DESC_REQ_SRSP</name>
    /// <summary>Response for ZDO_POWER_DESC_REQ</summary>
    public static final int ZDO_POWER_DESC_REQ_SRSP = 0x6503;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_POWER_DESC_RSP</name>
    /// <summary>This callback message is in response to the ZDO Power Descriptor Request.</summary>
    public static final int ZDO_POWER_DESC_RSP = 0x4583;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_SERVER_DISC_REQ</name>
    /// <summary>The command is used for local device to discover the location of a particular system server or servers as indicated by the ServerMask parameter. The destination addressing on this request is 'broadcast to all RxOnWhenIdle devices'.</summary>
    public static final int ZDO_SERVER_DISC_REQ = 0x250c;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_SERVER_DISC_REQ_SRSP</name>
    /// <summary>Response for ZDO_SERVER_DISC_REQ</summary>
    public static final int ZDO_SERVER_DISC_REQ_SRSP = 0x650c;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_SERVER_DISC_RSP</name>
    /// <summary>This callback message is</summary>
    public static final int ZDO_SERVER_DISC_RSP = 0x458a;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_SIMPLE_DESC_REQ</name>
    /// <summary>This command is generated to inquire as to the Simple Descriptor of the destination device's Endpoint</summary>
    public static final int ZDO_SIMPLE_DESC_REQ = 0x2504;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_SIMPLE_DESC_REQ_SRSP</name>
    /// <summary>Response for ZDO_SIMPLEDESCRIPTOR_REQUEST</summary>
    public static final int ZDO_SIMPLE_DESC_REQ_SRSP = 0x6504;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_SIMPLE_DESC_RSP</name>
    /// <summary>This callback message is in response to the ZDO Simple Descriptor Request.</summary>
    public static final int ZDO_SIMPLE_DESC_RSP = 0x4584;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_STARTUP_FROM_APP</name>
    /// <summary>In the case where compiler flag HOLD_AUTO_START is defined by default; device will start from HOLD state. Issuing this command will trigger the device to leave HOLD state to form or join a network.</summary>
    public static final int ZDO_STARTUP_FROM_APP = 0x2540;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_STARTUP_FROM_APP_SRSP</name>
    /// <summary>Response for ZDO_STARTUP_FROM_APP</summary>
    public static final int ZDO_STARTUP_FROM_APP_SRSP = 0x6540;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_STATE_CHANGE_IND</name>
    /// <summary>ZDO state change indication.</summary>
    public static final int ZDO_STATE_CHANGE_IND = 0x45c0;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_STATUS_ERROR_RSP</name>
    /// <summary>This message is the default message for error status.</summary>
    public static final int ZDO_STATUS_ERROR_RSP = 0x45c3;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_UNBIND_REQ</name>
    /// <summary>This command is generated to request an UnBind</summary>
    public static final int ZDO_UNBIND_REQ = 0x2522;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_UNBIND_REQ_SRSP</name>
    /// <summary>Response for ZDO_UNBIND_REQ</summary>
    public static final int ZDO_UNBIND_REQ_SRSP = 0x6522;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_UNBIND_RSP</name>
    /// <summary>This callback message is in response to the ZDO UnBind Request</summary>
    public static final int ZDO_UNBIND_RSP = 0x45a2;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_USER_DESC_CONF</name>
    /// <summary>This callback message is in response to the ZDO User Descriptor Set Request.</summary>
    public static final int ZDO_USER_DESC_CONF = 0x4589;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_USER_DESC_REQ</name>
    /// <summary>This command is generated to request for the destination device's user descriptor</summary>
    public static final int ZDO_USER_DESC_REQ = 0x2508;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_USER_DESC_REQ_SRSP</name>
    /// <summary>Response for ZDO_USER_DESC_REQ</summary>
    public static final int ZDO_USER_DESC_REQ_SRSP = 0x6508;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_USER_DESC_RSP</name>
    /// <summary>This callback message is in response to the ZDO User Description Request.</summary>
    public static final int ZDO_USER_DESC_RSP = 0x4588;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_USER_DESC_SET</name>
    /// <summary>This command is generated to request a User Descriptor Set Request</summary>
    public static final int ZDO_USER_DESC_SET = 0x250b;
    /// <name>TI.ZPI2.MESSAGE_ID.ZDO_USER_DESC_SET_SRSP</name>
    /// <summary>Response for ZDO_USER_DESC_SET</summary>
    public static final int ZDO_USER_DESC_SET_SRSP = 0x650b;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_ASSOCIATE_CNF</name>
    /// <summary>This sends a associat confirm command</summary>
    public static final int ZMAC_ASSOCIATE_CNF = 0x4282;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_ASSOCIATE_IND</name>
    /// <summary>This command is used to send (on behalf of the next higher layer) an association indication message</summary>
    public static final int ZMAC_ASSOCIATE_IND = 0x4281;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_ASSOCIATE_REQUEST</name>
    /// <summary>This command is used to request (on behalf of the next higher layer) an association with a coordinator</summary>
    public static final int ZMAC_ASSOCIATE_REQUEST = 0x2206;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_ASSOCIATE_RESPONSE</name>
    /// <summary>Response for ZMAC_ASSOCIATE_REQUEST</summary>
    public static final int ZMAC_ASSOCIATE_RESPONSE = 0x6206;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_BEACON_NOTIFY_IND</name>
    /// <summary>Beacon Notify Indication</summary>
    public static final int ZMAC_BEACON_NOTIFY_IND = 0x4283;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_COMM_STATUS_IND</name>
    /// <summary>Communcation status indication</summary>
    public static final int ZMAC_COMM_STATUS_IND = 0x428d;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_DATA_CNF</name>
    /// <summary>Data Request Confirmation</summary>
    public static final int ZMAC_DATA_CNF = 0x4284;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_DATA_IND</name>
    /// <summary>Data Request Confirmation</summary>
    public static final int ZMAC_DATA_IND = 0x4285;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_DATA_REQUEST</name>
    /// <summary>This command is used to send (on behalf of the next higher layer) MAC Data Frame packet.</summary>
    public static final int ZMAC_DATA_REQUEST = 0x2205;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_DATA_RESPONSE</name>
    /// <summary>Response for ZMAC_DATA_REQUEST</summary>
    public static final int ZMAC_DATA_RESPONSE = 0x6205;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_DISASSOCIATE_CNF</name>
    /// <summary>Disassociate Indication</summary>
    public static final int ZMAC_DISASSOCIATE_CNF = 0x4287;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_DISASSOCIATE_IND</name>
    /// <summary>Disassociate Indication</summary>
    public static final int ZMAC_DISASSOCIATE_IND = 0x4286;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_DISASSOCIATE_REQUEST</name>
    /// <summary>This command is used to request (on behalf of the next higher layer) a disassociation of the device from the coordinator.</summary>
    public static final int ZMAC_DISASSOCIATE_REQUEST = 0x2207;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_DISASSOCIATE_RESPONSE</name>
    /// <summary>Response for ZMAC_DISASSOCIATE_REQUEST</summary>
    public static final int ZMAC_DISASSOCIATE_RESPONSE = 0x6207;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_GET_REQUEST</name>
    /// <summary>This command is used to read (on behalf of the next higher layer) a MAC PIB attribute.</summary>
    public static final int ZMAC_GET_REQUEST = 0x2208;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_GET_RESPONSE</name>
    /// <summary>Response for ZMAC_GET_REQUEST</summary>
    public static final int ZMAC_GET_RESPONSE = 0x6208;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_INIT_REQUEST</name>
    /// <summary>This command is used to initialize the ZMAC on the current device (on behalf of the next higher layer).</summary>
    public static final int ZMAC_INIT_REQUEST = 0x2202;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_INIT_RESPONSE</name>
    /// <summary>Response for ZMAC_INIT_REQUEST</summary>
    public static final int ZMAC_INIT_RESPONSE = 0x6202;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_ORPHAN_IND</name>
    /// <summary>Orphan Indication</summary>
    public static final int ZMAC_ORPHAN_IND = 0x428a;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_POLL_CNF</name>
    /// <summary>Mac Poll Confirmation</summary>
    public static final int ZMAC_POLL_CNF = 0x428b;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_POLL_REQUEST</name>
    /// <summary>This command is used to send a MAC data request poll</summary>
    public static final int ZMAC_POLL_REQUEST = 0x220d;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_POLL_RESPONSE</name>
    /// <summary>Response for ZMAC_POLL_REQUEST</summary>
    public static final int ZMAC_POLL_RESPONSE = 0x620d;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_PURGE_CNF</name>
    /// <summary>Mac RX enable Confirmation</summary>
    public static final int ZMAC_PURGE_CNF = 0x4290;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_PURGE_REQUEST</name>
    /// <summary>This command is used to send a request to the device to purge a data frame</summary>
    public static final int ZMAC_PURGE_REQUEST = 0x220e;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_PURGE_RESPONSE</name>
    /// <summary>Response for ZMAC_PURGE_REQUEST</summary>
    public static final int ZMAC_PURGE_RESPONSE = 0x620e;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_RESET_REQUEST</name>
    /// <summary>This command is used to send a MAC Reset command to reset MAC state machine</summary>
    public static final int ZMAC_RESET_REQUEST = 0x2201;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_RESET_RESPONSE</name>
    /// <summary>Response for ZMAC_RESET_REQUEST</summary>
    public static final int ZMAC_RESET_RESPONSE = 0x6201;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_RX_ENABLE_CNF</name>
    /// <summary>Mac RX enable Confirmation</summary>
    public static final int ZMAC_RX_ENABLE_CNF = 0x428f;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_RX_ENABLE_REQUEST</name>
    /// <summary>This command contains timing information that tells the device when to enable or disable its receiver; in order to schedule a data transfer between itself and another device. The information is sent from the upper layers directly to the MAC sublayer.</summary>
    public static final int ZMAC_RX_ENABLE_REQUEST = 0x220b;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_RX_ENABLE_RESPONSE</name>
    /// <summary>Response for ZMAC_RX_ENABLE_REQUEST</summary>
    public static final int ZMAC_RX_ENABLE_RESPONSE = 0x620b;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SCAN_CNF</name>
    /// <summary>Scan Confirmation</summary>
    public static final int ZMAC_SCAN_CNF = 0x428c;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SCAN_REQUEST</name>
    /// <summary>This command is used to send a request to the device to perform a network scan.</summary>
    public static final int ZMAC_SCAN_REQUEST = 0x220c;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SCAN_RESPONSE</name>
    /// <summary>Response for ZMAC_SCAN_REQUEST</summary>
    public static final int ZMAC_SCAN_RESPONSE = 0x620c;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SET_REQUEST</name>
    /// <summary>This command is used to request the device to write a MAC PIB value.</summary>
    public static final int ZMAC_SET_REQUEST = 0x2209;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SET_RESPONSE</name>
    /// <summary>Response for ZMAC_SET_REQUEST</summary>
    public static final int ZMAC_SET_RESPONSE = 0x6209;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SET_RX_GAIN_REQUEST</name>
    /// <summary>This command is used to send a request to the device to set Rx gain</summary>
    public static final int ZMAC_SET_RX_GAIN_REQUEST = 0x220f;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SET_RX_GAIN_RESPONSE</name>
    /// <summary>Response for ZMAC_SET_RX_GAIN_REQUEST</summary>
    public static final int ZMAC_SET_RX_GAIN_RESPONSE = 0x620f;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_START_CNF</name>
    /// <summary>Mac Start Confirmation</summary>
    public static final int ZMAC_START_CNF = 0x428e;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_START_REQUEST</name>
    /// <summary>This command is used to request the MAC to transmit beacons and become a coordinator</summary>
    public static final int ZMAC_START_REQUEST = 0x2203;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_START_RESPONSE</name>
    /// <summary>Response for ZMAC_START_REQUEST</summary>
    public static final int ZMAC_START_RESPONSE = 0x6203;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SYNC_LOSS_IND</name>
    /// <summary>Indication for sync loss</summary>
    public static final int ZMAC_SYNC_LOSS_IND = 0x4280;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SYNCHRONIZE_REQUEST</name>
    /// <summary>This command is used to request synchronization to the current network beacon</summary>
    public static final int ZMAC_SYNCHRONIZE_REQUEST = 0x2204;
    /// <name>TI.ZPI2.MESSAGE_ID.ZMAC_SYNCHRONIZE_RESPONSE</name>
    /// <summary>Response for ZMAC_SYNCHRONIZE_REQUEST</summary>
    public static final int ZMAC_SYNCHRONIZE_RESPONSE = 0x6204;

}
