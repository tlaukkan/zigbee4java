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

package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.DeviceTemperatureConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class DeviceTemperatureConfigurationCluster extends ZCLClusterBase
        implements DeviceTemperatureConfiguration {

    private final AttributeImpl currentTemperature;
    private final AttributeImpl minTempExperienced;
    private final AttributeImpl maxTempExperienced;
    private final AttributeImpl overTempTotalDwell;
    private final AttributeImpl deviceTempAlarmMask;
    private final AttributeImpl lowTempThreshold;
    private final AttributeImpl highTempThreshold;
    private final AttributeImpl lowTempDwellTripPoint;
    private final AttributeImpl highTempDwellTripPoint;

    private final Attribute[] attributes;

    public DeviceTemperatureConfigurationCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);

        currentTemperature = new AttributeImpl(zbDevice, this, Attributes.CURRENT_TEMPERATURE);
        minTempExperienced = new AttributeImpl(zbDevice, this, Attributes.MIN_TEMP_EXPERIENCED);
        maxTempExperienced = new AttributeImpl(zbDevice, this, Attributes.MAX_TEMP_EXPERIENCED);
        overTempTotalDwell = new AttributeImpl(zbDevice, this, Attributes.OVER_TEMP_TOTAL_DWELL);
        deviceTempAlarmMask = new AttributeImpl(zbDevice, this, Attributes.DEVICE_TEMP_ALARM_MASK);
        lowTempThreshold = new AttributeImpl(zbDevice, this, Attributes.LOW_TEMP_THRESHOLD);
        highTempThreshold = new AttributeImpl(zbDevice, this, Attributes.HIGH_TEMP_THRESHOLD);
        lowTempDwellTripPoint = new AttributeImpl(zbDevice, this, Attributes.LOW_TEMP_DWELL_TRIP_POINT);
        highTempDwellTripPoint = new AttributeImpl(zbDevice, this, Attributes.HIGH_TEMP_DWELL_TRIP_POINT);

        attributes = new AttributeImpl[]{currentTemperature, minTempExperienced,
                maxTempExperienced, overTempTotalDwell, deviceTempAlarmMask,
                lowTempThreshold, highTempThreshold, lowTempDwellTripPoint,
                highTempDwellTripPoint};
    }

    @Override
    public short getId() {
        return DeviceTemperatureConfiguration.ID;
    }

    @Override
    public String getName() {
        return DeviceTemperatureConfiguration.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }

    public Attribute getAttributeCurrentTemperature() {
        return currentTemperature;
    }

    public Attribute getAttributeDeviceTempAlarmMask() {
        return deviceTempAlarmMask;
    }

    public Attribute getAttributeHighTempDwellTripPoint() {
        return highTempDwellTripPoint;
    }

    public Attribute getAttributeHighTempThreshold() {
        return highTempThreshold;
    }

    public Attribute getAttributeLowTempDwellTripPoint() {
        return lowTempDwellTripPoint;
    }

    public Attribute getAttributeLowTempThreshold() {
        return lowTempThreshold;
    }

    public Attribute getAttributeMaxTempExperienced() {
        return maxTempExperienced;
    }

    public Attribute getAttributeMinTempExperienced() {
        return minTempExperienced;
    }

    public Attribute getAttributeOverTempTotalDwell() {
        return overTempTotalDwell;
    }

}
