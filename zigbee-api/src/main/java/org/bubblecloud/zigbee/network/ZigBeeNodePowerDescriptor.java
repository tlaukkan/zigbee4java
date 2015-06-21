package org.bubblecloud.zigbee.network;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import org.bubblecloud.zigbee.network.packet.zdo.ZDO_POWER_DESC_RSP;

/**
 * This implements the Node Power Descriptor.
 * <p>
 * The node power descriptor gives a dynamic indication of the power status of the
 * node and is mandatory for each node.
 * <p>
 * There shall be only one node power descriptor in a node.
 * 
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 */
public class ZigBeeNodePowerDescriptor {
	private POWER_MODE powerMode;
	private List<POWER_SOURCE> powerSourcesAvailable;
	private POWER_SOURCE powerSource;
	private POWER_LEVEL powerLevel;

	enum POWER_MODE {
		RECEIVER_ON, RECEIVER_PERIODICAL, RECEIVER_USER, UNKNOWN
	}

	enum POWER_LEVEL {
		CRITICAL, LOW, GOOD, FULL, UNKNOWN
	}

	enum POWER_SOURCE {
		MAINS, BATTERY_RECHARCHABLE, BATTERY_DISPOSABLE, UNKNOWN
	}

	private final EnumMap<POWER_MODE, Integer> modeIndexes = new EnumMap<POWER_MODE, Integer>(
			POWER_MODE.class);
	private final EnumMap<POWER_SOURCE, Integer> sourceIndexes = new EnumMap<POWER_SOURCE, Integer>(
			POWER_SOURCE.class);
	private final EnumMap<POWER_LEVEL, Integer> levelIndexes = new EnumMap<POWER_LEVEL, Integer>(
			POWER_LEVEL.class);

	ZigBeeNodePowerDescriptor() {
		modeIndexes.put(POWER_MODE.RECEIVER_ON, 0);
		modeIndexes.put(POWER_MODE.RECEIVER_PERIODICAL, 1);
		modeIndexes.put(POWER_MODE.RECEIVER_USER, 2);

		sourceIndexes.put(POWER_SOURCE.MAINS, 1);
		sourceIndexes.put(POWER_SOURCE.BATTERY_RECHARCHABLE, 2);
		sourceIndexes.put(POWER_SOURCE.BATTERY_DISPOSABLE, 4);
		sourceIndexes.put(POWER_SOURCE.UNKNOWN, 8);

		levelIndexes.put(POWER_LEVEL.CRITICAL, 0);
		levelIndexes.put(POWER_LEVEL.LOW, 4);
		levelIndexes.put(POWER_LEVEL.GOOD, 8);
		levelIndexes.put(POWER_LEVEL.FULL, 12);
	}

	public ZigBeeNodePowerDescriptor(ZDO_POWER_DESC_RSP descriptorResponse) {
		this();

		powerMode = POWER_MODE.UNKNOWN;
		for (Entry<POWER_MODE, Integer> entry : modeIndexes.entrySet()) {
			if (entry.getValue() == descriptorResponse.CurrentMode) {
				powerMode = entry.getKey();
			}
		}

		powerSource = POWER_SOURCE.UNKNOWN;
		for (Entry<POWER_SOURCE, Integer> entry : sourceIndexes.entrySet()) {
			if (entry.getValue() == descriptorResponse.CurrentSource) {
				powerSource = entry.getKey();
			}
		}

		powerSourcesAvailable = new ArrayList<POWER_SOURCE>();
		for (Entry<POWER_SOURCE, Integer> entry : sourceIndexes.entrySet()) {
			if ((entry.getValue() & descriptorResponse.AvailableSources) == 1) {
				powerSourcesAvailable.add(entry.getKey());
			}
		}

		powerLevel = POWER_LEVEL.UNKNOWN;
		for (Entry<POWER_LEVEL, Integer> entry : levelIndexes.entrySet()) {
			if (entry.getValue() == descriptorResponse.CurrentLevel) {
				powerLevel = entry.getKey();
			}
		}
	}

	/*
	 * The current power mode field of the node power descriptor is four bits in
	 * length and specifies the current sleep/power-saving mode of the node.
	 */
	public POWER_MODE getPowerMode() {
		return powerMode;
	}

	/*
	 * The available power sources field of the node power descriptor is four
	 * bits in length and specifies the power sources available on this node.
	 * For each power source supported on this node, the corresponding bit of
	 * the available power sources field, shall be set to 1. All other bits
	 * shall be set to 0.
	 */
	public List<POWER_SOURCE> getPowerSourcesAvailable() {
		return powerSourcesAvailable;
	}

	/*
	 * The current power source field of the node power descriptor is four bits
	 * in length and specifies the current power source being utilized by the
	 * node. For the current power source selected, the corresponding bit of the
	 * current power source field, shall be set to 1. All other bits shall be
	 * set to 0.
	 */
	public POWER_SOURCE getPowerSource() {
		return powerSource;
	}

	/*
	 * The current power source level field of the node power descriptor is four
	 * bits in length and specifies the level of charge of the power source. The
	 * current power source level field shall be set to one of the non-reserved
	 * values.
	 */
	public POWER_LEVEL getPowerLevel() {
		return powerLevel;
	}
}
