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

package org.bubblecloud.zigbee.util;

import java.util.StringTokenizer;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class IEEEAddress {
    
    private IEEEAddress() {}

    public static final long fromColonNotation(String ieee) {
        StringTokenizer scanner = new StringTokenizer(ieee, ":");
        long result = 0;
        while (scanner.hasMoreTokens()) {
            int octect = Integer.parseInt(scanner.nextToken(), 16);
            result = (result << 8) + (octect & 0xFF);
        }
        return result;
    }

    public static final String toHex(long ieee) {
        String padding = "0000000000000000";
        String hex = Long.toHexString(ieee);
        if (hex.length() < 16) {
            hex = padding.substring(0, 16 - hex.length()) + hex;
        }
        return "0x" + hex.toUpperCase();
    }

    public static final String toHexString(long ieee) {
        StringBuffer buffer = new StringBuffer();
        String[] parts = new String[8];
        for (int i = 0; i < parts.length; i++) {
            parts[i] = Integer.toHexString((int) (ieee & 0xFF));
            ieee = ieee >> 8;
        }

        for (int i = parts.length - 1; i > 0; i--) {
            switch (parts[i].length()) {
                case 1:
                    buffer.append("0x0").append(parts[i]).append(' ');
                    break;
                case 2:
                    buffer.append("0x").append(parts[i]).append(' ');
                    break;
            }
        }
        switch (parts[0].length()) {
            case 1:
                buffer.append("0x0").append(parts[0]);
                break;
            case 2:
                buffer.append("0x").append(parts[0]);
                break;
        }

        return buffer.toString();
    }

    public static final String toString(long ieee) {
        return toColonNotation(ieee);
    }

    public static final String toColonNotation(long ieee) {
        String padding = "0000000000000000";
        String hex = Long.toHexString(ieee);
        if (hex.length() < 16) {
            hex = padding.substring(0, 16 - hex.length()) + hex;
        }
        hex = hex.toUpperCase();
        String result = hex.substring(0, 2);
        for (int i = 1; i <= 7; i++) {
            hex = hex.substring(2);
            result = result + ":" + hex.substring(0, 2);
        }
        return result;
    }

}
