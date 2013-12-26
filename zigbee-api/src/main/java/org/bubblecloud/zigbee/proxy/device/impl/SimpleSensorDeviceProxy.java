/*
   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
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
package org.bubblecloud.zigbee.proxy.device.impl;

import org.bubblecloud.zigbee.ZigbeeProxyContext;
import org.bubblecloud.zigbee.network.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.*;
import org.bubblecloud.zigbee.ZigbeeConstants;
import org.bubblecloud.zigbee.proxy.cluster.general.BinaryInput;
import org.bubblecloud.zigbee.proxy.device.generic.SimpleSensor;


/**
 * @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.0
 */
public class SimpleSensorDeviceProxy extends DeviceProxyBase implements SimpleSensor {

    private BinaryInput binaryInput;

    public SimpleSensorDeviceProxy(ZigbeeProxyContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {
        super(ctx, zbDevice);
        binaryInput = (BinaryInput) getCluster(ZigbeeConstants.CLUSTER_ID_BINARY_INPUT);
    }

    final static DeviceDescription DEVICE_DESCRIPTOR = new AbstractDeviceDescription() {

        public int[] getCustomClusters() {
            return SimpleSensor.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return SimpleSensor.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return SimpleSensor.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return SimpleSensor.STANDARD;
        }

    };

    @Override
    public DeviceDescription getDescription() {
        return DEVICE_DESCRIPTOR;
    }

    @Override
    public String getName() {
        return SimpleSensor.NAME;
    }

    public BinaryInput getBinaryInput() {
        return binaryInput;
    }

}
