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
 * The interface for helping the serialization ZCL frame on array of byte
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface ZBSerializer {

    /**
     * @param data {@link Object} containing the value to append
     * @param type {@link ZigBeeType} to select of data has to be appended
     * @since 0.4.0
     */
    public void appendZigBeeType(Object data, ZigBeeType type);

    /**
     * Append a {@link String} to the stream by prefixing it with the length of the String itself <br>
     * as specified by the <b>ZigBee Cluster Library</b> (<i>Document 075123r01ZB</i>
     *
     * @param str the {@link String} to append
     * @throws IllegalArgumentException if the length of the {@link String} is greater then 255
     * @since 0.4.0
     */
    public void appendString(String str);

    /**
     * Since version <b>0.4.0</b> the method must not used, use {@link #appendZigBeeType(Object, ZigBeeType)} instead.<br>
     * This method has a conceptual bug in respect to appending 8, 16, or 24 bit long data, in fact<br>
     * the methods can only fail in such cases.
     *
     * @param data {@link Object} to serialize as Java type
     * @deprecated Use {@link #appendZigBeeType(Object, ZigBeeType)} instead
     */
    public void appendObject(Object data);

    public void appendBoolean(Boolean data);

    public void appendByte(Byte data);

    public void appendShort(Short data);

    public void appendInteger(Integer data);

    public void appendLong(Long data);

    public void append_boolean(boolean data);

    public void append_byte(byte data);

    public void append_short(short data);

    public void append_int(int data);

    /**
     * @param data int value to append
     * @since 0.4.0
     */
    public void append_int24bit(int data);

    public void append_long(long data);

    /**
     * @return a copy of the payload
     * @since 0.8.0
     */
    public byte[] getPayload();
}
