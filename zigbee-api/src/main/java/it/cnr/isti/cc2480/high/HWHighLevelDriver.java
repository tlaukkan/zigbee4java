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

package it.cnr.isti.cc2480.high;

import com.itaca.ztool.api.ZToolException;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;
import it.cnr.isti.cc2480.low.HWLowLevelDriver;
import it.cnr.isti.cc2480.low.PacketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:michele.girolami@isti.cnr.it">Michele Girolami</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 *
 */
public class HWHighLevelDriver {

	private final static Logger logger = LoggerFactory.getLogger(HWHighLevelDriver.class);
	private final static Logger profiler = LoggerFactory.getLogger("profiling." + HWHighLevelDriver.class.getName());
	
    private class InternalPacketListener implements PacketListener {
    	
    	private final HWHighLevelDriver parent;
    	
    	private InternalPacketListener(HWHighLevelDriver parent){
    		this.parent = parent;
    	}
    	
    	public void packetReceived(ZToolPacket packet) {
    		final DoubleByte cmdId = packet.getCMD();
    		switch (cmdId.getMsb() & 0xE0) {
    			case  0x40: { //We received a AREQ
    				logger.debug("We received a AREQ");
    				parent.notifyAsynchrounsCommand(packet);
    			} break;
    			
    			case 0x60: { //We received a SRSP
    				logger.debug("We received a SRSP");
    				parent.notifySynchrounsCommand(packet);				
    			} break;
    			
    			default:{
    				throw new IllegalStateException("Recieved a Command from CC2480 with an unknow type"+packet);
    			}
    		}
    	}    	
    }
    
    private final InternalPacketListener internalListener 
    	= new InternalPacketListener(this);
    
    private final HWLowLevelDriver driver;


	private final Hashtable<Short, SynchrounsCommandListner> pendingSREQ 
		= new Hashtable<Short, SynchrounsCommandListner>();

	private final HashSet<AsynchrounsCommandListener> listeners
		= new HashSet<AsynchrounsCommandListener>();

	private final boolean supportMultipleSynchrounsCommand = false;

	private final HashMap<SynchrounsCommandListner, Long> timouts = new HashMap<SynchrounsCommandListner, Long>();
	
	public HWHighLevelDriver(HWLowLevelDriver driver){
		logger.debug("Creating {} bound to {}", this.getClass().getName(), driver);
		this.driver = driver;
		driver.addPacketListener(internalListener);
		logger.debug("Registered as {} to {}", PacketListener.class.getName(), this.getClass().getName());
	}
	
	public void close() {
		driver.close();
	}

	public void error(Throwable th) {
		driver.error(th);
	}

	public void open(String port, int baudRate) throws ZToolException {
		driver.open(port, baudRate);
		driver.addPacketListener(internalListener);
	}

	private void cleanExpiredListener(){
		final long now = System.currentTimeMillis();
		final ArrayList<Short> expired = new ArrayList<Short>();
		synchronized (pendingSREQ) {
			Iterator<Entry<Short, SynchrounsCommandListner>> i = pendingSREQ.entrySet().iterator();			
			while (i.hasNext()) {
				Entry<Short, SynchrounsCommandListner> entry = i.next();
				
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

	public void sendSynchrounsCommand(ZToolPacket packet, SynchrounsCommandListner listener, long timeout) throws IOException{
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
		profiler.info(" m_sendSynchrounsCommand(ZToolPacket packet, SynchrounsCommandListner listner): called");
		logger.debug("Preparing to send SynchrounsCommand {} ", packet);
		cleanExpiredListener();
		if ( supportMultipleSynchrounsCommand ) {
			synchronized (pendingSREQ) {
				final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
				while(pendingSREQ.get(cmdId) != null) {
					try {
						logger.debug("Waiting for other request {} to complete", id);
						pendingSREQ.wait(500);
						cleanExpiredListener();
					} catch (InterruptedException ignored) {
					}
				}			
				//No listener register for this type of command, so no pending request. We can proceed
				logger.debug("Put pendingSREQ listener for {} command", id);
				pendingSREQ.put(id, listner);			
			}
		}else{
			synchronized (pendingSREQ) {
				final short id = (short) (cmdId.get16BitValue() & 0x1FFF);
				//while(pendingSREQ.isEmpty() == false || pendingSREQ.size() == 1 && pendingSREQ.get(id) == listner ) {
				while(pendingSREQ.isEmpty() == false ) {
					try {
						logger.debug("Waiting for other request to complete");
						pendingSREQ.wait(500);
						cleanExpiredListener();
					} catch (InterruptedException ignored) {
					}
				}			
				//No listener at all registered so this is the only command that we are waiting for a response
				logger.debug("Put pendingSREQ listener for {} command", id);
				pendingSREQ.put(id, listner);			
			}
		}
		logger.debug("Sending SynchrounsCommand {} ", packet);
		profiler.info("m_sendSynchrounsCommand(ZToolPacket packet, SynchrounsCommandListner listner): acquired lock");
		driver.sendPacket(packet);
	}
	
	public void sendAsynchrounsCommand(ZToolPacket packet) throws IOException{
		int value = (packet.getCMD().getMsb() & 0xE0);
		if ( value != 0x40  ) {
			throw new IllegalArgumentException("You are trying to send a non AREQ packet. "
					+"Evaluated "+value+" instead of "+0x40+"\nPacket "+packet.getClass().getName()+"\n"+packet
			);
		}
		
		driver.sendPacket(packet);
	}



	protected void notifySynchrounsCommand(ZToolPacket packet) {
		//PRE: We received a SRSP packet
		final DoubleByte cmdId = packet.getCMD();
		profiler.info("notifySynchrounsCommand(ZToolPacket packet): called");
		
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
				logger.warn("Recieved {} synchronous command response but no listeners were registered", id);
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
		profiler.info("notifyAsynchrounsCommand(ZToolPacket packet): called");
   	
    	synchronized (listeners) {
    		copy = listeners.toArray(new AsynchrounsCommandListener[]{});
		}
    	
    	for (AsynchrounsCommandListener listener : copy) {
    		try{
    			listener.receivedAsynchrounsCommand(packet);
    		}catch(Throwable e){
    			logger.error("Error genereated by notifyAsynchrounsCommand {}", e);	
    		}
		}
    }
    
}
