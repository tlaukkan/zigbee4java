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

package org.bubblecloud.zigbee.api.cluster.impl.measurement_sensing;

import org.bubblecloud.zigbee.api.cluster.impl.api.measurement_sensing.OccupancySensing;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;

/**
 * Implementation of the {@link OccupancySensing} interface
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 */
public class OccupancySensingCluster extends ZCLClusterBase implements OccupancySensing {

    private final AttributeImpl occupancy;
    private final AttributeImpl occupancySensorType;
    private final AttributeImpl pirOccupiedToUnoccupiedDelay;
    private final AttributeImpl pirUnoccupiedToOccupiedDelay;
    private final AttributeImpl pirUnoccupiedToOccupiedThreshold;
    private final AttributeImpl ultraSonicOccupiedToUnoccupiedDelay;
    private final AttributeImpl ultraSonicUnoccupiedToOccupiedDelay;
    private final AttributeImpl ultrasonicUnoccupiedToOccupiedThreshold;

    private final Attribute[] attributes;

    public OccupancySensingCluster(ZigBeeEndpoint zbDevice) {
        super(zbDevice);
        occupancy = new AttributeImpl(zbDevice, this, Attributes.OCCUPANCY);
        occupancySensorType = new AttributeImpl(zbDevice, this, Attributes.OCCUPANCY_SENSOR_TYPE);
        pirOccupiedToUnoccupiedDelay = new AttributeImpl(zbDevice, this, Attributes.PIR_OCCUPIED_TO_UNOCCUPIED_DELAY);
        pirUnoccupiedToOccupiedDelay = new AttributeImpl(zbDevice, this, Attributes.PIR_UNOCCUPIED_TO_OCCUPIED_DELAY);
        ultraSonicOccupiedToUnoccupiedDelay = new AttributeImpl(zbDevice, this, Attributes.ULTRA_SONIC_OCCUPIED_TO_UNOCCUPIED_DELAY);
        ultraSonicUnoccupiedToOccupiedDelay = new AttributeImpl(zbDevice, this, Attributes.ULTRA_SONIC_UNOCCUPIED_TO_OCCUPIED_DELAY);
        pirUnoccupiedToOccupiedThreshold = new AttributeImpl(zbDevice, this, Attributes.PIR_UNOCCUPIED_TO_OCCUPIED_THRESHOLD);
        ultrasonicUnoccupiedToOccupiedThreshold = new AttributeImpl(zbDevice, this, Attributes.ULTRASONIC_UNOCCUPIED_TO_OCCUPIED_THRESHOLD);

        attributes = new AttributeImpl[]{occupancy, occupancySensorType, pirOccupiedToUnoccupiedDelay,
                pirUnoccupiedToOccupiedDelay, ultraSonicOccupiedToUnoccupiedDelay, ultraSonicUnoccupiedToOccupiedDelay,
                pirUnoccupiedToOccupiedThreshold, ultrasonicUnoccupiedToOccupiedThreshold};
    }

    @Override
    public short getId() {
        return OccupancySensing.ID;
    }

    @Override
    public String getName() {
        return OccupancySensing.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }

    public Attribute getAttributeOccupancy() {
        return occupancy;
    }

    public Attribute getAttributeOccupancySensorType() {
        return occupancySensorType;
    }

    public Attribute getAttributePIROccupiedToUnoccupiedDelay() {
        return pirOccupiedToUnoccupiedDelay;
    }

    public Attribute getAttributePIRUnoccupiedToOccupiedDelay() {
        return pirUnoccupiedToOccupiedDelay;
    }

    public Attribute getAttributeUltraSonicOccupiedToUnoccupiedDelay() {
        return ultraSonicOccupiedToUnoccupiedDelay;
    }

    public Attribute getAttributeUltraSonicUnoccupiedToOccupiedDelay() {
        return ultraSonicUnoccupiedToOccupiedDelay;
    }

    public Attribute getAttributePIRUnoccupiedToOccupiedThreshold() {
        return pirUnoccupiedToOccupiedThreshold;
    }

    public Attribute getAttributeUltrasonicUnoccupiedToOccupiedThreshold() {
        return ultrasonicUnoccupiedToOccupiedThreshold;
    }
}
