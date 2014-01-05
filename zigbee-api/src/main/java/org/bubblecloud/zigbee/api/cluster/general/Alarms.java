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
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.alarms.AlarmListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.alarms.GetAlarmResponse;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 889 $ ($LastChangedDate: 2013-11-04 17:59:19 +0200 (Mon, 04 Nov 2013) $)
 *
 */
public interface Alarms extends Cluster {
    
    public Attribute getAttributeAlarmCount();

    public void resetAlarm(int clusterId, int attributeId) throws ZigBeeDeviceException;

    public void resetAllAlarms() throws ZigBeeDeviceException;

    public GetAlarmResponse getAlarm() throws ZigBeeDeviceException;

    public void resetAlarmLog() throws ZigBeeDeviceException;

    public boolean addAlarmListener(AlarmListener listener);

    public boolean removeAlarmListerner(AlarmListener listener);

}
