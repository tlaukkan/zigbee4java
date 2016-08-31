package org.bubblecloud.zigbee.v3.zcl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import org.bubblecloud.zigbee.v3.CommandResult;
import org.bubblecloud.zigbee.v3.ZigBeeApi;
import org.bubblecloud.zigbee.v3.ZigBeeDevice;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclDataType;

/**
 * Base class for the ZCL Cluster
 * @author Chris Jackson
 *
 */
public abstract class ZclCluster {

    private final ZigBeeApi zigbeeApi;
    private final ZigBeeDevice zigbeeDevice;
    protected final int clusterId;

    protected Map<Integer, ZclAttribute> attributes = initializeAttributes();

    protected abstract Map<Integer, ZclAttribute> initializeAttributes();
    
    public ZclCluster(ZigBeeApi zigbeeApi, ZigBeeDevice zigbeeDevice, int clusterId) {
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
     * 
     * @param attributeId
     * @return
     */
    protected Future<CommandResult> read(final int attributeId) {
        return zigbeeApi.read(zigbeeDevice, clusterId, attributeId);
        /*
         * final ReadAttributesCommand command = new ReadAttributesCommand();
         * 
         * command.setClusterId(clusterId); final AttributeIdentifier
         * attributeIdentifier = new AttributeIdentifier();
         * attributeIdentifier.setAttributeIdentifier(attributeId);
         * command.setIdentifiers
         * (Collections.singletonList(attributeIdentifier));
         * 
         * command.setDestinationAddress(device.getNetworkAddress());
         * command.setDestinationEndpoint(device.getEndpoint());
         * 
         * return unicast(command);
         */
    }

    /**
     * Write an attribute
     * 
     * @param attributeId
     * @param value
     * @return
     */
    protected Future<CommandResult> write(final int attributeId, ZclDataType dataType, final Object value) {
        return zigbeeApi.write(zigbeeDevice, clusterId, attributeId, value);
        /*
         * final WriteAttributesCommand command = new WriteAttributesCommand();
         * command.setClusterId(clusterId);
         * 
         * final WriteAttributeRecord record = new WriteAttributeRecord();
         * record.setAttributeIdentifier(attributeId);
         * record.setAttributeDataType(ZclAttributeType.get(clusterId,
         * attributeId).getZigBeeType().getId());
         * record.setAttributeValue(value);
         * command.setRecords(Collections.singletonList(record));
         * 
         * command.setDestinationAddress(zigbeeDevice.getNetworkAddress());
         * command.setDestinationEndpoint(zigbeeDevice.getEndpoint());
         * 
         * return zigbeeApi.unicast(command);
         */
    }

    public Future<CommandResult> report(final int attributeId, final int minInterval, final int maxInterval,
            final Object reportableChange) {
        return zigbeeApi.report(zigbeeDevice, clusterId, attributeId, minInterval, maxInterval, reportableChange);
        /*
         * final ConfigureReportingCommand command = new
         * ConfigureReportingCommand();
         * 
         * command.setClusterId(clusterId);
         * 
         * final AttributeReportingConfigurationRecord record = new
         * AttributeReportingConfigurationRecord(); record.setDirection(0);
         * record.setAttributeIdentifier(attributeId);
         * record.setAttributeDataType(ZclAttributeType .get(clusterId,
         * attributeId).getZigBeeType().getId());
         * record.setMinimumReportingInterval(minInterval);
         * record.setMinimumReportingInterval(maxInterval);
         * record.setReportableChange(reportableChange);
         * record.setTimeoutPeriod(0);
         * command.setRecords(Collections.singletonList(record));
         * 
         * command.setDestinationAddress(device.getNetworkAddress());
         * command.setDestinationEndpoint(device.getEndpoint());
         * 
         * return unicast(command, new ZclCustomResponseMatcher());
         */
    }

    /**
     * Gets all the attributes supported by this cluster
     * This will return all attributes, even if they are not actually supported by the device.
     * The user should check to see if this is implemented.
     * @return {@link Set} containing all {@link ZclAttributes} available in this cluster
     */
    public Set<ZclAttribute> getAttributes() {
        Set <ZclAttribute> attr = new HashSet<ZclAttribute>();
        attr.addAll(attributes.values());
        return attr;
    }
    
    /**
     * Gets an attribute from the attribute ID
     * @param id the attribute ID
     * @return the {@link ZclAttribute}
     */
    public ZclAttribute getAttribute(int id) {
        return attributes.get(id);
    }
}
