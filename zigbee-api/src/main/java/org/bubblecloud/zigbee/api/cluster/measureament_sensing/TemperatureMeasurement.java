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

package org.bubblecloud.zigbee.api.cluster.measureament_sensing;

import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.measureament_sensing.event.MeasuredValueListener;
import org.bubblecloud.zigbee.api.cluster.measureament_sensing.event.ToleranceListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public interface TemperatureMeasurement extends Cluster {

    public Attribute getMeasuredValue();

    public Attribute getMinMeasuredValue();

    public Attribute getMaxMeasuredValue();

    public Attribute getTolerance();

    /**
     * @param tl The {@link ToleranceListener} to subscribe for events
     * @since 0.6.0
     */
    public boolean subscribe(ToleranceListener tl);

    /**
     * @param tl The {@link ToleranceListener} to unsubscribe
     * @since 0.6.0
     */
    public boolean unsubscribe(ToleranceListener tl);

    /**
     * @param mvl The {@link MeasuredValueListener} to subscribe for events
     * @since 0.6.0
     */
    public boolean subscribe(MeasuredValueListener mvl);

    /**
     * @param mvl The {@link MeasuredValueListener} to unsubscribe
     * @since 0.6.0
     */
    public boolean unsubscribe(MeasuredValueListener mvl);

}
