package org.bubblecloud.zigbee.tools.zcl;

import java.util.TreeMap;

/**
 * Created by tlaukkan on 4/10/2016.
 */
public class Profile {
    public int profileId;
    public String profileName;
    public String profileType;
    public TreeMap<Integer, Cluster> clusters = new TreeMap<Integer, Cluster>();
}
