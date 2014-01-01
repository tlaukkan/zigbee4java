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

package org.bubblecloud.zigbee.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.3.0
 */
public class ThreadUtils {

    private final static Logger logger = LoggerFactory.getLogger(ThreadUtils.class);

    /**
     * Wait for x ms even if interrupt are sent to the thread waiting
     *
     * @param time the number of ms to wait
     */
    public static final void waitNonPreemptive(long time) {
        final long end = System.currentTimeMillis() + time;
        do {
            try {
                final long delta = Math.max(end - System.currentTimeMillis(), 0);
                logger.trace("{} waiting for {}ms", Thread.currentThread(), delta);
                Thread.sleep(delta);
            } catch (InterruptedException ignored) {
            }
        } while (end > System.currentTimeMillis());
    }

    /**
     * Wait for up to x ms, in fact if interrupt is received it will end before the expected time
     *
     * @param time the number of ms to wait
     * @since 0.4.0
     */
    public static final void waitingUntil(long time) {
        do {
            try {
                final long delta = Math.max(time - System.currentTimeMillis(), 0);
                logger.trace("{} waiting for {}ms", Thread.currentThread(), delta);
                Thread.sleep(delta);
            } catch (InterruptedException ignored) {
                break;
            }
        } while (time > System.currentTimeMillis());
    }
}
