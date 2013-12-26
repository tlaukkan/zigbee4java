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

package org.bubblecloud.zigbee.network.discovery;

import org.bubblecloud.zigbee.network.ZigbeeNetworkManager;
import org.bubblecloud.zigbee.network.AnnounceListener;
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;
import org.bubblecloud.zigbee.network.ZigBeeNode;
import org.bubblecloud.zigbee.network.impl.ApplicationFrameworkLayer;
import org.bubblecloud.zigbee.network.impl.ZigBeeNodeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>NOTE:</b>This class doesn't implement a real {@link Thread}, anyway<br>
 * because it is a {@link org.bubblecloud.zigbee.network.AnnounceListener} a different thread then the application will call
 * the {@link #notify(org.bubblecloud.zigbee.network.packet.ZToolAddress16, org.bubblecloud.zigbee.network.packet.ZToolAddress64, org.bubblecloud.zigbee.network.packet.ZToolAddress16, int)} method.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class AnnounceListenerThread implements AnnounceListener {

    private final static Logger logger = LoggerFactory.getLogger(AnnounceListenerThread.class);

    private final ImportingQueue queue;

    private final ZigbeeNetworkManager driver;

    /**
     * Created the {@link AnnounceListenerThread} object and register itself to the {@link org.bubblecloud.zigbee.network.ZigbeeNetworkManager}<br>
     * as {@link ImportingQueue}
     *
     * @param queue  the {@link ImportingQueue} used to add the discovered devices
     * @param driver the {@link org.bubblecloud.zigbee.network.ZigbeeNetworkManager} to use for subscription
     */
    public AnnounceListenerThread(final ImportingQueue queue, final ZigbeeNetworkManager driver) {
        this.queue = queue;
        this.driver = driver;
    }

    public void notify(ZToolAddress16 senderAddress,
                       ZToolAddress64 ieeeAddress, ZToolAddress16 destinationAddress,
                       int capabilitiesBitmask) {

        logger.info("Received an ANNOUNCE from {} {}", senderAddress, ieeeAddress);
        queue.push(senderAddress, ieeeAddress);
        ApplicationFrameworkLayer.getAFLayer(driver).getZigBeeNetwork().notifyNodeAnnounced(
                new ZigBeeNodeImpl(senderAddress.get16BitValue(), ieeeAddress,
                        (short) driver.getCurrentPanId()));
    }

    private void annuncedNode(ZigBeeNode node) {
        ApplicationFrameworkLayer.getAFLayer(driver).getZigBeeNetwork().notifyNodeAnnounced(node);
    }

}