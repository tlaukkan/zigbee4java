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

package org.bubblecloud.zigbee.api.cluster.impl.general.scenes;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.Scenes;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.AddScenePayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.scenes.ExtensionFieldSetAddScene;
import org.bubblecloud.zigbee.api.cluster.impl.core.AbstractCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultSerializer;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class AddSceneCommand extends AbstractCommand {

    private AddScenePayload scenePayload;


    public AddSceneCommand(AddScenePayload scenePayload) {
        super(Scenes.ADD_SCENE);
        this.scenePayload = scenePayload;
    }

    public byte[] getPayload() {
        if (payload == null) {
            int length;
            length = 5 + scenePayload.getSceneName().length();

            for (int i = 0; i < scenePayload.getExtensionFieldSet().length; i++) {
                length = length + scenePayload.getExtensionFieldSet()[i].getLength();
            }
            payload = new byte[length];
            ZBSerializer serializer = new DefaultSerializer(payload, 0);
            serializer.append_short((short) scenePayload.getGroupId());
            serializer.append_byte((byte) scenePayload.getSceneId());
            serializer.append_short((short) scenePayload.getTransitionTime());
            //TODO use the serializer.appendObject(Object, ZigBeeType)
            serializer.appendObject(scenePayload.getSceneName());
            ExtensionFieldSetAddScene[] extensionFielSet = scenePayload.getExtensionFieldSet();

            for (int i = 0; i < extensionFielSet.length; i++) {
                int clusterId = extensionFielSet[i].getClusterId();
                serializer.append_short((short) clusterId);
                serializer.append_byte((byte) extensionFielSet[i].getAttributes(clusterId).length);
                for (int j = 0; j < extensionFielSet[i].getAttributes(clusterId).length; j++) {
                    Attribute attribute = extensionFielSet[i].getAttributes(clusterId)[j];
                    Object value = extensionFielSet[i].getValue(attribute);
                    serializer.append_short((short) attribute.getId());
                    //TODO use the serializer.appendObject(Object, ZigBeeType)
                    serializer.appendObject(value);
                }
            }
        }
        return payload;
    }

}
