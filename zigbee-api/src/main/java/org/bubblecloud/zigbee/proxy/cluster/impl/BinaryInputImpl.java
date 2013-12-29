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

package org.bubblecloud.zigbee.proxy.cluster.impl;

import org.bubblecloud.zigbee.network.ZigbeeEndpoint;
import org.bubblecloud.zigbee.proxy.ZigbeeDeviceException;
import org.bubblecloud.zigbee.proxy.cluster.general.BinaryInput;
import org.bubblecloud.zigbee.proxy.cluster.general.event.PresentValueListener;
import org.bubblecloud.zigbee.proxy.cluster.impl.event.PresentValueBridgeListeners;
import org.bubblecloud.zigbee.proxy.ReportingConfiguration;
import org.bubblecloud.zigbee.proxy.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.proxy.cluster.impl.api.core.Subscription;
import org.bubblecloud.zigbee.proxy.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.proxy.cluster.impl.general.BinaryInputCluster;


/**
 * @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.0
 */
public class BinaryInputImpl implements BinaryInput {

    private final Attribute presentValue;
    private final Attribute outOfService;
    private final Attribute statusFlags;
    private final BinaryInputCluster binaryInput;
    private PresentValueBridgeListeners eventBridge;

    public BinaryInputImpl(ZigbeeEndpoint zbDevice) {
        binaryInput = new BinaryInputCluster(zbDevice);
        presentValue = binaryInput.getAttributePresentValue();
        outOfService = binaryInput.getAttributeOutOfService();
        statusFlags = binaryInput.getAttributeStatusFlags();
        eventBridge = new PresentValueBridgeListeners(new ReportingConfiguration(), presentValue, this);
    }

    public boolean subscribe(PresentValueListener listener) {
        return eventBridge.subscribe(listener);
    }

    public boolean unsubscribe(PresentValueListener listener) {
        return eventBridge.unsubscribe(listener);
    }

    public Subscription[] getActiveSubscriptions() {
        return binaryInput.getActiveSubscriptions();
    }

    public int getId() {
        return binaryInput.getId();
    }

    public String getName() {
        return binaryInput.getName();
    }

    public Attribute getAttribute(int id) {
        Attribute[] attributes = binaryInput.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getId() == id)
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return binaryInput.getAvailableAttributes();
    }


    public String getActiveText() throws ZigbeeDeviceException {
        try {
            return (String) binaryInput.getAttributeActiveText().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigbeeDeviceException(e);
        }
    }


    public String getDescription() throws ZigbeeDeviceException {
        try {
            return (String) binaryInput.getAttributeDescription().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigbeeDeviceException(e);
        }
    }


    public String getInactiveText() throws ZigbeeDeviceException {
        try {
            return (String) binaryInput.getAttributeInactiveText().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigbeeDeviceException(e);
        }
    }


    public int getPolarity() throws ZigbeeDeviceException {
        try {
            return (Integer) binaryInput.getAttributePolarity().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigbeeDeviceException(e);
        }
    }


    public int getReliability() throws ZigbeeDeviceException {
        try {
            return (Integer) binaryInput.getAttributeReliability().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigbeeDeviceException(e);
        }
    }


    public long getApplicationType() throws ZigbeeDeviceException {
        try {
            return (Long) binaryInput.getAttributeApplicationType().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigbeeDeviceException(e);
        }
    }


    public boolean getOutOfService() throws ZigbeeDeviceException {
        try {
            return (Boolean) binaryInput.getAttributeOutOfService().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigbeeDeviceException(e);
        }
    }


    public boolean getPresentValue() throws ZigbeeDeviceException {
        try {
            return (Boolean) binaryInput.getAttributePresentValue().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigbeeDeviceException(e);
        }
    }


    public int getStatusFlags() throws ZigbeeDeviceException {
        try {
            return (Integer) binaryInput.getAttributeStatusFlags().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigbeeDeviceException(e);
        }
    }

}
