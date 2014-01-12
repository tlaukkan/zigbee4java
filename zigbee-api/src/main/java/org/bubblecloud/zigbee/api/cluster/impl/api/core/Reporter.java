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
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface Reporter {

    /**
     * By default all the new subscription will reported up to one per minute
     */
    public static final int DEFAULT_MAX_REPORTING_INTERVAL = 60;

    /**
     * By default all the new subscription will announce only when the value of the attribute change
     */
    public static final int DEFAULT_MIN_REPORTING_INTERVAL = 0;

    public boolean addReportListener(ReportListener listner);

    public boolean removeReportListener(ReportListener listner);

    public int getMinimumReportingInterval();

    public int setMinimumReportingInterval(int value);

    public int getMaximumReportingInterval();

    public int setMaximumReportingInterval(int value);

    /**
     * Remove all the {@link ReportListener} from this subscription
     */
    public void clear();

    /**
     * @return true if and only if a {@link ReportListener} is subscribed
     * @see #getReportListenersCount()
     * @since 0.2.0
     */
    public boolean isActive();

    /**
     * @return the number of {@link ReportListener} subscribed
     * @see #isActive()
     * @since 0.2.0
     */
    public int getReportListenersCount();

    /**
     * @return update the subscription configuration for the device
     * @since 0.6.0
     */
    public boolean updateConfiguration();

    /**
     * Gets the attribute subscribed to.
     * @return the attribute
     */
    Attribute getAttribute();
}
