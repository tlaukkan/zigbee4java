/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies
   of the Italian National Research Council


   See the NOTICE file distributed with this work for additional
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package it.cnr.isti.zigbee.ha.driver.core;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import org.bubblecloud.zigbee.BundleContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 *
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.4.0
 *
 */
public class ClusterFactoryBase implements ClusterFactory{

    private BundleContext ctx;
    private Dictionary dictionary;
    private Hashtable<String, Class> clusters;


    public ClusterFactoryBase(BundleContext ctx){
        this.ctx = ctx;
        dictionary = new Properties();
        clusters = new Hashtable<String, Class>();
    }



    public void addProperty(String key, Object value){
        dictionary.put(key, value);
    }


    public void register(){
        Enumeration<String> keys = clusters.keys();
        String[] clusterIDs = new String[clusters.size()];
        int i =0;
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            clusterIDs[i++]=key;
        }

        dictionary.put(Cluster.PROFILE_CLUSTER_IDs,clusterIDs);
        //registration = ctx.registerService(ClusterFactory.class.getName(), this, dictionary);
    }

    protected void addCluster(String key, Class clazz) {
        clusters.put(key,clazz);
    }

    public Cluster getInstance(String key, ZigBeeDevice zbDevice) {
        Class clazz = clusters.get(key);
        if (clazz != null) {
            try {
                Constructor<?> constructor = clazz.getConstructor(ZigBeeDevice.class);
                Cluster cluster =  (Cluster) constructor.newInstance(zbDevice);
                return cluster;
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

}
