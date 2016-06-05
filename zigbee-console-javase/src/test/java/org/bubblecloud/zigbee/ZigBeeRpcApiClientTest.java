package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.ZclCommandListener;
import org.bubblecloud.zigbee.network.zcl.protocol.command.general.ReadAttributesCommand;
import org.bubblecloud.zigbee.network.zcl.field.AttributeIdentifier;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ZigBeeConsoleApiClient integration test.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeRpcApiClientTest {

    @Test
    @Ignore
    public void testCommands() throws ZigBeeException {
        final ZigBeeClient zigBeeClient = new ZigBeeClient("http://127.0.0.1:5000/", "secret");
        //System.out.println(zigBeeClient.execute("help"));
        //System.out.println(zigBeeClient.execute("list"));

        zigBeeClient.startup();

        final ReadAttributesCommand readAttributesCommand = new ReadAttributesCommand();
        readAttributesCommand.setDestinationAddress(11022);
        readAttributesCommand.setDestinationEndpoint(11);
        readAttributesCommand.setClusterId(0);
        readAttributesCommand.setIdentifiers(new ArrayList<AttributeIdentifier>(Arrays.asList(new AttributeIdentifier())));
        zigBeeClient.sendCommand(readAttributesCommand);

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

        zigBeeClient.addCommandListener(new ZclCommandListener() {
            @Override
            public void commandReceived(final ZclCommand command) {
                System.out.println(command);
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*final OnCommand onCommand = new OnCommand();
        onCommand.setDestinationAddress(11022);
        onCommand.setDestinationEndpoint(11);
        zigBeeClient.sendCommand(onCommand);*/

        zigBeeClient.shutdown();
    }

}
