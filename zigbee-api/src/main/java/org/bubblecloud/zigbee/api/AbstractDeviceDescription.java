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

package org.bubblecloud.zigbee.api;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public abstract class AbstractDeviceDescription implements DeviceDescription {

    public abstract int[] getCustomClusters();

    public abstract int[] getMandatoryCluster();

    public abstract int[] getOptionalCluster();

    public abstract int[] getStandardClusters();

    /**
     * Checks the requested cluster against the devices custom cluster list
     * and returns true if this is an custom cluster.
     * 
     * @param clusterId to check if it's custom
     * @return true if this is an custom cluster
     */
    public boolean isCustom(int clusterId) {
        int[] array = getCustomClusters();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == clusterId) {
            	return true;
            }
        }
        return false;
    }

    /**
     * Checks the requested cluster against the devices mandatory cluster list
     * and returns true if this is an mandatory cluster.
     * 
     * @param clusterId to check if it's mandatory
     * @return true if this is an mandatory cluster
     */
    public boolean isMandatory(int clusterId) {
        int[] array = getMandatoryCluster();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == clusterId) {
            	return true;
            }
        }
        return false;
    }

    /**
     * Checks the requested cluster against the devices optional cluster list
     * and returns true if this is an optional cluster.
     * 
     * @param clusterId to check if it's optional
     * @return true if this is an optional cluster
     */
    public boolean isOptional(int clusterId) {
        int[] array = getOptionalCluster();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == clusterId) {
            	return true;
            }
        }
        return false;
    }

    /**
     * Checks the requested cluster against the devices standard cluster list
     * and returns true if this is an standard cluster.
     * 
     * @param clusterId to check if it's standard
     * @return true if this is an standard cluster
     */
    public boolean isStandard(int clusterId) {
        int[] array = getStandardClusters();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == clusterId) {
            	return true;
            }
        }
        return false;
    }

}
