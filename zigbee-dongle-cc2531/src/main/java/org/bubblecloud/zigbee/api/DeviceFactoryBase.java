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

import org.bubblecloud.zigbee.ZigBeeApiContext;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.util.ArraysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 */
public abstract class DeviceFactoryBase implements DeviceFactory {

    private static final Logger logger = LoggerFactory.getLogger(DeviceFactoryBase.class);

    protected ZigBeeApiContext ctx;

    private Class<?> refinement;
    protected int[] clusters;

    /**
     * @param ctx        {@link org.bubblecloud.zigbee.ZigBeeApiContext} of the bundle extending the refinement capabilities
     * @param refinement {@link Class} of the most refined interfaces provided by this factory
     */
    public DeviceFactoryBase(ZigBeeApiContext ctx, Class<?> refinement) {
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

    public abstract DeviceBase getInstance(ZigBeeEndpoint zbDevice);

    /**
     * Returns a <i>score</i> based on the number of endpoints supported by the device
     * that match those in the target device.
     * <p>
     * If the device is a direct match to the device type, then we return the maximum
     * score
     * @return int score being the number of endpoints supported
     */
    public int hasMatch(ZigBeeEndpoint device) {
    	// If this is an exact match for the device type, then return the maximum score
        if (device.getDeviceTypeId() == getDeviceId()) {
            return Integer.MAX_VALUE;
        }

        final Set<Integer> factoryInputClusters = new HashSet<Integer>();
        for (int cluster : getDeviceClusters()) {
            factoryInputClusters.add(cluster);
        }

        int score = device.getProfileId() == ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION
                ? ZigBeeEndpoint.MATCH_PROFILE_ID : 0;

        for (int c : device.getInputClusters()) {
            if (factoryInputClusters.contains(c)) {
                score++;
            }
        }

        return score;
    }

}
