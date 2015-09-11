/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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
package org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_wd;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASWD;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_wd.SquawkPayload;
import org.bubblecloud.zigbee.api.cluster.impl.core.AbstractCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.ByteArrayOutputStreamSerializer;

public class SquawkCommand extends AbstractCommand {

    private SquawkPayload squawkPayload;

    public SquawkCommand(SquawkPayload payload) {
        super(IASWD.SQUAWK);
        this.squawkPayload = payload;
    }

    public byte[] getPayload() {
        if (payload == null) {
            ZBSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_byte((byte)((squawkPayload.getSquawkLevel() << 6) |
                    (squawkPayload.getStrobe() << 4) |
                    squawkPayload.getSquawkMode()));
            payload = serializer.getPayload();
        }
        return payload;
    }
}
