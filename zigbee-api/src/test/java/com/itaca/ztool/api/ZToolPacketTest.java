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

package com.itaca.ztool.api;

import com.itaca.ztool.api.ZToolPacket.CommandSubsystem;
import com.itaca.ztool.api.ZToolPacket.CommandType;
import com.itaca.ztool.api.af.AF_DATA_CONFIRM;
import com.itaca.ztool.api.simple.ZB_GET_DEVICE_INFO;
import com.itaca.ztool.api.system.SYS_VERSION;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_REQ;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_RSP;
import com.itaca.ztool.util.DoubleByte;
import junit.framework.TestCase;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 *
 */
public class ZToolPacketTest extends TestCase {

    public void testGetCommandId() {
	ZToolPacket testing;
	testing = new ZToolPacket(new DoubleByte(0xF000), new int[] {});
	assertEquals(testing.getCMD().get16BitValue(), 61440);
	assertEquals(testing.getCommandId(), -4096);
	assertEquals(testing.getCommandId(), (short) 0xF000);

	testing = new ZToolPacket(new DoubleByte(0x11F1), new int[] {});
	assertEquals(testing.getCMD().get16BitValue(), 4593);
	assertEquals(testing.getCommandId(), (short) 4593);
	assertEquals(testing.getCommandId(), (short) 0x11F1);

	testing = new ZToolPacket(new DoubleByte(0x0014), new int[] {});
	assertEquals(testing.getCMD().get16BitValue(), 20);
	assertEquals(testing.getCommandId(), (short) 20);
	assertEquals(testing.getCommandId(), (short) 0x0014);
    }
	
    public void testGetCommandType() {
	assertEquals(CommandType.SREQ, new ZDO_ACTIVE_EP_REQ(new ZToolAddress16(), new ZToolAddress16()).getCommandType());
	assertEquals(CommandType.SRSP, new ZDO_ACTIVE_EP_REQ_SRSP(new int[5]).getCommandType());
	assertEquals(CommandType.AREQ, new ZDO_ACTIVE_EP_RSP(new int[35]).getCommandType());
    }

    public void testGetCommandSubsystem() {
	assertEquals(CommandSubsystem.ZDO, new ZDO_ACTIVE_EP_REQ(new ZToolAddress16(), new ZToolAddress16()).getCommandSubsystem());
	assertEquals(CommandSubsystem.SYS, new SYS_VERSION().getCommandSubsystem());
	assertEquals(CommandSubsystem.AF, new AF_DATA_CONFIRM(new int[5]).getCommandSubsystem());
	assertEquals(CommandSubsystem.ZB, new ZB_GET_DEVICE_INFO(4).getCommandSubsystem());
    }

}
