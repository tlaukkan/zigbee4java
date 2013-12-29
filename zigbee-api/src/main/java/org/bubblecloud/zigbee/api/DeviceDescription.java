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
 * This class represent static description associated to a generic <b>Home Automation Device</b><br>
 * as defined by the document:<br>
 * <i>ZigBee Home Automation</i> public release version 075123r01ZB
 * <br>
 * In particular, this class can be used for inspecting by code the <i>ZigBee Home Automation</i><br>
 * definition. In fact, all the device belonging to the same <b>DeviceId</b> have a common definition.<br>
 * Hence, a Singelton implementation that shares a {@link DeviceDescription} among all the {@link DeviceBase}<br>
 * objects belonging to the <b>DeviceId</b> is adviced.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface DeviceDescription {

    /**
     * @return the {@link ZCLCluster} array of all the cluster defined as <b>Optional</b> by the<br>
     *         <i>ZigBee Home Automation</i> profile documentation
     */
    public int[] getOptionalCluster();

    /**
     * @return the {@link ZCLCluster} array of all the cluster defined as <b>Mandatory</b> by the<br>
     *         <i>ZigBee Home Automation</i> profile documentation
     */
    public int[] getMandatoryCluster();

    /**
     * @return
     */
    public int[] getStandardClusters();

    public int[] getCustomClusters();

    public boolean isMandatory(int clusterId);

    public boolean isOptional(int clusterId);

    public boolean isStandard(int clusterId);

    public boolean isCustom(int clusterId);


}
