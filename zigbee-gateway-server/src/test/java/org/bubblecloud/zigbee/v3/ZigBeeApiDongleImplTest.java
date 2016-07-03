package org.bubblecloud.zigbee.v3;

import org.bubblecloud.zigbee.network.port.SerialPortImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Test class for local ZigBee API.
 */
public class ZigBeeApiDongleImplTest {
    /**
     * The LOGGER.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZigBeeApiDongleImplTest.class);


    /**
     * Tests local ZigBee API.
     */
    @Test
    @Ignore
    public void testZigBeeApiLocal() {
        final SerialPort port = new SerialPortImpl("COM5");
        final ZigBeeDongle dongle = new ZigBeeDongleTiCc2531Impl(port, 4951, 11, false);

        final ZigBeeApiDongleImpl api = new ZigBeeApiDongleImpl(dongle, false);

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
