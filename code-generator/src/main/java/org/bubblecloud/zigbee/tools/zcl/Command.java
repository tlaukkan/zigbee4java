package org.bubblecloud.zigbee.tools.zcl;

import java.util.TreeMap;

/**
 * Created by tlaukkan on 4/10/2016.
 */
public class Command {
    public int commandId;
    public String commandLabel;
    public String commandType;
    public String nameUpperCamelCase;
    public String nameLowerCamelCase;

    public TreeMap<Integer, Field> fields = new TreeMap<Integer, Field>();
}
