package org.bubblecloud.zigbee.network.packet.af;

import org.bubblecloud.zigbee.network.packet.ResponseStatus;
import org.bubblecloud.zigbee.network.packet.ZToolCMD;
import org.bubblecloud.zigbee.network.packet.ZToolPacket;
import org.bubblecloud.zigbee.util.DoubleByte;

/**
 * AF_DATA_SRSP_EXT command implementation.
 */
public class AfDataSrspExt extends ZToolPacket {
    /**
     * Response status.
     */
    private int status;

    /**
     * Constructor which sets frame data.
     * @param framedata the frame data
     */
    public AfDataSrspExt(int[] framedata) {
        this.status = framedata[0];
        super.buildPacket(new DoubleByte(ZToolCMD.AF_DATA_SRSP_EXT), framedata);
    }

    @Override
    public String toString() {
        return "AF_DATA_SRSP_EXT{" +
                "Status=" + ResponseStatus.getStatus(status) +
                '}';
    }

    /**
     * Gets response status.
     * @return the status
     */
    public int getStatus() {
        return status;
    }
}
