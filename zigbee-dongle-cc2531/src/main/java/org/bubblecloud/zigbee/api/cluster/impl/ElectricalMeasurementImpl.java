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
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.ElectricalMeasurement;
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.measurement_sensing.ElectricalMeasurementCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @author <a href="mailto:ryan@presslab.us">Ryan Press</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2015-06-23 19:00:05 +0300 (Tue, 23 Jun 2015) $)
 * @since 0.1.0
 */
public class ElectricalMeasurementImpl implements ElectricalMeasurement {

    private final ElectricalMeasurementCluster electricalMeasurementCluster;
    private final Attribute measurementType;
    private final Attribute acVoltageMultiplier;
    private final Attribute acVoltageDivisor;
    private final Attribute acCurrentMultiplier;
    private final Attribute acCurrentDivisor;
    private final Attribute acPowerMultiplier;
    private final Attribute acPowerDivisor;

    public ElectricalMeasurementImpl(ZigBeeEndpoint zbDevice) {
        electricalMeasurementCluster = new ElectricalMeasurementCluster(zbDevice);
        measurementType = electricalMeasurementCluster.getAttributeMeasurementType();
        acVoltageMultiplier = electricalMeasurementCluster.getAttributeAcVoltageMultiplier();
        acVoltageDivisor = electricalMeasurementCluster.getAttributeAcVoltageDivisor();
        acCurrentMultiplier = electricalMeasurementCluster.getAttributeAcCurrentMultiplier();
        acCurrentDivisor = electricalMeasurementCluster.getAttributeAcCurrentDivisor();
        acPowerMultiplier = electricalMeasurementCluster.getAttributeAcPowerMultiplier();
        acPowerDivisor = electricalMeasurementCluster.getAttributeAcPowerDivisor();
    }

    public Float getAcFrequency() throws ZigBeeDeviceException {
        try {
            return (Float) electricalMeasurementCluster.getAttributeAcFrequency().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Float getRmsVoltage() throws ZigBeeDeviceException {
        try {
            Float value = Float.valueOf((Integer)electricalMeasurementCluster.getAttributeRmsVoltage().getValue());
            try {
                return value * Float.valueOf((Integer)acVoltageMultiplier.getValue()) / Float.valueOf((Integer)acVoltageDivisor.getValue());
            } catch (ZigBeeClusterException e) {
              /* Scaling is unsupported */
              return value;
            }
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Float getRmsCurrent() throws ZigBeeDeviceException {
        try {
            Float value = Float.valueOf((Integer)electricalMeasurementCluster.getAttributeRmsCurrent().getValue());
            try {
                return value * Float.valueOf((Integer)acCurrentMultiplier.getValue()) / Float.valueOf((Integer)acCurrentDivisor.getValue());
            } catch (ZigBeeClusterException e) {
                /* Scaling unsupported */
                return value;
            }
        } catch (ZigBeeClusterException f) {
            throw new ZigBeeDeviceException(f);
        }
    }

    public Float getActivePower() throws ZigBeeDeviceException {
        try {
            Float value = Float.valueOf((Integer)electricalMeasurementCluster.getAttributeActivePower().getValue());
            try {
                return value * Float.valueOf((Integer)acPowerMultiplier.getValue()) / Float.valueOf((Integer)acPowerDivisor.getValue());
            } catch (ZigBeeClusterException e) {
                /* Scaling unsupported */
                return value;
            }
        } catch (ZigBeeClusterException f) {
            throw new ZigBeeDeviceException(f);
        }
    }

    public Float getReactivePower() throws ZigBeeDeviceException {
        try {
            Float value = Float.valueOf((Integer)electricalMeasurementCluster.getAttributeReactivePower().getValue());
            try {
                return value * Float.valueOf((Integer)acPowerMultiplier.getValue()) / Float.valueOf((Integer)acPowerDivisor.getValue());
            } catch (ZigBeeClusterException e) {
                /* Scaling unsupported */
                return value;
            }
        } catch (ZigBeeClusterException f) {
            throw new ZigBeeDeviceException(f);
        }
    }

    public Float getApparentPower() throws ZigBeeDeviceException {
        try {
            Float value = Float.valueOf((Integer)electricalMeasurementCluster.getAttributeApparentPower().getValue());
            try {
                return value * Float.valueOf((Integer)acPowerMultiplier.getValue()) / Float.valueOf((Integer)acPowerDivisor.getValue());
            } catch (ZigBeeClusterException e) {
                /* Scaling unsupported */
                return value;
            }
        } catch (ZigBeeClusterException f) {
            throw new ZigBeeDeviceException(f);
        }
    }

    public Float getPowerFactor() throws ZigBeeDeviceException {
        try {
            return Float.valueOf((Integer)electricalMeasurementCluster.getAttributePowerFactor().getValue()) / 100;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Reporter[] getAttributeReporters() {
        return electricalMeasurementCluster.getAttributeReporters();
    }

    public int getId() {
        return electricalMeasurementCluster.getId();
    }

    public String getName() {
        return electricalMeasurementCluster.getName();
    }

    public Attribute getAttribute(int id) {
        Attribute[] attributes = electricalMeasurementCluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getId() == id)
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return electricalMeasurementCluster.getAvailableAttributes();
    }
}
