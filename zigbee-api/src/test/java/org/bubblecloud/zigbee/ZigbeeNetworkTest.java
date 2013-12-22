package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_MGMT_LEAVE_REQ;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_MGMT_LEAVE_REQ_SRSP;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_MGMT_LEAVE_RSP;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_MGMT_PERMIT_JOIN_REQ;
import org.bubblecloud.zigbee.proxy.DeviceProxy;
import org.bubblecloud.zigbee.proxy.ProxyConstants;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Basic;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.OnOff;
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

        final ZigbeeDiscoveryManager zigbeeDiscoveryManager = new ZigbeeDiscoveryManager(zigbeeNetworkManager);
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
        final ZigbeeApi zigbeeApi = new ZigbeeApi("/dev/ttyACM0", 4951, 22, false);
        zigbeeApi.startup();

        Thread.sleep(500);

        logger.info("Listing proxies:");
        for (final DeviceProxy proxy : zigbeeApi.getZigbeeProxyContext().getDeviceProxies()) {
            logger.info(proxy.getClass().getSimpleName() + " : " + proxy.getDevice().getUniqueIdenfier());
        }



        while (true) {
            try {
                final DeviceProxy proxy = zigbeeApi.getZigbeeProxyContext().getDeviceProxy(528);

                if (proxy == null) {
                    continue;
                }

                final OnOff onOff = (OnOff) proxy.getCluster(ProxyConstants.ON_OFF);
                onOff.off();

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
            }
            Thread.sleep(100);
        }

        Thread.sleep(10000);

        zigbeeApi.shutdown();
    }


}
