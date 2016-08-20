package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.network.discovery.ZigBeeDiscoveryManager;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerImpl;
import org.bubblecloud.zigbee.v3.SerialPort;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Test for ZigBeeNetworkManagerSerialImpl.
 */
public abstract class ZigBeeNetworkTest {
    private final static Logger logger = LoggerFactory.getLogger(ZigBeeNetworkTest.class);
	private final SerialPort port;

	protected ZigBeeNetworkTest(SerialPort port) {
		this.port = port;
	}

    @Before
    public void setup() {
    }

	@Test
    @Ignore
    public void testOpenNetwork() throws Exception {

        final ZigBeeNetworkManagerImpl zigbeeNetwork = new ZigBeeNetworkManagerImpl(
                port, NetworkMode.Coordinator, 4951, 22, null, 2500L);
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

        final ZigBeeNetworkManagerImpl zigbeeNetworkManager = new ZigBeeNetworkManagerImpl(
                port, NetworkMode.Coordinator, 4951, 22, null, 2500L);

        final ZigBeeDiscoveryManager zigbeeDiscoveryManager = new ZigBeeDiscoveryManager(zigbeeNetworkManager, DiscoveryMode.ALL);
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
    public void testZigBeeApi() throws Exception {
        
        final Set<DiscoveryMode> discoveryModes = DiscoveryMode.ALL;
        discoveryModes.remove(DiscoveryMode.LinkQuality);
        final ZigBeeApi zigbeeApi = new ZigBeeApi(port, 4951, 11, null, false, discoveryModes);
        zigbeeApi.startup();

        Thread.sleep(1000);

        logger.info("Listing devices:");
        for (final Device device : zigbeeApi.getZigBeeApiContext().getDevices()) {
            logger.info(device.getClass().getSimpleName() + " : " + device.getEndpoint().getEndpointId());
        }

        while (true) {
            try {
                final Device switchDevice = zigbeeApi.getZigBeeApiContext().getDevice("00:12:4B:00:01:DD:8B:21/2");
                final Device lampDevice = zigbeeApi.getZigBeeApiContext().getDevice("00:17:88:01:00:BE:51:EC/11");

                if (lampDevice == null) {
                    continue;
                }

                Thread.sleep(1000);

                final OnOff onOff = lampDevice.getCluster(OnOff.class);
                onOff.off();

                break;
            } catch (final Throwable t) {
                logger.error("Error getting information for device.", t);
                break;
            }
        }

        zigbeeApi.shutdown();
    }


}
