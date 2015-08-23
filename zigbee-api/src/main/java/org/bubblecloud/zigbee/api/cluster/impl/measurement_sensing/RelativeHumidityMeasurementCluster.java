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
import org.bubblecloud.zigbee.api.cluster.impl.api.measurement_sensing.RelativeHumidityMeasurement;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;

/**
 * Implementation of the {@link RelativeHumidityMeasurement} interface
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 */
public class RelativeHumidityMeasurementCluster extends ZCLClusterBase implements RelativeHumidityMeasurement {

    private final AttributeImpl measuredValue;
    private final AttributeImpl minMeasuredValue;
    private final AttributeImpl maxMeasuredValue;
    private final AttributeImpl tolerance;

    private final Attribute[] attributes;

    public RelativeHumidityMeasurementCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        measuredValue = new AttributeImpl(zbDevice, this, Attributes.MEASURED_VALUE_UNSIGNED_16_BIT);
        minMeasuredValue = new AttributeImpl(zbDevice, this, Attributes.MIN_MEASURED_VALUE_UNSIGNED_16_BIT);
        maxMeasuredValue = new AttributeImpl(zbDevice, this, Attributes.MAX_MEASURED_VALUE_UNSIGNED_16_BIT);
        tolerance = new AttributeImpl(zbDevice, this, Attributes.TOLERANCE);
        attributes = new AttributeImpl[]{measuredValue, minMeasuredValue, maxMeasuredValue, tolerance};
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

    public Attribute getAttributeMaxMeasuredValue() {
        return maxMeasuredValue;
    }

    public Attribute getAttributeMeasuredValue() {
        return measuredValue;
    }

    public Attribute getAttributeMinMeasuredValue() {
        return minMeasuredValue;
    }

    public Attribute getAttributeTolerance() {
        return tolerance;
    }

}
