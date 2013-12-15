/*
   Copyright 2010-2010 CNR-ISTI, http://isti.cnr.it
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
package it.cnr.isti.cc2480.sniffer;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.ByteUtils;
import com.itaca.ztool.util.DoubleByte;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 42 $ ($LastChangedDate: 2010-09-23 15:21:48 +0300 (Thu, 23 Sep 2010) $)
 * @since 0.1.0
 *
 */
public class SimplePacketFormat {

	public String rawFormat(ZToolPacket packet) {
		return 	ByteUtils.toBase16(packet.getPacket());
	}

	public String parsedFormat(final ZToolPacket packet) {
		final DoubleByte cmd = packet.getCMD();
		String output = "0xFE \t " 
			+ packet.getLEN() + " \t " 
			+ ByteUtils.toBase16(cmd.getMsb()) + " " + ByteUtils.toBase16(cmd.getLsb()) + " \t "
			+ " [ " + ByteUtils.toBase16(packet.getPacket(), ZToolPacket.PAYLOAD_START_INDEX) +" ] \t "
			+ ByteUtils.toBase16(packet.getFCS());
		return 	output;
	}
	
	/*
    public ZToolPacket parseZToolPacketParsedFormat(final String packet) {
        String[] fields = packet.split( "\t" );
        int[] payload = new int[ Integer.parseInt( fields[1] ) ];
        new ZToolPacket( ApiId, payload );
        final DoubleByte cmd = packet.getCMD();
        String output = "0xFE \t " 
//            + packet.getLEN().getLength()+ " \t " 
            + ByteUtils.toBase16(cmd.getMsb()) + " " + ByteUtils.toBase16(cmd.getLsb()) + " \t "
            + " [ " + ByteUtils.toBase16(packet.getPacket(),ZToolPacket.PAYLOAD_START_INDEX) +" ] \t "
            + ByteUtils.toBase16(packet.getFCS());
        return  output;
    }
    */
}
