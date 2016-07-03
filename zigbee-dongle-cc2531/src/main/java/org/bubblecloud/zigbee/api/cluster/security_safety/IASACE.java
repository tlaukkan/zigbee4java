/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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

import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace.BypassPayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace.ZoneIDMapResponse;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace.ZoneInformationResponse;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 */
public interface IASACE extends Cluster {

    public Response arm(byte armMode) throws ZigBeeDeviceException;

    public void bypass(BypassPayload payload) throws ZigBeeDeviceException;

    public void emergency() throws ZigBeeDeviceException;

    public void fire() throws ZigBeeDeviceException;

    public void panic() throws ZigBeeDeviceException;

    public ZoneIDMapResponse getZoneIdMap() throws ZigBeeDeviceException;

    public ZoneInformationResponse getZoneInformation(int zoneID) throws ZigBeeDeviceException;
}