package org.bubblecloud.zigbee.tools.zcl;

import java.util.TreeMap;

/**
 * Created by tlaukkan on 4/10/2016.
 */
public class Command {
    public int commandId;
    public String commandName;
    public String commandType;

    public TreeMap<Integer, Field> fields = new TreeMap<Integer, Field>();
}
