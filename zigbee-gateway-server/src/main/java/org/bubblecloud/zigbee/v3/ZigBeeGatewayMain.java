package org.bubblecloud.zigbee.v3;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.bouncycastle.util.encoders.Hex;
import org.bubblecloud.zigbee.network.port.SerialPortImpl;
import org.bubblecloud.zigbee.util.ZigBeeConstants;
import org.bubblecloud.zigbee.v3.rpc.AccessLevel;
import org.bubblecloud.zigbee.v3.rpc.AuthorizationProvider;
import org.bubblecloud.zigbee.v3.rpc.ZigBeeRpcServer;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The ZigBee gateway server.
 */
public class ZigBeeGatewayMain {
    /**
     * The {@link org.slf4j.Logger}.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZigBeeGatewayMain.class);
    /**
     * The usage.
     */
	public static final String USAGE = "Syntax: java -jar zigbee4java-serialPort.jar SERIALPORT CHANNEL PAN RESET" +
			" [HTTP(S) PORT] [AUTHORIZATION TOKEN] [KEYSTORE] [SSLPROTOCOL1,SSLPROTOCOL2...]";

    /**
     * Private constructor to disable constructing main class.
     */
	private ZigBeeGatewayMain() {
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
        final byte[] networkKey;
		final boolean resetNetwork;
        final int port;
        final String apiAccessToken;
        final String keystorePath;
        final char[] password;
        final String[] sslProtocols;
		try {
			serialPortName = args[0];
			channel        = Integer.parseInt(args[1]);
			pan            = parseDecimalOrHexInt(args[2]);

            if (!StringUtils.isEmpty(System.getenv("ZIGBEE_NETWORK_KEY"))) {
                LOGGER.info("ZigBee network key defined by environment variable.");
                networkKey = Hex.decode(System.getenv("ZIGBEE_NETWORK_KEY"));
            } else if (args[3].equals("00000000000000000000000000000000")) {
                LOGGER.info("ZigBee network key left as default according to command argument.");
                networkKey = null;
            } else {
                LOGGER.info("ZigBee network key defined by command argument.");
                networkKey = Hex.decode(args[3]);
            }
            if (networkKey != null && networkKey.length != 16) {
                LOGGER.warn("ZigBee network key length should be 16 bytes.");
                return;
            }



            resetNetwork   = args[4].equals("true");


            if (args.length > 5) {
                port = Integer.parseInt(args[5]);
            } else {
                port = -1;
            }
            if (args.length > 6) {
                apiAccessToken = args[6];
            } else {
                apiAccessToken = null;
            }
            if (args.length > 7) {
                keystorePath = args[7];
            } else {
                keystorePath = null;
            }
            if (args.length > 8) {
                sslProtocols = args[8].split(",");
            } else {
                sslProtocols = null;
            }
            if (args.length > 7) {
                System.out.printf("Please enter your password: ");
                char[] passwordInput = System.console().readPassword();
                if (passwordInput.length > 0) {
                    password = passwordInput;
                } else {
                    password = null;
                }
            } else {
                password = null;
            }

		} catch (final Throwable t) {
            t.printStackTrace();
			System.out.println(USAGE);
			return;
		}

		final SerialPort serialPort = new SerialPortImpl(serialPortName, ZigBeeConstants.DEFAULT_BAUD_RATE);
        final ZigBeeDongle dongle = new ZigBeeDongleTiCc2531Impl(serialPort, pan, channel, networkKey, resetNetwork);
		final ZigBeeGateway gateway = new ZigBeeGateway(dongle, resetNetwork);

        final AuthorizationProvider authorizationProvider = new AuthorizationProvider() {
            @Override
            public AccessLevel getAccessLevel(final String accessToken) {
                return apiAccessToken != null &&
                        apiAccessToken.equals(accessToken) ? AccessLevel.ADMINISTRATION : AccessLevel.NONE;
            }
        };


        if (args.length > 5) {
            final ZigBeeRpcServer zigBeeRpcServer;
            try {
                zigBeeRpcServer = new ZigBeeRpcServer(gateway, port, keystorePath, password, sslProtocols,
                        authorizationProvider);
                zigBeeRpcServer.start();
            } catch (final IOException e) {
                LOGGER.error("Error starting ZigBeeConsole HTTP server in port: " + port, e);
                return;
            }

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    if (zigBeeRpcServer != null) {
                        zigBeeRpcServer.stop();
                    }
                }
            }));

        }

        gateway.start();

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
