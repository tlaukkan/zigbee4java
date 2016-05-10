package org.bubblecloud.zigbee.network.model;

import java.util.EnumSet;
import java.util.Set;

public enum DiscoveryMode {
    Announce(1),
    Addressing(2),
    LinkQuality(4);

    public static final Set<DiscoveryMode> ALL = EnumSet.allOf(DiscoveryMode.class);

    public final int bits;

    private DiscoveryMode(int i) {
        this.bits = i;
    }

    public static int fromEnumSet(Set<DiscoveryMode> set) {
        int value = 0;
        for (DiscoveryMode mode : set) {
            value += mode.bits;
        }
        return value;
    }

    public static Set<DiscoveryMode> toEnumSet(int mask) {
        DiscoveryMode[] modes = DiscoveryMode.values();
        EnumSet<DiscoveryMode> value = EnumSet.noneOf(DiscoveryMode.class);
        for (int i = 0; i < modes.length; i++) {
            if ((modes[i].bits & mask) > 0) {
                value.add(modes[i]);
            }
        }
        return value;
    }
}