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

package org.bubblecloud.zigbee.api.cluster.impl.api.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLCluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;

/**
 * This class represent the <b>Level Control</b> Cluster as defined by the document:
 * <i>ZigBee Cluster Library</i> public release version 075123r01ZB
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public interface LevelControl extends ZCLCluster {

    static final short ID = 0x0008;
    static final String NAME = "LevelControl";
    static final String DESCRIPTION = "Attributes and commands for controlling devices that";

    static final byte MOVE_TO_LEVEL_ID = 0x00;
    static final byte MOVE_ID = 0x01;
    static final byte STEP_ID = 0x02;
    static final byte STOP_ID = 0x03;

    static final byte MOVE_TO_LEVEL_WITH_ONOFF_ID = 0x04;
    static final byte MOVE_WITH_ONOFF_ID = 0x05;
    static final byte STEP_WITH_ONOFF_ID = 0x06;
    static final byte STOP_WITH_ONOFF_ID = 0x07;

    public Attribute getAttributeCurrentLevel();

    public Attribute getAttributeRemainingTime();

    public Attribute getAttributeOnOffTransitionTime();

    public Attribute getAttributeOnLevel();

    public Response moveToLevel(short level, int time) throws ZigBeeClusterException;

    public Response move(byte mode, short rate) throws ZigBeeClusterException;

    public Response step(byte mode, short step, int time) throws ZigBeeClusterException;

    public Response stop() throws ZigBeeClusterException;

    /**
     * @since 0.6.0
     */
    public Response moveToLevelWithOnOff(short level, int time) throws ZigBeeClusterException;

    /**
     * @since 0.6.0
     */
    public Response moveWithOnOff(byte mode, short rate) throws ZigBeeClusterException;

    /**
     * @since 0.6.0
     */
    public Response stepWithOnOff(byte mode, short step, int time) throws ZigBeeClusterException;

    /**
     * @since 0.6.0
     */
    public Response stopWithOnOff() throws ZigBeeClusterException;

}
