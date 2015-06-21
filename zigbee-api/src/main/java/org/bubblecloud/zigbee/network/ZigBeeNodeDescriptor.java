package org.bubblecloud.zigbee.network;

import java.util.EnumMap;
import java.util.Map.Entry;

import org.bubblecloud.zigbee.network.packet.zdo.ZDO_NODE_DESC_RSP;

/*
 * This implements the Node Descriptor.
 * <p>
 * The node descriptor contains information about the capabilities of the ZigBee
 * node and is mandatory for each node.
 * <p>
 * There shall be only one node descriptor in a node.
 * 
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 */
public class ZigBeeNodeDescriptor {
	private LOGICAL_TYPE logicalType;
	private int manufacturerCode;
	private int maxBufferSize;
	private int maxTransferSize;

	enum LOGICAL_TYPE {
		COORDINATOR, ROUTER, END_DEVICE, UNKNOWN
	}

	private final EnumMap<LOGICAL_TYPE, Integer> typeIndexes = new EnumMap<LOGICAL_TYPE, Integer>(
			LOGICAL_TYPE.class);

	ZigBeeNodeDescriptor() {
		typeIndexes.put(LOGICAL_TYPE.COORDINATOR, 0);
		typeIndexes.put(LOGICAL_TYPE.ROUTER, 1);
		typeIndexes.put(LOGICAL_TYPE.END_DEVICE, 2);
		typeIndexes.put(LOGICAL_TYPE.UNKNOWN, -1);
	}

	public ZigBeeNodeDescriptor(ZDO_NODE_DESC_RSP descriptorResponse) {
		this();

		logicalType = LOGICAL_TYPE.UNKNOWN;
		for (Entry<LOGICAL_TYPE, Integer> entry : typeIndexes.entrySet()) {
			if (entry.getValue() == descriptorResponse.NodeType) {
				logicalType = entry.getKey();
			}
		}

		manufacturerCode = descriptorResponse.ManufacturerCode.get16BitValue();
		maxBufferSize = descriptorResponse.BufferSize;
		maxTransferSize = descriptorResponse.TransferSize.get16BitValue();
	}

	public LOGICAL_TYPE getLogicalType() {
		return logicalType;
	}

	/**
	 * The manufacturer code field of the node descriptor is sixteen bits in
	 * length and specifies a manufacturer code that is allocated by the ZigBee
	 * Alliance, relating the manufacturer to the device.
	 * 
	 * @return
	 */
	public int getManufacturerCode() {
		return manufacturerCode;
	}

	/**
	 * The maximum buffer size field of the node descriptor is eight bits in
	 * length, with a valid range of 0x00-0x7f. This field specifies the maximum
	 * size, in octets, of the network sub-layer data unit (NSDU) for this node.
	 * This is the maximum size of data or commands passed to or from the
	 * application by the application support sub-layer, before any
	 * fragmentation or re-assembly.
	 * 
	 * This field can be used as a high-level indication for network management.
	 * 
	 * @return
	 */
	public int getMaximumBufferSize() {
		return maxBufferSize;
	}

	/*
	 * The maximum transfer size field of the node descriptor is sixteen bits in
	 * length, with a valid range of 0x0000-0x7fff. This field specifies the
	 * maximum size, in octets, of the application sub-layer data unit (ASDU)
	 * that can be transferred to this node in one single message transfer. This
	 * value can exceed the value of the node maximum buffer size field (see
	 * sub-clause2.3.2.3.8) through the use of fragmentation.
	 * 
	 * @return
	 */
	public int getMaximumTransferSize() {
		return maxTransferSize;
	}

	/*
	 * The MAC capability flags field is eight bits in length and specifies the
	 * node capabilities, as required by the IEEE 802.15.4-2003 MAC sub-layer
	 * [B1]..
	 * 
	 * The alternate PAN coordinator sub-field is one bit in length and shall be
	 * set to 1 if this node is capable of becoming a PAN coordinator.
	 * Otherwise, the alternative PAN coordinator sub-field shall be set to 0.
	 * 
	 * The device type sub-field is one bit in length and shall be set to 1 if
	 * this node is a full function device (FFD). Otherwise, the device type
	 * sub-field shall be set to 0, indicating a reduced function device (RFD).
	 * 
	 * The power source sub-field is one bit in length and shall be set to 1 if
	 * the current power source is mains power. Otherwise, the power source
	 * sub-field shall be set to 0. This information is derived from the node
	 * current power source field of the node power descriptor.
	 * 
	 * The security capability sub-field is one bit in length and shall be set
	 * to 1 if the device is capable of sending and receiving frames secured
	 * using the security suite specified in [B1]. Otherwise, the security
	 * capability sub-field shall be set to 0.
	 * 
	 * The receiver on when idle sub-field is one bit in length and shall be set
	 * to 1 if the device does not disable its receiver to conserve power during
	 * idle periods. Otherwise, the receiver on when idle sub-field shall be set
	 * to 0 The allocate address sub-field is one bit in length and shall be set
	 * to 0 or 1.
	 */

	/*
	 * The server mask field of the node descriptor is sixteen bits in length,
	 * with bit settings signifying the system server capabilities of this node.
	 * It is used to facilitate discovery of particular system servers by other
	 * nodes on the system.
	 */
}
