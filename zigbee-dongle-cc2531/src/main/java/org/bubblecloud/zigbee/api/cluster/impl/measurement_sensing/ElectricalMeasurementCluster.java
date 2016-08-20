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

package org.bubblecloud.zigbee.api.cluster.impl.measurement_sensing;

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.measurement_sensing.ElectricalMeasurement;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;

/**
 * Implementation of the {@link ElectricalMeasurement} interface
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:ryan@presslab.us">Ryan Press</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2015-06-23 19:00:05 +0300 (Tue, 23 Jun 2015) $)
 * @since 0.4.0
 */
public class ElectricalMeasurementCluster extends ZCLClusterBase implements ElectricalMeasurement {

    private final AttributeImpl measurementType;
    private final AttributeImpl acFrequency;
    private final AttributeImpl rmsVoltage;
    private final AttributeImpl rmsCurrent;
    private final AttributeImpl activePower;
    private final AttributeImpl reactivePower;
    private final AttributeImpl apparentPower;
    private final AttributeImpl powerFactor;
    private final AttributeImpl acVoltageMultiplier;
    private final AttributeImpl acVoltageDivisor;
    private final AttributeImpl acCurrentMultiplier;
    private final AttributeImpl acCurrentDivisor;
    private final AttributeImpl acPowerMultiplier;
    private final AttributeImpl acPowerDivisor;

    private final Attribute[] attributes;

    public ElectricalMeasurementCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        measurementType = new AttributeImpl(zbDevice, this, Attributes.MEASUREMENT_TYPE);
        acFrequency = new AttributeImpl(zbDevice, this, Attributes.AC_FREQUENCY);
        rmsVoltage = new AttributeImpl(zbDevice, this, Attributes.RMS_VOLTAGE);
        rmsCurrent = new AttributeImpl(zbDevice, this, Attributes.RMS_CURRENT);
        activePower = new AttributeImpl(zbDevice, this, Attributes.ACTIVE_POWER);
        reactivePower = new AttributeImpl(zbDevice, this, Attributes.REACTIVE_POWER);
        apparentPower = new AttributeImpl(zbDevice, this, Attributes.APPARENT_POWER);
        powerFactor = new AttributeImpl(zbDevice, this, Attributes.POWER_FACTOR);
        acVoltageMultiplier = new AttributeImpl(zbDevice, this, Attributes.AC_VOLTAGE_MULTIPLIER);
        acVoltageDivisor = new AttributeImpl(zbDevice, this, Attributes.AC_VOLTAGE_DIVISOR);
        acCurrentMultiplier = new AttributeImpl(zbDevice, this, Attributes.AC_CURRENT_MULTIPLIER);
        acCurrentDivisor = new AttributeImpl(zbDevice, this, Attributes.AC_CURRENT_DIVISOR);
        acPowerMultiplier = new AttributeImpl(zbDevice, this, Attributes.AC_POWER_MULTIPLIER);
        acPowerDivisor = new AttributeImpl(zbDevice, this, Attributes.AC_POWER_DIVISOR);

        attributes = new AttributeImpl[]{measurementType,acFrequency,rmsVoltage,rmsCurrent,activePower,reactivePower,
                apparentPower,powerFactor,acVoltageMultiplier,acVoltageDivisor,acCurrentMultiplier,acCurrentDivisor,
                acPowerMultiplier,acPowerDivisor};
    }

    @Override
    public short getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }


    public Attribute getAttributeMeasurementType() {
        return measurementType;
    }

    public Attribute getAttributeAcFrequency() {
        return acFrequency;
    }

    public Attribute getAttributeRmsVoltage() {
        return rmsVoltage;
    }

    public Attribute getAttributeRmsCurrent() {
        return rmsCurrent;
    }

    public Attribute getAttributeActivePower() {
        return activePower;
    }

    public Attribute getAttributeReactivePower() {
        return reactivePower;
    }

    public Attribute getAttributeApparentPower() {
        return apparentPower;
    }

    public Attribute getAttributePowerFactor() {
        return powerFactor;
    }

    public Attribute getAttributeAcVoltageMultiplier() {
        return acVoltageMultiplier;
    }

    public Attribute getAttributeAcVoltageDivisor() {
        return acVoltageDivisor;
    }

    public Attribute getAttributeAcCurrentMultiplier() {
        return acCurrentMultiplier;
    }

    public Attribute getAttributeAcCurrentDivisor() {
        return acCurrentDivisor;
    }

    public Attribute getAttributeAcPowerMultiplier() {
        return acPowerMultiplier;
    }

    public Attribute getAttributeAcPowerDivisor() {
        return acPowerDivisor;
    }
}
