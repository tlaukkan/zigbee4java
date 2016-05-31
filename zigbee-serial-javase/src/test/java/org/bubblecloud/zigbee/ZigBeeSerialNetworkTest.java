package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.port.SerialPortImpl;

/**
 * Created by Chris on 25/02/15.
 */
public final class ZigBeeSerialNetworkTest extends ZigBeeNetworkTest {
    /**
     * The serial port identifier.
     */
    public static final String SERIAL_PORT_IDENTIFIER = "/dev/ttyACM0";
    /**
     * The baud rate.
     */
    public static final int BAUD_RATE = 115200;

    /**
     * Constructor for defining the serial port implementation.
     */
    public ZigBeeSerialNetworkTest() {
        super(new SerialPortImpl(SERIAL_PORT_IDENTIFIER, BAUD_RATE));
    }
}
