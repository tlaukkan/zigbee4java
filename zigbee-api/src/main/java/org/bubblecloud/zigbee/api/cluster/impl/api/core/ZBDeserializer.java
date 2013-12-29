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
 * The interface for helping the deserialization ZCL frame on array of byte
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface ZBDeserializer {

    /**
     * @return true if and only if no byte left to read from the payload
     * @since 0.8.0
     */
    public boolean endOfStream();

    /**
     * @param the {@link ZigBeeType} to use for parsing the data
     * @return the parsed {@link Object}
     * @since 0.4.0
     */
    public Object readZigBeeType(ZigBeeType type);


    /**
     * Parse a {@link String} from the stream considering the first byte as the length of the<br>
     * String itself as specified by the <b>ZigBee Cluster Library</b> (<i>Document 075123r01ZB</i>
     *
     * @return the parsed {@link String}
     * @since 0.4.0
     * @deprecated {@link #readZigBeeType(ZigBeeType)} should be used instead
     */
    public String readString();


    /**
     * Since version <b>0.4.0</b> the method must not used, use {@link #readZigBeeType(ZigBeeType)} instead.<br>
     * This method has a conceptual bug in respect to the parsing of 8, 16, or 24 bit long data, in fact<br>
     * the methods can only fail in such cases.
     *
     * @param clazz the {@link Class} used to select the proper parsing
     * @return the parse {@link Object} from the stream depending the {@link Class} specified
     * @deprecated Use {@link #readZigBeeType(ZigBeeType)} instead
     */
    public Object readObject(Class clazz);

    public Boolean readBoolean();

    public Byte readByte();

    public Short readShort();

    public Integer readInteger();

    public Long readLong();

    public boolean read_boolean();

    public byte read_byte();

    public short read_short();

    public int read_int();

    /**
     * @return the 8bit unsigned
     * @since 0.8.0
     */
    public short read_uint8bit();

    /**
     * @return the 24bit parsed
     * @since 0.4.0
     */
    public int read_int24bit();

    public long read_long();

    /**
     * @return
     * @since 0.2.0
     */
    public int getPosition();

    /**
     * @param n
     * @since 0.2.0
     */
    public void skip(int n);


    /**
     * @return an integer read from a 16bit
     * @since 0.8.0
     */
    public int read_uint16bit();

}
