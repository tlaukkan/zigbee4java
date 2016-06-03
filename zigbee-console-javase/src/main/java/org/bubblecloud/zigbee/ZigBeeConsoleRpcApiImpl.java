package org.bubblecloud.zigbee;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * ZigBeeConsole API implementation.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeConsoleRpcApiImpl implements ZigBeeConsoleRpcApi {

    /**
     * The ZigBee console.
     */
    private final ZigBeeConsole zigBeeConsole;

    /**
     * Constructor for setting the ZigBeeConsole.
     * @param zigBeeConsole the ZigBeeConsole
     */
    public ZigBeeConsoleRpcApiImpl(final ZigBeeConsole zigBeeConsole) {
        this.zigBeeConsole = zigBeeConsole;
    }

    @Override
    public String execute(final String command) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(outputStream);
        this.zigBeeConsole.processInputLine(command, out);
        return outputStream.toString();
    }
}
