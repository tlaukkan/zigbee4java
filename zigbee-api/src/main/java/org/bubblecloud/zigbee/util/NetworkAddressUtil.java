package org.bubblecloud.zigbee.util;

import org.bubblecloud.zigbee.network.packet.ZToolAddress16;

/**
 * Utility for address manipulation.
 * @author Tommi S.E. Laukkanen
 */
public class NetworkAddressUtil {

    /**
     * Converts short network address to integer format.
     * @param networkAddress the network address as short
     * @return the network address as integer
     */
    public static int shortToInt(final short networkAddress) {
        ZToolAddress16 nwk = new ZToolAddress16(
                Integers.getByteAsInteger(networkAddress, 1),
                Integers.getByteAsInteger(networkAddress, 0)
        );
        return nwk.get16BitValue();
    }

}
