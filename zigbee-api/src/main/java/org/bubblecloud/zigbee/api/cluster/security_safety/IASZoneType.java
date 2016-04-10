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
package org.bubblecloud.zigbee.api.cluster.security_safety;


/**
 * The IAS zone types.
 *
 * @author Tommi S.E. Laukkanen
 */
public enum IASZoneType {
    STANDARD_CIE(0x0000),
    MOTION_SENSOR(0x000d),
    CONTACT_SWITCH(0x0015),
    FIRE_SENSOR(0x0028),
    WATER_SENSOR(0x002a),
    GAS_SENSOR(0x002b),
    PERSONAL_EMERGENCY_DEVICE(0x002c),
    VIBRATION_MOVEMENT_SENSOR(0x002d),
    REMOTE_CONTROL(0x010f),
    KEY_PAD(0x021d),
    STANDARD_WARNING_DEVICE(0x0225);

    final public int value;

    private IASZoneType(int value){
        this.value = value;
    }
}
