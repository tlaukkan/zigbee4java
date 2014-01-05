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
import org.bubblecloud.zigbee.network.impl.ApplicationFrameworkLayer;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;

/**
 * This class is tracks the {@link org.bubblecloud.zigbee.network.ZigBeeNetworkManager} service avaialable on the OSGi framework<br>
 * and it creates all the resources required by this implementation of the <i>ZigBee Base Driver</i>
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigBeeDiscoveryManager {

    private final static Logger logger = LoggerFactory.getLogger(ZigBeeDiscoveryManager.class);

    private ZigBeeNetworkManager managementInterface;
    private AnnounceListenerImpl annunceListener;

    private AssociationNetworkBrowser networkBrowser = null;
    private LinkQualityIndicatorNetworkBrowser networkLQIBrowser = null;

    private EndpointBuilder endpointBuilder;
    private final ImportingQueue importingQueue;

    private EnumSet<DiscoveryMode> enabledDiscoveries;

    public ZigBeeDiscoveryManager(ZigBeeNetworkManager simpleDriver, final EnumSet<DiscoveryMode> enabledDiscoveries) {
        importingQueue = new ImportingQueue();
        managementInterface = simpleDriver;
        this.enabledDiscoveries = enabledDiscoveries;
    }

    public void startup() {
        logger.trace("Setting up all the importer data and threads");
        importingQueue.clear();
        ApplicationFrameworkLayer.getAFLayer(managementInterface);

        if (enabledDiscoveries.contains(DiscoveryMode.Announce)) {
            annunceListener = new AnnounceListenerImpl(importingQueue, managementInterface);
            managementInterface.addAnnunceListener(annunceListener);
        } else {
            logger.trace("ANNOUNCE discovery disabled.");
        }

        if (enabledDiscoveries.contains(DiscoveryMode.Addressing)) {
            networkBrowser = new AssociationNetworkBrowser(importingQueue, managementInterface);
            new Thread(networkBrowser, "NetworkBrowser[" + managementInterface + "]").start();
        } else {
            logger.trace("{} discovery disabled.",
                    AssociationNetworkBrowser.class);
        }

        if (enabledDiscoveries.contains(DiscoveryMode.LinkQuality)) {
            networkLQIBrowser = new LinkQualityIndicatorNetworkBrowser(importingQueue, managementInterface);
            new Thread(networkLQIBrowser, "LinkQualityIndicatorNetworkBrowser[" + managementInterface + "]").start();
        } else {
            logger.trace("{} discovery disabled.",
                    LinkQualityIndicatorNetworkBrowser.class);
        }

        endpointBuilder = new EndpointBuilder(importingQueue, managementInterface);
        new Thread(endpointBuilder, "EndpointBuilder[" + managementInterface + "]").start();
    }

    public void shutdown() {
        //logger.info("Driver used left:clean up all the data and closing all the threads");

        managementInterface.removeAnnunceListener(annunceListener);

        if (networkBrowser != null) {
            networkBrowser.end();
            networkBrowser.interrupt();
        }
        if (networkLQIBrowser != null) {
            networkLQIBrowser.end();
            networkLQIBrowser.interrupt();
        }
        if (endpointBuilder != null) {
            endpointBuilder.end();
        }
        importingQueue.close();
    }


    public boolean isInitialNetworkBrowsingComplete() {
        return (networkBrowser == null || networkBrowser.isInitialNetworkBrowsingComplete())
                && endpointBuilder.isReady();
    }
}
