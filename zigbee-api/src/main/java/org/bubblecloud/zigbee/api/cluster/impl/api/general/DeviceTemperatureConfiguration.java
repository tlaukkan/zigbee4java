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

package org.bubblecloud.zigbee.api.cluster.impl.api.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLCluster;

/**
 * This class represent the <b>Device Temperature Configuration</b> Cluster as defined by the document:<br>
 * <i>ZigBee Cluster Library</i> public release version 075123r01ZB
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface DeviceTemperatureConfiguration extends ZCLCluster {

    static final short ID = 0x0002;
    static final String NAME = "DeviceTemperatureConfiguration";
    static final String DESCRIPTION = "Attributes for determining information about a device's internal temperature, and for configuring under/over temperature alarms.";


    public Attribute getAttributeCurrentTemperature();

    public Attribute getAttributeMinTempExperienced();

    public Attribute getAttributeMaxTempExperienced();

    public Attribute getAttributeOverTempTotalDwell();

    public Attribute getAttributeDeviceTempAlarmMask();

    public Attribute getAttributeLowTempThreshold();

    public Attribute getAttributeHighTempThreshold();

    public Attribute getAttributeLowTempDwellTripPoint();

    public Attribute getAttributeHighTempDwellTripPoint();

}
