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
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.Scenes;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.AddScenePayload;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.AddSceneCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.AddSceneResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.GetSceneMembershipCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.GetSceneMembershipResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.RecallSceneCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.RemoveAllScenesCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.RemoveAllScenesResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.RemoveSceneCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.RemoveSceneResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.StoreSceneCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.StoreSceneResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.ViewSceneCommand;
import org.bubblecloud.zigbee.api.cluster.impl.general.scenes.ViewSceneResponseImpl;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class ScenesCluster extends ZCLClusterBase implements Scenes {

    private final AttributeImpl sceneCount;
    private final AttributeImpl currentScene;
    //TODO Implement current group
    //private final AttributeImpl currentGroup;
    private final AttributeImpl sceneValid;
    private final AttributeImpl nameSupport;
    private final AttributeImpl lastConfiguredBy;

    private final Attribute[] attributes;

    public ScenesCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        sceneCount = new AttributeImpl(zbDevice, this, Attributes.SCENE_COUNT);
        currentScene = new AttributeImpl(zbDevice, this, Attributes.CURRENT_SCENE);
        //TODO currentGroup = new AttributeImpl(device,this,AttributeDescriptor.CURRENT_GROUP)
        sceneValid = new AttributeImpl(zbDevice, this, Attributes.SCENE_VALID);
        nameSupport = new AttributeImpl(zbDevice, this, Attributes.NAME_SUPPORT_SCENES);
        lastConfiguredBy = new AttributeImpl(zbDevice, this, Attributes.LAST_CONFIGURED_BY);

        attributes = new AttributeImpl[]{sceneCount, currentScene, //TODO currentGroup,
                sceneValid, nameSupport, lastConfiguredBy};
    }

    @Override
    public short getId() {
        return Scenes.ID;
    }

    @Override
    public String getName() {
        return Scenes.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }

    public Response addScene(AddScenePayload scenepayload) throws ZigBeeClusterException {
        AddSceneCommand addSceneCmd = new AddSceneCommand(scenepayload);
        Response response = invoke(addSceneCmd);
        return new AddSceneResponseImpl(response);
    }

    public Attribute getAttributeCurrentGroup() {
        return null; //TODO return currentGroup;
    }

    public Attribute getAttributeCurrentScene() {
        return currentScene;
    }

    public Attribute getAttributeLastConfiguredBy() {
        return lastConfiguredBy;
    }

    public Attribute getAttributeNameSupport() {
        return nameSupport;
    }

    public Attribute getAttributeSceneCount() {
        return sceneCount;
    }

    public Attribute getAttributeSceneValid() {
        return sceneValid;
    }

    public Response getSceneMembership(int groupId) throws ZigBeeClusterException {
        GetSceneMembershipCommand getSceneMemCmd = new GetSceneMembershipCommand(groupId);
        Response response = invoke(getSceneMemCmd);
        return new GetSceneMembershipResponseImpl(response);
    }

    public void recallScene(int groupId, short sceneId) throws ZigBeeClusterException {
        RecallSceneCommand recallSceneCmd = new RecallSceneCommand(groupId, sceneId);
        invoke(recallSceneCmd);
    }

    public Response removeAllScenes(int groupId) throws ZigBeeClusterException {
        RemoveAllScenesCommand removeAllScenes = new RemoveAllScenesCommand(groupId);
        Response response = invoke(removeAllScenes);
        return new RemoveAllScenesResponseImpl(response);

    }

    public Response removeScene(int groupId, short sceneId) throws ZigBeeClusterException {
        RemoveSceneCommand removeSceneCmd = new RemoveSceneCommand(groupId, sceneId);
        Response response = invoke(removeSceneCmd);
        return new RemoveSceneResponseImpl(response);
    }

    public Response storeScene(int groupId, short sceneId) throws ZigBeeClusterException {
        StoreSceneCommand storeSceneCmd = new StoreSceneCommand(groupId, sceneId);
        Response response = invoke(storeSceneCmd);
        return new StoreSceneResponseImpl(response);
    }

    public Response viewScene(int groupId, short sceneId) throws ZigBeeClusterException {
        ViewSceneCommand viewSceneCmd = new ViewSceneCommand(groupId, sceneId);
        Response response = invoke(viewSceneCmd);
        return new ViewSceneResponseImpl(response);
    }

}
