/*
   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
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

package org.bubblecloud.zigbee.api.cluster.impl;

import org.bubblecloud.zigbee.api.cluster.general.AnchorNodeAnnounceListener;
import org.bubblecloud.zigbee.api.cluster.general.LocationMethod;
import org.bubblecloud.zigbee.api.cluster.general.LocationType;
import org.bubblecloud.zigbee.api.cluster.general.RSSILocation;
import org.bubblecloud.zigbee.api.cluster.general.RSSIPingListener;
import org.bubblecloud.zigbee.api.cluster.general.ReportRSSIMeasurementsListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;

import java.util.EnumSet;

/**
 * PLACEHOLDER TO IMPLEMENT
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.0
 */
public class RSSILocationImpl implements RSSILocation {

    public int getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public Reporter[] getAttributeReporters() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute[] getAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttribute(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    public EnumSet<LocationType> getLocationType() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setLocationType(EnumSet<LocationType> type) {
        // TODO Auto-generated method stub

    }

    public LocationMethod getLocationMethod() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setLocationMethod(LocationMethod method) {
        // TODO Auto-generated method stub

    }

    public int getLocationAge() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getQualityMeasure() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getNumberOfDevices() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getCoordinate1() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setCoordinate1(int coordinate1) {
        // TODO Auto-generated method stub

    }

    public int getCoordinate2() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setCoordinate2(int coordinate2) {
        // TODO Auto-generated method stub

    }

    public int getCoordinate3() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setCoordinate3(int coordinate3) {
        // TODO Auto-generated method stub

    }

    public int getPower() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setPower(int power) {
        // TODO Auto-generated method stub

    }

    public int getPathLossExponent() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setPathLossExponent(int pathLossExponent) {
        // TODO Auto-generated method stub

    }

    public int getReportingPeriod() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setReportingPeriod(int reportingPeriod) {
        // TODO Auto-generated method stub

    }

    public int getCalculationPeriod() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setCalculationPeriod(int calculationPeriod) {
        // TODO Auto-generated method stub

    }

    public int getNumberRSSIMeasurements() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setNumberRSSIMeasurements(int numberRSSIMeasurements) {
        // TODO Auto-generated method stub

    }

    public void setAbsoluteLocation(int x, int y, int z, int power, int ple) {
        // TODO Auto-generated method stub

    }

    public void setDeviceConfiguration(int power, int ple, int calcPeriod,
                                       int nRSSI, int reportPeriod) {
        // TODO Auto-generated method stub

    }

    public Response getDeviceConfiguration(long ieeeAddress) {
        // TODO Auto-generated method stub
        return null;
    }

    public void getAbsoluteLocationData(boolean compact,
                                        boolean unicastResponse, int nResponse, long ieeeAddress) {
        // TODO Auto-generated method stub

    }

    public void getLocationData(boolean compact, boolean unicastResponse,
                                boolean recalc, int nResponse, long ieeeAddress) {
        // TODO Auto-generated method stub

    }

    public void broadcastAbsoluteLocationData(boolean compact,
                                              boolean unicastResponse, int nResponse) {
        // TODO Auto-generated method stub

    }

    public void broadcastLocationData(boolean compact, boolean unicastResponse,
                                      boolean recalc, int nResponse) {
        // TODO Auto-generated method stub

    }

    public boolean addAnchorNodeAnnounceListener(
            AnchorNodeAnnounceListener listner) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean removeAnchorNodeAnnounceListener(
            AnchorNodeAnnounceListener listner) {
        // TODO Auto-generated method stub
        return false;
    }

    public void RSSIResponse(long ieeeAddress, int x, int y, int z, int rssi,
                             int nsamples) {
        // TODO Auto-generated method stub

    }

    public void sendPings(long ieeeAddress, int nRSSI, int calcPeriod) {
        // TODO Auto-generated method stub

    }

    public boolean addRSSIPingListener(RSSIPingListener listener) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean removeRSSIPingListener(RSSIPingListener listener) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean addReportRSSIMeasurementsListener(
            ReportRSSIMeasurementsListener listener) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean removeReportRSSIMeasurementsListener(
            ReportRSSIMeasurementsListener listener) {
        // TODO Auto-generated method stub
        return false;
    }


}
