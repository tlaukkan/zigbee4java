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

package org.bubblecloud.zigbee.api.cluster.impl.attribute;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class AbstractAttribute implements AttributeDescriptor {

    private int id;
    private String name;
    private Class clazz;
    private ZigBeeType zbType;
    private boolean isReportable;
    private boolean isWritable;

    public AbstractAttribute() {
    }

    final public int getId() {
        return id;
    }

    public AbstractAttribute setId(int id) {
        this.id = id;
        return this;
    }

    final public String getName() {
        return name;
    }

    public AbstractAttribute setName(String name) {
        this.name = name;
        return this;
    }

    final public Class getType() {
        return clazz;
    }

    private AbstractAttribute setType(Class clazz) {
        this.clazz = clazz;
        return this;
    }


    final public boolean isReportable() {
        return isReportable;
    }

    public AbstractAttribute setReportable(boolean isReportable) {
        this.isReportable = isReportable;
        return this;
    }

    final public boolean isWritable() {
        return isWritable;
    }

    public AbstractAttribute setWritable(boolean isWritable) {
        this.isWritable = isWritable;
        return this;
    }

    public AbstractAttribute setZigBeeType(ZigBeeType type) {
        this.zbType = type;
        setType(type.getJavaClass());
        return this;
    }

    final public ZigBeeType getZigBeeType() {
        return zbType;
    }


}
