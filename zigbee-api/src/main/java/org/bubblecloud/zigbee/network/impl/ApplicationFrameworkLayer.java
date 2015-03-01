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

package org.bubblecloud.zigbee.network.impl;

import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.ZigBeeNetworkManager;
import org.bubblecloud.zigbee.network.packet.af.AF_REGISTER;
import org.bubblecloud.zigbee.network.packet.af.AF_REGISTER_SRSP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * This class is a <i>singelton</i> aimed at share the <b>the Application Framework Layer</b><br>
 * status of the <b>ZigBee Base Drier</b> among all the {@link org.bubblecloud.zigbee.network.ZigBeeEndpoint} register by it.<br>
 * <br>
 * In particular, this class tracks the <i>Transaction Id</i> and the <i>Active End Point</i><br>
 * on the hardware providing access to <i>ZigBee Network</i> (currently the <b>Texas Instrument CC2480</b>)<br>
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ApplicationFrameworkLayer {

    private final static Object LOCK = new Object();
    private final static Logger logger = LoggerFactory.getLogger(ApplicationFrameworkLayer.class);

    class SenderIdentifier {
        int profileId;
        int clusterId;

        public SenderIdentifier(int profileId, int clusterId) {
            this.profileId = profileId;
            this.clusterId = clusterId;
        }

        @Override
        public int hashCode() {
            return (profileId << 16) + clusterId;
        }

        public boolean equals(Object o) {
            if (o instanceof SenderIdentifier) {
                SenderIdentifier si = (SenderIdentifier) o;
                return si.profileId == profileId && si.clusterId == clusterId;
            } else {
                return false;
            }
        }
    }

    private static ApplicationFrameworkLayer singelton;

    final HashMap<SenderIdentifier, Short> sender2EndPoint = new HashMap<SenderIdentifier, Short>();
    final HashMap<Integer, List<Integer>> profile2Cluster = new HashMap<Integer, List<Integer>>();
    final HashMap<Short, Byte> endPoint2Transaction = new HashMap<Short, Byte>();

    private final ZigBeeNetworkManager driver;
    private final ZigBeeNetwork network;

    private byte firstFreeEndPoint;


    private ApplicationFrameworkLayer(ZigBeeNetworkManager driver) {
        this.driver = driver;
        firstFreeEndPoint = 1;
        network = new ZigBeeNetwork();
    }

    public static ApplicationFrameworkLayer getAFLayer(ZigBeeNetworkManager driver) {
        synchronized (LOCK) {
            if (singelton == null) {
                singelton = new ApplicationFrameworkLayer(driver);
            } else if (singelton.driver != driver) {
                /*
				 * It means that the service implementing the driver has been changed 
				 * so we have to create a new ApplicationFrameworkLayer
				 */
                singelton = new ApplicationFrameworkLayer(driver);
            }
            return singelton;
        }
    }


    public short getSendingEndpoint(ZigBeeEndpoint endpoint, int clusterId) {
        SenderIdentifier si = new SenderIdentifier(
                ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION, (short) clusterId
        );
        logger.trace("Looking for a registered enpoint among {}", sender2EndPoint.size());
        synchronized (sender2EndPoint) {
            if (sender2EndPoint.containsKey(si)) {
                logger.trace("An enpoint already registered for <profileId,clusterId>=<{},{}>", si.profileId, si.clusterId);
                return sender2EndPoint.get(si);
            } else {
                logger.info("No endpoint registered for <profileId,clusterId>=<{},{}>", si.profileId, si.clusterId);
                final byte ep = createEndPoint(si, endpoint.getProfileId());
                return ep;
            }
        }
    }

    public short getSendingEndpoint(ZigBeeEndpoint endpoint, ClusterMessage input) {
        return getSendingEndpoint(endpoint, input.getId());
    }

    /**
     * Creates default sending end point.
     */
    public void createDefaultSendingEndPoint() {
        final SenderIdentifier si = new SenderIdentifier(
                ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION,
                (short) ZigBeeApiConstants.CLUSTER_ID_BASIC
        );
        final byte endPoint = getFreeEndPoint();
        final List<Integer> clusterSet = Arrays.asList(
            ZigBeeApiConstants.CLUSTER_ID_BASIC,
            ZigBeeApiConstants.CLUSTER_ID_POWER_CONFIGURATION,
            ZigBeeApiConstants.CLUSTER_ID_DEVICE_TEMPERATURE_CONFIGURATION,
            ZigBeeApiConstants.CLUSTER_ID_IDENTIFY,
            ZigBeeApiConstants.CLUSTER_ID_GROUPS,
            ZigBeeApiConstants.CLUSTER_ID_SCENES,
            ZigBeeApiConstants.CLUSTER_ID_ON_OFF,
            ZigBeeApiConstants.CLUSTER_ID_ON_OFF_SWITCH_CONFIGURATION ,
            ZigBeeApiConstants.CLUSTER_ID_LEVEL_CONTROL,
            ZigBeeApiConstants.CLUSTER_ID_ALARMS,
            ZigBeeApiConstants.CLUSTER_ID_BINARY_INPUT,
            ZigBeeApiConstants.CLUSTER_ID_TIME,
            ZigBeeApiConstants.CLUSTER_ID_ANALOG_INPUT,
            ZigBeeApiConstants.CLUSTER_ID_COMMISSIONING,
            ZigBeeApiConstants.CLUSTER_ID_METERING,
            ZigBeeApiConstants.CLUSTER_ID_SHADE_CONFIGURATION,
            ZigBeeApiConstants.CLUSTER_ID_DOOR_LOCK,
            ZigBeeApiConstants.CLUSTER_ID_WINDOW_COVERING,
            ZigBeeApiConstants.CLUSTER_ID_PUMP_CONFIGURATION_AND_CONTROL,
            ZigBeeApiConstants.CLUSTER_ID_THERMOSTAT,
            ZigBeeApiConstants.CLUSTER_ID_FAN_CONTROL,
            ZigBeeApiConstants.CLUSTER_ID_THERMOSTAT_USER_INTERFACE_CONFIGURATION,
            ZigBeeApiConstants.CLUSTER_ID_COLOR_CONTROL,
            ZigBeeApiConstants.CLUSTER_ID_PRESSURE_MEASUREMENT,
            ZigBeeApiConstants.CLUSTER_ID_ILLUMINANCE_MEASUREMENT,
            ZigBeeApiConstants.CLUSTER_ID_ILLUMINANCE_LEVEL_SENSING,
            ZigBeeApiConstants.CLUSTER_ID_TEMPERATURE_MEASUREMENT,
            ZigBeeApiConstants.CLUSTER_ID_FLOW_MEASUREMENT,
            ZigBeeApiConstants.CLUSTER_ID_RELATIVE_HUMIDITY_MEASUREMENT,
            ZigBeeApiConstants.CLUSTER_ID_OCCUPANCY_SENSING,
            ZigBeeApiConstants.CLUSTER_ID_IAS_ZONE,
            ZigBeeApiConstants.CLUSTER_ID_IAS_ACE,
            ZigBeeApiConstants.CLUSTER_ID_IAS_WD
        );

        final int[] clusters = new int[clusterSet.size()];
        if (clusters.length > 33) {
            throw new RuntimeException("Too many default clusters.");
        }

        int index = 0;
        for (final Integer cluster : clusterSet) {
            clusters[index] = cluster;
            index++;
        }

        final AF_REGISTER_SRSP result = driver.sendAFRegister(new AF_REGISTER(
                endPoint, si.profileId, (short) 0, (byte) 0,
                new int[0], clusters
        ));

        if (result.getStatus() != 0) {
            // Default end point creation failed probably due to end point already exists.
            logger.warn("Default end point creation failed with status: {} ", Status.getStatus((byte) result.getStatus()));
            return;
        }

        logger.info("Registered default sending endpoint {} with clusters: {}", endPoint, clusters);
        registerSenderEndPoint(endPoint, si.profileId, clusters);
    }

    private byte createEndPoint(SenderIdentifier si, int receiverProfileId) {
        byte endPoint = getFreeEndPoint();
        logger.debug("Registering a new endpoint for <profileId,clusterId>  <{},{}>", si.profileId, si.clusterId);

        Set<Integer> clusterSet = collectClusterForProfile(receiverProfileId);
        final int[] clusters = new int[clusterSet.size()];

        int index = 0;
        for (final Integer cluster : clusterSet) {
            clusters[index] = cluster;
            index++;
        }

        if (clusters.length > 33) {
            logger.warn(
                    "We found too many cluster to implement for a single endpoint " +
                            "(maxium is 32), so we are filtering out the extra {}", clusters
            );
			/*
			 * Too many cluster to implement for this profile we must use the first 31
			 * plus the cluster that we are trying to create as 32nd value 
			 */
            int[] values = new int[33];
            values[0] = si.clusterId;
            for (int i = 1; i < values.length; i++) {
                values[i] = clusters[i];
            }
            logger.warn("Following the list of filtered cluster that we are going to register: {} ", clusters);
        }
        AF_REGISTER_SRSP result = null;
        int retry = 0;
        do {
            result = driver.sendAFRegister(new AF_REGISTER(
                    endPoint, si.profileId, (short) 0, (byte) 0,
                    new int[0], clusters
            ));
            //FIX We should retry only when Status != 0xb8  ( Z_APS_DUPLICATE_ENTRY )
            if (result.getStatus() != 0) {
                if (retry < 1) {
                    endPoint = getFreeEndPoint();
                } else {
					/*
					 * //TODO We should provide a workaround for the maximum number of registered EndPoint
					 * For example, with the CC2480 we could reset the dongle
					 */
                    throw new IllegalStateException("Unable create a new Endpoint. AF_REGISTER command failed with " + result.getStatus() + ":" + result.getErrorMsg());
                }
            } else {
                break;
            }
        } while (true);
        final int profileId = si.profileId;

        logger.debug("Registered endpoint {} with clusters: {}", endPoint, clusters);

        registerSenderEndPoint(endPoint, profileId, clusters);
        return endPoint;
    }

    public void registerSenderEndPoint(short endPoint, int profileId, int[] clusters) {
        final List<Integer> list;
        synchronized (profile2Cluster) {
            if (profile2Cluster.containsKey(profileId)) {
                list = profile2Cluster.get(profileId);
            } else {
                list = new ArrayList<Integer>();
                profile2Cluster.put(profileId, list);
            }
        }
        synchronized (sender2EndPoint) {
            for (int i = 0; i < clusters.length; i++) {
                list.add(clusters[i]);
                SenderIdentifier adding = new SenderIdentifier(profileId, clusters[i]);
                if (sender2EndPoint.containsKey(adding)) {
                    if (sender2EndPoint.get(adding).equals(endPoint)) {
                        continue;
                    }
                    logger.warn("Overriding a valid <profileId,clusterId> endpoint with this {}", adding);
                }
                logger.debug("Adding <profileId,clusterId> <{},{}> to sender2EndPoint hashtable", adding.profileId, adding.clusterId);
                sender2EndPoint.put(adding, endPoint);
            }
        }
    }

    private Set<Integer> collectClusterForProfile(int profileId) {
        final HashSet<Integer> clusters = new HashSet<Integer>();
        final Collection<ZigBeeEndpoint> endpoints = network.getEndpoints(profileId);
        logger.debug("Found {} devices belonging to profile {}", endpoints.size(), profileId);
        for (ZigBeeEndpoint endpoint : endpoints) {
            int[] ids;
            ids = endpoint.getInputClusters();
            logger.debug(
                    "Device {} provides the following cluster as input {}",
                    endpoint.getEndpointId(), ids
            );
            for (int i = 0; i < ids.length; i++) {
                clusters.add(ids[i]);
            }
            ids = endpoint.getOutputClusters();
            logger.debug(
                    "Device {} provides the following cluster as input {}",
                    endpoint.getEndpointId(), ids
            );
            for (int i = 0; i < ids.length; i++) {
                clusters.add(ids[i]);
            }
        }

        final List<Integer> implementedCluster = profile2Cluster.get(profileId);
        if (implementedCluster != null) {
            logger.debug("List of clusters of profile {} already provided by some registered endpoint {}", profileId,
                    implementedCluster);
            clusters.removeAll(implementedCluster);
        } else {
            logger.debug("No previus clusters registered on any endpoint of the dongle for the profile {}", profileId);
        }

        return clusters;
    }

    public ZigBeeNetwork getZigBeeNetwork() {
        return network;
    }

    public byte getNextTransactionId(short endPoint) {
        if (!endPoint2Transaction.containsKey(endPoint)) {
            endPoint2Transaction.put(endPoint, (byte) 1);
        }
        byte value = endPoint2Transaction.get(endPoint);
        switch (value) {
            case 127: {
                endPoint2Transaction.put(endPoint, (byte) -128);
                return 127;
            }
            default: {
                endPoint2Transaction.put(endPoint, (byte) (value + 1));
                return value;
            }
        }
    }

    private byte getFreeEndPoint() {
        switch (firstFreeEndPoint) {
            case 127: {
                firstFreeEndPoint = -128;
                return 127;
            }
            case -15: {
                throw new IllegalStateException("No more end point free");
            }
            default: {
                firstFreeEndPoint += 1;
                return (byte) (firstFreeEndPoint - 1);
            }
        }
    }

}
