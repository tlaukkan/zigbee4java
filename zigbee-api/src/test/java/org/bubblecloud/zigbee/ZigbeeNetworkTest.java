package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.proxy.DeviceProxy;
import org.bubblecloud.zigbee.proxy.ProxyConstants;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.Basic;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for ZigbeeNetworkManager.
 */
public class ZigbeeNetworkTest {
    private final static Logger logger = LoggerFactory.getLogger(ZigbeeNetworkTest.class);

    @Before
    public void setup() {
    }

    @Test
    @Ignore
    public void testOpenNetwork() throws Exception {

        final ZigbeeNetworkManager zigbeeNetwork = new ZigbeeNetworkManager(
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

        final ZigbeeNetworkManager zigbeeNetworkManager = new ZigbeeNetworkManager(
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
        final ZigbeeApi zigbeeApi = new ZigbeeApi("/dev/ttyACM0");
        zigbeeApi.startup();

        Thread.sleep(2000);

        for (final DeviceProxy proxy : zigbeeApi.getContext().getDeviceProxies()) {
            logger.info(proxy.getDevice().getUniqueIdenfier());
        }

        for (final DeviceProxy proxy : zigbeeApi.getContext().getDeviceProxies()) {
            try {
                final Basic basic = (Basic) proxy.getCluster(ProxyConstants.BASIC);
                logger.info("Reading manufacturer info for: " + proxy.getDevice().getUniqueIdenfier());
                logger.info("" + basic.getManufacturerName().getValue());
                Thread.sleep(3000);
            } catch (final Throwable t) {
                logger.error("Error getting information for device.", t);
            }
        }

        zigbeeApi.shutdown();
    }


}
