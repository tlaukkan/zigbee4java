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

package org.bubblecloud.zigbee.api.cluster.impl.global.reporting;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.AttributeReportingConfigurationRecord;
import org.bubblecloud.zigbee.api.cluster.impl.core.AbstractCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ConfigureReportingCommand extends AbstractCommand {

    private static final Logger logger = LoggerFactory.getLogger(ConfigureReportingCommand.class);

    private static byte ID = 0x06;

    private AttributeReportingConfigurationRecord[] attributerecord;

    public ConfigureReportingCommand(AttributeReportingConfigurationRecord[] attributerecord) {
        super(ID, false);
        this.attributerecord = attributerecord;
    }

    public byte[] getPayload() {
        if (payload == null) {
            logger.debug("Creating Payload for command");
            int length = 0;
            for (int i = 0; i < attributerecord.length; i++) {
                if (attributerecord[i].getDiretion() == 1) {
                    //CASE OF ATTRIBUTE CONFIGURATION SENT TO CLIENT
                    //Size of: Direction + Attribute Id + Timeout
                    length += 1 + 2 + 2;
                } else if (attributerecord[i].getAttributeDataType().isAnalog() == false) {
                    //CASE OF ATTRIBUTE CONFIGURATION SENT TO SERVER OF A DISCRETE ATTRIBUTE
                    //Size of: Direction + Attribute Id + Data Type + Minimum + Maxium
                    length += 1 + 2 + 1 + 2 + 2;
                } else {
                    //CASE OF ATTRIBUTE CONFIGURATION SENT TO SERVER OF A ANALOG ATTRIBUTE
                    //Size of: Direction + Attribute Id + Data Type + Minimum + Maxium + Change
                    final int valueSize = attributerecord[i].getAttributeDataType().getLength();
                    length += 1 + 2 + 1 + 2 + 2 + valueSize;
                }
            }
            payload = new byte[length];
            ZBSerializer serializer = new DefaultSerializer(payload, 0);

            for (int i = 0; i < attributerecord.length; i++) {
                if (attributerecord[i].getDiretion() == 1) {
                    //CASE OF ATTRIBUTE CONFIGURATION SENT TO CLIENT
                    //Size of: Direction + Attribute Id + Timeout
                    serializer.append_byte((byte) attributerecord[i].getDiretion());
                    serializer.append_short((short) attributerecord[i].getAttributeId());
                    serializer.append_short((short) attributerecord[i].getTimeoutPeriod());
                } else if (attributerecord[i].getAttributeDataType().isAnalog() == false) {
                    //CASE OF ATTRIBUTE CONFIGURATION SENT TO SERVER OF A DISCRETE ATTRIBUTE
                    //Size of: Direction + Attribute Id + Data Type + Minimum + Maxium
                    serializer.append_byte((byte) attributerecord[i].getDiretion());
                    serializer.append_short((short) attributerecord[i].getAttributeId());
                    serializer.append_byte((byte) attributerecord[i].getAttributeDataType().getId());
                    serializer.append_short((short) attributerecord[i].getMinimumReportingInterval());
                    serializer.append_short((short) attributerecord[i].getMaximumReportinInterval());
                } else {
                    //CASE OF ATTRIBUTE CONFIGURATION SENT TO SERVER OF A ANALOG ATTRIBUTE
                    //Size of: Direction + Attribute Id + Data Type + Minimum + Maxium + Change
                    serializer.append_byte((byte) attributerecord[i].getDiretion());
                    serializer.append_short((short) attributerecord[i].getAttributeId());
                    final ZigBeeType type = attributerecord[i].getAttributeDataType();
                    serializer.append_byte((byte) type.getId());
                    serializer.append_short((short) attributerecord[i].getMinimumReportingInterval());
                    serializer.append_short((short) attributerecord[i].getMaximumReportinInterval());
                    serializer.appendZigBeeType(attributerecord[i].getReportableChange(), type);
                }
            }
        }
        return payload;
    }

}
