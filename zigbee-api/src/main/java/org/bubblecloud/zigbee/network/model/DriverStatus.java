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

package org.bubblecloud.zigbee.network.model;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi - ISTI-CNR
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 */
public enum DriverStatus {

    /**
     * The driver has been created and it will start to initialize all the hardware resources<br>
     * and the ZigBee network (i.e.: it will either join or create a network).
     */
    CREATED,

    /**
     * The driver has already initialized all the hardware resources, and it is waiting for<br>
     * the hardware to complete the initialization process
     */
    HARDWARE_INITIALIZING,

    /**
     * The all the hardware resources have been initialized successfully, it will start to<br>
     * initialize the ZigBee network
     */
    HARDWARE_READY,

    /**
     * The driver has already initialized the ZigBee network, and it is waiting for<br>
     * the completion of process (i.e.: it joined to the network and it is waiting for <br>
     * a network address)
     */
    NETWORK_INITIALIZING,

    /**
     * The driver successfully joined to or create the ZigBee network
     */
    NETWORK_READY,

    /**
     * The driver is closed, no resources is in use
     */
    CLOSED;
}
