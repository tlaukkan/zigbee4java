package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.ZclCommandListener;
import org.junit.Ignore;
import org.junit.Test;

/**
 * ZigBeeConsoleApiClient integration test.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeConsoleApiClientTest {

    @Test
    @Ignore
    public void testListDevices() {
        final ZigBeeConsoleApiClient zigBeeConsoleApiClient = new ZigBeeConsoleApiClient("http://127.0.0.1:5000/", "secret");
        final ZigBeeConsoleRpcApi zigBeeConsoleApi = zigBeeConsoleApiClient.getZigBeeConsoleApi();
        System.out.println(zigBeeConsoleApi.execute("help"));
        System.out.println(zigBeeConsoleApi.execute("list"));

        zigBeeConsoleApiClient.startup();

        zigBeeConsoleApiClient.addCommandListener(new ZclCommandListener() {
            @Override
            public void commandReceived(final ZclCommand command) {
                System.out.println(command);
            }
        });

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        zigBeeConsoleApiClient.shutdown();
    }

}
