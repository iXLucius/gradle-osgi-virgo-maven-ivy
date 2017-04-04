package org.lucius.framework.config.osgi;

import java.io.IOException;

import org.lucius.framework.config.Configuration;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lqzhai
 */
public class OsgiConfigManager {
    private static Logger log = LoggerFactory.getLogger(OsgiConfigManager.class);
    
    private static OsgiConfigManager configManager = new OsgiConfigManager();
    private ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configAdminTracker;
    private ConfigurationAdmin configurationAdmin;
//    private Map<String,Configuration> configurations = new ConcurrentHashMap<String,Configuration>();

    private OsgiConfigManager() {
    }

    public static OsgiConfigManager getInstance() {
        return configManager;
    }

    public Configuration getConfiguration(String pid){
//        if( null != configurations.get(pid)){
//            return this.configurations.get(pid);
//        }
        if( null != this.configurationAdmin){
            try {
                org.osgi.service.cm.Configuration cf = this.configurationAdmin.getConfiguration(pid);
                if( null != cf && null != cf.getProperties()){
                    Configuration c = new OsgiConfiguration(cf.getProperties());
//                    this.configurations.put(pid, c);
                    return c;
                }
            } catch (IOException ex) {
                log.error("get configuration fail! pid="+pid,ex);
            }
        }
        return null;
    }
    
    public void start(final BundleContext bc) {
        configAdminTracker = new ServiceTracker<ConfigurationAdmin, ConfigurationAdmin>(bc, ConfigurationAdmin.class, new ServiceTrackerCustomizer<ConfigurationAdmin, ConfigurationAdmin>() {
            @Override
            public ConfigurationAdmin addingService(ServiceReference<ConfigurationAdmin> sr) {
                configurationAdmin = bc.getService(sr);
                return configurationAdmin;
            }

            @Override
            public void modifiedService(ServiceReference<ConfigurationAdmin> sr, ConfigurationAdmin t) {
                configurationAdmin = bc.getService(sr);
            }

            @Override
            public void removedService(ServiceReference<ConfigurationAdmin> sr, ConfigurationAdmin t) {
                configurationAdmin = null;
                bc.ungetService(sr);
            }
        });
        configAdminTracker.open();
    }

    public void stop() {
        if (null != configAdminTracker) {
            configAdminTracker.close();
        }
    }
}
