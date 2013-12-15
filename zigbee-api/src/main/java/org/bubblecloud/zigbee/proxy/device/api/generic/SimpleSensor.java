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
package org.bubblecloud.zigbee.proxy.device.api.generic;


import org.bubblecloud.zigbee.proxy.DeviceProxy;
import org.bubblecloud.zigbee.proxy.cluster.glue.general.BinaryInput;
import org.bubblecloud.zigbee.util.ArraysUtil;
import org.bubblecloud.zigbee.proxy.ProxyConstants;


/**
 * @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.0
 *
 */
public interface SimpleSensor extends DeviceProxy {

    public static final int DEVICE_ID = 0x000C;
    public static final String NAME = "Simple Sensor";
    public static final int[] MANDATORY = ArraysUtil.append( DeviceProxy.MANDATORY, new int[] { ProxyConstants.BINARY_INPUT } );
    public static final int[] OPTIONAL = DeviceProxy.OPTIONAL;
    public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
    public static final int[] CUSTOM = {};

    /**
     * Access method for the <b>Mandatory</b> cluster: {@link BinaryInput}
     *
     * @return the {@link BinaryInput} cluster object
     */
    public BinaryInput getBinaryInput();

}
