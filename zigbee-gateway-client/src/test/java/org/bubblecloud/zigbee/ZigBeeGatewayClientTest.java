package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.v3.ZigBeeGatewayClient;
import org.bubblecloud.zigbee.v3.zcl.field.AttributeIdentifier;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.general.ReadAttributesCommand;
import org.bubblecloud.zigbee.v3.Command;
import org.junit.Assert;
import org.bubblecloud.zigbee.v3.CommandListener;
import org.bubblecloud.zigbee.v3.ZigBeeDevice;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ZigBeeConsoleApiClient integration test.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeGatewayClientTest {

    @Test
    @Ignore
    public void testCommands() throws Exception {
        final ZigBeeGatewayClient client = new ZigBeeGatewayClient("http://127.0.0.1:5000/", "secret");

        client.startup();

        client.addCommandListener(new CommandListener() {
            @Override
            public void commandReceived(final Command command) {
                System.out.println(command);
            }
        });


        final ReadAttributesCommand readAttributesCommand = new ReadAttributesCommand();
        readAttributesCommand.setDestinationAddress(11022);
        readAttributesCommand.setDestinationEndpoint(11);
        readAttributesCommand.setClusterId(0);
        readAttributesCommand.setIdentifiers(new ArrayList<AttributeIdentifier>(Arrays.asList(new AttributeIdentifier())));
        client.sendCommand(readAttributesCommand);

        final ZigBeeDevice device = client.getDevices().get(3);

        Assert.assertTrue(client.on(device).get().isSuccess());

        Thread.sleep(1000);

        Assert.assertTrue(client.color(device, 1.0, 0.0, 0.0, 1.0).get().isSuccess());

        Thread.sleep(1000);

        Assert.assertTrue(client.color(device, 0.0, 1.0, 0.0, 1.0).get().isSuccess());

        Thread.sleep(1000);

        Assert.assertTrue(client.color(device, 0.0, 0.0, 1.0, 1.0).get().isSuccess());

        Thread.sleep(1000);

        Assert.assertTrue(client.off(device).get().isSuccess());

        /*final ReadAttributesCommand readAttributesCommand = new ReadAttributesCommand();
        readAttributesCommand.setDestinationAddress(11022);
        readAttributesCommand.setDestinationEndpoint(11);
        readAttributesCommand.setClusterId(0);
        readAttributesCommand.setIdentifiers(new ArrayList<AttributeIdentifier>(Arrays.asList(new AttributeIdentifier())));
        zigBeeClient.sendCommand(readAttributesCommand);*/

        /*final DiscoverAttributesCommand discoverAttributesCommand = new DiscoverAttributesCommand();
        discoverAttributesCommand.setClusterId(1);
        discoverAttributesCommand.setDestinationAddress(8951);
        discoverAttributesCommand.setDestinationEndpoint(1);
        discoverAttributesCommand.setStartAttributeIdentifier(0);
        discoverAttributesCommand.setMaximumAttributeIdentifiers(1);
        zigBeeClient.sendCommand(discoverAttributesCommand);*/

        /*final OffCommand offCommand = new OffCommand();
        offCommand.setDestinationAddress(11022);
        offCommand.setDestinationEndpoint(11);
        zigBeeClient.sendCommand(offCommand);*/

        /**final IdentifyCommand identifyCommand = new IdentifyCommand();
        identifyCommand.setDestinationAddress(11022);
        identifyCommand.setDestinationEndpoint(11);
        identifyCommand.setIdentifyTime(10);
        zigBeeClient.sendCommand(identifyCommand);*/

        /*final OnCommand onCommand = new OnCommand();
        onCommand.setDestinationAddress(11022);
        onCommand.setDestinationEndpoint(11);
        zigBeeClient.sendCommand(onCommand);*/

        client.shutdown();
    }

}
