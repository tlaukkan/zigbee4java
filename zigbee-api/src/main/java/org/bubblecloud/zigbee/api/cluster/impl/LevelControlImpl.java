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
import org.bubblecloud.zigbee.api.cluster.general.LevelControl;
import org.bubblecloud.zigbee.api.cluster.general.event.CurrentLevelListener;
import org.bubblecloud.zigbee.api.cluster.impl.event.CurrentLevelBridgeListeners;
import org.bubblecloud.zigbee.api.ReportingConfiguration;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.global.DefaultResponse;
import org.bubblecloud.zigbee.api.cluster.impl.general.LevelControlCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 */
public class LevelControlImpl implements LevelControl {

    private LevelControlCluster levelControlCluster;
    private Attribute currentLevel;
    private Attribute remainingTime;
    private Attribute onOffTransitionTime;
    private Attribute onLevel;
    private final CurrentLevelBridgeListeners eventBridge;

    public LevelControlImpl(ZigBeeEndpoint zbDevice) {
        levelControlCluster = new LevelControlCluster(zbDevice);
        currentLevel = levelControlCluster.getAttributeCurrentLevel();
        remainingTime = levelControlCluster.getAttributeRemainingTime();
        onOffTransitionTime = levelControlCluster.getAttributeOnOffTransitionTime();
        onLevel = levelControlCluster.getAttributeOnLevel();
        eventBridge = new CurrentLevelBridgeListeners(new ReportingConfiguration(), currentLevel, this);
    }


    public Attribute getCurrentLevel() {
        return currentLevel;
    }

    public Attribute getOnLevel() {
        return onLevel;
    }

    public Attribute getOnOffTransitionTime() {
        return onOffTransitionTime;
    }

    public Attribute getRemainingTime() {
        return remainingTime;
    }

    public void move(byte mode, short rate) throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) levelControlCluster.move(mode, rate);
            if (!response.getStatus().equals(Status.SUCCESS))
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void moveWithOnOff(byte mode, short rate) throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) levelControlCluster.moveWithOnOff(mode, rate);
            if (!response.getStatus().equals(Status.SUCCESS))
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }


    public void moveToLevel(short level, int time) throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) levelControlCluster.moveToLevel(level, time);
            if (!response.getStatus().equals(Status.SUCCESS))
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void moveToLevelWithOnOff(short level, int time) throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) levelControlCluster.moveToLevelWithOnOff(level, time);
            if (!response.getStatus().equals(Status.SUCCESS))
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void step(byte mode, short step, int time) throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) levelControlCluster.step(mode, step, time);
            if (!response.getStatus().equals(Status.SUCCESS))
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void stepWithOnOff(byte mode, short step, int time) throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) levelControlCluster.stepWithOnOff(mode, step, time);
            if (!response.getStatus().equals(Status.SUCCESS))
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }


    public void stop() throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) levelControlCluster.stop();
            if (!response.getStatus().equals(Status.SUCCESS))
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }

    public void stopWithOnOff() throws ZigBeeDeviceException {
        try {
            DefaultResponse response = (DefaultResponse) levelControlCluster.stopWithOnOff();
            if (!response.getStatus().equals(Status.SUCCESS))
                throw new ZigBeeDeviceException(response.getStatus().toString());
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeDeviceException(e);
        }
    }


    public Reporter[] getAttributeReporters() {
        return levelControlCluster.getAttributeReporters();
    }

    public int getId() {
        return levelControlCluster.getId();
    }

    public String getName() {
        return levelControlCluster.getName();
    }

    public Attribute getAttribute(int id) {
        Attribute[] attributes = levelControlCluster.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getId() == id)
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return levelControlCluster.getAvailableAttributes();
    }


    public boolean subscribe(CurrentLevelListener listener) {
        return eventBridge.subscribe(listener);
    }

    public boolean unsubscribe(CurrentLevelListener listener) {
        return eventBridge.unsubscribe(listener);
    }

}
