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

package org.bubblecloud.zigbee.api.cluster.security_safety;

import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneEnrollRequestPayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneEnrollResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneStatusChangeNotificationListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone.ZoneStatusChangeNotificationPayload;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public interface IASZone extends Cluster {

    public Attribute getZoneState();

    public Attribute getZoneType();

    public Attribute getZoneStatus();

    public Attribute getIASCIEAddress();

    public ZoneEnrollResponse zoneEnrollRequest(ZoneEnrollRequestPayload payload) throws ZigBeeDeviceException;

    /**
     * @since 0.7.0
     */
    public Response zoneStatusChangeNotification(ZoneStatusChangeNotificationPayload payload) throws ZigBeeDeviceException;

    public boolean addZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener);

    public boolean removeZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener);
}
