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
 * This class represent an Attribute as defined by the <i>ZigBee Cluster Library</i> specification
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface Attribute {

    /**
     * @return the <b>16-bit</b> identifier of the attribute as specified by the cluster
     */
    public int getId();

    /**
     * @return the {@link String} associated to this attribute as specified by the cluster
     */
    public String getName();

    /**
     * @return the java {@link Class} used to represent the attribute value
     */
    @SuppressWarnings("unchecked")
    public Class getType();

    /**
     * @return the {@link ZigBeeType} that describes the attribute type
     */
    public ZigBeeType getZigBeeType();

    /**
     * @return the true if and only if the attribute can be written
     */
    public boolean isWritable();

    /**
     * @return the true if and only if the attribute support the {@link Reporter}
     */
    public boolean isReportable();

    /**
     * @return the current value of the attribute
     */
    public Object getValue() throws ZigBeeClusterException;

    /**
     * If the attribute can be written ({@link #isWritable()} this method set its value.
     *
     * @param o the value of to set the attribute to
     */
    public void setValue(Object o) throws ZigBeeClusterException;

    /**
     * @return the {@link Reporter}
     */
    public Reporter getReporter();

}
