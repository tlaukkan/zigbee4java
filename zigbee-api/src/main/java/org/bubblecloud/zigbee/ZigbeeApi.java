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
import org.bubblecloud.zigbee.network.ZigbeeEndpoint;
import org.bubblecloud.zigbee.network.discovery.ZigbeeDiscoveryManager;
import org.bubblecloud.zigbee.network.impl.ZigbeeNetwork;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.api.DeviceListener;
import org.bubblecloud.zigbee.network.impl.ApplicationFrameworkLayer;
import org.bubblecloud.zigbee.api.*;
import org.bubblecloud.zigbee.api.device.generic.*;
import org.bubblecloud.zigbee.api.device.hvac.Pump;
import org.bubblecloud.zigbee.api.device.hvac.TemperatureSensor;
import org.bubblecloud.zigbee.api.device.lighting.*;
import org.bubblecloud.zigbee.api.device.security_safety.IASAncillaryControlEquipment;
import org.bubblecloud.zigbee.api.device.security_safety.IASControlAndIndicatingEquipment;
import org.bubblecloud.zigbee.api.device.security_safety.IAS_Warning;
import org.bubblecloud.zigbee.api.device.security_safety.IAS_Zone;
import org.bubblecloud.zigbee.api.device.impl.*;
import org.bubblecloud.zigbee.api.DeviceBase;
import org.bubblecloud.zigbee.network.serial.ZigbeeNetworkManagerSerialImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Zigbee Application Interface.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigbeeApi implements EndpointListener, DeviceListener {
    /**
     * The logger.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ZigbeeDiscoveryManager.class);
    /**
     * The Zigbee network manager.
     */
    private final ZigbeeNetworkManagerSerialImpl networkManager;
    /**
     * The Zigbee discovery manager.
     */
    private final ZigbeeDiscoveryManager discoveryManager;
    /**
     * The Zigbee context.
     */
    private ZigbeeApiContext context;
    /**
     * The zigbee network.
     */
    private ZigbeeNetwork network;

    /**
     * Constructor to configure the serial interface.
     *
     * @param serialPortName the serial port name
     * @param pan            the pan
     * @param channel        the channel
     * @param discoveryModes the discovery modes
     * @param resetNetwork   the flag indicating network reset on startup
     */
    public ZigbeeApi(final String serialPortName, final int pan, final int channel,
            final EnumSet<DiscoveryMode> discoveryModes, final boolean resetNetwork) {
        networkManager = new ZigbeeNetworkManagerSerialImpl(serialPortName, 115200,
                NetworkMode.Coordinator, pan, channel, resetNetwork, 2500L);

        discoveryManager = new ZigbeeDiscoveryManager(networkManager, discoveryModes);
    }

    /**
     * Constructor to configure the serial interface.
     *
     * @param serialPortName the serial port name
     * @param pan            the pan
     * @param channel        the channel
     * @param resetNetwork   the flag indicating network reset on startup
     */
    public ZigbeeApi(final String serialPortName, final int pan, final int channel,
                     final boolean resetNetwork) {
        networkManager = new ZigbeeNetworkManagerSerialImpl(serialPortName, 115200,
                NetworkMode.Coordinator, pan, channel, resetNetwork, 2500L);

        final EnumSet<DiscoveryMode> discoveryModes = DiscoveryMode.ALL;
        discoveryModes.remove(DiscoveryMode.LinkQuality);
        discoveryManager = new ZigbeeDiscoveryManager(networkManager, discoveryModes);
    }


    /**
     * Starts up network manager, network, context and discovery manager.
     *
     * @return true if startup was success.
     */
    public boolean startup() {
        networkManager.startup();

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

        network = ApplicationFrameworkLayer.getAFLayer(networkManager).getZigBeeNetwork();
        network.addEndpointListenerListener(this);

        context = new ZigbeeApiContext();

        final ClusterFactory clusterFactory = new ClusterFactoryImpl(context);
        context.setClusterFactory(clusterFactory);

        final Map<Class<?>, Class<?>> deviceIntefaceImplemetnationMap = new HashMap<Class<?>, Class<?>>();
        deviceIntefaceImplemetnationMap.put(ColorDimmableLight.class, ColorDimmableLightDevice.class);
        deviceIntefaceImplemetnationMap.put(DimmableLight.class, DimmableLightDevice.class);
        deviceIntefaceImplemetnationMap.put(IAS_Zone.class, IAS_ZoneDevice.class);
        deviceIntefaceImplemetnationMap.put(IASAncillaryControlEquipment.class, IASAncillaryControlEquipmentDevice.class);
        deviceIntefaceImplemetnationMap.put(IASControlAndIndicatingEquipment.class, IASControlAndIndicatingEquipmentDevice.class);
        deviceIntefaceImplemetnationMap.put(LevelControlSwitch.class, LevelControlSwitchDevice.class);
        deviceIntefaceImplemetnationMap.put(LightSensor.class, LightSensorDevice.class);
        deviceIntefaceImplemetnationMap.put(MainsPowerOutlet.class, MainsPowerOutletDevice.class);
        deviceIntefaceImplemetnationMap.put(OccupancySensor.class, OccupancySensorDevice.class);
        deviceIntefaceImplemetnationMap.put(OnOffLight.class, OnOffLightDevice.class);
        deviceIntefaceImplemetnationMap.put(OnOffLightSwitch.class, OnOffLightSwitchDevice.class);
        deviceIntefaceImplemetnationMap.put(OnOffOutput.class, OnOffOutputDevice.class);
        deviceIntefaceImplemetnationMap.put(OnOffSwitch.class, OnOffSwitchDevice.class);
        deviceIntefaceImplemetnationMap.put(OnOffLight.class, OnOffLightDevice.class);
        deviceIntefaceImplemetnationMap.put(Pump.class, PumpDevice.class);
        deviceIntefaceImplemetnationMap.put(TemperatureSensor.class, TemperatureSensorDevice.class);
        deviceIntefaceImplemetnationMap.put(IAS_Warning.class, IAS_Warning_Device.class);
        deviceIntefaceImplemetnationMap.put(SimpleSensor.class, SimpleSensorDevice.class);

        final Iterator<Map.Entry<Class<?>, Class<?>>> i = deviceIntefaceImplemetnationMap.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<Class<?>, Class<?>> refining = i.next();
            try {
                context.getDeviceFactories().add(
                        new DeviceFactoryImpl(context, refining.getKey(), refining.getValue()));
            } catch (Exception ex) {
                LOGGER.error("Failed to register DeviceFactoryImpl for " + refining.getKey(), ex);
            }
        }

        context.addDeviceListener(this);

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
        context.removeDeviceListener(this);
        network.removeEndpointListener(this);

        discoveryManager.shutdown();
        networkManager.shutdown();

    }

    /**
     * Gets Zigbee network manager.
     *
     * @return the Zigbee network manager.
     */
    public ZigbeeNetworkManagerSerialImpl getZigbeeNetworkManager() {
        return networkManager;
    }

    /**
     * Gets Zigbee discovery manager.
     *
     * @return the Zigbee discovery manager.
     */
    public ZigbeeDiscoveryManager getZigbeeDiscoveryManager() {
        return discoveryManager;
    }

    /**
     * Gets Zigbee proxy context.
     *
     * @return the Zigbee proxy context.
     */
    public ZigbeeApiContext getZigbeeApiContext() {
        return context;
    }

    /**
     * Gets Zigbee network.
     *
     * @return the Zigbee network.
     */
    public ZigbeeNetwork getZigbeeNetwork() {
        return network;
    }

    public Device getDevice(String endPointId) {
        return context.getDevice(endPointId);
    }

    public List<Device> getDevices() {
        return context.getDevices();
    }

    public void addDeviceListener(DeviceListener deviceListener) {
        context.addDeviceListener(deviceListener);
    }

    public void removeDeviceListener(DeviceListener deviceListener) {
        context.removeDeviceListener(deviceListener);
    }

    @Override
    public void endpointAdded(final ZigbeeEndpoint endpoint) {
        final DeviceFactory factory = context.getBestDeviceProxyFactory(endpoint);
        if (factory == null) { // pending services
            LOGGER.warn("No proxy for Zigbee endpoint {} found.", endpoint.getDeviceTypeId());
            return;
        }

        final DeviceBase haDevice = factory.getInstance(endpoint);
        context.addDevice(haDevice);
        LOGGER.trace("Endpoint added: " + endpoint.getEndpointId());
    }

    @Override
    public void endpointUpdated(final ZigbeeEndpoint endpoint) {
        LOGGER.trace("Endpoint updated: " + endpoint.getEndpointId());
    }

    @Override
    public void endpointRemoved(final ZigbeeEndpoint endpoint) {
        LOGGER.trace("Endpoint removed: " + endpoint.getEndpointId());
    }

    @Override
    public void deviceAdded(final Device device) {
        LOGGER.trace(device.getClass().getSimpleName() +
                " added: " + device.getEndpoint().getEndpointId());
    }

    @Override
    public void deviceUpdated(final Device device) {
        LOGGER.trace(device.getClass().getSimpleName() +
                " updated: " + device.getEndpoint().getEndpointId());
    }

    @Override
    public void deviceRemoved(final Device device) {
        LOGGER.trace(device.getClass().getSimpleName() +
                " removed: " + device.getEndpoint().getEndpointId());
    }
}
