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

package org.bubblecloud.zigbee.api;

/**
 * The interface of the service registered by the <b>Home Automation Driver</b> architecture
 * which is used to provides among all the bundles composing the <b>Home Automation Driver</b>,
 * (i.e.: core drivers and driver extension) the default configuration of the reporting.
 * <p>
 * Interfaces that contains the definition of all the properties key and default values,
 * that affects the reporting behavior of the <b>Home Automation Driver</b> architecture
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.6.0
 */
public class ReportingConfiguration {


    public int getReportingMinimum() {
        return 60;
    }

    public int getReportingMaximum() {
        return 0;
    }

    public double getReportingChange() {
        return 0.0d;
    }

    public boolean getReportingOverwrite() {
        return true;
    }

}