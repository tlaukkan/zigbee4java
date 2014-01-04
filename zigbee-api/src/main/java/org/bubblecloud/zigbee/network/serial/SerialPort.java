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
package org.bubblecloud.zigbee.network.serial;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Generic serial port interface.
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public interface SerialPort {
    /**
     * Open the serial port.
     * @param port the port name
     * @param rate the rate
     * @return true if startup was success.
     */
    public boolean open(String port, int rate);
    /**
     * Close the serial port.
     */
    public void close();
    /**
     * Gets output stream connected to the serial port.
     * @return the output stream
     */
    public OutputStream getOutputStream();
    /**
     * Gets input stream connected to the serial port.
     * @return the input stream
     */
    public InputStream getInputStream();
}
