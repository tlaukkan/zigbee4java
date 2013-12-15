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
package it.cnr.isti.cc2480.virtual;

import com.itaca.ztool.api.ZToolPacketHandler;
import com.itaca.ztool.api.ZToolPacketParser;
import it.cnr.isti.cc2480.low.SerialHandler;
import it.cnr.isti.thread.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 * 
 */
public class Emulator implements SerialHandler {

    private final static Logger logger = LoggerFactory.getLogger(Emulator.class);
    
    private static final int INDEX_OF_END_OF_HEX_DATA = 50;
    private static final int INDEX_OF_START_OF_HEX_DATA = 1;

    private enum State {
        Loaded, FileOpened, Request, Answer, EndOfFileReached
    }

    class StreamItem {
        final long delta;
        final byte[] data;
        int used;
        
        StreamItem(long t, ArrayList<byte[]> d) {
            delta = t;
            int tot = 0;
            for ( byte[] slice : d ) {
                tot+=slice.length;
            }
            data = new byte[tot];
            tot = 0;
            for ( byte[] slice : d ) {
                System.arraycopy( slice, 0, data, tot, slice.length );
                tot+=slice.length;
            }
            used = 0;
        }
    }
    
    class EmulatorOutputStream extends OutputStream {

        @Override
        public synchronized void write( int b ) throws IOException {            
        	final StreamItem item;
        	synchronized (emulatorIO) {
                if( fromJavaIdx >= fromJava.size() ) return;
                
                item = fromJava.get( fromJavaIdx );				
			}

            if( item.data[item.used] == (byte ) b ) {
                item.used++;
            }
            if ( item.used == item.data.length ) {
            	synchronized (emulatorIO) {
	                fromJavaIdx++;
	                toJavaIdx++;
	                emulatorIO.notify();
            	}
                ThreadUtils.waitNonPreemptive(item.delta);
                synchronized ( parser ) {
                    parser.notify();
                }
            }
        }
        
    }
    
    class EmulatorInputStream extends InputStream {

    	/**
    	 * 
    	 * @return an {@link java.io.ByteArrayInputStream} with <b>all</b> the data parsed from the serial log
    	 * @since 0.6.0
    	 */
        public InputStream getFullInputStream() {
            int size = 0;
            for ( StreamItem chunck : toJava ) {
                size += chunck.data.length;
            }
            int idx = 0;
            byte[] big = new byte[size];
            for ( StreamItem chunck : toJava ) {
                System.arraycopy( chunck.data, 0, big, idx, chunck.data.length );
                idx += chunck.data.length;
            }
            return new ByteArrayInputStream( big );           
        }
        
        @Override
        public synchronized int available() throws IOException {
        	synchronized (emulatorIO) {
                if( toJavaIdx < 0  || toJavaIdx >= toJava.size() ) return 0;
                
                final StreamItem item = toJava.get( toJavaIdx );
                return item.data.length-item.used;
			}
        }

        @Override
        public synchronized int read() throws IOException {
        	StreamItem item;
        	synchronized (emulatorIO) {
        		if ( toJavaIdx >= toJava.size() ) {
        			return -1;
        		}
	            while( toJavaIdx < 0 ) {
	                try {
						emulatorIO.wait();
					} catch (InterruptedException e) {
					}
	            }
            	item = toJava.get( toJavaIdx );
	            while( item.used == item.data.length ){
	            	try {
						emulatorIO.wait();
					} catch (InterruptedException e) {
					}
	            }
        	}
            if ( item.used == 0 ) {
            	ThreadUtils.waitNonPreemptive(item.delta);
            }
            int r = ( item.data[item.used] + 0x100 ) & 0xFF;
            item.used++;
            return r;
        }
        
    }
    
    private State status;

    private final ArrayList<byte[]> buffer = new ArrayList<byte[]>();
    private final ArrayList<StreamItem> fromJava = new ArrayList<StreamItem>();
    private int fromJavaIdx = 0;
    private final ArrayList<StreamItem> toJava = new ArrayList<StreamItem>();
    private int toJavaIdx = -1;
    
    private BufferedReader reader;

    private final EmulatorInputStream eis;
    private final EmulatorOutputStream eos;
    private final Object emulatorIO = new Object(); 
    
    private boolean timeing;

	private Object parserLock = new Object();
    private ZToolPacketParser parser = null;



    /**
     * Only for JUnit purpose
     */
    Emulator( ) {
        eis = new EmulatorInputStream();
        eos = new EmulatorOutputStream();
    }

    /**
     * <b>NOTE:</b> Used only for testunit purpose
     * @since 0.6.0
     */
    EmulatorInputStream getEmulatorInputStream() {
    	return eis;
    }
    
    /**
     * <b>NOTE:</b> Used only for testunit purpose
     * @since 0.6.0
     */
    EmulatorOutputStream getEmulatorOutputStream() {
    	return eos;
    }
    
    public Emulator( String log ) throws IOException {
        this(new FileInputStream( log ) , false);
    }    

    public Emulator( InputStream in ) throws IOException {
        this( in, true );
    }
    
    public Emulator( InputStream in, boolean useTiming ) throws IOException {
        reader = new BufferedReader( new InputStreamReader( in ) );
        status = State.Loaded;
        simulate();
        eis = new EmulatorInputStream();
        eos = new EmulatorOutputStream();
        in.close();
        timeing = useTiming;
        reader.close();
    }       
    
    void simulate()
        throws IOException {
        status = State.FileOpened;
        String line;
        long delta = 0;
        while ( ( line = reader.readLine() ) != null ) {
            logger.debug( "Parsing line {}" , line );
            if ( line.contains( State.Request.toString() ) ) {
                if ( status == State.Answer ) {
                    StreamItem item = new StreamItem( delta, buffer );
                    buffer.clear();
                    toJava.add( item );
                }
                status = State.Request;
                delta = extractTimeMillis( line );
            } else if ( line.contains( State.Answer.toString() ) ) {
                if ( status == State.Request ) {
                    StreamItem item = new StreamItem( delta, buffer );
                    buffer.clear();
                    fromJava.add( item );
                }
                status = State.Answer;
                delta = extractTimeMillis( line );
            } else {
                if( line.length() == 0 ) continue;
                if( line.contains( "Port" )) continue;
                final byte[] data = extractBytes( line );
                buffer.add(data);
            }
        }
        if ( status == State.Answer ) {
            StreamItem item = new StreamItem( delta, buffer );
            buffer.clear();
            toJava.add( item );
        } else if ( status == State.Request ) {
            StreamItem item = new StreamItem( delta, buffer );
            buffer.clear();
            fromJava.add( item );
        }
        status = State.EndOfFileReached;
    }

    byte[] extractBytes( String line ) {
        logger.trace( "Extracting bytes from {}", line );
        line = line.substring( INDEX_OF_START_OF_HEX_DATA, INDEX_OF_END_OF_HEX_DATA );
        String[] bytes = line.split( " " );
        byte[] result = new byte[bytes.length];
        for ( int i = 0; i < result.length; i++ ) {
            result[i] = (byte) Short.parseShort( bytes[i], 16 );
        }
        return result;
    }

    long extractTimeMillis( String line ) {
        logger.trace( "Extracting time from {}", line );
        int s, e, d;
        s = line.indexOf( '+' );
        if ( s == -1 ) return 0;
        e = line.indexOf( ' ', s + 1 );
        d = line.indexOf( '.', s + 1 );
        long result = Long.parseLong( line.substring( s + 1, d ) ) * 1000;;
        result = result + Long.parseLong( line.substring( d + 1, e ) ) / 10;
        return result;
    }

	public void close() {
        // shutdown parser thread
        if ( parser != null ) {
            parser.close();
        }
	}

	public OutputStream getOutputStream() {
		return eos;		
	}

	public void open(String port, int baudRate, ZToolPacketHandler packethandler) {
	    if ( timeing ){
	        parser = new ZToolPacketParser( eis, packethandler);
	    } else {
            parser = new ZToolPacketParser( eis.getFullInputStream(), packethandler);
	    }
	}
	
	public ZToolPacketParser getParser() {
		return parser;
	}
}
