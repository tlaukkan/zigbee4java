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

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.general.DeviceTemperatureConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.general.DeviceTemperatureConfigurationCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 */
public class DeviceTemperatureConfigurationImpl implements DeviceTemperatureConfiguration {

    private final DeviceTemperatureConfigurationCluster cluster;
    private final Attribute currentTemperature;
    private final Attribute deviceTemperature;
    private final Attribute highTemperature;
    private final Attribute highTemperatureThreshold;
    private final Attribute lowTemperature;
    private final Attribute lowTemperatureThreshold;
    private final Attribute maxTemperature;
    private final Attribute minTemperature;
    private final Attribute overTemperature;

    public DeviceTemperatureConfigurationImpl(ZigBeeEndpoint zbDevice) {
        cluster = new DeviceTemperatureConfigurationCluster(zbDevice);
        currentTemperature = cluster.getAttributeCurrentTemperature();
        deviceTemperature = cluster.getAttributeDeviceTempAlarmMask();
        highTemperature = cluster.getAttributeHighTempDwellTripPoint();
        highTemperatureThreshold = cluster.getAttributeHighTempThreshold();
        lowTemperature = cluster.getAttributeLowTempDwellTripPoint();
        lowTemperatureThreshold = cluster.getAttributeLowTempThreshold();
        maxTemperature = cluster.getAttributeMaxTempExperienced();
        minTemperature = cluster.getAttributeMinTempExperienced();
        overTemperature = cluster.getAttributeOverTempTotalDwell();
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

    public Attribute getCurrentTemperature() {
        return currentTemperature;
    }

    public Attribute getDeviceTempAlarmMask() {
        return deviceTemperature;
    }

    public Attribute getHighTempDwellTripPoint() {
        return highTemperature;
    }

    public Attribute getHighTempThreshold() {
        return highTemperatureThreshold;
    }

    public Attribute getLowTempDwellTripPoint() {
        return lowTemperature;
    }

    public Attribute getLowTempThreshold() {
        return lowTemperatureThreshold;
    }

    public Attribute getMaxTempExperienced() {
        return maxTemperature;
    }

    public Attribute getMinTempExperienced() {
        return minTemperature;
    }

    public Attribute getOverTempTotalDwell() {
        return overTemperature;
    }

}
