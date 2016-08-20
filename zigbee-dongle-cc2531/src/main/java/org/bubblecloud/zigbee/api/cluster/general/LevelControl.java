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

package org.bubblecloud.zigbee.api.cluster.general;

import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.general.event.CurrentLevelListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 *
 */
public interface LevelControl extends Cluster {

	public Attribute getCurrentLevel();
	public Attribute getRemainingTime();
	public Attribute getOnOffTransitionTime();
	public Attribute getOnLevel();
	
	/**
	 * 
	 * @param listener The {@link org.bubblecloud.zigbee.api.cluster.general.event.CurrentLevelListener} to subscribe for events
	 * @since 0.2.0
	 */
	public boolean subscribe(CurrentLevelListener listener);
	
	/**
	 * 
	 * @param listener The {@link CurrentLevelListener} to unsubscribe
	 * @since 0.2.0
	 */
	public boolean unsubscribe(CurrentLevelListener listener);
	
	
	public void moveToLevel(short level, int time) throws ZigBeeDeviceException;
	public void move(byte mode, short rate) throws ZigBeeDeviceException;
	public void step(byte mode, short step, int time) throws ZigBeeDeviceException;
	public void stop() throws ZigBeeDeviceException;

	/**
	 * @since 0.6.0
	 */
	public void moveToLevelWithOnOff(short level, int time) throws ZigBeeDeviceException;

	/**
	 * @since 0.6.0
	 */
	public void moveWithOnOff(byte mode, short rate) throws ZigBeeDeviceException;

	/**
	 * @since 0.6.0
	 */
	public void stepWithOnOff(byte mode, short step, int time) throws ZigBeeDeviceException;
	
	/**
	 * @since 0.6.0
	 */
	public void stopWithOnOff() throws ZigBeeDeviceException;
	
}
