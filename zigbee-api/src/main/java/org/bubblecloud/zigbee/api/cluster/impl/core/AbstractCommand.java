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

package org.bubblecloud.zigbee.api.cluster.impl.core;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class AbstractCommand implements Command {

    private byte id;
    private byte[] manufacturerId;
    private boolean isClientServerDirection;
    private boolean isClusterSpecific;
    private boolean isManufacturerExtension;
    private byte[] allowedResponseIds;
    protected byte[] payload = null;
    protected int freeIdxPayload = 0;

    public AbstractCommand(byte id) {
        this(id, null, true);
    }

    public AbstractCommand(byte id, boolean isClusterSpecific) {
        this(id, null, true, isClusterSpecific, null);
    }

    public AbstractCommand(byte id, byte[] manufacturerId, boolean isClientServerDirection) {
        this(id, manufacturerId, isClientServerDirection, true, null);
    }

    public AbstractCommand(byte id, byte[] manufacturerId, boolean isClientServerDirection, boolean isClusterSpecific) {
        this(id, manufacturerId, isClientServerDirection, isClusterSpecific, null);
    }

    public AbstractCommand(byte id, byte[] manufacturerId, boolean isClientServerDirection, boolean isClusterSpecific,
                           byte[] allowedResponseIds) {
        super();
        this.id = id;
        this.manufacturerId = manufacturerId;
        this.isClientServerDirection = isClientServerDirection;
        this.isClusterSpecific = isClusterSpecific;
        this.isManufacturerExtension = manufacturerId != null;
        this.allowedResponseIds = allowedResponseIds;
    }

    public byte[] getAllowedResponseId() {
        return allowedResponseIds;
    }

    protected AbstractCommand setAllowedResponseId(byte[] allowedResponseIds) {
        this.allowedResponseIds = allowedResponseIds;
        return this;
    }

    public byte getHeaderCommandId() {
        return id;
    }

    public byte[] getManufacturerId() {
        return manufacturerId;
    }

    public boolean isClientServerDirection() {
        return isClientServerDirection;
    }

    public boolean isClusterSpecific() {
        return isClusterSpecific;
    }

    public boolean isManufacturerExtension() {
        return isManufacturerExtension;
    }

    protected void setId(byte id) {
        this.id = id;
    }

    public byte[] getPayload() {
        if (payload == null) {
            payload = new byte[0];
        }
        return payload;
    }
}
