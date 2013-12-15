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

package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOff;
import it.cnr.isti.zigbee.ha.cluster.glue.general.event.OnOffListener;
import it.cnr.isti.zigbee.ha.cluster.impl.event.OnOffBridgeListeners;
import it.cnr.isti.zigbee.ha.driver.core.ReportingConfiguration;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.impl.general.OnOffCluster;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 *
 */
public class OnOffImpl implements OnOff {

    private final Attribute onOff;
    private final OnOffCluster onOffCluster;
    private OnOffBridgeListeners eventBridge;

    public OnOffImpl(ZigBeeDevice zbDevice){
        onOffCluster = new OnOffCluster(zbDevice);
        onOff = onOffCluster.getAttributeOnOff();
        eventBridge = new OnOffBridgeListeners(new ReportingConfiguration(), onOff, this);
    }

    public boolean getOnOff() throws ZigBeeHAException {
        try {
            Boolean value = (Boolean) onOff.getValue();
            return value.booleanValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public boolean subscribe(OnOffListener listener) {
        return eventBridge.subscribe(listener);
    }

    public boolean unsubscribe(OnOffListener listener) {
        return eventBridge.unsubscribe(listener);
    }

    public void off() throws ZigBeeHAException {
        try {
            DefaultResponse response = onOffCluster.off();
            if (response.getStatus() != Status.SUCCESS)
                throw new ZigBeeHAException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public void on() throws ZigBeeHAException {
        try {
            DefaultResponse response = onOffCluster.on();
            if (response.getStatus() != Status.SUCCESS)
                throw new ZigBeeHAException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public void toggle() throws ZigBeeHAException {
        try {
            DefaultResponse response = onOffCluster.toggle();
            if (response.getStatus() != Status.SUCCESS)
                throw new ZigBeeHAException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

    public Subscription[] getActiveSubscriptions() {
        return onOffCluster.getActiveSubscriptions();
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
            if( attributes[i].getId() == id )
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return onOffCluster.getAvailableAttributes();
    }


}
