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

package org.bubblecloud.zigbee.api.cluster.impl.api.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLCluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.AddScenePayload;

/**
 * This class represent the <b>Scenes</b> Cluster as defined by the document:
 * <i>ZigBee Cluster Library</i> public release version 075123r01ZB
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface Scenes extends ZCLCluster {

    static final short ID = 0x0005;
    static final String NAME = "Scenes";
    static final String DESCRIPTION = "Attributes and commands for scene configuration and manipulation.";

    static final byte ADD_SCENE = 0x00;
    static final byte VIEW_SCENE = 0x01;
    static final byte REMOVE_SCENE = 0x02;
    static final byte REMOVE_ALL_SCENES = 0x03;
    static final byte STORE_SCENE = 0x04;
    static final byte RECALL_SCENE = 0x05;
    static final byte GET_SCENE_MEMBERSHIP = 0x06;

    public Attribute getAttributeSceneCount();

    public Attribute getAttributeCurrentScene();

    public Attribute getAttributeCurrentGroup();

    public Attribute getAttributeSceneValid();

    public Attribute getAttributeNameSupport();

    public Attribute getAttributeLastConfiguredBy();

    public Response addScene(AddScenePayload scenepayload) throws ZigBeeClusterException; //TODO Create proper class

    public Response viewScene(int groupId, short sceneId) throws ZigBeeClusterException;

    public Response removeScene(int groupId, short sceneId) throws ZigBeeClusterException;

    public Response removeAllScenes(int groupId) throws ZigBeeClusterException;

    public Response storeScene(int groupId, short sceneId) throws ZigBeeClusterException;

    public void recallScene(int groupId, short sceneId) throws ZigBeeClusterException;

    public Response getSceneMembership(int groupId) throws ZigBeeClusterException;

}
