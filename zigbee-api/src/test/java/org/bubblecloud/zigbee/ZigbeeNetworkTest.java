package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.zdo.*;
import org.bubblecloud.zigbee.proxy.DeviceProxy;
import org.bubblecloud.zigbee.proxy.ProxyConstants;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.PowerConfiguration;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Basic;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Groups;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.LevelControl;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.OnOff;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.event.OnOffEvent;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.event.OnOffListener;
import org.bubblecloud.zigbee.util.Integers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.omg.CORBA.TIMEOUT;
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
        zigbeeNetwork.open();

        while (true) {
            if (zigbeeNetwork.getDriverStatus() == DriverStatus.NETWORK_READY) {
                break;
            }
            Thread.sleep(1000);
        }

        zigbeeNetwork.close();
    }

    @Test
    @Ignore
    public void testDiscoverNetwork() throws Exception {

        final ZigbeeNetworkManagerSerialImpl zigbeeNetworkManager = new ZigbeeNetworkManagerSerialImpl(
                "/dev/ttyACM0", 115200, NetworkMode.Coordinator, 4951, 22,
                false, 2500L);

        final ZigbeeDiscoveryManager zigbeeDiscoveryManager = new ZigbeeDiscoveryManager(zigbeeNetworkManager, true);
        zigbeeDiscoveryManager.startup();

        zigbeeNetworkManager.open();

        while (true) {
            if (zigbeeNetworkManager.getDriverStatus() == DriverStatus.NETWORK_READY) {
                break;
            }
            Thread.sleep(1000);
        }

        Thread.sleep(20000);

        zigbeeDiscoveryManager.shutdown();

        zigbeeNetworkManager.close();
    }

    @Test
    public void testZigbeeApi() throws Exception {
        final ZigbeeApi zigbeeApi = new ZigbeeApi("/dev/ttyACM0", 4951, 11, false, false);
        zigbeeApi.startup();

        //Thread.sleep(500);
        //Thread.sleep(500);

        Thread.sleep(1000);

        logger.info("Listing proxies:");
        for (final DeviceProxy proxy : zigbeeApi.getZigbeeProxyContext().getDeviceProxies()) {
            logger.info(proxy.getClass().getSimpleName() + " : " + proxy.getDevice().getUniqueIdenfier());
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
                final DeviceProxy switchProxy = zigbeeApi.getZigbeeProxyContext().getDeviceProxy("00:12:4B:00:01:DD:8B:21/2");
                final DeviceProxy lampProxy = zigbeeApi.getZigbeeProxyContext().getDeviceProxy("00:17:88:01:00:BE:51:EC/11");

                if (lampProxy == null) {
                    continue;
                }

                Thread.sleep(1000);


                //switchProxy.bindTo(lampProxy, ProxyConstants.ON_OFF);
                //lampProxy.bind(ProxyConstants.ON_OFF);

                //Thread.sleep(1000);

                final OnOff onOff = lampProxy.getCluster(ProxyConstants.ON_OFF);
                onOff.off();
                //Thread.sleep(5000);

                //final PowerConfiguration powerConfiguration = (PowerConfiguration) switchProxy.getCluster(ProxyConstants.POWER_CONFIGURATION);

                /*onOff.subscribe(new OnOffListener() {

                    @Override
                    public void changedOnOff(final OnOffEvent event) {
                        logger.info("On/off event: " + event);
                    }
                });*/


                /*final Groups groups = (Groups) lampProxy.getCluster(ProxyConstants.GROUPS);
                groups.addGroup(1, "test");*/

                /*final Basic basic = (Basic) lampProxy.getCluster(ProxyConstants.BASIC);
                logger.info("Reading manufacturer information for: " + lampProxy.getDevice().getUniqueIdenfier());
                logger.info("" + basic.getManufacturerName().getValue());*/
                /*final Basic basic = (Basic) proxy.getCluster(ProxyConstants.BASIC);
                logger.info("Reading manufacturer information for: " + proxy.getDevice().getUniqueIdenfier());
                logger.info("" + basic.getManufacturerName().getValue());*/
                /*Thread.sleep(5000);
                switchProxy.getDevice().bindTo(lampProxy.getDevice(), ProxyConstants.ON_OFF);
                Thread.sleep(5000);
                lampProxy.getDevice().bindTo(switchProxy.getDevice(), ProxyConstants.ON_OFF);
                Thread.sleep(5000);*/
                //final LevelControl levelControl = (LevelControl) lampProxy.getCluster(ProxyConstants.LEVEL_CONTROL);
                //logger.info("Level: " + levelControl.getCurrentLevel().getValue());
                //switchProxy.getDevice().bind(ProxyConstants.BASIC);
                /*Basic basic = (Basic) lampProxy.getCluster(ProxyConstants.BASIC);
                logger.info("" + basic.getModelIdentifier().getValue());*/

                /*int networkManagerAddress= 0;
                int address = proxy.getDevice().getPhysicalNode().getNetworkAddress();
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

                /*final OnOff onOff = (OnOff) proxy.getCluster(ProxyConstants.ON_OFF);
                onOff.off();*/

                /*final Basic basic = (Basic) proxy.getCluster(ProxyConstants.BASIC);
                logger.info("Reading manufacturer information for: " + proxy.getDevice().getUniqueIdenfier());
                logger.info("" + basic.getManufacturerName().getValue());*/

                /*int address = proxy.getDevice().getPhysicalNode().getNetworkAddress();
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
                    final Basic basic = (Basic) proxy.getCluster(ProxyConstants.BASIC);
                    logger.info("Reading manufacturer information for: " + proxy.getDevice().getUniqueIdenfier());
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
