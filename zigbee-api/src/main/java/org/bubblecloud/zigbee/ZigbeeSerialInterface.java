package org.bubblecloud.zigbee;

import com.itaca.ztool.api.ZToolException;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.api.ZToolPacketHandler;
import com.itaca.ztool.util.DoubleByte;
import it.cnr.isti.cc2480.high.AsynchrounsCommandListener;
import it.cnr.isti.cc2480.high.SynchrounsCommandListner;
import it.cnr.isti.cc2480.low.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * ZigbeeSerialInterface is used to open connection to ZigBee network.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZigbeeSerialInterface implements ZToolPacketHandler {
    /** The logger. */
    private final static Logger LOGGER = LoggerFactory.getLogger(ZigbeeSerialInterface.class);
    /** The serial port name. */
    final String serialPortName;

    final SerialPort serialPort;

    /**
     * Constructor for configuring the Zigbee Network connection parameters.
     *
     * @param serialPortName the serial port name.
     */
    public ZigbeeSerialInterface(String serialPortName) {
        this.serialPortName = serialPortName;
        serialPort = new SerialPort();
    }

    /**
     * Opens connection to Zigbee Network.
     *
     * @return true if connection open was success.
     */
    public boolean open() {
        try {
            serialPort.open(serialPortName, 115200, this);
            return true;
        } catch (final ZToolException e) {
            LOGGER.error("Failed to open serial port.", e);
            return false;
        }
    }

    /**
     * Closes connection ot Zigbee Network.
     */
    public void close() {
        serialPort.close();
    }

    // ZToolPacketHandler ------------------------------------------------------------------

    public void error(Throwable th) {
        LOGGER.error("Error in packet parsing: ", th);
    }

    public void handlePacket(final ZToolPacket packet) {
        final DoubleByte cmdId = packet.getCMD();
        switch (cmdId.getMsb() & 0xE0) {
            case  0x40: { //We received a AREQ
                LOGGER.debug("We received a AREQ");
                notifyAsynchrounsCommand(packet);
            } break;

            case 0x60: { //We received a SRSP
                LOGGER.debug("We received a SRSP");
                notifySynchrounsCommand(packet);
            } break;

            default:{
                throw new IllegalStateException("Recieved a Command from CC2480 with an unknow type"+packet);
            }
        }
    }

    // Driver ------------------------------------------------------------------------------


    private void sendPacket( ZToolPacket packet )
            throws IOException {

        //FIX Sending byte instead of int
        LOGGER.debug( "Sending Packet {} {} ", packet.getClass(), packet.toString() );

        final int[] pck = packet.getPacket();
        final OutputStream out = serialPort.getOutputStream();

        if (out == null) {
            // Socket has not been opened or is already closed.
            return;
        }

        //Only a packet at the time can be sent, otherwise link communication will be mess up
        synchronized ( out ) {

            for ( int i = 0; i < pck.length; i++ ) {
                out.write( pck[i] );
            }
            out.flush();
        }
    }

    private final Hashtable<Short, SynchrounsCommandListner> pendingSREQ
            = new Hashtable<Short, SynchrounsCommandListner>();

    private final HashSet<AsynchrounsCommandListener> listeners
            = new HashSet<AsynchrounsCommandListener>();

    private final boolean supportMultipleSynchrounsCommand = false;

    private final HashMap<SynchrounsCommandListner, Long> timouts = new HashMap<SynchrounsCommandListner, Long>();

    private void cleanExpiredListener(){
        final long now = System.currentTimeMillis();
        final ArrayList<Short> expired = new ArrayList<Short>();
        synchronized (pendingSREQ) {
            Iterator<Map.Entry<Short, SynchrounsCommandListner>> i = pendingSREQ.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<Short, SynchrounsCommandListner> entry = i.next();

                final long expiration = timouts.get(entry.getValue());
                if ( expiration != -1L && expiration < now ) {
                    expired.add(entry.getKey());
                }
            }

            for (Short key : expired) {
                pendingSREQ.remove(key);
            }
            pendingSREQ.notifyAll();
        }
    }

    public void sendSynchrounsCommand(ZToolPacket packet, SynchrounsCommandListner listener, long timeout) throws IOException {
        if ( timeout == -1L ) {
            timouts.put(listener, -1L);
        } else {
            final long expirationTime = System.currentTimeMillis() + timeout;
            timouts.put(listener, expirationTime);
        }
        m_sendSynchrounsCommand(packet, listener);
    }

    private void m_sendSynchrounsCommand(ZToolPacket packet, SynchrounsCommandListner listner) throws IOException{
        final DoubleByte cmdId = packet.getCMD();
        final int value = (cmdId.getMsb() & 0xE0);
        if ( value != 0x20  ) {
            throw new IllegalArgumentException("You are trying to send a non SREQ packet. "
                    +"Evaluated "+value+" instead of "+0x20+"\nPacket "+packet.getClass().getName()+"\n"+packet
            );
        }
        //LOGGER.debug("Preparing to send SynchrounsCommand {} ", packet);
        cleanExpiredListener();
        if ( supportMultipleSynchrounsCommand ) {
            synchronized (pendingSREQ) {
                final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
                while(pendingSREQ.get(cmdId) != null) {
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
        }else{
            synchronized (pendingSREQ) {
                final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
                //while(pendingSREQ.isEmpty() == false || pendingSREQ.size() == 1 && pendingSREQ.get(id) == listner ) {
                while(pendingSREQ.isEmpty() == false ) {
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

    public void sendAsynchrounsCommand(ZToolPacket packet) throws IOException{
        int value = (packet.getCMD().getMsb() & 0xE0);
        if ( value != 0x40  ) {
            throw new IllegalArgumentException("You are trying to send a non AREQ packet. "
                    +"Evaluated "+value+" instead of "+0x40+"\nPacket "+packet.getClass().getName()+"\n"+packet
            );
        }

        sendPacket(packet);
    }



    protected void notifySynchrounsCommand(ZToolPacket packet) {
        //PRE: We received a SRSP packet
        final DoubleByte cmdId = packet.getCMD();

        synchronized (pendingSREQ) {
            final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
            //TODO Invoke in a separated Thread?!?!
            final SynchrounsCommandListner listener = pendingSREQ.get(id);
            if(listener != null){
                listener.receivedCommandResponse(packet);
                pendingSREQ.remove(id);
                pendingSREQ.notifyAll();
            }else{
				/*
				 * This happen only if we receive a synchronous command
				 * response but no listener registered in advance
				 * for instance we a LowLevel driver and HighLevel driver
				 * are working on same port
				 */
                LOGGER.warn("Recieved {} synchronous command response but no listeners were registered", id);
            }

        }
    }

    public boolean addAsynchrounsCommandListener(AsynchrounsCommandListener listener){
        boolean result = false;
        synchronized (listeners) {
            result = listeners.add(listener);
        }
        return result;
    }

    public boolean removeAsynchrounsCommandListener(AsynchrounsCommandListener listener){
        boolean result = false;
        synchronized (listeners) {
            result = listeners.remove(listener);
        }
        return result;
    }

    protected void notifyAsynchrounsCommand(ZToolPacket packet){
        //PRE: We received a AREQ packet
        //XXX Should we split to Multi-threaded notifier to speed up everything
        AsynchrounsCommandListener[] copy;

        synchronized (listeners) {
            copy = listeners.toArray(new AsynchrounsCommandListener[]{});
        }

        for (AsynchrounsCommandListener listener : copy) {
            try{
                listener.receivedAsynchrounsCommand(packet);
            }catch(Throwable e){
                LOGGER.error("Error genereated by notifyAsynchrounsCommand {}", e);
            }
        }
    }
}
