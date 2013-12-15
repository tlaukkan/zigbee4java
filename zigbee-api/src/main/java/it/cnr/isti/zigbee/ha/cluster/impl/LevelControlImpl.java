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

package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.LevelControl;
import it.cnr.isti.zigbee.ha.cluster.glue.general.event.CurrentLevelListener;
import it.cnr.isti.zigbee.ha.cluster.impl.event.CurrentLevelBridgeListeners;
import it.cnr.isti.zigbee.ha.driver.core.ReportingConfiguration;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.impl.general.LevelControlCluster;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 *
 */
public class LevelControlImpl implements LevelControl {
	
	private LevelControlCluster levelControlCluster;
	private Attribute currentLevel;  
	private Attribute remainingTime;
	private Attribute onOffTransitionTime;
	private Attribute onLevel;
	private final CurrentLevelBridgeListeners eventBridge;
	
	public LevelControlImpl(ZigBeeDevice zbDevice){
		levelControlCluster = new LevelControlCluster(zbDevice);
		currentLevel = levelControlCluster.getAttributeCurrentLevel();
		remainingTime = levelControlCluster.getAttributeRemainingTime();
		onOffTransitionTime = levelControlCluster.getAttributeOnOffTransitionTime();
		onLevel = levelControlCluster.getAttributeOnLevel();
		eventBridge = new CurrentLevelBridgeListeners(new ReportingConfiguration(),currentLevel, this);
	}
	

	public Attribute getCurrentLevel() {
		return currentLevel;
	}

	public Attribute getOnLevel() {
		return onLevel;
	}

	public Attribute getOnOffTransitionTime() {
		return onOffTransitionTime;
	}

	public Attribute getRemainingTime() {
		return remainingTime;
	}

	public void move(byte mode, short rate) throws ZigBeeHAException {
		try {
			DefaultResponse response = (DefaultResponse)levelControlCluster.move(mode, rate);
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public void moveWithOnOff(byte mode, short rate) throws ZigBeeHAException {
		try {
			DefaultResponse response = (DefaultResponse)levelControlCluster.moveWithOnOff(mode, rate);
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}
	

	public void moveToLevel(short level, int time) throws ZigBeeHAException{
		try {
			DefaultResponse response = (DefaultResponse)levelControlCluster.moveToLevel(level, time);
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public void moveToLevelWithOnOff(short level, int time) throws ZigBeeHAException {
		try {
			DefaultResponse response = (DefaultResponse)levelControlCluster.moveToLevelWithOnOff(level, time);
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}
	
	public void step(byte mode, short step, int time) throws ZigBeeHAException{
		try {
			DefaultResponse response = (DefaultResponse)levelControlCluster.step(mode, step, time);
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}	
	
	public void stepWithOnOff(byte mode, short step, int time) throws ZigBeeHAException {
		try {
			DefaultResponse response = (DefaultResponse)levelControlCluster.stepWithOnOff(mode, step, time);
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}
	

	public void stop() throws ZigBeeHAException{
		try {
			DefaultResponse response = (DefaultResponse)levelControlCluster.stop();
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public void stopWithOnOff() throws ZigBeeHAException {
		try {
			DefaultResponse response = (DefaultResponse)levelControlCluster.stopWithOnOff();
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}
	
	
	public Subscription[] getActiveSubscriptions() {
		return levelControlCluster.getActiveSubscriptions();
	}

	public int getId() {
		return levelControlCluster.getId();
	}

	public String getName() {
		return levelControlCluster.getName();
	}
	
	public Attribute getAttribute(int id) {		
		Attribute[] attributes = levelControlCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public Attribute[] getAttributes() {
		return levelControlCluster.getAvailableAttributes();
	}


	public boolean subscribe(CurrentLevelListener listener) {
		return eventBridge.subscribe(listener);
	}

	public boolean unsubscribe(CurrentLevelListener listener) {
		return eventBridge.unsubscribe(listener);
	}

}
