package org.bubblecloud.zigbee.v3.zcl.clusters;

import java.util.concurrent.Future;
import org.bubblecloud.zigbee.v3.CommandResult;
import org.bubblecloud.zigbee.v3.ZigBeeApi;
import org.bubblecloud.zigbee.v3.ZigBeeDevice;
import org.bubblecloud.zigbee.v3.zcl.ZclCluster;

/**
 * <b>Ballast Configuration</b> cluster implementation (<i>Cluster ID 0x0301</i>).
 * This code is autogenerated. Modifications may be overwritten!
 */
public class ZclBallastConfigurationCluster extends ZclCluster {
    // Cluster ID
    private static final int CLUSTER_ID = 0x0301;

    /**
     * Default constructor.
     */
    public ZclBallastConfigurationCluster(final ZigBeeApi zigbeeApi, final ZigBeeDevice zigbeeDevice) {
        super(zigbeeApi, zigbeeDevice, CLUSTER_ID);
    }


}
