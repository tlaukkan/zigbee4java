package org.bubblecloud.zigbee.api.cluster.impl.general.color_control;

import org.bubblecloud.zigbee.api.cluster.impl.api.lighting.ColorControl;
import org.bubblecloud.zigbee.api.cluster.impl.core.AbstractCommand;
import org.bubblecloud.zigbee.api.cluster.impl.core.ByteArrayOutputStreamSerializer;
import org.bubblecloud.zigbee.util.Integers;

/**
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class MoveToColorCommand extends AbstractCommand {


    /**
     * Moves to color according to CIE 1931 Color Space.
     *
     * @param colorX x * 65536 where colorX can be in rance 0 to 65279
     * @param colorY y * 65536 where colorY can be in rance 0 to 65279

     * @param time transition time in 1/10ths of a second.
     */
    public MoveToColorCommand(int colorX, int colorY, int time) {
        super(ColorControl.MOVE_TO_COLOR_ID);
        payload = new byte[6];
        ByteArrayOutputStreamSerializer serializer = new ByteArrayOutputStreamSerializer();
        serializer.append_short((short) colorX);
        serializer.append_short((short) colorY);
        serializer.append_short((short) time);
        payload = serializer.getPayload();
    }

}
