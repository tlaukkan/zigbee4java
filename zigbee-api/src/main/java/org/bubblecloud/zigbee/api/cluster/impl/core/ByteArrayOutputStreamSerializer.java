/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The implementation of the {@link ZBSerializer} that will replace the {@link DefaultSerializer}. <br />
 * In fact, this class does not require the user to build in advance a payload buffer.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.1
 */
public class ByteArrayOutputStreamSerializer implements ZBSerializer {

    private ByteArrayOutputStream payload;
    private byte[] buffer = new byte[8];

    public ByteArrayOutputStreamSerializer() {
        this.payload = new ByteArrayOutputStream();
    }

    public ByteArrayOutputStreamSerializer(ByteArrayOutputStream payload) {
        this.payload = payload;
    }

    public void appendBoolean(Boolean data) {
        int used = Integers.writeBooleanObject(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void appendByte(Byte data) {
        int used = Integers.writeByteObject(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void appendInteger(Integer data) {
        int used = Integers.writeIntObject(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void appendLong(Long data) {
        int used = Integers.writeLongObject(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void appendString(String str) {
        try {
            payload.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        int used = Integers.writeObject(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void appendShort(Short data) {
        int used = Integers.writeShortObject(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void append_boolean(boolean data) {
        int used = Integers.writeBoolean(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void append_byte(byte data) {
        int used = Integers.writeByte(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void append_int(int data) {
        int used = Integers.writeInt(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void append_int24bit(int data) {
        int used = Integers.writeInt24bit(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void append_long(long data) {
        int used = Integers.writeLong(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public void append_short(short data) {
        int used = Integers.writeShort(buffer, 0, data);
        payload.write(buffer, 0, used);
    }

    public byte[] getPayload() {
        return payload.toByteArray();
    }

}
