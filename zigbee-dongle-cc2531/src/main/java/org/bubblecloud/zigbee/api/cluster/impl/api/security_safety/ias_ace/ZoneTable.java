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
package org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_ace;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 */
public interface ZoneTable {

    class Zone {

        Short zoneID;
        Short zoneType;
        String zoneAddress;

        public Zone(Short zoneID, Short zoneType, String zoneAddress) {
            this.zoneID = zoneID;
            this.zoneType = zoneType;
            this.zoneAddress = zoneAddress;
        }

        public Short getZoneID() {
            return zoneID;
        }

        public void setZoneID(Short zoneID) {
            this.zoneID = zoneID;
        }

        public Short getZoneType() {
            return zoneType;
        }

        public void setZoneType(Short zoneType) {
            this.zoneType = zoneType;
        }

        public String getZoneAddress() {
            return zoneAddress;
        }

        public void setZoneAddress(String zoneAddress) {
            this.zoneAddress = zoneAddress;
        }
    }

    public Zone[] getZoneTable();

    public void addZone(Short zoneID, Short zoneType, String zoneAddress);

    public void removeZone(Short zoneID);
}