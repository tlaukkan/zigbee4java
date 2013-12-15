package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.core.ZigBeeDevice;
import org.bubblecloud.zigbee.proxy.core.HAClustersFactory;
import org.bubblecloud.zigbee.proxy.device.api.generic.*;
import org.bubblecloud.zigbee.proxy.device.api.hvac.Pump;
import org.bubblecloud.zigbee.proxy.device.api.hvac.TemperatureSensor;
import org.bubblecloud.zigbee.proxy.device.api.lighting.*;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IASAncillaryControlEquipment;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IASControlAndIndicatingEquipment;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IAS_Warning;
import org.bubblecloud.zigbee.proxy.device.api.security_safety.IAS_Zone;
import org.bubblecloud.zigbee.proxy.device.impl.*;
import org.bubblecloud.zigbee.proxy.core.ClusterFactory;
import org.bubblecloud.zigbee.proxy.core.GenericHADeviceFactory;
import org.bubblecloud.zigbee.proxy.core.HADeviceBase;
import org.bubblecloud.zigbee.proxy.core.HADeviceFactory;
import org.bubblecloud.zigbee.core.ZigBeeNetwork;
import org.bubblecloud.zigbee.model.DeviceListener;
import org.bubblecloud.zigbee.model.HaDeviceListener;
import org.bubblecloud.zigbee.model.SimpleDriver;
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

    private final SimpleDriver simpleDriver;
    private BundleContext context;
    private ZigBeeNetwork network;

    public ZigbeeApi(final SimpleDriver simpleDriver){
        this.simpleDriver = simpleDriver;
    }

    public void startup() {
        network = AFLayer.getAFLayer(simpleDriver).getZigBeeNetwork();
        network.addDeviceListener(this);

        context = new BundleContext();

        final ClusterFactory clusterFactory = new HAClustersFactory(context);

        context.setClusterFactory(clusterFactory);

        Map< Class<?>, Class<?> > refinedAvailables = new HashMap< Class<?>, Class<?> >();
        refinedAvailables.put(ColorDimmableLight.class, ColorDimmableLightDevice.class);
        refinedAvailables.put( DimmableLight.class, DimmableLightDevice.class );
        refinedAvailables.put( IAS_Zone.class, IAS_ZoneDevice.class );
        refinedAvailables.put( IASAncillaryControlEquipment.class, IASAncillaryControlEquipmentDevice.class );
        refinedAvailables.put( IASControlAndIndicatingEquipment.class, IASControlAndIndicatingEquipmentDevice.class );
        refinedAvailables.put( LevelControlSwitch.class, LevelControlSwitchDevice.class );
        refinedAvailables.put( LightSensor.class, LightSensorDevice.class );
        refinedAvailables.put( MainsPowerOutlet.class, MainsPowerOutletDevice.class );
        refinedAvailables.put( OccupancySensor.class, OccupancySensorDevice.class );
        refinedAvailables.put( OnOffLight.class, OnOffLightDevice.class );
        refinedAvailables.put( OnOffLightSwitch.class, OnOffLightSwitchDevice.class );
        refinedAvailables.put( OnOffOutput.class, OnOffOutputDevice.class );
        refinedAvailables.put( OnOffSwitch.class, OnOffSwitchDevice.class );
        refinedAvailables.put( OnOffLight.class, OnOffLightDevice.class );
        refinedAvailables.put( Pump.class, PumpDevice.class );
        refinedAvailables.put( TemperatureSensor.class, TemperatureSensorDevice.class );
        refinedAvailables.put( IAS_Warning.class, IAS_Warning_Device.class );
        refinedAvailables.put( SimpleSensor.class, SimpleSensorDevice.class );

        final Iterator<Map.Entry<Class<?>, Class<?>>> i = refinedAvailables.entrySet().iterator();
        while ( i.hasNext() ) {
            Map.Entry<Class<?>, Class<?>> refining = i.next();
            try {
                context.getDeviceFactories().add(
                        new GenericHADeviceFactory(context, refining.getKey(), refining.getValue()).register());
            } catch ( Exception ex) {
                logger.error( "Failed to register GenericHADeviceFactory for " + refining.getKey(), ex );
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
        final HADeviceFactory factory = context.getBestDeviceFactory(device);
        if ( factory == null) { // pending services
            logger.warn("No refinement for ZigbeeDevice {} found.", device.getPhysicalNode().getIEEEAddress());
            return;
        }

        final HADeviceBase haDevice = factory.getInstance(device);
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
    public void deviceAdded(HADeviceBase device) {
        logger.info("Device proxy added: " + device.getZBDevice().getUniqueIdenfier()
                + " (" + device.getClass().getSimpleName() + ")");
    }

    @Override
    public void deviceUpdated(HADeviceBase device) {
        logger.info("Device proxy updated: " + device.getZBDevice().getUniqueIdenfier()
                + " (" + device.getClass().getSimpleName() + ")");
    }

    @Override
    public void deviceRemoved(HADeviceBase device) {
        logger.info("Device proxy removed: " + device.getZBDevice().getUniqueIdenfier()
                + " (" + device.getClass().getSimpleName() + ")");
    }
}
