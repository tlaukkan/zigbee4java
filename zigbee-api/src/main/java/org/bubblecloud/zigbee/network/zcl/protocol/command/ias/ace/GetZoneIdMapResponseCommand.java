package org.bubblecloud.zigbee.network.zcl.protocol.command.ias.ace;

import org.bubblecloud.zigbee.network.zcl.ZclCommandMessage;
import org.bubblecloud.zigbee.network.zcl.ZclUtil;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclCommandType;
import org.bubblecloud.zigbee.network.zcl.protocol.ZclFieldType;
import org.bubblecloud.zigbee.network.packet.ZToolAddress64;

/**
 * Code generated Get Zone ID Map Response Command value object class.
 */
public class GetZoneIdMapResponseCommand extends ZclCommand {
    /**
     * Zone ID Map section 0 command message field.
     */
    private Short zoneIdMapSection0;
    /**
     * Zone ID Map section 1 command message field.
     */
    private Short zoneIdMapSection1;
    /**
     * Zone ID Map section 2 command message field.
     */
    private Short zoneIdMapSection2;
    /**
     * Zone ID Map section 3 command message field.
     */
    private Short zoneIdMapSection3;
    /**
     * Zone ID Map section 4 command message field.
     */
    private Short zoneIdMapSection4;
    /**
     * Zone ID Map section 5 command message field.
     */
    private Short zoneIdMapSection5;
    /**
     * Zone ID Map section 6 command message field.
     */
    private Short zoneIdMapSection6;
    /**
     * Zone ID Map section 7 command message field.
     */
    private Short zoneIdMapSection7;
    /**
     * Zone ID Map section 8 command message field.
     */
    private Short zoneIdMapSection8;
    /**
     * Zone ID Map section 9 command message field.
     */
    private Short zoneIdMapSection9;
    /**
     * Zone ID Map section 10 command message field.
     */
    private Short zoneIdMapSection10;
    /**
     * Zone ID Map section 11 command message field.
     */
    private Short zoneIdMapSection11;
    /**
     * Zone ID Map section 12 command message field.
     */
    private Short zoneIdMapSection12;
    /**
     * Zone ID Map section 13 command message field.
     */
    private Short zoneIdMapSection13;
    /**
     * Zone ID Map section 14 command message field.
     */
    private Short zoneIdMapSection14;
    /**
     * Zone ID Map section 15 command message field.
     */
    private Short zoneIdMapSection15;

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
        this.zoneIdMapSection0 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_0);
        this.zoneIdMapSection1 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_1);
        this.zoneIdMapSection2 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_2);
        this.zoneIdMapSection3 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_3);
        this.zoneIdMapSection4 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_4);
        this.zoneIdMapSection5 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_5);
        this.zoneIdMapSection6 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_6);
        this.zoneIdMapSection7 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_7);
        this.zoneIdMapSection8 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_8);
        this.zoneIdMapSection9 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_9);
        this.zoneIdMapSection10 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_10);
        this.zoneIdMapSection11 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_11);
        this.zoneIdMapSection12 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_12);
        this.zoneIdMapSection13 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_13);
        this.zoneIdMapSection14 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_14);
        this.zoneIdMapSection15 = (Short) message.getFields().get(ZclFieldType.GET_ZONE_ID_MAP_RESPONSE_COMMAND_ZONE_ID_MAP_SECTION_15);
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
    public Short getZoneIdMapSection0() {
        return zoneIdMapSection0;
    }

    /**
     * Sets Zone ID Map section 0.
     * @param zoneIdMapSection0 the Zone ID Map section 0
     */
    public void setZoneIdMapSection0(final Short zoneIdMapSection0) {
        this.zoneIdMapSection0 = zoneIdMapSection0;
    }

    /**
     * Gets Zone ID Map section 1.
     * @return the Zone ID Map section 1
     */
    public Short getZoneIdMapSection1() {
        return zoneIdMapSection1;
    }

    /**
     * Sets Zone ID Map section 1.
     * @param zoneIdMapSection1 the Zone ID Map section 1
     */
    public void setZoneIdMapSection1(final Short zoneIdMapSection1) {
        this.zoneIdMapSection1 = zoneIdMapSection1;
    }

    /**
     * Gets Zone ID Map section 2.
     * @return the Zone ID Map section 2
     */
    public Short getZoneIdMapSection2() {
        return zoneIdMapSection2;
    }

    /**
     * Sets Zone ID Map section 2.
     * @param zoneIdMapSection2 the Zone ID Map section 2
     */
    public void setZoneIdMapSection2(final Short zoneIdMapSection2) {
        this.zoneIdMapSection2 = zoneIdMapSection2;
    }

    /**
     * Gets Zone ID Map section 3.
     * @return the Zone ID Map section 3
     */
    public Short getZoneIdMapSection3() {
        return zoneIdMapSection3;
    }

    /**
     * Sets Zone ID Map section 3.
     * @param zoneIdMapSection3 the Zone ID Map section 3
     */
    public void setZoneIdMapSection3(final Short zoneIdMapSection3) {
        this.zoneIdMapSection3 = zoneIdMapSection3;
    }

    /**
     * Gets Zone ID Map section 4.
     * @return the Zone ID Map section 4
     */
    public Short getZoneIdMapSection4() {
        return zoneIdMapSection4;
    }

    /**
     * Sets Zone ID Map section 4.
     * @param zoneIdMapSection4 the Zone ID Map section 4
     */
    public void setZoneIdMapSection4(final Short zoneIdMapSection4) {
        this.zoneIdMapSection4 = zoneIdMapSection4;
    }

    /**
     * Gets Zone ID Map section 5.
     * @return the Zone ID Map section 5
     */
    public Short getZoneIdMapSection5() {
        return zoneIdMapSection5;
    }

    /**
     * Sets Zone ID Map section 5.
     * @param zoneIdMapSection5 the Zone ID Map section 5
     */
    public void setZoneIdMapSection5(final Short zoneIdMapSection5) {
        this.zoneIdMapSection5 = zoneIdMapSection5;
    }

    /**
     * Gets Zone ID Map section 6.
     * @return the Zone ID Map section 6
     */
    public Short getZoneIdMapSection6() {
        return zoneIdMapSection6;
    }

    /**
     * Sets Zone ID Map section 6.
     * @param zoneIdMapSection6 the Zone ID Map section 6
     */
    public void setZoneIdMapSection6(final Short zoneIdMapSection6) {
        this.zoneIdMapSection6 = zoneIdMapSection6;
    }

    /**
     * Gets Zone ID Map section 7.
     * @return the Zone ID Map section 7
     */
    public Short getZoneIdMapSection7() {
        return zoneIdMapSection7;
    }

    /**
     * Sets Zone ID Map section 7.
     * @param zoneIdMapSection7 the Zone ID Map section 7
     */
    public void setZoneIdMapSection7(final Short zoneIdMapSection7) {
        this.zoneIdMapSection7 = zoneIdMapSection7;
    }

    /**
     * Gets Zone ID Map section 8.
     * @return the Zone ID Map section 8
     */
    public Short getZoneIdMapSection8() {
        return zoneIdMapSection8;
    }

    /**
     * Sets Zone ID Map section 8.
     * @param zoneIdMapSection8 the Zone ID Map section 8
     */
    public void setZoneIdMapSection8(final Short zoneIdMapSection8) {
        this.zoneIdMapSection8 = zoneIdMapSection8;
    }

    /**
     * Gets Zone ID Map section 9.
     * @return the Zone ID Map section 9
     */
    public Short getZoneIdMapSection9() {
        return zoneIdMapSection9;
    }

    /**
     * Sets Zone ID Map section 9.
     * @param zoneIdMapSection9 the Zone ID Map section 9
     */
    public void setZoneIdMapSection9(final Short zoneIdMapSection9) {
        this.zoneIdMapSection9 = zoneIdMapSection9;
    }

    /**
     * Gets Zone ID Map section 10.
     * @return the Zone ID Map section 10
     */
    public Short getZoneIdMapSection10() {
        return zoneIdMapSection10;
    }

    /**
     * Sets Zone ID Map section 10.
     * @param zoneIdMapSection10 the Zone ID Map section 10
     */
    public void setZoneIdMapSection10(final Short zoneIdMapSection10) {
        this.zoneIdMapSection10 = zoneIdMapSection10;
    }

    /**
     * Gets Zone ID Map section 11.
     * @return the Zone ID Map section 11
     */
    public Short getZoneIdMapSection11() {
        return zoneIdMapSection11;
    }

    /**
     * Sets Zone ID Map section 11.
     * @param zoneIdMapSection11 the Zone ID Map section 11
     */
    public void setZoneIdMapSection11(final Short zoneIdMapSection11) {
        this.zoneIdMapSection11 = zoneIdMapSection11;
    }

    /**
     * Gets Zone ID Map section 12.
     * @return the Zone ID Map section 12
     */
    public Short getZoneIdMapSection12() {
        return zoneIdMapSection12;
    }

    /**
     * Sets Zone ID Map section 12.
     * @param zoneIdMapSection12 the Zone ID Map section 12
     */
    public void setZoneIdMapSection12(final Short zoneIdMapSection12) {
        this.zoneIdMapSection12 = zoneIdMapSection12;
    }

    /**
     * Gets Zone ID Map section 13.
     * @return the Zone ID Map section 13
     */
    public Short getZoneIdMapSection13() {
        return zoneIdMapSection13;
    }

    /**
     * Sets Zone ID Map section 13.
     * @param zoneIdMapSection13 the Zone ID Map section 13
     */
    public void setZoneIdMapSection13(final Short zoneIdMapSection13) {
        this.zoneIdMapSection13 = zoneIdMapSection13;
    }

    /**
     * Gets Zone ID Map section 14.
     * @return the Zone ID Map section 14
     */
    public Short getZoneIdMapSection14() {
        return zoneIdMapSection14;
    }

    /**
     * Sets Zone ID Map section 14.
     * @param zoneIdMapSection14 the Zone ID Map section 14
     */
    public void setZoneIdMapSection14(final Short zoneIdMapSection14) {
        this.zoneIdMapSection14 = zoneIdMapSection14;
    }

    /**
     * Gets Zone ID Map section 15.
     * @return the Zone ID Map section 15
     */
    public Short getZoneIdMapSection15() {
        return zoneIdMapSection15;
    }

    /**
     * Sets Zone ID Map section 15.
     * @param zoneIdMapSection15 the Zone ID Map section 15
     */
    public void setZoneIdMapSection15(final Short zoneIdMapSection15) {
        this.zoneIdMapSection15 = zoneIdMapSection15;
    }

    static {
        ZclUtil.registerCommandTypeClassMapping(ZclCommandType.GET_ZONE_ID_MAP_RESPONSE_COMMAND,GetZoneIdMapResponseCommand.class);
    }
}
