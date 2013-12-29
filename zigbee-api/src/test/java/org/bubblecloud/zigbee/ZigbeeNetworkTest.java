package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.discovery.ZigbeeDiscoveryManager;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.network.serial.ZigbeeNetworkManagerSerialImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for ZigbeeNetworkManagerSerialImpl.
 */
public class ZigbeeNetworkTest {
    private final static Logger logger = LoggerFactory.getLogger(ZigbeeNetworkTest.class);

    @Before
    public void setup() {
    }

    @Test
    @Ignore
    public void testOpenNetwork() throws Exception {

        final ZigbeeNetworkManagerSerialImpl zigbeeNetwork = new ZigbeeNetworkManagerSerialImpl(
                "/dev/ttyACM0", 115200, NetworkMode.Coordinator, 4951, 22,
                false, 2500L);
        zigbeeNetwork.startup();

        while (true) {
            if (zigbeeNetwork.getDriverStatus() == DriverStatus.NETWORK_READY) {
                break;
            }
            Thread.sleep(1000);
        }

        zigbeeNetwork.shutdown();
    }

    @Test
    @Ignore
    public void testDiscoverNetwork() throws Exception {

        final ZigbeeNetworkManagerSerialImpl zigbeeNetworkManager = new ZigbeeNetworkManagerSerialImpl(
                "/dev/ttyACM0", 115200, NetworkMode.Coordinator, 4951, 22,
                false, 2500L);

        final ZigbeeDiscoveryManager zigbeeDiscoveryManager = new ZigbeeDiscoveryManager(zigbeeNetworkManager, DiscoveryMode.ALL);
        zigbeeDiscoveryManager.startup();

        zigbeeNetworkManager.startup();

        while (true) {
            if (zigbeeNetworkManager.getDriverStatus() == DriverStatus.NETWORK_READY) {
                break;
            }
            Thread.sleep(1000);
        }

        Thread.sleep(20000);

        zigbeeDiscoveryManager.shutdown();

        zigbeeNetworkManager.shutdown();
    }

    @Test
    @Ignore
    public void testZigbeeApi() throws Exception {
        final ZigbeeApi zigbeeApi = new ZigbeeApi("/dev/ttyACM0", 4951, 11, false);
        zigbeeApi.startup();

        //Thread.sleep(500);
        //Thread.sleep(500);

        Thread.sleep(1000);

        logger.info("Listing devices:");
        for (final Device device : zigbeeApi.getZigbeeApiContext().getDevices()) {
            logger.info(device.getClass().getSimpleName() + " : " + device.getDevice().getEndpointId());
        }

        /*int networkManagerAddress= 0;
        int address = 0;
        zigbeeApi.getZigbeeNetworkManager().sendLocalRequest(
                new ZDO_MGMT_NWK_UPDATE_REQ(
                        address,
                        0x02,
                        ZDO_MGMT_NWK_UPDATE_REQ.CHANNEL_MASK_11,
                        0xfe,
                        0,
                        networkManagerAddress
                )
        );*/

        while (true) {
            try {
                final Device switchDevice = zigbeeApi.getZigbeeApiContext().getDevice("00:12:4B:00:01:DD:8B:21/2");
                final Device lampDevice = zigbeeApi.getZigbeeApiContext().getDevice("00:17:88:01:00:BE:51:EC/11");

                if (lampDevice == null) {
                    continue;
                }

                Thread.sleep(1000);


                //switchProxy.bindTo(lampProxy, ZigbeeApiConstants.CLUSTER_ID_ON_OFF);
                //lampProxy.bind(ZigbeeApiConstants.CLUSTER_ID_ON_OFF);

                //Thread.sleep(1000);

                final OnOff onOff = lampDevice.getCluster(OnOff.class);
                onOff.off();
                //Thread.sleep(5000);

                //final PowerConfiguration powerConfiguration = (PowerConfiguration) switchProxy.getCluster(ZigbeeApiConstants.CLUSTER_ID_POWER_CONFIGURATION);

                /*onOff.subscribe(new OnOffListener() {

                    @Override
                    public void changedOnOff(final OnOffEvent event) {
                        logger.info("On/off event: " + event);
                    }
                });*/


                /*final Groups groups = (Groups) lampProxy.getCluster(ZigbeeApiConstants.CLUSTER_ID_GROUPS);
                groups.addGroup(1, "test");*/

                /*final Basic basic = (Basic) lampProxy.getCluster(ZigbeeApiConstants.CLUSTER_ID_BASIC);
                logger.info("Reading manufacturer information for: " + lampProxy.getDevice().getEndpointId());
                logger.info("" + basic.getManufacturerName().getValue());*/
                /*final Basic basic = (Basic) proxy.getCluster(ZigbeeApiConstants.CLUSTER_ID_BASIC);
                logger.info("Reading manufacturer information for: " + proxy.getDevice().getEndpointId());
                logger.info("" + basic.getManufacturerName().getValue());*/
                /*Thread.sleep(5000);
                switchProxy.getDevice().bindTo(lampProxy.getDevice(), ZigbeeApiConstants.CLUSTER_ID_ON_OFF);
                Thread.sleep(5000);
                lampProxy.getDevice().bindTo(switchProxy.getDevice(), ZigbeeApiConstants.CLUSTER_ID_ON_OFF);
                Thread.sleep(5000);*/
                //final LevelControl levelControl = (LevelControl) lampProxy.getCluster(ZigbeeApiConstants.CLUSTER_ID_LEVEL_CONTROL);
                //logger.info("Level: " + levelControl.getCurrentLevel().getValue());
                //switchProxy.getDevice().bind(ZigbeeApiConstants.CLUSTER_ID_BASIC);
                /*Basic basic = (Basic) lampProxy.getCluster(ZigbeeApiConstants.CLUSTER_ID_BASIC);
                logger.info("" + basic.getModelIdentifier().getValue());*/

                /*int networkManagerAddress= 0;
                int address = proxy.getDevice().getNode().getNetworkAddress();
                ZDO_MGMT_NWK_UPDATE_REQ_SRSP response = zigbeeApi.getZigbeeNetworkManager().sendLocalRequest(
                        new ZDO_MGMT_NWK_UPDATE_REQ(
                                address,
                                0x02,
                                ZDO_MGMT_NWK_UPDATE_REQ.CHANNEL_MASK_11,
                                0xfe,
                                0,
                                networkManagerAddress
                        )
                );*/

                /*final OnOff onOff = (OnOff) proxy.getCluster(ZigbeeApiConstants.CLUSTER_ID_ON_OFF);
                onOff.off();*/

                /*final Basic basic = (Basic) proxy.getCluster(ZigbeeApiConstants.CLUSTER_ID_BASIC);
                logger.info("Reading manufacturer information for: " + proxy.getDevice().getEndpointId());
                logger.info("" + basic.getManufacturerName().getValue());*/

                /*int address = proxy.getDevice().getNode().getNetworkAddress();
                zigbeeApi.getZigbeeNetworkManager().sendPermitJoinRequest(new ZDO_MGMT_PERMIT_JOIN_REQ(
                        new ZToolAddress16(Integers.getByteAsInteger(address, 1), Integers.getByteAsInteger(address, 0)),
                        0x10,
                        0
                ));*/
                /* zigbeeApi.getZigbeeNetworkManager().sendZDOLeaveRequest(new ZToolAddress16[] {
                    new ZToolAddress16(Integers.getByteAsInteger(address, 1), Integers.getByteAsInteger(address, 0))
                });*/

                /*zigbeeApi.getZigbeeNetworkManager().sendZDOLeaveRequest(new ZToolAddress16[] {
                        new ZToolAddress16(0, 0)
                });*/

                /*if (proxy != null) {
                    final Basic basic = (Basic) proxy.getCluster(ZigbeeApiConstants.CLUSTER_ID_BASIC);
                    logger.info("Reading manufacturer information for: " + proxy.getDevice().getEndpointId());
                    logger.info("" + basic.getManufacturerName().getValue());
                }
                if (proxy != null) {
                }*/
                break;
            } catch (final Throwable t) {
                logger.error("Error getting information for device.", t);
                break;
            }
        }

        zigbeeApi.shutdown();
    }


}
