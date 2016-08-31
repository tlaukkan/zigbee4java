package org.bubblecloud.zigbee.v3.zcl;

import java.util.Calendar;

import org.bubblecloud.zigbee.v3.zcl.protocol.ZclDataType;

public class ZclAttribute {
    /**
     * The attribute identifier field is 16-bits in length and shall contain the
     * identifier of the attribute that the reporting configuration details
     * apply to.
     */
    private final int id;

    /**
     * Defines if the attribute must be implemented
     */
    private final boolean mandatory;

    /**
     * Length of the data
     */
    private int length;
    
    /**
     * Value that defines an invalid or unset value;
     */
    private int invalid;
    
    /**
     * Defines the ZigBee data type.
     */
    private final ZclDataType dataType;

    /**
     * Defines if the attribute is implemented by the device
     */
    private boolean implemented;

    /**
     * The minimum reporting interval field is 16-bits in length and shall
     * contain the minimum interval, in seconds, between issuing reports for the
     * attribute specified in the attribute identifier field. If the minimum
     * reporting interval has not been configured, this field shall contain the
     * value 0xffff.
     */
    private int minimumReportingPeriod;

    /**
     * The maximum reporting interval field is 16-bits in length and shall
     * contain the maximum interval, in seconds, between issuing reports for the
     * attribute specified in the attribute identifier field. If the maximum
     * reporting interval has not been configured, this field shall contain the
     * value 0xffff.
     */
    private int maximumReportingPeriod;

    /**
     * The reportable change field shall contain the minimum change to the
     * attribute that will result in a report being issued. For attributes with
     * 'analog' data type the field has the same data type as the attribute. If
     * the reportable change has not been configured, this field shall contain
     * the invalid value for the relevant data type
     */
    private Object reportingChange;

    /**
     * The timeout period field is 16-bits in length and shall contain the
     * maximum expected time, in seconds, between received reports for the
     * attribute specified in the attribute identifier field. If the timeout
     * period has not been configured, this field shall contain the value
     * 0xffff.
     */
    private int reportingTimeout;

    /**
     * Records the last time a report was received
     */
    private Calendar lastReportTime;

    /**
     * Records the last value received
     */
    private Object lastValue;

    /**
     * Constructor used to set the static information
     * @param id
     * @param dataType
     * @param mandatory
     */
    public ZclAttribute(int id, ZclDataType dataType, boolean mandatory, int invalid, int length) {
        this.id = id;
        this.dataType = dataType;
        this.mandatory = mandatory;
        this.invalid = invalid;
        this.length = length;
    }
    
    /**
     * Gets the attribute ID
     * 
     * @return the attribute ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns true if the implementation of this attribute in the cluster is
     * mandatory as required by the ZigBee standard.
     * <br>
     * Note that this does not necessarily mean that the attribute is actually
     * implemented in any device if it does not conform to the standard.
     * 
     * @return true if the attribute must be implemented
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Returns true if this attribute is supported by this device
     * 
     * @return true if the attribute is supported
     */
    public boolean isImplemented() {
        return implemented;
    }

    /**
     * Gets the {@link ZigBeeType} of this attribute
     * 
     * @return the {@link ZigBeeType} of this attribute
     */
    public ZclDataType getDataType() {
        return dataType;
    }

    /**
     * Gets the minimum reporting interval in seconds. <br>
     * The minimum reporting interval field is 16-bits in length and shall
     * contain the minimum interval, in seconds, between issuing reports for the
     * attribute specified in the attribute identifier field. If the minimum
     * reporting interval has not been configured, this field shall contain the
     * value 0xffff.
     *
     * @return minimum reporting period in seconds
     */
    public int getMinimumReportingPeriod() {
        return minimumReportingPeriod;
    }

    /**
     * Gets the maximum reporting interval in seconds. <br>
     * The maximum reporting interval field is 16-bits in length and shall
     * contain the maximum interval, in seconds, between issuing reports for the
     * attribute specified in the attribute identifier field. If the maximum
     * reporting interval has not been configured, this field shall contain the
     * value 0xffff.
     *
     * @return maximum reporting period in seconds
     */
    public int getMaximumReportingPeriod() {
        return maximumReportingPeriod;
    }

    /**
     * Gets the reportable change field. <br>
     * The reportable change field shall contain the minimum change to the
     * attribute that will result in a report being issued. For attributes with
     * 'analog' data type the field has the same data type as the attribute. If
     * the reportable change has not been configured, this field shall contain
     * the invalid value for the relevant data type
     * 
     * @return
     */
    public Object getReportingChange() {
        return reportingChange;
    }

    /**
     * Gets the reporting timeout in seconds. <br>
     * The timeout period field is 16-bits in length and shall contain the
     * maximum expected time, in seconds, between received reports for the
     * attribute specified in the attribute identifier field. If the timeout
     * period has not been configured, this field shall contain the value
     * 0xffff.
     * 
     * @return
     */
    public int getReportingTimeout() {
        return reportingTimeout;
    }

    /**
     * Gets the last reported value of this attribute
     * @return the last value, or null if no update has been received
     */
    public Object getLastValue() {
        return lastValue;
    }

    /**
     * Gets the last report time of this attribute
     * @return the time of the last report, or null if not reports have been received
     */
    public Calendar getLastReportTime() {
        return lastReportTime;
    }
}
