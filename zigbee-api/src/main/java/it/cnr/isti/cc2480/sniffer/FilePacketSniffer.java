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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi - ISTI-CNR
 *         </a> - Copyright (c) 04/ago/2010
 * @version $LastChangedRevision: 42 $ ($LastChangedDate: 2010-09-23 15:21:48 +0300 (Thu, 23 Sep 2010) $)
 * @since 0.6.0
 */
public class FilePacketSniffer implements SnifferInterface {

	
	private static final Logger logger = LoggerFactory.getLogger(FilePacketSniffer.class);
	private PrintStream out;
	
	public static final String OUTPUT_FILE_DEFAULT = "low.sniffer.log";
	public static final String OUTPUT_FILE_KEY = "it.cnr.isti.cc2480.low.sniffer.output_file";
		
	private static final SimpleDateFormat LONG_TIME_STAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static final SimplePacketFormat PACKET_FORMATTER = new SimplePacketFormat();

	
	public boolean initialize(){
		String fileName = System.getProperty(OUTPUT_FILE_KEY,OUTPUT_FILE_DEFAULT);
		try {
			out = new PrintStream(new FileOutputStream(fileName,true));
			out.println("#Starting new log session at "+LONG_TIME_STAMP.format(new Date(System.currentTimeMillis())));
		} catch (Exception e) {
			cleanUp();
			logger.debug("Failed to initialize the sniffer",e);
			return false;
		}
		return true;
	}
	
	public void finalize() {
	    cleanUp();
	}
	
	/**
	 * 
	 */
	private void cleanUp() {
		if( out != null ) {
			out.flush();
			out.close();
		}
		out = null;
	}

	public void incomingPacket(ZToolPacket p) {
		synchronized (out) {
			out.println( "<-"
				+ "," + LONG_TIME_STAMP.format(new Date(System.currentTimeMillis())) 
				+ "," + p.getClass().getName()
				+ "," + PACKET_FORMATTER.parsedFormat(p)
				+ "," + PACKET_FORMATTER.rawFormat(p)	
			);
		}
	}

	public void outcomingPacket(ZToolPacket p) {
		synchronized (out) {
			out.println( "->"
				+ "," + LONG_TIME_STAMP.format(new Date(System.currentTimeMillis())) 
				+ "," + p.getClass().getName()
				+ "," + PACKET_FORMATTER.parsedFormat(p)
				+ "," + PACKET_FORMATTER.rawFormat(p)	
			);			
		}		
	}

}
