package org.bubblecloud.zigbee.network.impl.protocol;

import java.util.ArrayList;
import java.util.List;

public class ZclCommandFormat {
    private int profileId;
    private int clusterId;
    private int commandId;
    private List<ZclCommandField> fields = new ArrayList<ZclCommandField>();

    public ZclCommandFormat(int profileId, int clusterId, int commandId) {
        this.profileId = profileId;
        this.clusterId = clusterId;
        this.commandId = commandId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public List<ZclCommandField> getFields() {
        return fields;
    }
}
