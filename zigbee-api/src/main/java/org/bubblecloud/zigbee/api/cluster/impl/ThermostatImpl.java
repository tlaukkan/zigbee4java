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

import org.bubblecloud.zigbee.api.ReportingConfiguration;
import org.bubblecloud.zigbee.api.cluster.general.Thermostat;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.event.MeasuredValueBridgeListeners;
import org.bubblecloud.zigbee.api.cluster.impl.general.ThermostatCluster;
import org.bubblecloud.zigbee.api.cluster.measurement_sensing.event.MeasuredValueListener;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;

/**
 * 
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision:  $ ($LastChangedDate: $)
 *
 */
public class ThermostatImpl implements Thermostat {

	private final ThermostatCluster thermostatCluster;
    private final Attribute localTemperature;
    private final Attribute occupiedCoolingSetpoint;
    private final Attribute occupiedHeatingSetpoint;
    private final Attribute controlSequenceOfOperation;
    private final Attribute systemMode;

    private final MeasuredValueBridgeListeners measureBridge;

	public ThermostatImpl(ZigBeeEndpoint zbDevice){
		thermostatCluster = new ThermostatCluster(zbDevice);
		localTemperature = thermostatCluster.getAttributeLocalTemperature();
		occupiedCoolingSetpoint = thermostatCluster.getAttributeOccupiedCoolingSetpoint();
		occupiedHeatingSetpoint = thermostatCluster.getAttributeOccupiedHeatingSetpoint();
		controlSequenceOfOperation = thermostatCluster.getAttributeControlSequenceOfOperation();
		systemMode = thermostatCluster.getAttributeSystemMode();

		measureBridge = new MeasuredValueBridgeListeners(new ReportingConfiguration(), localTemperature, this);
	}

    public Attribute getlocalTemperature() {
        return localTemperature;
    }

    public Attribute getoccupiedCoolingSetpoint() {
        return occupiedCoolingSetpoint;
    }

    public Attribute getoccupiedHeatingSetpoint() {
        return occupiedHeatingSetpoint;
    }

    public Attribute getcontrolSequenceOfOperation() {
        return controlSequenceOfOperation;
    }

    public Attribute getsystemMode() {
        return systemMode;
    }

	public int getId() {
		
		return thermostatCluster.getId();
	}

	public String getName() {
	
		return thermostatCluster.getName();
	}

    public Reporter[] getAttributeReporters() {
		return thermostatCluster.getAttributeReporters();
	}

	public Attribute[] getAttributes() {
	
		return thermostatCluster.getAvailableAttributes();
	}

	public Attribute getAttribute(int id) {
		Attribute[] attributes = thermostatCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

    public boolean subscribe(MeasuredValueListener listener) {
        return measureBridge.subscribe(listener);
    }

    public boolean unsubscribe(MeasuredValueListener listener) {
        return measureBridge.unsubscribe(listener);
    }
}