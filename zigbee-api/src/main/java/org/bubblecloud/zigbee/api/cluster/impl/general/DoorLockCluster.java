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

import org.bubblecloud.zigbee.api.cluster.impl.api.closures.DoorLock;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;


public class DoorLockCluster extends ZCLClusterBase implements DoorLock {
	
	

	private static AttributeImpl description;
	private final Attribute[] attributes;
	
	public DoorLockCluster(ZigBeeEndpoint zbDevice){
		super(zbDevice);
		
		
		
		description = new AttributeImpl(zbDevice,this, Attributes.DESCRIPTION);

		attributes = new AttributeImpl[]{description};
	}



	@Override
	public short getId() {
		return DoorLock.ID;
	}

	@Override
	public String getName() {
		return DoorLock.NAME;
	}

	@Override
	public Attribute[] getStandardAttributes() {
		return attributes;
	}



	public Attribute getAttributeDescription() {
		return description;
	}

	
	
	

}
