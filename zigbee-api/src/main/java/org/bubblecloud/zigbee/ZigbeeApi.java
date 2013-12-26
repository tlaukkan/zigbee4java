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

import org.bubblecloud.zigbee.network.model.DriverStatus;
import org.bubblecloud.zigbee.network.model.NetworkMode;
import org.bubblecloud.zigbee.proxy.DeviceProxyListener;
import org.bubblecloud.zigbee.network.impl.ApplicationFrameworkLayer;
import org.bubblecloud.zigbee.network.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.*;
import org.bubblecloud.zigbee.proxy.device.api.generic.*;
import org.bubblecloud.zigbee.proxy.device.api.hvac.Pump;
import org.bubblecloud.zigbee.proxy.device.api.hvac.TemperatureSensor;
import org.bubblecloud.zigbee.proxy.device.api.lighting.*;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IASAncillaryControlEquipment;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IASControlAndIndicatingEquipment;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IAS_Warning;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IAS_Zone;
import org.bubblecloud.zigbee.proxy.device.impl.*;
import org.bubblecloud.zigbee.proxy.DeviceProxyBase;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetwork;
import org.bubblecloud.zigbee.network.DeviceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Zigbee Application Interface.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigbeeApi implements DeviceListener, DeviceProxyListener {
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
    private ZigbeeProxyContext context;
    /**
     * The zigbee network.
     */
    private ZigBeeNetwork network;

    /**
     * Constructor to configure the serial interface.
     *
     * @param serialPortName
     * @param pan            the pan ID
     * @param channel        the channel
     * @param resetNetwork   set true reset network
     */
    public ZigbeeApi(final String serialPortName, final int pan, final int channel, final boolean resetNetwork,
                     final boolean lqiDiscovery) {
        networkManager = new ZigbeeNetworkManagerSerialImpl(serialPortName, 115200,
                NetworkMode.Coordinator, pan, channel, resetNetwork, 2500L);

        discoveryManager = new ZigbeeDiscoveryManager(networkManager, lqiDiscovery);
    }

    /**
     * Starts up network manager, network, context and discovery manager.
     *
     * @return true if startup was success.
     */
    public boolean startup() {
        networkManager.open();

        while (true) {
            if (networkManager.getDriverStatus() == DriverStatus.NETWORK_READY) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
                return false;
            }
        }

        network = ApplicationFrameworkLayer.getAFLayer(networkManager).getZigBeeNetwork();
        network.addDeviceListener(this);

        context = new ZigbeeProxyContext();

        final ClusterFactory clusterFactory = new ClusterFactoryImpl(context);
        context.setClusterFactory(clusterFactory);

        final Map<Class<?>, Class<?>> interfaceProxyMap = new HashMap<Class<?>, Class<?>>();
        interfaceProxyMap.put(ColorDimmableLight.class, ColorDimmableLightDeviceProxy.class);
        interfaceProxyMap.put(DimmableLight.class, DimmableLightDeviceProxy.class);
        interfaceProxyMap.put(IAS_Zone.class, IAS_ZoneDeviceProxy.class);
        interfaceProxyMap.put(IASAncillaryControlEquipment.class, IASAncillaryControlEquipmentDeviceProxy.class);
        interfaceProxyMap.put(IASControlAndIndicatingEquipment.class, IASControlAndIndicatingEquipmentDeviceProxy.class);
        interfaceProxyMap.put(LevelControlSwitch.class, LevelControlSwitchDeviceProxy.class);
        interfaceProxyMap.put(LightSensor.class, LightSensorDeviceProxy.class);
        interfaceProxyMap.put(MainsPowerOutlet.class, MainsPowerOutletDeviceProxy.class);
        interfaceProxyMap.put(OccupancySensor.class, OccupancySensorDeviceProxy.class);
        interfaceProxyMap.put(OnOffLight.class, OnOffLightDeviceProxy.class);
        interfaceProxyMap.put(OnOffLightSwitch.class, OnOffLightSwitchDeviceProxy.class);
        interfaceProxyMap.put(OnOffOutput.class, OnOffOutputDeviceProxy.class);
        interfaceProxyMap.put(OnOffSwitch.class, OnOffSwitchDeviceProxy.class);
        interfaceProxyMap.put(OnOffLight.class, OnOffLightDeviceProxy.class);
        interfaceProxyMap.put(Pump.class, PumpDeviceProxy.class);
        interfaceProxyMap.put(TemperatureSensor.class, TemperatureSensorDeviceProxy.class);
        interfaceProxyMap.put(IAS_Warning.class, IAS_Warning_DeviceProxy.class);
        interfaceProxyMap.put(SimpleSensor.class, SimpleSensorDeviceProxy.class);

        final Iterator<Map.Entry<Class<?>, Class<?>>> i = interfaceProxyMap.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<Class<?>, Class<?>> refining = i.next();
            try {
                context.getDeviceProxyFactories().add(
                        new DeviceProxyFactoryImpl(context, refining.getKey(), refining.getValue()));
            } catch (Exception ex) {
                LOGGER.error("Failed to register DeviceProxyFactoryImpl for " + refining.getKey(), ex);
            }
        }

        context.addDeviceProxyListener(this);

        discoveryManager.startup();

        return true;
    }

    /**
     * Shuts down network manager, network, context and discovery manager.
     */
    public void shutdown() {
        context.removeDeviceProxyListener(this);
        network.removeDeviceListener(this);

        discoveryManager.shutdown();
        networkManager.close();

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
    public ZigbeeProxyContext getZigbeeProxyContext() {
        return context;
    }

    /**
     * Gets Zigbee network.
     *
     * @return the Zigbee network.
     */
    public ZigBeeNetwork getZigbeeNetwork() {
        return network;
    }

    @Override
    public void deviceAdded(ZigBeeDevice device) {
        final DeviceProxyFactory factory = context.getBestDeviceProxyFactory(device);
        if (factory == null) { // pending services
            LOGGER.warn("No proxy for Zigbee device {} found.", device.getDeviceId());
            return;
        }

        final DeviceProxyBase haDevice = factory.getInstance(device);
        context.addDeviceProxy(haDevice);
        LOGGER.info("Device added: " + device.getUniqueIdenfier());
    }

    @Override
    public void deviceUpdated(ZigBeeDevice device) {
        LOGGER.info("Device updated: " + device.getUniqueIdenfier());
    }

    @Override
    public void deviceRemoved(ZigBeeDevice device) {
        LOGGER.info("Device removed: " + device.getUniqueIdenfier());
    }

    @Override
    public void deviceAdded(DeviceProxy device) {
        LOGGER.info(device.getClass().getSimpleName() +
                " added: " + device.getDevice().getUniqueIdenfier());
    }

    @Override
    public void deviceUpdated(DeviceProxy device) {
        LOGGER.info(device.getClass().getSimpleName() +
                " updated: " + device.getDevice().getUniqueIdenfier());
    }

    @Override
    public void deviceRemoved(DeviceProxy device) {
        LOGGER.info(device.getClass().getSimpleName() +
                " removed: " + device.getDevice().getUniqueIdenfier());
    }
}
