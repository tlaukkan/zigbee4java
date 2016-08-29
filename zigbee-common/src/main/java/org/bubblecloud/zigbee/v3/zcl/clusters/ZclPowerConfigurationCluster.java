package org.bubblecloud.zigbee.v3.zcl.clusters;

import java.util.concurrent.Future;
import org.bubblecloud.zigbee.v3.CommandResult;
import org.bubblecloud.zigbee.v3.ZigBeeApi;
import org.bubblecloud.zigbee.v3.ZigBeeDevice;
import org.bubblecloud.zigbee.v3.zcl.ZclCluster;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclDataType;

/**
 * <b>Power configuration</b> cluster implementation (<i>Cluster ID 0x0001</i>).
 * <p>
 * Attributes for determining detailed information about a device’s power source(s),
 * and for configuring under/over voltage alarms.
 * </p>
 * This code is autogenerated. Modifications may be overwritten!
 */
public class ZclPowerConfigurationCluster extends ZclCluster {
    // Cluster ID
    private static final int CLUSTER_ID = 0x0001;

    // Attribute constants
    private final int ATTR_MAINSVOLTAGE = 0x0000;
    private final int ATTR_MAINSFREQUENCY = 0x0001;
    private final int ATTR_MAINSALARMMASK = 0x0010;
    private final int ATTR_MAINSVOLTAGEMINTHRESHOLD = 0x0011;
    private final int ATTR_MAINSVOLTAGEMAXTHRESHOLD = 0x0012;
    private final int ATTR_MAINSVOLTAGEDWELLTRIPPOINT = 0x0013;
    private final int ATTR_BATTERYVOLTAGE = 0x0020;
    private final int ATTR_BATTERYMANUFACTURER = 0x0030;
    private final int ATTR_BATTERYSIZE = 0x0031;
    private final int ATTR_BATTERYAHRRATING = 0x0032;
    private final int ATTR_BATTERYQUANTITY = 0x0033;
    private final int ATTR_BATTERYRATEDVOLTAGE = 0x0034;
    private final int ATTR_BATTERYALARMMASK = 0x0035;
    private final int ATTR_BATTERYVOLTAGEMINTHRESHOLD = 0x0036;

    /**
     * Default constructor.
     */
    public ZclPowerConfigurationCluster(final ZigBeeApi zigbeeApi, final ZigBeeDevice zigbeeDevice) {
        super(zigbeeApi, zigbeeDevice, CLUSTER_ID);
    }



    /**
     * Get the <i>MainsVoltage</i> attribute
     * <p>
     * The MainsVoltage attribute is 16-bits in length and specifies the actual (measured)
     * RMS voltage (or DC voltage in the case of a DC supply) currently applied to the
     * device, measured in units of 100mV.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getMainsVoltage() {
        return read(ATTR_MAINSVOLTAGE);
    }


    /**
     * Get the <i>MainsFrequency</i> attribute
     * <p>
     * <br>
     * The MainsFrequency attribute is 8-bits in length and represents the frequency, in
     * Hertz, of the mains as determined by the device as follows:-
     * <br>
     * MainsFrequency = 0.5 x measured frequency
     * <br>
     * Where 2 Hz <= measured frequency <= 506 Hz, corresponding to a
     * <br>
     * MainsFrequency in the range 1 to 0xfd.
     * <br>
     * The maximum resolution this format allows is 2 Hz.
     * The following special values of MainsFrequency apply.
     * <li>0x00 indicates a frequency that is too low to be measured.</li>
     * <li>0xfe indicates a frequency that is too high to be measured.</li>
     * <li>0xff indicates that the frequency could not be measured.</li>
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getMainsFrequency() {
        return read(ATTR_MAINSFREQUENCY);
    }


    /**
     * Set the <i>MainsAlarmMask</i> attribute
     * <p>
     * <br>
     * The MainsAlarmMask attribute is 8-bits in length and specifies which mains
     * alarms may be generated. A ‘1’ in each bit position enables the alarm.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param mainsAlarmMask the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setMainsAlarmMask(final Object value) {
        return write(ATTR_MAINSALARMMASK, ZclDataType.BITMAP_8_BIT, value);
    }


    /**
     * Get the <i>MainsAlarmMask</i> attribute
     * <p>
     * <br>
     * The MainsAlarmMask attribute is 8-bits in length and specifies which mains
     * alarms may be generated. A ‘1’ in each bit position enables the alarm.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getMainsAlarmMask() {
        return read(ATTR_MAINSALARMMASK);
    }


    /**
     * Set the <i>MainsVoltageMinThreshold</i> attribute
     * <p>
     * <br>
     * The MainsVoltageMinThreshold attribute is 16-bits in length and specifies the
     * lower alarm threshold, measured in units of 100mV, for the MainsVoltage
     * attribute. The value of this attribute shall be less than MainsVoltageMaxThreshold.
     * <br>
     * If the value of MainsVoltage drops below the threshold specified by
     * MainsVoltageMinThreshold, the device shall start a timer to expire after
     * MainsVoltageDwellTripPoint seconds. If the value of this attribute increases to
     * greater than or equal to MainsVoltageMinThreshold before the timer expires, the
     * device shall stop and reset the timer. If the timer expires, an alarm shall be
     * generated.
     * <br>
     * The Alarm Code field (see 3.11.2.3.1) included in the generated alarm shall be
     * 0x00.
     * <br>
     * If this attribute takes the value 0xffff then this alarm shall not be generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param mainsVoltageMinThreshold the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setMainsVoltageMinThreshold(final Object value) {
        return write(ATTR_MAINSVOLTAGEMINTHRESHOLD, ZclDataType.UNSIGNED_16_BIT_INTEGER, value);
    }


    /**
     * Get the <i>MainsVoltageMinThreshold</i> attribute
     * <p>
     * <br>
     * The MainsVoltageMinThreshold attribute is 16-bits in length and specifies the
     * lower alarm threshold, measured in units of 100mV, for the MainsVoltage
     * attribute. The value of this attribute shall be less than MainsVoltageMaxThreshold.
     * <br>
     * If the value of MainsVoltage drops below the threshold specified by
     * MainsVoltageMinThreshold, the device shall start a timer to expire after
     * MainsVoltageDwellTripPoint seconds. If the value of this attribute increases to
     * greater than or equal to MainsVoltageMinThreshold before the timer expires, the
     * device shall stop and reset the timer. If the timer expires, an alarm shall be
     * generated.
     * <br>
     * The Alarm Code field (see 3.11.2.3.1) included in the generated alarm shall be
     * 0x00.
     * <br>
     * If this attribute takes the value 0xffff then this alarm shall not be generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getMainsVoltageMinThreshold() {
        return read(ATTR_MAINSVOLTAGEMINTHRESHOLD);
    }


    /**
     * Set the <i>MainsVoltageMaxThreshold</i> attribute
     * <p>
     * <br>
     * The MainsVoltageMaxThreshold attribute is 16-bits in length and specifies the
     * upper alarm threshold, measured in units of 100mV, for the MainsVoltage
     * attribute. The value of this attribute shall be greater than
     * MainsVoltageMinThreshold.
     * <br>
     * If the value of MainsVoltage rises above the threshold specified by
     * MainsVoltageMaxThreshold, the device shall start a timer to expire after
     * MainsVoltageDwellTripPoint seconds. If the value of this attribute drops to lower
     * than or equal to MainsVoltageMaxThreshold before the timer expires, the device
     * shall stop and reset the timer. If the timer expires, an alarm shall be generated.
     * <br>
     * The Alarm Code field (see 3.11.2.3.1) included in the generated alarm shall be
     * 0x01.
     * <br>
     * If this attribute takes the value 0xffff then this alarm shall not be generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param mainsVoltageMaxThreshold the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setMainsVoltageMaxThreshold(final Object value) {
        return write(ATTR_MAINSVOLTAGEMAXTHRESHOLD, ZclDataType.UNSIGNED_16_BIT_INTEGER, value);
    }


    /**
     * Get the <i>MainsVoltageMaxThreshold</i> attribute
     * <p>
     * <br>
     * The MainsVoltageMaxThreshold attribute is 16-bits in length and specifies the
     * upper alarm threshold, measured in units of 100mV, for the MainsVoltage
     * attribute. The value of this attribute shall be greater than
     * MainsVoltageMinThreshold.
     * <br>
     * If the value of MainsVoltage rises above the threshold specified by
     * MainsVoltageMaxThreshold, the device shall start a timer to expire after
     * MainsVoltageDwellTripPoint seconds. If the value of this attribute drops to lower
     * than or equal to MainsVoltageMaxThreshold before the timer expires, the device
     * shall stop and reset the timer. If the timer expires, an alarm shall be generated.
     * <br>
     * The Alarm Code field (see 3.11.2.3.1) included in the generated alarm shall be
     * 0x01.
     * <br>
     * If this attribute takes the value 0xffff then this alarm shall not be generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getMainsVoltageMaxThreshold() {
        return read(ATTR_MAINSVOLTAGEMAXTHRESHOLD);
    }


    /**
     * Set the <i>MainsVoltageDwellTripPoint</i> attribute
     * <p>
     * <br>
     * The MainsVoltageDwellTripPoint attribute is 16-bits in length and specifies the
     * length of time, in seconds that the value of MainsVoltage may exist beyond either
     * of its thresholds before an alarm is generated.
     * <br>
     * If this attribute takes the value 0xffff then the associated alarms shall not be
     * generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param mainsVoltageDwellTripPoint the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setMainsVoltageDwellTripPoint(final Object value) {
        return write(ATTR_MAINSVOLTAGEDWELLTRIPPOINT, ZclDataType.UNSIGNED_16_BIT_INTEGER, value);
    }


    /**
     * Get the <i>MainsVoltageDwellTripPoint</i> attribute
     * <p>
     * <br>
     * The MainsVoltageDwellTripPoint attribute is 16-bits in length and specifies the
     * length of time, in seconds that the value of MainsVoltage may exist beyond either
     * of its thresholds before an alarm is generated.
     * <br>
     * If this attribute takes the value 0xffff then the associated alarms shall not be
     * generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getMainsVoltageDwellTripPoint() {
        return read(ATTR_MAINSVOLTAGEDWELLTRIPPOINT);
    }


    /**
     * Get the <i>BatteryVoltage</i> attribute
     * <p>
     * <br>
     * The BatteryVoltage attribute is 8-bits in length and specifies the current actual
     * (measured) battery voltage, in units of 100mV.
     * The value 0xff indicates an invalid or unknown reading.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getBatteryVoltage() {
        return read(ATTR_BATTERYVOLTAGE);
    }


    /**
     * Set the <i>BatteryManufacturer</i> attribute
     * <p>
     * <br>
     * The BatteryManufacturer attribute is a maximum of 16 bytes in length and
     * specifies the name of the battery manufacturer as a ZigBee character string.
     * </p>
     * The attribute is of type {@link String}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param batteryManufacturer the {@link String} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setBatteryManufacturer(final Object value) {
        return write(ATTR_BATTERYMANUFACTURER, ZclDataType.CHARACTER_STRING, value);
    }


    /**
     * Get the <i>BatteryManufacturer</i> attribute
     * <p>
     * <br>
     * The BatteryManufacturer attribute is a maximum of 16 bytes in length and
     * specifies the name of the battery manufacturer as a ZigBee character string.
     * </p>
     * The attribute is of type {@link String}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getBatteryManufacturer() {
        return read(ATTR_BATTERYMANUFACTURER);
    }


    /**
     * Set the <i>BatterySize</i> attribute
     * <p>
     * <br>
     * The BatterySize attribute is an enumeration which specifies the type of battery
     * being used by the device.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param batterySize the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setBatterySize(final Object value) {
        return write(ATTR_BATTERYSIZE, ZclDataType.ENUMERATION_8_BIT, value);
    }


    /**
     * Get the <i>BatterySize</i> attribute
     * <p>
     * <br>
     * The BatterySize attribute is an enumeration which specifies the type of battery
     * being used by the device.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getBatterySize() {
        return read(ATTR_BATTERYSIZE);
    }


    /**
     * Set the <i>BatteryAHrRating</i> attribute
     * <p>
     * <br>
     * The BatteryAHrRating attribute is 16-bits in length and specifies the Ampere-hour
     * rating of the battery, measured in units of 10mAHr.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param batteryAHrRating the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setBatteryAHrRating(final Object value) {
        return write(ATTR_BATTERYAHRRATING, ZclDataType.UNSIGNED_16_BIT_INTEGER, value);
    }


    /**
     * Get the <i>BatteryAHrRating</i> attribute
     * <p>
     * <br>
     * The BatteryAHrRating attribute is 16-bits in length and specifies the Ampere-hour
     * rating of the battery, measured in units of 10mAHr.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getBatteryAHrRating() {
        return read(ATTR_BATTERYAHRRATING);
    }


    /**
     * Set the <i>BatteryQuantity</i> attribute
     * <p>
     * <br>
     * The BatteryQuantity attribute is 8-bits in length and specifies the number of
     * battery cells used to power the device.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param batteryQuantity the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setBatteryQuantity(final Object value) {
        return write(ATTR_BATTERYQUANTITY, ZclDataType.UNSIGNED_8_BIT_INTEGER, value);
    }


    /**
     * Get the <i>BatteryQuantity</i> attribute
     * <p>
     * <br>
     * The BatteryQuantity attribute is 8-bits in length and specifies the number of
     * battery cells used to power the device.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getBatteryQuantity() {
        return read(ATTR_BATTERYQUANTITY);
    }


    /**
     * Set the <i>BatteryRatedVoltage</i> attribute
     * <p>
     * <br>
     * The BatteryRatedVoltage attribute is 8-bits in length and specifies the rated
     * voltage of the battery being used in the device, measured in units of 100mV.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param batteryRatedVoltage the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setBatteryRatedVoltage(final Object value) {
        return write(ATTR_BATTERYRATEDVOLTAGE, ZclDataType.UNSIGNED_8_BIT_INTEGER, value);
    }


    /**
     * Get the <i>BatteryRatedVoltage</i> attribute
     * <p>
     * <br>
     * The BatteryRatedVoltage attribute is 8-bits in length and specifies the rated
     * voltage of the battery being used in the device, measured in units of 100mV.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getBatteryRatedVoltage() {
        return read(ATTR_BATTERYRATEDVOLTAGE);
    }


    /**
     * Set the <i>BatteryAlarmMask</i> attribute
     * <p>
     * <br>
     * The BatteryAlarmMask attribute is 8-bits in length and specifies which battery
     * alarms may be generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param batteryAlarmMask the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setBatteryAlarmMask(final Object value) {
        return write(ATTR_BATTERYALARMMASK, ZclDataType.BITMAP_8_BIT, value);
    }


    /**
     * Get the <i>BatteryAlarmMask</i> attribute
     * <p>
     * <br>
     * The BatteryAlarmMask attribute is 8-bits in length and specifies which battery
     * alarms may be generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getBatteryAlarmMask() {
        return read(ATTR_BATTERYALARMMASK);
    }


    /**
     * Set the <i>BatteryVoltageMinThreshold</i> attribute
     * <p>
     * <br>
     * The BatteryVoltageMinThreshold attribute is 8-bits in length and specifies the low
     * voltage alarm threshold, measured in units of 100mV, for the BatteryVoltage
     * attribute.
     * <br>
     * If the value of BatteryVoltage drops below the threshold specified by
     * BatteryVoltageMinThreshold an alarm shall be generated.
     * <br>
     * The Alarm Code field included in the generated alarm shall be 0x10.
     * <br>
     * If this attribute takes the value 0xff then this alarm shall not be generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param batteryVoltageMinThreshold the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> setBatteryVoltageMinThreshold(final Object value) {
        return write(ATTR_BATTERYVOLTAGEMINTHRESHOLD, ZclDataType.UNSIGNED_8_BIT_INTEGER, value);
    }


    /**
     * Get the <i>BatteryVoltageMinThreshold</i> attribute
     * <p>
     * <br>
     * The BatteryVoltageMinThreshold attribute is 8-bits in length and specifies the low
     * voltage alarm threshold, measured in units of 100mV, for the BatteryVoltage
     * attribute.
     * <br>
     * If the value of BatteryVoltage drops below the threshold specified by
     * BatteryVoltageMinThreshold an alarm shall be generated.
     * <br>
     * The Alarm Code field included in the generated alarm shall be 0x10.
     * <br>
     * If this attribute takes the value 0xff then this alarm shall not be generated.
     * </p>
     * The attribute is of type {@link Integer}<br>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> getBatteryVoltageMinThreshold() {
        return read(ATTR_BATTERYVOLTAGEMINTHRESHOLD);
    }


    /**
     * Add a binding for this cluster to the local node
     *
     * @return the {@link Future<CommandResult>} command result future
     */
    public Future<CommandResult> bind() {
        return bind();
    }

}
