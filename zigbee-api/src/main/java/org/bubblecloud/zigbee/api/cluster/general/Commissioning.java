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

package org.bubblecloud.zigbee.api.cluster.general;

import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.7.0
 *
 */
public interface Commissioning extends Cluster {

    public abstract int getShortAddress();
    public abstract void setShortAddress(int shortAddress);

    public abstract long getExtendedPANId();
    public abstract void setExtendedPANId(long extendedPANId);

    public abstract int getPANId();
    public abstract void setPANId(int PANId);

    public abstract int getChannelmask();
    public abstract void setChannelmask(int channelMask);

    public int getProtocolVersion();
    public void setProtocolVersion(int protocolVersion);

    public int getStackProfile();
    public void setStackProfile(int stackProfile);

    public StartupControl getStartupControl();
    public void setStartupControl(StartupControl startupControl);

    public long getTrustCenterAddress();
    public void setTrustCenterAddress(long trustCenterAddress);

    public byte[] getTrustCenterMasterKey();
    public void setTrustCenterMasterKey(byte[] trustCenterMasterKey);

    public byte[] getNetworkKey();
    public void setNetworkKey(byte[] networkKey);

    public boolean isUseInsecureJoin();
    public void setUseInsecureJoin(boolean useInsecureJoin);

    public byte[] getPreconfiguredLinkKey();
    public void setPreconfiguredLinkKey(byte[] preconfiguredLinkKey);

    public int getNetworkKeySeqNum();
    public void setNetworkKeySeqNum(int networkKeySeqNum);

    public NetworkKeyType getNetworkKeyType();
    public void setNetworkKeyType(NetworkKeyType networkKeyType);

    public int getNetworkManagerAddress();
    public void setNetworkManagerAddress(int networkManagerAddress);

    public int getScanAttempts();
    public void setScanAttempts(int scanAttempts);

    public int getTimeBetweenScans();
    public void setTimeBetweenScans(int timeBetweenScans);

    public int getRejoinInterval();
    public void setRejoinInterval(int rejoinInterval);

    public int getMaxRejoinInterval();
    public void setMaxRejoinInterval(int maxRejoinInterval);

    public int getIndirectPollRate();
    public void setIndirectPollRate(int indirectPollRate);

    public int getParentRetryThreshold();
    public void setParentRetryThreshold(int parentRetryThreshold);

    public boolean isConcentratorFlag();
    public void setConcentratorFlag(boolean concentratorFlag);

    public int getConcentratorRadius();
    public void setConcentratorRadius(int concentratorRadius);

    public int getConcentratorDiscoveryTime();
    public void setConcentratorDiscoveryTime(int concentratorDiscoveryTime);

    public Status restartDevice(StartupControl mode, boolean immediate, int delay, int jitter);
    public Status saveStartupParameters(int index);
    public Status restoreStartupParameters(int index);

    public Status resetStartupParameter(int index);
    public Status resetCurrentStartupParameters();
    public Status resetAllStartupParameters();

}
