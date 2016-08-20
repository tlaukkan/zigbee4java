package org.bubblecloud.zigbee.api.cluster.impl.general.security;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;

public class DoorLockCommandImpl implements Command {
	
	private String pinCode;
	public DoorLockCommandImpl()
	{
		
	}
	
	public DoorLockCommandImpl(String pinCode)
	{
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
