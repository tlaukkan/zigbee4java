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
import org.bubblecloud.zigbee.api.cluster.impl.api.HVAC.Thermostat;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.measurement_sensing.TemperatureMeasurement;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;

/**
 * Implementation of the {@link ThermostatCluster} interface
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:ryan@presslab.us">Ryan Press</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 */
public class ThermostatCluster extends ZCLClusterBase implements Thermostat {

    private final AttributeImpl localTemperature;
    private final AttributeImpl occupiedCoolingSetpoint;
    private final AttributeImpl occupiedHeatingSetpoint;
    private final AttributeImpl controlSequenceOfOperation;
    private final AttributeImpl systemMode;

    private final Attribute[] attributes;
    
    public ThermostatCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        
        localTemperature = new AttributeImpl(zbDevice, this, Attributes.LOCAL_TEMPERATURE);
        occupiedCoolingSetpoint = new AttributeImpl(zbDevice, this, Attributes.OCCUPIED_COOLING_SETPOINT);
        occupiedHeatingSetpoint = new AttributeImpl(zbDevice, this, Attributes.OCCUPIED_HEATING_SETPOINT);
        controlSequenceOfOperation = new AttributeImpl(zbDevice, this, Attributes.CONTROL_SEQUENCE_OF_OPERATION);
        systemMode = new AttributeImpl(zbDevice, this, Attributes.SYSTEM_MODE);

        attributes = new AttributeImpl[]{localTemperature,occupiedCoolingSetpoint,occupiedHeatingSetpoint,controlSequenceOfOperation,systemMode};
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
    
	public Attribute getAttributeLocalTemperature() {
		return localTemperature;
	}

	public Attribute getAttributeOccupiedCoolingSetpoint() {
		return occupiedCoolingSetpoint;
	}

	public Attribute getAttributeOccupiedHeatingSetpoint() {
		return occupiedHeatingSetpoint;
	}

	public Attribute getAttributeControlSequenceOfOperation() {
		return controlSequenceOfOperation;
	}

	public Attribute getAttributeSystemMode() {
		return systemMode;
	}

}
