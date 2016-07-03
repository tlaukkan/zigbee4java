/*
   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
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
import org.bubblecloud.zigbee.api.cluster.impl.api.general.BinaryInput;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;

/**
 * @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 */
public class BinaryInputCluster extends ZCLClusterBase implements BinaryInput {

    private final Attribute presentValue;
    private final Attribute outOfService;
    private final Attribute statusFlags;

    private final Attribute[] attributes;

    private final Attribute applicationType;
    private final Attribute reliability;
    private final Attribute polarity;
    private final Attribute inactiveText;
    private final Attribute description;
    private final Attribute activeText;

    public BinaryInputCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        presentValue = new AttributeImpl(zbDevice, this, Attributes.PRESENT_VALUE);
        outOfService = new AttributeImpl(zbDevice, this, Attributes.OUT_OF_SERVICE);
        statusFlags = new AttributeImpl(zbDevice, this, Attributes.STATUS_FLAGS);

        applicationType = new AttributeImpl(zbDevice, this, Attributes.APPLICATION_TYPE);
        reliability = new AttributeImpl(zbDevice, this, Attributes.RELIABILITY);
        polarity = new AttributeImpl(zbDevice, this, Attributes.POLARITY);
        inactiveText = new AttributeImpl(zbDevice, this, Attributes.INACTIVE_TEXT);
        description = new AttributeImpl(zbDevice, this, Attributes.DESCRIPTION);
        activeText = new AttributeImpl(zbDevice, this, Attributes.ACTIVE_TEXT);

        attributes = new Attribute[]{
                presentValue, outOfService, statusFlags, applicationType, reliability, polarity, inactiveText,
                description, activeText
        };
    }

    @Override
    public short getId() {
        return BinaryInput.ID;
    }

    @Override
    public String getName() {
        return BinaryInput.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }

    public Attribute getAttributePresentValue() {
        return presentValue;
    }

    public Attribute getAttributeOutOfService() {
        return outOfService;
    }

    public Attribute getAttributeStatusFlags() {
        return statusFlags;
    }

    public Attribute getAttributeDescription() {
        return description;
    }

    public Attribute getAttributeInactiveText() {
        return inactiveText;
    }

    public Attribute getAttributePolarity() {
        return polarity;
    }

    public Attribute getAttributeReliability() {
        return reliability;
    }

    public Attribute getAttributeApplicationType() {
        return applicationType;
    }

    public Attribute getAttributeActiveText() {
        return activeText;
    }
}