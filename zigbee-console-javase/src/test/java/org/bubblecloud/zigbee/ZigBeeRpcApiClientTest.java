package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.ZclCommandListener;
import org.bubblecloud.zigbee.network.zcl.protocol.command.on.off.OffCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.command.on.off.OnCommand;
import org.junit.Ignore;
import org.junit.Test;

/**
 * ZigBeeConsoleApiClient integration test.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeRpcApiClientTest {

    @Test
    @Ignore
    public void testListDevices() throws ZigBeeException {
        final ZigBeeClient zigBeeClient = new ZigBeeClient("http://127.0.0.1:5000/", "secret");
        System.out.println(zigBeeClient.execute("help"));
        System.out.println(zigBeeClient.execute("list"));

        zigBeeClient.startup();

        final OffCommand offCommand = new OffCommand();
        offCommand.setDestinationAddress(11022);
        offCommand.setDestinationEndpoint((short) 11);
        zigBeeClient.sendCommand(offCommand);

        zigBeeClient.addCommandListener(new ZclCommandListener() {
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

        final OnCommand onCommand = new OnCommand();
        onCommand.setDestinationAddress(11022);
        onCommand.setDestinationEndpoint((short) 11);
        zigBeeClient.sendCommand(onCommand);

        zigBeeClient.shutdown();
    }

}
