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

import java.util.Hashtable;

/**
 * Defines the ZigBee data types.
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public enum ZigBeeType {

    //TODO Add missing data types

    Boolean(0x10, 1, false, Boolean.class),
    Data8bit(0x08, 1, false, Integer.class),
    Data16bit(0x09, 2, false, Integer.class),
    Data24bit(0x0a, 3, false, Integer.class),
    Data32bit(0x0b, 4, false, Integer.class),
    Bitmap32bit(0x1b, 4, false, Integer.class),
    Bitmap24bit(0x1a, 3, false, Integer.class),
    Bitmap16bit(0x19, 2, false, Integer.class),
    Bitmap8bit(0x18, 1, false, Integer.class),
    UnsignedInteger8bit(0x20, 1, true, Integer.class),
    UnsignedInteger16bit(0x21, 2, true, Integer.class),
    UnsignedInteger24bit(0x22, 3, true, Integer.class),
    UnsignedInteger32bit(0x23, 4, true, Long.class),
    OctectString(0x41, -1, false, String.class),
    CharacterString(0x42, -1, false, String.class),
    LongOctectString(0x43, -1, false, String.class),
    LongCharacterString(0x44, -1, false, String.class),
    Enumeration8bit(0x30, 1, false, Byte.class),
    Enumeration16bit(0x31, 2, false, Byte.class),
    IEEEAddress(0xf0, 8, false, String.class),
    SignedInteger8bit(0x28, 1, true, Integer.class),
    SignedInteger16bit(0x29, 2, true, Integer.class),
    SignedInteger24bit(0x2a, 3, true, Integer.class),
    SignedInteger32bit(0x2b, 4, true, Integer.class),
    SemiPrecision(0x38, 2, true, Short.class),
    SinglePrecision(0x39, 4, true, Float.class),
    DoublePrecision(0x3a, 8, true, Double.class);




    static Hashtable<Byte, ZigBeeType> MAP;

    private int id;
    private int length;
    private boolean analog;
    private Class javaClass;

    private ZigBeeType(int id, int length, boolean analog, Class javaClass){
        this.id = id;
        this.length = length;
        this.analog = analog;
        this.javaClass = javaClass;
        if (ZigBeeType.getMap() == null) ZigBeeType.setMap();
        ZigBeeType.getMap().put(Byte.valueOf((byte) id),this);
    }

    public int getId() {
        return id;
    }

    /**
     *
     * @return the length, in bytes, required for storing the data. <code>-1</code> if and
     * 	only if the type has a variable length
     */
    public int getLength() {
        return length;
    }

    public boolean isAnalog() {
        return analog;
    }

    public Class getJavaClass() {
        return javaClass;
    }

    public static ZigBeeType getType(byte b){
        return MAP.get(Byte.valueOf(b));
    }

    private static Hashtable<Byte, ZigBeeType> getMap(){
        return MAP;
    }

    private static void setMap(){
        MAP = new Hashtable<Byte, ZigBeeType>();
    }
}