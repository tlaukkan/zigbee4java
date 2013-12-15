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

package org.bubblecloud.zigbee.proxy;

import org.bubblecloud.zigbee.core.ZigBeeDevice;
import org.bubblecloud.zigbee.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 *
 * Generic class that simplify the creation of factory, and avoids the creation of a class for<br>
 * each factory that we want to register
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @since 0.4.0
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $) *
 */
public class GenericHADeviceFactory extends HADeviceFactoryBase {

    static final Logger logger = LoggerFactory.getLogger(GenericHADeviceFactory.class);

    final protected int deviceId;
    final protected Class<?> inter, impl;
    
    protected String[] classes;
    
    
    /**
     * Build an {@link HADeviceFactory} that refine {@link ZigBeeDevice} by means of <code>cImplementation</code>,<br>
     * into a <code>cInterface</code> service. This factory build the instance of the new service by invoking<br>
     * the constructor  <code>cImplementation(BundleContext, ZigBeeDevice)</code>.<br>
     * By default the {@link #getRefinedInterfaces()} is built to return the values:<br>
     * <pre>
     * cInterface.getName()
     * HADevice.class.getName()
     * </pre>
     * 
     *  
     * @param ctx {@link BundleContext} to use for registering the {@link HADeviceFactory} service
     * @param cInterface {@link Class} representing the refined service that this factory will install
     * @param cImplementation {@link Class} representing the implementation of the refinement service that will be created by the factory
     * 
     * @throws ZigBeeHAException
     */
    public GenericHADeviceFactory(BundleContext ctx, Class<?> cInterface, Class<?> cImplementation) throws ZigBeeHAException {
		super(ctx, cInterface);
		
		this.inter = cInterface;
		this.impl = cImplementation;
		classes = new String[]{ cInterface.getName(), HADevice.class.getName() };
		
		try {
		    deviceId = (Integer) cInterface.getField("DEVICE_ID").get(null);
		} catch (Exception e) {
		    logger.error(
			    "Unable to retrive the implemented the DeviceId refined by this driver"
			    + " by means of the reflection, the class {} does not provide the static "
			    + "field DEVICE_ID.\n"
			    + "Please modify the source code of the class by adding such static field or"
			    + "implement a proper {} class", 
			    cInterface.getName(), HADeviceFactory.class.getName()
		    );
		    logger.debug("Stack exception of the GenericHADeviceFactory() error", e);
		    
			throw new ZigBeeHAException("Unable to retrieve DEVICE_ID by means of reflection", e);
		}
    }

    @Override
    public int getDeviceId() {
		return deviceId;
    }

    public GenericHADeviceFactory setRefinedInterfaces(String[] classNames) {
		classes = classNames;
		return this;
    }
    
    @Override
    public String[] getRefinedInterfaces() {
		return classes;
    }

    @Override
    public HADeviceBase getInstance(ZigBeeDevice zbd) {
		try {
		    Constructor builder = impl.getConstructor(BundleContext.class, ZigBeeDevice.class);
		    HADeviceBase device = (HADeviceBase) builder.newInstance(ctx, zbd);
		    return device;
		} catch (Exception e) {
		    logger.error("Unable to create an instance of the refined device due to:", e);
		    return null;
		}
    }

}
