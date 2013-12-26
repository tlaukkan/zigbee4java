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

package org.bubblecloud.zigbee.network.model;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class NetworkAddress {

    public static final short fromDecimal(int value) {
        return (short) value;
    }

    public static final short fromString(String value) {
        return Short.decode(value);
    }


    public static final int toDecimal(short nwk) {
        if (nwk >= 0) {
            return nwk;
        } else {
            return 0x0000FFFF & nwk;
        }

    }

    public static final String toHex(short nwk) {
        return toString(nwk);
    }

    public static final String toString(short nwk) {
        String padding = "0000";
        String hex = Integer.toHexString(nwk);
        if (hex.length() > 4) {
            hex = hex.substring(hex.length() - 4);
        } else {
            hex = padding.substring(0, 4 - hex.length()) + hex;
        }
        return "0x" + hex.toUpperCase();
    }

}
