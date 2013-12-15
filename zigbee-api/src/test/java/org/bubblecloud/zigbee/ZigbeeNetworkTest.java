package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.model.DriverStatus;
import org.bubblecloud.zigbee.model.NetworkMode;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for ZigbeeNetworkManager.
 */
public class ZigbeeNetworkTest {

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

}
