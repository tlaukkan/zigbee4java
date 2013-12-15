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

package org.bubblecloud.zigbee.proxy.device.impl;

import org.bubblecloud.zigbee.ZigbeeContext;
import org.bubblecloud.zigbee.network.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.DeviceProxyBase;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Identify;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.OnOffSwitchConfiguration;
import org.bubblecloud.zigbee.proxy.device.api.generic.OnOffSwitch;
import org.bubblecloud.zigbee.proxy.ZigBeeHAException;
import org.bubblecloud.zigbee.proxy.AbstractDeviceDescription;
import org.bubblecloud.zigbee.proxy.DeviceDescription;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 *
 */
public class OnOffSwitchDeviceProxy extends DeviceProxyBase implements OnOffSwitch {
	
	private OnOffSwitchConfiguration onOffSwitchConfiguration;
	
	public OnOffSwitchDeviceProxy(ZigbeeContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException{
		super(ctx,zbDevice);
	}

	final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

		public int[] getCustomClusters() {
			return OnOffSwitch.CUSTOM;
		}

		public int[] getMandatoryCluster() {
			return OnOffSwitch.MANDATORY;
		}

		public int[] getOptionalCluster() {
			return OnOffSwitch.OPTIONAL;
		}

		public int[] getStandardClusters() {
			return OnOffSwitch.STANDARD;
		}
		
	};
	
	@Override
	public DeviceDescription getDescription() {
		return DEVICE_DESCRIPTOR;
	}

	@Override
	public String getName() {
		return OnOffSwitch.NAME;
	}

	public OnOffSwitchConfiguration getOnOffSwitchConfiguration() {
		return onOffSwitchConfiguration;
	}

	public Identify getIdentify() {
		return identify;
	}

}
