package org.bubblecloud.zigbee.network.port;

import j.extensions.comm.SerialComm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The system serial-port implementation of ZigBeePort.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 */
public class ZigBeeSerialPortImpl implements ZigBeePort
{
    /**
     * The logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(ZigBeeSerialPortImpl.class);
    /**
     * The portIdentifier portIdentifier.
     */
    private SerialComm serialPort;
    /**
     * The portIdentifier portIdentifier input stream.
     */
    private InputStream inputStream;
    /**
     * The portIdentifier portIdentifier output stream.
     */
    private OutputStream outputStream;

	private final String portIdentifier;
	private final int    baudRate;

	public ZigBeeSerialPortImpl(final String portIdentifier, final int baudRate) {
		this.portIdentifier = portIdentifier;
		this.baudRate = baudRate;
	}

    @Override
    public boolean open() {
        try {
            openSerialPort(portIdentifier, 0, baudRate, 8, SerialComm.ONE_STOP_BIT,
                    SerialComm.NO_PARITY, SerialComm.FLOW_CONTROL_DISABLED);
            return true;
        } catch (Exception e) {
            logger.error("Error...", e);
            return false;
        }
    }

    /**
     * Opens portIdentifier portIdentifier.
     *
     * @param port          the portIdentifier
     * @param timeoutMillis the timeout in milliseconds
     * @param baudRate      the baud rate
     * @param dataBits      the data bits count
     * @param stopBits      the stop bits count
     * @param parity        the parity
     * @param flowControl   the flow control mode
     */
    private void openSerialPort(String port, int timeoutMillis, int baudRate, int dataBits, int stopBits, int parity, int flowControl) {
        if (serialPort != null) {
            throw new RuntimeException("Serial portIdentifier '" + serialPort.getSystemPortName()
                    + "' is already startup for this portIdentifier comm instance.");
        }

        final SerialComm[] ports = SerialComm.getCommPorts();
        logger.trace("Serial ports:");

        final Map<String, SerialComm> portMap = new HashMap<String, SerialComm>();
        for (int i = 0; i < ports.length; ++i) {
            logger.trace(i + ") '" + ports[i].getSystemPortName() + "': " + ports[i].getDescriptivePortName());


            portMap.put(ports[i].getSystemPortName(), ports[i]);
        }

        if (!portMap.containsKey(port)) {
            throw new RuntimeException("Serial portIdentifier '" + port + "' not found.");
        }

        serialPort = portMap.get(port);
        logger.info("Opening portIdentifier portIdentifier '" + serialPort.getSystemPortName() + "'.");
        if (!serialPort.openPort()) {
            throw new RuntimeException("Serial portIdentifier '" + port + "' startup failed.");
        }

        serialPort.setComPortTimeouts(SerialComm.TIMEOUT_READ_BLOCKING, timeoutMillis, 0);
        serialPort.setBaudRate(baudRate);
        serialPort.setNumDataBits(dataBits);
        serialPort.setNumStopBits(stopBits);
        serialPort.setParity(parity);
        serialPort.setFlowControl(flowControl);

        inputStream = serialPort.getInputStream();
        outputStream = serialPort.getOutputStream();
    }

    @Override
    public void close() {
        try {
            if (serialPort != null) {
                while (inputStream.available() > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (final InterruptedException e) {
                        logger.warn("Interrupted while waiting input stream to flush.");
                    }
                }
                inputStream.close();
                outputStream.flush();
                outputStream.close();
                serialPort.closePort();
                logger.info("Serial portIdentifier '" + serialPort.getSystemPortName() + "' closed.");
                serialPort = null;
                inputStream = null;
                outputStream = null;
            }
        } catch (Exception e) {
            logger.warn("Error closing portIdentifier portIdentifier: '" + serialPort.getSystemPortName() + "'", e);
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }
}
