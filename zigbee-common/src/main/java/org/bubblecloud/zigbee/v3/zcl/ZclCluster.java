package org.bubblecloud.zigbee.v3.zcl;

import java.util.Collections;
import java.util.concurrent.Future;

import org.bubblecloud.zigbee.v3.CommandResult;
import org.bubblecloud.zigbee.v3.ZclCustomResponseMatcher;
import org.bubblecloud.zigbee.v3.ZigBeeApi;
import org.bubblecloud.zigbee.v3.ZigBeeDevice;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.ReadAttributesCommand;
import org.bubblecloud.zigbee.v3.zcl.clusters.general.WriteAttributesCommand;
import org.bubblecloud.zigbee.v3.zcl.field.AttributeIdentifier;
import org.bubblecloud.zigbee.v3.zcl.field.WriteAttributeRecord;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclAttributeType;

/**
 * 
 * @author Chris Jackson
 *
 */
public class ZclCluster {
	
	private final ZigBeeApi zigbeeApi;
	private final ZigBeeDevice zigbeeDevice;
	protected final int clusterId;

	public ZclCluster(ZigBeeApi zigbeeApi, ZigBeeDevice zigbeeDevice,
			int clusterId) {
		this.zigbeeApi = zigbeeApi;
		this.zigbeeDevice = zigbeeDevice;
    	this.clusterId = clusterId;
	}

    protected Future<CommandResult> send(ZclCommand command) {
    	command.setDestinationAddress(zigbeeDevice.getNetworkAddress());
    	command.setDestinationEndpoint(zigbeeDevice.getEndpoint());
        return zigbeeApi.unicast(command);
	}

    /**
     * Read an attribute
     * @param attributeId
     * @return
     */
    protected Future<CommandResult> read(final int attributeId) {
    	return zigbeeApi.read(zigbeeDevice, clusterId, attributeId);
    	/*
        final ReadAttributesCommand command = new ReadAttributesCommand();

        command.setClusterId(clusterId);
        final AttributeIdentifier attributeIdentifier = new AttributeIdentifier();
        attributeIdentifier.setAttributeIdentifier(attributeId);
        command.setIdentifiers(Collections.singletonList(attributeIdentifier));

        command.setDestinationAddress(device.getNetworkAddress());
        command.setDestinationEndpoint(device.getEndpoint());

        return unicast(command);*/
	}

    /**
     * Write an attribute
     * @param attributeId
     * @param value
     * @return
     */
    protected Future<CommandResult> write(final int attributeId, final Object value) {
		return zigbeeApi.write(zigbeeDevice,  clusterId, attributeId, value);
    	/*
        final WriteAttributesCommand command = new WriteAttributesCommand();
        command.setClusterId(clusterId);

        final WriteAttributeRecord record = new WriteAttributeRecord();
        record.setAttributeIdentifier(attributeId);
        record.setAttributeDataType(ZclAttributeType.get(clusterId, attributeId).getZigBeeType().getId());
        record.setAttributeValue(value);
        command.setRecords(Collections.singletonList(record));

        command.setDestinationAddress(zigbeeDevice.getNetworkAddress());
        command.setDestinationEndpoint(zigbeeDevice.getEndpoint());

        return zigbeeApi.unicast(command);*/
	}

}
