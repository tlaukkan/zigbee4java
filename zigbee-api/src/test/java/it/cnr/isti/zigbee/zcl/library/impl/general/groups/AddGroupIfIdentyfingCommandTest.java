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
package it.cnr.isti.zigbee.zcl.library.impl.general.groups;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.1
 *
 */
public class AddGroupIfIdentyfingCommandTest {

    @Test
    public void testGetPayload() {
        AddGroupIfIdentyfingCommand command = new AddGroupIfIdentyfingCommand(0x1020,"House");
        assertArrayEquals(new byte[]{
                0x20, 0x10, 0x05, 0x48, 0x6f, 0x75, 0x73, 0x65
        }, command.getPayload()
        );

        assertArrayEquals(new byte[]{
                0x20, 0x10, 0x05, 0x48, 0x6f, 0x75, 0x73, 0x65
        }, command.getPayload()
        );
    }

}
