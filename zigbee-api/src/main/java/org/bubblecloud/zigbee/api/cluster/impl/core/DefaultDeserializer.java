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
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

/**
 * The defualt implementation of the {@link ZBDeserializer}
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class DefaultDeserializer implements ZBDeserializer {
    int index = 0;
    private byte[] payload;

    public DefaultDeserializer(byte[] payload, int index) {
        this.payload = payload;
        this.index = index;
    }

    public boolean endOfStream() {
        return index >= payload.length;
    }

    public Boolean readBoolean() {
        Object value = Integers.readBooleanObject(payload, index);
        index++;
        return (Boolean) value;
    }

    public Byte readByte() {
        Byte value = Integers.readByteObject(payload, index);
        index += 1;
        return value;
    }

    public Integer readInteger() {
        Integer value = Integers.readIntObject(payload, index);
        index += 4;
        return value;
    }

    public Long readLong() {
        Long value = Integers.readLongObject(payload, index);
        index += 8;
        return value;
    }

    @SuppressWarnings("unchecked")
    public Object readObject(Class clazz) {
        Object[] value = new Object[1];
        int step = Integers.readObject(payload, index, clazz, value);
        index += step;
        return value[0];
    }

    public Short readShort() {
        Short value = Integers.readShortObject(payload, index);
        index += 2;
        return value;
    }

    public boolean read_boolean() {
        boolean value = Integers.readBoolean(payload, index);
        index += 1;
        return value;
    }

    public short read_uint8bit() {
        short value = Integers.readByte(payload, index);
        value = (short) (value & 0x000000FF);
        index += 1;
        return value;
    }

    public int read_uint16bit() {
        int value = Integers.readShort(payload, index);
        value = value & 0x0000FFFF;
        index += 2;
        return value;
    }

    public byte read_byte() {
        byte value = Integers.readByte(payload, index);
        index += 1;
        return value;
    }

    public int read_int() {
        int value = Integers.readInt(payload, index);
        index += 4;
        return value;
    }

    public long read_long() {
        long value = Integers.readLong(payload, index);
        index += 8;
        return value;
    }

    public short read_short() {
        short value = Integers.readShort(payload, index);
        index += 2;
        return value;
    }

    public int getPosition() {
        return index;
    }

    public void skip(int n) {
        index += n;
    }

    public String readString() {
        final String result = new String(payload, index + 1, payload[index] & 0xFF);
        index += payload[index] + 1;
        return result;
    }

    public String readString(int size) {
        final String result = new String(payload, index, size);
        index += size;
        return result;
    }

    public Object readZigBeeType(ZigBeeType type) {
        Object[] value = new Object[1];
        switch (type) {
            case Boolean:
                value[0] = readBoolean();
                break;
            case Data8bit:
            case Bitmap8bit:
            case Enumeration8bit:
            case UnsignedInteger8bit:
            case SignedInteger8bit:
                byte b = read_byte();
                if (type == ZigBeeType.UnsignedInteger8bit) {
                    value[0] = new Integer(b & 0xFF);
                } else {
                    value[0] = new Integer(b);
                }
                break;
            case Data16bit:
            case Bitmap16bit:
            case Enumeration16bit:
            case UnsignedInteger16bit:
            case SignedInteger16bit:
                short s = read_short();
                if (type == ZigBeeType.UnsignedInteger16bit) {
                    value[0] = new Integer(s & 0xFFFF);
                } else {
                    value[0] = new Integer(s);
                }
                break;
            case Data24bit:
            case Bitmap24bit:
            case UnsignedInteger24bit:
            case SignedInteger24bit:
                int i = read_int24bit();
                if (type == ZigBeeType.UnsignedInteger32bit) {
                    value[0] = new Long(i & 0xFFFFFFFF);
                } else {
                    value[0] = new Integer(i);
                }
                break;
            case Data32bit:
            case Bitmap32bit:
            case UnsignedInteger32bit:
            case SignedInteger32bit:
                int i24 = read_int24bit();
                value[0] = new Integer(i24);
                break;
            case CharacterString:
            case OctectString: {
                int size = read_byte();
                value[0] = readString(size);
            }
            break;
            case LongCharacterString:
            case LongOctectString: {
                int size = read_short();
                value[0] = readString(size);
            }
            default:
                throw new IllegalArgumentException(
                        "No reader defined by this " + ZBDeserializer.class.getName() +
                                " for " + type.toString() + " (" + type.getId() + ")"
                );
        }
        return value[0];
    }

    public int read_int24bit() {
        int value = Integers.readInt24bit(payload, index);
        index += 3;
        return value;
    }

}
