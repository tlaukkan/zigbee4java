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

package org.bubblecloud.zigbee.api.cluster;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;

/**
 * This is the base class for the clusters in the cluster library.
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public interface Cluster {
    /**
     * This method return the actual <i>ClusterId</i>. Please remember that the final and official <i>ClusterId</i>
     * is defined by the <i>Profile</i> even if the Cluster is defined by a <i>Cluster Library</i>.
     *
     * @return the <i>ClusterId</i>
     */
    public int getId();

    /**
     * @return This method return the {@link String} identifying the human readable name of the cluster.
     *         The name is assigned by the document defining the Cluster, either the <i>Cluster Library</i> or
     *         or the <i>Profile</i>
     */
    public String getName();

    /**
     * This method return an array of {@link org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter}
     * objects according to the number of reportable {@link Attribute} currently used.
     *
     * @return an array of {@link org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter}
     */
    public Reporter[] getAttributeReporters();

    /**
     * @return {@link Attribute} array with all the attribute implemented by the {@link Cluster}
     * @since 0.2.0
     */
    public Attribute[] getAttributes();

    /**
     * @param id the <code>int</code> value identifying an attribute of the {@link Cluster}
     * @return {@link Attribute} implemented by the cluster identified by the given id
     * @since 0.2.0
     */
    public Attribute getAttribute(int id);
}
