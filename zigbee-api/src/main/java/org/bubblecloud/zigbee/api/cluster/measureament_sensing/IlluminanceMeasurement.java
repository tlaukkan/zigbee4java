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

package org.bubblecloud.zigbee.api.cluster.measureament_sensing;

import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.measureament_sensing.event.MeasuredValueListener;
import org.bubblecloud.zigbee.api.cluster.measureament_sensing.event.ToleranceListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 */
public interface IlluminanceMeasurement extends Cluster {

    public Attribute getMeasuredValue();

    public Attribute getMinMeasuredValue();

    public Attribute getMaxMeasuredValue();

    public Attribute getTolerance();

    public Attribute getLightSensorType();

    public boolean subscribe(MeasuredValueListener tl);

    public boolean unsubscribe(MeasuredValueListener tl);

    public boolean subscribe(ToleranceListener tl);

    public boolean unsubscribe(ToleranceListener tl);

    //public boolean subscribe(LightSensorTypeListener tl);

    //public boolean unsubscribe(LightSensorTypeListener tl);
}
