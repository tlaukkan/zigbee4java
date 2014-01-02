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

package org.bubblecloud.zigbee.api.cluster.general;

import org.bubblecloud.zigbee.api.ZigbeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.general.event.PresentValueListener;

/**
 * 
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision:  $ ($LastChangedDate: $)
 *
 */

public interface AnalogInput extends Cluster{

	
   
    public String getDescription() throws ZigbeeDeviceException;
    public Float getMaxPresentValue() throws ZigbeeDeviceException;
    public Float getMinPresentValue() throws ZigbeeDeviceException;
    public boolean getOutOfService() throws ZigbeeDeviceException;
    public Float getPresentValue() throws ZigbeeDeviceException;
    public int getReliability() throws ZigbeeDeviceException;
    public float getResolution() throws ZigbeeDeviceException;
    public int getStatusFlags() throws ZigbeeDeviceException;
    public int getEngineeringUnits() throws ZigbeeDeviceException;
    public long getApplicationType() throws ZigbeeDeviceException;

    /**
	 * 
	 * @param listener The {@link PresentValueListener} to subscribe for events
	 * @since 0.2.0
	 */
	public boolean subscribe(PresentValueListener listener);
	
	/**
	 * 
	 * @param listener The {@link PresentValueListener} to unsubscribe
	 * @since 0.2.0
	 */
	public boolean unsubscribe(PresentValueListener listener);
	
}

