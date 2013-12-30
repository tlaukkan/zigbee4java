/*
   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
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

import org.bubblecloud.zigbee.api.cluster.general.Commissioning;
import org.bubblecloud.zigbee.api.cluster.general.NetworkKeyType;
import org.bubblecloud.zigbee.api.cluster.general.StartupControl;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;

/**
 * PLACEHOLDER TO IMPLEMENT
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.0
 */
public class CommissioningImpl implements Commissioning {

    public int getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public Reporter[] getAttributeReporters() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute[] getAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttribute(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getShortAddress() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setShortAddress(int shortAddress) {
        // TODO Auto-generated method stub

    }

    public long getExtendedPANId() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setExtendedPANId(long extendedPANId) {
        // TODO Auto-generated method stub

    }

    public int getPANId() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setPANId(int PANId) {
        // TODO Auto-generated method stub

    }

    public int getChannelmask() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setChannelmask(int channelMask) {
        // TODO Auto-generated method stub

    }

    public int getProtocolVersion() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setProtocolVersion(int protocolVersion) {
        // TODO Auto-generated method stub

    }

    public int getStackProfile() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setStackProfile(int stackProfile) {
        // TODO Auto-generated method stub

    }

    public StartupControl getStartupControl() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setStartupControl(StartupControl startupControl) {
        // TODO Auto-generated method stub

    }

    public long getTrustCenterAddress() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setTrustCenterAddress(long trustCenterAddress) {
        // TODO Auto-generated method stub

    }

    public byte[] getTrustCenterMasterKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setTrustCenterMasterKey(byte[] trustCenterMasterKey) {
        // TODO Auto-generated method stub

    }

    public byte[] getNetworkKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setNetworkKey(byte[] networkKey) {
        // TODO Auto-generated method stub

    }

    public boolean isUseInsecureJoin() {
        // TODO Auto-generated method stub
        return false;
    }

    public void setUseInsecureJoin(boolean useInsecureJoin) {
        // TODO Auto-generated method stub

    }

    public byte[] getPreconfiguredLinkKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setPreconfiguredLinkKey(byte[] preconfiguredLinkKey) {
        // TODO Auto-generated method stub

    }

    public int getNetworkKeySeqNum() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setNetworkKeySeqNum(int networkKeySeqNum) {
        // TODO Auto-generated method stub

    }

    public NetworkKeyType getNetworkKeyType() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setNetworkKeyType(NetworkKeyType networkKeyType) {
        // TODO Auto-generated method stub

    }

    public int getNetworkManagerAddress() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setNetworkManagerAddress(int networkManagerAddress) {
        // TODO Auto-generated method stub

    }

    public int getScanAttempts() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setScanAttempts(int scanAttempts) {
        // TODO Auto-generated method stub

    }

    public int getTimeBetweenScans() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setTimeBetweenScans(int timeBetweenScans) {
        // TODO Auto-generated method stub

    }

    public int getRejoinInterval() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setRejoinInterval(int rejoinInterval) {
        // TODO Auto-generated method stub

    }

    public int getMaxRejoinInterval() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setMaxRejoinInterval(int maxRejoinInterval) {
        // TODO Auto-generated method stub

    }

    public int getIndirectPollRate() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setIndirectPollRate(int indirectPollRate) {
        // TODO Auto-generated method stub

    }

    public int getParentRetryThreshold() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setParentRetryThreshold(int parentRetryThreshold) {
        // TODO Auto-generated method stub

    }

    public boolean isConcentratorFlag() {
        // TODO Auto-generated method stub
        return false;
    }

    public void setConcentratorFlag(boolean concentratorFlag) {
        // TODO Auto-generated method stub

    }

    public int getConcentratorRadius() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setConcentratorRadius(int concentratorRadius) {
        // TODO Auto-generated method stub

    }

    public int getConcentratorDiscoveryTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setConcentratorDiscoveryTime(int concentratorDiscoveryTime) {
        // TODO Auto-generated method stub

    }

    public Status restartDevice(StartupControl mode, boolean immediate,
                                int delay, int jitter) {
        // TODO Auto-generated method stub
        return null;
    }

    public Status saveStartupParameters(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    public Status restoreStartupParameters(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    public Status resetStartupParameter(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    public Status resetCurrentStartupParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    public Status resetAllStartupParameters() {
        // TODO Auto-generated method stub
        return null;
    }

}
