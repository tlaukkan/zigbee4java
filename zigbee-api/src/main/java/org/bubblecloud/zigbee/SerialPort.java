/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 


   See the NOTICE file distributed with this work for additional 
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.packet.ZToolException;
import org.bubblecloud.zigbee.packet.ZToolPacketHandler;
import org.bubblecloud.zigbee.packet.ZToolPacketParser;
import org.bubblecloud.zigbee.packet.SerialHandler;
import j.extensions.comm.SerialComm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The serial communications implementation for zb4osgi.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 * 
 */
public class SerialPort implements SerialHandler {
    /** The logger. */
    private final static Logger logger = LoggerFactory.getLogger(SerialPort.class);
    /** The serial port. */
    private SerialComm serialPort;
    /** The serial port input stream. */
    private InputStream inputStream;
    /** The serial port output stream. */
    private OutputStream outputStream;
    /** The packet parser. */
    private ZToolPacketParser parser;

    /**
     * Opens serial port and starts packet parser.
     *
     * @param port the port
     * @param baudRate the baud rate
     * @param packetHandler the packet handler
     * @throws org.bubblecloud.zigbee.packet.ZToolException if serial port can not be opened.
     */
    public void open(final String port, final int baudRate, final ZToolPacketHandler packetHandler) throws ZToolException {
        try {
            openSerialPort(port, 0, baudRate, 8, SerialComm.ONE_STOP_BIT,
                    SerialComm.NO_PARITY, SerialComm.FLOW_CONTROL_DISABLED);
            parser = new ZToolPacketParser(getInputStream(), packetHandler);
        } catch ( Exception e ) {
            logger.error("Error...", e);
            throw new ZToolException("Unable to open SerialHandler due to exception",e);
        }        
    }

    /**
     * Opens serial port.
     *
     * @param port the port
     * @param timeoutMillis the timeout in milliseconds
     * @param baudRate the baud rate
     * @param dataBits the data bits count
     * @param stopBits the stop bits count
     * @param parity the parity
     * @param flowControl the flow control mode
     */
    private void openSerialPort(String port, int timeoutMillis, int baudRate, int dataBits, int stopBits, int parity, int flowControl) {
        if (serialPort != null) {
            throw new RuntimeException("Serial port '" + serialPort.getSystemPortName()
                    + "' is already open for this serial comm instance.");
        }

        final SerialComm[] ports = SerialComm.getCommPorts();
        logger.debug("Serial ports:");

        final Map<String, SerialComm> portMap = new HashMap<String, SerialComm>();
        for (int i = 0; i < ports.length; ++i) {
            logger.debug(i + ") '" + ports[i].getSystemPortName() + "': " + ports[i].getDescriptivePortName());


            portMap.put(ports[i].getSystemPortName(), ports[i]);
        }

        if (!portMap.containsKey(port)) {
            throw new RuntimeException("Serial port '" + port + "' not found.");
        }

        serialPort = portMap.get(port);
        logger.info("Opening serial port '" + serialPort.getSystemPortName() + "'.");
        if (!serialPort.openPort()) {
            throw new RuntimeException("Serial port '" + port + "' open failed.");
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

    /**
     * Closes serial port and packet parser.
     */
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
                logger.info("Serial port '" + serialPort.getSystemPortName() + "' closed.");
                serialPort = null;
                inputStream = null;
                outputStream = null;
            }
            if ( parser != null ) {
                parser.close();
            }
        } catch (Exception e) {
            logger.warn("Error closing serial port: '" + serialPort.getSystemPortName() + "'" , e);
        }
    }

    /**
     * Gets port output stream.
     * @return the output stream
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * Gets serial port input stream.
     * @return the input stream
     */
    private InputStream getInputStream() {
        return inputStream;
    }
}
