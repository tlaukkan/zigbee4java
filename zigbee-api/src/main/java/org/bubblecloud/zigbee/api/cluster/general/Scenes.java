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

import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.*;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 *
 */
public interface Scenes extends Cluster{

	public AddSceneResponse addScene(AddScenePayload scenePayload) throws ZigBeeDeviceException;
	
	public GetSceneMembershipResponse getSceneMembership(int groupId) throws ZigBeeDeviceException;
	
	public void recallScene(int groupId, short sceneId) throws ZigBeeDeviceException;
	
	public RemoveAllScenesResponse removeAllScene(int groupId) throws ZigBeeDeviceException;
	
	public RemoveSceneResponse removeScene(int groupId, short sceneId) throws ZigBeeDeviceException;

	public StoreSceneResponse storeScene(int groupId, short sceneId) throws ZigBeeDeviceException;

	public ViewSceneResponse viewScene(int groupId, short sceneId) throws ZigBeeDeviceException;
	
	public Attribute getSceneCount();
	
	public Attribute getCurrentScene();
	
	public Attribute getCurrentGroup();
	
	public Attribute getSceneValid();
	
	public Attribute getNameSupport();
	
	public Attribute getLastConfiguredBy();
}
