package org.bubblecloud.zigbee.network.zcl;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.network.zcl.protocol.command.general.DiscoverAttributesResponseCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.command.general.ReadAttributesResponseCommand;
import org.bubblecloud.zigbee.network.zcl.type.AttributeInformation;
import org.bubblecloud.zigbee.network.zcl.type.ReadAttributeStatusRecord;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.bubblecloud.zigbee.network.zcl.protocol.command.general.DiscoverAttributesCommand;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tlaukkan on 6/5/2016.
 */
public class ZclCommandProtocolTest {

    @Test
    public void testDiscoverAttributesCommand() throws Exception {
        final DiscoverAttributesCommand command = new DiscoverAttributesCommand();
        command.setStartAttributeIdentifier(0);
        command.setMaximumAttributeIdentifiers(100);
        testSerialization(command);
    }

    @Test
    public void testDiscoverAttributesResponseCommand() throws Exception {
        final DiscoverAttributesResponseCommand command = new DiscoverAttributesResponseCommand();
        command.setCommandIdentifier(true);
        final List<AttributeInformation> attributeInformationList = new ArrayList<AttributeInformation>();
        final AttributeInformation attributeInformation = new AttributeInformation();
        attributeInformation.setAttributeIdentifier(1);
        attributeInformation.setAttributeDataType(2);
        attributeInformationList.add(attributeInformation);
        command.setInformation(attributeInformationList);
        testSerialization(command);
    }

    @Test
    public void testReadAttributesResponseCommand() throws Exception {
        final ReadAttributesResponseCommand command = new ReadAttributesResponseCommand();
        final List<ReadAttributeStatusRecord> list = new ArrayList<ReadAttributeStatusRecord>();
        final ReadAttributeStatusRecord data = new ReadAttributeStatusRecord();
        data.setAttributeIdentifier(1);
        data.setStatus(0);
        data.setAttributeDataType(ZigBeeType.UnsignedInteger8bit.getId());
        data.setAttributeValue(1);
        list.add(data);
        command.setRecords(list);
        testSerialization(command);
    }


    /**
     * Tests command serialization.
     * @param command the command
     * @throws IOException if IO exception occurs.
     */
    private void testSerialization(final ZclCommand command) throws IOException {
        System.out.println(command);

        final ZclCommandMessage message1 = command.toCommandMessage();

        final byte[] payload = ZclCommandProtocol.serializePayload(message1);

        final ZclCommandMessage message2 = new ZclCommandMessage();
        message2.setType(message1.getType());

        ZclCommandProtocol.deserializePayload(payload, message2);

        final ZclCommand command2 = (ZclCommand) ZclUtil.toCommand(message2);

        Assert.assertEquals("Command equality after payload ZigBee serialization", command.toString(), command2.toString());

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        final String json = objectMapper.writeValueAsString(message1);
        System.out.println(json);
        final ZclCommandMessage message3 = objectMapper.readValue(json, ZclCommandMessage.class);

        Assert.assertEquals("Command equality after JSON serialization", message1.toString(), message3.toString());
    }

}
