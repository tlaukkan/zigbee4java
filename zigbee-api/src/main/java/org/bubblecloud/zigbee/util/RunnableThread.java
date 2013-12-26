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

package org.bubblecloud.zigbee.util;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 */
public abstract class RunnableThread implements Stoppable, Threaded {

    private final Object threadLock = new Object();
    private boolean done = false;

    private Thread executor = null;

    public Thread getExecutorThread() {
        synchronized (threadLock) {
            return executor;
        }
    }

    public void end() {
        synchronized (threadLock) {
            done = true;
        }
    }

    protected boolean isDone() {
        synchronized (threadLock) {
            return done;
        }
    }

    public void run() {
        synchronized (threadLock) {
            executor = Thread.currentThread();
        }
        task();
        synchronized (threadLock) {
            executor = null;
        }
    }

    public void interrupt() {
        synchronized (threadLock) {
            if (executor == null) return;
            executor.interrupt();
        }
    }

    protected abstract void task();
}
