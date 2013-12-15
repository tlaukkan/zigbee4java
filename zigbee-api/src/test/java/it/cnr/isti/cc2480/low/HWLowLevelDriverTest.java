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
package it.cnr.isti.cc2480.low;

import com.itaca.ztool.api.ZToolException;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.api.system.SYS_VERSION;
import com.itaca.ztool.api.system.SYS_VERSION_RESPONSE;
import it.cnr.isti.cc2480.virtual.Emulator;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 *
 */
public class HWLowLevelDriverTest {

    /**
     * Test method for {@link it.cnr.isti.cc2480.low.HWLowLevelDriver#sendPacket(com.itaca.ztool.api.ZToolPacket)}.
     */
    @Test
    public void testSendPacket() {
        final HWLowLevelDriver driver = new HWLowLevelDriver();
        final ZToolPacket[] packets = new ZToolPacket[1];
        Emulator serial;
        try {
            serial = new Emulator( Emulator.class.getResourceAsStream( "session.fsm" ) );
        } catch (IOException e) {
            serial = null;
        }
        driver.addPacketListener(new PacketListener(){
            public void packetReceived(ZToolPacket packet) {
                synchronized (packets) {
                    packets[0] = packet;
                    packets.notify();
                }
            }
        });
        driver.setSerialHandler(serial);
        try {
            driver.open("VIRTUAL", 19200);
        } catch (ZToolException ex) {
            ex.printStackTrace();
        }
        try {
            driver.sendPacket(new SYS_VERSION());
        } catch (IOException e) {
        }
        long future = System.currentTimeMillis()+5000;
        synchronized (packets) {
            while (packets[0] == null && future > System.currentTimeMillis() ) {
                try {
                    packets.wait(250);
                } catch (InterruptedException e) {
                }
            }
        }
        if( packets[0] == null ){
            fail("Recieved no answer from the emulated transmission");
        }else{
            assertEquals(
                    "Recieved the wrong packet from the emulated transmission",
                    SYS_VERSION_RESPONSE.class.getName(),
                    packets[0].getClass().getName()
            );
        }
    }

}
