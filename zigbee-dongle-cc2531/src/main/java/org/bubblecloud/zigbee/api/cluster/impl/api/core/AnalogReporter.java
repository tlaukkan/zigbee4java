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

package org.bubblecloud.zigbee.api.cluster.impl.api.core;

/**
 * This interface extends the {@link Reporter} interface for the analog attribute.<br>
 * In fact, for the analog attribute a report is sent only if the value changes by a value<br>
 * that is greater than the <i>ReportableChagne</i> property.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface AnalogReporter extends Reporter {

    /**
     * By default all the new {@link AnalogReporter} will reported when the value change is greater<br>
     * equal of the following value
     */
    public static final double DEFAULT_REPORTABLE_CHANGE_INTERVAL = 0.0d;

    /**
     * @return the current value of the <i>ReportableChagne</i> property
     */
    public Object getReportableChange();

    /**
     * Set the value of the <i>ReportableChagne</i> property to the <code>value</code>
     *
     * @param value the new value for the <i>ReportableChagne</i>
     */
    public void setReportableChange(Object value);

}
