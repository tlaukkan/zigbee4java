package org.bubblecloud.zigbee;

import org.apache.log4j.xml.DOMConfigurator;
import org.bubblecloud.zigbee.network.port.ZigBeePort;
import org.bubblecloud.zigbee.network.port.ZigBeeSerialPortJsscImpl;

/**
 * Example runtime arguments on Mac OS-X: /dev/cu.usbmodem1411 4951 22 false
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 */
public class ZigBeeConsoleJavaSE
{
	private static final int DefaultBaudRate = 38400;

	private ZigBeeConsoleJavaSE(){}

	public static void main(final String[] args) {
		DOMConfigurator.configure("./log4j.xml");

		final String serialPortName;
		final int channel;
		final int pan;
		final boolean resetNetwork;
		try {
			serialPortName = args[0];
			channel        = Integer.parseInt(args[1]);
			pan            = parseDecimalOrHexInt(args[2]);			
			resetNetwork   = args[3].equals("true");
		} catch (final Throwable t) {
			System.out.println("Syntax: java -jar zigbee4java-serialPort.jar SERIALPORT CHANNEL PAN RESET");
			return;
		}

		//ZigBeePort serialPort = new ZigBeeSerialPortImpl(serialPortName, DefaultBaudRate);
		ZigBeePort serialPort = new ZigBeeSerialPortJsscImpl(serialPortName, DefaultBaudRate);

		ZigBeeConsole console = new ZigBeeConsole(serialPort,pan,channel,resetNetwork);

		console.start();
	}
	
	private static int parseDecimalOrHexInt(String s) {
		int radix = 10;
		String number = s;
		if (number.startsWith("0x")) {
			number = number.substring(2);
			radix = 16;
		}
		return Integer.parseInt(number, radix);
	}
}
