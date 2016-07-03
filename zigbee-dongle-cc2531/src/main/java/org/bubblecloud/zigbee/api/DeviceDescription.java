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

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLCluster;

/**
 * This class represent static description associated to a generic <b>Home Automation Device</b>
 * as defined by the document 
 * <i>ZigBee Home Automation</i> public release version 075123r01ZB
 * <p>
 * In particular, this class can be used for inspecting by code the <i>ZigBee Home Automation</i>
 * definition. In fact, all the device belonging to the same <b>DeviceId</b> have a common definition.
 * Hence, a Singelton implementation that shares a {@link DeviceDescription} among all the {@link DeviceBase}
 * objects belonging to the <b>DeviceId</b> is advised.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface DeviceDescription {

    /**
     * @return the {@link ZCLCluster} array of all the cluster defined as <b>Optional</b> by the
     *         <i>ZigBee Home Automation</i> profile documentation
     */
    public int[] getOptionalCluster();

    /**
     * Gets the array of mandatory clusters for this endpoint
     * 
     * @return the {@link ZCLCluster} array of all the cluster defined as <b>Mandatory</b> by the
     *         <i>ZigBee Home Automation</i> profile documentation
     */
    public int[] getMandatoryCluster();

    /**
     * Gets the array of standard clusters for this endpoint
     * 
     * @return the {@link ZCLCluster} array of all the cluster defined as <b>Standard</b> by the
     *         <i>ZigBee Home Automation</i> profile documentation
     */
    public int[] getStandardClusters();

    /**
    * Gets the array of custom clusters for this endpoint
    * 
    * @return the {@link ZCLCluster} array of all the cluster defined as <b>Custom</b> by the
    *         <i>ZigBee Home Automation</i> profile documentation
    */
    public int[] getCustomClusters();

    /**
     * Checks the cluster ID to see if it is mandatory for this device
     * 
     * @param clusterId
     * @return true if the cluster is mandatory
     */
    public boolean isMandatory(int clusterId);

    /**
     * Checks the cluster ID to see if it is optional for this device
     * 
     * @param clusterId
     * @return true if the cluster is optional
     */
    public boolean isOptional(int clusterId);

    /**
     * Checks the cluster ID to see if it is standard for this device
     * 
     * @param clusterId
     * @return true if the cluster is standard
     */
    public boolean isStandard(int clusterId);

    /**
     * Checks the cluster ID to see if it is custom for this device
     * 
     * @param clusterId
     * @return true if the cluster is custom
     */
    public boolean isCustom(int clusterId);

}
