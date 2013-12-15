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

package com.itaca.ztool.api.af;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class of {@link AF_DATA_CONFIRM}
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class AF_DATA_CONFIRMTest {

    @Test
    public void testGetStatus() {
        assertEquals(1, new AF_DATA_CONFIRM(1,1,1).getStatus());
        assertEquals(1, new AF_DATA_CONFIRM(new int[]{1, 1, 1}).getStatus());

        assertEquals(1, new AF_DATA_CONFIRM(1,2,3).getStatus());
        assertEquals(1, new AF_DATA_CONFIRM(new int[]{1, 2, 3}).getStatus());
    }

}
