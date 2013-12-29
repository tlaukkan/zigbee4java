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

package org.bubblecloud.zigbee.api.cluster.impl.general.level_control;

import org.bubblecloud.zigbee.api.cluster.impl.api.general.LevelControl;
import org.bubblecloud.zigbee.api.cluster.impl.core.AbstractCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.ByteArrayOutputStreamSerializer;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class StepCommand extends AbstractCommand {

    private byte mode;
    private short step;
    private int time;

    public StepCommand(byte mode, short step, int time) {
        this(mode, step, time, false);
    }

    public StepCommand(byte mode, short step, int time, boolean hasOnOff) {
        super(LevelControl.STEP_ID);
        if (hasOnOff) {
            setId(LevelControl.STEP_WITH_ONOFF_ID);
        }
        this.mode = mode;
        this.step = step;
        this.time = time;
    }

    public byte[] getPayload() {
        if (payload == null) {
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_byte((byte) mode);
            serializer.append_byte((byte) step);
            serializer.append_short((short) time);
            payload = serializer.getPayload();
        }
        return payload;
    }

}
