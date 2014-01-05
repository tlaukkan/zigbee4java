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
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;

import java.util.EnumSet;

/**
 * PLACEHOLDER TO IMPLEMENT
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.0
 *
 */
public interface RSSILocation extends Cluster {

    public EnumSet<LocationType> getLocationType();
    public void setLocationType(EnumSet<LocationType> type);

    public LocationMethod getLocationMethod();
    public void setLocationMethod(LocationMethod method);

    public int getLocationAge();

    public int getQualityMeasure();

    public int getNumberOfDevices();

    public int getCoordinate1();
    public void setCoordinate1(int coordinate1);

    public int getCoordinate2();
    public void setCoordinate2(int coordinate2);

    public int getCoordinate3();
    public void setCoordinate3(int coordinate3);

    public int getPower();
    public void setPower(int power);

    public int getPathLossExponent();
    public void setPathLossExponent(int pathLossExponent);

    public int getReportingPeriod();
    public void setReportingPeriod(int reportingPeriod);

    public int getCalculationPeriod();
    public void setCalculationPeriod(int calculationPeriod);

    public int getNumberRSSIMeasurements();
    public void setNumberRSSIMeasurements(int numberRSSIMeasurements);

    public void setAbsoluteLocation(int x, int y, int z, int power, int ple);
    public void setDeviceConfiguration(int power, int ple, int calcPeriod, int nRSSI, int reportPeriod);
    public Response getDeviceConfiguration(long ieeeAddress);

    public void getAbsoluteLocationData(boolean compact, boolean unicastResponse, int nResponse, long ieeeAddress);
    public void getLocationData(boolean compact, boolean unicastResponse, boolean recalc, int nResponse, long ieeeAddress);
    public void broadcastAbsoluteLocationData(boolean compact, boolean unicastResponse, int nResponse);
    public void broadcastLocationData(boolean compact, boolean unicastResponse, boolean recalc, int nResponse);
    public boolean addAnchorNodeAnnounceListener(AnchorNodeAnnounceListener listner);
    public boolean removeAnchorNodeAnnounceListener(AnchorNodeAnnounceListener listner);

    public void RSSIResponse(long ieeeAddress, int x, int y, int z, int rssi, int nsamples);
    public void sendPings(long ieeeAddress, int nRSSI, int calcPeriod);

    public boolean addRSSIPingListener(RSSIPingListener listener);
    public boolean removeRSSIPingListener(RSSIPingListener listener);

    public boolean addReportRSSIMeasurementsListener(ReportRSSIMeasurementsListener listener);
    public boolean removeReportRSSIMeasurementsListener(ReportRSSIMeasurementsListener listener);

}
