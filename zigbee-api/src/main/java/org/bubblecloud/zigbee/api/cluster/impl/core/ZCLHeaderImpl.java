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
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLHeader;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 */
public class ZCLHeaderImpl implements ZCLHeader {

    private ZCLFrameControlImpl frameControl;
    private byte[] manufacturerId;
    private byte transactionId;
    private byte commandId;

    private Command cmd;
    private byte[] header;
    private byte[] frame;

    public ZCLHeaderImpl(Command cmd, boolean isDefaultResponseEnabled) {

        this.cmd = cmd;

        frameControl = new ZCLFrameControlImpl(cmd, isDefaultResponseEnabled);
        manufacturerId = cmd.getManufacturerId();
        transactionId = ZCLLayer.getTransactionId();
        commandId = cmd.getHeaderCommandId();

        header = createHeader();
    }

    private byte[] createHeader() {
        byte[] newHeader;
        if (cmd.isManufacturerExtension()) {
            newHeader = new byte[5];
            newHeader[0] = frameControl.toByte();
            newHeader[1] = manufacturerId[0];
            newHeader[2] = manufacturerId[1];
            newHeader[3] = transactionId;
            newHeader[4] = commandId;
        } else {
            newHeader = new byte[3];
            newHeader[0] = frameControl.toByte();
            newHeader[1] = transactionId;
            newHeader[2] = commandId;
        }
        return newHeader;
    }

    public ZCLHeaderImpl(byte[] frame) {
        this.frame = frame;
        frameControl = new ZCLFrameControlImpl(frame[0]);
        header = copyHeader(frameControl.isManufacturerExtension());
    }

    private byte[] copyHeader(boolean extendedHeader) {
        byte[] resultHeader;
        if (extendedHeader) {
            resultHeader = new byte[5];
            System.arraycopy(frame, 0, resultHeader, 0, 5);
            manufacturerId = new byte[2];
            manufacturerId[0] = resultHeader[1];
            manufacturerId[1] = resultHeader[2];
            transactionId = resultHeader[3];
            commandId = resultHeader[4];
        } else {
            resultHeader = new byte[3];
            System.arraycopy(frame, 0, resultHeader, 0, 3);
            transactionId = resultHeader[1];
            commandId = resultHeader[2];
        }
        return resultHeader;
    }


    /* (non-Javadoc)
     * @see it.cnr.isti.zigbee.zcl.library.impl.core.ZCLHeaderInterface#getFramecontrol()
     */
    public ZCLFrameControlImpl getFramecontrol() {
        return frameControl;
    }

    /* (non-Javadoc)
     * @see it.cnr.isti.zigbee.zcl.library.impl.core.ZCLHeaderInterface#getManufacturerId()
     */
    public byte[] getManufacturerId() {
        return manufacturerId;
    }

    /* (non-Javadoc)
     * @see it.cnr.isti.zigbee.zcl.library.impl.core.ZCLHeaderInterface#getTransactionId()
     */
    public byte getTransactionId() {
        return transactionId;
    }

    /* (non-Javadoc)
     * @see it.cnr.isti.zigbee.zcl.library.impl.core.ZCLHeaderInterface#getCommandId()
     */
    public byte getCommandId() {
        return commandId;
    }


    public byte[] toByte() {
        return header;
    }

    public int size() {
        return toByte().length;
    }
}
