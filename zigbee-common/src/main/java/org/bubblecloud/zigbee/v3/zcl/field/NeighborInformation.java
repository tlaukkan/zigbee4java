package org.bubblecloud.zigbee.v3.zcl.field;

import org.bubblecloud.zigbee.v3.serialization.ZBDeserializer;
import org.bubblecloud.zigbee.v3.serialization.ZBSerializer;
import org.bubblecloud.zigbee.v3.model.ZigBeeType;
import org.bubblecloud.zigbee.v3.zcl.ZclListItemField;

/**
 * Attribute Identifier field.
 */
public class NeighborInformation implements ZclListItemField {
    /**
     * The neighbor address.
     */
    private long neighborAddress;
    /**
     * The coordinate 1
     */
    private int coordinate1;
    /**
     * The coordinate 2
     */
    private int coordinate2;
    /**
     * The coordinate 3
     */
    private int coordinate3;
    /**
     * The RSSI.
     */
    private int rssi;
    /**
     * The RSSI measurement count.
     */
    private int measurementCount;

    /**
     * Gets coordinate 1.
     * @return the coordinate 1.
     */
    public int getCoordinate1() {
        return coordinate1;
    }

    /**
     * Sets coordinate 1
     * @param coordinate1 the coordinate 1
     */
    public void setCoordinate1(int coordinate1) {
        this.coordinate1 = coordinate1;
    }

    /**
     * Gets coordinate 2.
     * @return the coordinate 2
     */
    public int getCoordinate2() {
        return coordinate2;
    }

    /**
     * Sets coordinate 2.
     * @param coordinate2 the coordinate 2
     */
    public void setCoordinate2(int coordinate2) {
        this.coordinate2 = coordinate2;
    }

    /**
     * Gets coordinate 3.
     * @return the coordinate 3
     */
    public int getCoordinate3() {
        return coordinate3;
    }

    /**
     * Sets coordinate 3.
     * @param coordinate3 the coordinate 3
     */
    public void setCoordinate3(int coordinate3) {
        this.coordinate3 = coordinate3;
    }

    /**
     * Gets measurement count.
     * @return the measurement count
     */
    public int getMeasurementCount() {
        return measurementCount;
    }

    /**
     * Sets measurement count
     * @param measurementCount the measurement count
     */
    public void setMeasurementCount(int measurementCount) {
        this.measurementCount = measurementCount;
    }

    /**
     * Gets neighbor address.
     * @return the neighbor address
     */
    public long getNeighborAddress() {
        return neighborAddress;
    }

    /**
     * Sets neighbor address.
     * @param neighborAddress the neighbor address.
     */
    public void setNeighborAddress(long neighborAddress) {
        this.neighborAddress = neighborAddress;
    }

    /**
     * Gets RSSI.
     * @return the RSSI
     */
    public int getRssi() {
        return rssi;
    }

    /**
     * Sets RSSI.
     * @param rssi the RSSI
     */
    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public void serialize(final ZBSerializer serializer) {
        serializer.appendZigBeeType(neighborAddress, ZigBeeType.IEEEAddress);
        serializer.append_short((short) coordinate1);
        serializer.append_short((short) coordinate2);
        serializer.append_short((short) coordinate3);
        serializer.append_byte((byte) rssi);
        serializer.append_byte((byte) measurementCount);
    }

    @Override
    public void deserialize(final ZBDeserializer deserializer) {
        neighborAddress = (Long) deserializer.readZigBeeType(ZigBeeType.IEEEAddress);
        coordinate1 = deserializer.read_short();
        coordinate2 = deserializer.read_short();
        coordinate3 = deserializer.read_short();
        rssi = deserializer.read_byte();
        measurementCount = deserializer.read_uint8bit();
    }

    @Override
    public String toString() {
        return "Neighbor Information " +
                "coordinate1=" + coordinate1 +
                ", neighborAddress=" + neighborAddress +
                ", coordinate2=" + coordinate2 +
                ", coordinate3=" + coordinate3 +
                ", rssi=" + rssi +
                ", measurementCount=" + measurementCount;
    }
}
