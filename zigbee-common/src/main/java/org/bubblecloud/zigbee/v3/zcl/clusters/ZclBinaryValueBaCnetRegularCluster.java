package org.bubblecloud.zigbee.v3.zcl.clusters;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import org.bubblecloud.zigbee.v3.CommandResult;
import org.bubblecloud.zigbee.v3.ZigBeeApi;
import org.bubblecloud.zigbee.v3.ZigBeeDevice;
import org.bubblecloud.zigbee.v3.zcl.ZclAttribute;
import org.bubblecloud.zigbee.v3.zcl.ZclCluster;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclDataType;

/**
 * <b>Binary Value (BACnet Regular)</b> cluster implementation (<i>Cluster ID 0x060C</i>).
 * This code is autogenerated. Modifications may be overwritten!
 */
public class ZclBinaryValueBaCnetRegularCluster extends ZclCluster {
    // Cluster ID
    private static final int CLUSTER_ID = 0x060C;

    // Attribute initialisation
    protected Map<Integer, ZclAttribute> initializeAttributes() {
        Map<Integer, ZclAttribute> attributeMap = new HashMap<Integer, ZclAttribute>(0);


        return attributeMap;
    }

    /**
     * Default constructor.
     */
    public ZclBinaryValueBaCnetRegularCluster(final ZigBeeApi zigbeeApi, final ZigBeeDevice zigbeeDevice) {
        super(zigbeeApi, zigbeeDevice, CLUSTER_ID);
    }


}
