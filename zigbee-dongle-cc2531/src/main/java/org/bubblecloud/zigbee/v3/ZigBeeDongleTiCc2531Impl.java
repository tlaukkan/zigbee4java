package org.bubblecloud.zigbee.v3;

import org.bubblecloud.zigbee.network.impl.*;
import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.v3.zcl.ZclCommand;
import org.bubblecloud.zigbee.v3.zcl.ZclCommandTransmitter;
import org.bubblecloud.zigbee.v3.zdo.ZdoCommand;
import org.bubblecloud.zigbee.v3.zdo.ZdoCommandTransmitter;
import org.bubblecloud.zigbee.v3.zdo.command.UserDescriptorSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ZigBee Dongle TI CC2531 implementation.
 */
public class ZigBeeDongleTiCc2531Impl implements ZigBeeDongle {
    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ZigBeeDongleTiCc2531Impl.class);
    /**
     * The {@link ZigBeeNetworkManagerImpl ZigBee network manager}.
     */
    private final ZigBeeNetworkManagerImpl networkManager;
    /**
     * The ZCL command transmitter.
     */
    private ZclCommandTransmitter zclCommandTransmitter;
    /**
     * The ZDO command transmitter.
     */
    private ZdoCommandTransmitter zdoCommandTransmitter;
    /**
     * Flag to reset the network on startup
     */
	private boolean resetNetwork = false;

    /**
     * Constructor to configure the port interface.
     *
     * @param serialPort     the serial port
     * @param pan            the pan
     * @param channel        the channel
     * @param resetNetwork   the reset network flag
     */
    public ZigBeeDongleTiCc2531Impl(final SerialPort serialPort, final int pan, final int channel,
                                    final boolean resetNetwork) {
    	this.resetNetwork = resetNetwork;

        networkManager = new ZigBeeNetworkManagerImpl(serialPort, NetworkMode.Coordinator, pan, channel, 2500L);
        zclCommandTransmitter = new ZclCommandTransmitter(networkManager);
        zdoCommandTransmitter = new ZdoCommandTransmitter(networkManager);
    }

    @Override
    public boolean startup() {
        if (!networkManager.startup()) {
            return false;
        }

        networkManager.addAFMessageListener(zclCommandTransmitter);
        networkManager.addAsynchronousCommandListener(zdoCommandTransmitter);

        if (!networkManager.initializeZigBeeNetwork(resetNetwork)) {
            return false;
        }

        while (true) {
            if (networkManager.getDriverStatus() == DriverStatus.NETWORK_READY) {
                break;
            }
            if (networkManager.getDriverStatus() == DriverStatus.CLOSED) {
                return false;
            }
            try {
                Thread.sleep(10);
            } catch (final InterruptedException e) {
                return false;
            }
        }

        ApplicationFrameworkLayer.getAFLayer(networkManager).createDefaultSendingEndPoint();

        return true;
    }

    @Override
    public void shutdown() {
        networkManager.shutdown();
    }

    @Override
    public int sendCommand(org.bubblecloud.zigbee.v3.Command command) throws ZigBeeException {
        if (command instanceof  ZclCommand) {
            return zclCommandTransmitter.sendCommand(((ZclCommand)command).toCommandMessage());
        }  else {
            zdoCommandTransmitter.sendCommand((ZdoCommand) command);
            return -1;
        }
    }

    @Override
    public void addCommandListener(final CommandListener commandListener) {
        this.zclCommandTransmitter.addCommandListener(commandListener);
        this.zdoCommandTransmitter.addCommandListener(commandListener);
    }

    @Override
    public void removeCommandListener(final CommandListener commandListener) {
        this.zclCommandTransmitter.removeCommandListener(commandListener);
        this.zdoCommandTransmitter.removeCommandListener(commandListener);
    }

}
