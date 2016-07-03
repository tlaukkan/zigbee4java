/*

   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
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
package org.bubblecloud.zigbee.api.cluster.impl.general;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.v3.model.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.Alarms;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.DefaultResponse;
import org.bubblecloud.zigbee.api.cluster.impl.RawClusterMessageImpl;

import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.8.0
 *
 */
public class AlarmsClusterTest {

    private ZigBeeEndpoint createMockDevice() throws ZigBeeNetworkManagerException {
        ZigBeeEndpoint mock = createMock(ZigBeeEndpoint.class);

        expect(mock.invoke( (ClusterMessage) anyObject()))
            .andReturn( new RawClusterMessageImpl(
                            Alarms.ID,
                            new byte[]{0x28, 0x0A, 0x0B, 0x00, 0x00 }
            ) );
        replay( mock );
        return mock;
    }

    @Test
    @Ignore
    public void testResetAlarm() {
        AlarmsCluster cluster = null;
        ZigBeeEndpoint device = null;
        try {
            device = createMockDevice();
            cluster = new AlarmsCluster(device);
        } catch (ZigBeeNetworkManagerException ignored) {
        }
        try {
            DefaultResponse response = (DefaultResponse) cluster.resetAlarm(1, 1);
            assertEquals( Status.SUCCESS, response.getStatus() );
        } catch (ZigBeeClusterException ex) {
            fail("Unexpected exception "+ex);
            ex.printStackTrace();
        }
    }

}
