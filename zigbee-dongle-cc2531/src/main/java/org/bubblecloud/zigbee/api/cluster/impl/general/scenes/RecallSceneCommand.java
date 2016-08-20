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

import org.bubblecloud.zigbee.v3.serialization.ZBSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.Scenes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AbstractCommand;
import org.bubblecloud.zigbee.v3.serialization.DefaultSerializer;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class RecallSceneCommand extends AbstractCommand {

    private int groupId;
    private short sceneId;


    public RecallSceneCommand(int groupId, short sceneId) {
        super(Scenes.RECALL_SCENE);
        this.groupId = groupId;
        this.sceneId = sceneId;
    }

    public byte[] getPayload() {
        if (payload == null) {
            payload = new byte[3];
            ZBSerializer serializer = new DefaultSerializer(payload, 0);
            serializer.append_short((short) groupId);
            serializer.append_byte((byte) sceneId);
        }
        return payload;
    }
}
