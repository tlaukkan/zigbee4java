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

package it.cnr.isti.zigbee.zcl.library.impl.general;

import it.cnr.isti.zigbee.api.ClusterMessage;
import it.cnr.isti.zigbee.api.ClusterFilter;
import it.cnr.isti.zigbee.api.ClusterListner;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.Alarms;
import it.cnr.isti.zigbee.zcl.library.api.general.alarms.AlarmListener;
import it.cnr.isti.zigbee.zcl.library.api.general.alarms.AlarmResponse;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.EmptyPayloadCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.ResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;
import it.cnr.isti.zigbee.zcl.library.impl.general.alarms.AlarmResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.alarms.AlarmsClusterFilter;
import it.cnr.isti.zigbee.zcl.library.impl.general.alarms.GetAlarmResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.alarms.ResetAlarmCommand;
import it.cnr.isti.zigbee.zcl.library.impl.global.DefaultResponseImpl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 *
 */
public class AlarmsCluster extends ZCLClusterBase implements Alarms{
	
	private final Logger log = LoggerFactory.getLogger(AlarmsCluster.class);
    
    private final AttributeImpl alarmCount;
	
	private final Attribute[] attributes;	
	
	private final ArrayList<AlarmListener> listeners = new ArrayList<AlarmListener>();

	private AlarmsListenerNotifier bridge;
	
	private static final EmptyPayloadCommand RESET_ALL_ALARMS = new EmptyPayloadCommand()
		.setId(Alarms.RESET_ALL_ALARMS_ID)
		.setClientServerDirection(true)
		.setClusterSpecific(true)
		.setManufacturerExtension(false);
	
	private static final EmptyPayloadCommand GET_ALARM = new EmptyPayloadCommand()
		.setId(Alarms.GET_ALARM_ID)
		.setClientServerDirection(true)
		.setClusterSpecific(true)
		.setManufacturerExtension(false);

	private static final EmptyPayloadCommand RESET_ALARM_LOG = new EmptyPayloadCommand()
		.setId(Alarms.RESET_ALARM_LOG_ID)
		.setClientServerDirection(true)
		.setClusterSpecific(true)
		.setManufacturerExtension(false);
								

	private class AlarmsListenerNotifier implements ClusterListner {

		public void handleCluster(ZigBeeDevice device, ClusterMessage c) {
			try {
				ResponseImpl response = new ResponseImpl(c, ID);
				AlarmResponse alarm = new AlarmResponseImpl(response);
				ArrayList<AlarmListener> localCopy;
				synchronized (listeners) {
					localCopy = new ArrayList<AlarmListener>(listeners);					
				}
				log.debug("Notifying {} AlarmListener", localCopy.size());
				for (AlarmListener alarmListner : localCopy) {
					try{
						log.debug("Notifying {}:{}", alarmListner.getClass().getName(), alarmListner);
						alarmListner.alarm(alarm.getAllarmCode(), alarm.getClusterID());
					}catch(Exception e){
						log.error("Error while notifying {}:{} caused by {}",new Object[]{
								alarmListner.getClass().getName(), alarmListner, e.getStackTrace() 
						});
					}
				}
				
			} catch (ZigBeeClusterException e) {
				e.printStackTrace();
			}
		}

		public ClusterFilter getClusterFilter() {
			return AlarmsClusterFilter.FILTER;
		}

		public void setClusterFilter(ClusterFilter filter) {			
		}
		
	}
	
	public AlarmsCluster(ZigBeeDevice zbDevice){
		super(zbDevice);
		alarmCount = new AttributeImpl(zbDevice,this,Attributes.ALLARM_COUNT);
		attributes = new AttributeImpl[]{alarmCount};
		bridge = new AlarmsListenerNotifier();
	}

	public short getId() {
		return Alarms.ID;
	}

	public String getName() {
		return Alarms.NAME;
	}

	public Attribute[] getStandardAttributes() {
		return attributes;
	}

	public Attribute getAttributeAlarmCount() {
	    return alarmCount;
	}

	public boolean addAlarmListerner(AlarmListener listener) {
	    synchronized (listeners) {
			if ( listeners.size() == 0 ){
			    try {
			    	getZigBeeDevice().bind(ID);
			    } catch (ZigBeeBasedriverException e) {
					log.error("Unable to bind to device for Alarms reporting", e);
					return false;
			    }
			    if ( getZigBeeDevice().addClusterListener(bridge) == false ) {
					log.error("Unable to register the cluster listener for Alarms reporting");
					return false;
			    }
			}
			listeners.add(listener);
			return true;		
	    }
	}

	public boolean removeAlarmListerner(AlarmListener listener) {
	    synchronized (listeners) {
			boolean removed = listeners.remove(listener); 
			if ( listeners.size() == 0 && removed ){
			    try {
			    	getZigBeeDevice().unbind(ID);
			    } catch (ZigBeeBasedriverException e) {
					log.error("Unable to unbind to device for Alarms reporting", e);
					return false;
			    }
			    if ( getZigBeeDevice().removeClusterListener(bridge) == false ) {
					log.error("Unable to unregister the cluster listener for Alarms reporting");
					return false;
			    }
			}
			return removed;		
	    }
	}
	
	public Response getAlarm() throws ZigBeeClusterException {
		enableDefaultResponse();
		Response response = invoke(GET_ALARM);
		if ( response.getZCLHeader().getFramecontrol().isClusterSpecificCommand() ){
		    return new GetAlarmResponseImpl(response);
		}else{
		    return new DefaultResponseImpl(response);
		}
	}

	public Response resetAlarm(int clusterId, int attributeId) throws ZigBeeClusterException {
		enableDefaultResponse();
    	ResetAlarmCommand resetAlarmCmd = new ResetAlarmCommand((byte)clusterId,(short)attributeId);
    	Response response = invoke(resetAlarmCmd);
    	return  new DefaultResponseImpl(response);	    
	}

	public Response resetAlarmLog() throws ZigBeeClusterException {
		enableDefaultResponse();
		Response response = invoke(RESET_ALARM_LOG);
		return new DefaultResponseImpl(response);
	}

	public Response resetAllAlarms() throws ZigBeeClusterException {
		enableDefaultResponse();
		Response response = invoke(RESET_ALL_ALARMS);
		return new DefaultResponseImpl(response);
	}



}
