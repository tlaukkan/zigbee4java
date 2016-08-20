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

package org.bubblecloud.zigbee.api.cluster.general;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.0
 */
public class NeighborsInformation {

    private final long ieeeAddress;
    private final int x;
    private final int y;
    private final int z;
    private final int rssi;
    private final int nSamples;

    /**
     * @param ieeeAddress
     * @param x
     * @param y
     * @param z
     * @param rssi
     * @param samples
     */
    public NeighborsInformation( long ieeeAddress, int x, int y, int z, int rssi, int samples ) {
        this.ieeeAddress = ieeeAddress;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rssi = rssi;
        nSamples = samples;
    }

    /**
     * @return the ieeeAddress
     */
    public long getIeeeAddress() {
        return ieeeAddress;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the z
     */
    public int getZ() {
        return z;
    }

    /**
     * @return the rssi
     */
    public int getRssi() {
        return rssi;
    }

    /**
     * @return the nSamples
     */
    public int getNSamples() {
        return nSamples;
    }

}
