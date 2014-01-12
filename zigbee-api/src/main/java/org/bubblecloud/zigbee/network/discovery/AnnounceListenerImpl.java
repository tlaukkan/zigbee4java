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

import org.bubblecloud.zigbee.network.ZigBeeNetworkManager;
import org.bubblecloud.zigbee.network.AnnounceListener;
import org.bubblecloud.zigbee.network.model.IEEEAddress;
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;
import org.bubblecloud.zigbee.network.impl.ApplicationFrameworkLayer;
import org.bubblecloud.zigbee.network.impl.ZigBeeNodeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>NOTE:</b>This class doesn't implement a real {@link Thread}, anyway<br>
 * because it is a {@link org.bubblecloud.zigbee.network.AnnounceListener} a different thread then the application will call
 * the {@link #announce(org.bubblecloud.zigbee.network.packet.ZToolAddress16, org.bubblecloud.zigbee.network.packet.ZToolAddress64, org.bubblecloud.zigbee.network.packet.ZToolAddress16, int)} method.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class AnnounceListenerImpl implements AnnounceListener {

    private final static Logger logger = LoggerFactory.getLogger(AnnounceListenerImpl.class);

    private final ImportingQueue queue;

    private final ZigBeeNetworkManager zigbeeNetworkManager;

    /**
     * Created the {@link AnnounceListenerImpl} object and register itself to the {@link org.bubblecloud.zigbee.network.ZigBeeNetworkManager}<br>
     * as {@link ImportingQueue}
     *
     * @param queue  the {@link ImportingQueue} used to add the discovered endpoints
     * @param driver the {@link org.bubblecloud.zigbee.network.ZigBeeNetworkManager} to use for subscription
     */
    public AnnounceListenerImpl(final ImportingQueue queue, final ZigBeeNetworkManager driver) {
        this.queue = queue;
        this.zigbeeNetworkManager = driver;
    }

    public void announce(final ZToolAddress16 senderAddress,
                         final ZToolAddress64 ieeeAddress, final ZToolAddress16 destinationAddress,
                         final int capabilitiesBitmask) {

        logger.info("Device announced Network Address: {} IEEE Address: {}", senderAddress.get16BitValue(),
                IEEEAddress.toColonNotation(ieeeAddress.getLong()));
        queue.push(senderAddress, ieeeAddress);
        final Thread notifyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationFrameworkLayer.getAFLayer(zigbeeNetworkManager).getZigBeeNetwork().notifyNodeAnnounced(
                        new ZigBeeNodeImpl(senderAddress.get16BitValue(), ieeeAddress,
                                (short) zigbeeNetworkManager.getCurrentPanId()));
            }
        });
    }

}