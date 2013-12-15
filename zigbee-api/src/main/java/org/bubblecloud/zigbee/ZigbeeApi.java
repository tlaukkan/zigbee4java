package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.network.glue.ZigbeeNetworkManagementInterface;
import org.bubblecloud.zigbee.network.ApplicationFrameworkLayer;
import org.bubblecloud.zigbee.network.glue.ZigBeeDevice;
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
import org.bubblecloud.zigbee.network.ZigBeeNetwork;
import org.bubblecloud.zigbee.network.glue.DeviceListener;
import org.bubblecloud.zigbee.network.glue.HaDeviceListener;
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
public class ZigbeeApi implements DeviceListener, HaDeviceListener {
    private final static Logger logger = LoggerFactory.getLogger(ZigbeeDiscoveryManager.class);

    private final ZigbeeNetworkManagementInterface simpleDriver;
    private BundleContext context;
    private ZigBeeNetwork network;

    public ZigbeeApi(final ZigbeeNetworkManagementInterface simpleDriver){
        this.simpleDriver = simpleDriver;
    }

    public void startup() {
        network = ApplicationFrameworkLayer.getAFLayer(simpleDriver).getZigBeeNetwork();
        network.addDeviceListener(this);

        context = new BundleContext();

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
                context.getDeviceFactories().add(
                        new DeviceProxyFactoryImpl(context, refining.getKey(), refining.getValue()).register());
            } catch ( Exception ex) {
                logger.error( "Failed to register DeviceProxyFactoryImpl for " + refining.getKey(), ex );
            }
        }

        context.addDeviceListener(this);
                /*try {
            factories.add( new UnknowHADeviceFactory( bc ).register() );
        } catch ( Exception ex) {
            logger.error( "Failed to register UnknowHADeviceFactory", ex );
        }*/

    }

    public void shutdown() {
        context.removeDeviceListener(this);
        network.removeDeviceListener(this);
    }

    public void deviceAdded(ZigBeeDevice device) {
        final DeviceProxyFactory factory = context.getBestDeviceFactory(device);
        if ( factory == null) { // pending services
            logger.warn("No refinement for ZigbeeDevice {} found.", device.getPhysicalNode().getIEEEAddress());
            return;
        }

        final DeviceProxyBase haDevice = factory.getInstance(device);
        context.addHaDevice(haDevice);
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
        logger.info("Device proxy added: " + device.getZBDevice().getUniqueIdenfier()
                + " (" + device.getClass().getSimpleName() + ")");
    }

    @Override
    public void deviceUpdated(DeviceProxyBase device) {
        logger.info("Device proxy updated: " + device.getZBDevice().getUniqueIdenfier()
                + " (" + device.getClass().getSimpleName() + ")");
    }

    @Override
    public void deviceRemoved(DeviceProxyBase device) {
        logger.info("Device proxy removed: " + device.getZBDevice().getUniqueIdenfier()
                + " (" + device.getClass().getSimpleName() + ")");
    }
}
