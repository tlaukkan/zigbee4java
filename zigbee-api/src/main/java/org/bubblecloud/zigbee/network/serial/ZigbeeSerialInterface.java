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

import org.bubblecloud.zigbee.network.SynchrounsCommandListener;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.network.packet.ZToolPacketHandler;
import org.bubblecloud.zigbee.network.packet.ZToolPacketParser;
import org.bubblecloud.zigbee.util.DoubleByte;
import org.bubblecloud.zigbee.network.AsynchrounsCommandListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * ZigbeeSerialInterface is used to startup connection to ZigBee network.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigbeeSerialInterface implements ZToolPacketHandler {
    /**
     * The logger.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ZigbeeSerialInterface.class);
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
     * Constructor for configuring the Zigbee Network connection parameters.
     *
     * @param serialPortName the serial port name.
     */
    public ZigbeeSerialInterface(String serialPortName) {
        this.serialPortName = serialPortName;
        serialPort = new SerialPortImpl();
    }

    /**
     * Opens connection to Zigbee Network.
     *
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
     * Closes connection ot Zigbee Network.
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

    // ZToolPacketHandler ------------------------------------------------------------------

    public void error(Throwable th) {
        LOGGER.error("Error in packet parsing: ", th);
    }

    public void handlePacket(final ZToolPacket packet) {
        final DoubleByte cmdId = packet.getCMD();
        switch (cmdId.getMsb() & 0xE0) {
            case 0x40: { //We received a message
                LOGGER.trace("<-- {} ({})", packet.getClass().getSimpleName(), packet);
                notifyAsynchrounsCommand(packet);
            }
            break;

            case 0x60: { //We received a SRSP
                LOGGER.trace("<- {} ({})", packet.getClass().getSimpleName(), packet);
                notifySynchrounsCommand(packet);
            }
            break;

            default: {
                LOGGER.error("Incoming unknown packet. ({}) ({})", packet.getClass().getSimpleName(), packet);
            }
        }
    }

    // Driver ------------------------------------------------------------------------------


    private void sendPacket(ZToolPacket packet)
            throws IOException {


        //FIX Sending byte instead of int
        LOGGER.trace("-> {} ({}) ", packet.getClass().getSimpleName(), packet.toString());

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

    private final Hashtable<Short, SynchrounsCommandListener> pendingSREQ
            = new Hashtable<Short, SynchrounsCommandListener>();

    private final HashSet<AsynchrounsCommandListener> listeners
            = new HashSet<AsynchrounsCommandListener>();

    private final boolean supportMultipleSynchrounsCommand = false;

    private final HashMap<SynchrounsCommandListener, Long> timouts = new HashMap<SynchrounsCommandListener, Long>();

    private void cleanExpiredListener() {
        final long now = System.currentTimeMillis();
        final ArrayList<Short> expired = new ArrayList<Short>();
        synchronized (pendingSREQ) {
            Iterator<Map.Entry<Short, SynchrounsCommandListener>> i = pendingSREQ.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<Short, SynchrounsCommandListener> entry = i.next();

                final long expiration = timouts.get(entry.getValue());
                if (expiration != -1L && expiration < now) {
                    expired.add(entry.getKey());
                }
            }

            for (Short key : expired) {
                pendingSREQ.remove(key);
            }
            pendingSREQ.notifyAll();
        }
    }

    public void sendSynchrounsCommand(ZToolPacket packet, SynchrounsCommandListener listener, long timeout) throws IOException {
        if (timeout == -1L) {
            timouts.put(listener, -1L);
        } else {
            final long expirationTime = System.currentTimeMillis() + timeout;
            timouts.put(listener, expirationTime);
        }
        m_sendSynchrounsCommand(packet, listener);
    }

    private void m_sendSynchrounsCommand(ZToolPacket packet, SynchrounsCommandListener listner) throws IOException {
        final DoubleByte cmdId = packet.getCMD();
        final int value = (cmdId.getMsb() & 0xE0);
        if (value != 0x20) {
            throw new IllegalArgumentException("You are trying to send a non SREQ packet. "
                    + "Evaluated " + value + " instead of " + 0x20 + "\nPacket " + packet.getClass().getName() + "\n" + packet
            );
        }
        //LOGGER.debug("Preparing to send SynchrounsCommand {} ", packet);
        cleanExpiredListener();
        if (supportMultipleSynchrounsCommand) {
            synchronized (pendingSREQ) {
                final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
                while (pendingSREQ.get(cmdId) != null) {
                    try {
                        LOGGER.debug("Waiting for other request {} to complete", id);
                        pendingSREQ.wait(500);
                        cleanExpiredListener();
                    } catch (InterruptedException ignored) {
                    }
                }
                //No listener register for this type of command, so no pending request. We can proceed
                //LOGGER.debug("Put pendingSREQ listener for {} command", id);
                pendingSREQ.put(id, listner);
            }
        } else {
            synchronized (pendingSREQ) {
                final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
                //while(pendingSREQ.isEmpty() == false || pendingSREQ.size() == 1 && pendingSREQ.get(id) == listner ) {
                while (pendingSREQ.isEmpty() == false) {
                    try {
                        LOGGER.debug("Waiting for other request to complete");
                        pendingSREQ.wait(500);
                        cleanExpiredListener();
                    } catch (InterruptedException ignored) {
                    }
                }
                //No listener at all registered so this is the only command that we are waiting for a response
                LOGGER.debug("Put pendingSREQ listener for {} command", id);
                pendingSREQ.put(id, listner);
            }
        }
        LOGGER.debug("Sending SynchrounsCommand {} ", packet);
        sendPacket(packet);
    }

    public void sendAsynchrounsCommand(ZToolPacket packet) throws IOException {
        int value = (packet.getCMD().getMsb() & 0xE0);
        if (value != 0x40) {
            throw new IllegalArgumentException("You are trying to send a non AREQ packet. "
                    + "Evaluated " + value + " instead of " + 0x40 + "\nPacket " + packet.getClass().getName() + "\n" + packet
            );
        }

        sendPacket(packet);
    }


    protected void notifySynchrounsCommand(ZToolPacket packet) {
        final DoubleByte cmdId = packet.getCMD();

        synchronized (pendingSREQ) {
            final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
            //TODO Invoke in a separated Thread?!?!
            final SynchrounsCommandListener listener = pendingSREQ.get(id);
            if (listener != null) {
                listener.receivedCommandResponse(packet);
                pendingSREQ.remove(id);
                pendingSREQ.notifyAll();
            } else {
                /*
				 * This happen only if we receive a synchronous command
				 * response but no listener registered in advance
				 * for instance we a LowLevel driver and HighLevel driver
				 * are working on same port
				 */
                LOGGER.warn("Received {} synchronous command response but no listeners were registered", id);
            }

        }
    }

    public boolean addAsynchrounsCommandListener(AsynchrounsCommandListener listener) {
        boolean result = false;
        synchronized (listeners) {
            result = listeners.add(listener);
        }
        return result;
    }

    public boolean removeAsynchrounsCommandListener(AsynchrounsCommandListener listener) {
        boolean result = false;
        synchronized (listeners) {
            result = listeners.remove(listener);
        }
        return result;
    }

    protected void notifyAsynchrounsCommand(ZToolPacket packet) {
        AsynchrounsCommandListener[] copy;

        synchronized (listeners) {
            copy = listeners.toArray(new AsynchrounsCommandListener[]{});
        }

        for (AsynchrounsCommandListener listener : copy) {
            try {
                listener.receivedAsynchrounsCommand(packet);
            } catch (Throwable e) {
                LOGGER.error("Error genereated by notifyAsynchrounsCommand {}", e);
            }
        }
    }
}
