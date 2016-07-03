package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.closures.DoorLock;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;

public class DoorCommandImpl implements Command {

	private byte id;
	private String pinCode;
	public DoorCommandImpl(boolean lock)
	{
		if(lock) {
			this.id = DoorLock.LOCK_ID;
		} else {
			this.id = DoorLock.UNLOCK_ID;
		}
	}

	public DoorCommandImpl(boolean lock, String pinCode)
	{
		this(lock);
		this.pinCode = pinCode;
	}

	@Override
	public boolean isManufacturerExtension() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClusterSpecific() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isClientServerDirection() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public byte[] getPayload() {
		// TODO Auto-generated method stub
		if (this.pinCode != null) {
			return (Character.toChars(this.pinCode.length())[0] + this.pinCode).getBytes();
		} else {
			return new byte[] {};
		}

	}

	@Override
	public byte[] getManufacturerId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getHeaderCommandId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getAllowedResponseId() {
		// TODO Auto-generated method stub
		return null;
	}


}
