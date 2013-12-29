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

import org.bubblecloud.zigbee.util.Integers;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

/**
 * The defualt implementation of the {@link ZBSerializer}
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 * @deprecated From version 0.7.0 please use the {@link ByteArrayOutputStreamSerializer} instead
 */
public class DefaultSerializer implements ZBSerializer {
    int index = 0;
    private byte[] payload;

    /**
     * @param payload the byte array that will used for storing the serialiazed data
     * @param index   the first empty byte where data will be written
     * @deprecated From version 0.7.0 please use the {@link ByteArrayOutputStreamSerializer} instead
     */
    public DefaultSerializer(byte[] payload, int index) {
        this.payload = payload;
        this.index = index;
    }

    public void appendBoolean(Boolean data) {
        index += Integers.writeBooleanObject(payload, index, data);
    }

    public void appendByte(Byte data) {
        index += Integers.writeByteObject(payload, index, data);
    }

    public void appendInteger(Integer data) {
        index += Integers.writeIntObject(payload, index, data);
    }

    public void appendLong(Long data) {
        index += Integers.writeLongObject(payload, index, data);
    }

    public void appendString(String str) {
        final byte[] raw = str.getBytes();
        if (raw.length > 255) {
            throw new IllegalArgumentException("Given string '" + str + "' is too long - maximum String size is 255.");
        }

        payload[index] = (byte) (raw.length & 0xFF);
        System.arraycopy(raw, 0, payload, index + 1, raw.length);
        index += raw.length + 1;
    }

    public void appendZigBeeType(Object data, ZigBeeType type) {
        if (data == null) {
            throw new NullPointerException("You can not append null data to a stream");
        }
        switch (type) {
            case Boolean:
                appendBoolean((Boolean) data);
                break;
            case Data8bit:
            case SignedInteger8bit:
            case Bitmap8bit:
            case UnsignedInteger8bit:
            case Enumeration8bit:
                final Number b = (Number) data;
                append_byte(b.byteValue());
                break;
            case Data16bit:
            case SignedInteger16bit:
            case Bitmap16bit:
            case UnsignedInteger16bit:
            case Enumeration16bit:
                final Number s = (Number) data;
                append_short(s.shortValue());
                break;
            case Data24bit:
            case SignedInteger24bit:
            case Bitmap24bit:
            case UnsignedInteger24bit:
                new IllegalArgumentException(
                        "No reader defined by this " + ZBDeserializer.class.getName() +
                                " for " + type.toString() + " (" + type.getId() + ")"
                );
                break;
            case Data32bit:
            case SignedInteger32bit:
            case Bitmap32bit:
            case UnsignedInteger32bit:
                if (type == ZigBeeType.UnsignedInteger32bit) {
                    final Long l = (Long) data;
                    append_int(l.intValue());
                } else {
                    final Integer i = (Integer) data;
                    append_int(i.intValue());
                }
                break;
            case CharacterString:
            case OctectString: {
                final String str = (String) data;
                append_byte((byte) (str.length() & (0xFF)));
                appendString(str);
            }
            break;
            case LongCharacterString:
            case LongOctectString: {
                final String str = (String) data;
                append_short((short) (str.length() & (0xFFFF)));
                appendString(str);
            }
            break;
            default:
                throw new IllegalArgumentException(
                        "No reader defined by this " + ZBDeserializer.class.getName() +
                                " for " + type.toString() + " (" + type.getId() + ")"
                );
        }
    }

    public void appendObject(Object data) {
        index += Integers.writeObject(payload, index, data);
    }

    public void appendShort(Short data) {
        index += Integers.writeShortObject(payload, index, data);
    }

    public void append_boolean(boolean data) {
        index += Integers.writeBoolean(payload, index, data);
    }

    public void append_byte(byte data) {
        index += Integers.writeByte(payload, index, data);
    }

    public void append_int(int data) {
        index += Integers.writeInt(payload, index, data);
    }

    public void append_int24bit(int data) {
        index += Integers.writeInt24bit(payload, index, data);
    }

    public void append_long(long data) {
        index += Integers.writeLong(payload, index, data);
    }

    public void append_short(short data) {
        index += Integers.writeShort(payload, index, data);
    }

    public byte[] getPayload() {
        byte[] copy = new byte[index];
        System.arraycopy(payload, 0, copy, 0, copy.length);
        return copy;
    }
}
