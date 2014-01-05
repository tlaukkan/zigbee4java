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
import org.bubblecloud.zigbee.api.cluster.impl.api.general.PowerConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class PowerConfigurationCluster extends ZCLClusterBase implements
        PowerConfiguration {

    private final AttributeImpl mainsVoltage;
    private final AttributeImpl mainsFrequency;
    private final AttributeImpl mainsAlarmMask;
    private final AttributeImpl mainsVoltageMinThreshold;
    private final AttributeImpl mainsVoltageMaxThreshol;
    private final AttributeImpl mainsVoltageDwellTripPoint;
    private final AttributeImpl batteryVoltage;
    private final AttributeImpl batteryManufaturer;
    private final AttributeImpl batterySize;
    private final AttributeImpl batteryAHrRating;
    private final AttributeImpl batteryQuantity;
    private final AttributeImpl batteryRatedVoltage;
    private final AttributeImpl batteryAlarmMask;
    private final AttributeImpl batteryVoltageMinThreshold;

    private final Attribute[] attributes;

    public PowerConfigurationCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        mainsVoltage = new AttributeImpl(zbDevice, this, Attributes.MAINS_VOLTAGE);
        mainsFrequency = new AttributeImpl(zbDevice, this, Attributes.MAINS_FREQUENCY);
        mainsAlarmMask = new AttributeImpl(zbDevice, this, Attributes.MAINS_ALARM_MASK);
        mainsVoltageMinThreshold = new AttributeImpl(zbDevice, this, Attributes.MAINS_VOLTAGE_MIN_THRESHOLD);
        mainsVoltageMaxThreshol = new AttributeImpl(zbDevice, this, Attributes.MAINS_VOLTAGE_MAX_THRESHOLD);
        mainsVoltageDwellTripPoint = new AttributeImpl(zbDevice, this, Attributes.MAINS_VOLTAGE_DWELL_TRIP_POINT);
        batteryVoltage = new AttributeImpl(zbDevice, this, Attributes.BATTERY_VOLTAGE);
        batteryManufaturer = new AttributeImpl(zbDevice, this, Attributes.BATTERY_MANUFACTURER);
        batterySize = new AttributeImpl(zbDevice, this, Attributes.BATTERY_SIZE);
        batteryAHrRating = new AttributeImpl(zbDevice, this, Attributes.BATTERY_AHr_RATING);
        batteryQuantity = new AttributeImpl(zbDevice, this, Attributes.BATTERY_QUANTITY);
        batteryRatedVoltage = new AttributeImpl(zbDevice, this, Attributes.BATTERY_RATED_VOLTAGE);
        batteryAlarmMask = new AttributeImpl(zbDevice, this, Attributes.BATTERY_ALARM_MASK);
        batteryVoltageMinThreshold = new AttributeImpl(zbDevice, this, Attributes.BATTERY_VOLTAGE_MIN_THRESHOLD);

        attributes = new AttributeImpl[]{mainsVoltage, mainsFrequency, mainsAlarmMask,
                mainsVoltageMinThreshold, mainsVoltageMaxThreshol, mainsVoltageDwellTripPoint,
                batteryVoltage, batteryManufaturer, batterySize, batteryAHrRating,
                batteryQuantity, batteryRatedVoltage, batteryAlarmMask, batteryVoltageMinThreshold};
    }

    @Override
    public short getId() {
        return PowerConfiguration.ID;
    }

    @Override
    public String getName() {
        return PowerConfiguration.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }

    public Attribute getAttributeBatteryAHrRating() {
        return batteryAHrRating;
    }

    public Attribute getAttributeBatteryAlarmMask() {
        return batteryAlarmMask;
    }

    public Attribute getAttributeBatteryManufacturer() {
        return batteryManufaturer;
    }

    public Attribute getAttributeBatteryQuantity() {
        return batteryQuantity;
    }

    public Attribute getAttributeBatteryRatedVoltage() {
        return batteryRatedVoltage;
    }

    public Attribute getAttributeBatterySize() {
        return batterySize;
    }

    public Attribute getAttributeBatteryVoltage() {
        return batteryVoltage;
    }

    public Attribute getAttributeBatteryVoltageMinThreshold() {
        return batteryVoltageMinThreshold;
    }

    public Attribute getAttributeMainsAlarmMask() {
        return mainsAlarmMask;
    }

    public Attribute getAttributeMainsDwellTripPoint() {
        return mainsVoltageDwellTripPoint;
    }

    public Attribute getAttributeMainsFrequency() {
        return mainsFrequency;
    }

    public Attribute getAttributeMainsVoltage() {
        return mainsVoltage;
    }

    public Attribute getAttributeMainsVoltageMaxThreshold() {
        return mainsVoltageMaxThreshol;
    }

    public Attribute getAttributeMainsVoltageMinThreshold() {
        return mainsVoltageMinThreshold;
    }

}
