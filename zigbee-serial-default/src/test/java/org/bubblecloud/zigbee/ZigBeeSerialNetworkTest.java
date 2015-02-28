package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.port.ZigBeeSerialPortImpl;

/**
 * Created by Chris on 25/02/15.
 */
public final class ZigBeeSerialNetworkTest extends ZigBeeNetworkTest
{
	public static final String SerialPortIdentifier = "/dev/ttyACM0";
	public static final int    BaudRate             = 115200;

	protected ZigBeeSerialNetworkTest()
	{
		super(new ZigBeeSerialPortImpl(SerialPortIdentifier, BaudRate));
	}
}
