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

package org.bubblecloud.zigbee.api.cluster.impl;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.general.PowerConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.general.PowerConfigurationCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 */
public class PowerConfigurationImpl implements PowerConfiguration {

    private final PowerConfigurationCluster cluster;
    private final Attribute batteryRating;
    private final Attribute batteryAlarm;
    private final Attribute batteryManufacturer;
    private final Attribute batteryQty;
    private final Attribute batteryRatedVoltage;
    private final Attribute batterySize;
    private final Attribute batteryVoltage;
    private final Attribute batteryVoltageMin;
    private final Attribute mainsAlarm;
    private final Attribute mainsDwell;
    private final Attribute mainsHz;
    private final Attribute mainsV;
    private final Attribute mainsVMax;
    private final Attribute mainsVMin;

    public PowerConfigurationImpl(ZigBeeEndpoint zbDevice) {
        cluster = new PowerConfigurationCluster(zbDevice);
        batteryRating = cluster.getAttributeBatteryAHrRating();
        batteryAlarm = cluster.getAttributeBatteryAlarmMask();
        batteryManufacturer = cluster.getAttributeBatteryManufacturer();
        batteryQty = cluster.getAttributeBatteryQuantity();
        batteryRatedVoltage = cluster.getAttributeBatteryRatedVoltage();
        batterySize = cluster.getAttributeBatterySize();
        batteryVoltage = cluster.getAttributeBatteryVoltage();
        batteryVoltageMin = cluster.getAttributeBatteryVoltageMinThreshold();
        mainsAlarm = cluster.getAttributeMainsAlarmMask();
        mainsDwell = cluster.getAttributeMainsDwellTripPoint();
        mainsHz = cluster.getAttributeMainsFrequency();
        mainsV = cluster.getAttributeMainsVoltage();
        mainsVMax = cluster.getAttributeMainsVoltageMaxThreshold();
        mainsVMin = cluster.getAttributeMainsVoltageMinThreshold();
    }

    public Reporter[] getAttributeReporters() {
        return cluster.getAttributeReporters();
    }

    public int getId() {
        return cluster.getId();
    }

    public String getName() {
        return cluster.getName();
    }

    public Attribute getAttribute(int id) {
        Attribute[] attributes = cluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getId() == id)
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return cluster.getAvailableAttributes();
    }

    public Attribute getBatteryAHrRating() {
        return batteryRating;
    }

    public Attribute getBatteryAlarmMask() {
        return batteryAlarm;
    }

    public Attribute getBatteryManufacturer() {
        return batteryManufacturer;
    }

    public Attribute getBatteryQuantity() {
        return batteryQty;
    }

    public Attribute getBatteryRatedVoltage() {
        return batteryRatedVoltage;
    }

    public Attribute getBatterySize() {
        return batterySize;
    }

    public Attribute getBatteryVoltage() {
        return batteryVoltage;
    }

    public Attribute getBatteryVoltageMinThreshold() {
        return batteryVoltageMin;
    }

    public Attribute getMainsAlarmMask() {
        return mainsAlarm;
    }

    public Attribute getMainsDwellTripPoint() {
        return mainsDwell;
    }

    public Attribute getMainsFrequency() {
        return mainsHz;
    }

    public Attribute getMainsVoltage() {
        return mainsV;
    }

    public Attribute getMainsVoltageMaxThreshold() {
        return mainsVMax;
    }

    public Attribute getMainsVoltageMinThreshold() {
        return mainsVMin;
    }

}
