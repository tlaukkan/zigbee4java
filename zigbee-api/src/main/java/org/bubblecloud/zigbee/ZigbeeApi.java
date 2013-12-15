package org.bubblecloud.zigbee;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.factory.HAClustersFactory;
import it.cnr.isti.zigbee.ha.device.api.generic.*;
import it.cnr.isti.zigbee.ha.device.api.hvac.Pump;
import it.cnr.isti.zigbee.ha.device.api.hvac.TemperatureSensor;
import it.cnr.isti.zigbee.ha.device.api.lighting.*;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IASAncillaryControlEquipment;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IASControlAndIndicatingEquipment;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IAS_Warning;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IAS_Zone;
import it.cnr.isti.zigbee.ha.device.impl.*;
import it.cnr.isti.zigbee.ha.driver.core.ClusterFactory;
import it.cnr.isti.zigbee.ha.driver.core.GenericHADeviceFactory;
import org.bubblecloud.zigbee.discovery.*;
import org.bubblecloud.zigbee.impl.ZigBeeNetwork;
import org.bubblecloud.zigbee.model.DeviceListener;
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
public class ZigbeeApi implements DeviceListener {
    private final static Logger logger = LoggerFactory.getLogger(ZigbeeDiscoveryManager.class);

    private final SimpleDriver simpleDriver;

    public ZigbeeApi(final SimpleDriver simpleDriver){
        this.simpleDriver = simpleDriver;
    }

    public void startup() {
        final ZigBeeNetwork network = AFLayer.getAFLayer(simpleDriver).getZigBeeNetwork();
        network.addDeviceListener(this);

        final BundleContext context = new BundleContext();

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
                        new GenericHADeviceFactory(context, refining.getKey(), refining.getValue() ).register());
            } catch ( Exception ex) {
                logger.error( "Failed to register GenericHADeviceFactory for " + refining.getKey(), ex );
            }
        }

        /*try {
            factories.add( new UnknowHADeviceFactory( bc ).register() );
        } catch ( Exception ex) {
            logger.error( "Failed to register UnknowHADeviceFactory", ex );
        }*/

    }

    public void deviceAdded(ZigBeeDevice device) {

    }

    public void deviceUpdated(ZigBeeDevice device) {

    }

    public void deviceRemoved(ZigBeeDevice device) {

    }
}
