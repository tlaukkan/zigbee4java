package org.bubblecloud.zigbee.v3;

/**
 * 
 * @author Chris Jackson
 *
 */
public class ZigBeeDeviceAddress extends ZigBeeAddress {
	private int networkAddress;
	private int endpoint;
	
	public ZigBeeDeviceAddress(int networkAddress, int endpoint) {
		this.networkAddress = networkAddress;
		this.endpoint = endpoint;
	}

	public ZigBeeDeviceAddress() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isGroup() {
		return false;
	}

	public void setEndpoint(int endpoint) {
		this.endpoint = endpoint;
	}

	public int getEndpoint() {
		return endpoint;
	}

	public int getAddress() {
		return networkAddress;
	}

	public void setAddress(int networkAddress) {
		this.networkAddress = networkAddress;
	}

	@Override
	public
	String toString() {
		return networkAddress + "/" + endpoint;
	}
}
