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

package org.bubblecloud.zigbee.proxy;

import org.bubblecloud.zigbee.network.ZigBeeDevice;
import org.bubblecloud.zigbee.network.impl.ZigBeeBasedriverException;
import org.bubblecloud.zigbee.proxy.cluster.glue.Cluster;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Alarms;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Basic;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.DeviceTemperatureConfiguration;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Identify;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.PowerConfiguration;
import org.bubblecloud.zigbee.util.ArraysUtil;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface DeviceProxy extends ZigBeeDevice {

    public static final String ZIGBEE_DEVICE_SERVICE = "zigbee.service.id";
    public static final String ZIGBEE_DEVICE_UUID = "zigbee.device.uuid";
    public static final String HA_DRIVER = "zigbee.ha.driver.name";
    public static final String HA_DEVICE_NAME = "zigbee.ha.name";
    public static final String HA_DEVICE_GROUP = "zigbee.ha.group";
    public static final String HA_DEVICE_STANDARD = "zigbee.ha.standard";

    public static final int[] MANDATORY = {ProxyConstants.BASIC, ProxyConstants.IDENTIFY};
    public static final int[] OPTIONAL = {ProxyConstants.POWER_CONFIGURATION, ProxyConstants.DEVICE_TEMPERATURE_CONFIGURATION, ProxyConstants.ALARMS};
    public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);

    public String getName();

    /**
     * @return an <code>int</code> representing the <b>DeviceId</b> as defined by the<br>
     *         <i>ZigBee Home Automation</i> profile documentation
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
     * <p/>
     * <b>NOTE</b>: The {@link DeviceDescription} <b>should</b> be shared among all {@link DeviceProxyBase}<br>
     * sharing the same <b>DeviceId</b>
     *
     * @return the {@link DeviceDescription}
     */
    public DeviceDescription getDescription();

    /**
     * @param id the <code>int</code> representing the <b>ClusterId</b>
     * @return the {@link Cluster} identified by the given id if this device implements<br>
     *         otherwise <code>null</code>
     * @since 0.2.0
     */
    public <T extends Cluster> T getCluster(int id);

    /**
     * @return the {@link Cluster} array effectively implemented by the remote ZigBee Device<br>
     *         otherwise <code>null</code>
     */
    public Cluster[] getAvailableCluster();


    /**
     * @return return {@link ZigBeeDevice} service that has been refined has {@link DeviceProxy}
     * @since 0.2.0
     */
    public ZigBeeDevice getDevice();

    /**
     * This method modify the <i>Binding Table</i> of physical device by adding the following entry:
     * <pre>
     * this.getPhysicalNode().getIEEEAddress(), this.getDeviceId(), clusterId, device.getPhysicalNode().getIEEEAddress(), device.getDeviceId()
     * </pre>
     *
     * @param deviceProxy {@link ZigBeeDevice} the device proxy that we want to bound to
     * @param clusterId   the clusterId that we want to bound to
     * @return <code>true</code> if and only if the operation succeeded
     * @throws org.bubblecloud.zigbee.network.impl.ZigBeeBasedriverException
     *
     * @since 0.5.0
     */
    public boolean bindTo(DeviceProxy deviceProxy, int clusterId) throws ZigBeeBasedriverException;

    /**
     * This method modify the <i>Binding Table</i> of physical device by removing the entry if exists
     * <pre>
     * this.getPhysicalNode().getIEEEAddress(), this.getDeviceId(), clusterId, device.getPhysicalNode().getIEEEAddress(), device.getDeviceId()
     * </pre>
     *
     * @param deviceProxy {@link ZigBeeDevice} the deviceProxy that we want to bound to
     * @param clusterId   the clusterId that we want to unbound from
     * @return <code>true</code> if and only if the operation succeeded
     * @throws ZigBeeBasedriverException
     * @since 0.5.0
     */
    public boolean unbindFrom(DeviceProxy deviceProxy, int clusterId) throws ZigBeeBasedriverException;
}
