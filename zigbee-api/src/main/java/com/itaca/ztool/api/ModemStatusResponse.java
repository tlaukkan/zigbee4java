/*
   Copyright 2008-2013 Andrew Rapp, http://code.google.com/p/xbee-api/
   
   Copyright 2008-2013 ITACA-TSB, http://www.tsb.upv.es/
   Instituto Tecnologico de Aplicaciones de Comunicacion 
   Avanzadas - Grupo Tecnologias para la Salud y el 
   Bienestar (TSB)


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

package com.itaca.ztool.api;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author <a href="mailto:andrew.rapp@gmail.com">Andrew Rapp</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 *
 */
public class ModemStatusResponse extends ZToolResponse {
		
	public enum Status {
		HARDWARE_RESET (0),
		WATCHDOG_TIMER_RESET (1),
		ASSOCIATED (2),
		DISASSOCIATED (3),
		SYNCHRONIZATION_LOST (4),
		COORDINATOR_REALIGNMENT (5),
		COORDINATOR_STARTED (6);
		
		private static final Map<Integer,Status> lookup = new HashMap<Integer,Status>();
		
		static {
			for(Status s : EnumSet.allOf(Status.class)) {
				lookup.put(s.getValue(), s);
			}
		}
		
		public static Status get(int value) { 
			return lookup.get(value); 
		}
		
	    private final int value;
	    
	    Status(int value) {
	        this.value = value;
	    }

		public int getValue() {
			return value;
		}
	}
	
	private Status status;
	
	public ModemStatusResponse() {

	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String toString() {
		return super.toString() + ",status=" + this.status;
	}
}