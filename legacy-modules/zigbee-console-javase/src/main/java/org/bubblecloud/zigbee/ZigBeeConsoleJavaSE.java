package org.bubblecloud.zigbee;

import org.apache.log4j.xml.DOMConfigurator;
import org.bubblecloud.zigbee.v3.SerialPort;
import org.bubblecloud.zigbee.network.port.SerialPortImpl;
//import org.bubblecloud.zigbee.rpc.AccessLevel;
//import org.bubblecloud.zigbee.rpc.AuthorizationProvider;
//import org.bubblecloud.zigbee.rpc.ZigBeeRpcServer;
import org.slf4j.LoggerFactory;

/**
 * Example runtime arguments on Mac OS-X: /dev/cu.usbmodem1411 4951 22 false
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 */
public class ZigBeeConsoleJavaSE {
    /**
     * The {@link org.slf4j.Logger}.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZigBeeConsoleJavaSE.class);
    /**
     * The default baud rate.
     */
	private static final int DEFAULT_BAUD_RATE = 38400;
    /**
     * The usage.
     */
	public static final String USAGE = "Syntax: java -jar zigbee4java-serialPort.jar SERIALPORT CHANNEL PAN RESET" +
			" [HTTP(S) PORT] [AUTHORIZATION TOKEN] [KEYSTORE] [SSLPROTOCOL1,SSLPROTOCOL2...]";

    /**
     * Private constructor to disable constructing main class.
     */
	private ZigBeeConsoleJavaSE() {
    }

    /**
     * The main method.
     * @param args the command arguments
     */
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
            t.printStackTrace();
			System.out.println(USAGE);
			return;
		}

		final SerialPort serialPort = new SerialPortImpl(serialPortName, DEFAULT_BAUD_RATE);
		final ZigBeeConsole console = new ZigBeeConsole(serialPort,pan,channel,resetNetwork);

        console.start();

	}

    /**
     * Parse decimal or hexadecimal integer.
     * @param s the string
     * @return the parsed integer value
     */
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
