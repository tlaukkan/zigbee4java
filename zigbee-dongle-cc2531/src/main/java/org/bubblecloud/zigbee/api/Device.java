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

package org.bubblecloud.zigbee.api;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.general.Alarms;
import org.bubblecloud.zigbee.api.cluster.general.Basic;
import org.bubblecloud.zigbee.api.cluster.general.DeviceTemperatureConfiguration;
import org.bubblecloud.zigbee.api.cluster.general.Identify;
import org.bubblecloud.zigbee.api.cluster.general.PowerConfiguration;
import org.bubblecloud.zigbee.util.ArraysUtil;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface Device extends ZigBeeEndpoint {

    public static final String ZIGBEE_DEVICE_SERVICE = "zigbee.service.id";
    public static final String ZIGBEE_DEVICE_UUID = "zigbee.device.uuid";
    public static final String HA_DRIVER = "zigbee.ha.driver.name";
    public static final String HA_DEVICE_NAME = "zigbee.ha.name";
    public static final String HA_DEVICE_GROUP = "zigbee.ha.group";
    public static final String HA_DEVICE_STANDARD = "zigbee.ha.standard";

    public static final int[] MANDATORY = {ZigBeeApiConstants.CLUSTER_ID_BASIC, ZigBeeApiConstants.CLUSTER_ID_IDENTIFY};
    public static final int[] OPTIONAL = {ZigBeeApiConstants.CLUSTER_ID_POWER_CONFIGURATION, ZigBeeApiConstants.CLUSTER_ID_DEVICE_TEMPERATURE_CONFIGURATION, ZigBeeApiConstants.CLUSTER_ID_ALARMS};
    public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);

    public String getDeviceType();

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
     * <b>NOTE</b>: The {@link DeviceDescription} <b>should</b> be shared among all {@link DeviceBase}<br>
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
     * @param clusterIntercace the cluster interface
     * @return the {@link Cluster} identified by the given id if this device implements<br>
     *         otherwise <code>null</code>
     * @since 0.2.0
     */
    public <T extends Cluster> T getCluster(Class<T> clusterIntercace);

    /**
     * @return the {@link Cluster} array effectively implemented by the remote ZigBee Device<br>
     *         otherwise <code>null</code>
     */
    public Cluster[] getAvailableCluster();


    /**
     * Gets the endpoint associated with the device
     * @return return {@link org.bubblecloud.zigbee.network.ZigBeeEndpoint} service that has been refined has {@link Device}
     * @since 0.2.0
     */
    public ZigBeeEndpoint getEndpoint();

    /**
     * This method modifies the <i>Binding Table</i> of physical device by adding the following entry:
     * <pre>
     * this.getNode().getIeeeAddress(), this.getDeviceTypeId(), clusterId, device.getNode().getIeeeAddress(), device.getDeviceTypeId()
     * </pre>
     *
     * @param device {@link org.bubblecloud.zigbee.network.ZigBeeEndpoint} the device proxy that we want to bound to
     * @param clusterId   the clusterId that we want to bound to
     * @return <code>true</code> if and only if the operation succeeded
     * @throws org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException
     *
     * @since 0.5.0
     */
    public boolean bindTo(Device device, int clusterId) throws ZigBeeNetworkManagerException;

    /**
     * This method modifies the <i>Binding Table</i> of physical device by removing the entry if exists
     * <pre>
     * this.getNode().getIeeeAddress(), this.getDeviceTypeId(), clusterId, device.getNode().getIeeeAddress(), device.getDeviceTypeId()
     * </pre>
     *
     * @param device {@link org.bubblecloud.zigbee.network.ZigBeeEndpoint} the device that we want to bound to
     * @param clusterId   the clusterId that we want to unbound from
     * @return <code>true</code> if and only if the operation succeeded
     * @throws org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException
     * @since 0.5.0
     */
    public boolean unbindFrom(Device device, int clusterId) throws ZigBeeNetworkManagerException;
}
