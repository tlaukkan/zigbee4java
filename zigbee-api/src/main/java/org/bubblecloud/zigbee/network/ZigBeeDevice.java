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

import org.bubblecloud.zigbee.network.impl.ZigBeeBasedriverException;

import java.util.Dictionary;


/**
 * This class represent a ZigBee End Point
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface ZigBeeDevice {

    /**
     * Key of the {@link String} profile id implemented by the device.
     * <br>It is <b>mandatory</b> property for this service
     */
    public final String PROFILE_ID = "zigbee.device.profile.id";

    /**
     * Key of the {@link String} profile implemented by the device
     * <br>It is <b>optional</b> property for this service
     */
    public final String PROFILE_NAME = "zigbee.device.profile.name";

    /**
     * Key of the {@link String} containing the DeviceId of the device
     * <br>It is <b>mandatory</b> property for this service
     */
    public final String DEVICE_ID = "zigbee.device.device.id";

    /**
     * Key of the {@link String} containing the DeviceVersion of the device
     * <br>It is <b>mandatory</b> property for this service
     */
    public final String DEVICE_VERSION = "zigbee.device.device.version";

    /**
     * Key of the {@link String} containing the name of the device
     * <br>It is <b>optional</b> property for this service
     */
    public final String DEVICE_NAME = "zigbee.device.device.name";

    /**
     * Key of the int array of containing the ids of each input cluster
     * <br>It is <b>mandatory</b> property for this service
     */
    public final String CLUSTERS_INPUT_ID = "zigbee.device.clusters.input.id";

    /**
     * Key of the {@link String} array of containing the names of input cluster
     * <br>It is <b>optional</b> property for this service
     */
    public final String CLUSTERS_INPUT_NAME = "zigbee.device.clusters.input.name";

    /**
     * Key of the int array of containing the ids of each output cluster
     * <br>It is <b>mandatory</b> property for this service
     */
    public final String CLUSTERS_OUTPUT_ID = "zigbee.device.clusters.output.id";

    /**
     * Key of the {@link String} array of containing the names of output cluster
     * <br>It is <b>optional</b> property for this service
     */
    public final String CLUSTERS_OUTPUT_NAME = "zigbee.device.clusters.output.name";

    /**
     * Key of the {@link String} containing the EndPoint Address of the device
     * <br>It is <b>mandatory</b> property for this service
     */
    public final String ENDPOINT = "zigbee.device.endpoint";

    /**
     * Key of the {@link String} containing an unique identifier that represent an unique identifier
     * for the device composed by: <br>
     * {@link #PROFILE_ID}, {@link #DEVICE_ID}, {@link #DEVICE_VERSION},
     * {@link ZigBeeNode#IEEE_ADDRESS}, and {@link #ENDPOINT}
     * Joined using the following format <pre>profile_id:device_id:device_version@ieee_address:endpoint</pre><br>
     * It is used also as {@link Constants#DEVICE_SERIAL}
     * <br>It is <b>mandatory</b> property for this service
     */
    public final String UUID = "zigbee.device.uuid";

    /**
     * Constant for the value of the service property {@link Constants#DEVICE_CATEGORY} used for
     * all ZigBee devices.
     * <br>It is <b>mandatory</b> property for this service
     */
    public final String DEVICE_CATEGORY = "ZigBee";

    /**
     * This property identify which <code>ZigbeeNetworkManager</code> has been used for discovering
     * the ZigBee device
     */
    public final String ZIGBEE_IMPORT = "zigbee.import.id";

    /**
     * Constant for the ZigBee device match scale, indicating a match with the ProfileId. Value is 1024.
     */
    public static final int MATCH_PROFILE_ID = 1024;

    /**
     * Constant for the ZigBee device match scale to add when a device match with the ProfileId
     * for each of the cluster matching. Value is 1.
     */
    public static final int MATCH_CLUSTER_ID = 1;

    /**
     * Constant for the ZigBee device match scale, indicating a match with the ProfileId
     * and the DeviceId. Value is 4096.
     */
    public static final int MATCH_DEVICE_ID = 4096;


    /**
     * @return Address of the EndPoint represented by this object, value ranges from 1 to 240.
     */
    public short getEndPoint();

    /**
     * @return the profile Id implemented by this EndPoint
     */
    public int getProfileId();

    /**
     * @return the device id implemented by this EndPoint
     */
    public int getDeviceId();

    /**
     * @return the {@link ZigBeeNode} containing this EndPoint
     * @since 0.2.0
     */
    public ZigBeeNode getPhysicalNode();

    /**
     * @return a {@link String} the represent an URI of device. The same as property {@link #UUID}
     * @since 0.5.0
     */
    public String getUniqueIdenfier();

    /**
     * @return the device version implemented by this EndPoint
     */
    public short getDeviceVersion();

    /**
     * @return the list of Input Cluster identifies implemented by this EndPoint
     */
    public int[] getInputClusters();

    /**
     * @param id the Cluster identifier
     * @return true if and only if the EnpPoint implements the given Cluster id<br>
     *         as Input Cluster
     */
    public boolean providesInputCluster(int id);

    /**
     * @return the list of Output Cluster identifies implemented by this EndPoint
     */
    public int[] getOutputClusters();

    /**
     * @param id the Cluster identifier
     * @return true if and only if the EnpPoint implements the given Cluster id<br>
     *         as Output Cluster
     */
    public boolean providesOutputCluster(int id);

    /**
     * Invoke the given {@link ClusterMessage} (which is supposed to part of the Input Cluster) of this EndPoint,<br>
     * and wait for the a response from.
     *
     * @param input the {@link ClusterMessage} containing the id of the Cluster to invoke and the cluster message
     * @return the {@link ClusterMessage} representing the response received after that the Cluster answer to<br>
     *         the request.
     */
    public ClusterMessage invoke(ClusterMessage input) throws ZigBeeBasedriverException;

    /**
     * Send the given {@link ClusterMessage} (which is supposed to be Input Cluster) to this EndPoint,<br>
     * on the contrary than the {@link #invoke(ClusterMessage)} this method doesn't wait for nor requires<br>
     * an answer from the EndPoint-
     *
     * @param input the {@link ClusterMessage} containing the id of the Cluster to send to the cluster message
     */
    public void send(ClusterMessage input) throws ZigBeeBasedriverException;

    /**
     * This method modify the <i>Binding Table</i> of physical device by adding the following entry:
     * <pre>
     * this.getPhysicalNode().getIEEEAddress(), this.getDeviceId(), clusterId, device.getPhysicalNode().getIEEEAddress(), device.getDeviceId()
     * </pre>
     *
     * @param device    {@link ZigBeeDevice} the device that we want to bound to
     * @param clusterId the clusterId that we want to bound to
     * @return <code>true</code> if and only if the operation succeeded
     * @throws ZigBeeBasedriverException
     * @since 0.5.0
     */
    public boolean bindTo(ZigBeeDevice device, int clusterId) throws ZigBeeBasedriverException;

    /**
     * This method modify the <i>Binding Table</i> of physical device by removing the entry if exists
     * <pre>
     * this.getPhysicalNode().getIEEEAddress(), this.getDeviceId(), clusterId, device.getPhysicalNode().getIEEEAddress(), device.getDeviceId()
     * </pre>
     *
     * @param device    {@link ZigBeeDevice} the device that we want to bound to
     * @param clusterId the clusterId that we want to unbound from
     * @return <code>true</code> if and only if the operation succeeded
     * @throws ZigBeeBasedriverException
     * @since 0.5.0
     */
    public boolean unbindFrom(ZigBeeDevice device, int clusterId) throws ZigBeeBasedriverException;

    /**
     * @param clusterId
     * @return
     * @throws ZigBeeBasedriverException
     */
    public boolean bind(int clusterId) throws ZigBeeBasedriverException;

    /**
     * @param clusterId
     * @return
     * @throws ZigBeeBasedriverException
     */
    public boolean unbind(int clusterId) throws ZigBeeBasedriverException;

    /**
     * Add a {@link ClusterListener} to this EndPoint. The same instance of a {@link ClusterListener} will<br>
     * registered only once.
     *
     * @param listener the {@link ClusterListener} to register
     * @return true if and only if the {@link ClusterListener} has been registered
     */
    public boolean addClusterListener(ClusterListener listener);

    /**
     * Remove a {@link ClusterListener} to this EndPoint.
     *
     * @param listener the {@link ClusterListener} to unregister
     * @return true if and only if the {@link ClusterListener} has been unregistered
     */
    public boolean removeClusterListener(ClusterListener listener);

    /**
     * This method returns a {@link Dictionary} object which contains at least<br>
     * all the information used for registering the service on the OSGi Framework
     *
     * @return {@link Dictionary} with the service registration information
     * @since 0.3.0
     */
    //public Dictionary getDescription();
}

