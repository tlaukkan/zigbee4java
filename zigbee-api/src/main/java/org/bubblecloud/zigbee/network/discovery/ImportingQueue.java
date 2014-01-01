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

import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class ImportingQueue {

    private static final Logger logger = LoggerFactory.getLogger(ImportingQueue.class);
    private int waitingThread = 0;
    private boolean closing = false;

    public class ZigBeeNodeAddress {

        private final ZToolAddress16 networkAddress;
        private final ZToolAddress64 ieeeAddress;

        public ZigBeeNodeAddress(final ZToolAddress16 networkAddress, final ZToolAddress64 ieeeAddress) {
            this.networkAddress = networkAddress;
            this.ieeeAddress = ieeeAddress;
        }

        public final ZToolAddress16 getNetworkAddress() {
            return networkAddress;
        }

        public final ZToolAddress64 getIEEEAddress() {
            return ieeeAddress;
        }
    }

    private final ArrayList<ZigBeeNodeAddress> addresses = new ArrayList<ZigBeeNodeAddress>();

    public void clear() {
        synchronized (addresses) {
            if (closing) return;
            addresses.clear();
        }
    }

    public boolean isEmpty() {
        synchronized (addresses) {
            return addresses.size() == 0;
        }
    }

    public int size() {
        synchronized (addresses) {
            return addresses.size();
        }
    }

    public void push(ZToolAddress16 nwkAddress, ZToolAddress64 ieeeAddress) {
        ZigBeeNodeAddress inserting = new ZigBeeNodeAddress(nwkAddress, ieeeAddress);
        logger.trace("Adding {} ({})", nwkAddress, ieeeAddress);
        synchronized (addresses) {
            if (closing) return;
            addresses.add(inserting);
            addresses.notify();
        }
        logger.trace("Added {} ({})", nwkAddress, ieeeAddress);
    }

    public ZigBeeNodeAddress pop() {
        ZigBeeNodeAddress result = null;
        logger.trace("Removing element");
        synchronized (addresses) {
            if (closing) return null;
            waitingThread++;
            while (addresses.isEmpty()) {
                try {
                    addresses.wait();
                } catch (InterruptedException ignored) {
                }
            }
            waitingThread--;
            result = addresses.remove(addresses.size() - 1);
        }
        if (result != null) {
            logger.trace("Removed {} {}", result.networkAddress, result.ieeeAddress);
        } else {
            logger.trace("Removed null value from the queue it means that queue is closing down");
        }
        return result;
    }

    public void close() {
        do {
            synchronized (addresses) {
                if (waitingThread <= 0) return;
                closing = true;
                addresses.add(null);
                addresses.notify();
            }
            Thread.yield();
        } while (true);
    }
}