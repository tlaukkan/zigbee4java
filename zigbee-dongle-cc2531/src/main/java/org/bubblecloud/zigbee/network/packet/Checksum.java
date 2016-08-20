/*
   Copyright 2008-2013 Andrew Rapp, http://code.google.com/p/xbee-api/

   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 

   Copyright 2008-2013 ITACA-TSB, http://www.tsb.upv.es/
   Instituto Tecnologico de Aplicaciones de Comunicacion 
   Avanzadas - Grupo Tecnologias para la Salud y el 
   Bienestar (TSB)


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

package org.bubblecloud.zigbee.network.packet;

/**
 * @author <a href="mailto:andrew.rapp@gmail.com">Andrew Rapp</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class Checksum {

    //private final static Logger log = Logger.getLogger(Checksum.class);

    public int checksum = 0;

    /**
     * Don't add Checksum byte when computing checksum!!
     *
     * @param val
     */
    public void addByte(int val) {
        //checksum+= val;
        checksum = checksum ^ val;
    }

    /**
     * Computes the checksum
     * 
     * <b> Actually, this does nothing!!</b>
     */
    public void compute() {

        // discard values > 1 byte
        //checksum = 0xff & checksum;
        // perform 2s complement
        //checksum = 0xff - checksum;

        //log.debug("computed checksum is " + ByteUtils.formatByte(checksum));
    }

    /**
     * First add all relevant bytes, including checksum
     *
     * @return true if the checksum is correct (equals 0xff)
     */
    public boolean verify() {
        checksum = checksum & 0xff;
        //log.debug("verify checksum is " + checksum);
        return 0xff == checksum;
    }

    public int getChecksum() {
        return checksum;
    }

	/*public static void main(String[] args) {
		//83 56 78 24 00 01 02 00 03 ff 85
		Checksum ck = new Checksum();

		ck.addByte(0x83);
		ck.addByte(0x56);
		ck.addByte(0x78);
		ck.addByte(0x26);
		ck.addByte(0x00);
		ck.addByte(0x01);
		ck.addByte(0x02);
		ck.addByte(0x00);
		ck.addByte(0x03);
		ck.addByte(0xff);
		// checksum
		ck.addByte(0x83);
		
		// checksum is 0x83
		//ck.compute();
		ck.verify();
		
		System.out.println("checksum is " + ByteUtils.formatByte(ck.getChecksum()));
	}*/
}
