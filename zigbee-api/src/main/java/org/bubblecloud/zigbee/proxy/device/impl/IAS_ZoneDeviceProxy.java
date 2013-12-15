/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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

import org.bubblecloud.zigbee.network.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.cluster.glue.security_safety.IASZone;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IAS_Zone;
import org.bubblecloud.zigbee.proxy.DeviceProxyBase;
import org.bubblecloud.zigbee.proxy.ProxyConstants;
import org.bubblecloud.zigbee.proxy.ZigBeeHAException;
import org.bubblecloud.zigbee.proxy.AbstractDeviceDescription;
import org.bubblecloud.zigbee.proxy.DeviceDescription;
import org.bubblecloud.zigbee.ZigbeeContext;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 *
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public class IAS_ZoneDeviceProxy extends DeviceProxyBase implements IAS_Zone {

    private IASZone iasZoneCluster;

    public IAS_ZoneDeviceProxy(ZigbeeContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {

        super(ctx, zbDevice);

        iasZoneCluster = (IASZone) getCluster(ProxyConstants.IAS_ZONE);
    }

    public IASZone getIASZone() {

        return iasZoneCluster;
    }

    @Override
    public String getName() {

        return IAS_Zone.NAME;
    }

    @Override
    public DeviceDescription getDescription() {

        return DEVICE_DESCRIPTOR;
    }

    final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

        public int[] getCustomClusters() {
            return IAS_Zone.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return IAS_Zone.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return IAS_Zone.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return IAS_Zone.STANDARD;
        }
    };
}