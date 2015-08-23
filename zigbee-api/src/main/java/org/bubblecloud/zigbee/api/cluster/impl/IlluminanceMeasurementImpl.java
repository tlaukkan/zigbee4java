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

package org.bubblecloud.zigbee.api.cluster.impl;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.IlluminanceMeasurement;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.event.MeasuredValueListener;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.event.ToleranceListener;
import org.bubblecloud.zigbee.api.cluster.impl.event.MeasuredValueBridgeListeners;
import org.bubblecloud.zigbee.api.cluster.impl.event.ToleranceBridgeListeners;
import org.bubblecloud.zigbee.api.ReportingConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.measurement_sensing.IlluminanceMeasurementCluster;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 */
public class IlluminanceMeasurementImpl implements IlluminanceMeasurement {

    private final IlluminanceMeasurementCluster cluster;

    private final Attribute measuredValue;
    private final Attribute minMeasuredValue;
    private final Attribute maxMeasuredValue;
    private final Attribute tolerance;
    private final Attribute lightSensorType;

    private final MeasuredValueBridgeListeners measureBridge;
    private final ToleranceBridgeListeners toleranceBridge;
    //private final ToleranceBridgeListeners toleranceBridge;

    public IlluminanceMeasurementImpl(ZigBeeEndpoint zbDevice) {
        cluster = new IlluminanceMeasurementCluster(zbDevice);
        measuredValue = cluster.getMeasuredValue();
        minMeasuredValue = cluster.getMinMeasuredValue();
        maxMeasuredValue = cluster.getMaxMeasuredValue();
        tolerance = cluster.getTolerance();
        lightSensorType = cluster.getLightSensorType();

        toleranceBridge = new ToleranceBridgeListeners(new ReportingConfiguration(), tolerance, this);
        measureBridge = new MeasuredValueBridgeListeners(new ReportingConfiguration(), measuredValue, this);
    }

    public int getId() {

        return cluster.getId();
    }

    public String getName() {

        return cluster.getName();
    }

    public Reporter[] getAttributeReporters() {

        return cluster.getAttributeReporters();
    }

    public Attribute[] getAttributes() {

        return cluster.getAvailableAttributes();
    }

    public Attribute getAttribute(int id) {

        Attribute[] attributes = cluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getId() == id)
                return attributes[i];
        }
        return null;
    }

    public Attribute getMeasuredValue() {

        return measuredValue;
    }

    public Attribute getMinMeasuredValue() {

        return minMeasuredValue;
    }

    public Attribute getMaxMeasuredValue() {

        return maxMeasuredValue;
    }

    public Attribute getTolerance() {

        return tolerance;
    }

    public Attribute getLightSensorType() {

        return lightSensorType;
    }

    public boolean subscribe(MeasuredValueListener tl) {

        return measureBridge.subscribe(tl);
    }

    public boolean unsubscribe(MeasuredValueListener tl) {

        return measureBridge.unsubscribe(tl);
    }

    public boolean subscribe(ToleranceListener tl) {

        return toleranceBridge.subscribe(tl);
    }

    public boolean unsubscribe(ToleranceListener tl) {

        return toleranceBridge.unsubscribe(tl);
    }
}