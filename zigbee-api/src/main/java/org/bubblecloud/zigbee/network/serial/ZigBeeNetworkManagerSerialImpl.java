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
package org.bubblecloud.zigbee.network.serial;

import org.bubblecloud.zigbee.network.*;
import org.bubblecloud.zigbee.network.packet.*;
import org.bubblecloud.zigbee.network.packet.af.*;
import org.bubblecloud.zigbee.network.packet.simple.*;
import org.bubblecloud.zigbee.network.packet.system.SYS_RESET;
import org.bubblecloud.zigbee.network.packet.system.SYS_RESET_RESPONSE;
import org.bubblecloud.zigbee.network.packet.util.UTIL_GET_DEVICE_INFO;
import org.bubblecloud.zigbee.network.packet.util.UTIL_GET_DEVICE_INFO_RESPONSE;
import org.bubblecloud.zigbee.network.packet.zdo.*;
import org.bubblecloud.zigbee.util.Integers;
import org.bubblecloud.zigbee.network.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The zigbee network manager serial port implementation.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:michele.girolami@isti.cnr.it">Michele Girolami</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigBeeNetworkManagerSerialImpl implements ZigBeeNetworkManager {

    private final static Logger logger = LoggerFactory.getLogger(ZigBeeNetworkManagerSerialImpl.class);
    private final static Logger logger4Waiter = LoggerFactory.getLogger(WaitForCommand.class);

    public static final int DEFAULT_TIMEOUT = 5000;
    public static final String TIMEOUT_KEY = "zigbee.driver.cc2530.timeout";

    public static final int RESEND_TIMEOUT_DEFAULT = 1000;
    public static final String RESEND_TIMEOUT_KEY = "zigbee.driver.cc2530.resend.timeout";

    public static final int RESEND_MAX_RESEND_DEFAULT = 3;
    public static final String RESEND_MAX_RESEND_KEY = "zigbee.driver.cc2530.resend.max";

    public static final boolean RESEND_ONLY_EXCEPTION_DEFAULT = true;
    public static final String RESEND_ONLY_EXCEPTION_KEY = "zigbee.driver.cc2530.resend.exceptionally";

    private final int TIMEOUT;
    private final int RESEND_TIMEOUT;
    private final int RESEND_MAX_RETRY;
    private final boolean RESEND_ONLY_EXCEPTION;

    private ZigBeeSerialInterface zigbeeSerialInterface;
    private String port;
    private int rate;
    private DriverStatus state;
    private NetworkMode mode;
    private short pan;
    private byte channel;
    private boolean cleanStatus;

    private final HashSet<AnnounceListener> announceListeners = new HashSet<AnnounceListener>();
    private final AnnounceListenerFilter announceListenerFilter = new AnnounceListenerFilter(announceListeners);

    private final ArrayList<ApplicationFrameworkMessageListener> messageListeners = new ArrayList<ApplicationFrameworkMessageListener>();
    private final AFMessageListenerFilter afMessageListenerFilter = new AFMessageListenerFilter(messageListeners);

    private long ieeeAddress = -1;
    private final HashMap<Class<?>, Thread> conversation3Way = new HashMap<Class<?>, Thread>();

    public ZigBeeNetworkManagerSerialImpl(String serialPort, int bitRate, NetworkMode mode, int pan, int channel,
                                          boolean cleanNetworkStatus, long timeout) {

        int aux = RESEND_TIMEOUT_DEFAULT;
        try {
            aux = Integer.parseInt(System.getProperty(RESEND_TIMEOUT_KEY));
            logger.trace("Using RESEND_TIMEOUT set from enviroment {}", aux);
        } catch (NumberFormatException ex) {
            logger.trace("Using RESEND_TIMEOUT set as DEFAULT {}", aux);
        }
        RESEND_TIMEOUT = aux;

        aux = (int) Math.max(DEFAULT_TIMEOUT, timeout);
        try {
            aux = Integer.parseInt(System.getProperty(TIMEOUT_KEY));
            logger.trace("Using TIMEOUT set from enviroment {}", aux);
        } catch (NumberFormatException ex) {
            logger.trace("Using TIMEOUT set as DEFAULT {}ms", aux);
        }
        TIMEOUT = aux;

        aux = RESEND_MAX_RESEND_DEFAULT;
        try {
            aux = Integer.parseInt(System.getProperty(RESEND_MAX_RESEND_KEY));
            logger.trace("Using RESEND_MAX_RETRY set from enviroment {}", aux);
        } catch (NumberFormatException ex) {
            logger.trace("Using RESEND_MAX_RETRY set as DEFAULT {}", aux);
        }
        RESEND_MAX_RETRY = aux;

        boolean b = RESEND_ONLY_EXCEPTION_DEFAULT;
        try {
            aux = Integer.parseInt(System.getProperty(RESEND_ONLY_EXCEPTION_KEY));
            logger.trace("Using RESEND_MAX_RETRY set from enviroment {}", aux);
        } catch (NumberFormatException ex) {
            logger.trace("Using RESEND_MAX_RETRY set as DEFAULT {}", aux);
        }
        RESEND_ONLY_EXCEPTION = b;
        state = DriverStatus.CLOSED;
        this.cleanStatus = cleanNetworkStatus;
        setSerialPort(serialPort, bitRate);
        setZigBeeNetwork((byte) channel, (short) pan);
        setZigBeeNodeMode(mode);
    }

    public void startup() {
        if (state == DriverStatus.CLOSED) {
            state = DriverStatus.CREATED;
            logger.trace("Initializing hardware.");
            setState(DriverStatus.HARDWARE_INITIALIZING);
            if (initializeHardware()) {
                setState(DriverStatus.HARDWARE_READY);
            } else {
                shutdown();
                return;
            }

            logger.trace("Initializing network.");
            setState(DriverStatus.NETWORK_INITIALIZING);
            if (!initializeZigBeeNetwork()) {
                shutdown();
                return;
            }
        } else {
            throw new IllegalStateException("Driver already opened, current status is:" + state);
        }
    }

    public void shutdown() {
        if (state == DriverStatus.CLOSED) {
            logger.debug("Already CLOSED");
            return;
        }
        if (state == DriverStatus.NETWORK_READY) {
            logger.trace("Closing NETWORK");
            setState(DriverStatus.HARDWARE_READY);
        }
        if (state == DriverStatus.NETWORK_INITIALIZING || state == DriverStatus.HARDWARE_READY) {
            logger.trace("Closing HARDWARE");
            dongleReset();
            zigbeeSerialInterface.close();
            setState(DriverStatus.CREATED);
        }
        setState(DriverStatus.CLOSED);
    }

    @SuppressWarnings("unchecked")
    private boolean initializeHardware() {
        zigbeeSerialInterface = new ZigBeeSerialInterface(port);
        if (!zigbeeSerialInterface.open()) {
            logger.error("Failed to initialize the dongle on port {} at rate {}", port, rate);
            return false;
        }
        return true;
    }

    private boolean initializeZigBeeNetwork() {
        if (cleanStatus) {
            if (!configureZigBeeNetwork()) {
                return false;
            }
        }
        if (!createZigBeeNetwork()) {
            logger.error("Failed to start zigbee network.");
            return false;
        }
        if (checkZigBeeNetworkConfiguration()) {
            logger.error("Dongle configuration does not match the specified configuration.");
            return false;
        }
        return true;
    }

    private boolean createZigBeeNetwork() {
        createCustomDevicesOnDongle();
        switch (mode) {
            case Coordinator:
                logger.debug("Creating network as coordinator.");
            break;

            case Router:
                logger.debug("Creating network as router.");
            break;

            case EndDevice:
                logger.debug("Creating network as end device.");
            break;
        }

        final int INSTANT_STARTUP = 0;

        ZDO_STARTUP_FROM_APP_SRSP response = (ZDO_STARTUP_FROM_APP_SRSP) sendSynchrouns(
                zigbeeSerialInterface, new ZDO_STARTUP_FROM_APP(INSTANT_STARTUP)
        );
        if (response == null) return false;
        switch (response.Status) {
            case 0: {
                logger.info("Initialized ZigBee network with existing network state.");
                return true;
            }
            case 1: {
                logger.info("Initialized ZigBee network with new or reset network state.");
                return true;
            }
            case 2: {
                logger.warn("Initializing ZigBee network failed.");
                return false;
            }
            default: {
                logger.error("Unexpected response state for ZDO_STARTUP_FROM_APP {}", response.Status);
                return false;
            }
        }


    }

    private boolean checkZigBeeNetworkConfiguration() {
        int value = -1;
        boolean mismatch = false;
        if ((value = getCurrentChannel()) != channel) {
            logger.warn(
                    "The channel configuration differ from the channel configuration in use: " +
                            "in use {}, while the configured is {}.\n" +
                            "The ZigBee network should be reconfigured or configuration corrected.",
                    value, channel
            );
            mismatch = true;
        }
        if ((value = getCurrentPanId()) != pan) {
            logger.warn(
                    "The PanId configuration differ from the channel configuration in use: " +
                            "in use {}, while the configured is {}.\n" +
                            "The ZigBee network should be reconfigured or configuration corrected.",
                    value, pan
            );
            mismatch = true;
        }
        if ((value = getZigBeeNodeMode()) != mode.ordinal()) {
            logger.warn(
                    "The NetworkMode configuration differ from the channel configuration in use: " +
                            "in use {}, while the configured is {}.\n" +
                            "The ZigBee network should be reconfigured or configuration corrected.",
                    value, mode.ordinal()
            );
            mismatch = true;
        }

        return mismatch;
    }

    private boolean configureZigBeeNetwork() {
        logger.debug("Resetting network stack.");

        logger.error("Setting clean state.");
        if (!dongleSetCleanState(true)) {
            logger.error("Unable to set clean state for dongle");
            return false;
        }
        logger.debug("Setting channel to {}.", channel);
        if (!dongleSetChannel()) {
            logger.error("Unable to set CHANNEL for ZigBee Network");
            return false;
        } else {
            logger.trace("CHANNEL set");
        }
        logger.debug("Setting PAN to {}.", pan);
        if (!dongleSetPanId()) {
            logger.error("Unable to set PANID for ZigBee Network");
            return false;
        } else {
            logger.trace("PANID set");
        }
        logger.debug("Changing the Network Mode to {}.", mode);
        if (dongleSetNetworkMode() == false) {
            logger.error("Unable to set NETWORK_MODE for ZigBee Network");
            return false;
        } else {
            logger.trace("NETWORK_MODE set");
        }
        if (!dongleReset()) {
            logger.error("Unable to reset dongle");
            return false;
        }
        if (!dongleSetCleanState(false)) {
            logger.error("Unable to unset clean state for dongle");
            return false;
        }
        return true;
    }

    protected boolean dongleMasterReset() {
        //---------START FROM APP
        logger.trace("Reset seq: Trying STARTFROMAPP");
        ZDO_STARTUP_FROM_APP_SRSP responseA1 = (ZDO_STARTUP_FROM_APP_SRSP) sendSynchrouns(
                zigbeeSerialInterface, new ZDO_STARTUP_FROM_APP(ZDO_STARTUP_FROM_APP.RESET_TYPE.TARGET_DEVICE)
        );
        if (responseA1 == null) {
            logger.error("Reset seq: Failed STARTFROMAPP");
            return false;
        }
        //---------ZB WRITE CONF
        logger.trace("Reset seq: Trying WRITECONF");
        ZB_WRITE_CONFIGURATION_RSP responseA2 = (ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
                zigbeeSerialInterface, new ZB_WRITE_CONFIGURATION(3, new int[]{2})
        );
        if (responseA2 == null) {
            logger.error("Reset seq: Failed WRITECONF");
            return false;
        }
        //---------GET DEVICE INFO
        logger.debug("Reset seq: Trying GETDEVICEINFO");
//		final WaitForCommand waiter1 = new WaitForCommand(
//				ZToolCMD.UTIL_GET_DEVICE_INFO_RESPONSE,
//				zigbeeSerialInterface
//		);
//		try{
//			zigbeeSerialInterface.sendAsynchrounsCommand(new UTIL_GET_DEVICE_INFO());
//		} catch (IOException e) {
//			logger.error("GetDeviceInfo of Master Reset failed", e);
//			return false;
//		}
//		UTIL_GET_DEVICE_INFO_RESPONSE responseA3 = (UTIL_GET_DEVICE_INFO_RESPONSE) waiter1.getCommand(TIMEOUT);
        UTIL_GET_DEVICE_INFO_RESPONSE responseA3 = (UTIL_GET_DEVICE_INFO_RESPONSE) sendSynchrouns(zigbeeSerialInterface, new UTIL_GET_DEVICE_INFO());
        if (responseA3 == null) {
            logger.error("Reset seq: Failed GETDEVICEINFO");
            return false;
        }
        ZToolAddress16[] addresses = new ZToolAddress16[responseA3.AssocDevicesList.length];
        for (int k = 0; k < responseA3.AssocDevicesList.length; k++) {
            addresses[k] = new ZToolAddress16(responseA3.AssocDevicesList[k].getMsb(), responseA3.AssocDevicesList[k].getLsb());
        }
        //---------ZDO GET IEEE ADDR
        logger.trace("Reset seq: Trying GETIEEEADDR");
        ZToolAddress64[] longAddresses = new ZToolAddress64[addresses.length];
        for (int k = 0; k < addresses.length; k++) {
//			ZDO_IEEE_ADDR_RSP responseA4 = sendZDOIEEEAddressRequest(new ZDO_IEEE_ADDR_REQ(addresses[k],ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED.getValue(),0));

            ZDO_IEEE_ADDR_RSP responseA4 = null;
            WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_IEEE_ADDR_RSP, zigbeeSerialInterface);
            logger.trace("Sending ZDO_IEEE_ADDR_REQ");
            ZDO_IEEE_ADDR_REQ_SRSP response = (ZDO_IEEE_ADDR_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, new ZDO_IEEE_ADDR_REQ(addresses[k], ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED.getValue(), 0));
            if (response == null || response.Status != 0) {
                logger.trace("ZDO_IEEE_ADDR_REQ failed, received {}", response);
                waiter.cleanup();
            } else {
                responseA4 = (ZDO_IEEE_ADDR_RSP) waiter.getCommand(TIMEOUT);
            }

            if (responseA4 != null) {
                longAddresses[k] = responseA4.getIEEEAddress();
            } else {
                longAddresses[k] = null;
            }
        }
        //---------ZDO MGMT LEAVE
        logger.trace("Reset seq: Trying LEAVE");
        long start = System.currentTimeMillis();
        for (int k = 0; k < longAddresses.length; k++) {
            if (longAddresses[k] != null) {
                WaitForCommand waiter3 = new WaitForCommand(ZToolCMD.ZDO_MGMT_LEAVE_RSP, zigbeeSerialInterface);

                ZDO_MGMT_LEAVE_REQ_SRSP response = (ZDO_MGMT_LEAVE_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, new ZDO_MGMT_LEAVE_REQ(addresses[k], longAddresses[k], 3));
                if ((System.currentTimeMillis() - start) > TIMEOUT) {
                    logger.error("Reset seq: Failed LEAVE");
                    return false;
                }
                if (response == null || response.Status != 0) {
                    waiter3.cleanup();
                    logger.error("Reset seq: Failed LEAVE");
                    return false;
                } else {
                    if ((System.currentTimeMillis() - start) > TIMEOUT) {
                        logger.error("Reset seq: Failed LEAVE");
                        return false;
                    }
                    ZDO_MGMT_LEAVE_RSP responseA5 = (ZDO_MGMT_LEAVE_RSP) waiter3.getCommand(TIMEOUT);
                    if ((System.currentTimeMillis() - start) > TIMEOUT) {
                        logger.error("Reset seq: Failed LEAVE");
                        return false;
                    }
                    if (responseA5 == null || responseA5.Status != 0) {
                        logger.error("Reset seq: Failed LEAVE");
                        return false;
                    }
                }
            }
            if ((System.currentTimeMillis() - start) > TIMEOUT) {
                logger.error("Reset seq: Failed LEAVE");
                return false;
            }
        }

        //---------SYS RESET
        logger.debug("Reset seq: Trying SYSRESET");
        if (!dongleReset()) {
            logger.error("Reset seq: Failed SYSRESET");
            return false;
        }
        //---------START FROM APP
//		//already in initializeZigBeeNetwork
//		ZDO_STARTUP_FROM_APP_SRSP responseA7 = (ZDO_STARTUP_FROM_APP_SRSP) sendSynchrouns(
//				zigbeeSerialInterface, new ZDO_STARTUP_FROM_APP(ZDO_STARTUP_FROM_APP.RESET_TYPE.TARGET_DEVICE)
//		);
//		if (responseA7==null)return false;
        return true;
    }

    private void setState(DriverStatus value) {
        logger.trace("{} -> {}", this.state, value);
        synchronized (this) {
            state = value;
            notifyAll();
        }
        if (state == DriverStatus.HARDWARE_READY) {
            postHardwareEnabled();
        }
    }

    private void postHardwareEnabled() {
        if (!messageListeners.contains(afMessageListenerFilter)) {
            zigbeeSerialInterface.addAsynchronousCommandListener(afMessageListenerFilter);
        }
        if (!announceListeners.contains(announceListenerFilter)) {
            zigbeeSerialInterface.addAsynchronousCommandListener(announceListenerFilter);
        }
    }

    private boolean waitForHardware() {
        synchronized (this) {
            while (state == DriverStatus.CREATED || state == DriverStatus.CLOSED) {
                logger.debug("Waiting for hardware to become ready");
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            return isHardwareReady();
        }
    }

    private boolean waitForNetwork() {
        synchronized (this) {
            while (state != DriverStatus.NETWORK_READY && state != DriverStatus.CLOSED) {
                logger.debug("Waiting for network to become ready");
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            return isNetworkReady();
        }
    }


    private String buildDriverThreadName(String serialPort, int bitrate, int channel) {
        return "ZigBeeNetworkManager[" + serialPort + "," + bitrate + "]";
    }

    public void setZigBeeNodeMode(NetworkMode m) {
        if (state != DriverStatus.CLOSED) {
            throw new IllegalStateException("Network mode can be changed only " +
                    "if driver is CLOSED while it is:" + state);
        }
//        cleanStatus = mode != m;
        mode = m;
    }

    public void setZigBeeNetwork(byte ch, short panId) {
        if (state != DriverStatus.CLOSED) {
            throw new IllegalStateException("Network mode can be changed only " +
                    "if driver is CLOSED while it is:" + state);
        }
        //cleanStatus = ch != channel || panId != pan;
        channel = ch;
        pan = panId;
    }

    public void setSerialPort(String serialName, int bitRate) {
        if (state != DriverStatus.CLOSED) {
            throw new IllegalStateException("Serial port can be changed only " +
                    "if driver is CLOSED while it is:" + state);
        }
        port = serialName;
        rate = bitRate;
    }

    @Override
    public HashSet<AnnounceListener> getAnnounceListeners() {
        return announceListeners;
    }

    public <REQUEST extends ZToolPacket, RESPONSE extends ZToolPacket> RESPONSE sendLocalRequest(REQUEST request) {
        if (waitForNetwork() == false) return null;
        RESPONSE result = (RESPONSE) sendSynchrouns(zigbeeSerialInterface, request);
        if (result == null) {
            logger.error("{} timed out waiting for synchronous local response.", request.getClass().getSimpleName());
        }
        return result;
    }

    public <REQUEST extends ZToolPacket, RESPONSE extends ZToolPacket> RESPONSE sendRemoteRequest(REQUEST request) {
        if (waitForNetwork() == false) return null;
        RESPONSE result = null;

        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_MGMT_PERMIT_JOIN_RSP, zigbeeSerialInterface);

        logger.trace("Sending {}", request);
        ZToolPacket response = sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null) {
            logger.error("{} timed out waiting for synchronous local response.", request.getClass().getSimpleName());
            waiter.cleanup();
            return null;
        } else {
            logger.error("{} timed out waiting for asynchronous remote response.", request.getClass().getSimpleName());
            result = (RESPONSE) waiter.getCommand(TIMEOUT);
            unLock3WayConversation(request);
            return result;
        }
    }

    public ZDO_MGMT_LQI_RSP sendLQIRequest(ZDO_MGMT_LQI_REQ request) {

        if (waitForNetwork() == false) return null;
        ZDO_MGMT_LQI_RSP result = null;

        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_MGMT_LQI_RSP, zigbeeSerialInterface);

        logger.trace("Sending ZDO_MGMT_LQI_REQ {}", request);
        ZDO_MGMT_LQI_REQ_SRSP response = (ZDO_MGMT_LQI_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null || response.Status != 0) {
            logger.trace("ZDO_MGMT_LQI_REQ failed, received {}", response);
            waiter.cleanup();
        } else {
            result = (ZDO_MGMT_LQI_RSP) waiter.getCommand(TIMEOUT);
        }
        unLock3WayConversation(request);
        return result;
    }

    public ZDO_IEEE_ADDR_RSP sendZDOIEEEAddressRequest(ZDO_IEEE_ADDR_REQ request) {
        if (waitForNetwork() == false) return null;
        ZDO_IEEE_ADDR_RSP result = null;

        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_IEEE_ADDR_RSP, zigbeeSerialInterface);

        logger.trace("Sending ZDO_IEEE_ADDR_REQ {}", request);
        ZDO_IEEE_ADDR_REQ_SRSP response = (ZDO_IEEE_ADDR_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null || response.Status != 0) {
            logger.warn("ZDO_IEEE_ADDR_REQ failed, received {}", response);
            waiter.cleanup();
        } else {
            result = (ZDO_IEEE_ADDR_RSP) waiter.getCommand(TIMEOUT);
        }
        unLock3WayConversation(request);
        return result;
    }

    public ZDO_NODE_DESC_RSP sendZDONodeDescriptionRequest(ZDO_NODE_DESC_REQ request) {
        if (waitForNetwork() == false) return null;
        ZDO_NODE_DESC_RSP result = null;

        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_NODE_DESC_RSP, zigbeeSerialInterface);

        ZDO_NODE_DESC_REQ_SRSP response = (ZDO_NODE_DESC_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null || response.Status != 0) {
            waiter.cleanup();
        } else {
            result = (ZDO_NODE_DESC_RSP) waiter.getCommand(TIMEOUT);
        }

        unLock3WayConversation(request);
        return result;
    }

    public ZDO_ACTIVE_EP_RSP sendZDOActiveEndPointRequest(ZDO_ACTIVE_EP_REQ request) {
        if (waitForNetwork() == false) return null;
        ZDO_ACTIVE_EP_RSP result = null;

        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_ACTIVE_EP_RSP, zigbeeSerialInterface);

        logger.trace("Sending ZDO_ACTIVE_EP_REQ {}", request);
        ZDO_ACTIVE_EP_REQ_SRSP response = (ZDO_ACTIVE_EP_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null || response.Status != 0) {
            logger.trace("ZDO_ACTIVE_EP_REQ failed, received {}", response);
            waiter.cleanup();
        } else {
            result = (ZDO_ACTIVE_EP_RSP) waiter.getCommand(TIMEOUT);
        }
        unLock3WayConversation(request);
        return result;
    }

    public ZDO_MGMT_PERMIT_JOIN_RSP sendPermitJoinRequest(ZDO_MGMT_PERMIT_JOIN_REQ request) {
        if (waitForNetwork() == false) return null;
        ZDO_MGMT_PERMIT_JOIN_RSP result = null;

        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_MGMT_PERMIT_JOIN_RSP, zigbeeSerialInterface);

        logger.trace("Sending ZDO_MGMT_PERMIT_JOIN_REQ {}", request);
        ZDO_MGMT_PERMIT_JOIN_REQ_SRSP response = (ZDO_MGMT_PERMIT_JOIN_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null || response.Status != 0) {
            logger.trace("ZDO_MGMT_PERMIT_JOIN_REQ failed, received {}", response);
            waiter.cleanup();
        } else {
            result = (ZDO_MGMT_PERMIT_JOIN_RSP) waiter.getCommand(TIMEOUT);
        }
        unLock3WayConversation(request);
        return result;
    }

    public boolean sendZDOLeaveRequest(ZToolAddress16[] addresses) {
        //---------ZDO GET IEEE ADDR
        logger.trace("Reset seq: Trying GETIEEEADDR");
        ZToolAddress64[] longAddresses = new ZToolAddress64[addresses.length];
        for (int k = 0; k < addresses.length; k++) {
//			ZDO_IEEE_ADDR_RSP responseA4 = sendZDOIEEEAddressRequest(new ZDO_IEEE_ADDR_REQ(addresses[k],ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED.getValue(),0));

            ZDO_IEEE_ADDR_RSP responseA4 = null;
            WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_IEEE_ADDR_RSP, zigbeeSerialInterface);
            logger.trace("Sending ZDO_IEEE_ADDR_REQ");
            ZDO_IEEE_ADDR_REQ_SRSP response = (ZDO_IEEE_ADDR_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, new ZDO_IEEE_ADDR_REQ(addresses[k], ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED.getValue(), 0));
            if (response == null || response.Status != 0) {
                logger.trace("ZDO_IEEE_ADDR_REQ failed, received {}", response);
                waiter.cleanup();
            } else {
                responseA4 = (ZDO_IEEE_ADDR_RSP) waiter.getCommand(TIMEOUT);
            }

            if (responseA4 != null) {
                longAddresses[k] = responseA4.getIEEEAddress();
            } else {
                longAddresses[k] = null;
            }
        }
        //---------ZDO MGMT LEAVE
        logger.debug("Reset seq: Trying LEAVE");
        long start = System.currentTimeMillis();
        for (int k = 0; k < longAddresses.length; k++) {
            if (longAddresses[k] != null) {
                WaitForCommand waiter3 = new WaitForCommand(ZToolCMD.ZDO_MGMT_LEAVE_RSP, zigbeeSerialInterface);

                ZDO_MGMT_LEAVE_REQ_SRSP response = (ZDO_MGMT_LEAVE_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, new ZDO_MGMT_LEAVE_REQ(addresses[k], longAddresses[k], 0));
                if (response == null) {
                    logger.error("Leave request time out.");
                    return false;
                }
                if (response.Status != 0) {
                    waiter3.cleanup();
                    logger.error("Leave SRSP error status: " + response.Status);
                    return false;
                }
                ZDO_MGMT_LEAVE_RSP responseA5 = (ZDO_MGMT_LEAVE_RSP) waiter3.getCommand(TIMEOUT);
                if (responseA5 == null) {
                    logger.error("Leave request got no RSP.");
                    return false;
                }
                if (responseA5.Status != 0) {
                    logger.error("Leave request RSP error status: " + responseA5.Status);
                    return false;
                }
            }
            if ((System.currentTimeMillis() - start) > TIMEOUT) {
                logger.error("Reset seq: Failed LEAVE");
                return false;
            }
        }

        return true;
    }


    /**
     * @param request
     */
    private void waitAndLock3WayConversation(ZToolPacket request) {
        synchronized (conversation3Way) {
            Class<?> clz = request.getClass();
            Thread requestor = null;
            while ((requestor = conversation3Way.get(clz)) != null) {
                if (requestor.isAlive() == false) {
                    logger.error("Thread {} whom requested {} DIED before unlocking the conversation");
                    logger.debug("The thread {} who was waiting for {} to complete DIED, so we have to remove the lock");
                    conversation3Way.put(clz, null);
                    break;
                }
                logger.trace(
                        "{} is waiting for {} to complete which was issued by {} to complete",
                        new Object[]{Thread.currentThread(), clz, requestor}
                );
                try {
                    conversation3Way.wait();
                } catch (InterruptedException ex) {
                    //ex.printStackTrace();
                } catch (IllegalMonitorStateException ex) {
                    ex.printStackTrace();
                }
            }
            conversation3Way.put(clz, Thread.currentThread());
        }
    }

    /**
     * Release the lock held for the 3-way communication
     *
     * @param request
     */
    private void unLock3WayConversation(ZToolPacket request) {
        Class<?> clz = request.getClass();
        Thread requestor = null;
        synchronized (conversation3Way) {
            requestor = conversation3Way.get(clz);
            conversation3Way.put(clz, null);
            conversation3Way.notify();
        }
        if (requestor == null) {
            logger.error("LOCKING BROKEN - SOMEONE RELEASE THE LOCK WITHOUT LOCKING IN ADVANCE for {}", clz);
        } else if (requestor != Thread.currentThread()) {
            logger.error(
                    "Thread {} stolen the answer of {} waited by {}",
                    new Object[]{Thread.currentThread(), clz, requestor}
            );
        }
    }

    public ZDO_SIMPLE_DESC_RSP sendZDOSimpleDescriptionRequest(ZDO_SIMPLE_DESC_REQ request) {
        if (waitForNetwork() == false) return null;
        ZDO_SIMPLE_DESC_RSP result = null;
        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_SIMPLE_DESC_RSP, zigbeeSerialInterface);

        ZDO_SIMPLE_DESC_REQ_SRSP response = (ZDO_SIMPLE_DESC_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null || response.Status != 0) {
            waiter.cleanup();
        } else {
            result = (ZDO_SIMPLE_DESC_RSP) waiter.getCommand(TIMEOUT);
        }

        unLock3WayConversation(request);
        return result;
    }

    private class WaitForCommand implements AsynchronousCommandListener {

        final ZToolPacket[] result = new ZToolPacket[]{null};
        final int waitFor;
        final ZigBeeSerialInterface driver;

        public WaitForCommand(int waitFor, ZigBeeSerialInterface driver) {
            this.waitFor = waitFor;
            this.driver = driver;
            logger4Waiter.trace("Waiting for asynchronous response message {}.", waitFor);
            driver.addAsynchronousCommandListener(this);
        }


        public void receivedAsynchronousCommand(ZToolPacket packet) {
            logger4Waiter.trace("Received a packet {} and waiting for {}", packet.getCMD().get16BitValue(), waitFor);
            logger4Waiter.trace("received {} {}", packet.getClass(), packet.toString());
            if (packet.isError()) return;
            if (packet.getCMD().get16BitValue() != waitFor) {
                logger4Waiter.trace("Received unexpected packet: " + packet.getClass().getSimpleName());
                return;
            }
            synchronized (result) {
                result[0] = packet;
                logger4Waiter.trace("Received expected response: {}", packet.getClass().getSimpleName());
                cleanup();
            }
        }

        public ZToolPacket getCommand(final long timeout) {
            synchronized (result) {
                final long wakeUpTime = System.currentTimeMillis() + timeout;
                while (result[0] == null && wakeUpTime > System.currentTimeMillis()) {
                    try {
                        result.wait(wakeUpTime - System.currentTimeMillis());
                    } catch (InterruptedException ignored) {
                    }

                }
            }
            if (result[0] == null) {
                logger4Waiter.trace("Timeout {} expired and no packet with {} received", timeout, waitFor);
            }
            cleanup();
            return result[0];
        }

        public void cleanup() {
            synchronized (result) {
                driver.removeAsynchronousCommandListener(this);
                result.notify();
            }
        }
    }

    private boolean dongleReset() {
        if (waitForHardware() == false) return false;

        final WaitForCommand waiter = new WaitForCommand(
                ZToolCMD.SYS_RESET_RESPONSE,
                zigbeeSerialInterface
        );

        try {
            zigbeeSerialInterface.sendAsynchronousCommand(new SYS_RESET(SYS_RESET.RESET_TYPE.SERIAL_BOOTLOADER));
        } catch (IOException e) {
            logger.error("DongleReset failed", e);
            return false;
        }

        SYS_RESET_RESPONSE response =
                (SYS_RESET_RESPONSE) waiter.getCommand(TIMEOUT);

        return response != null;
    }

    private boolean dongleSetCleanState(boolean clean) {
        ZB_WRITE_CONFIGURATION_RSP response;
        if (clean) {
            response = (ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
                    zigbeeSerialInterface,
                    new ZB_WRITE_CONFIGURATION(
                            ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_STARTUP_OPTION,
                            new int[]{0x00000002}
                    )
            );

            if (response == null || response.Status != 0) {
                logger.warn("Couldn't set ZCD_NV_STARTUP_OPTION to CLEAN_STATE");
                return false;
            } else {
                logger.trace("Set ZCD_NV_STARTUP_OPTION to CLEAN_STATE");
            }
        } else {
            response = (ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
                    zigbeeSerialInterface,
                    new ZB_WRITE_CONFIGURATION(
                            ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_STARTUP_OPTION,
                            new int[]{0x00000000}
                    )
            );

            if (response == null || response.Status != 0) {
                logger.info("Couldn't set ZCD_NV_STARTUP_OPTION back to DO_NOTHING");
                return false;
            }
        }
        return true;
    }

    static final int[] buildChannelMask(int channel) {
        int channelMask = 1 << channel;
        int[] mask = new int[4];
        for (int i = 0; i < mask.length; i++) {
            mask[i] = Integers.getByteAsInteger(channelMask, i);
        }
        return mask;
    }

    private boolean dongleSetChannel(int[] channelMask) {

        logger.trace(
                "Setting the channel to {}{}{}{}", new Object[]{
                Integer.toHexString(channelMask[0]),
                Integer.toHexString(channelMask[1]),
                Integer.toHexString(channelMask[2]),
                Integer.toHexString(channelMask[3])
        }
        );

        ZB_WRITE_CONFIGURATION_RSP response =
                (ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
                        zigbeeSerialInterface,
                        new ZB_WRITE_CONFIGURATION(
                                ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_CHANLIST,
                                channelMask
                        )
                );

        return response != null && response.Status == 0;
    }

    private boolean dongleSetChannel(int ch) {
        int[] channelMask = buildChannelMask(ch);

        return dongleSetChannel(channelMask);
    }

    private boolean dongleSetChannel() {
        int[] channelMask = buildChannelMask(channel);

        return dongleSetChannel(channelMask);
    }

    private boolean dongleSetNetworkMode() {

        ZB_WRITE_CONFIGURATION_RSP response =
                (ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
                        zigbeeSerialInterface,
                        new ZB_WRITE_CONFIGURATION(
                                ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_LOGICAL_TYPE,
                                new int[]{mode.ordinal()}
                        )
                );

        return response != null && response.Status == 0;
    }

    private boolean dongleSetPanId() {
        ZB_WRITE_CONFIGURATION_RSP response =
                (ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
                        zigbeeSerialInterface,
                        new ZB_WRITE_CONFIGURATION(
                                ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_PANID,
                                new int[]{
                                        Integers.getByteAsInteger(pan, 0),
                                        Integers.getByteAsInteger(pan, 1)
                                }
                        )
                );

        return response != null && response.Status == 0;
    }

    private ZToolPacket sendSynchrouns(final ZigBeeSerialInterface hwDriver, final ZToolPacket request) {
        final ZToolPacket[] response = new ZToolPacket[]{null};
//		final int TIMEOUT = 1000, MAX_SEND = 3;
        int sending = 1;

        logger.trace("{} sending as synchronous command.", request.getClass().getSimpleName());

        SynchronousCommandListener listener = new SynchronousCommandListener() {

            public void receivedCommandResponse(ZToolPacket packet) {
                logger.trace(" {} received as synchronous command.", packet.getClass().getSimpleName());
                synchronized (response) {
                    // Do not set response[0] again.
                    response[0] = packet;
                    response.notify();
                }
            }
        };

        while (sending <= RESEND_MAX_RETRY) {
            try {
                try {
                    hwDriver.sendSynchronousCommand(request, listener, RESEND_TIMEOUT);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                logger.trace("{} sent (synchronous command, retry: {}).", request.getClass().getSimpleName(), sending);
                synchronized (response) {
                    long wakeUpTime = System.currentTimeMillis() + RESEND_TIMEOUT;
                    while (response[0] == null && wakeUpTime > System.currentTimeMillis()) {
                        final long sleeping = wakeUpTime - System.currentTimeMillis();
                        logger.trace("Waiting for synchronous command up to {}ms till {} Unixtime", sleeping, wakeUpTime);
                        if (sleeping <= 0) {
                            break;
                        }
                        try {
                            response.wait(sleeping);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
                if (response[0] != null) {
                    logger.trace("{} -> {}",
                            request.getClass().getSimpleName(), response[0].getClass().getSimpleName());
                    break; // Break out as we have response.
                } else {
                    logger.warn("{} executed and timed out while waiting for response.",
                            request.getClass().getSimpleName());
                }
                if (RESEND_ONLY_EXCEPTION) {
                    break;
                } else {
                    logger.info("Failed to send {} during the {}-th tentative", request.getClass().getName(), sending);
                    sending++;
                }
            } catch (Exception ignored) {
                logger.info("Failed to send {} during the {}-th tentative", request.getClass().getName(), sending);
                logger.trace("Sending operation failed due to ", ignored);
                sending++;
            }
        }

        return response[0];
    }

    public boolean addAnnunceListener(AnnounceListener listner) {
        if (announceListeners.isEmpty() && isHardwareReady()) {
            zigbeeSerialInterface.addAsynchronousCommandListener(announceListenerFilter);
        }
        return announceListeners.add(listner);
    }

    public boolean removeAnnunceListener(AnnounceListener listner) {
        boolean result = announceListeners.remove(listner);
        if (announceListeners.isEmpty() && isHardwareReady()) {
            zigbeeSerialInterface.removeAsynchronousCommandListener(announceListenerFilter);
        }
        return result;
    }

    public AF_REGISTER_SRSP sendAFRegister(AF_REGISTER request) {
        if (waitForNetwork() == false) return null;

        AF_REGISTER_SRSP response = (AF_REGISTER_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        return response;
    }

    public AF_DATA_CONFIRM sendAFDataRequest(AF_DATA_REQUEST request) {
        if (waitForNetwork() == false) return null;
        AF_DATA_CONFIRM result = null;

        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.AF_DATA_CONFIRM, zigbeeSerialInterface);

        AF_DATA_SRSP response = (AF_DATA_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null || response.Status != 0) {
            waiter.cleanup();
        } else {
            result = (AF_DATA_CONFIRM) waiter.getCommand(TIMEOUT);
        }
        unLock3WayConversation(request);

        return result;
    }

    public ZDO_BIND_RSP sendZDOBind(ZDO_BIND_REQ request) {
        if (waitForNetwork() == false) return null;
        ZDO_BIND_RSP result = null;

        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_BIND_RSP, zigbeeSerialInterface);

        ZDO_BIND_REQ_SRSP response = (ZDO_BIND_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null || response.Status != 0) {
            waiter.cleanup();
        } else {
            result = (ZDO_BIND_RSP) waiter.getCommand(TIMEOUT);
        }
        unLock3WayConversation(request);
        return result;
    }

    public ZDO_UNBIND_RSP sendZDOUnbind(ZDO_UNBIND_REQ request) {
        if (waitForNetwork() == false) return null;
        ZDO_UNBIND_RSP result = null;

        waitAndLock3WayConversation(request);
        final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_UNBIND_RSP, zigbeeSerialInterface);

        ZDO_UNBIND_REQ_SRSP response = (ZDO_UNBIND_REQ_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
        if (response == null || response.Status != 0) {
            waiter.cleanup();
        } else {
            result = (ZDO_UNBIND_RSP) waiter.getCommand(TIMEOUT);
        }

        unLock3WayConversation(request);
        return result;
    }

    public boolean removeAFMessageListener(ApplicationFrameworkMessageListener listner) {
        boolean result = false;
        synchronized (messageListeners) {
            result = messageListeners.remove(listner);
        }

        if (messageListeners.isEmpty() && isHardwareReady()) {
            if (zigbeeSerialInterface.removeAsynchronousCommandListener(afMessageListenerFilter)) {
                logger.trace("Removed AsynchrounsCommandListener {} to ZigBeeSerialInterface", afMessageListenerFilter.getClass().getName());
            } else {
                logger.warn("Could not remove AsynchrounsCommandListener {} to ZigBeeSerialInterface", afMessageListenerFilter.getClass().getName());
            }
        }
        if (result) {
            logger.trace("Removed ApplicationFrameworkMessageListener {}:{}", listner, listner.getClass().getName());
            return true;
        } else {
            logger.warn("Could not remove ApplicationFrameworkMessageListener {}:{}", listner, listner.getClass().getName());
            return false;
        }
    }

    public boolean addAFMessageListner(ApplicationFrameworkMessageListener listner) {
        if (messageListeners.isEmpty() && isHardwareReady()) {
            if (zigbeeSerialInterface.addAsynchronousCommandListener(afMessageListenerFilter)) {
                logger.trace("Added AsynchrounsCommandListener {} to ZigBeeSerialInterface", afMessageListenerFilter.getClass().getName());
            } else {
                logger.trace("Could not add AsynchrounsCommandListener {} to ZigBeeSerialInterface", afMessageListenerFilter.getClass().getName());
            }
        }
        boolean result = false;
        synchronized (messageListeners) {
            result = messageListeners.add(listner);
        }

        if (result) {
            logger.trace("Added ApplicationFrameworkMessageListener {}:{}", listner, listner.getClass().getName());
            return true;
        } else {
            logger.warn("Could not add ApplicationFrameworkMessageListener {}:{}", listner, listner.getClass().getName());
            return false;
        }
    }

    private boolean isNetworkReady() {
        synchronized (this) {
            return state.ordinal() >= DriverStatus.NETWORK_READY.ordinal()
                    && state.ordinal() < DriverStatus.CLOSED.ordinal();
        }
    }

    private boolean isHardwareReady() {
        synchronized (this) {
            return state.ordinal() >= DriverStatus.HARDWARE_READY.ordinal()
                    && state.ordinal() < DriverStatus.CLOSED.ordinal();
        }
    }

    /**
     * @return
     * @since 0.2.0
     */
    public long getExtendedPanId() {
        if (waitForNetwork() == false) {
            logger.info("Failed to reach the {} level: getExtendedPanId() failed", DriverStatus.NETWORK_READY);
            return -1;
        }

        int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.EXT_PAN_ID);

        if (result == null) {
            return -1;
        } else {
            return Integers.shortFromInts(result, 7, 0);
        }
    }

    /**
     * @return
     * @since 0.2.0
     */
    public long getIEEEAddress() {

        if (ieeeAddress != -1) {
            return ieeeAddress;
        }

        if (waitForNetwork() == false) {
            logger.info("Failed to reach the {} level: getIEEEAddress() failed", DriverStatus.NETWORK_READY);
            return -1;
        }

        int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.IEEE_ADDR);

        if (result == null) {
            return -1;
        } else {
            ieeeAddress = Integers.longFromInts(result, 7, 0);
            return ieeeAddress;
        }
    }

    /**
     * @return
     * @since 0.2.0
     */
    public int getCurrentPanId() {
        if (waitForNetwork() == false) {
            logger.info("Failed to reach the {} level: getCurrentPanId() failed", DriverStatus.NETWORK_READY);
            return -1;
        }

        int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.PAN_ID);

        if (result == null) {
            return -1;
        } else {
            return Integers.shortFromInts(result, 1, 0);
        }
    }

    /**
     * @return
     * @since 0.2.0
     */
    public int getCurrentChannel() {
        if (waitForNetwork() == false) {
            logger.info("Failed to reach the {} level: getCurrentChannel() failed", DriverStatus.NETWORK_READY);
            return -1;
        }

        int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.CHANNEL);

        if (result == null) {
            return -1;
        } else {
            return result[0];
        }
    }

    /**
     * @return
     * @since 0.2.0
     */
    public int getCurrentState() {
        if (waitForNetwork() == false) {
            logger.info("Failed to reach the {} level: getCurrentChannel() failed", DriverStatus.NETWORK_READY);
            return -1;
        }

        int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.STATE);

        if (result == null) {
            return -1;
        } else {
            return result[0];
        }
    }


    private int[] getDeviceInfo(int type) {
        ZB_GET_DEVICE_INFO_RSP response = (ZB_GET_DEVICE_INFO_RSP) sendSynchrouns(
                zigbeeSerialInterface, new ZB_GET_DEVICE_INFO(type)
        );

        if (response == null) {
            logger.warn("Failed getDeviceInfo for {} due to null value", type);
            return null;
        } else if (response.Param != type) {
            logger.warn("Failed getDeviceInfo for {} non matching response returned {}", type, response.Param);
            return null;
        } else {
            logger.trace("getDeviceInfo for {} done", type);
            return response.Value;
        }
    }

    public int getZigBeeNodeMode() {
        ZB_READ_CONFIGURATION_RSP response =
                (ZB_READ_CONFIGURATION_RSP) sendSynchrouns(
                        zigbeeSerialInterface,
                        new ZB_READ_CONFIGURATION(ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_LOGICAL_TYPE)
                );
        if (response != null && response.Status == 0) {
            return response.Value[0];
        } else {
            return -1;
        }
    }

    public DriverStatus getDriverStatus() {
        return state;
    }

    private int[] ep, prof, dev, ver;
    private short[][] inp, out;

    public void addCustomEndpoint(String endpointNumber, String profileID, String deviceID, String version, String inputClusters, String outputCluster) {

        String[] inputGroupsNumber = null, outputGroupsNumber = null, inputClusterGroup = null, outputClusterGroup = null;
        if (checkString(inputClusters))
            inputGroupsNumber = inputClusters.trim().split("]");
        if (checkString(outputCluster))
            outputGroupsNumber = outputCluster.trim().split("]");

        if (inputGroupsNumber != null && outputGroupsNumber != null && inputGroupsNumber.length > 0 && inputGroupsNumber.length == outputGroupsNumber.length) {

            int max = -1;
            for (int i = 0; i < inputGroupsNumber.length; i++) {
                inputGroupsNumber[i] = inputGroupsNumber[i].replace("[", "");
                inputGroupsNumber[i] = inputGroupsNumber[i].replace("]", "");
                inputGroupsNumber[i] = inputGroupsNumber[i].replace(" ", "");

                int temp = inputGroupsNumber[i].trim().split(",").length;
                if (temp > max)
                    max = temp;
            }
            inp = new short[inputGroupsNumber.length][max];
            for (int i = 0; i < inputGroupsNumber.length; i++) {
                inputClusterGroup = inputGroupsNumber[i].trim().split(",");
                for (int j = 0; j < inputClusterGroup.length; j++) {
                    if (toShort(inputClusterGroup[j]) != -1)
                        inp[i][j] = toShort(inputClusterGroup[j]);
                }
            }

            max = -1;
            for (int i = 0; i < outputGroupsNumber.length; i++) {
                outputGroupsNumber[i] = outputGroupsNumber[i].replace("[", "");
                outputGroupsNumber[i] = outputGroupsNumber[i].replace("]", "");
                outputGroupsNumber[i] = outputGroupsNumber[i].replace(" ", "");

                int temp = outputGroupsNumber[i].trim().split(",").length;
                if (temp > max)
                    max = temp;
            }
            out = new short[outputGroupsNumber.length][max];
            for (int i = 0; i < outputGroupsNumber.length; i++) {
                outputClusterGroup = outputGroupsNumber[i].trim().split(",");
                for (int j = 0; j < outputClusterGroup.length; j++) {
                    if (toShort(outputClusterGroup[j]) != -1)
                        out[i][j] = toShort(outputClusterGroup[j]);
                }
            }

            ep = new int[inputGroupsNumber.length];
            prof = new int[inputGroupsNumber.length];
            dev = new int[inputGroupsNumber.length];
            ver = new int[inputGroupsNumber.length];

            String[] eps = endpointNumber.trim().split(",");
            for (int i = 0; i < eps.length; i++) {
                ep[i] = toShort(eps[i]);
            }
            String[] profs = profileID.trim().split(",");
            for (int i = 0; i < profs.length; i++) {
                prof[i] = toShort(profs[i]);
            }
            String[] devs = deviceID.trim().split(",");
            for (int i = 0; i < devs.length; i++) {
                dev[i] = toShort(devs[i]);
            }
            String[] vers = version.trim().split(",");
            for (int i = 0; i < vers.length; i++) {
                ver[i] = toShort(vers[i]);
            }
        }
    }

    private boolean checkString(String s) {
        if (s != null && !s.isEmpty())
            return true;

        return false;
    }

    private Short toShort(String s) {

        try {
            return Short.parseShort(s);
        } catch (Exception ex) {
            return -1;
        }
    }

    public void createCustomDevicesOnDongle() {

        int[] input, output;

        if (this.ep != null)
            for (int i = 0; i < this.ep.length; i++) {
                // input
                int size = 0;
                for (int j = 0; j < this.inp[i].length; j++) {

                    if (this.inp[i][j] != 0 && this.inp[i][j] != -1)
                        size++;
                }

                input = new int[size];
                for (int j = 0; j < this.inp[i].length; j++) {

                    if (this.inp[i][j] != 0 && this.inp[i][j] != -1)
                        input[j] = this.inp[i][j];
                }

                // output
                size = 0;
                for (int j = 0; j < this.out[i].length; j++) {

                    if (this.out[i][j] != 0 && this.out[i][j] != -1)
                        size++;
                }

                output = new int[size];
                for (int j = 0; j < this.out[i].length; j++) {

                    if (this.out[i][j] != 0 && this.out[i][j] != -1)
                        output[j] = this.out[i][j];
                }

                if (newDevice(new AF_REGISTER(new Byte(this.ep[i] + ""), this.prof[i], new Short(this.dev[i] + ""), new Byte(this.ver[i] + ""), input, output)))
                    logger.debug("Custom device {} registered at endpoint {}", this.dev[i], this.ep[i]);
                else
                    logger.debug("Custom device {} registration failed at endpoint {}", this.dev[i], this.ep[i]);
            }
    }

    public boolean newDevice(AF_REGISTER request) {

        try {
            AF_REGISTER_SRSP response = (AF_REGISTER_SRSP) sendSynchrouns(zigbeeSerialInterface, request);
            if (response != null && response.Status == 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    private class AnnounceListenerFilter implements AsynchronousCommandListener {

        private final Collection<AnnounceListener> listners;

        private AnnounceListenerFilter(Collection<AnnounceListener> list) {
            listners = list;
        }

        public void receivedAsynchronousCommand(ZToolPacket packet) {
            if (packet.isError()) return;
            if (packet.getCMD().get16BitValue() == ZToolCMD.ZDO_END_DEVICE_ANNCE_IND) {
                logger.debug("Recieved announce message {} value is {}", packet.getClass(), packet);
                ZDO_END_DEVICE_ANNCE_IND annunce = (ZDO_END_DEVICE_ANNCE_IND) packet;
                for (AnnounceListener l : listners) {
                    l.announce(annunce.SrcAddr, annunce.IEEEAddr, annunce.NwkAddr, annunce.Capabilities);

                }
            } else if (packet.getCMD().get16BitValue() == ZToolCMD.ZDO_STATE_CHANGE_IND) {
                try {
                    ZDO_STATE_CHANGE_IND p = ((ZDO_STATE_CHANGE_IND) packet);
                    switch (p.State) {
                        case 0:
                            logger.trace("Initialized - not started automatically");
                            break;
                        case 1:
                            logger.trace("Initialized - not connected to anything");
                            break;
                        case 2:
                            logger.trace("Discovering PANs to join or waiting for permit join");
                            break;
                        case 3:
                            logger.trace("Joining a PAN");
                            break;
                        case 4:
                            logger.trace("Rejoining a PAN, only for end-devices");
                            break;
                        case 5:
                            logger.trace("Joined but not yet authenticated by trust center");
                            break;
                        case 6:
                            logger.trace("Started as device after authentication");
                            break;
                        case 7:
                            logger.trace("Device joined, authenticated and is a router");
                            break;
                        case 8:
                            logger.trace("Starting as ZigBee Coordinator");
                            break;
                        case 9:
                            logger.debug("Started as ZigBee Coordinator");
                            setState(DriverStatus.NETWORK_READY);
                            break;
                        case 10:
                            logger.debug("Device has lost information about its parent");
                            break;
                        default:
                            break;
                    }
                } catch (Exception ex) {
                    // ignored
                }
            }
        }
    }

    private class AFMessageListenerFilter implements AsynchronousCommandListener {

        private final Collection<ApplicationFrameworkMessageListener> listners;

        private AFMessageListenerFilter(Collection<ApplicationFrameworkMessageListener> list) {
            listners = list;
        }

        public void receivedAsynchronousCommand(ZToolPacket packet) {
            if (packet.isError()) return;
            if (packet.getCMD().get16BitValue() == ZToolCMD.AF_INCOMING_MSG) {
                AF_INCOMING_MSG msg = (AF_INCOMING_MSG) packet;
                if (listners.isEmpty()) {
                    logger.warn("Received AF_INCOMING_MSG but no listeners. Message was: {} ", msg);
                } else {
                    logger.trace("Received AF_INCOMING_MSG notifying {} listeners of {}", listners.size(), msg);
                }
                ArrayList<ApplicationFrameworkMessageListener> localCopy = null;
                synchronized (listners) {
                    localCopy = new ArrayList<ApplicationFrameworkMessageListener>(listners);
                }
                for (ApplicationFrameworkMessageListener l : localCopy) {
                    l.notify(msg);
                }
            }
        }
    }

}
