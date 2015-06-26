package org.bubblecloud.zigbee.network;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import org.bubblecloud.zigbee.network.packet.zdo.ZDO_NODE_DESC_RSP;

/**
 * This implements the Node Descriptor.
 * <p>
 * The node descriptor contains information about the capabilities of the ZigBee
 * node and is mandatory for each node.
 * <p>
 * The node descriptor may be requested during service discovery to gain
 * information about the device.
 * <p>
 * There shall be only one node descriptor in a node.
 * 
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 */
public class ZigBeeNodeDescriptor {
	private LOGICAL_TYPE logicalType;
	private int manufacturerCode;
	private int maximumBufferSize;
	private int maximumTransferSize;
	private List<MAC_CAPABILITY> macCapabilities;
	private List<SERVER_MASK> serverMask;

	/**
	 * The logical type field of the node descriptor is three bits in length and
	 * specifies the device type of the ZigBee node.
	 *
	 */
	enum LOGICAL_TYPE {
		COORDINATOR, ROUTER, END_DEVICE, UNKNOWN
	}

	/**
	 * The server mask field of the node descriptor is sixteen bits in length,
	 * with bit settings signifying the system server capabilities of this node.
	 *
	 */
	enum SERVER_MASK {
		PRIMARY_TRUST_CENTRE, BACKUP_TRUST_CENTRE, PRIMARY_BINDING_TABLE, BACKUP_BINDING_TABLE, PRIMARY_DISCOVERY_CACHE
	}

	/**
	 * The MAC capability flags field is eight bits in length and specifies the
	 * node capabilities, as required by the IEEE 802.15.4-2003 MAC sub-layer
	 *
	 */
	enum MAC_CAPABILITY {
		PAN_COORDINATOR, FFD, MAINS_POWER, RX_ALWAYS_ON, SECURITY_CAPABLE
	}

	private final EnumMap<LOGICAL_TYPE, Integer> typeIndexes = new EnumMap<LOGICAL_TYPE, Integer>(
			LOGICAL_TYPE.class);
	private final EnumMap<MAC_CAPABILITY, Integer> macIndexes = new EnumMap<MAC_CAPABILITY, Integer>(
			MAC_CAPABILITY.class);
	private final EnumMap<SERVER_MASK, Integer> serverIndexes = new EnumMap<SERVER_MASK, Integer>(
			SERVER_MASK.class);

	ZigBeeNodeDescriptor() {
		typeIndexes.put(LOGICAL_TYPE.COORDINATOR, 0);
		typeIndexes.put(LOGICAL_TYPE.ROUTER, 1);
		typeIndexes.put(LOGICAL_TYPE.END_DEVICE, 2);

		macIndexes.put(MAC_CAPABILITY.PAN_COORDINATOR, 1);
		macIndexes.put(MAC_CAPABILITY.FFD, 2);
		macIndexes.put(MAC_CAPABILITY.MAINS_POWER, 4);
		macIndexes.put(MAC_CAPABILITY.RX_ALWAYS_ON, 8);
		macIndexes.put(MAC_CAPABILITY.SECURITY_CAPABLE, 64);

		serverIndexes.put(SERVER_MASK.PRIMARY_TRUST_CENTRE, 1);
		serverIndexes.put(SERVER_MASK.BACKUP_TRUST_CENTRE, 2);
		serverIndexes.put(SERVER_MASK.PRIMARY_BINDING_TABLE, 4);
		serverIndexes.put(SERVER_MASK.BACKUP_BINDING_TABLE, 8);
		serverIndexes.put(SERVER_MASK.PRIMARY_DISCOVERY_CACHE, 16);
	}

	public ZigBeeNodeDescriptor(ZDO_NODE_DESC_RSP descriptorResponse) {
		this();

		logicalType = LOGICAL_TYPE.UNKNOWN;
		for (Entry<LOGICAL_TYPE, Integer> entry : typeIndexes.entrySet()) {
			if (entry.getValue() == descriptorResponse.NodeType) {
				logicalType = entry.getKey();
			}
		}

		List<MAC_CAPABILITY> macCapabilities = new ArrayList<MAC_CAPABILITY>();
		for (Entry<MAC_CAPABILITY, Integer> entry : macIndexes.entrySet()) {
			if ((entry.getValue() & descriptorResponse.Capabilities) == 1) {
				macCapabilities.add(entry.getKey());
			}
		}

		List<SERVER_MASK> serverMask = new ArrayList<SERVER_MASK>();
		for (Entry<SERVER_MASK, Integer> entry : serverIndexes.entrySet()) {
			if ((entry.getValue() & descriptorResponse.Capabilities) == 1) {
				serverMask.add(entry.getKey());
			}
		}

		manufacturerCode = descriptorResponse.ManufacturerCode.get16BitValue();
		maximumBufferSize = descriptorResponse.BufferSize;
		maximumTransferSize = descriptorResponse.TransferSize.get16BitValue();
	}

	/**
	 * Returns the logical type of this node
	 * 
	 * @return {@link LOGICAL_TYPE}
	 */
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
		return maximumBufferSize;
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
		return maximumTransferSize;
	}

	/*
	 * The MAC capability flags field is eight bits in length and specifies the
	 * node capabilities, as required by the IEEE 802.15.4-2003 MAC sub-layer.
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
	 * to 1 if the device is capable of sending and receiving frames. Otherwise,
	 * the security capability sub-field shall be set to 0.
	 * 
	 * The receiver on when idle sub-field is one bit in length and shall be set
	 * to 1 if the device does not disable its receiver to conserve power during
	 * idle periods. Otherwise, the receiver on when idle sub-field shall be set
	 * to 0 The allocate address sub-field is one bit in length and shall be set
	 * to 0 or 1.
	 * 
	 * @return array containing all {@link MAC_CAPABILITY}
	 */
	public List<MAC_CAPABILITY> getMacCapabilities() {
		return macCapabilities;
	}

	/*
	 * The server mask field of the node descriptor is sixteen bits in length,
	 * with bit settings signifying the system server capabilities of this node.
	 * It is used to facilitate discovery of particular system servers by other
	 * nodes on the system.
	 * 
	 * @return array containing all {@link SERVER_MASK}
	 */
	public List<SERVER_MASK> getServerMask() {
		return serverMask;
	}
}
