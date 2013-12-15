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

package it.cnr.isti.cc2480.low;

import com.itaca.ztool.api.ZToolException;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.api.ZToolPacketHandler;
import it.cnr.isti.cc2480.sniffer.FilePacketSniffer;
import it.cnr.isti.cc2480.sniffer.GUIPacketSniffer;
import it.cnr.isti.cc2480.sniffer.SnifferInterface;
import it.cnr.isti.thread.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 * 
 */
public class HWLowLevelDriver
    implements ZToolPacketHandler {

	public enum HWLowLevelDriverState {
		LOADED, OPENING, OPENED, CLOSING, CLOSED
	}
	
    private final static Logger logger = LoggerFactory.getLogger(HWLowLevelDriver.class);

    private final static Logger profiler = LoggerFactory.getLogger("profiling." + HWLowLevelDriver.class.getName());

    /**
     * The name of the system properties which minimum gap (expressed in milliseconds) between two packets.<br>
     * If set to 0 the Driver has no minimum gap and it runs at maximum speed.<br>
     * If property is not set, the default value is {@link HWLowLevelDriver#MILLISECONDS_PER_PACKET_DEFAULT}
     */
    public static final String MILLISECONDS_PER_PACKET_KEY = "it.cnr.isti.cc2480.low.milliseconds_per_packet";

    public static final String MILLISECONDS_PER_PACKET_DEFAULT = "0";

    /**
     * The name of the system properties which contains the full qualify class name of the {@link SnifferInterface}
     * implementation that MUST be loaded by this class.<br>
     * If the value is an <code>""</code> no {@link SnifferInterface} are loaded.<br>
     * If property is not set, the default value is {@link HWLowLevelDriver#PACKET_SNIFFER_DEFAULT} See also:
     * {@link GUIPacketSniffer}, {@link FilePacketSniffer}
     */
    public static final String PACKET_SNIFFER_KEY = "it.cnr.isti.cc2480.low.packet_sniffer";

    public static final String PACKET_SNIFFER_DEFAULT = "";

    /**
     * The name of the system properties which contains the full qualify class name of the {@link SnifferInterface}
     * implementation that MUST be loaded by this class.<br>
     * If the value is an <code>""</code> no {@link SnifferInterface} are loaded.<br>
     * If property is not set, the default value is {@link HWLowLevelDriver#PACKET_SNIFFER_DEFAULT} See also:
     * {@link GUIPacketSniffer}, {@link FilePacketSniffer}
     */
    public static final String SERIAL_HANLDER_KEY = "it.cnr.isti.cc2480.low.serial_handler";

    public static final String SERIAL_HANLDER_DEFAULT = "it.cnr.isti.cc2480.low.SerialPort";
    
    private List<Throwable> errorList = new ArrayList<Throwable>();

    private final HashSet<PacketListener> listeners = new HashSet<PacketListener>();

    private long lastSentPacketAt = -1;

    private long milliSecondsPerPacket = Long.parseLong( MILLISECONDS_PER_PACKET_DEFAULT );

    private String serialName;

    private int rate;
    private HWLowLevelDriverState status = HWLowLevelDriverState.LOADED;

    private SerialHandler serialHandler;
    private SnifferInterface sniffer;

    //private final byte[] buffer = new byte[256];

    public void open( String port, int baudRate) throws ZToolException {
    	
    	if ( ! ( status == HWLowLevelDriverState.LOADED || status == HWLowLevelDriverState.CLOSED ) ) {
    		throw new IllegalStateException("Driver already opened, current status is " + status);
    	}
    	
    	status = HWLowLevelDriverState.OPENING;
        logger.debug( "Opening {} ", this.getClass() );
        serialName = port;
        rate = baudRate;
        milliSecondsPerPacket = Long.parseLong( 
        		System.getProperty( MILLISECONDS_PER_PACKET_KEY, MILLISECONDS_PER_PACKET_DEFAULT ) 
        );

        createSniffer();
        createSerialHandler();
        
        logger.debug( "Opening port {}@{}", serialName, rate );
        serialHandler.open( serialName, rate, this );
        logger.debug( "Opened port {}@{}", serialName, rate );
        logger.debug( "Opened {}", this );
        
    	status = HWLowLevelDriverState.OPENED;
    } 

    /**
     * @throws ZToolException 
	 * 
	 */
	private void createSerialHandler() throws ZToolException {
		if( serialHandler != null ) {
			logger.info("Serial Handler already specified, using {}", serialHandler.getClass().getName());
			return;
		}
        String claz = System.getProperty( SERIAL_HANLDER_KEY, SERIAL_HANLDER_DEFAULT );
        try {
            logger.debug( "Loading serial handler {}", claz );
            serialHandler = (SerialHandler) Class.forName( claz ).newInstance();
        } catch ( Exception e ) {
            logger.error( "Unable to laod the selected SerialHandler " + claz + " due to exception", e );
            throw new ZToolException("Unable to laod SerialHandler", e);
        }
	}

	private void createSniffer() {
        String claz = System.getProperty( PACKET_SNIFFER_KEY, PACKET_SNIFFER_DEFAULT );
        if ( "".equals( claz ) == false ) {
            try {
                logger.debug( "Loading sniffer {}", claz );
                sniffer = (SnifferInterface) Class.forName( claz ).newInstance();
                logger.debug( "Created sniffer {}", sniffer );
                if ( sniffer.initialize() == false ) {
                    logger.error( "Failed to initialize the sniffer {}", sniffer );
                    sniffer = null;
                }
            }
            catch ( Exception e ) {
                logger.error( "Unable to laod the selected sniffer " + claz + " due to exception", e );
                sniffer = null;
            }
        } else {
        	logger.info("SNIFFER Disabled: No sniffer class specified");
        }
    }

    /**
     * 
     * @return the number of milliseconds that the driver wait before sending a new frame to the dongle
     * @since 0.6.0
     */
    public long getMilliSecondsPerPacket() {
        return milliSecondsPerPacket;
    }

    
    
    /**
     * 
     * @param packetSniffer
     * @since 0.6.0
     */
    public void setZToolPacketSniffer(SnifferInterface packetSniffer){
    	if ( packetSniffer == null ) {
    		logger.debug("Disabling packet sniffing");
    	} else if ( sniffer != null ) {
    		logger.debug("Replaceing sniffer {} with {}", sniffer, packetSniffer);
    	} else {
    		logger.debug("Enabling packet sniffing with {}", packetSniffer);
    	}
		sniffer = packetSniffer;
    }
    
    /**
     * 
     * @return
     * @since 0.6.0
     */
    public SnifferInterface getZToolPacketSniffer() {
    	return sniffer;
    }
    
    /**
     * 
     * @return
     * @since 0.6.0
     */
    public SerialHandler getSerialHandler() {
    	return serialHandler;
    }

    /**
     * 
     * @param handler
     * @since 0.6.0
     */
    public void setSerialHandler(SerialHandler handler) {
    	if ( ! ( status == HWLowLevelDriverState.LOADED || status == HWLowLevelDriverState.CLOSED ) ) {
    		throw new IllegalStateException("SerailHandler cannot be changed when driver is active, current state is " + status);
    	}
    	serialHandler = handler;
    }
    
    
    /**
     * Set the number of milliseconds that must elapse before sending a new frame to the dongle
     * 
     * @param milliSecondsPerPacket the number of milliseconds, zero or negative values disable this check
     * @since 0.6.0
     */
    public void setMilliSecondsPerPacket( long milliSecondsPerPacket ) {
        this.milliSecondsPerPacket = milliSecondsPerPacket;
    }

    public boolean addPacketListener( PacketListener listener ) {
        boolean result = false;
        synchronized ( listeners ) {
            result = listeners.add( listener );
        }
        return result;
    }

    public boolean removePacketListener( PacketListener listener ) {
        boolean result = false;
        synchronized ( listeners ) {
            result = listeners.remove( listener );
        }
        return result;
    }

    protected void notifyPacketListeners( ZToolPacket packet ) {
        //XXX Should we split to Multi-threaded notifier to speed up everything
        PacketListener[] copy;

        synchronized ( listeners ) {
            copy = listeners.toArray( new PacketListener[] {} );
        }

        for ( PacketListener listener : copy ) {
            try {
                listener.packetReceived( packet );
            }
            catch ( Throwable e ) {
                logger.error("Error genereated by notifyPacketListeners {}", e );
            }
        }
    }

    public void sendPacket( ZToolPacket packet )
        throws IOException {
        if ( milliSecondsPerPacket > 0 ) {
            forceMaxiumumPacketRate();
        }
        if ( sniffer != null ) {
            sniffer.outcomingPacket( packet );
        }

        //FIX Sending byte instead of int
        logger.debug( "Sending Packet {} {} ", packet.getClass(), packet.toString() );
        profiler.info( "sendPacket(ZToolPacket packet): call" );

        final int[] pck = packet.getPacket();
        final OutputStream out = serialHandler.getOutputStream();
        //Only a packet at the time can be sent, otherwise link communication will be mess up
        synchronized ( out ) {
            profiler.info( "sendPacket(ZToolPacket packet): start" );
            /*
             * for (int i = 0; i < pck.length; i++) {
             * buffer [i] = (byte) pck[i];
             * }
             * out.write(buffer);
             */

            for ( int i = 0; i < pck.length; i++ ) {
                out.write( pck[i] );
            }
            out.flush();
            profiler.info( "sendPacket(ZToolPacket packet): finished" );
        }
    }

    private void forceMaxiumumPacketRate() {
        long waitNextSlot = System.currentTimeMillis() - lastSentPacketAt - milliSecondsPerPacket;
        ThreadUtils.waitNonPreemptive( waitNextSlot );
        lastSentPacketAt = System.currentTimeMillis();
    }

    public void handlePacket( ZToolPacket packet ) {
        profiler.info( "handlePacket(ZToolPacket packet): callback" );
        if ( sniffer != null ) {
            sniffer.incomingPacket( packet );
        }
        notifyPacketListeners( packet );
    }

    public void error( Throwable th ) {
        errorList.add( th );
    }

    public List<Throwable> getErrorList() {
        return errorList;
    }

    /**
     * Shuts down RXTX and input stream threads
     */
    public void close() {
    	status = HWLowLevelDriverState.CLOSING;    	
        logger.debug( "Closing {} ", this.getClass().getName() );
        serialHandler.close();
        logger.debug( "Closed {} ", this.getClass().getName() );
        if ( sniffer != null ) {
            sniffer.finalize();
        }        
    	status = HWLowLevelDriverState.CLOSED;    	
    }

    public String toString() {
        return this.getClass().getName() + "[" + serialName + "," + rate + "," + milliSecondsPerPacket + "]";
    }
}
