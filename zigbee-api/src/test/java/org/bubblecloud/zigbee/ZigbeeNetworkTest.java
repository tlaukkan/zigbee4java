package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.proxy.DeviceProxy;
import org.bubblecloud.zigbee.proxy.ProxyConstants;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.OnOff;
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
        final ZigbeeApi zigbeeApi = new ZigbeeApi("/dev/ttyACM0");
        zigbeeApi.startup();

        Thread.sleep(500);

        logger.info("Listing proxies:");
        for (final DeviceProxy proxy : zigbeeApi.getZigbeeProxyContext().getDeviceProxies()) {
            logger.info(proxy.getClass().getSimpleName() + " : " + proxy.getDevice().getUniqueIdenfier());
        }



        while (true) {
            try {
                final DeviceProxy proxy = zigbeeApi.getZigbeeProxyContext().getDeviceProxy(528);
                /*if (proxy != null) {
                    final Basic basic = (Basic) proxy.getCluster(ProxyConstants.BASIC);
                    logger.info("Reading manufacturer information for: " + proxy.getDevice().getUniqueIdenfier());
                    logger.info("" + basic.getManufacturerName().getValue());
                }*/
                if (proxy != null) {
                    final OnOff onOff = (OnOff) proxy.getCluster(ProxyConstants.ON_OFF);
                    onOff.off();
                }
            } catch (final Throwable t) {
                logger.error("Error getting information for device.", t);
            }
            Thread.sleep(100);
        }

        //zigbeeApi.shutdown();
    }


}
