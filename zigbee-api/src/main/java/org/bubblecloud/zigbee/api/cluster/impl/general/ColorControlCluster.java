/*
   Copyright 2012-2012 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies
   of the Italian National Research Council


   See the NOTICE file distributed with this work for additional
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.core.AbstractCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.ByteArrayOutputStreamSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.global.DefaultResponseImpl;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Response;
import org.bubblecloud.zigbee.api.cluster.impl.api.lighting.ColorControl;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ColorControlCluster extends ZCLClusterBase implements ColorControl {

    private final AttributeImpl currentHue;
    private final AttributeImpl currentSaturation;
    private final AttributeImpl remainingTime;
    private final AttributeImpl currentX;
    private final AttributeImpl currentY;
    private final AttributeImpl driftCompensation;
    private final AttributeImpl compensationText;
    private final AttributeImpl colorTemperature;
    private final AttributeImpl colorMode;

    // defined primaries information
    private final AttributeImpl numberOfPrimaries;
    private final AttributeImpl primary1X;
    private final AttributeImpl primary1Y;
    private final AttributeImpl primary1Intensity;
    private final AttributeImpl primary2X;
    private final AttributeImpl primary2Y;
    private final AttributeImpl primary2Intensity;
    private final AttributeImpl primary3X;
    private final AttributeImpl primary3Y;
    private final AttributeImpl primary3Intensity;

    // additional defined primaries information
    private final AttributeImpl primary4X;
    private final AttributeImpl primary4Y;
    private final AttributeImpl primary4Intensity;
    private final AttributeImpl primary5X;
    private final AttributeImpl primary5Y;
    private final AttributeImpl primary5Intensity;
    private final AttributeImpl primary6X;
    private final AttributeImpl primary6Y;
    private final AttributeImpl primary6Intensity;

    // defined color point settings
    private final AttributeImpl whitePointX;
    private final AttributeImpl whitePointY;
    private final AttributeImpl colorPointRX;
    private final AttributeImpl colorPointRY;
    private final AttributeImpl colorPointRIntensity;
    private final AttributeImpl colorPointGX;
    private final AttributeImpl colorPointGY;
    private final AttributeImpl colorPointGIntensity;
    private final AttributeImpl colorPointBX;
    private final AttributeImpl colorPointBY;
    private final AttributeImpl colorPointBIntensity;

    private final Attribute[] attributes;

    public ColorControlCluster(ZigBeeEndpoint zbDevice) {

        super(zbDevice);

        currentHue = new AttributeImpl(zbDevice, this, Attributes.CURRENT_HUE);
        currentSaturation = new AttributeImpl(zbDevice, this, Attributes.CURRENT_SATURATION);
        remainingTime = new AttributeImpl(zbDevice, this, Attributes.REMAINING_TIME_COLOR_CONTROL);
        currentX = new AttributeImpl(zbDevice, this, Attributes.CURRENT_X);
        currentY = new AttributeImpl(zbDevice, this, Attributes.CURRENT_Y);
        driftCompensation = new AttributeImpl(zbDevice, this, Attributes.DRIFT_COMPENSATION);
        compensationText = new AttributeImpl(zbDevice, this, Attributes.COMPENSATION_TEXT);
        colorTemperature = new AttributeImpl(zbDevice, this, Attributes.COLOR_TEMPERATURE);
        colorMode = new AttributeImpl(zbDevice, this, Attributes.COLOR_MODE);
        numberOfPrimaries = new AttributeImpl(zbDevice, this, Attributes.NUMBER_OF_PRIMARIES);
        primary1X = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_1_X);
        primary1Y = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_1_Y);
        primary1Intensity = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_1_INTENSITY);
        primary2X = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_2_X);
        primary2Y = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_2_Y);
        primary2Intensity = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_2_INTENSITY);
        primary3X = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_3_X);
        primary3Y = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_3_Y);
        primary3Intensity = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_3_INTENSITY);
        primary4X = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_4_X);
        primary4Y = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_4_Y);
        primary4Intensity = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_4_INTENSITY);
        primary5X = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_5_X);
        primary5Y = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_5_Y);
        primary5Intensity = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_5_INTENSITY);
        primary6X = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_6_X);
        primary6Y = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_6_Y);
        primary6Intensity = new AttributeImpl(zbDevice, this, Attributes.PRIMARY_6_INTENSITY);
        whitePointX = new AttributeImpl(zbDevice, this, Attributes.WHITE_POINT_X);
        whitePointY = new AttributeImpl(zbDevice, this, Attributes.WHITE_POINT_Y);
        colorPointRX = new AttributeImpl(zbDevice, this, Attributes.COLOR_POINT_RX);
        colorPointRY = new AttributeImpl(zbDevice, this, Attributes.COLOR_POINT_RY);
        colorPointRIntensity = new AttributeImpl(zbDevice, this, Attributes.COLOR_POINT_R_INTENSITY);
        colorPointGX = new AttributeImpl(zbDevice, this, Attributes.COLOR_POINT_GX);
        colorPointGY = new AttributeImpl(zbDevice, this, Attributes.COLOR_POINT_GY);
        colorPointGIntensity = new AttributeImpl(zbDevice, this, Attributes.COLOR_POINT_G_INTENSITY);
        colorPointBX = new AttributeImpl(zbDevice, this, Attributes.COLOR_POINT_BX);
        colorPointBY = new AttributeImpl(zbDevice, this, Attributes.COLOR_POINT_BY);
        colorPointBIntensity = new AttributeImpl(zbDevice, this, Attributes.COLOR_POINT_B_INTENSITY);

        attributes = new AttributeImpl[]{currentHue, currentSaturation, remainingTime, currentX, currentY, driftCompensation, compensationText,
                colorTemperature, colorMode, numberOfPrimaries, primary1X, primary1Y, primary1Intensity, primary2X, primary2Y, primary2Intensity,
                primary3X, primary3Y, primary3Intensity, primary4X, primary4Y, primary4Intensity, primary5X, primary5Y, primary5Intensity,
                primary6X, primary6Y, primary6Intensity, whitePointX, whitePointY, colorPointRX, colorPointRY, colorPointRIntensity, colorPointGX,
                colorPointGY, colorPointGIntensity, colorPointBX, colorPointBY, colorPointBIntensity};
    }

    public Attribute getAttributeCurrentHue() {
        return currentHue;
    }

    public Attribute getAttributeCurrentSaturation() {
        return currentSaturation;
    }

    public Attribute getAttributeRemainingTime() {
        return remainingTime;
    }

    public Attribute getAttributeCurrentX() {
        return currentX;
    }

    public Attribute getAttributeCurrentY() {
        return currentY;
    }

    public Attribute getAttributeDriftCompensation() {
        return driftCompensation;
    }

    public Attribute getAttributeCompensationText() {
        return compensationText;
    }

    public Attribute getAttributeColorTemperature() {
        return colorTemperature;
    }

    public Attribute getAttributeColorMode() {
        return colorMode;
    }

    public Attribute getAttributeNumberOfPrimaries() {
        return numberOfPrimaries;
    }

    public Attribute getAttributePrimary1X() {
        return primary1X;
    }

    public Attribute getAttributePrimary1Y() {
        return primary1Y;
    }

    public Attribute getAttributePrimary1Intensity() {
        return primary1Intensity;
    }

    public Attribute getAttributePrimary2X() {
        return primary2X;
    }

    public Attribute getAttributePrimary2Y() {
        return primary2Y;
    }

    public Attribute getAttributePrimary2Intensity() {
        return primary2Intensity;
    }

    public Attribute getAttributePrimary3X() {
        return primary3X;
    }

    public Attribute getAttributePrimary3Y() {
        return primary3Y;
    }

    public Attribute getAttributePrimary3Intensity() {
        return primary3Intensity;
    }

    public Attribute getAttributePrimary4X() {
        return primary4X;
    }

    public Attribute getAttributePrimary4Y() {
        return primary4Y;
    }

    public Attribute getAttributePrimary4Intensity() {
        return primary4Intensity;
    }

    public Attribute getAttributePrimary5X() {
        return primary5X;
    }

    public Attribute getAttributePrimary5Y() {
        return primary5Y;
    }

    public Attribute getAttributePrimary5Intensity() {
        return primary5Intensity;
    }

    public Attribute getAttributePrimary6X() {
        return primary6X;
    }

    public Attribute getAttributePrimary6Y() {
        return primary6Y;
    }

    public Attribute getAttributePrimary6Intensity() {
        return primary6Intensity;
    }

    public Attribute getAttributeWhitePointX() {
        return whitePointX;
    }

    public Attribute getAttributeWhitePointY() {
        return whitePointY;
    }

    public Attribute getAttributeColorPointRX() {
        return colorPointRX;
    }

    public Attribute getAttributeColorPointRY() {
        return colorPointRY;
    }

    public Attribute getAttributeColorPointRIntensity() {
        return colorPointRIntensity;
    }

    public Attribute getAttributeColorPointGX() {
        return colorPointGX;
    }

    public Attribute getAttributeColorPointGY() {
        return colorPointGY;
    }

    public Attribute getAttributeColorPointGIntensity() {
        return colorPointGIntensity;
    }

    public Attribute getAttributeColorPointBX() {
        return colorPointBX;
    }

    public Attribute getAttributeColorPointBY() {
        return colorPointBY;
    }

    public Attribute getAttributeColorPointBIntensity() {
        return colorPointBIntensity;
    }

    @Override
    public short getId() {
        return ColorControl.ID;
    }

    @Override
    public String getName() {
        return ColorControl.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }

    @Override
    public Response moveToHue(int hue, int direction, int transitionTime) throws ZigBeeClusterException {
        enableDefaultResponse();
        final MoveToHueCommand command = new MoveToHueCommand(hue, direction, transitionTime);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class MoveToHueCommand extends AbstractCommand {
        public MoveToHueCommand(int hue, int direction, int transitionTime) {
            super(ColorControl.MOVE_TO_HUE_ID);
            payload = new byte[4];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_byte((byte) hue);
            serializer.append_byte((byte) direction);
            serializer.append_short((short) transitionTime);
            payload = serializer.getPayload();
        }
    }

    @Override
    public Response moveHue(int moveMode, int rate) throws ZigBeeClusterException {
        enableDefaultResponse();
        final MoveHueCommand command = new MoveHueCommand(moveMode, rate);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class MoveHueCommand extends AbstractCommand {
        public MoveHueCommand(int moveMode, int rate) {
            super(ColorControl.MOVE_HUE_ID);
            payload = new byte[2];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_byte((byte) moveMode);
            serializer.append_byte((byte) rate);
            payload = serializer.getPayload();
        }
    }

    @Override
    public Response stepHue(int stepMode, int stepSize, int transtionTime) throws ZigBeeClusterException {
        enableDefaultResponse();
        final StepHueCommand command = new StepHueCommand(stepMode, stepSize, transtionTime);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class StepHueCommand extends AbstractCommand {
        public StepHueCommand(int stepMode, int stepSize, int transtionTime) {
            super(ColorControl.STEP_HUE_ID);
            payload = new byte[3];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_byte((byte) stepMode);
            serializer.append_byte((byte) stepSize);
            serializer.append_byte((byte) transtionTime);
        }
    }

    @Override
    public Response movetoSaturation(int saturation, int transitionTime) throws ZigBeeClusterException {
        enableDefaultResponse();
        final MoveToSaturationCommand command = new MoveToSaturationCommand(saturation, transitionTime);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class MoveToSaturationCommand extends AbstractCommand {
        public MoveToSaturationCommand(int saturation, int transitionTime) {
            super(ColorControl.MOVE_TO_SATURATION_ID);
            payload = new byte[3];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_byte((byte) saturation);
            serializer.append_short((short) transitionTime);
            payload = serializer.getPayload();
        }
    }

    @Override
    public Response moveSaturation(int moveMode, int rate) throws ZigBeeClusterException {
        enableDefaultResponse();
        final MoveSaturationCommand command = new MoveSaturationCommand(moveMode, rate);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class MoveSaturationCommand extends AbstractCommand {
        public MoveSaturationCommand(int moveMode, int rate) {
            super(ColorControl.MOVE_SATURATION_ID);
            payload = new byte[2];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_byte((byte) moveMode);
            serializer.append_byte((byte) rate);
            payload = serializer.getPayload();
        }
    }

    @Override
    public Response stepSaturation(int stepMode, int stepSize,
                                   int transitionTime) throws ZigBeeClusterException {
        enableDefaultResponse();
        final StepSaturationCommand command = new StepSaturationCommand(stepMode, stepSize, transitionTime);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class StepSaturationCommand extends AbstractCommand {
        public StepSaturationCommand(int stepMode, int stepSize, int transtionTime) {
            super(ColorControl.STEP_SATURATION_ID);
            payload = new byte[3];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_byte((byte) stepMode);
            serializer.append_byte((byte) stepSize);
            serializer.append_byte((byte) transtionTime);
        }
    }

    @Override
    public Response moveToHueAndSaturation(int hue, int saturation,
                                           int transitionTime) throws ZigBeeClusterException {
        enableDefaultResponse();
        final MoveToHueAndSaturationCommand command = new MoveToHueAndSaturationCommand(hue, saturation, transitionTime);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class MoveToHueAndSaturationCommand extends AbstractCommand {
        public MoveToHueAndSaturationCommand(int hue, int saturation, int transtionTime) {
            super(ColorControl.MOVE_TO_HUE_AND_SATURATION_ID);
            payload = new byte[4];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_byte((byte) hue);
            serializer.append_byte((byte) saturation);
            serializer.append_short((short) transtionTime);
        }
    }

    @Override
    public Response moveToColor(int colorX, int colorY, int transitionTime) throws ZigBeeClusterException {
        enableDefaultResponse();
        final MoveToColorCommand command = new MoveToColorCommand(colorX, colorY, transitionTime);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class MoveToColorCommand extends AbstractCommand {
        public MoveToColorCommand(int colorX, int colorY, int transitionTime) {
            super(ColorControl.MOVE_TO_COLOR_ID);
            payload = new byte[6];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_short((short) colorX);
            serializer.append_short((short) colorY);
            serializer.append_short((short) transitionTime);
            payload = serializer.getPayload();
        }
    }

    @Override
    public Response moveColor(int rateX, int rateY) throws ZigBeeClusterException {
        enableDefaultResponse();
        final MoveColorCommand command = new MoveColorCommand(rateX, rateY);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class MoveColorCommand extends AbstractCommand {
        public MoveColorCommand(int rateX, int rateY) {
            super(ColorControl.MOVE_COLOR_ID);
            payload = new byte[4];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_short((short) rateX);
            serializer.append_short((short) rateY);
            payload = serializer.getPayload();
        }
    }

    @Override
    public Response stepColor(int stepX, int stepY, int transitionTime) throws ZigBeeClusterException {
        enableDefaultResponse();
        final StepColorCommand command = new StepColorCommand(stepX, stepY, transitionTime);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class StepColorCommand extends AbstractCommand {
        public StepColorCommand(int stepX, int stepY, int transitionTime) {
            super(ColorControl.STEP_COLOR_ID);
            payload = new byte[6];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_short((short) stepX);
            serializer.append_short((short) stepY);
            serializer.append_short((short) transitionTime);
            payload = serializer.getPayload();
        }
    }

    @Override
    public Response moveToColorTemperature(int colorTemperature, int transitionTime) throws ZigBeeClusterException {
        enableDefaultResponse();
        final MoveToColorTemperatureCommand command = new MoveToColorTemperatureCommand(colorTemperature, transitionTime);
        final Response response = invoke(command);
        return new DefaultResponseImpl(response);
    }

    private class MoveToColorTemperatureCommand extends AbstractCommand {
        public MoveToColorTemperatureCommand(int colorTemperature, int transitionTime) {
            super(ColorControl.MOVE_TO_COLOR_TEMPERATURE_ID);
            payload = new byte[4];
            ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
            serializer.append_short((short) colorTemperature);
            serializer.append_short((short) transitionTime);
            payload = serializer.getPayload();
        }
    }
}