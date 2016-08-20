package org.bubblecloud.zigbee.tools.zcl;

import java.util.TreeMap;

/**
 * Created by tlaukkan on 4/10/2016.
 */
public class Cluster {
    public int clusterId;
    public String clusterName;
    public String clusterType;
    public TreeMap<Integer, Command> received = new TreeMap<Integer, Command>();
    public TreeMap<Integer, Command> generated = new TreeMap<Integer, Command>();
}
