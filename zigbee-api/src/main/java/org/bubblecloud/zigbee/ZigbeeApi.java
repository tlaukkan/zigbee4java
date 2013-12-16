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
 * Created with IntelliJ IDEA.
 * User: tlaukkan
 * Date: 12/15/13
 * Time: 8:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ZigbeeApi implements DeviceListener, DeviceProxyListener {
    private final static Logger logger = LoggerFactory.getLogger(ZigbeeDiscoveryManager.class);

    private final ZigbeeNetworkManager networkManager;
    private final ZigbeeDiscoveryManager discoveryManager;

    private ZigbeeContext context;
    private ZigBeeNetwork network;

    public ZigbeeApi(final String serialPortName){
       networkManager = new ZigbeeNetworkManager(serialPortName, 115200,
               NetworkMode.Coordinator, 4951, 22, false, 2500L);

        discoveryManager = new ZigbeeDiscoveryManager(networkManager);
    }

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

        context = new ZigbeeContext();

        final ClusterFactory clusterFactory = new ClusterFactoryImpl(context);

        context.setClusterFactory(clusterFactory);

        Map< Class<?>, Class<?> > refinedAvailables = new HashMap< Class<?>, Class<?> >();
        refinedAvailables.put(ColorDimmableLight.class, ColorDimmableLightDeviceProxy.class);
        refinedAvailables.put( DimmableLight.class, DimmableLightDeviceProxy.class );
        refinedAvailables.put( IAS_Zone.class, IAS_ZoneDeviceProxy.class );
        refinedAvailables.put( IASAncillaryControlEquipment.class, IASAncillaryControlEquipmentDeviceProxy.class );
        refinedAvailables.put( IASControlAndIndicatingEquipment.class, IASControlAndIndicatingEquipmentDeviceProxy.class );
        refinedAvailables.put( LevelControlSwitch.class, LevelControlSwitchDeviceProxy.class );
        refinedAvailables.put( LightSensor.class, LightSensorDeviceProxy.class );
        refinedAvailables.put( MainsPowerOutlet.class, MainsPowerOutletDeviceProxy.class );
        refinedAvailables.put( OccupancySensor.class, OccupancySensorDeviceProxy.class );
        refinedAvailables.put( OnOffLight.class, OnOffLightDeviceProxy.class );
        refinedAvailables.put( OnOffLightSwitch.class, OnOffLightSwitchDeviceProxy.class );
        refinedAvailables.put( OnOffOutput.class, OnOffOutputDeviceProxy.class );
        refinedAvailables.put( OnOffSwitch.class, OnOffSwitchDeviceProxy.class );
        refinedAvailables.put( OnOffLight.class, OnOffLightDeviceProxy.class );
        refinedAvailables.put( Pump.class, PumpDeviceProxy.class );
        refinedAvailables.put( TemperatureSensor.class, TemperatureSensorDeviceProxy.class );
        refinedAvailables.put( IAS_Warning.class, IAS_Warning_DeviceProxy.class );
        refinedAvailables.put( SimpleSensor.class, SimpleSensorDeviceProxy.class );

        final Iterator<Map.Entry<Class<?>, Class<?>>> i = refinedAvailables.entrySet().iterator();
        while ( i.hasNext() ) {
            Map.Entry<Class<?>, Class<?>> refining = i.next();
            try {
                context.getDeviceProxyFactories().add(
                        new DeviceProxyFactoryImpl(context, refining.getKey(), refining.getValue()).register());
            } catch ( Exception ex) {
                logger.error( "Failed to register DeviceProxyFactoryImpl for " + refining.getKey(), ex );
            }
        }

        /*try {
            factories.add( new UnknowHADeviceFactory( bc ).register() );
        } catch ( Exception ex) {
            logger.error( "Failed to register UnknowHADeviceFactory", ex );
        }*/

        context.addDeviceProxyListener(this);

        discoveryManager.startup();

        return true;
    }

    public void shutdown() {
        context.removeDeviceProxyListener(this);
        network.removeDeviceListener(this);

        discoveryManager.shutdown();
        networkManager.close();

    }

    public void deviceAdded(ZigBeeDevice device) {
        final DeviceProxyFactory factory = context.getBestDeviceProxyFactory(device);
        if ( factory == null) { // pending services
            logger.warn("No refinement for ZigbeeDevice {} found.", device.getPhysicalNode().getIEEEAddress());
            return;
        }

        final DeviceProxyBase haDevice = factory.getInstance(device);
        context.addDeviceProxy(haDevice);
        logger.info("Device added: " + device.getUniqueIdenfier());
    }

    public void deviceUpdated(ZigBeeDevice device) {
        logger.info("Device updated: " + device.getUniqueIdenfier());
    }

    public void deviceRemoved(ZigBeeDevice device) {
        logger.info("Device removed: " + device.getUniqueIdenfier());
    }

    @Override
    public void deviceAdded(DeviceProxyBase device) {
        logger.info(device.getClass().getSimpleName() +
                " added: " + device.getDevice().getUniqueIdenfier());
    }

    @Override
    public void deviceUpdated(DeviceProxyBase device) {
        logger.info(device.getClass().getSimpleName() +
                " updated: " + device.getDevice().getUniqueIdenfier());
    }

    @Override
    public void deviceRemoved(DeviceProxyBase device) {
        logger.info(device.getClass().getSimpleName() +
                " removed: " + device.getDevice().getUniqueIdenfier());
    }

    public ZigbeeNetworkManager getNetworkManager() {
        return networkManager;
    }

    public ZigbeeDiscoveryManager getDiscoveryManager() {
        return discoveryManager;
    }

    public ZigbeeContext getContext() {
        return context;
    }

    public ZigBeeNetwork getNetwork() {
        return network;
    }
}
