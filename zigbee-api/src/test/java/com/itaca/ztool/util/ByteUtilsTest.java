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
package com.itaca.ztool.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 * 
 */
public class ByteUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(ByteUtilsTest.class);

	/**
	 * @since 0.8.0
	 */
	@Test
	public void testToBase16() {
		assertEquals("0x5f", ByteUtils.toBase16(-161) );
		try{
			ByteUtils.toBase16(325);
			fail("Expected IllegalArgumentException");
		}catch(IllegalArgumentException ex){			
		}
		try{		
			ByteUtils.toBase16(-257);
			fail("Expected IllegalArgumentException");
		}catch(IllegalArgumentException ex){			
		}
		assertEquals("0xc8", ByteUtils.toBase16(200) );
		assertEquals("0xc8", ByteUtils.toBase16((byte) 0xc8) );
		assertEquals("0xc8", ByteUtils.toBase16(0xc8) );
	}

	/**
	 * Test methods for coherance among the following group:<br>
	 * <ul>
	 * <li>{@link com.itaca.ztool.util.ByteUtils#fromBase16(String)}</li>
	 * <li>{@link com.itaca.ztool.util.ByteUtils#toBase16(byte[])}</li>
	 * <li>{@link com.itaca.ztool.util.ByteUtils#toBase16(int[])}</li>
	 * </ul>
	 */
	@Test
	public void testFromToBase16() {
		Object[][] stream = new Object[][]{ 
				new Object[]{
					"0x00 0x06 0x0f 0x10 0x60 0xff 0xf1",
					new byte[]{ 0, 6, 15, 16, 96, -1, -15},
					new int[] { 0, 6, 15, 16, 96, 255, 241}
				},
				new Object[]{
						"0xb8 0x3c",
						new byte[]{ (byte) 0xb8, 0x3c },
						new int[] { 0xb8, 0x3c }
				}
		};
		
		String doubleConversion = 
			"0x00 0xfa 0x6b 0x25 0x01 0x00 0x4b 0x12 0x00 0x00 " +
			"0x00 0x00 0x07 0x01 0x00 0x3e 0x14 0x7b 0x28 0xb8 " +
			"0x3c 0xf5 0x50 0x32 0x65 0x6f 0x79";
		for (int i = 0; i < stream.length; i++) {
			assertEquals(
					"toBase16((byte[]) doesn't match toBase16((int[]) output", 
					ByteUtils.toBase16((byte[]) stream[i][1]),
					ByteUtils.toBase16((int[]) stream[i][2])
			);
			assertArrayEquals(
					"int[] doesn't match fromBase16(String) output",
					(byte[]) stream[i][1],
					ByteUtils.fromBase16toByteArray((String) stream[i][0])
			);
			assertArrayEquals(
					"ftom byte[] to String and back to byte[]",
					(byte[]) stream[i][1],
					ByteUtils.fromBase16toByteArray(ByteUtils.toBase16((byte[]) stream[i][1]))
			);
		}
		assertEquals(
				"ftom String to byte[] and back to String",
				doubleConversion,
				ByteUtils.toBase16(ByteUtils.fromBase16toByteArray(doubleConversion))
		);
	}

}
