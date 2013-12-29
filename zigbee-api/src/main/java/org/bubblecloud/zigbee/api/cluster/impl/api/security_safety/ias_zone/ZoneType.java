/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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
package org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_zone;

/**
 * @author <a href="mailto:manlio.baco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision: 42 $ ($LastChangedDate: 2010-09-23 14:21:48 +0200 (Thu, 23 Sep 2010) $)
 * @since 0.8.0
 */
public interface ZoneType {

    public class ZT {
        String attributeValue;
        String description;
        String alarm1;
        String alarm2;

        public ZT(String attributeValue, String description, String alarm1, String alarm2) {
            this.attributeValue = attributeValue;
            this.description = description;
            this.alarm1 = alarm1;
            this.alarm2 = alarm2;
        }

        public String getAttributeValue() {
            return this.attributeValue;
        }

        public String getDescription() {
            return this.description;
        }

        public String getAlarm1() {
            return this.alarm1;
        }

        public String getAlarm2() {
            return this.alarm2;
        }
    }

    public final ZT STANDARD_CIE = new ZT("0000", "Standard CIE", "System Alarm", "");
    public final ZT MOTION_SENSOR = new ZT("000d", "Motion Sensor", "Intrusion detection", "Presence indication");
    public final ZT CONTACT_SWITCH = new ZT("0015", "Contact Switch", "First portal startup-shutdown", "Second portal startup-shutdown");
    public final ZT FIRE_SENSOR = new ZT("0028", "Fire Sensor", "Fire indication", "");
    public final ZT WATER_SENSOR = new ZT("002a", "Water Sensor", "Water overflow indication", "");
    public final ZT GAS_SENSOR = new ZT("002b", "Gas Sensor", "CO indication", "Cooking indication");
    public final ZT PERSONAL_EMERGENCY_DEVICE = new ZT("002c", "Personal Emergency Device", "Fall or concussion", "Emergency button");
    public final ZT VIBRATION_MOVEMENT_SENSOR = new ZT("002d", "Vibration / Movement Sensor", "Movement indication", "Vibration");
    public final ZT REMOTE_CONTROL = new ZT("010f", "Remote Control", "Panic", "Emergency");
    public final ZT KEY_FOB = new ZT("0115", "Key FOB", "Panic", "Emergency");
    public final ZT KEYPAD = new ZT("021d", "Keypad", "Panic", "Emergency");
    public final ZT STANDARD_WARNING_DEVICES = new ZT("0225", "Standard Warning Device", "", "");
}