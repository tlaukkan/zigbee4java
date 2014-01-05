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

package org.bubblecloud.zigbee.api.cluster.impl;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.general.Basic;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.DefaultResponse;
import org.bubblecloud.zigbee.api.cluster.impl.general.BasicCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 */
public class BasicImpl implements Basic {

    private BasicCluster basicCluster;
    private Attribute zclVersion;
    private Attribute applicationVersion;
    private Attribute stackVersion;
    private Attribute hwVersion;
    private Attribute manufacturerName;
    private Attribute modelIdentifier;
    private Attribute dateCode;
    private Attribute powerSource;
    private Attribute locationDescription;
    private Attribute physicalEnviroment;
    private Attribute deviceEnabled;
    private Attribute alarmMask;
    private Attribute disableLocalConfig;

    public BasicImpl(ZigBeeEndpoint zbDevice) {
        basicCluster = new BasicCluster(zbDevice);
        zclVersion = basicCluster.getAttributeZCLVersion();
        applicationVersion = basicCluster.getAttributeApplicationVersion();
        stackVersion = basicCluster.getAttributeStackVersion();
        hwVersion = basicCluster.getAttributeHWVersion();
        manufacturerName = basicCluster.getAttributeManufacturerName();
        modelIdentifier = basicCluster.getAttributeModelIdentifier();
        dateCode = basicCluster.getAttributeDateCode();
        powerSource = basicCluster.getPowerSource();
        locationDescription = basicCluster.getAttributeLocationDescription();
        physicalEnviroment = basicCluster.getAttributePhysicalEnvironment();
        deviceEnabled = basicCluster.getAttributeDeviceEnabled();
        alarmMask = basicCluster.getAttributeAlarmMask();
        disableLocalConfig = basicCluster.getAttributeDisableLocalConfig();
    }

    public Attribute getDisableLocalConfig() {
        return disableLocalConfig;
    }

    public Attribute getAlarmMask() {
        return alarmMask;
    }

    public Attribute getApplicationVersion() {
        return applicationVersion;
    }

    public Attribute getDateCode() {
        return dateCode;
    }

    public boolean getDeviceEnabled() throws ZigBeeDeviceException {
        try {
            Boolean value = (Boolean) deviceEnabled.getValue();
            return value.booleanValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Attribute getHWVersion() {
        return hwVersion;
    }

    public String getLocationDescription() throws ZigBeeDeviceException {
        try {
            String value = (String) locationDescription.getValue();
            return value;
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Attribute getManufacturerName() {
        return manufacturerName;
    }

    public Attribute getModelIdentifier() {
        return modelIdentifier;
    }

    public Attribute getPhysicalEnvironment() {
        return physicalEnviroment;
    }

    public Attribute getPowerSource() {
        return powerSource;
    }

    public Attribute getStackVersion() {
        return stackVersion;
    }

    public Attribute getZCLVersion() {
        return zclVersion;
    }

    public void resetToFactoryDefault() throws ZigBeeDeviceException {
        try {
            DefaultResponse response = basicCluster.resetToFactoryDefault();
            if (!response.getStatus().equals(Status.SUCCESS))
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public Reporter[] getAttributeReporters() {
        return basicCluster.getAttributeReporters();
    }

    public int getId() {
        return basicCluster.getId();
    }

    public String getName() {
        return basicCluster.getName();
    }

    public Attribute getAttribute(int id) {
        Attribute[] attributes = basicCluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getId() == id)
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return basicCluster.getAvailableAttributes();
    }

}
