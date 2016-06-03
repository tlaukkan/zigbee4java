package org.bubblecloud.zigbee;

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
        final ZigBeeConsoleApi zigBeeConsoleApi = zigBeeConsoleApiClient.getZigBeeConsoleApi();
        System.out.println(zigBeeConsoleApi.execute("help"));
        System.out.println(zigBeeConsoleApi.execute("list"));
    }

}
