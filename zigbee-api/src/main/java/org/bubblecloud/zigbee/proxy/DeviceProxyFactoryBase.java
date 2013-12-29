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

import org.bubblecloud.zigbee.ZigbeeConstants;
import org.bubblecloud.zigbee.ZigbeeProxyContext;
import org.bubblecloud.zigbee.network.ZigbeeEndpoint;
import org.bubblecloud.zigbee.util.ArraysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 */
public abstract class DeviceProxyFactoryBase implements DeviceProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(DeviceProxyFactoryBase.class);

    protected ZigbeeProxyContext ctx;

    private Class<?> refinement;
    protected int[] clusters;

    /**
     * @param ctx        {@link org.bubblecloud.zigbee.ZigbeeProxyContext} of the bundle extending the refinement capabilities
     * @param refinement {@link Class} of the most refined interfaces provided by this factory
     */
    public DeviceProxyFactoryBase(ZigbeeProxyContext ctx, Class<?> refinement) {
        this.ctx = ctx;
        this.refinement = refinement;
    }

    public abstract int getDeviceId();

    public int[] getDeviceClusters() {
        synchronized (this) {
            if (clusters == null) {
                try {
                    final int[] standard = (int[]) refinement.getDeclaredField("STANDARD").get(null);
                    final int[] custom = (int[]) refinement.getDeclaredField("CUSTOM").get(null);
                    clusters = ArraysUtil.append(standard, custom);
                } catch (Exception e) {
                    logger.error(
                            "Unable to retrieve the implemented clusters by means of the reflection, "
                                    + "the class {} does not provide the static field STANDARD or CUSTOM.\n"
                                    + "Please modify the source code of the class by adding such static field or"
                                    + "override the getDeviceClusters() method ", refinement.getName()
                    );
                    logger.debug("Stack exception of the getDeviceClusters() error", e);
                }
            }
            return clusters;
        }
    }

    public abstract String[] getRefinedInterfaces();

    public abstract DeviceProxyBase getInstance(ZigbeeEndpoint zbDevice);

    public int hasMatch(ZigbeeEndpoint device) {
        int[] inputClusterIDs = device.getInputClusters();
        int[] refinedClusterIds = (int[]) getDeviceClusters();
        int score = device.getProfileId() == ZigbeeConstants.PROFILE_ID_HOME_AUTOMATION
                ? ZigbeeEndpoint.MATCH_PROFILE_ID : 0;

        for (int i = 0; i < inputClusterIDs.length; i++) {
            for (int j = 0; j < refinedClusterIds.length; j++) {
                score += inputClusterIDs[i] == refinedClusterIds[j] ? ZigbeeEndpoint.MATCH_CLUSTER_ID : 0;
            }
        }

        score += device.getDeviceId() == getDeviceId() ? ZigbeeEndpoint.MATCH_DEVICE_ID : 0;

        return score;
    }

}
