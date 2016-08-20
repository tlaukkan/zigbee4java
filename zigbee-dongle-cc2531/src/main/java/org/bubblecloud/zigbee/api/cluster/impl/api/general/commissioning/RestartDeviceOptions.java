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
package org.bubblecloud.zigbee.api.cluster.impl.api.general.commissioning;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 */
public interface RestartDeviceOptions {

    /* startup mode
     * 0b000: restart installing and using current startup parameters set
     * 0b001: restart using and not replacing current set of stack attributes	 *
     */
    byte[] getStartupMode();

    /* immediate
     * 1: 	restart either immediately on receipt of the RestartDeviceRequest frame if delay 0
     * 		or immediately after prescribed delay and jitter has transpired if not
     * 0: 	restart until after prescribed delay and jitter (if any) has transpired
     * 		but can also wait for a "convenient" moment (i.e. all pending frames have been transmitted)
     */
    byte getImmediate();
    // byte[] getReserved();
}
