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

import java.io.IOException;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface ZCLCluster {

    /**
     * @return <code>true</code> if and only if the <i>Default response</i> command is enabled
     */
    public boolean isDefaultResponseEnabled();

    /**
     * This method enable the <i>Default response</i> command to be sent by the device that<br>
     * receives the {@link Command}
     */
    public void enableDefaultResponse();

    /**
     * This method return the actual <i>ClusterId</i>. Please remember that the final and official <i>ClusterId</i><br>
     * is defined by the <i>Profile</i> even if the Cluster is defined by a <i>Cluster Library</i>.
     *
     * @return the <i>ClusterId</i>
     */
    public short getId();

    /**
     * @return This method returns -1 if and only if no <b>Manufacturer</b> specific extension<br>
     *         are implement, otherwise it returns the <i>ManufacturerId</i>
     */
    public int getManufacturerId();

    /**
     * @return This method return the {@link String} identifying the human readable name of the cluster.<br>
     *         The name is assigned by the document defining the Cluster, either the <i>Cluster Library</i> or<br>
     *         or the <i>Profile</i>
     */
    public String getName();


    /**
     * This method returns an {@link Attribute} array containing the {@link Attribute} that are actually<br>
     * implemented on the cluster instance. Hence this method should return even the {@link Attribute}<br>
     * defined by the <b>Manufacturer</b>.
     *
     * @return The {@link Attribute} arrays
     */
    Attribute[] getAvailableAttributes();

    /**
     * This method returns an {@link Attribute} array containing the {@link Attribute} defined by<br>
     * the specification of the cluster. Hence, this method returns all {@link Attribute} defined<br>
     * either as <b>Mandatory</b> or as <b>Optional</b>, but does not return the {@link Attribute}<br>
     * defined by the <b>Manufacturer</b>.
     *
     * @return The {@link Attribute} arrays
     */
    Attribute[] getStandardAttributes();


    /**
     * This method is an alias for {@link #invoke(Command, false)}
     *
     * @throws IOException
     * @throws ZigBeeClusterException
     * @see #invoke(Command, boolean)
     */
    public Response invoke(Command cmd) throws ZigBeeClusterException;


    /**
     * This method invokes the {@link Command} on this Cluster and <b>wait</b> for its {@link Response}
     *
     * @param cmd the {@link Command} to invoke
     * @param if  suppressResponse true <i>Default response</i> are disabled
     * @return {@link Response} the response to the invoked {@link Command}. This method returns <code>null</code>
     * @throws IOException
     * @throws org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException
     */
    public Response invoke(Command cmd, boolean suppressResponse) throws ZigBeeClusterException; //TODO Remove

    /**
     * @param id the id of attribute to get
     * @return {@link Attribute} identified by the given id
     * @since 0.2.0
     */
    public Attribute getAttribute(int id);
}
