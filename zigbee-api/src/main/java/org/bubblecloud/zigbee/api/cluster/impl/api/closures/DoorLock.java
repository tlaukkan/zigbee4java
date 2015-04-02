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

package org.bubblecloud.zigbee.api.cluster.impl.api.closures;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLCluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.security.DoorLockResponse;


/**
 * This class represent the <b>Door Lock</b> Cluster as defined by the document:<br>
 * <i>ZigBee Cluster Library</i> public release version 075123r01ZB
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 18:00:05 +0200 (mar, 06 ago 2013) $)
 * @since 0.8.0
 */

public interface DoorLock extends ZCLCluster {

   public static final short  ID = 0x101;
   static final String NAME = "Door Lock";
   static final String DESCRIPTION = "Attributes and commands for controlling smart door.";
	
   static final byte LOCK_ID = 0x0;
   static final byte UNLOCK_ID = 0x1;
	
   public DoorLockResponse lock() throws ZigBeeClusterException;
   public DoorLockResponse lock(String pinCode) throws ZigBeeClusterException;
	
   public Attribute getAttributeDescription();
   public Attribute getAttributeLockState();
}
