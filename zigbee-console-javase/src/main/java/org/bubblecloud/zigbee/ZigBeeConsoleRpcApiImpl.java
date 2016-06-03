package org.bubblecloud.zigbee;

import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * ZigBeeConsole API implementation.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigBeeConsoleRpcApiImpl implements ZigBeeConsoleRpcApi {
    /**
     * The {@link org.slf4j.Logger}.
     */
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZigBeeConsoleRpcApiImpl.class);
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
        try {
            this.zigBeeConsole.processInputLine(command, out);
        } catch (final Exception e) {
            LOGGER.error("Error in ZigBeeConsole API execute command.", e);
            out.println("Error: " + e.getMessage());
        }
        return outputStream.toString();
    }
}
