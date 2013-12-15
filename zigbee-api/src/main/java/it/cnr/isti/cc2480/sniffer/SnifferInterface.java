/*
   Copyright 2010-2010 CNR-ISTI, http://isti.cnr.it
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
package it.cnr.isti.cc2480.sniffer;

import com.itaca.ztool.api.ZToolPacket;

/**
 * This is class is MUST be implemented to receive all the data is sent or received by the Driver.<br />
 * At the moment only {@link it.cnr.isti.cc2480.low.HWLowLevelDriver} support the sniffing of packets.
 *
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 42 $ ($LastChangedDate: 2010-09-23 15:21:48 +0300 (Thu, 23 Sep 2010) $)
 * @since 0.1.0
 *
 */
public interface SnifferInterface {

	/**
	 * The driver using the sniffer MUST invoke this method before notifying any packet to the
	 * implementor of {@link SnifferInterface}. The {@link SnifferInterface} implementation
	 * should implement this method for initialize all the resources required by the sniffing.
	 *
	 * @return true if the {@link SnifferInterface} will be able to receive packet
	 */
	public boolean initialize();

	/**
	 * The driver using the sniffer MUST invoke this method to allow the implementor of
	 * {@link SnifferInterface} to release and complete all the pending operation.
	 * 
	 */
	public void finalize();
	
	/**
	 * This method is invoked from the driver when a packet from the hardware is received<br>
	 * The driver MUST invoke the packet before fordwaring it to any other class.<br>
	 * Only bad formatted packet MAY be discarded and not notified by the Driver<br>
	 * 
	 * @param packet the {@link ZToolPacket} received from the hardware
	 */
	public void incomingPacket(ZToolPacket packet);

	/**
	 * This method is invoked from the driver when a packet should be sent to the hardware<br>
	 * The driver MUST invoke the packet before fordwaring it the hardware.<br>
	 * Only bad formatted packet MAY be discarded and not notified by the Driver<br>
	 * 
	 * @param packet the {@link ZToolPacket} received from the hardware
	 */
	public void outcomingPacket(ZToolPacket packet);	
	
}
