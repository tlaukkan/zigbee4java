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

package org.bubblecloud.zigbee.api.cluster.impl.core;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.*;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.ClusterMessageImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public abstract class ZCLClusterBase implements ZCLCluster {

    final public static Logger logger = LoggerFactory.getLogger(ZCLClusterBase.class);

    private ZigBeeEndpoint zbDevice;
    private boolean isDefaultResponseEnabled;
    private HashMap<Integer, Attribute> attributes;

    public ZCLClusterBase(ZigBeeEndpoint zbDevice) {
        this.zbDevice = zbDevice;
    }

    public abstract short getId();

    public abstract String getName();

    public abstract Attribute[] getStandardAttributes();

    protected ZigBeeEndpoint getZigBeeEndpoint() {
        return zbDevice;
    }

    public void enableDefaultResponse() {
        isDefaultResponseEnabled = true;
    }

    public boolean isDefaultResponseEnabled() {
        return isDefaultResponseEnabled;
    }

    public Attribute getAttribute(int id) {
        if (attributes == null) {
            attributes = new HashMap<Integer, Attribute>();
            Attribute[] list = getAvailableAttributes();
            for (int i = 0; i < list.length; i++) {
                attributes.put(list[i].getId(), list[i]);
            }
        }

        return attributes.get(id);
    }

    public Attribute[] getAvailableAttributes() {
        //TODO use Discovery Attribute command to find the real attribute
        return getStandardAttributes();
    }

    public int getManufacturerId() {
        return -1;
    }

    public Response invoke(Command cmd) throws ZigBeeClusterException {
        return invoke(cmd, !isDefaultResponseEnabled);
    }

    public Response invoke(Command cmd, boolean suppressResponse) throws ZigBeeClusterException {
        ZCLFrame inFrame = new ZCLFrame(cmd, isDefaultResponseEnabled);
        ClusterMessage input = new ClusterMessageImpl(getId(), inFrame);
        logger.info("Sending {} command to {} (#{}).", cmd.getClass().getSimpleName(), zbDevice.getEndpointId(),
                zbDevice.getNetworkAddress());
        if (suppressResponse) {
            try {
                zbDevice.send(input);
                return null;
            } catch (ZigBeeNetworkManagerException e) {
                throw new ZigBeeClusterException(e);
            }
        } else {
            ClusterMessage clusterMessage;
            try {
                clusterMessage = zbDevice.invoke(input);
                Response response = new ResponseImpl(clusterMessage, getId());
                logger.debug("Received response {} to request {}", response, inFrame);
                return response;
            } catch (ZigBeeNetworkManagerException e) {
                throw new ZigBeeClusterException(e);
            }
        }
    }

    public Reporter[] getAttributeReporters() {
        final ArrayList<Reporter> actives = new ArrayList<Reporter>();
        final Attribute[] attributes = getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            final Reporter reporter = attributes[i].getReporter();
            if (reporter != null) {
                actives.add(reporter);
            }
        }
        return actives.toArray(new Reporter[]{});
    }


}
