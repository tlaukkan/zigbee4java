package org.bubblecloud.zigbee.simple;

import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.command.color.control.MoveToColorCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.command.on.off.OffCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.command.on.off.OnCommand;
import org.bubblecloud.zigbee.util.Cie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * Simple ZigBee API. This API is experimental and under development.
 */
public class SimpleZigBeeApi {
    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleZigBeeApi.class);
    /**
     * The network.
     */
    private ZigBeeNetwork network;
    /**
     * The network state.
     */
    private ZigBeeNetworkState networkState;

    /**
     * Default constructor inheritance.
     */
    public SimpleZigBeeApi() {
    }

    /**
     * Constructor for setting the ZCL API.
     * @param network the ZCL API
     */
    public SimpleZigBeeApi(final ZigBeeNetwork network) {
        this.network = network;
    }

    /**
     * Sets network.
     * @param network the network
     */
    public void setNetwork(final ZigBeeNetwork network) {
        this.network = network;
    }

    /**
     * Gets the ZigBee network.
     * @return the ZigBee network
     */
    public ZigBeeNetwork getNetwork() {
        return network;
    }

    /**
     * Sets network state.
     * @param networkState the network state
     */
    public void setNetworkState(ZigBeeNetworkState networkState) {
        this.networkState = networkState;
    }

    /**
     * Gets the ZigBee network state.
     * @return the ZigBee network state
     */
    public ZigBeeNetworkState getNetworkState() {
        return networkState;
    }

    /**
     * Gets ZigBee devices.
     * @return list of ZigBee devices
     */
    public List<ZigBeeDevice> getZigBeeDevices() {
        return network.getZigBeeDevices();
    }

    /**
     * Switches device on.
     * @param device the device
     * @return the Future for accessing CommandResponse.
     */
    public Future<ZclCommandResponse> on(final ZigBeeDevice device) {
        final OnCommand command = new OnCommand();
        return send(device, command);
    }

    /**
     * Switches device off.
     * @param device the device
     * @return the Future for accessing CommandResponse.
     */
    public Future<ZclCommandResponse> off(final ZigBeeDevice device) {
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
    public Future<ZclCommandResponse> color(final ZigBeeDevice device, final double red, final double green, final double blue, double time) {
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
    private Future<ZclCommandResponse> send(final ZigBeeDevice device, final ZclCommand command) {
        command.setDestinationAddress(device.getNetworkAddress());
        command.setDestinationEndpoint(device.getEndpoint());

        final FutureImpl<ZclCommandResponse> future = new FutureImpl<ZclCommandResponse>();

        synchronized (command) {
            network.addCommandListener(new CommandListener() {
                @Override
                public void commandReceived(Command receivedCommand) {
                    // Ensure that received command is not processed before command is sent and
                    // hence transaction ID for the command set.
                    if (receivedCommand instanceof ZclCommand) {
                        synchronized (command) {
                            final byte transactionId = command.getTransactionId();
                            if (new Byte(transactionId).equals(((ZclCommand) receivedCommand).getTransactionId())) {
                                synchronized (future) {
                                    future.set(new ZclCommandResponse((ZclCommand) receivedCommand));
                                    future.notify();
                                    network.removeCommandListener(this);
                                }
                            }
                        }
                    }
                }
            });

            try {
                int transactionId = network.sendCommand(command);
                command.setTransactionId((byte) transactionId);
            } catch (ZigBeeException e) {
                throw new SimpleZigBeeApiException("Error sending " + command.getClass().getSimpleName()
                        + " to " + device.getNetworkAddress() + "/" + device.getEndpoint(), e);
            }
        }

        return future;
    }

}
