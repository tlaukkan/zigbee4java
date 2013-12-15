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

package it.cnr.isti.zigbee.ha.driver.core;

import org.bubblecloud.zigbee.core.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Alarms;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Basic;
import it.cnr.isti.zigbee.ha.cluster.glue.general.DeviceTemperatureConfiguration;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Identify;
import it.cnr.isti.zigbee.ha.cluster.glue.general.PowerConfiguration;
import org.bubblecloud.zigbee.util.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 *
 */
public interface HADevice {
	
	public static final String ZIGBEE_DEVICE_SERVICE = "zigbee.service.id";
	public static final String ZIGBEE_DEVICE_UUID = "zigbee.device.uuid";
	public static final String HA_DRIVER = "zigbee.ha.driver.name";
	public static final String HA_DEVICE_NAME = "zigbee.ha.name";
	public static final String HA_DEVICE_GROUP = "zigbee.ha.group";
	public static final String HA_DEVICE_STANDARD = "zigbee.ha.standard";
	
	public static final int[] MANDATORY = {HAProfile.BASIC,HAProfile.IDENTIFY};
	public static final int[] OPTIONAL = {HAProfile.POWER_CONFIGURATION, HAProfile.DEVICE_TEMPERATURE_CONFIGURATION, HAProfile.ALARMS};
	public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
	
	public String getName();	
	/**
	 * 
	 * @return an <code>int</code> representing the <b>DeviceId</b> as defined by the<br> 
	 * 		<i>ZigBee Home Automation</i> profile documentation
	 */
	public int getDeviceType();
	public int getEndPointId();
	public int getProfileId();
	
	/**
	 * Access method for the <b>Mandatory</b> cluster: {@link Basic} 
	 *  
	 * @return the {@link Basic} cluster object
	 */
	public Basic getBasic();
	
	/**
	 * Access method for the <b>Mandatory</b> cluster: {@link Basic} 
	 *  
	 * @return the {@link Basic} cluster object
	 */	
	public Identify getIdentify();

	/**
	 * Access method for the <b>Optional</b> cluster: {@link PowerConfiguration} 
	 *  
	 * @return the {@link PowerConfiguration} cluster object
	 * @since 0.4.0
	 */
	public PowerConfiguration getPowerConfiguration();

	/**
	 * Access method for the <b>Optional</b> cluster: {@link DeviceTemperatureConfiguration} 
	 *  
	 * @return the {@link DeviceTemperatureConfiguration} cluster object
	 * @since 0.4.0
	 */
	public DeviceTemperatureConfiguration getDeviceTemperatureConfiguration();

	/**
	 * Access method for the <b>Optional</b> cluster: {@link Alarms} 
	 *  
	 * @return the {@link Alarms} cluster object
	 * @since 0.4.0
	 */
	public Alarms getAlarms();	
	
	/**
	 * Access method to the {@link DeviceDescription}.<br>
	 * 
	 * <b>NOTE</b>: The {@link DeviceDescription} <b>should</b> be shared among all {@link HADeviceBase}<br>
	 * sharing the same <b>DeviceId</b>
	 * 
	 * @return the {@link DeviceDescription}
	 */	
	public DeviceDescription getDescription();
	/**
	 * 
	 * @param id the <code>int</code> representing the <b>ClusterId</b> 
	 * @return the {@link Cluster} identified by the given id if this device implements<br>
	 * 		otherwise <code>null</code>
	 * @since 0.2.0
	 */
	public Cluster getCluster(int id);
	
	/**
	 * 
	 * @return the {@link Cluster} array effectively implemented by the remote ZigBee Device<br>
	 * 		otherwise <code>null</code>
	 */
	public  Cluster[] getAvailableCluster();

	
	/**
	 * 
	 * @return return {@link ZigBeeDevice} service that has been refined has {@link HADevice} 
	 * @since 0.2.0
	 * @deprecated The method should be avoided because it cause Service Reference Leak
	 */
	public ZigBeeDevice getZBDevice(); 
}
