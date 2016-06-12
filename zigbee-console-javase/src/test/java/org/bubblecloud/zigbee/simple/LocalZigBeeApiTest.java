package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.network.SerialPort;
import org.bubblecloud.zigbee.network.port.SerialPortImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Test class for local ZigBee API.
 */
public class LocalZigBeeApiTest {
    /**
     * The LOGGER.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LocalZigBeeApiTest.class);


    /**
     * Tests local ZigBee API.
     */
    @Test
    @Ignore
    public void testZigBeeApiLocal() {
        final SerialPort port = new SerialPortImpl("COM5");
        final LocalZigBeeApi api = new LocalZigBeeApi(port, 4951, 11, false);

        api.getNetworkState().addNetworkListener(new ZigBeeNetworkStateListener() {
            @Override
            public void deviceAdded(ZigBeeDevice device) {
                LOGGER.info("Device added: " + device);
            }

            @Override
            public void deviceUpdated(ZigBeeDevice device) {
                LOGGER.info("Device updated: " + device);
            }

            @Override
            public void deviceRemoved(ZigBeeDevice device) {
                LOGGER.info("Device removed: " + device);
            }
        });

        api.startup();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final List<ZigBeeDevice> devices = api.getNetworkState().getDevices();
        for (final ZigBeeDevice device : devices) {
            LOGGER.info(device.toString());
        }

        api.shutdown();
        port.close();
    }

}
