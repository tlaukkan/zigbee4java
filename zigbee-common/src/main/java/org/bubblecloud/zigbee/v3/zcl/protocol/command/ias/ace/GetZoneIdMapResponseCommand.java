package org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.v3.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclFieldType;


/**
 * Code generated Get Zone ID Map Response Command value object class.
 */
public class GetZoneIdMapResponseCommand extends ZclCommand {
    /**
     * Zone ID Map section 0 command message field.
     */
    private Integer zoneIdMapSection0;
    /**
     * Zone ID Map section 1 command message field.
     */
    private Integer zoneIdMapSection1;
    /**
     * Zone ID Map section 2 command message field.
     */
    private Integer zoneIdMapSection2;
    /**
     * Zone ID Map section 3 command message field.
     */
    private Integer zoneIdMapSection3;
    /**
     * Zone ID Map section 4 command message field.
     */
    private Integer zoneIdMapSection4;
    /**
     * Zone ID Map section 5 command message field.
     */
    private Integer zoneIdMapSection5;
    /**
     * Zone ID Map section 6 command message field.
     */
    private Integer zoneIdMapSection6;
    /**
     * Zone ID Map section 7 command message field.
     */
    private Integer zoneIdMapSection7;
    /**
     * Zone ID Map section 8 command message field.
     */
    private Integer zoneIdMapSection8;
    /**
     * Zone ID Map section 9 command message field.
     */
    private Integer zoneIdMapSection9;
    /**
     * Zone ID Map section 10 command message field.
     */
    private Integer zoneIdMapSection10;
    /**
     * Zone ID Map section 11 command message field.
     */
    private Integer zoneIdMapSection11;
    /**
     * Zone ID Map section 12 command message field.
     */
    private Integer zoneIdMapSection12;
    /**
     * Zone ID Map section 13 command message field.
     */
    private Integer zoneIdMapSection13;
    /**
     * Zone ID Map section 14 command message field.
     */
    private Integer zoneIdMapSection14;
    /**
     * Zone ID Map section 15 command message field.
     */
    private Integer zoneIdMapSection15;

    /**
     * Default constructor setting the command type field.
     */
    public GetZoneIdMapResponseCommand() {
        this.setType(ZclCommandType.GET_ZONE_ID_MAP_RESPONSE_COMMAND);
    }

    /**
     * Constructor copying field values from command message.
     * @param message the command message
     */
    public GetZoneIdMapResponseCommand(final ZclCommandMessage message) {
        super(message);
        this.zoneIdMapSection0 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_0);
        this.zoneIdMapSection1 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_1);
        this.zoneIdMapSection2 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_2);
        this.zoneIdMapSection3 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_3);
        this.zoneIdMapSection4 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_4);
        this.zoneIdMapSection5 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_5);
        this.zoneIdMapSection6 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_6);
        this.zoneIdMapSection7 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_7);
        this.zoneIdMapSection8 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_8);
        this.zoneIdMapSection9 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_9);
        this.zoneIdMapSection10 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_10);
        this.zoneIdMapSection11 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_11);
        this.zoneIdMapSection12 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_12);
        this.zoneIdMapSection13 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_13);
        this.zoneIdMapSection14 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_14);
        this.zoneIdMapSection15 = (Integer) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_15);
    }

    @Override
    public ZclCommandMessage toCommandMessage() {
        final ZclCommandMessage message = super.toCommandMessage();
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_0,zoneIdMapSection0);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_1,zoneIdMapSection1);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_2,zoneIdMapSection2);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_3,zoneIdMapSection3);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_4,zoneIdMapSection4);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_5,zoneIdMapSection5);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_6,zoneIdMapSection6);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_7,zoneIdMapSection7);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_8,zoneIdMapSection8);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_9,zoneIdMapSection9);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_10,zoneIdMapSection10);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_11,zoneIdMapSection11);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_12,zoneIdMapSection12);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_13,zoneIdMapSection13);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_14,zoneIdMapSection14);
        message.getFields().put(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_15,zoneIdMapSection15);
        return message;
    }

    /**
     * Gets Zone ID Map section 0.
     * @return the Zone ID Map section 0
     */
    public Integer getZoneIdMapSection0() {
        return zoneIdMapSection0;
    }

    /**
     * Sets Zone ID Map section 0.
     * @param zoneIdMapSection0 the Zone ID Map section 0
     */
    public void setZoneIdMapSection0(final Integer zoneIdMapSection0) {
        this.zoneIdMapSection0 = zoneIdMapSection0;
    }

    /**
     * Gets Zone ID Map section 1.
     * @return the Zone ID Map section 1
     */
    public Integer getZoneIdMapSection1() {
        return zoneIdMapSection1;
    }

    /**
     * Sets Zone ID Map section 1.
     * @param zoneIdMapSection1 the Zone ID Map section 1
     */
    public void setZoneIdMapSection1(final Integer zoneIdMapSection1) {
        this.zoneIdMapSection1 = zoneIdMapSection1;
    }

    /**
     * Gets Zone ID Map section 2.
     * @return the Zone ID Map section 2
     */
    public Integer getZoneIdMapSection2() {
        return zoneIdMapSection2;
    }

    /**
     * Sets Zone ID Map section 2.
     * @param zoneIdMapSection2 the Zone ID Map section 2
     */
    public void setZoneIdMapSection2(final Integer zoneIdMapSection2) {
        this.zoneIdMapSection2 = zoneIdMapSection2;
    }

    /**
     * Gets Zone ID Map section 3.
     * @return the Zone ID Map section 3
     */
    public Integer getZoneIdMapSection3() {
        return zoneIdMapSection3;
    }

    /**
     * Sets Zone ID Map section 3.
     * @param zoneIdMapSection3 the Zone ID Map section 3
     */
    public void setZoneIdMapSection3(final Integer zoneIdMapSection3) {
        this.zoneIdMapSection3 = zoneIdMapSection3;
    }

    /**
     * Gets Zone ID Map section 4.
     * @return the Zone ID Map section 4
     */
    public Integer getZoneIdMapSection4() {
        return zoneIdMapSection4;
    }

    /**
     * Sets Zone ID Map section 4.
     * @param zoneIdMapSection4 the Zone ID Map section 4
     */
    public void setZoneIdMapSection4(final Integer zoneIdMapSection4) {
        this.zoneIdMapSection4 = zoneIdMapSection4;
    }

    /**
     * Gets Zone ID Map section 5.
     * @return the Zone ID Map section 5
     */
    public Integer getZoneIdMapSection5() {
        return zoneIdMapSection5;
    }

    /**
     * Sets Zone ID Map section 5.
     * @param zoneIdMapSection5 the Zone ID Map section 5
     */
    public void setZoneIdMapSection5(final Integer zoneIdMapSection5) {
        this.zoneIdMapSection5 = zoneIdMapSection5;
    }

    /**
     * Gets Zone ID Map section 6.
     * @return the Zone ID Map section 6
     */
    public Integer getZoneIdMapSection6() {
        return zoneIdMapSection6;
    }

    /**
     * Sets Zone ID Map section 6.
     * @param zoneIdMapSection6 the Zone ID Map section 6
     */
    public void setZoneIdMapSection6(final Integer zoneIdMapSection6) {
        this.zoneIdMapSection6 = zoneIdMapSection6;
    }

    /**
     * Gets Zone ID Map section 7.
     * @return the Zone ID Map section 7
     */
    public Integer getZoneIdMapSection7() {
        return zoneIdMapSection7;
    }

    /**
     * Sets Zone ID Map section 7.
     * @param zoneIdMapSection7 the Zone ID Map section 7
     */
    public void setZoneIdMapSection7(final Integer zoneIdMapSection7) {
        this.zoneIdMapSection7 = zoneIdMapSection7;
    }

    /**
     * Gets Zone ID Map section 8.
     * @return the Zone ID Map section 8
     */
    public Integer getZoneIdMapSection8() {
        return zoneIdMapSection8;
    }

    /**
     * Sets Zone ID Map section 8.
     * @param zoneIdMapSection8 the Zone ID Map section 8
     */
    public void setZoneIdMapSection8(final Integer zoneIdMapSection8) {
        this.zoneIdMapSection8 = zoneIdMapSection8;
    }

    /**
     * Gets Zone ID Map section 9.
     * @return the Zone ID Map section 9
     */
    public Integer getZoneIdMapSection9() {
        return zoneIdMapSection9;
    }

    /**
     * Sets Zone ID Map section 9.
     * @param zoneIdMapSection9 the Zone ID Map section 9
     */
    public void setZoneIdMapSection9(final Integer zoneIdMapSection9) {
        this.zoneIdMapSection9 = zoneIdMapSection9;
    }

    /**
     * Gets Zone ID Map section 10.
     * @return the Zone ID Map section 10
     */
    public Integer getZoneIdMapSection10() {
        return zoneIdMapSection10;
    }

    /**
     * Sets Zone ID Map section 10.
     * @param zoneIdMapSection10 the Zone ID Map section 10
     */
    public void setZoneIdMapSection10(final Integer zoneIdMapSection10) {
        this.zoneIdMapSection10 = zoneIdMapSection10;
    }

    /**
     * Gets Zone ID Map section 11.
     * @return the Zone ID Map section 11
     */
    public Integer getZoneIdMapSection11() {
        return zoneIdMapSection11;
    }

    /**
     * Sets Zone ID Map section 11.
     * @param zoneIdMapSection11 the Zone ID Map section 11
     */
    public void setZoneIdMapSection11(final Integer zoneIdMapSection11) {
        this.zoneIdMapSection11 = zoneIdMapSection11;
    }

    /**
     * Gets Zone ID Map section 12.
     * @return the Zone ID Map section 12
     */
    public Integer getZoneIdMapSection12() {
        return zoneIdMapSection12;
    }

    /**
     * Sets Zone ID Map section 12.
     * @param zoneIdMapSection12 the Zone ID Map section 12
     */
    public void setZoneIdMapSection12(final Integer zoneIdMapSection12) {
        this.zoneIdMapSection12 = zoneIdMapSection12;
    }

    /**
     * Gets Zone ID Map section 13.
     * @return the Zone ID Map section 13
     */
    public Integer getZoneIdMapSection13() {
        return zoneIdMapSection13;
    }

    /**
     * Sets Zone ID Map section 13.
     * @param zoneIdMapSection13 the Zone ID Map section 13
     */
    public void setZoneIdMapSection13(final Integer zoneIdMapSection13) {
        this.zoneIdMapSection13 = zoneIdMapSection13;
    }

    /**
     * Gets Zone ID Map section 14.
     * @return the Zone ID Map section 14
     */
    public Integer getZoneIdMapSection14() {
        return zoneIdMapSection14;
    }

    /**
     * Sets Zone ID Map section 14.
     * @param zoneIdMapSection14 the Zone ID Map section 14
     */
    public void setZoneIdMapSection14(final Integer zoneIdMapSection14) {
        this.zoneIdMapSection14 = zoneIdMapSection14;
    }

    /**
     * Gets Zone ID Map section 15.
     * @return the Zone ID Map section 15
     */
    public Integer getZoneIdMapSection15() {
        return zoneIdMapSection15;
    }

    /**
     * Sets Zone ID Map section 15.
     * @param zoneIdMapSection15 the Zone ID Map section 15
     */
    public void setZoneIdMapSection15(final Integer zoneIdMapSection15) {
        this.zoneIdMapSection15 = zoneIdMapSection15;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", ");
        builder.append("zoneIdMapSection0");
        builder.append('=');
        builder.append(zoneIdMapSection0);
        builder.append(", ");
        builder.append("zoneIdMapSection1");
        builder.append('=');
        builder.append(zoneIdMapSection1);
        builder.append(", ");
        builder.append("zoneIdMapSection2");
        builder.append('=');
        builder.append(zoneIdMapSection2);
        builder.append(", ");
        builder.append("zoneIdMapSection3");
        builder.append('=');
        builder.append(zoneIdMapSection3);
        builder.append(", ");
        builder.append("zoneIdMapSection4");
        builder.append('=');
        builder.append(zoneIdMapSection4);
        builder.append(", ");
        builder.append("zoneIdMapSection5");
        builder.append('=');
        builder.append(zoneIdMapSection5);
        builder.append(", ");
        builder.append("zoneIdMapSection6");
        builder.append('=');
        builder.append(zoneIdMapSection6);
        builder.append(", ");
        builder.append("zoneIdMapSection7");
        builder.append('=');
        builder.append(zoneIdMapSection7);
        builder.append(", ");
        builder.append("zoneIdMapSection8");
        builder.append('=');
        builder.append(zoneIdMapSection8);
        builder.append(", ");
        builder.append("zoneIdMapSection9");
        builder.append('=');
        builder.append(zoneIdMapSection9);
        builder.append(", ");
        builder.append("zoneIdMapSection10");
        builder.append('=');
        builder.append(zoneIdMapSection10);
        builder.append(", ");
        builder.append("zoneIdMapSection11");
        builder.append('=');
        builder.append(zoneIdMapSection11);
        builder.append(", ");
        builder.append("zoneIdMapSection12");
        builder.append('=');
        builder.append(zoneIdMapSection12);
        builder.append(", ");
        builder.append("zoneIdMapSection13");
        builder.append('=');
        builder.append(zoneIdMapSection13);
        builder.append(", ");
        builder.append("zoneIdMapSection14");
        builder.append('=');
        builder.append(zoneIdMapSection14);
        builder.append(", ");
        builder.append("zoneIdMapSection15");
        builder.append('=');
        builder.append(zoneIdMapSection15);
        return builder.toString();
    }

}
