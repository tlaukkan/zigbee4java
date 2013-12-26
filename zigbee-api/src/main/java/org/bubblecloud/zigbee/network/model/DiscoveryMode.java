package org.bubblecloud.zigbee.network.model;

import java.util.EnumSet;

public enum DiscoveryMode {
    Announce(1),
    Addressing(2),
    LinkQuality(4);

    public static final EnumSet<DiscoveryMode> ALL = EnumSet.allOf(DiscoveryMode.class);

    public final int bits;

    private DiscoveryMode(int i) {
        this.bits = i;
    }

    public static int fromEnumSet(EnumSet<DiscoveryMode> set) {
        int value = 0;
        for (DiscoveryMode mode : set) {
            value += mode.bits;
        }
        return value;
    }

    public static EnumSet<DiscoveryMode> toEnumSet(int mask) {
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