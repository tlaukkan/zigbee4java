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

package org.bubblecloud.zigbee.network;

import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.network.packet.af.AF_DATA_CONFIRM;
import org.bubblecloud.zigbee.network.packet.af.AF_DATA_REQUEST;
import org.bubblecloud.zigbee.network.packet.af.AF_REGISTER;
import org.bubblecloud.zigbee.network.packet.af.AF_REGISTER_SRSP;
import org.bubblecloud.zigbee.network.packet.zdo.*;

import java.util.HashSet;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi - ISTI-CNR</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco - ISTI-CNR</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface ZigBeeNetworkManager {

    public abstract void setZigBeeNodeMode(NetworkMode m);

    public abstract void setZigBeeNetwork(byte ch, short panId);

    public abstract void setSerialPort(String serialName, int bitRate);

    public abstract void startup();

    public abstract void shutdown();

    public abstract ZDO_IEEE_ADDR_RSP sendZDOIEEEAddressRequest(ZDO_IEEE_ADDR_REQ request);

    public abstract ZDO_NODE_DESC_RSP sendZDONodeDescriptionRequest(ZDO_NODE_DESC_REQ request);

    public abstract ZDO_ACTIVE_EP_RSP sendZDOActiveEndPointRequest(ZDO_ACTIVE_EP_REQ request);

    public abstract ZDO_SIMPLE_DESC_RSP sendZDOSimpleDescriptionRequest(ZDO_SIMPLE_DESC_REQ request);

    public abstract boolean addAnnunceListener(AnnounceListener listner);

    public abstract boolean removeAnnunceListener(AnnounceListener listner);

    public abstract AF_REGISTER_SRSP sendAFRegister(AF_REGISTER request);

    public abstract AF_DATA_CONFIRM sendAFDataRequest(AF_DATA_REQUEST request);

    public abstract ZDO_BIND_RSP sendZDOBind(ZDO_BIND_REQ request);

    public abstract ZDO_UNBIND_RSP sendZDOUnbind(ZDO_UNBIND_REQ request);

    public abstract boolean removeAFMessageListener(ApplicationFrameworkMessageListener listner);

    public abstract boolean addAFMessageListner(ApplicationFrameworkMessageListener listner);

    /**
     * Send LQI request cluster and wait for its response<br />
     * This method is used for the discovering of {@link ZigBeeEndpoint}
     *
     * @return the answer to the request or null in case of an error
     * @since 0.7.0
     */
    public abstract ZDO_MGMT_LQI_RSP sendLQIRequest(ZDO_MGMT_LQI_REQ request);

    /**
     * This method is used for the creation of an virtual device on the dongle<br />
     * Note: a proper {@link addAFMessageListner} has to be register for answering to request coming from the network
     *
     * @since 0.7.0
     * @deprecated
     */
    public abstract void addCustomEndpoint(String endpointNumber, String profileID, String deviceID, String version, String inputClusters, String outputCluster);

    /**
     * <b>WARNING</b>: This method may have to wait for the initialization of the ZigBee network<br>
     * thus, it may be quite slow or end up in a deadlock of the application
     *
     * @return The long representing the IEEE Address of coordinator of the ZigBee network in use, or -1 if and only if the method failed
     * @since 0.2.0
     */
    public abstract long getExtendedPanId();

    /**
     * <b>WARNING</b>: This method may have to wait for the initialization of the ZigBee network<br>
     * thus, it may be quite slow or end up in a deadlock of the application
     *
     * @return The long representing the IEEE Address of ZigBee device in use, or -1 if and only if the method failed
     * @since 0.2.0
     */
    public abstract long getIEEEAddress();

    /**
     * <b>WARNING</b>: This method may have to wait for the initialization of the ZigBee network<br>
     * thus, it may be quite slow or end up in a deadlock of the application
     *
     * @return The panId of ZigBee network configured in use, or -1 if and only if the method failed
     * @since 0.2.0
     */
    public abstract int getCurrentPanId();

    /**
     * <b>WARNING</b>: This method may have to wait for the initialization of the ZigBee network<br>
     * thus, it may be quite slow or end up in a deadlock of the application
     *
     * @return The ZigBee network channel in use, or -1 if and only if the method failed
     * @since 0.2.0
     */
    public abstract int getCurrentChannel();

    /**
     * <b>WARNING</b>: This method may have to wait for the initialization of the ZigBee network<br>
     * thus, it may be quite slow or end up in a deadlock of the application
     *
     * @return The ZStack state of the device, or -1 if and only if the method failed
     * @since 0.2.0
     * @deprecated
     */
    public abstract int getCurrentState();

    /**
     * <b>WARNING</b>: This method may have to wait for the initialization of the ZigBee network<br>
     * thus, it may be quite slow or end up in a deadlock of the application
     *
     * @return The int representation of the {@link NetworkMode} in use, or -1 if and only if the method failed
     * @since 0.6.0
     */
    public abstract int getZigBeeNodeMode();

    /**
     * @return The current status of the driver
     * @see 0.6.0
     */
    public abstract DriverStatus getDriverStatus();

    HashSet<AnnounceListener> getAnnounceListeners();
}