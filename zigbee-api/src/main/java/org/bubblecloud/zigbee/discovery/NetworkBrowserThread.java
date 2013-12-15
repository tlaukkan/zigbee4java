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

package org.bubblecloud.zigbee.discovery;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.zdo.ZDO_IEEE_ADDR_REQ;
import com.itaca.ztool.api.zdo.ZDO_IEEE_ADDR_RSP;
import it.cnr.isti.primitvetypes.util.Integers;
import it.cnr.isti.thread.RunnableThread;
import it.cnr.isti.thread.ThreadUtils;
import it.cnr.isti.zigbee.api.ZigBeeNode;
import org.bubblecloud.zigbee.AFLayer;
import org.bubblecloud.zigbee.impl.ZigBeeNetwork;
import org.bubblecloud.zigbee.impl.ZigBeeNodeImpl;
import org.bubblecloud.zigbee.model.SimpleDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 *
 */
public class NetworkBrowserThread extends RunnableThread {

    private static final Logger logger = LoggerFactory.getLogger(NetworkBrowserThread.class);

    private static final short COORDINATOR_NWK_ADDRESS = 0;

    private final ImportingQueue queue;
    final SimpleDriver driver;

    final ArrayList<NetworkAddressNodeItem> toInspect = new ArrayList<NetworkAddressNodeItem>();
    final HashMap<Integer, NetworkAddressNodeItem> alreadyInspected = new HashMap<Integer, NetworkAddressNodeItem>();


    private class NetworkAddressNodeItem {
        final NetworkAddressNodeItem parent;
        final short address;
        ZigBeeNodeImpl node = null;

        NetworkAddressNodeItem(NetworkAddressNodeItem addressTreeParent, short networkAddress){
            parent = addressTreeParent;
            address = networkAddress;
        }

        public String toString(){
            if ( parent != null ) {
                return "<" + parent.address + " / " + parent.node + "," + address + " / " + node + ">";
            } else {
                return "< NULL ," + address + " / " + node + ">";
            }
        }
    }

    public NetworkBrowserThread(ImportingQueue queue, SimpleDriver driver) {
        this.queue = queue;
        this.driver = driver;
    }

    public void task(){
        final String threadName = Thread.currentThread().getName();

        logger.debug("{} STARTED Succesfully", threadName);

        while( ! isDone() ){
            long wakeUpTime = System.currentTimeMillis() + 15 * 60 * 1000;
            cleanUpWalkingTree();

            logger.debug("Inspecting ZigBee network for new nodes.");
            toInspect.add(new NetworkAddressNodeItem(null, COORDINATOR_NWK_ADDRESS) );
            try{
                while(toInspect.size() != 0){
                    final NetworkAddressNodeItem inspecting = toInspect.remove( toInspect.size()-1 );

                    alreadyInspected.put((int) inspecting.address, inspecting);
                    logger.info("Inspecting node #{} ({})", inspecting.address, ((int) inspecting.address & 0xFFFF));
                    ZDO_IEEE_ADDR_RSP result = driver.sendZDOIEEEAddressRequest(
                            new ZDO_IEEE_ADDR_REQ(inspecting.address,ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED,(byte) 0)
                    );

                    if( result == null) {
                        logger.debug("No answer from #{} ({})", inspecting.address, ((int) inspecting.address & 0xFFFF));
                        continue;
                    } else if ( result.Status == 0 ){
                        logger.debug(
                                "Answer from {} with {} associated",
                                result.getIEEEAddress(), result.getAssociatedDeviceCount()
                        );
                        inspecting.node = new ZigBeeNodeImpl(inspecting.address, result.getIEEEAddress(),
                                (short) driver.getCurrentPanId());

                        ZToolAddress16 nwk = new ZToolAddress16(
                                Integers.getByteAsInteger( inspecting.address, 1 ),
                                Integers.getByteAsInteger( inspecting.address, 0 )
                        );
                        queue.push( nwk, result.getIEEEAddress() );

                        notifyBrowsedNode( inspecting );
                    }
                    toInspect.addAll( addChildrenNodesToInspectingQueue( inspecting, result ) );
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            logger.info("Network browsing completed, waiting until {}", wakeUpTime);
            if ( ! isDone() ) ThreadUtils.waitingUntil( wakeUpTime );
        }
        logger.debug("{} TERMINATED Succesfully", threadName);
    }


    private void cleanUpWalkingTree() {
        alreadyInspected.clear();
        toInspect.clear();
    }

    private ArrayList<NetworkAddressNodeItem> addChildrenNodesToInspectingQueue(NetworkAddressNodeItem inspecting, ZDO_IEEE_ADDR_RSP result) {
        int start = 0;
        final ArrayList<NetworkAddressNodeItem> adding = new ArrayList<NetworkAddressNodeItem>();
        do{
            short[] toAdd = result.getAssociatedDeviceList();
            for (int i = 0; i < toAdd.length; i++) {
                logger.info( "Found node #{} associated to node #{}", toAdd[i], inspecting.address );
                final NetworkAddressNodeItem next = new NetworkAddressNodeItem( inspecting, toAdd[i] );
                final NetworkAddressNodeItem found = alreadyInspected.get( (int) toAdd[i] );
                if ( found != null ) {
                    //NOTE Logging this wrong behavior but doing nothing
                    logger.error(
                            "BROKEN ZIGBEE UNDERSTANDING (while walking address-tree): " +
                            "found twice the same node with network address {} ", toAdd[i]
                    );
                    logger.debug("Previus node data was {} while current has parent {}", found, inspecting);
                } else {
                    adding.add( next );
                }
            }
            if( toAdd.length + result.getStartIndex() >= result.getAssociatedDeviceCount() ) {
                //NOTE No more node connected to inspecting
                return adding;
            }
            start += toAdd.length;

            logger.info(
                    "Node #{} as too many device connected to it received only {} out of {}, " +
                    "we need to inspect it once more", new Object[]{
                    inspecting.address, toAdd.length, result.getAssociatedDeviceCount()
            });
            result = driver.sendZDOIEEEAddressRequest(
                    new ZDO_IEEE_ADDR_REQ(inspecting.address,ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED,(byte) start )
            );
            if ( result == null ){
                logger.error("Faild to further inspect connected device to node #{}", inspecting.address);
            }
        } while( result != null );

        return adding;
    }

    private void notifyBrowsedNode(NetworkAddressNodeItem item) {
        final ZigBeeNode node = item.node;

        final ZigBeeNetwork network = AFLayer.getAFLayer(driver).getZigBeeNetwork();
        network.notifyNodeBrowsed(node);
    }
}
