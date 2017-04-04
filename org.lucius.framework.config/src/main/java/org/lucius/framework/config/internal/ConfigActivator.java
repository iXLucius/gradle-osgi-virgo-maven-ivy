/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lucius.framework.config.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.lucius.framework.config.osgi.DefaultService;
import org.lucius.framework.config.osgi.OsgiConfigManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;

/**
 *
 * @author lqzhai
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ConfigActivator implements BundleActivator{
    private ServiceRegistration redisService;
    @Override
    public void start(BundleContext bc) throws Exception {
        OsgiConfigManager.getInstance().start(bc);
        Dictionary props = new Hashtable();
        props.put("service.pid", DefaultService.COMMON_CONFIG_ID);
        redisService = bc.registerService(ManagedService.class.getName(), new DefaultService(), props);        
    }

    @Override
    public void stop(BundleContext bc) throws Exception {
        if (redisService != null) {
            redisService.unregister();
            redisService = null;
        }        
        OsgiConfigManager.getInstance().stop();
    }
    
}
