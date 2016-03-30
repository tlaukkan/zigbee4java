/**
 * Copyright 2013 Tommi S.E. Laukkanen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.EndpointListener;
import org.bubblecloud.zigbee.network.NodeListener;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.ZigBeeNode;
import org.bubblecloud.zigbee.network.discovery.ZigBeeDiscoveryManager;
import org.bubblecloud.zigbee.network.impl.NetworkStateSerializer;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetwork;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.network.packet.ZToolAddress16;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_MGMT_PERMIT_JOIN_REQ;
import org.bubblecloud.zigbee.network.packet.zdo.ZDO_MGMT_PERMIT_JOIN_RSP;
import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.DeviceListener;
import org.bubblecloud.zigbee.network.impl.ApplicationFrameworkLayer;
import org.bubblecloud.zigbee.api.*;
import org.bubblecloud.zigbee.api.device.generic.*;
import org.bubblecloud.zigbee.api.device.hvac.Pump;
import org.bubblecloud.zigbee.api.device.hvac.ThermostatControl;
import org.bubblecloud.zigbee.api.device.hvac.TemperatureSensor;
import org.bubblecloud.zigbee.api.device.lighting.*;
import org.bubblecloud.zigbee.api.device.security_safety.IASAncillaryControlEquipment;
import org.bubblecloud.zigbee.api.device.security_safety.IASControlAndIndicatingEquipment;
import org.bubblecloud.zigbee.api.device.security_safety.IAS_Warning;
import org.bubblecloud.zigbee.api.device.security_safety.IAS_Zone;
import org.bubblecloud.zigbee.api.device.impl.*;
import org.bubblecloud.zigbee.api.DeviceBase;
import org.bubblecloud.zigbee.network.port.ZigBeeNetworkManagerImpl;
import org.bubblecloud.zigbee.network.port.ZigBeePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ZigBee Application Programming Interface.
 * <p>
 * This is the main interface to the ZigBee library.
 * <p>
 * <u><b>Battery Device Operation</b></u>
 * <p>
 * Note that in the ZigBee Pro stack profile the MAC of the router is buffering
 * the indirect transmissions addressing sleeping end devices (aka
 * <i>rxOnWhenIdle</i>=false) for exactly 7.5s. After this interval an indirect
 * message is purged by the router.
 * <p>
 * So if you send a message, say from the coordinator, to a sleeping end device,
 * the device might not be able to extract the message from its router via a
 * poll, unless it polls its router with a period <= 7.5s which the HA profiles
 * specification does not allow and would waste too much energy on the device.
 * <p>
 * Since battery powered devices cannot normally poll their routers at these
 * rates due to energy constraints, so you cannot talk to a device at any time.
 * <p>
 * Instead, these device can be configured to report (some of) their attributes
 * to the coordinator with a long period, say once every 10 minutes. Usually,
 * the device's stack will poll it's router shortly after sending the report.
 * maybe to receive an application acknowledge from the coordinator.
 * <p>
 * This will be the time to try to talk to the device and you must do that
 * quickly. In the meantime, the messages to be delivered to a sleeping end
 * device must be buffered by the sender because the device's router cannot
 * buffer them for more than 7.5s.
 * <p>
 * On the reply timeout you should choose a value > 7.5s. Waiting much more is
 * not needed if the route to the device already exists.
 * 
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 */
public class ZigBeeApi implements EndpointListener {
    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ZigBeeApi.class);
    /**
     * The {@link ZigBeeNetworkManagerImpl ZigBee network manager}.
     */
    private final ZigBeeNetworkManagerImpl networkManager;
    /**
     * The {@link ZigBeeDiscoveryManager ZigBee discovery manager}.
     */
    private final ZigBeeDiscoveryManager discoveryManager;
    /**
     * The {@link ZigBeeApiContext ZigBee context}.
     */
    private ZigBeeApiContext context;
    /**
     * The {@link ZigBeeNetwork ZigBee network}.
     */
    private ZigBeeNetwork network;
    /**
     * Flag to reset the network on startup
     */
	private boolean resetNetwork = false;

    /**
     * Constructor to configure the port interface.
     *
     * @param port           the ZigBee interface port (reference implementation provided by the zigbee4java-serialPort module)
     * @param pan            the pan
     * @param channel        the channel
     * @param discoveryModes the discovery modes
     * @param resetNetwork   the flag indicating network reset on startup
     */
    public ZigBeeApi(final ZigBeePort port, final int pan, final int channel,
            final EnumSet<DiscoveryMode> discoveryModes, final boolean resetNetwork) {
    	this.resetNetwork = resetNetwork;

        networkManager = new ZigBeeNetworkManagerImpl(port,
                NetworkMode.Coordinator, pan, channel, 2500L);

        discoveryManager = new ZigBeeDiscoveryManager(networkManager, discoveryModes);
    }

    /**
     * Constructor to configure the port interface.
     *
     * @param port           the ZigBee interface port (reference implementation provided by the zigbee4java-serialPort module)
     * @param pan            the pan
     * @param channel        the channel
     * @param discoveryModes the discovery modes
     */
    public ZigBeeApi(final ZigBeePort port, final int pan, final int channel, final EnumSet<DiscoveryMode> discoveryModes) {
        networkManager = new ZigBeeNetworkManagerImpl(port,
                NetworkMode.Coordinator, pan, channel, 2500L);

        discoveryManager = new ZigBeeDiscoveryManager(networkManager, discoveryModes);
    }

    /**
     * Constructor to configure the port interface.
     *
     * @param port           the ZigBee interface port (reference implementation provided by the zigbee4java-serialPort module)
     * @param pan            the pan
     * @param channel        the channel
     * @param resetNetwork   the flag indicating network reset on startup
     */
    public ZigBeeApi(final ZigBeePort port, final int pan, final int channel,
                     final boolean resetNetwork, final EnumSet<DiscoveryMode> discoveryModes) {
    	this.resetNetwork = resetNetwork;

        networkManager = new ZigBeeNetworkManagerImpl(port, NetworkMode.Coordinator, pan, channel, 2500L);
        discoveryManager = new ZigBeeDiscoveryManager(networkManager, discoveryModes);
        network = ApplicationFrameworkLayer.getAFLayer(networkManager).getZigBeeNetwork();

        network.addEndpointListener(this);

        context = new ZigBeeApiContext();

        final ClusterFactory clusterFactory = new ClusterFactoryImpl(context);
        context.setClusterFactory(clusterFactory);

        try {
	        context.getDeviceFactories().put(ColorDimmableLight.DEVICE_ID, new DeviceFactoryImpl(context, ColorDimmableLight.class, ColorDimmableLightDevice.class));
	        context.getDeviceFactories().put(DimmableLight.DEVICE_ID, new DeviceFactoryImpl(context, DimmableLight.class, DimmableLightDevice.class));
	        context.getDeviceFactories().put(IAS_Zone.DEVICE_ID, new DeviceFactoryImpl(context, IAS_Zone.class, IAS_ZoneDevice.class));
	        context.getDeviceFactories().put(IASAncillaryControlEquipment.DEVICE_ID, new DeviceFactoryImpl(context, IASAncillaryControlEquipment.class, IASAncillaryControlEquipmentDevice.class));
	        context.getDeviceFactories().put(IASControlAndIndicatingEquipment.DEVICE_ID, new DeviceFactoryImpl(context, IASControlAndIndicatingEquipment.class, IASControlAndIndicatingEquipmentDevice.class));
	        context.getDeviceFactories().put(LevelControlSwitch.DEVICE_ID, new DeviceFactoryImpl(context, LevelControlSwitch.class, LevelControlSwitchDevice.class));
	        context.getDeviceFactories().put(LightSensor.DEVICE_ID, new DeviceFactoryImpl(context, LightSensor.class, LightSensorDevice.class));
	        context.getDeviceFactories().put(MainsPowerOutlet.DEVICE_ID, new DeviceFactoryImpl(context, MainsPowerOutlet.class, MainsPowerOutletDevice.class));
	        context.getDeviceFactories().put(OccupancySensor.DEVICE_ID, new DeviceFactoryImpl(context, OccupancySensor.class, OccupancySensorDevice.class));
	        context.getDeviceFactories().put(OnOffLight.DEVICE_ID, new DeviceFactoryImpl(context, OnOffLight.class, OnOffLightDevice.class));
	        context.getDeviceFactories().put(OnOffLightSwitch.DEVICE_ID, new DeviceFactoryImpl(context, OnOffLightSwitch.class, OnOffLightSwitchDevice.class));
	        context.getDeviceFactories().put(OnOffOutput.DEVICE_ID, new DeviceFactoryImpl(context, OnOffOutput.class, OnOffOutputDevice.class));
	        context.getDeviceFactories().put(OnOffSwitch.DEVICE_ID, new DeviceFactoryImpl(context, OnOffSwitch.class, OnOffSwitchDevice.class));
	        context.getDeviceFactories().put(OnOffLight.DEVICE_ID, new DeviceFactoryImpl(context, OnOffLight.class, OnOffLightDevice.class));
	        context.getDeviceFactories().put(Pump.DEVICE_ID, new DeviceFactoryImpl(context, Pump.class, PumpDevice.class));
	        context.getDeviceFactories().put(ThermostatControl.DEVICE_ID, new DeviceFactoryImpl(context, ThermostatControl.class, ThermostatControlDevice.class));
	        context.getDeviceFactories().put(TemperatureSensor.DEVICE_ID, new DeviceFactoryImpl(context, TemperatureSensor.class, TemperatureSensorDevice.class));
	        context.getDeviceFactories().put(IAS_Warning.DEVICE_ID, new DeviceFactoryImpl(context, IAS_Warning.class, IAS_Warning_Device.class));
	        context.getDeviceFactories().put(SimpleSensorDevice.DEVICE_ID, new DeviceFactoryImpl(context, SimpleSensor.class, SimpleSensorDevice.class));
	    } catch (Exception ex) {
	        LOGGER.error("Failed to register DeviceFactoryImpl ", ex);
	    }
    }

    /**
     * Starts up network manager, network, context and discovery manager.
     *
     * @return true if startup was success.
     */
    public boolean startup() {
        if (!networkManager.startup()) {
            return false;
        }

        return initializeNetwork(this.resetNetwork);
    }

    /**
     * Initialize the zigbee hardware
     */
    public boolean initializeHardware() {
        return networkManager.startup();    	
    }

    /**
     * Initializes the ZigBee network.
     * <p>
     * This is only required if the port is opened using initializeHardware
     * 
     * @param resetNetwork true to reset the network to the current panid and channel
     * @return true if the network was initialized correctly
     */
    public boolean initializeNetwork(boolean resetNetwork) {
        networkManager.initializeZigBeeNetwork(resetNetwork);

        while (true) {
            if (networkManager.getDriverStatus() == DriverStatus.NETWORK_READY) {
                break;
            }
            if (networkManager.getDriverStatus() == DriverStatus.CLOSED) {
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
                return false;
            }
        }

        ApplicationFrameworkLayer.getAFLayer(networkManager).createDefaultSendingEndPoint();

        /* disable permit join by default */
        //
        // permitJoin(false);

        discoveryManager.startup();

        return true;
    }

    /**
     * Return true if initial networking browsing based on associations is complete.
     *
     * @return true if initial network browsing is complete.
     */
    public boolean isInitialBrowsingComplete() {
        return discoveryManager.isInitialNetworkBrowsingComplete();
    }


    /**
     * Shuts down network manager, network, context and discovery manager.
     */
    public void shutdown() {
        network.removeEndpointListener(this);
        discoveryManager.shutdown();
        networkManager.shutdown();
    }

    /**
     * Changes the permit join state.
     *
     * @param joinState boolean join state, true for enabled indefinitely, false for disabled
     *
     * @return true if success
     */
    public boolean permitJoin(boolean joinState) {
        if (joinState) {
            return sendPermitJoin((byte)0xFF);
        } else {
            return sendPermitJoin((byte)0);
        }
    }

    /**
     * Changes the permit join state with a timeout duration.
     *
     * @param durationSeconds join duration in seconds, from 1-254
     *
     * @return true if success
     */
    public boolean permitJoin(int durationSeconds) {
        if (durationSeconds < 1 || durationSeconds > 254) {
            LOGGER.error("permitJoin durationSeconds out of range: {}", durationSeconds);
            return false;
        }
        return sendPermitJoin((byte)durationSeconds);
    }

    /**
     * Sends the permit join state to routers and coordinators.
     *
     * @param data the data in the permit join request
     *
     * @return true if success
     */
    private boolean sendPermitJoin(byte data) {
        ZDO_MGMT_PERMIT_JOIN_RSP result;
        final byte AddrBroadcast = 0x0F;
        final byte AddrUnicast = 0x02;

        LOGGER.debug("Sending permit join with data: {}", data);

        // Notify routers of permit join change; don't check result because they're not obligated to respond
        networkManager.sendPermitJoinRequest(new ZDO_MGMT_PERMIT_JOIN_REQ(AddrBroadcast, ZToolAddress16.ZCZR_BROADCAST, data, 1), false);

        // Notify coordinator of permit join change.
        // Local coordinator does not seem to respond when network is empty i.e. no nodes joined.
        networkManager.sendPermitJoinRequest(new ZDO_MGMT_PERMIT_JOIN_REQ(AddrUnicast, new ZToolAddress16(0, 0), data, 1), false);

        return true;
    }

    /**
	 * Serializes network state.
	 * <p>
	 * Creates a json string containing the network state which can be saved by
	 * the application and restored next time the network starts. This allows
	 * the network startup speed to be increased.
	 * 
	 * @return the network state as a json {@link String}
	 */
    public String serializeNetworkState() {
        final NetworkStateSerializer networkStateSerializer = new NetworkStateSerializer();
        return networkStateSerializer.serialize(network);
    }

	/**
	 * Deserialize network state.
	 * <p>
	 * Reinitialised the network given the json {@link String} that was
	 * generated by {@link NetworkStateSerializer.serializeNetworkState}
	 * 
	 * @param networkState
	 *            the network state as a json {@link String}
	 */
    public void deserializeNetworkState(final String networkState) {
        final NetworkStateSerializer networkStateSerializer = new NetworkStateSerializer();
        networkStateSerializer.deserialize(networkManager, network, networkState);
    }


    /**
     * Gets the {@link ZigBeeNetworkManagerImpl ZigBee network manager}.
     *
     * @return the ZigBee network manager.
     */
    public ZigBeeNetworkManagerImpl getZigBeeNetworkManager() {
        return networkManager;
    }

    /**
     * Gets the {@link ZigBeeDiscoveryManager ZigBee discovery manager}.
     *
     * @return the ZigBee discovery manager.
     */
    public ZigBeeDiscoveryManager getZigBeeDiscoveryManager() {
        return discoveryManager;
    }

    /**
     * Gets ZigBee proxy context.
     *
     * @return the ZigBee proxy context.
     */
    public ZigBeeApiContext getZigBeeApiContext() {
        return context;
    }

    /**
     * Gets the {@link ZigBeeNetwork ZigBee network}.
     *
     * @return the ZigBee network.
     */
    public ZigBeeNetwork getZigBeeNetwork() {
        return network;
    }

    /**
     * Gets a single {@link Device ZigBee device}
     * 
     * @param endPointId
     * @return the ZigBee {@link Device ZigBee device}
     */
    public Device getDevice(String endPointId) {
        return context.getDevice(endPointId);
    }

    /**
     * Gets the list of {@link Device ZigBee devices}
     * 
     * @return the list of devices
     */
    public List<Device> getDevices() {
        return context.getDevices();
    }

    /**
     * Gets the list of current {@link ZigBeeNode}s
     * 
     * @return the list of current {@link ZigBeeNode}s
     */
    public List<ZigBeeNode> getNodes() {
    	ArrayList<ZigBeeNode> nodes = new ArrayList<ZigBeeNode>();
        for(ZigBeeNode n : network.getNodes().values()) {
        	nodes.add(n);
        }

        return nodes;
    }

    /**
     * Gets the list of current {@link ZigBeeEndpoint}s within the specified {@link ZigBeeNode}
     * 
     * @return the list of {@link ZigBeeEndpoint}s
     */
    public List<ZigBeeEndpoint> getNodeEndpoints(ZigBeeNode node) {
    	return network.getEndpoints(node);
    }

    /**
     * Adds a device listener. The listener will be notified for each new endpoint
     * that is found.
     * @param deviceListener {@link DeviceListener}
     */
    public void addDeviceListener(DeviceListener deviceListener) {
        context.addDeviceListener(deviceListener);
    }

    /**
     * Removes a previously registered device listener
     * @param deviceListener {@link DeviceListener}
     */
    public void removeDeviceListener(DeviceListener deviceListener) {
        context.removeDeviceListener(deviceListener);
    }

    @Override
    public void endpointAdded(final ZigBeeEndpoint endpoint) {
        final DeviceFactory factory = context.getBestDeviceProxyFactory(endpoint);
        if (factory == null) { // pending services
            LOGGER.warn("No proxy for ZigBee device type {} found with endpoint {}.",
            		endpoint.getDeviceTypeId(), endpoint.getEndpointId());
            return;
        }

        final DeviceBase haDevice = factory.getInstance(endpoint);
        context.addDevice(haDevice);
        LOGGER.trace("Endpoint added: " + endpoint.getEndpointId());
    }

    @Override
    public void endpointUpdated(final ZigBeeEndpoint endpoint) {
        LOGGER.trace("Endpoint updated: " + endpoint.getEndpointId());
        final Device device = context.getDevice(endpoint.getEndpointId());
        if (device != null) {
            context.updateDevice(device);
        }
    }

    @Override
    public void endpointRemoved(final ZigBeeEndpoint endpoint) {
        LOGGER.trace("Endpoint removed: " + endpoint.getEndpointId());
        final Device device = context.getDevice(endpoint.getEndpointId());
        if (device != null) {
            context.removeDevice(device);
        }
    }
    
    /**
     * Adds a {@link NodeListener node listener}. The listener will be notified for each new {@link ZigBeeNode}
     * that is found.
     * @param deviceListener {@link NodeListener}
     */
    public void addNodeListener(NodeListener nodeListener) {
        network.addNodeListener(nodeListener);
    }

    /**
     * Removes a previously registered node listener
     * @param nodeListener {@link NodeListener}
     */
    public void removeNodeListener(NodeListener nodeListener) {
        network.removeNodeListener(nodeListener);
    }

}
