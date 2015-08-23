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

import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;

/**
 * Attributes for determining detailed information about a device's power source(s),
 * and for configuring under/over voltage alarms.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 *
 */
public interface PowerConfiguration extends Cluster {

	public Attribute getMainsVoltage();
	public Attribute getMainsFrequency();
	
	public Attribute getMainsAlarmMask();
	public Attribute getMainsVoltageMinThreshold();
	public Attribute getMainsVoltageMaxThreshold();
	public Attribute getMainsDwellTripPoint();
	
	public Attribute getBatteryVoltage();
	
	public Attribute getBatteryManufacturer();
	public Attribute getBatterySize();
	public Attribute getBatteryAHrRating();
	public Attribute getBatteryQuantity();
	public Attribute getBatteryRatedVoltage();
	public Attribute getBatteryAlarmMask();
	public Attribute getBatteryVoltageMinThreshold();
}
