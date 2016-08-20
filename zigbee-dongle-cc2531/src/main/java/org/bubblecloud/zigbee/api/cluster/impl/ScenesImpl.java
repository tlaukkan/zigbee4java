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

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.general.Scenes;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.AddScenePayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.AddSceneResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.GetSceneMembershipResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.RemoveAllScenesResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.RemoveSceneResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.StoreSceneResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.ViewSceneResponse;
import org.bubblecloud.zigbee.api.cluster.impl.general.ScenesCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ScenesImpl implements Scenes {

    private ScenesCluster scenesCluster;
    private Attribute sceneCount;
    private Attribute currentScene;
    private Attribute currentGroup;
    private Attribute sceneValid;
    private Attribute nameSupport;
    private Attribute lastConfiguredBy;

    public ScenesImpl(ZigBeeEndpoint zbDevice) {
        scenesCluster = new ScenesCluster(zbDevice);
        sceneCount = scenesCluster.getAttributeSceneCount();
        currentScene = scenesCluster.getAttributeCurrentScene();
        currentGroup = scenesCluster.getAttributeCurrentGroup();
        sceneValid = scenesCluster.getAttributeSceneValid();
        nameSupport = scenesCluster.getAttributeNameSupport();
        lastConfiguredBy = scenesCluster.getAttributeLastConfiguredBy();

    }

    public AddSceneResponse addScene(AddScenePayload scenepayload) throws ZigBeeDeviceException {
        try {
            AddSceneResponse response = (AddSceneResponse) scenesCluster.addScene(scenepayload);
            return response;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Attribute getCurrentGroup() {
        return currentGroup;
    }

    public Attribute getCurrentScene() {
        return currentScene;
    }

    public Attribute getLastConfiguredBy() {
        return lastConfiguredBy;
    }

    public Attribute getNameSupport() {
        return nameSupport;
    }

    public Attribute getSceneCount() {
        return sceneCount;
    }

    public GetSceneMembershipResponse getSceneMembership(int groupId) throws ZigBeeDeviceException {
        try {
            GetSceneMembershipResponse response = (GetSceneMembershipResponse) scenesCluster.getSceneMembership(groupId);
            return response;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Attribute getSceneValid() {
        return sceneValid;
    }

    public void recallScene(int groupId, short sceneId) throws ZigBeeDeviceException {
        try {
            scenesCluster.recallScene(groupId, sceneId);
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }

    }

    public RemoveAllScenesResponse removeAllScene(int groupId) throws ZigBeeDeviceException {
        try {
            RemoveAllScenesResponse response = (RemoveAllScenesResponse) scenesCluster.removeAllScenes(groupId);
            return response;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public RemoveSceneResponse removeScene(int groupId, short sceneId) throws ZigBeeDeviceException {
        try {
            RemoveSceneResponse response = (RemoveSceneResponse) scenesCluster.removeScene(groupId, sceneId);
            return response;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public StoreSceneResponse storeScene(int groupId, short sceneId) throws ZigBeeDeviceException {
        try {
            StoreSceneResponse response = (StoreSceneResponse) scenesCluster.storeScene(groupId, sceneId);
            return response;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public ViewSceneResponse viewScene(int groupId, short sceneId) throws ZigBeeDeviceException {
        try {
            ViewSceneResponse response = (ViewSceneResponse) scenesCluster.viewScene(groupId, sceneId);
            return response;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Reporter[] getAttributeReporters() {
        return scenesCluster.getAttributeReporters();
    }

    public int getId() {
        return scenesCluster.getId();
    }

    public String getName() {
        return scenesCluster.getName();
    }

    public Attribute getAttribute(int id) {
        Attribute[] attributes = scenesCluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getId() == id)
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return scenesCluster.getAvailableAttributes();
    }

}
