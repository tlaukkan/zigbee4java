package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.port.ZigBeeSerialPortImpl;

/**
 * Example runtime arguments on Mac OS-X: /dev/cu.usbmodem1411 4951 22 false
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 */
public class ZigBeeConsoleJavaSE
{
	private static final int DefaultBaudRate = 115200;

	private ZigBeeConsoleJavaSE(){}

	public static void main(final String[] args) {
		final String serialPortName;
		final int channel;
		final int pan;
		final boolean resetNetwork;
		try {
			serialPortName = args[0];
			channel        = Integer.parseInt(args[1]);
			pan            = Integer.parseInt(args[2]);
			resetNetwork   = args[3].equals("true");
		} catch (final Throwable t) {
			System.out.println("Syntax: java -jar zigbee4java-serialPort.jar SERIALPORT CHANNEL PAN RESET");
			return;
		}

		ZigBeeSerialPortImpl serialPort = new ZigBeeSerialPortImpl(serialPortName, DefaultBaudRate);

		ZigBeeConsole console = new ZigBeeConsole(serialPort,pan,channel,resetNetwork);

		console.start();
	}
}
