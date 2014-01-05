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
package org.bubblecloud.zigbee.network.serial;

import org.bubblecloud.zigbee.network.AsynchronousCommandListener;
import org.bubblecloud.zigbee.network.SynchronousCommandListener;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.network.packet.ZToolPacketHandler;
import org.bubblecloud.zigbee.network.packet.ZToolPacketParser;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * ZigBeeSerialInterface is used to startup connection to ZigBee network.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigBeeSerialInterface implements ZToolPacketHandler {
    /**
     * The logger.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ZigBeeSerialInterface.class);
    /**
     * The serial port name.
     */
    final String serialPortName;
    /**
     * The serial interface.
     */
    final SerialPortImpl serialPort;
    /**
     * The packet parser.
     */
    private ZToolPacketParser parser;
    /**
     * Support paraller processing of different command types. Only one command per command ID can be in process
     * at a time.
     */
    private final boolean supportMultipleSynchrounsCommand = false;
    /**
     * Synchronous command listeners.
     */
    private final Hashtable<Short, SynchronousCommandListener> synchronousCommandListeners
            = new Hashtable<Short, SynchronousCommandListener>();
    /**
     * Asynchronous command listeners.
     */
    private final HashSet<AsynchronousCommandListener> asynchrounsCommandListeners
            = new HashSet<AsynchronousCommandListener>();
    /**
     * Timeout times for synchronous command listeners.
     */
    private final HashMap<SynchronousCommandListener, Long> synchronousCommandListenerTimeouts =
            new HashMap<SynchronousCommandListener, Long>();

    /**
     * Constructor for configuring the ZigBee Network connection parameters.
     * @param serialPortName the serial port name.
     */
    public ZigBeeSerialInterface(String serialPortName) {
        this.serialPortName = serialPortName;
        serialPort = new SerialPortImpl();
    }

    /**
     * Opens connection to ZigBee Network.
     * @return true if connection startup was success.
     */
    public boolean open() {
        if (!serialPort.open(serialPortName, 115200)) {
            return false;
        }
        parser = new ZToolPacketParser(serialPort.getInputStream(), this);
        return true;
    }

    /**
     * Closes connection ot ZigBee Network.
     */
    public void close() {
        synchronized (serialPort) {
            if (serialPort != null) {
                serialPort.close();
            }
            if (parser != null) {
                parser.close();
            }
        }
    }

    /* ZToolPacketHandler */

    /**
     * Exception in packet parsing.
     * @param th the exception
     */
    public void error(final Throwable th) {
        LOGGER.error("Exception in packet parsing: ", th);
    }

    /**
     * Handle parsed packet.
     * @param packet the packet
     */
    public void handlePacket(final ZToolPacket packet) {
        final DoubleByte cmdId = packet.getCMD();
        switch (cmdId.getMsb() & 0xE0) {
            // Received incoming message which can be either message from dongle or remote device.
            case 0x40:
                LOGGER.debug("<-- {} ({})", packet.getClass().getSimpleName(), packet);
                notifyAsynchronousCommand(packet);
                break;

            // Received synchronous command response.
            case 0x60:
                LOGGER.debug("<- {} ({})", packet.getClass().getSimpleName(), packet);
                notifySynchronousCommand(packet);
                break;

            default:
                LOGGER.error("Received unknown packet. ({}) ({})", packet.getClass().getSimpleName(), packet);
        }
    }

    /**
     * Send packet to dongle.
     * @param packet the packet
     * @throws IOException if IO exception occurs while sending packet
     */
    private void sendPacket(final ZToolPacket packet)
            throws IOException {
        LOGGER.debug("-> {} ({}) ", packet.getClass().getSimpleName(), packet.toString());
        final int[] pck = packet.getPacket();
        synchronized (serialPort) {
            final OutputStream out = serialPort.getOutputStream();
            if (out == null) {
                // Socket has not been opened or is already closed.
                return;
            }
            for (int i = 0; i < pck.length; i++) {
                out.write(pck[i]);
            }
            out.flush();
        }
    }


    /**
     * Cleans expired synchronous command listeners.
     */
    private void cleanExpiredSynchronousCommandListeners() {
        final long now = System.currentTimeMillis();
        final ArrayList<Short> expired = new ArrayList<Short>();
        synchronized (synchronousCommandListeners) {
            final Iterator<Map.Entry<Short, SynchronousCommandListener>> i =
                    synchronousCommandListeners.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<Short, SynchronousCommandListener> entry = i.next();

                final long expiration = synchronousCommandListenerTimeouts.get(entry.getValue());
                if (expiration != -1L && expiration < now) {
                    expired.add(entry.getKey());
                }
            }

            for (Short key : expired) {
                synchronousCommandListeners.remove(key);
            }
            synchronousCommandListeners.notifyAll();
        }
    }

    /**
     * Sends synchronous command and adds listener.
     * @param packet the command packet
     * @param listener the synchronous command response listener
     * @param timeout the timeout
     * @throws IOException if IO exception occurs in packet sending
     */
    public void sendSynchronousCommand(final ZToolPacket packet, final SynchronousCommandListener listener,
                                       final long timeout)
            throws IOException {
        if (timeout == -1L) {
            synchronousCommandListenerTimeouts.put(listener, -1L);
        } else {
            final long expirationTime = System.currentTimeMillis() + timeout;
            synchronousCommandListenerTimeouts.put(listener, expirationTime);
        }

        final DoubleByte cmdId = packet.getCMD();
        final int value = (cmdId.getMsb() & 0xE0);
        if (value != 0x20) {
            throw new IllegalArgumentException("You are trying to send a non SREQ packet as synchronous command. "
                    + "Evaluated " + value + " instead of " + 0x20 + "\nPacket "
                    + packet.getClass().getName() + "\n" + packet
            );
        }

        cleanExpiredSynchronousCommandListeners();

        if (supportMultipleSynchrounsCommand) {
            synchronized (synchronousCommandListeners) {
                final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
                while (synchronousCommandListeners.get(cmdId) != null) {
                    try {
                        LOGGER.trace("Waiting for other request {} to complete", id);
                        synchronousCommandListeners.wait(500);
                        cleanExpiredSynchronousCommandListeners();
                    } catch (InterruptedException ignored) {
                    }
                }
                synchronousCommandListeners.put(id, listener);
            }
        } else {
            synchronized (synchronousCommandListeners) {
                final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
                while (synchronousCommandListeners.isEmpty() == false) {
                    try {
                        LOGGER.debug("Waiting for other request to complete");
                        synchronousCommandListeners.wait(500);
                        cleanExpiredSynchronousCommandListeners();
                    } catch (InterruptedException ignored) {
                    }
                }
                LOGGER.trace("Put synchronousCommandListeners listener for {} command", id);
                synchronousCommandListeners.put(id, listener);
            }
        }
        LOGGER.trace("Sending SynchrounsCommand {} ", packet);
        sendPacket(packet);
    }

    /**
     * Sends asynchronous command.
     * @param packet the packet.
     * @throws IOException if IO exception occurs in packet sending.
     */
    public void sendAsynchronousCommand(final ZToolPacket packet) throws IOException {
        int value = (packet.getCMD().getMsb() & 0xE0);
        if (value != 0x40) {
            throw new IllegalArgumentException("You are trying to send a non AREQ packet. "
                    + "Evaluated " + value + " instead of "
                    + 0x40 + "\nPacket " + packet.getClass().getName() + "\n" + packet
            );
        }
        sendPacket(packet);
    }

    /**
     * Notifies listeners about synchronous command response.
     * @param packet the received packet
     */
    private void notifySynchronousCommand(final ZToolPacket packet) {
        final DoubleByte cmdId = packet.getCMD();
        synchronized (synchronousCommandListeners) {
            final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
            final SynchronousCommandListener listener = synchronousCommandListeners.get(id);
            if (listener != null) {
                listener.receivedCommandResponse(packet);
                synchronousCommandListeners.remove(id);
                synchronousCommandListeners.notifyAll();
            } else {
                LOGGER.warn("Received {} synchronous command response but no listeners were registered", id);
            }

        }
    }

    /**
     * Adds asynchronous command listener.
     * @param listener the listener
     * @return true if listener did not already exist.
     */
    public boolean addAsynchronousCommandListener(AsynchronousCommandListener listener) {
        boolean result = false;
        synchronized (asynchrounsCommandListeners) {
            result = asynchrounsCommandListeners.add(listener);
        }
        return result;
    }

    /**
     * Removes asynchronous command listener.
     * @param listener the listener
     * @return true if listener did not already exist.
     */
    public boolean removeAsynchronousCommandListener(AsynchronousCommandListener listener) {
        boolean result = false;
        synchronized (asynchrounsCommandListeners) {
            result = asynchrounsCommandListeners.remove(listener);
        }
        return result;
    }

    /**
     * Notifies listeners about asynchronous message.
     * @param packet the packet containing the message
     */
    private void notifyAsynchronousCommand(final ZToolPacket packet) {
        final AsynchronousCommandListener[] listeners;

        synchronized (asynchrounsCommandListeners) {
            listeners = asynchrounsCommandListeners.toArray(new AsynchronousCommandListener[]{});
        }

        for (final AsynchronousCommandListener listener : listeners) {
            try {
                listener.receivedAsynchronousCommand(packet);
            } catch (Throwable e) {
                LOGGER.error("Error in incoming asynchronous message processing.", e);
            }
        }
    }
}
