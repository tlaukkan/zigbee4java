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

import org.bubblecloud.zigbee.network.impl.ZigbeeBasedriverException;


/**
 * This class represent a Zigbee Endpoint.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface ZigbeeEndpoint {

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
    public short getEndPointAddress();

    /**
     * @return the profile Id implemented by this EndPoint
     */
    public int getProfileId();

    /**
     * @return the device id implemented by this EndPoint
     */
    public int getDeviceId();

    /**
     * @return the {@link ZigbeeNode} containing this endpoint
     * @since 0.2.0
     */
    public ZigbeeNode getNode();

    /**
     * @return a {@link String} the represent an URI of endpoint.
     * @since 0.5.0
     */
    public String getEndpointId();

    /**
     * @return the device version implemented by this endpoint
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
    public ClusterMessage invoke(ClusterMessage input) throws ZigbeeBasedriverException;

    /**
     * Send the given {@link ClusterMessage} (which is supposed to be Input Cluster) to this EndPoint,<br>
     * on the contrary than the {@link #invoke(ClusterMessage)} this method doesn't wait for nor requires<br>
     * an answer from the EndPoint-
     *
     * @param input the {@link ClusterMessage} containing the id of the Cluster to send to the cluster message
     */
    public void send(ClusterMessage input) throws ZigbeeBasedriverException;

    /**
     * This method modify the <i>Binding Table</i> of physical endpoint by adding the following entry:
     * <pre>
     * this.getNode().getIEEEAddress(), this.getDeviceId(), clusterId, endpoint.getNode().getIEEEAddress(), endpoint.getDeviceId()
     * </pre>
     *
     * @param endpoint    {@link ZigbeeEndpoint} the endpoint that we want to bound to
     * @param clusterId the clusterId that we want to bound to
     * @return <code>true</code> if and only if the operation succeeded
     * @throws org.bubblecloud.zigbee.network.impl.ZigbeeBasedriverException
     * @since 0.5.0
     */
    public boolean bindTo(ZigbeeEndpoint endpoint, int clusterId) throws ZigbeeBasedriverException;

    /**
     * This method modify the <i>Binding Table</i> of physical device by removing the entry if exists
     * <pre>
     * this.getNode().getIEEEAddress(), this.getDeviceId(), clusterId, device.getNode().getIEEEAddress(), device.getDeviceId()
     * </pre>
     *
     * @param endpoint    {@link ZigbeeEndpoint} the device that we want to bound to
     * @param clusterId the clusterId that we want to unbound from
     * @return <code>true</code> if and only if the operation succeeded
     * @throws org.bubblecloud.zigbee.network.impl.ZigbeeBasedriverException
     * @since 0.5.0
     */
    public boolean unbindFrom(ZigbeeEndpoint endpoint, int clusterId) throws ZigbeeBasedriverException;

    /**
     * @param clusterId
     * @return
     * @throws org.bubblecloud.zigbee.network.impl.ZigbeeBasedriverException
     */
    public boolean bind(int clusterId) throws ZigbeeBasedriverException;

    /**
     * @param clusterId
     * @return
     * @throws org.bubblecloud.zigbee.network.impl.ZigbeeBasedriverException
     */
    public boolean unbind(int clusterId) throws ZigbeeBasedriverException;

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

}

