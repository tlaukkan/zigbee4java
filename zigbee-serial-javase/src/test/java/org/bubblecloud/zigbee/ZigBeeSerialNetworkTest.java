package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.port.SerialPortImpl;

/**
 * Created by Chris on 25/02/15.
 */
public final class ZigBeeSerialNetworkTest extends ZigBeeNetworkTest {
	public static final String SerialPortIdentifier = "/dev/ttyACM0";
	public static final int    BaudRate             = 115200;

    /**
     * Constructor for defining the serial port implementation.
     */
	public ZigBeeSerialNetworkTest() {
		super(new SerialPortImpl(SerialPortIdentifier, BaudRate));
	}
}
