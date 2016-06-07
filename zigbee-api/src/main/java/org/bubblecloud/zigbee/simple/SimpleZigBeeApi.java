package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.zcl.ZclApi;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.ZclCommandListener;
import org.bubblecloud.zigbee.network.zcl.protocol.command.color.control.MoveToColorCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.command.on.off.OffCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.command.on.off.OnCommand;
import org.bubblecloud.zigbee.util.Cie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Simple ZigBee API. This API is experimental and under development.
 *
 * @author Tommi S.E. Laukkanen
 */
public class SimpleZigBeeApi {
    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleZigBeeApi.class);
    /**
     * The ZCL API.
     */
    private ZclApi zclApi;

    /**
     * Constructor for setting the ZCL API.
     * @param zclApi the ZCL API
     */
    public SimpleZigBeeApi(final ZclApi zclApi) {
        this.zclApi = zclApi;
    }

    /**
     * Gets ZigBee devices.
     * @return list of ZigBee devices
     */
    public List<ZigBeeDevice> getZigBeeDevices() {
        return zclApi.getZigBeeDevices();
    }

    /**
     * Switches device on.
     * @param device the device
     * @return the Future for accessing CommandResponse.
     */
    public Future<CommandResponse> on(final ZigBeeDevice device) {
        final OnCommand command = new OnCommand();
        return send(device, command);
    }

    /**
     * Switches device off.
     * @param device the device
     * @return the Future for accessing CommandResponse.
     */
    public Future<CommandResponse> off(final ZigBeeDevice device) {
        final OffCommand command = new OffCommand();
        return send(device, command);
    }

    /**
     * Colors device light.
     *
     * @param device the device
     * @param red the red component [0..1]
     * @param green the green component [0..1]
     * @param blue the blue component [0..1]
     * @param time the in seconds
     * @return the Future for accessing CommandResponse.
     */
    public Future<CommandResponse> color(final ZigBeeDevice device, final double red, final double green, final double blue, double time) {
        final MoveToColorCommand command = new MoveToColorCommand();

        final Cie cie = Cie.rgb2cie(red, green ,blue);

        int x = (int) (cie.x * 65536);
        int y = (int) (cie.y * 65536);
        if (x > 65279) {
            x = 65279;
        }
        if (y > 65279) {
            y = 65279;
        }

        command.setColorX(x);
        command.setColorY(y);
        command.setTransitionTime((int) (time * 10));

        return send(device, command);
    }

    /**
     * Sends ZCL command.
     * @param device the destination device
     * @param command the command
     * @return the Future for accessing CommandResponse.
     */
    private Future<CommandResponse> send(final ZigBeeDevice device, final ZclCommand command) {
        command.setDestinationAddress(device.getNetworkAddress());
        command.setDestinationEndpoint(device.getEndPoint());

        final FutureImpl<CommandResponse> future = new FutureImpl<CommandResponse>();

        synchronized (command) {
            zclApi.addCommandListener(new ZclCommandListener() {
                @Override
                public void commandReceived(ZclCommand receivedCommand) {
                    // Ensure that received command is not processed before command is sent and
                    // hence transaction ID for the command set.
                    synchronized (command) {
                        if (command.getTransactionId().equals(receivedCommand.getTransactionId())) {
                            synchronized (future) {
                                future.set(new CommandResponse(receivedCommand));
                                future.notify();
                                zclApi.removeCommandListener(this);
                            }
                        }
                    }
                }
            });

            try {
                int transactionId = zclApi.sendCommand(command);
                command.setTransactionId((byte) transactionId);
            } catch (ZigBeeException e) {
                throw new SimpleZigBeeApiException("Error sending " + command.getClass().getSimpleName()
                        + " to " + device.getNetworkAddress() + "/" + device.getEndPoint(), e);
            }
        }

        return future;
    }

}
