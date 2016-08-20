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
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.general.AnalogInput;
import org.bubblecloud.zigbee.api.cluster.general.event.PresentValueListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.event.PresentValueBridgeListeners;
import org.bubblecloud.zigbee.api.cluster.impl.general.AnalogInputCluster;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;

/**
 * 
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision:  $ ($LastChangedDate: $)
 *
 */


public class AnalogInputImpl implements AnalogInput {
    private final Attribute presentValue;
    private final Attribute outOfService;
    private final Attribute statusFlags;
	private AnalogInputCluster analogInputCluster;
	private PresentValueBridgeListeners eventBridge;

    public AnalogInputImpl(ZigBeeEndpoint zbDevice){
    	analogInputCluster = new AnalogInputCluster(zbDevice);
        presentValue = analogInputCluster.getAttributePresentValue();
        outOfService = analogInputCluster.getAttributeOutOfService();
        statusFlags = analogInputCluster.getAttributeStatusFlags();
        eventBridge = new PresentValueBridgeListeners(new ReportingConfiguration(), presentValue, this);
    }
	

    public boolean subscribe(PresentValueListener listener) {
        return eventBridge.subscribe(listener);
    }

    public boolean unsubscribe(PresentValueListener listener) {
        return eventBridge.unsubscribe(listener);
    }

    public Reporter[] getAttributeReporters() {
		return analogInputCluster.getAttributeReporters();
	}

	public int getId() {
		return analogInputCluster.getId();
	}

	public String getName() {
		return analogInputCluster.getName();
	}

	
	
	public Attribute getAttribute(int id) {		
		Attribute[] attributes = analogInputCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public Attribute[] getAttributes() {
		return analogInputCluster.getAvailableAttributes();
	}


	public String getDescription() throws ZigBeeDeviceException {
		 try {
	            return (String) analogInputCluster.getAttributeDescription().getValue();
	        } catch (ZigBeeClusterException e) {
	            throw new ZigBeeDeviceException(e);
	        }
	}
	 public int getReliability() throws ZigBeeDeviceException {
	        try {
	            return (Integer) analogInputCluster.getAttributeReliability().getValue();
	        } catch (ZigBeeClusterException e) {
	            throw new ZigBeeDeviceException(e);
	        }
	    }


	    public long getApplicationType() throws ZigBeeDeviceException {
	        try {
	            return (Long) analogInputCluster.getAttributeApplicationType().getValue();
	        } catch (ZigBeeClusterException e) {
	            throw new ZigBeeDeviceException(e);
	        }
	    }


	    public boolean getOutOfService() throws ZigBeeDeviceException {
	        try {
	            return (Boolean) analogInputCluster.getAttributeOutOfService().getValue();
	        } catch (ZigBeeClusterException e) {
	            throw new ZigBeeDeviceException(e);
	        }
	    }


	    public Float getPresentValue() throws ZigBeeDeviceException {
	        try {
	            return (Float) analogInputCluster.getAttributePresentValue().getValue();
	        } catch (ZigBeeClusterException e) {
	            throw new ZigBeeDeviceException(e);
	        }
	    }


	    public int getStatusFlags() throws ZigBeeDeviceException {
	        try {
	            return (Integer) analogInputCluster.getAttributeStatusFlags().getValue();
	        } catch (ZigBeeClusterException e) {
	            throw new ZigBeeDeviceException(e);
	        }
	    }


		public Float getMaxPresentValue() throws ZigBeeDeviceException {
			 try {
		            return (Float) analogInputCluster.getAttributeMaxPresentValue().getValue();
		        } catch (ZigBeeClusterException e) {
		            throw new ZigBeeDeviceException(e);
		        }
		}


		public Float getMinPresentValue() throws ZigBeeDeviceException {
			 try {
		            return (Float) analogInputCluster.getAttributeMinPresentValue().getValue();
		        } catch (ZigBeeClusterException e) {
		            throw new ZigBeeDeviceException(e);
		        }
		}


		public float getResolution() throws ZigBeeDeviceException {
			// TODO Auto-generated method stub
			return 0;
		}


		public int getEngineeringUnits() throws ZigBeeDeviceException {
			// TODO Auto-generated method stub
			return 0;
		}


	



	
}
