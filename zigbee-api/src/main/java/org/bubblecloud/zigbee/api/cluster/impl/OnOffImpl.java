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

import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.api.cluster.general.event.OnOffListener;
import org.bubblecloud.zigbee.api.cluster.impl.event.OnOffBridgeListeners;
import org.bubblecloud.zigbee.api.ReportingConfiguration;
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.DefaultResponse;
import org.bubblecloud.zigbee.api.cluster.impl.general.OnOffCluster;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class OnOffImpl implements OnOff {

    private final Attribute onOff;
    private final OnOffCluster onOffCluster;
    private OnOffBridgeListeners eventBridge;

    public OnOffImpl(ZigBeeEndpoint zbDevice) {
        onOffCluster = new OnOffCluster(zbDevice);
        onOff = onOffCluster.getAttributeOnOff();
        eventBridge = new OnOffBridgeListeners(new ReportingConfiguration(), onOff, this);
    }

    public boolean getOnOff() throws ZigBeeDeviceException {
        try {
            Boolean value = (Boolean) onOff.getValue();
            return value.booleanValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public boolean subscribe(OnOffListener listener) {
        return eventBridge.subscribe(listener);
    }

    public boolean unsubscribe(OnOffListener listener) {
        return eventBridge.unsubscribe(listener);
    }

    public void off() throws ZigBeeDeviceException {
        try {
            DefaultResponse response = onOffCluster.off();
            if (response.getStatus() != Status.SUCCESS)
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void on() throws ZigBeeDeviceException {
        try {
            DefaultResponse response = onOffCluster.on();
            if (response.getStatus() != Status.SUCCESS)
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void toggle() throws ZigBeeDeviceException {
        try {
            DefaultResponse response = onOffCluster.toggle();
            if (response.getStatus() != Status.SUCCESS)
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Reporter[] getAttributeReporters() {
        return onOffCluster.getAttributeReporters();
    }

    public int getId() {
        return onOffCluster.getId();
    }

    public String getName() {
        return onOffCluster.getName();
    }

    public Attribute getAttribute(int id) {
        Attribute[] attributes = onOffCluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getId() == id)
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return onOffCluster.getAvailableAttributes();
    }


}
