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


import org.bubblecloud.zigbee.network.model.NetworkMode;

/**
 * 
 * This class contains all the system property that <b>SHOULD</b> affect the behavior of a {@link ZigbeeNetworkManagementInterface}
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi - ISTI-CNR</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco - ISTI-CNR</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 *
 */
public interface ConfigurationProperties {

	/**
	 * The default value for the {@link #NETWORK_MODE_KEY} property, that is <b>Coordinator</b>
	 */
	public final static String NETWORK_MODE = NetworkMode.Coordinator.toString();
	/**
	 * Key to use to change the <code>Network Mode</code>, accepted values are:<br>
	 *  - "<b>Coordinator</b>": the driver try to create a ZigBee network and will be the coordinator<br>
	 *  - "<b>Router</b>": the driver try to join any ZigBee as Router to an existing network<br>
	 *  - "<b>EndDevice</b>" : the driver to join to a ZigBee as End-Device to an existing network<br> 
	 */
	public final static String NETWORK_MODE_KEY = "it.cnr.isti.zigbee.driver.mode";
	
	
    /**
     * The default value for the {@link #PAN_ID_KEY} property, that is <b>0x1357</b>
     */
	public final static int PAN_ID = 0x1357;

	/**
     * Key to use to control the <b>Network Pan Id</b> to connect to or to create by the driver 
     */
	public final static String PAN_ID_KEY = "it.cnr.isti.zigbee.pan.id";
	
	
    /**
     * The default value for the {@link #CHANNEL_ID_KEY} property, that is <b>23</b>
     */
	public final static byte CHANNEL_ID = 23;

	/**
     * Key to use to control the <b>Network Channel</b> to connect to or to create by the driver 
     */
	public final static String CHANNEL_ID_KEY = "it.cnr.isti.zigbee.pan.channel";

	
    /**
     * The default value for the {@link #COM_NAME_KEY} property, that is <b>auto</b>
     */
	public final static String COM_NAME = "auto";

    /**
     * Key to use to control the <b>Serial Port</b> used by the driver for communicating with the dongle.<br>
     * The value <b>MUST</b> represent the <i>Serial Port</i> name or <i>auto</i> for enabling the auto discovery
     */
	public final static String COM_NAME_KEY = "it.cnr.isti.zigbee.driver.serial.portname";

	
    /**
     * The default value for the {@link #COM_BOUDRATE_KEY} property, that is <b>38400</b>
     */
	public final static int COM_BOUDRATE = 38400;

    /**
     * Key to use to control the <b>Boud Rate</b> used by the driver for communicating with the dongle
     */
	public final static String COM_BOUDRATE_KEY = "it.cnr.isti.zigbee.driver.serial.boudrate";

	
    /**
     * The default value for the {@link #NETWORK_FLUSH_KEY} property, that is <b>false</b>
     */
	public final static boolean NETWORK_FLUSH = false;

	/**
     * Key to use to control if we have to reset the status of the dongle. We it is set to <b>true</b> it reset the dongle<br>
     * regardless of the current setting of the used and the old setting used by the dongle<br>
     * <br>
     * <b>NOTE</b>:Resetting the status usually means creation of a new ZigBee network
     */
	public final static String NETWORK_FLUSH_KEY = "it.cnr.isti.zigbee.driver.flush";

	    
    /**
     * The default value for the {@link #APPLICATION_MSG_RETRY_COUNT_KEY} property, that is <b>3</b>
     */
	public final static int APPLICATION_MSG_RETRY_COUNT = 3;

	/**
     * Key to use to control the how many times the driver should retransmit an application message before giving up
     */
	public final static String APPLICATION_MSG_RETRY_COUNT_KEY = "it.cnr.isti.zigbee.driver.communication.retry.count";
	
    
    /**
     * The default value for the {@link #APPLICATION_MSG_RETRY_DELAY_KEY} property, that is <b>1000ms</b>
     */
	public final static int APPLICATION_MSG_RETRY_DELAY = 1000;

    /**
     * Key to use to control the how much time to wait between the automatic retransmission
     */
	public final static String APPLICATION_MSG_RETRY_DELAY_KEY = "it.cnr.isti.zigbee.driver.communication.retry.delay";

    
    /**
     * The default value for the {@link #APPLICATION_MSG_TIMEOUT_KEY} property, that is <b>2500ms</b>
     */
	public final static int APPLICATION_MSG_TIMEOUT = 2500;

    /**
     * Key to use to control the how much time to wait for waiting an response to a request,<br>
     * when timeout is fired it is considered as a failed communication even if the message arrives later on
     */
	public final static String APPLICATION_MSG_TIMEOUT_KEY = "it.cnr.isti.zigbee.driver.communication.timeout";
	

    /**
     * The default value for the {@link ConfigurationProperties#AUTOMATIC_ENDPOINT_ADDRESS_RETRY_KEY} property, that is <b>0</b>.
     * @since 0.6.0 - Revision 86
     */
    public final static int AUTOMATIC_ENDPOINT_ADDRESS_RETRY = 0;
    /**
     *  The maximum number of retry that the ZigBee Base Driver should before giving up the<br>
     *  registration of a local device when the End Point address chosen was not actually free.<br>
     *  <br>
     *  <b>NOTE:</b><i>The maximum theoretical value is 240 so any value greater than 240 would<br>
     *  not change the result</i>
     *
     *  @since 0.6.0 - Revision 86
     */
	public static final String AUTOMATIC_ENDPOINT_ADDRESS_RETRY_KEY = "it.cnr.isti.zigbee.driver.communication.ep.address.retry";


    /**
     * The default value for the {@link ConfigurationProperties#FIRST_ENDPOINT_ADDRESS_KEY} property, that is <b>2</b>.
     * @since 0.6.0 - Revision 86
     */
    public final static int FIRST_ENDPOINT_ADDRESS = 2;
    /**
     *  The first end point address that the ZigBee Base Driver should use for registering local device<br>
     *  @since 0.6.0 - Revision 86
     */
	public static final String FIRST_ENDPOINT_ADDRESS_KEY  = "it.cnr.isti.zigbee.driver.communication.ep.address.first";


	/**
	 * The default value for the {@link ConfigurationProperties#NETWORK_BROWSING_PERIOD_KEY} property, that is <b>15 minutes</b>.
	 */
    public final static int NETWORK_BROWSING_PERIOD = 15 * 60 * 1000;
    /**
     *  The minimum number of milliseconds that must elapse between that two execution of the ZigBee network<br>
     *  address-tree browsing, for discovering devices.
     */
    public final static String NETWORK_BROWSING_PERIOD_KEY = "it.cnr.isti.zigbee.driver.network.browsing.period";


    /**
     * The default value for the {@link ConfigurationProperties#DEVICE_INSPECTION_PERIOD_KEY} property, that is <b>10 seconds</b>.
     */
    public final static int DEVICE_INSPECTION_PERIOD = 10 * 1000;
    /**
     *  The minimum number of milliseconds that must elapse between that two device are inspected by the<br> 
     *  ZigBee Base Driver. It is useful to reduce the network saturation during the first boot of the driver.  
     */
    public final static String DEVICE_INSPECTION_PERIOD_KEY = "it.cnr.isti.zigbee.driver.device.inspection.rate";
}
