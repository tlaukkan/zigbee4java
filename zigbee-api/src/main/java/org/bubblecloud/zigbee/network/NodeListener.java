package org.bubblecloud.zigbee.network;

/**
 * Provides an interface to notify upper layers of changes to {@link ZigBeeNode nodes}.
 */
public interface NodeListener {

	/**
	 * Notification that a node has been added to the list of nodes maintained within the network.
	 * <p>
	 * This is called when a node is initially added, and before any discovery has occurred. The listener
	 * can not assume that all information contained within the {@link ZigBeeNode} is complete since
	 * discovery is still taking place.
	 * @param node {@link ZigBeeNode}
	 */
    public void nodeAdded(final ZigBeeNode node);

	/**
	 * Notification that a new node has completed its endpoint and descriptor discovery. When this
	 * notification is called, the listener can assume that all information contained within the
	 * {@link ZigBeeNode} is complete.
	 * @param node {@link ZigBeeNode}
	 */
    public void nodeDiscovered(final ZigBeeNode node);

	/**
	 * Notification that a node has been removed from the list of nodes maintained within the network.
	 * @param node {@link ZigBeeNode}
	 */
    public void nodeRemoved(final ZigBeeNode node);

}
