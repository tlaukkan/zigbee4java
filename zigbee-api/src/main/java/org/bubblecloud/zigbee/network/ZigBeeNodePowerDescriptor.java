package org.bubblecloud.zigbee.network;

/*
 * This implements the Node Power Descriptor.
 * <p>
 * The node power descriptor gives a dynamic indication of the power status of the
 * node and is mandatory for each node.
 * <p>
 * There shall be only one node power descriptor in a node.
 * 
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 */
public class ZigBeeNodePowerDescriptor {

	/*
	 * The current power mode field of the node power descriptor is four bits in
	 * length and specifies the current sleep/power-saving mode of the node.
	 */

	/*
	 * The available power sources field of the node power descriptor is four
	 * bits in length and specifies the power sources available on this node.
	 * For each power source supported on this node, the corresponding bit of
	 * the available power sources field, shall be set to 1. All other bits
	 * shall be set to 0.
	 */

	/*
	 * The current power source field of the node power descriptor is four bits
	 * in length and specifies the current power source being utilized by the
	 * node. For the current power source selected, the corresponding bit of the
	 * current power source field, shall be set to 1. All other bits shall be
	 * set to 0.
	 */

	/*
	 * The current power source level field of the node power descriptor is four
	 * bits in length and specifies the level of charge of the power source. The
	 * current power source level field shall be set to one of the non-reserved
	 * values.
	 */
}
